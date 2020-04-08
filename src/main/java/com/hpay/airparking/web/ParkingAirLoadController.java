package com.hpay.airparking.web;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;


import able.com.util.fmt.StringUtil;
import able.com.web.HController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;

import com.hpay.common.service.HpayLogService;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.parking.vo.ParkingLotVO;

import com.hpay.airparking.service.ParkingAirLoadService;
import com.hpay.airparking.service.dao.AirParkingMDAO;
import com.hpay.airparking.vo.AirCollectHistoryVO;
import com.hpay.airparking.vo.AirPortParkVO;


import com.hpay.airparking.vo.airport.Items;
import com.hpay.airparking.vo.airport.Item;
import com.hpay.airparking.vo.airport.Header;



/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ParkingAirLoadController.java
 * @Description : 공항주차장  적재
 * @author o1709
 * @since 2019. 12. 02.
 * @version 1.0
 * @see
 * @Modification Information
 * 
 * 
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 12. 02.     o1709     	최초 생성
 * </pre>
 */
@RestController
/*@EnableScheduling*/
public class ParkingAirLoadController extends HController {

    
    @Resource(name = "AirParkingMDAO")
    private AirParkingMDAO AirParkingMDAO;
    
    @Resource(name = "ParkingAirLoadService")
    private ParkingAirLoadService ParkingAirLoadService;
    
    @Autowired
    HpayLogService hpayLogService;
    
    
    /**
     * [요청]URL호출하고 REDIS로 저장하기(주기:2분= 60 * 2 * 1000)
     * @Scheduled(cron="0 0/2 * * * *")  : 매 2분마다 실행
     * @Scheduled(initialDelay=120000, fixedRate=120000) : 2분 후 시작하염 매 2분마다 실행
     */   
    /*@Scheduled(cron="0 0/2 * * * *")*/
    @RequestMapping(value="/HPAY-PARKING/reqParkingAirInfo.do", method = RequestMethod.POST , produces="application/json;charset=UTF-8")    
    public void loadParkingLotInfoAll() {
        
        
        String interfaceCode = propertiesService.getString("parking.air.dynamic.collect.interfaceCode"); 
        String src = propertiesService.getString("parking.cp.air.code"); //"AIR" //공항주차장
        String resultKey = propertiesService.getString("parking.cp."+src+".resultKey");
        String resultValue = propertiesService.getString("parking.cp."+src+".resultValue");

        logger.info("++++프로세스 시작 Airparking collect");
        //System.out.println("++++프로세스 시작  Airparking collect+++++++++++++++++++++++++++++++++++++++");
  
        //1. 작업서버 체크(서버이중화)
        HpayLogVO voHpayLog = null;
        int rcvSize = 0;
        int insSize = 0;
        voHpayLog = hpayLogService.initTask(interfaceCode,  HpayLogService.typeSrc , src);
        
       if (!hpayLogService.checkOrder(voHpayLog)) {
            logger.info("++++ AIR return because other server running!!!");
            return ;
        }
        AirCollectHistoryVO chvo = new AirCollectHistoryVO();
        String jobStatus="";

        try {
  
            //JSONObject responseData = new JSONObject();    
            //2. 주차장목록 key 일부: 저장일자(YYYYMMDD) + work_date_seq(3)
            logger.info("STEP1. selectWorkDateSeq ");
            chvo = ParkingAirLoadService.selectWorkDateSeqParkListUseCount();
            if(chvo == null || StringUtils.isEmpty(chvo.getWorkDateSeq())) {
                throw new Exception();
            }
            logger.info("###AirWorkDateSeq ==> "+ chvo.getWorkDateSeq());

            //3. History 저장
            chvo.setSrc(src);              
            chvo.setResult("0001");
            chvo.setResultMessage("CP호출 시작");
            ParkingAirLoadService.insertAllParkUseCountHistory(chvo);
            
            
            //4. xml vo로 전환  인천
            List<AirPortParkVO> tempList = ParkingAirLoadService.IncheonxmlToVo(src); //2019.3.27
            
            logger.info("  Incheon==>"+ tempList.size());
            //5. xml vo로 전환 전국공항
            List<AirPortParkVO>  saveList = ParkingAirLoadService.AirxmlToVo(src, tempList); //50%
            
            rcvSize = saveList.size();
                         
            logger.info(" ALLAIR==>"+rcvSize);
            
            //6. 날씨 정보 수신 데이터 insert
            ParkingAirLoadService.insertAirPort(saveList);
            
            
            logger.info("savelist는"+saveList.get(0).toString());
            //7. Redis 전체 삭제 후 적재
            insSize = ParkingAirLoadService.jsonToRedis(saveList);
            
            //7.1 파일로 저장
            ParkingAirLoadService.reqCollectToJson(chvo, saveList);
            
            //8. History 수정 
            chvo.setResult(getMessage("airparking.success.cd"));
            chvo.setResultMessage(getMessage("airparking.success.msg"));
            jobStatus="DONE";
 
        } catch (PSQLException e) {
            logger.info("공항주차장  적재 시 PSQLException :"+e.getMessage());
            jobStatus="FAIL";
            if(chvo != null) {
                chvo.setResult(getMessage("airparking.error.PSQLException.cd"));   //9995 기타오류(Exception)
                chvo.setResultMessage(getMessage("airparking.error.PSQLException.msg"));
            }
            e.printStackTrace();
        } catch (Exception e) {
            logger.info("공항주차장 적재 시 Exception :"+e.getMessage());
            jobStatus="FAIL";
            if(chvo != null) {
                chvo.setResult(getMessage("airparking.error.Exception.cd"));   //9995 기타오류(Exception)
                chvo.setResultMessage(getMessage("airparking.error.Exception.msg"));
            }
            e.printStackTrace();
        } finally {
            try {
                //이력 저장
                ParkingAirLoadService.updateAllParkUseCountHistory(chvo);
            
            } catch (Exception e) {
                logger.info("공항주차장 적재 이력 저장 시 오류======>"+e.getMessage());
                e.printStackTrace();
            }    
            
            //서버이중화체크
            try {
           
                String errCode = "";
                String errMsg = "";
                if(chvo != null && !StringUtil.isEmpty(chvo.getResult())) { errCode =chvo.getResult(); }
                if(chvo != null && !StringUtil.isEmpty(chvo.getResultMessage())) { errMsg =chvo.getResultMessage(); }
                if("DONE".equals(jobStatus)) {
                    hpayLogService.setCount(voHpayLog, rcvSize, insSize);
                    hpayLogService.setDone(voHpayLog, HpayLogService.statusDone, errCode, errMsg);
                } else {
                    hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, errCode, errMsg);
                }
                hpayLogService.update(voHpayLog);
                logger.info("++++프로세스 종료 Airparking.dynamic.collect");
             
            } catch (Exception e) {
                logger.info("++++프로세스 종료 Airparking.dynamic.collect Exception : "+e.getMessage());
                e.printStackTrace();
            }

            
        }

    }



}
