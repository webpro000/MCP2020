package com.hpay.Hsptl.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import able.com.web.HController;

import org.apache.commons.collections4.map.MultiValueMap;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.Hsptl.vo.HsptVO;
import com.hpay.Hsptl.vo.Item;
import com.hpay.notice.vo.NoticeVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HsptlController.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 14.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 14.     webpro000     	최초 생성
 * </pre>
 */
@RestController
public class HsptlController  extends HController {
    
    @RequestMapping(path="/TEST/HsptlListCall.do")
    public @ResponseBody String HsptlListCall(Model model, HttpServletRequest req){
        // RestTemplate 에 MessageConverter 세팅
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
     
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(converters);
     
        // 전송 parameter 세팅
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("str", "전송데이터");
     
        // REST API 호출
        String ReturnJson = restTemplate.postForObject("http://localhost:8080/TEST/HsptlList.do", map, String.class);
        System.out.println("------------------ Return 결과 ------------------");
        System.out.println(ReturnJson);
        
        
        ObjectMapper mapper = new ObjectMapper();
        HsptVO Hvo = null;
        try{
            Hvo = mapper.readValue(ReturnJson, HsptVO.class);
            System.out.println("------------------ Json Parsing OK ------------------");
            System.out.println(Hvo.toString());
            for( int i=0; i<Hvo.getItems().size();i++){
                Item item = (Item)Hvo.getItems().get(i);
                System.out.println(item.toString());
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return ReturnJson;
    }
 
    
}
