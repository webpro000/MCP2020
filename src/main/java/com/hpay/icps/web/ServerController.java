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
 * @ClassName   : ServerController.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 9.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =======    ===========================
 *  2019. 05. 09.       O1484       최초 생성
 *  2019. 07. 18.       O1484       Log 적용
 * </pre>
 */
@Controller
public class ServerController extends HController {
    @Resource(name="utilityService")
    private UtilityService utilityService;
    @Resource(name="hpayStoreAddedService")
    private HpayStoreAddedService hpayStoreAddedService;
    @Resource(name="connCertService")
    private ConnCertService connCertService;
    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;
    @Resource(name="storeDeletedService")
    private StoreDeletedService storeDeletedService;
    
    @Resource(name="deltaService")
    private DeltaService deltaService;

    @Resource(name = "connRedisService")
    private ConnRedisService connRedisService;

    @Autowired
    PropertyService propertyService;

    @RequestMapping(value="/cmdSendDeltaToICPS.do", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    public @ResponseBody String SendDeltaToICPS() {
        return deltaService.sendDeltaToICPS();
    }

    @RequestMapping(value="/cmdHpayDelStoreSend.do", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    public @ResponseBody String delstore1() {        
        return storeDeletedService.sendDeletedStoreInfoCompareWithRecentDelta();
    }

    @RequestMapping(value="/reqDeleteStoreInfoInitiate.do", produces="application/json;charset=UTF-8")
    public @ResponseBody String delstore2(@RequestBody @Valid StoreDeletedPackageVO voStoreDeletedPackage , BindingResult bindingResult) {
        logger.info("+++Del Store : get initiate from ICTS : Start");
        
        HpayLogVO voHpayLog = hpayLogService.init(propertyService.getString("icps.delstore2.interfacecode"), HpayLogService.typeSrc, "ICPS");
        String msgErr="";
        if (voStoreDeletedPackage.getInterface_code()==null || !voStoreDeletedPackage.getInterface_code().equals(propertyService.getString("icps.delstore2.interfacecode"))){
            msgErr="icps.delstore2.wronginterfacecode";
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, getMessage(msgErr+".cd"), getMessage(msgErr+".msg"));
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : get initiate from ICTS : Fail");
            return voHpayLog.getReturnData("fail");
        }
        logger.info("Step 1 : check interfaceCode");

        String[] arrErr=new String []{"icps.delstore2.nomandatory"};
        msgErr=checkError(bindingResult, arrErr);
        if (!msgErr.equals("")){
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, getMessage(msgErr+".cd"), getMessage(msgErr+".msg"));
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : get initiate from ICTS : Fail");
            return voHpayLog.getReturnData("fail");
        }
        logger.info("Step 2 : check mandatory");

        int countTotal=voStoreDeletedPackage.getArrVOStoreDelete().size();
        int countSuccess=0;
        logger.info("countTotal : "+ countTotal);
        List<StoreDeletedVO> arrVOStoreDeleted=voStoreDeletedPackage.getArrVOStoreDelete();
        List<String> tempKey=new ArrayList<String>();
        for(int i=0; i<arrVOStoreDeleted.size(); i++){
            String tempPoi=arrVOStoreDeleted.get(i).getPoi_id();
            if (tempPoi.equals("")){
                arrVOStoreDeleted.remove(i);
                i--;
                continue;                
            }
            if (tempKey.contains(tempPoi)){
                arrVOStoreDeleted.remove(i);
                i--;
                continue;
            }
            tempKey.add(tempPoi);
        }
        countSuccess=arrVOStoreDeleted.size();
        logger.info("countSuccess : "+ countSuccess);          
        if (countTotal!=countSuccess) {
            voStoreDeletedPackage.setArrVOStoreDelete(arrVOStoreDeleted);
        }
        logger.info("Step 3 : remove empty or duplicate poiId : "+countTotal+"->"+countSuccess);
        hpayLogService.setCount(voHpayLog, countTotal, countSuccess);

        try {
            logger.info("Step 4 : (async)receiveDeleteStoreInit");
            storeDeletedService.receiveDeleteStoreInit(voStoreDeletedPackage);            
        } catch (Exception e) {
            e.printStackTrace();
        }
        hpayLogService.setDone(voHpayLog, HpayLogService.statusDone);
        hpayLogService.update(voHpayLog);
        Map<String, String> ReturnData=new LinkedHashMap<String, String>();
        ReturnData.put("interfaceCode", voHpayLog.getInterface_code());
        ReturnData.put("resultCode", getMessage("icps.delstore2.success.cd"));
        ReturnData.put("resultMessage", getMessage("icps.delstore2.success.msg"));
        logger.info("+++Del Store : get initiate from ICTS : Success");
        return voHpayLog.getReturnData(ReturnData);
    }
    
    @RequestMapping(value="/reqProvideDayDeleteList.do", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    public @ResponseBody String delstore3() {
        HpayLogVO voHpayLog = hpayLogService.init(propertyService.getString("icps.delstore3.interfacecode"), "", "");
        logger.info("+++Del Store : send binary to ICTS : Start");
        try {
            StoreDeletedPackageVO voStoreDeletedPackage=storeDeletedService.getLastDelStore3();
            logger.info("Step 1 : get last delete store from db");            
            //ONESUN 품확기간동안 막아놓자
            //String ReturnValue=storeDeletedService.sendToICPSLastDeletedBinary(voStoreDeletedPackage);
            String ReturnValue="";
            logger.info("ONESUN 품확기간동안 막아놓자=====reqProvideDayDeleteList.do==sendToICPSLastDeletedBinary");
            
            logger.info("Step 2 : parse json & send to ICPS : "+ReturnValue);            
            if (!ReturnValue.equals("")) {
                hpayLogService.setDone(voHpayLog, HpayLogService.statusDone, "", ReturnValue);
                hpayLogService.update(voHpayLog);
                logger.info("+++Del Store : send binary to ICTS : Success");
                return ReturnValue;
            }

            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, "9999", "실패");
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : send binary to ICTS : Fail");
            return "{\"interfaceCode\":\""+propertyService.getString("icps.delta.interfacecode")+"\", \"resultCode\":\"400\", \"resultMessage\":\"실패\"}";                            
        } catch (Exception e) {
            e.printStackTrace();
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, "9999", "실패");
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : send binary to ICTS : Fail");
            return "{\"interfaceCode\":\""+propertyService.getString("icps.delta.interfacecode")+"\", \"resultCode\":\"400\", \"resultMessage\":\"실패\"}";            
        }
    }    
        
    @RequestMapping(value="/reqSearchProvideDayDeleteList.do", produces="application/json;charset=UTF-8")
    public @ResponseBody String delstore3json(@RequestBody @Valid StoreDeletedPackageVO voStoreDeletedPackage , BindingResult bindingResult , HttpServletRequest request) {
        logger.info("+++Del Store : recent deleted store data to JSON : Start");
        HpayLogVO voHpayLog = hpayLogService.init(propertyService.getString("icps.delstore3json.interfacecode"), "", "");
        String msgErr="";
        String certKey=request.getHeader("certKey");
        if (!voStoreDeletedPackage.getInterface_code().equals(propertiesService.getString("icps.delstore3json.interfacecode"))) {
            msgErr="icps.delstore3json.wronginterfacecode";
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, getMessage(msgErr+".cd"), getMessage(msgErr+".msg"));
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : recent deleted store data to JSON : Fail");
            return voHpayLog.getReturnData("fail");
        }
        logger.info("Step 1 : check interfaceCode");

        if (certKey==null) {
            msgErr="icps.delstore3json.nocertkey";
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, getMessage(msgErr+".cd"), getMessage(msgErr+".msg"));
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : recent deleted store data to JSON : Fail");
            return voHpayLog.getReturnData("fail");
        } 
        
        Map<String, Object> resultValidation=connCertService.validationCertKey(certKey, propertyService.getString("icps.delstore3json.interfacecode"));            
        if ( resultValidation.containsKey("resultCode") && resultValidation.get("resultCode").equals("200") ){
            hpayLogService.setTarget(voHpayLog, HpayLogService.typeCertKeyUser, resultValidation.get("certKeyUser").toString());
        } else {
            msgErr="icps.delstore3json.errorcertkey";
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, getMessage(msgErr+".cd"), getMessage(msgErr+".msg"));
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : recent deleted store data to JSON : Fail");
            return voHpayLog.getReturnData("fail");
        }
        logger.info("Step 2 : validate certKey");
    
        String[] arrErr=new String []{"icps.delstore3json.nomandatory"};
        msgErr=checkError(bindingResult, arrErr);
        if (!msgErr.equals("")) {
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, getMessage(msgErr+".cd"), getMessage(msgErr+".msg"));
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store : recent deleted store data to JSON : Fail");
            return voHpayLog.getReturnData("fail");
        }
        logger.info("Step 3 : check mandatory");

        StoreDeletedPackageVO voStoreDeletedPackageRecent=storeDeletedService.getLastDelStore3();
        logger.info("Step 4 : get last delete store from db");

        String json ="";
        if (voStoreDeletedPackageRecent.getData_version()==null){
            json =storeDeletedService.parseVOToJson("hpaySearchProvideDayDeleteList_no_delete", voStoreDeletedPackageRecent);
        } else if (voStoreDeletedPackage.getData_version().equals(voStoreDeletedPackageRecent.getData_version_delta()+"_"+voStoreDeletedPackageRecent.getData_version())){
            json =storeDeletedService.parseVOToJson("hpaySearchProvideDayDeleteList_version_same", voStoreDeletedPackageRecent);
        } else {
            json =storeDeletedService.parseVOToJson("hpaySearchProvideDayDeleteList_version_different", voStoreDeletedPackageRecent);
        }
        logger.info("Step 5 : parse json");

        if (propertyService.getBoolean("icps.delstore3json.isSaveJson")) {
            String fileName=propertyService.getString("icps.delstore3json.dump.path")+new Date().getTime();
            try {
                utilityService.SaveFile(propertyService.getString("icps.delstore3json.dump.prefix"), fileName , json);
                logger.info("STEP 6 : save json Success : "+fileName );
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("STEP 6 : save json Fail");
            }            
        }
        
        hpayLogService.setDone(voHpayLog, HpayLogService.statusDone);
        hpayLogService.update(voHpayLog);
        logger.info("+++Del Store : recent deleted store data to JSON : Success");
        return json;
    }
    
    
    private String checkError(BindingResult bindingResult, String[] arrErr) {
       if (bindingResult.hasErrors()) { 
           List<FieldError>  errors=bindingResult.getFieldErrors();
           for(int i =0; i<arrErr.length; i++){
               boolean isError=false; 
               for(FieldError each :errors) {
                   if (each.getDefaultMessage().indexOf(arrErr[i]) > -1) {
                       isError=true;
                       break;
                   }
               }
               if (isError){
                   return arrErr[i];
               }
           }
       }
        return "";
    }
}