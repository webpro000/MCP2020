package com.hpay.hsptl.web;

import javax.servlet.http.HttpServletRequest;

import able.com.web.HController;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.hsptl.vo.*;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HsptlController.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 20.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 20.     webpro000     	최초 생성
 * </pre>
 */
@RestController
public class HsptlController extends HController {

    @RequestMapping(path="/TEST/HsptlList.do")
    public HsptVO HsptlList(Model model, HttpServletRequest req){
        HsptVO Hvo=new HsptVO();
        try{
            logger.info("-------------------------------------------Start");
            
            Item item1=new Item();
            item1.setRnum(100);
            item1.setDutyAddr("서울시");
            item1.setDutyDiv("변원분류");
            Hvo.getItems().add(item1);
            
            Item item2=new Item();
            item2.setRnum(101);
            item2.setDutyAddr("서울시11");
            item2.setDutyDiv("변원분류11");
            Hvo.getItems().add(item2);
            
            Hvo.setResultCode("OK");
            Hvo.setResultMag("성공입니다.");

            logger.info("-------------------------------------------End");
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return Hvo;
    }
    

    @RequestMapping(value="/TEST/HsptlListCall.do")
    public String ParmarcyListCall(){
        
        //Unmarshaller unmarshaller;
        String Url = "http://localhost:8080/TEST/HsptlList.do";
        
        RestTemplate restTemplate = new RestTemplate();
        String ReturnJson = restTemplate.postForObject(Url, "", String.class);  // XML to Json
        System.out.println(ReturnJson);
        
        ObjectMapper mapper = new ObjectMapper();
        HsptVO Hsptlist = new HsptVO();
        try{
            Hsptlist = mapper.readValue(ReturnJson, HsptVO.class);
            System.out.println("ResultCode:"+Hsptlist.getResultCode());
            for(int i=0; i<Hsptlist.getItems().size(); i++){
                Item item = Hsptlist.getItems().get(i);
                System.out.println(item.toString());
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ReturnJson;
    }
    
    
    
}
