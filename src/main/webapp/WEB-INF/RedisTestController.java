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

import org.apache.log4j.Logger;
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
public class RedisTestController {
    
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
        
        voHpayLog = hpayLogService.init(interfaceCode, HpayLogService.typeSrc, src);
        
        if(!hpayLogService.checkOrder(voHpayLog))
        {
            logger.info("++++ AIR return because other server running!!!");
            return ;
        }
        
        AirCollectHistoryVO chvo = new AirCollectHistoryVO();
        String jobStatus = "";
        
        try{
            
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
            ParkingAirLoadService.insertAllParkUseCountHistory(chvo);
            
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
            
        }catch(PSQLException e)
        {
            logger.info("공항 주차장 적재시 PSQLException:"+e.getMessage());
            jobStatus = "FAIL";
            if(chvo!= null){
               
                
            }
            e.printStackTrace();
        }catch(Exception e)
        {
            logger.info("공항주차장 적재 시 Exception :"+e.getMessage());
            jobStatus="FAIL";
            e.printStackTrace();
        }finally
        {
            
        }

        
    }
    

}
