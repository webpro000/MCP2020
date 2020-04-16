package com.hpay.pharmacy.web;

import able.com.web.HController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hpay.pharmacy.vo.*;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : PharmacyController.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 16.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 16.     webpro000     	최초 생성
 * </pre>
 */
@RestController
public class PharmacyController  extends HController {
    
    /**
     * XML 반환
     * Statements
     *
     * @return
     */
    @RequestMapping(value="/TEST/PharmacyList.do")
    public PharmacyListVO ParmarcyList(){
        PharmacyListVO parmlist = new PharmacyListVO();
        parmlist.getRecord().add(new PharmacyVO(1,"aaa","aaaaaaaa1111111"));
        parmlist.getRecord().add(new PharmacyVO(2,"bbbb","bbbbb22222"));
        parmlist.getRecord().add(new PharmacyVO(3,"cc","cccccc3333333333"));
        
        parmlist.setRecordCnt( parmlist.getRecord().size() );
        
        return parmlist;
    }
    
    /**
     * XML 수집.
     * Statements
     *
     * @return
     */
    @RequestMapping(value="/TEST/PharmacyListCall.do")
    public @ResponseBody String ParmarcyListCall(){
        
        //Unmarshaller unmarshaller;
        String Url = "http://localhost:8080/TEST/PharmacyList.do";
        
        RestTemplate restTemplate = new RestTemplate();
        String ReturnJson = restTemplate.postForObject(Url, "", String.class);  // XML to Json
        System.out.println(ReturnJson);
        
        ObjectMapper mapper = new ObjectMapper();
        PharmacyListVO pharmlist = new PharmacyListVO();
        try{
            pharmlist = mapper.readValue(ReturnJson, PharmacyListVO.class);
            System.out.println(pharmlist.getRecordCnt());
            for(int i=0; i<pharmlist.getRecord().size(); i++){
                PharmacyVO pharm = pharmlist.getRecord().get(i);
                System.out.println(pharm.toString());
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ReturnJson;
    }
    
    
}
