package com.hpay.diningcd.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import able.com.service.prop.PropertyService;
import able.com.web.HController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hpay.diningcd.service.DincdService;
import com.hpay.diningcd.service.dao.DincdDAO;
import com.hpay.diningcd.vo.DincdJsonVO;
import com.hpay.diningcd.vo.DincdVO;
import com.hpay.diningcd.vo.DincdVO2;
import com.hpay.diningcd.vo.Item;
import com.hpay.diningcd.vo.Item_additional;
import com.hpay.diningcd.vo.Item_basic;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.notice.vo.NoticeVO;

import org.apache.commons.net.ntp.TimeStamp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DiningCodeTest.java
 * @Description : 클래스 설명을 기술합니다.
 * @author 김진우
 * @since 2020. 4. 13.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 13.     김진우     	최초 생성
 * </pre>
 */

@RestController
public class DiningCodeTestContoller extends HController {
    
 
    
    
    @Resource(name="dincdService")
    private DincdService dincdService;
    
    
    @Autowired
    PropertyService propertyService;
    
    @RequestMapping(value="/DincdTest.do")
    public @ResponseBody DincdVO2 receiveStoreDelta(HttpServletRequest request)  {
           
        DincdVO dincdVO = new DincdVO();
            
        DincdVO2 dincdVO2 = new DincdVO2();
        
        logger.info("시작");
        
        try{                                
            
            dincdVO = dincdService.selectDincdList();   //selectDincdList 서비스 호출.                 
            
            dincdVO2.setBusinessId(dincdVO.getBusinessId());
            
            Item_basic item3 = new Item_basic();
            
            item3.setName(dincdVO.getName());
            
            item3.setAddress(dincdVO.getAddress());
            
            item3.setTelephone(dincdVO.getTelephone());
            
            item3.setLatitude(dincdVO.getLatitude());            
           
            item3.setLongitude(dincdVO.getLongitude());
            
            dincdVO2.getBasic_data().add(item3);
                                    
               
             
        }catch(Exception e){
            e.printStackTrace();
        }
       
        logger.info("+++sendDeltaToMCP : Success : \n" +dincdVO2.toString());  
        return dincdVO2;
        
    }
    
    @RequestMapping(value="/DincdJsonPars.do")
    public String ParmarcyListCall() throws java.text.ParseException{
        
        //Unmarshaller unmarshaller;
        String Url = "http://localhost:8080/DincdTest.do";
        
        RestTemplate restTemplate = new RestTemplate();
        String ReturnJson = restTemplate.postForObject(Url, "", String.class);       
        JSONParser parser = new JSONParser();
        JSONObject obj = null;        
        try{
            
            obj = (JSONObject)parser.parse(ReturnJson);
            
            
        }catch(ParseException e){
            System.out.println("변환에 실패");
            e.printStackTrace();
        }
        
        try{
            Gson gson = new Gson();
            DincdJsonVO imgVO = gson.fromJson(obj.toString(), DincdJsonVO.class);
            
            if(imgVO.getBusinessId()!=0){

            //dincdService.deleteTblDincdInfo();
         
            List<Map<String,Object>> additional_data = imgVO.getAdditionnal_data();
            List<Map<String,Object>> basic_data = imgVO.getBasic_data();
                    
            Map<String,Object> input = new HashMap<String,Object>();
                    
            input.put("business_id",imgVO.getBusinessId());
                    
                    
            input.put("name",basic_data.get(0).get("name"));
            input.put("address", basic_data.get(0).get("address"));
            input.put("telephone", basic_data.get(0).get("telephone"));
            input.put("logitude",  basic_data.get(0).get("longitude"));
            input.put("latitude", basic_data.get(0).get("latitude"));
                    
           
                    
           dincdService.insertTblDincdInfo(input);   //insertDincdList 서비스 호출.  
     
         }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ReturnJson;
   
    }
}
  
