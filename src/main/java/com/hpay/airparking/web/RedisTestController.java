package com.hpay.airparking.web;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import able.com.service.prop.PropertyService;
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
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;

import com.hpay.common.service.HpayLogService;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.notice.vo.NoticeVO;
import com.hpay.parking.vo.ParkingLotVO;

import com.hpay.airparking.service.ParkingAirLoadService;
import com.hpay.airparking.service.dao.AirParkingMDAO;
import com.hpay.airparking.vo.AirCollectHistoryVO;
import com.hpay.airparking.vo.AirPortParkVO;


import com.hpay.airparking.vo.airport.Items;
import com.hpay.airparking.vo.airport.Item;
import com.hpay.airparking.vo.airport.Header;

import org.apache.log4j.Logger;
import com.hpay.common.service.HpayLogService;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : RedisTestController.java
 * @Description : 클래스 설명을 기술합니다.
 * @author 김진우
 * @since 2020. 4. 7.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 7.     김진우     	최초 생성
 * </pre>
 */
@RestController
public class RedisTestController extends HController{
    
    @Resource(name = "AirParkingMDAO")
    private AirParkingMDAO AirParkingMDAO;
    
    @Resource(name = "ParkingAirLoadService")
    private ParkingAirLoadService ParkingAirLoadService;
    
    @Autowired
    HpayLogService hpayLogService;
    
    @Autowired
    PropertyService propertyService;
    
    private static Logger logger= Logger.getLogger(RedisTestController.class);
    
    @RequestMapping(value="/HPAY-PARKING/reqParkingAirInfo.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8" )
    public void loadParkingLotInfoAll() 
    {
                
        String interfaceCode = propertyService.getString("parking.air.dynamic.collect.interfaceCode"); 
        String src = propertyService.getString("parking.cp.air.code"); //"AIR" //공항주차장
        String resultKey = propertyService.getString("parking.cp."+src+".resultKey");
        String resultValue = propertyService.getString("parking.cp."+src+".resultValue");
                        
        logger.info("++++프로세스 시작 Airparking collect");
        
        HpayLogVO voHpayLog = null;
        int rcvSize = 0;
        int insSize = 0;
        
        voHpayLog = hpayLogService.initTask(interfaceCode, HpayLogService.typeSrc, src);
        
        if(!hpayLogService.checkOrder(voHpayLog))
        {
            logger.info("++++ AIR return because other server running!!!");
            return ;
        }
        
        AirCollectHistoryVO chvo = new AirCollectHistoryVO();
        String jobStatus = "";
        
        try{
            
            
            // TO DO ... 주차장 상태 정보 REDIS 업데이트 Start.
            
            logger.info("STEP1. selectWorkDateSeq ");
            chvo = ParkingAirLoadService.selectWorkDateSeqParkListUseCount();
            if(chvo == null || StringUtils.isEmpty(chvo.getWorkDateSeq()))
            {
                throw new Exception();
            }
            
          
            
            logger.info("###AirWorkDateSeq ==> "+ chvo.getWorkDateSeq());
            
            //3. History 저장
            chvo.setSrc(src);
            chvo.setResult("0001");
            chvo.setResultMessage("CP호출 시작");
            
            /*ParkingAirLoadService.insertAllParkUseCountHistory(chvo);*/
            
            //4 xml vo로 전환 인천
            List<AirPortParkVO> tempList = ParkingAirLoadService.IncheonxmlToVo(src);
            
            logger.info("  Incheon==>"+ tempList.size());
            
            List<AirPortParkVO> savelist = ParkingAirLoadService.AirxmlToVo(src, tempList);
            
            rcvSize = savelist.size();
            
            logger.info("ALLAIR==>"+rcvSize);
            
            //날씨 정보 수신 데이터
            ParkingAirLoadService.insertAirPort(savelist);
            
            //7 redis 적재 후 삭제
            insSize = ParkingAirLoadService.jsonToRedis(savelist);
            
            //7.1 파일로 저장
            
            //8. History 수정 
            
            // TO DO ... 주차장 상태 정보 REDIS 업데이트 End.
          
            hpayLogService.setDone(voHpayLog, getMessage("airparking.success.cd"),getMessage("airparking.success.msg"));
            hpayLogService.update(voHpayLog);
            jobStatus="DONE";
            
            throw new Exception();
                    
        }catch(PSQLException e)
        {
            logger.info("공항주차장  적재 시 PSQLException :"+e.getMessage());
            jobStatus="FAIL";
            String ErrMsg = e.getLocalizedMessage();
            ErrMsg = e.getLocalizedMessage();
                        
            if(chvo != null) {
               
                hpayLogService.setDone(voHpayLog, getMessage("airparking.error.PSQLException.cd"),getMessage("airparking.error.PSQLException.msg"),ErrMsg);
                hpayLogService.update(voHpayLog);
            }
            e.printStackTrace();
        }catch(Exception e)
        {
            logger.info("공항주차장 적재 시 Exception :"+e.getMessage());
            jobStatus="FAIL";
            String ErrMsg = e.getLocalizedMessage();
            
           
                              
                hpayLogService.setDone(voHpayLog, getMessage("airparking.error.Exception.cd"),getMessage("airparking.error.Exception.msg"),ErrMsg);
                hpayLogService.update(voHpayLog);
            
            e.printStackTrace();
        }finally {
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
                    hpayLogService.update(voHpayLog);
                    
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
