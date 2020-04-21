package com.hpay.dincd.web;

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

import com.hpay.dincd.service.DincdService;
import com.hpay.dincd.service.dao.DincdDAO;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.dincd.vo.DincdJsonVO;
import com.hpay.dincd.vo.DincdVO;
import com.hpay.dincd.vo.DincdVO2;
import com.hpay.notice.vo.NoticeVO;
import com.hpay.dincd.vo.DincdVO;



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

import com.hpay.dincd.vo.DinsptVO;
import com.hpay.dincd.vo.Item;
import com.hpay.dincd.vo.Item_additional;
import com.hpay.dincd.vo.Item_basic;

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
            
            Item item2 = new Item();                        
            
            item2.setBusinessId(dincdVO.getBusinessId());
            
            dincdVO2.setBusinessId(dincdVO.getBusinessId());
            
            Item_basic item3 = new Item_basic();
            
            item3.setAddress(dincdVO.getAddress());
            
            item3.setLatitude(dincdVO.getLatitude());
            
            item3.setName(dincdVO.getName());
            
            item3.setTelephone(dincdVO.getTelephone());
            
            item3.setLongitude(dincdVO.getLongitude());
            
            dincdVO2.getBasic_data().add(item3);
            
            Item_additional item4 = new Item_additional();
                                    
            item4.setArea(dincdVO.getArea());
            
            item4.setAtmosphere(dincdVO.getAtmosphere());
            
            item4.setAiScore(dincdVO.getAiScore());
            
            item4.setRepPhoto(dincdVO.getRepPhoto());
            
            item4.setOpenHour(dincdVO.getOpenHour());
            
            item4.setVisitPurpose(dincdVO.getVisitPurpose());
            
            item4.setRepKeyword(dincdVO.getRepKeyword());
            
            item4.setServiceRating(dincdVO.getServiceRating());
            
            item4.setUptdt(dincdVO.getUptdt());
            
            SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //date 시간수정 00:00:00
            
            Date tempDate = null;
            
            tempDate = beforeFormat.parse(dincdVO.getRegdt());
            
            String transDate = sdf.format(tempDate);                                  
            
            item4.setRegdt(transDate);
            
            item4.setRepFoodKeyword(dincdVO.getRepFoodKeyword());
            
            item4.setMenuPhoto(dincdVO.getMenuPhoto());
            
            item4.setAvgRating(dincdVO.getAvgRating());
            
            item4.setFacility(dincdVO.getFacility());
            
            item4.setPriceRating(dincdVO.getPriceRating());
            
            item4.setrLikeCount(dincdVO.getrLikeCount());
            
            item4.setTasteRating(dincdVO.getTasteRating());
                        
            dincdVO2.getAdditionnal_data().add(item4);                          
               
             
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
        String ReturnJson = restTemplate.postForObject(Url, "", String.class);  // XML to Json              
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
                    
                
            dincdService.deleteTblDincdInfo();
                    
                   
                            
            List<Map<String,Object>> additional_data = imgVO.getAdditionnal_data();
            List<Map<String,Object>> basic_data = imgVO.getBasic_data();
                    
            Map<String,Object> input = new HashMap<String,Object>();
                    
            input.put("business_id",imgVO.getBusinessId());
                    
                    
            input.put("name",basic_data.get(0).get("name"));
            input.put("address", basic_data.get(0).get("address"));
            input.put("telephone", basic_data.get(0).get("telephone"));
            input.put("logitude",  basic_data.get(0).get("longitude"));
            input.put("latitude", basic_data.get(0).get("latitude"));
                    
                   
            input.put("avg_rating",additional_data.get(0).get("avgRating"));
            input.put("ai_score",additional_data.get(0).get("aiScore"));
            input.put("open_hour",additional_data.get(0).get("openHour"));        
            input.put("rep_photo",additional_data.get(0).get("repPhoto"));
            input.put("menu_photo",additional_data.get(0).get("menuPhoto"));
            input.put("area",additional_data.get(0).get("area"));
            input.put("r_like_count",additional_data.get(0).get("rLikeCount"));
            input.put("ai_score",additional_data.get(0).get("aiScore"));
            input.put("rep_food_keyword",additional_data.get(0).get("repFoodKeyword"));
            input.put("rep_keyword",additional_data.get(0).get("repKeyword"));
            input.put("taste_rating",additional_data.get(0).get("tasteRating"));
            input.put("price_rating",additional_data.get(0).get("priceRating"));
            input.put("service_rating",additional_data.get(0).get("serviceRating"));        
            input.put("visit_purpose",additional_data.get(0).get("visitPurpose"));
            input.put("facility",additional_data.get(0).get("facility"));
            input.put("atmosphere",additional_data.get(0).get("atmosphere"));
                    
            String date = (String) additional_data.get(0).get("regdt");
                    
            SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
                    
            Date tempdate = null;
                    
            tempdate = beforeFormat.parse(date);
                    
            input.put("regdt",tempdate);
            input.put("rep_food_keyword",additional_data.get(0).get("repFoodKeyword"));
            input.put("uptdt",additional_data.get(0).get("uptdt"));
                    
           dincdService.insertTblDincdInfo(input);   //insertDincdList 서비스 호출.  
     
         }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ReturnJson;
   
    }
}
  
