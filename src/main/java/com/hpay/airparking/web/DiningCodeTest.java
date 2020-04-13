package com.hpay.airparking.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import able.com.service.prop.PropertyService;
import able.com.web.HController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hpay.common.service.DincdService;
import com.hpay.common.service.HpayLogService;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.icps.vo.DincdVO;

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
    public @ResponseBody String receiveStoreDelta(HttpServletRequest request,DincdVO dincdvo,BindingResult bindingResult) {
            //BindingResult bindingResult,) {
        logger.info("+++sendDeltaToMCP : Start");
        HpayLogVO voHpayLog = hpayLogService.init(propertiesService.getString("icps.DincoInfo.interfacecode"));
       
        DincdVO dincdVO = null;
        
        dincdVO = dincdService.selectDincdList();
        
        
        
        String responseBody="";
        responseBody="{\"interfaceCode\":\""+voHpayLog.getInterface_code()+"\", \"resultCode\":\"200\", \"resultMessage\":\"성공\"}";
        logger.info("+++sendDeltaToMCP : Success : \n" +responseBody+"dincdVO"+dincdVO.getArea());  
        
        return responseBody;
        
        
        
    }
    

}
