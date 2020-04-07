package com.hpay.icps.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import able.com.service.HService;
import able.com.service.prop.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.common.service.HpayLogService;
import com.hpay.common.service.UtilityService;
import com.hpay.common.service.impl.UtilityServiceImpl;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.icps.service.StoreDeletedService;
import com.hpay.icps.service.dao.DeltaMDAO;
import com.hpay.icps.service.dao.StoreDeletedMDAO;
import com.hpay.icps.vo.StoreDeletedPackageVO;
import com.hpay.icps.vo.StoreDeletedVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : StoreDeletedServiceImpl.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 7. 1.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 7. 1.     O1484     	최초 생성
 * </pre>
 */
@Service("storeDeletedService")
public class StoreDeletedServiceImpl extends HService implements StoreDeletedService{
    @Autowired
    PropertyService propertyService;
    
    @Resource(name = "deltaMDAO")
    private DeltaMDAO deltaMDAO; 

    @Resource(name = "storeDeletedMDAO")
    private StoreDeletedMDAO storeDeletedMDAO;
    
    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;

    private String getWorkSeq(String interfaceCode, String workDate){
        logger.info("workSeq : "+interfaceCode+"___"+workDate);
        String workSeq;
        try {
            workSeq = storeDeletedMDAO.selectTodayLastSeq(interfaceCode, workDate);
            if (workSeq==null){
                workSeq="01";
            } else {
                workSeq=String.format("%02d", Integer.parseInt(workSeq)+1);
            }
        } catch (Exception e) {
            workSeq="01";
        }
        logger.info("workSeq : "+workSeq);
        return workSeq;
    }
    
    private Boolean insertStoreDeleted(StoreDeletedPackageVO voStoreDeletedPackage){
        try {
            int hpayStoreDeletePackageSeq=storeDeletedMDAO.insertStoreDeletedPackage(voStoreDeletedPackage);
            voStoreDeletedPackage.setHpay_store_delete_package_seq(hpayStoreDeletePackageSeq);
            if (voStoreDeletedPackage.getArrVOStoreDelete()!=null && voStoreDeletedPackage.getArrVOStoreDelete().size()>0){
                storeDeletedMDAO.insertArrStoreDeleted(voStoreDeletedPackage.getArrVOStoreDelete());                
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    private boolean parseRecentDelstore2To3(StoreDeletedPackageVO voStoreDeletedPackage) {
        voStoreDeletedPackage.setInterface_code(propertyService.getString("icps.delstore3.interfacecode"));
        if (voStoreDeletedPackage.getData_version_delta().length()>21) {
            voStoreDeletedPackage.setData_version_delta(voStoreDeletedPackage.getData_version_delta().substring(0, 21));
        }
        
        String today= new SimpleDateFormat ( "yyyyMMdd").format(new Date());        
        String workSeq=getWorkSeq(voStoreDeletedPackage.getInterface_code(), today);
        
        voStoreDeletedPackage.setWork_date(today);
        voStoreDeletedPackage.setWork_date_seq(workSeq);
        voStoreDeletedPackage.setData_version(voStoreDeletedPackage.getWork_date()+"_"+voStoreDeletedPackage.getWork_date_seq());
        try {            
            //지난것 모두 모은다.  
            voStoreDeletedPackage.setArrVOStoreDelete(storeDeletedMDAO.selectStoreDeleted_byId(voStoreDeletedPackage.getHpay_store_delete_package_seq()));
            UtilityService utilityService=new UtilityServiceImpl();
            byte[] binary=parseVOToBinary(voStoreDeletedPackage);
            
            //utilityService.SaveFile("C:\\Users\\Administrator\\Downloads\\test_file\\", "test.bin", binary);
            
            String encoded=utilityService.encodeBase64(binary);
            logger.info("encoded : "+encoded);

            /*
            byte[] compressed = utilityService.GzipCompress(binary);
            String encoded=utilityService.encodeBase64(compressed);
            */
            voStoreDeletedPackage.setBinary_delete(encoded);
            return true;
        } catch (Exception e) {
            return false; 
        }
    }
    
    public StoreDeletedPackageVO getLastDelStore3(){
       
        /* //하루에 한번만 만들꺼면 주석해제
        try {
            voStoreDeletedPackage = storeDeletedMDAO.selectTodayIniticatedBinaryPackage();
            if (voStoreDeletedPackage!=null){
                return voStoreDeletedPackage;
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        String dataVersion_LastDelta;
        try {
            dataVersion_LastDelta = deltaMDAO.selectDataVersion_LastDelta();
            if (dataVersion_LastDelta.length()>21){
                dataVersion_LastDelta=dataVersion_LastDelta.substring(0,  21);
            }
            logger.info("getLastDelStore3 : last version="+dataVersion_LastDelta);
        } catch (Exception e) {
            logger.info("getLastDelStore3 : no DeltaData");
            return null;
        }

        StoreDeletedPackageVO voStoreDeletedPackage;
        try {            
            voStoreDeletedPackage = storeDeletedMDAO.selectLastInitiatedDeletedPackage(dataVersion_LastDelta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        if (voStoreDeletedPackage==null){
            voStoreDeletedPackage=new StoreDeletedPackageVO();
            voStoreDeletedPackage.setInterface_code(propertyService.getString("icps.delstore3.interfacecode"));
            voStoreDeletedPackage.setData_version_delta(dataVersion_LastDelta);
            return voStoreDeletedPackage;
        }
        
        if (voStoreDeletedPackage.getInterface_code().equals(propertyService.getString("icps.delstore3.interfacecode"))){
            try {
                logger.info("---------------"+voStoreDeletedPackage.getHpay_store_delete_package_seq());
                List<StoreDeletedVO> arrVOStoreDeleted=storeDeletedMDAO.selectStoreDeleted_byId(voStoreDeletedPackage.getHpay_store_delete_package_seq());
                voStoreDeletedPackage.setArrVOStoreDelete(arrVOStoreDeleted);                
            } catch (Exception e) {
                e.printStackTrace();
            }
            return voStoreDeletedPackage; //그냥 그대로 return
        }

        if (parseRecentDelstore2To3(voStoreDeletedPackage)){
            insertStoreDeleted(voStoreDeletedPackage);       
            logger.info("---insertStoreDeleted");
        }        
        return voStoreDeletedPackage;
    }
    
    public String sendToICPSLastDeletedBinary(StoreDeletedPackageVO voStoreDeletedPackage) {
        logger.info("method : sendToICPSLastDeletedBinary : Start");
        String json=parseVOToJson("hpayProvidDayDelSend", voStoreDeletedPackage);
        logger.info("1 : parseVOToJson");
        
        //logger.info(json);
        
        UtilityService utilityService=new UtilityServiceImpl();        
        if (propertyService.getBoolean("icps.delstore3.isSaveJson")) {
            String fileName=propertyService.getString("icps.delstore3.dump.prefix")+new Date().getTime();
            try {
                utilityService.SaveFile(propertyService.getString("icps.delstore3.dump.path"), fileName , json);
                logger.info("STEP 2 : save json Success : "+fileName );
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("STEP 2 : save json Fail");
            }            
        }
        
        try {
            String returnValue = utilityService.sendJson(propertyService.getString("icps.delstore3.targetUri"), json);
            logger.info("STEP 2 : send json to ICPS Success : "+returnValue);
            return returnValue;
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("STEP 2 : send json to ICPS Fail");
            return "";
        }
    }
    
    @Async
    @Override
    public void receiveDeleteStoreInit(StoreDeletedPackageVO voStoreDeletedPackage) {        
        HpayLogVO voHpayLog = hpayLogService.init(propertyService.getString("icps.delstore2.interfacecode"), HpayLogService.typeSrc, "ICPS", "receiveDeleteStoreInit");
        logger.info("method : hpayDelStoreInitSend : start");
        
        voStoreDeletedPackage.setFormat_version("V1.0");
        String deltaVersion="";
        try {
            deltaVersion = storeDeletedMDAO.selectRelatedDeltaVersion(voStoreDeletedPackage.getReqDeleteStoreDate());
            //deltaVersion="KR_CARPAY_20190906_01_00000000_00";
        } catch (Exception e) {
            e.printStackTrace();
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("method : hpayDelStoreInitSend : Fail");
            return ;
        }
        voStoreDeletedPackage.setData_version_delta(deltaVersion);
        logger.info("1 : found related delta version : "+deltaVersion);
                
        String workSeq=getWorkSeq(voStoreDeletedPackage.getInterface_code(), voStoreDeletedPackage.getWork_date());
        voStoreDeletedPackage.setWork_date_seq(workSeq);
        logger.info("2 : generate data version : "+voStoreDeletedPackage.getWork_date()+"_"+workSeq);
        insertStoreDeleted(voStoreDeletedPackage);
        logger.info("3 : db insert delete list");
        try {
            for(int i=0; i<voStoreDeletedPackage.getArrVOStoreDelete().size(); i++) {
                storeDeletedMDAO.updateStoreDelta_DeletedDate(voStoreDeletedPackage.getWork_date(), voStoreDeletedPackage.getArrVOStoreDelete().get(i).getPoi_id(), voStoreDeletedPackage.getData_version_delta());
            }
        } catch (Exception e) {
            e.printStackTrace();
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("method : hpayDelStoreInitSend : Fail");

            return ;
        }
        logger.info("4 : db update delta");
        hpayLogService.setDone(voHpayLog, HpayLogService.statusDone);
        hpayLogService.update(voHpayLog);
        
        voHpayLog = hpayLogService.init(propertyService.getString("icps.delstore3.interfacecode"), "", "");
        if (parseRecentDelstore2To3(voStoreDeletedPackage)) {
            logger.info("5 : convert VO to ProvideDayDeleteList");
            insertStoreDeleted(voStoreDeletedPackage);            
            logger.info("6 : db insert delete list");
        }
        hpayLogService.setCount(voHpayLog, voStoreDeletedPackage.getArrVOStoreDelete().size(), voStoreDeletedPackage.getArrVOStoreDelete().size());

        //////ICPS로 전송
        //ONESUN 품확기간동안 막아놓자
        //String ReturnValue=sendToICPSLastDeletedBinary(voStoreDeletedPackage);
        String ReturnValue="";
        logger.info("ONESUN 품확기간동안 막아놓자=====reqProvideDayDeleteList.do==sendToICPSLastDeletedBinary");
        logger.info("7 : send Deleted Binary to ICPS : "+ReturnValue);
        
        if (ReturnValue.equals("")){
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("method : hpayDelStoreInitSend : Fail");
        }

        ObjectMapper mapper = new ObjectMapper(); 
        try {
            Map<String, String> map = mapper.readValue(ReturnValue, new TypeReference<Map<String, String>>(){});
            if (map.containsKey("resultCode") && map.get("resultCode").equals("0000")){
                hpayLogService.setDone(voHpayLog, HpayLogService.statusDone, map.get("resultCode"), map.get("resultMessage"));
                hpayLogService.update(voHpayLog);
            } else {
                hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, "", ReturnValue);
                hpayLogService.update(voHpayLog);                
            }
            logger.info("method : hpayDelStoreInitSend : Success");
        } catch (Exception e) {
            e.printStackTrace();
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, "", ReturnValue);
            hpayLogService.update(voHpayLog);                
            logger.info("method : hpayDelStoreInitSend : Fail");
        }
    }
        
    @Override
    public String sendDeletedStoreInfoCompareWithRecentDelta() {    
        logger.info("+++Del Store Step 1 Start");
        HpayLogVO voHpayLog=hpayLogService.init(propertyService.getString("icps.delstore1.interfacecode"), "endpoint", propertyService.getString("icps.delstore1.targetUri"), "sendToICPS");
        String returnStr_Fail="{\"interfaceCode\":\""+propertyService.getString("icps.delstore1.interfacecode")+"\", \"resultCode\":\"9999\", \"resultMessage\":\"실패\"}";

        String dataVersion_LastDelta;
        try {
            dataVersion_LastDelta = deltaMDAO.selectDataVersion_LastDelta();
            //dataVersion_LastDelta="KR_CARPAY_20190906_01_00000000_00";
            logger.info("1 : current delta version : "+dataVersion_LastDelta);
        } catch (Exception e) {
            logger.info("1 : current delta version : no data");
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store Step 1 Fail");
            return returnStr_Fail;
        }        

        String today= new SimpleDateFormat ( "yyyyMMdd").format(new Date());        
        String interfaceCode=propertyService.getString("icps.delstore1.interfacecode");
        String workSeq=getWorkSeq(interfaceCode, today);
        
        StoreDeletedPackageVO voStoreDeletePackage=new StoreDeletedPackageVO();
        voStoreDeletePackage.setInterface_code(interfaceCode);
        voStoreDeletePackage.setWork_date(today);
        voStoreDeletePackage.setWork_date_seq(workSeq);
        voStoreDeletePackage.setData_version_delta(dataVersion_LastDelta);
        
        
        List<StoreDeletedVO> arrVOStoreDelete=new ArrayList<StoreDeletedVO>();
        try {
            arrVOStoreDelete = storeDeletedMDAO.searchStoreDelete(dataVersion_LastDelta);
            logger.info("2 : delete count : "+arrVOStoreDelete.size());
        } catch (Exception e1) {
            e1.printStackTrace();
            arrVOStoreDelete=new ArrayList<StoreDeletedVO>();
        }
        
        //"KR_CARPAY_20190906_01_00000000_00"로 고정. 데이터 아래로 고정
        /*
        List<StoreDeletedVO> arrVOStoreDelete=new ArrayList<StoreDeletedVO>();
        StoreDeletedVO voStoreDeleted=new StoreDeletedVO();
        voStoreDeleted.setPoi_id("10636536");
        voStoreDeleted.setStore_id("889");
        arrVOStoreDelete.add(voStoreDeleted);
        */
        
        voStoreDeletePackage.setArrVOStoreDelete(arrVOStoreDelete);
        voHpayLog.setData_count_total(arrVOStoreDelete.size());
        voHpayLog.setData_count_success(arrVOStoreDelete.size());
        
        if (insertStoreDeleted(voStoreDeletePackage)){
            logger.info("4 : db insert success");            
        } else {
            logger.info("4 : db insert fail");
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store Step 1 Fail");
            return returnStr_Fail;
        }

        String json="";
        UtilityService utilityService=new UtilityServiceImpl();

        try {
            json = parseVOToJson(voStoreDeletePackage.getInterface_code(),  voStoreDeletePackage);
            logger.info("----------------------------------");
            logger.info(json);
            logger.info("----------------------------------");
            logger.info("5 : parse json success");            
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("5 : parse json fail");
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store Step 1 Fail");
            return returnStr_Fail;
        }
        if (json.equals("")){
            logger.info("5 : no json to return");
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store Step 1 Fail");
            return returnStr_Fail;
        }
        
        if (propertyService.getBoolean("icps.delstore1.isSaveJson")) {
            DateFormat format_dateout = new SimpleDateFormat("yyyyMMdd");
            Date now=new Date();
            String fileName=propertyService.getString("icps.delstore1.dump.prefix")+format_dateout.format(now)+"_"+now.getTime();
            try {
                utilityService.SaveFile(propertyService.getString("icps.delstore1.dump.path"), fileName , json);
                logger.info("6 : save json success : "+fileName);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("6 : save json fail : "+e.getMessage());
            }
        }
        
        String returnValue ="";
        try {
            returnValue = utilityService.sendJson(propertyService.getString("icps.delstore1.targetUri"), json);
            logger.info("7 : send to ICPS success : "+returnValue);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("7 : send to ICPS Fail : "+e.getMessage());
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store Step 1 Fail");
            return returnStr_Fail;
        }
        
        if (returnValue.equals("")){
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);
            logger.info("+++Del Store Step 1 Fail");
            return "{\"interfaceCode\":\""+propertyService.getString("icps.delta.interfacecode")+"\", \"resultCode\":\"400\", \"resultMessage\":\"실패\"}";                        
        }

        try {
            ObjectMapper mapper = new ObjectMapper(); 
            Map<String, String> map = mapper.readValue(returnValue, new TypeReference<Map<String, String>>(){});
            if (map.containsKey("resultCode") && map.get("resultCode").equals("0000")){
                hpayLogService.setDone(voHpayLog, HpayLogService.statusDone, map.get("resultCode"), map.get("resultMessage"));
                hpayLogService.update(voHpayLog);
                logger.info("+++Del Store Step 1 Success");
            } else {
                hpayLogService.setDone(voHpayLog, HpayLogService.statusFail, "", returnValue);
                hpayLogService.update(voHpayLog);                
                logger.info("+++Del Store Step 1 Fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return returnValue;
    }
    
    public String parseVOToJson(String jsonFormat,  StoreDeletedPackageVO voStoreDeletePackage) {        
        Map<String, Object> data=new LinkedHashMap<String, Object>();
        if (jsonFormat.equals("hpayDelStoreSend")){
            data.put("interfaceCode", voStoreDeletePackage.getInterface_code());
            data.put("reqDeleteStoreDate", voStoreDeletePackage.getWork_date());
            
            List<Map<String, Object>> inner=new ArrayList<Map<String, Object>>();
            for(int i=0; i<voStoreDeletePackage.getArrVOStoreDelete().size(); i++){
                Map<String, Object> inner_data=new LinkedHashMap<String, Object>();
                inner_data.put("poiId", voStoreDeletePackage.getArrVOStoreDelete().get(i).getPoi_id());
                inner_data.put("storeId", voStoreDeletePackage.getArrVOStoreDelete().get(i).getStore_id());
                inner.add(inner_data);
            }
            if (inner.size()>0){
                data.put("delStoreList", inner);                            
            } else {
                data.put("delStoreList", null);                            
            }
        } else if (jsonFormat.equals("hpayProvidDayDelSend")){            
            data.put("interfaceCode", voStoreDeletePackage.getInterface_code() );
            data.put("dataVersion", voStoreDeletePackage.getData_version_delta()+"_"+voStoreDeletePackage.getData_version());
            data.put("versionDate", voStoreDeletePackage.getWork_date() );
            data.put("initiateCode", voStoreDeletePackage.getInitiate_code() );
            data.put("deleteBinary",voStoreDeletePackage.getBinary_delete() );
        } else if (jsonFormat.equals("hpaySearchProvideDayDeleteList_no_delete")){
            data.put("interfaceCode", propertyService.getString("icps.delstore3json.interfacecode"));
            data.put("formatVersion", voStoreDeletePackage.getInterface_code() );
            data.put("dataVersion", voStoreDeletePackage.getData_version_delta());
            data.put("resultCode", "0002");
            data.put("resultMessage", "삭제데이터 없음");
            data.put("deletionCount", "0" );
            data.put("poiIdList",null);            
        } else if (jsonFormat.equals("hpaySearchProvideDayDeleteList_version_same")){
            data.put("interfaceCode", propertyService.getString("icps.delstore3json.interfacecode"));
            data.put("formatVersion", voStoreDeletePackage.getInterface_code() );
            data.put("dataVersion", voStoreDeletePackage.getData_version_delta()+"_"+voStoreDeletePackage.getData_version());
            data.put("resultCode", "0001");
            data.put("resultMessage", "버전동일");
            data.put("deletionCount", "0" );
            data.put("poiIdList",null);
        } else if (jsonFormat.equals("hpaySearchProvideDayDeleteList_version_different")){
            data.put("interfaceCode", propertyService.getString("icps.delstore3json.interfacecode"));
            data.put("formatVersion", voStoreDeletePackage.getInterface_code() );
            data.put("dataVersion", voStoreDeletePackage.getData_version_delta()+"_"+voStoreDeletePackage.getData_version());
            data.put("resultCode", "0000");
            data.put("resultMessage", "성공");
            data.put("deletionCount",voStoreDeletePackage.getArrVOStoreDelete().size()+"");
            List<String> arrPoi=new LinkedList<String>();
            for(int i=0; i<voStoreDeletePackage.getArrVOStoreDelete().size(); i++){
                arrPoi.add(voStoreDeletePackage.getArrVOStoreDelete().get(i).getPoi_id());
            }
            data.put("poiIdList",arrPoi.toArray());
        } else if (jsonFormat.equals("forBinary")){
            data.put("FormatVersion", voStoreDeletePackage.getFormat_version());
            data.put("DataVersion", voStoreDeletePackage.getData_version_delta()+"_"+voStoreDeletePackage.getWork_date()+"_"+voStoreDeletePackage.getWork_date_seq());
            data.put("DeletionCount", voStoreDeletePackage.getArrVOStoreDelete().size());
            int[] arrPoi=new int[voStoreDeletePackage.getArrVOStoreDelete().size()];
            for(int i=0; i<voStoreDeletePackage.getArrVOStoreDelete().size(); i++){
                arrPoi[i]=Integer.parseInt(voStoreDeletePackage.getArrVOStoreDelete().get(i).getPoi_id());
            }
            data.put("PoiIdList", arrPoi);            
        } 
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonList="";
        try {
            jsonList = mapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonList;
    }
    private byte[] parseVOToBinary(StoreDeletedPackageVO voStoreDeletePackage){
        String FormatVersion=voStoreDeletePackage.getFormat_version();
        String DataVersion=voStoreDeletePackage.getData_version_delta()+"_"+voStoreDeletePackage.getWork_date()+"_"+voStoreDeletePackage.getWork_date_seq();
        int DeletionCount=voStoreDeletePackage.getArrVOStoreDelete().size();
        int[] arrPoi=new int[voStoreDeletePackage.getArrVOStoreDelete().size()];
        for(int i=0; i<voStoreDeletePackage.getArrVOStoreDelete().size(); i++){
            arrPoi[i]=Integer.parseInt(voStoreDeletePackage.getArrVOStoreDelete().get(i).getPoi_id());
        }

        byte[] resultByte=new byte[30+60+4+(4*DeletionCount)];
        System.arraycopy(FormatVersion.getBytes(), 0, resultByte, 0, FormatVersion.getBytes().length);
        System.arraycopy(DataVersion.getBytes(), 0, resultByte, 30, DataVersion.getBytes().length);
        System.arraycopy(intToByteArray_littleEndian(DeletionCount), 0, resultByte, 90, 4);
        for(int i=0; i<DeletionCount; i++){
            System.arraycopy(intToByteArray_littleEndian(arrPoi[i]), 0, resultByte, 94+(i*4), 4);            
        }
        return resultByte;
    }
    /*
    private byte[] intToByteArray(int value) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte)(value >> 24);
        byteArray[1] = (byte)(value >> 16);
        byteArray[2] = (byte)(value >> 8);
        byteArray[3] = (byte)(value);
        return byteArray;
    }
    */
    private byte[] intToByteArray_littleEndian(int value) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte)(value);
        byteArray[1] = (byte)(value >> 8);
        byteArray[2] = (byte)(value >> 16);
        byteArray[3] = (byte)(value >> 24);
        return byteArray;
    }

}
