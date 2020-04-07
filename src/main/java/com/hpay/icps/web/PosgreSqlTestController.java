package com.hpay.icps.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import able.com.service.prop.PropertyService;
import able.com.web.HController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.common.service.ConnCertService;
import com.hpay.common.service.ConnRedisService;
import com.hpay.common.service.HpayLogService;
import com.hpay.common.service.UtilityService;
import com.hpay.common.vo.HpayLogVO;
//import com.hpay.hmns_icps.vo.StoreDeltaPackageVO;
import com.hpay.icps.service.DeltaService;
import com.hpay.icps.service.HpayStoreAddedService;
import com.hpay.icps.service.StoreDeletedService;
import com.hpay.icps.vo.StoreDeletedPackageVO;
import com.hpay.icps.vo.StoreDeletedVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : PosgreSqlTestController.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 6.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 6.     webpro000     	최초 생성
 * </pre>
 */
@Controller
public class PosgreSqlTestController extends HController {

    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;
    
    
    @Autowired
    PropertyService propertyService;
    
    
    //@RequestMapping(value="/reqProvideDayDeleteList_.do", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @RequestMapping(value="/TEST2.do")
    public @ResponseBody String TEST2() {
        logger.info("+++Del Store : send binary to ICTS : Start");
        //String interfaceCode = "TEST2"; // propertyService.getString("icps.delstore3.interfacecode")
        String interfaceCode = propertyService.getString("icps.delstore3.interfacecode");
        HpayLogVO voHpayLog = hpayLogService.init( interfaceCode , "", "");
        logger.info("+++Del Store : send binary to ICTS : " + voHpayLog.toString());
        
        return voHpayLog.getReturnData("fail");    
    }
    
    @RequestMapping(value="/TEST.do")
    public @ResponseBody String TEST() {
        
        return "TEST";    
    }
    
    @RequestMapping(value="/TEST3.do")
    public @ResponseBody String receiveStoreDelta(
            //@RequestBody @Valid StoreDeltaPackageVO voDeltaPackage, 
            //BindingResult bindingResult,
            HttpServletRequest request) {
        logger.info("+++sendDeltaToMCP : Start");
        HpayLogVO voHpayLog = hpayLogService.init(propertiesService.getString("hmns.delta.interfacecode"));
        hpayLogService.update(voHpayLog);
        
        String responseBody="";
        responseBody="{\"interfaceCode\":\""+voHpayLog.getInterface_code()+"\", \"resultCode\":\"200\", \"resultMessage\":\"성공\"}";
        logger.info("+++sendDeltaToMCP : Success : \n" +responseBody);  
        
        return responseBody;
        
        
        
    }
        
        
        
        
        
}
