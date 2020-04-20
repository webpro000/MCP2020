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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import com.hpay.dincd.service.DincdService;
import com.hpay.common.service.HpayLogService;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.dincd.vo.DincdJsonVO;
import com.hpay.dincd.vo.DincdVO;
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
    
    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;
    
    
    @Resource(name="dincdService")
    private DincdService dincdService;
    
    
    @Autowired
    PropertyService propertyService;
    
    @RequestMapping(value="/DincdTest.do" ,method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    public @ResponseBody DinsptVO receiveStoreDelta(HttpServletRequest request,DincdVO dincdvo,BindingResult bindingResult)  {
           
        DincdVO dincdVO = new DincdVO();
        
        DinsptVO dinsptVO = new DinsptVO();
        
        logger.info("시작");
        
        try{
            HpayLogVO voHpayLog = hpayLogService.init(propertiesService.getString("icps.DincoInfo.interfacecode"));                        
            
            dincdVO = dincdService.selectDincdList();   //selectDincdList 서비스 호출.  
            
            dinsptVO.setBusiness_id(dincdVO.getBusinessId());                    
                                   
            Map<String,Object> input = new HashMap<String,Object>();
                    
            Map<String,Object> realinput1 = new HashMap<String,Object>();
            
            realinput1.put("name",dincdVO.getName());
            
            realinput1.put("address",dincdVO.getAddress());
            
            realinput1.put("telephone",dincdVO.getTelephone());
            
            realinput1.put("longitude",dincdVO.getLongitude());
            
            realinput1.put("latitude",dincdVO.getLatitude());
            
            List<Map<String,Object>> realinlist1 =new ArrayList<Map<String,Object>>();
            
            realinlist1.add(realinput1);
            
            dinsptVO.setBasic_data(realinlist1);                                  
            
            
            Map<String,Object> realinput2 = new HashMap<String,Object>();
            
            realinput2.put("avgRating",dincdVO.getAvgRating());
            
            realinput2.put("aiScore", dincdVO.getAiScore());
            
            realinput2.put("openHour", dincdVO.getOpenHour());
            
            realinput2.put("repPhoto", dincdVO.getRepPhoto());
            
            realinput2.put("menuPhoto", dincdVO.getMenuPhoto());
                      
            realinput2.put("area", dincdVO.getArea());
            
            realinput2.put("rLikeCount", dincdVO.getrLikeCount());
            
            realinput2.put("repFoodKeyword", dincdVO.getRepFoodKeyword());
            
            realinput2.put("repKeyword", dincdVO.getRepKeyword());
            
            realinput2.put("tasteRating", dincdVO.getTasteRating());
            
            realinput2.put("priceRating", dincdVO.getPriceRating());
            
            realinput2.put("serviceRating", dincdVO.getServiceRating());
            
            realinput2.put("visitPurpose", dincdVO.getVisitPurpose());
            
            realinput2.put("facility", dincdVO.getFacility());
            
            realinput2.put("atmosphere", dincdVO.getAtmosphere());
            
            SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
           
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //date 시간수정 00:00:00
            
                       
            Date tempDate = null;
            
            tempDate = beforeFormat.parse(dincdVO.getRegdt());
            
           String transDate = sdf.format(tempDate);
                        
            logger.info("transDat22e"+transDate );
            
            realinput2.put("regdt",transDate );
            
            realinput2.put("uptdt", dincdVO.getUptdt());
    
            List<Map<String,Object>> realinlist2 =new ArrayList<Map<String,Object>>();
            
            realinlist2.add(realinput2);
            
            
            dinsptVO.setAdditionnal_data(realinlist2);          //additionalList의 정보를 VO에 입력
        }catch(Exception e){
            e.printStackTrace();
        }
       
        logger.info("+++sendDeltaToMCP : Success : \n" +dinsptVO.toString());  
        return dinsptVO;
        
    }
    
    @RequestMapping(value="/DincdJsonPars.do" ,method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    public @ResponseBody DinsptVO DincdJsonPars(HttpServletRequest request) throws FileNotFoundException, IOException, java.text.ParseException   {
        
               
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        JSONObject jsonObj2 = null;
        try{
            obj = (JSONObject)parser.parse(new FileReader("C://GIT//MCP2020//jsontemp//DincdTest_1.json"));
            JSONArray companyList = (JSONArray)obj.get("basic_data");
            
            for(int i=0;i<companyList.size();i++)
            {
               jsonObj2 = (JSONObject)companyList.get(i);                        
            }
            
            
        }catch(ParseException e){
            System.out.println("변환에 실패");
            e.printStackTrace();
        }
        
        logger.info(obj.toString());
        logger.info(jsonObj2.toString());
        
        
        Gson gson = new Gson();
        DincdJsonVO imgVO = gson.fromJson(obj.toString(), DincdJsonVO.class);
        
        logger.info("imgVO"+imgVO.getAdditionnal_data());
        logger.info("imgVO"+imgVO.getBasic_data());
        logger.info("imgVO"+imgVO.getBusiness_id());
        
        List<Map<String,Object>> additional_data = imgVO.getAdditionnal_data();
        List<Map<String,Object>> basic_data = imgVO.getBasic_data();
        int business_id = imgVO.getBusiness_id();
        
        Map<String,Object> input = new HashMap<String,Object>();
        
        input.put("business_id",imgVO.getBusiness_id());
        
        
       /* basic_data.get(0).get("name");
        basic_data.get(0).get("address");
        basic_data.get(0).get("telephone");
        basic_data.get(0).get("longitude");
        basic_data.get(0).get("latitude"); */           
        
        input.put("name",basic_data.get(0).get("name"));
        input.put("address", basic_data.get(0).get("address"));
        input.put("telephone", basic_data.get(0).get("telephone"));
        input.put("logitude",  basic_data.get(0).get("longitude"));
        input.put("latitude", basic_data.get(0).get("latitude"));
        
        
        /*additional_data.get(0).get("avgRating");
        additional_data.get(0).get("aiScore");
        additional_data.get(0).get("openHour");
        additional_data.get(0).get("repPhoto");
        additional_data.get(0).get("menuPhoto");
        additional_data.get(0).get("area");
        additional_data.get(0).get("rLikeCount");        
        additional_data.get(0).get("aiScore");
        additional_data.get(0).get("repFoodKeyword");
        additional_data.get(0).get("repKeyword");
        additional_data.get(0).get("tasteRating");
        additional_data.get(0).get("priceRating");
        additional_data.get(0).get("serviceRating");
        additional_data.get(0).get("visitPurpose");
        additional_data.get(0).get("facility");
        additional_data.get(0).get("atmosphere");
        additional_data.get(0).get("regdt");
        additional_data.get(0).get("repFoodKeyword");
        additional_data.get(0).get("uptdt");*/
        
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
        
        
        
        return null;
        
        
    }
}
  
