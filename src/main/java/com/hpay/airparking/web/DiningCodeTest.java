package com.hpay.airparking.web;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.Hsptl.vo.HsptVO;
import com.hpay.Hsptl.vo.Item;
import com.hpay.common.service.DincdService;
import com.hpay.common.service.HpayLogService;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.icps.vo.DincdVO;
import com.hpay.notice.vo.NoticeVO;
import org.apache.commons.beanutils.BeanUtils;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.hpay.icps.vo.DinsptVO;

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
public class DiningCodeTest extends HController {
    
    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;
    
    
    @Resource(name="dincdService")
    private DincdService dincdService;
    
    
    @Autowired
    PropertyService propertyService;
    
    @RequestMapping(value="/DincdTest.do" ,method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    public @ResponseBody DinsptVO receiveStoreDelta(HttpServletRequest request,DincdVO dincdvo,BindingResult bindingResult) throws JsonProcessingException {
           
        DincdVO dincdVO = new DincdVO();
        
        DinsptVO dinsptVO = new DinsptVO();
        
        logger.info("시작");
        
        try{
            HpayLogVO voHpayLog = hpayLogService.init(propertiesService.getString("icps.DincoInfo.interfacecode"));                        
            
            dincdVO = dincdService.selectDincdList();   //selectDincdList 서비스 호출.  
            
            dinsptVO.setBusiness_id(dincdVO.getBusinessId());
            
            Item item = new Item();                        
                                   
            Map<String,Object> input = new HashMap<String,Object>();
            
            input = BeanUtils.describe(dincdVO); //Vo를 HashMap으로 변환
            
            Map<String,Object> realinput1 = new HashMap<String,Object>();
            
            realinput1.put("name",input.get("name"));
            
            realinput1.put("address",input.get("address"));
            
            realinput1.put("telephone",input.get("telephone"));
            
            realinput1.put("longitude",input.get("longitude"));
            
            realinput1.put("latitude",input.get("latitude"));
            
            List<Map<String,Object>> realinlist1 =new ArrayList<Map<String,Object>>();
            
            realinlist1.add(realinput1);
            
            dinsptVO.setBasic_data(realinlist1);                                  
            
            
            Map<String,Object> realinput2 = new HashMap<String,Object>();
            
            realinput2.put("avgRating", input.get("avgRating"));
            
            realinput2.put("aiScore", input.get("aiScore"));
            
            realinput2.put("openHour", input.get("openHour"));
            
            realinput2.put("repPhoto", input.get("repPhoto"));
            
            realinput2.put("menuPhoto", input.get("menuPhoto"));
            
            realinput2.put("menuPhoto", input.get("menuPhoto"));
            
            realinput2.put("area", input.get("area"));
            
            realinput2.put("rLikeCount", input.get("rLikeCount"));
            
            realinput2.put("repFoodKeyword", input.get("repFoodKeyword"));
            
            realinput2.put("repKeyword", input.get("repKeyword"));
            
            realinput2.put("tasteRating", input.get("tasteRating"));
            
            realinput2.put("priceRating", input.get("priceRating"));
            
            realinput2.put("serviceRating", input.get("serviceRating"));
            
            realinput2.put("visitPurpose", input.get("visitPurpose"));
            
            realinput2.put("facility", input.get("facility"));
            
            realinput2.put("atmosphere", input.get("atmosphere"));
            
            realinput2.put("regdt", input.get("regdt"));
            
            realinput2.put("uptdt", input.get("uptdt"));
    
            List<Map<String,Object>> realinlist2 =new ArrayList<Map<String,Object>>();
            
            realinlist2.add(realinput2);
            
            
            dinsptVO.setAdditionnal_data(realinlist2);          //additionalList의 정보를 VO에 입력
        }catch(Exception e){
            e.printStackTrace();
        }
       
        logger.info("+++sendDeltaToMCP : Success : \n" +dinsptVO.toString());  
        return dinsptVO;
        
    }
}
