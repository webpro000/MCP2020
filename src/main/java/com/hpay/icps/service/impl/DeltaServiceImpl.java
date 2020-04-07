package com.hpay.icps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import able.com.service.HService;
import able.com.service.prop.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.common.service.HpayLogService;
import com.hpay.common.service.UtilityService;
import com.hpay.common.service.impl.UtilityServiceImpl;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.icps.service.DeltaService;
import com.hpay.icps.service.dao.DeltaMDAO;
import com.hpay.icps.vo.HpayStoreDeltaPackageVO;
import com.hpay.icps.vo.StoreDetailVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DeltaServiceImpl.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 29.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 29.     O1484     	최초 생성
 * </pre>
 */
@Service("deltaService")
public class DeltaServiceImpl extends HService implements DeltaService{

    @Autowired
    PropertyService propertyService;

    @Resource(name="deltaMDAO")
    private DeltaMDAO deltaMDAO;

    @Resource(name="utilityService")
    private UtilityService utilityService;

    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;

    @Override
    public String sendDeltaToICPS() {
        logger.info("+++SendDeltaToICPS start");
        HpayLogVO voHpayLog = hpayLogService.init(propertyService.getString("icps.delta.interfacecode"), "endpoint", propertyService.getString("icps.delta.targetUri"));
        
        String version_no;
        try {
            version_no = deltaMDAO.selectDataVersion_LastDelta();
            logger.info("Step 1 : get last delta version success : "+version_no);
        } catch (Exception e) {
            logger.info("Step 1 : get last delta version : fail");
            e.printStackTrace();
            return null;
        }
        
        HpayStoreDeltaPackageVO voHpayStoreDeltaPackage;
        String json="";
        try {
            voHpayStoreDeltaPackage = deltaMDAO.selectHpayStoreDeltaPackage(version_no);
            if (voHpayStoreDeltaPackage==null) {
                logger.info("Step 2 : get delta package from DB : fail");
                return null;
            }
            logger.info("Step 2 : get delta package from DB : success");
            
            List<StoreDetailVO> arrVOStoreDetail=deltaMDAO.selectArrHpayStoreDelta(version_no);
            voHpayStoreDeltaPackage.setArrVOHpayStoreDelta(arrVOStoreDetail);
            hpayLogService.setCount(voHpayLog, arrVOStoreDetail.size(), arrVOStoreDetail.size());
            logger.info("Step 3 : get delta body from DB : success");

            json= parseVOToJson(voHpayStoreDeltaPackage);
            logger.info("Step 4 : parse vo to json : success");

        } catch (Exception e) {
            logger.info("Step 1 : get last delta version : fail");
            e.printStackTrace();
            return null;
        }

        if (json.equals("")) {
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("Step 4 : parse vo to json : fail");
            return "{\"interfaceCode\":\""+propertyService.getString("icps.delta.interfacecode")+"\", \"resultCode\":\"400\", \"resultMessage\":\"실패\"}";            
        }

        if (propertyService.getBoolean("icps.delta.isSaveJson")) {
            String fileName=propertyService.getString("icps.delta.dump.prefix")+new Date().getTime();
            try {
                utilityService.SaveFile(propertyService.getString("icps.delta.dump.path"), fileName , json);
                logger.info("Step 5 : save json Success : "+fileName );
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Step 5 : save json Fail");
            }            
        }
        

        String msg="";
        try {
            msg = utilityService.sendJson(propertyService.getString("icps.delta.targetUri"), json);
            logger.info("Step 6 : send json to ICPS Success : "+msg);
        } catch (Exception e) {
            logger.info("Step 6 : send json to ICPS Fail");
            e.printStackTrace();
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++SendDeltaToICPS Fail");
            return "{\"interfaceCode\":\""+propertyService.getString("icps.delta.interfacecode")+"\", \"resultCode\":\"400\", \"resultMessage\":\"실패\"}";            
        }        

        if (msg.equals("")){
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++SendDeltaToICPS Fail");
            return "{\"interfaceCode\":\""+propertyService.getString("icps.delta.interfacecode")+"\", \"resultCode\":\"400\", \"resultMessage\":\"실패\"}";                        
        }
        try {
            ObjectMapper mapper = new ObjectMapper(); 
            Map<String, String> map = mapper.readValue(msg, new TypeReference<Map<String, String>>(){});
            if (map.containsKey("resultCode") && map.get("resultCode").equals("0000")){
                hpayLogService.setDone(voHpayLog, HpayLogService.statusDone, map.get("resultCode"), map.get("resultMessage"));
                hpayLogService.update(voHpayLog);
            } else {
                hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, "", msg);
                hpayLogService.update(voHpayLog);                
            }
            logger.info("+++SendDeltaToICPS Success");
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return msg;
    }
        
    private String parseVOToJson(HpayStoreDeltaPackageVO voHpayStoreDeltaPackage) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        List<Object> dataCenter=new ArrayList<Object>();        
        List<StoreDetailVO> arrHpayStoreDelta=voHpayStoreDeltaPackage.getArrVOHpayStoreDelta();
        
        String[] eachOrder=new String[]{
                "poiId", "poiCode", "poiCodeDesc", "poiCodeDesc_eng", "fName", "fName_eng", "storeName", "storeName_eng"
                , "stationPhone", "src", "park_category", "address", "address_eng", "sanBun", "primaryBun", "secondaryBun"
                , "addressStr", "addressStr_eng", "lat", "lon", "storeId", "referId"                
                , "selfType", "7LvParcelId"};
        
        for (int i=0; i<arrHpayStoreDelta.size(); i++){
            Map<String, Object> row=new LinkedHashMap<String, Object>();
            for (int j=0; j<eachOrder.length;j++){
                
                String targetData=arrHpayStoreDelta.get(i).getByColumnName(eachOrder[j]);

                if(targetData !=null && !targetData.equals("null")){
                    row.put(eachOrder[j],  targetData);                
                } else {
                    row.put(eachOrder[j],  null );                
                }                   
            }
            dataCenter.add(row);
        }
        
        Map<String, Object> dataStoreDeltaPackage=new LinkedHashMap<String, Object>();
        dataStoreDeltaPackage.put("interfaceCode", "hpayRecStoreDeltaSend");
        dataStoreDeltaPackage.put("dataVersion", voHpayStoreDeltaPackage.getDataVersion());
        dataStoreDeltaPackage.put("formatVersion", voHpayStoreDeltaPackage.getFormatVersion());
        dataStoreDeltaPackage.put("centerData", dataCenter);
        dataStoreDeltaPackage.put("2WBinary", voHpayStoreDeltaPackage.getBinary2W());
        String jsonInString = mapper.writeValueAsString(dataStoreDeltaPackage);        
               
        return jsonInString;
    }
}