package com.hpay.parking.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import able.com.service.HService;
import able.com.service.prop.PropertyService;
import able.com.util.fmt.StringUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.parking.service.ParkListCollectService;
import com.hpay.parking.service.ParkingUtilService;
import com.hpay.parking.service.dao.ParkListMDAO;
import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkEchargeInfoVO;
import com.hpay.parking.vo.ParkInfoReqVO;
import com.hpay.parking.vo.ParkInfoVO;
import com.hpay.parking.vo.ProvideVersionDetailVO;
import com.hpay.parking.vo.ProvideVersionVO;

/**
 *
 * @ClassName   : ParkListCollectServiceImpl.java
 * @Description : 주차장 Static 정보 수집 서비스 구현 클래스
 * @author ONESUN
 * @since 2019. 4. 24.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 7. 2.     ONESUN         최초 생성
 * </pre>
 */
@Service("parkListCollectService")
public class ParkListCollectServiceImpl extends HService implements ParkListCollectService {
	
    @Resource(name = "parkListDAO")
    private ParkListMDAO parkListDAO;
    
    @Autowired
    PropertyService propertyService; 
        
    @Resource(name = "parkingUtilService")
    ParkingUtilService parkingUtilService; 
        
    
    /**
     * CP호출하고 JSON으로 변환
     * @param chvo
     * @return
     * @throws Exception
     * @throws IOException
     */
    @Override
    public JSONObject reqCollectSaveFile(CollectHistoryVO chvo) throws Exception ,IOException{

        String fileDownPath = propertyService.getString("parking.parklist.download.path"); 
        String strUrl = propertyService.getString("parking.cp.parkingcloud.url.parkList");

        String workDate = chvo.getWorkDate();
        String workDateSeq = chvo.getWorkDateSeq();

        String fileName = fileDownPath+"PARKLIST_"+workDate+"_"+workDateSeq+".json";   
        List<String> listUrl = parkingUtilService.addressAliveCheck(strUrl);
        JSONObject jsonObject = null;
        for(int i=0; i< listUrl.size(); i++){
            strUrl = listUrl.get(i);
            jsonObject = parkingUtilService.connectParkingCloud(strUrl, fileName);
            if(jsonObject != null) { break; }
        }
 
        return jsonObject;
    }
   
    
    /**
     * 전체 주차장 파일 읽기
     */
    public JSONObject readFileParkList(String fileName) throws Exception {
        
        if(StringUtil.isEmpty(fileName)){
            throw new Exception();
        }
        
        JSONParser parser = new JSONParser(); 
        JSONObject jsonData = null;
        String inData = "";
        BufferedReader in = null;
        try { 
            //FileReader 16M 파일이 안 읽혀짐
            //jsonData = (JSONObject) parser.parse(new FileReader(fileName)); 
            
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF8"));
            StringBuffer sb = new StringBuffer();            
            while ((inData = in.readLine()) != null) {
                sb.append(inData+"\n");
                            
            }
            jsonData = (JSONObject) parser.parse(sb.toString());
            in.close();     
            
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
        
        return jsonData;
        
        
    }    
    
    /**
     * 디비에 저장
     * @param jsonarray
     * @param keyVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public boolean insertDataToDB(JSONArray jsonarray,ParkInfoVO keyVO) throws Exception , PSQLException  {

        //JACKSON 사용 JSONObject로 자동전환 
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);    //park_echarge_list가  ""으로 와도 변환
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
//        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
         
        ParkInfoReqVO vo = null;
        //주차장 목록 저장
        for(int i=0; i<jsonarray.size(); i++){ 

            try{
                JSONObject jsonObject = (JSONObject) jsonarray.get(i);  
                vo = objectMapper.readValue(jsonObject.toJSONString(), ParkInfoReqVO.class);
                
                //1. 주차장 정보 저장
                //parkListDAO.insertOrUpdateParkList(vo); //[사용안함]이력없이저장 -> insertParkList변경
                vo.setWorkDate(keyVO.getWorkDate());
                vo.setWorkDateSeq(keyVO.getWorkDateSeq());
                vo.setSrc(keyVO.getSrc());  //CP src
                
                if(validationParkInfoVO(vo)) {
                    throw new Exception();
                    //continue;   //해당 ROW 저장하지 않음
                }
                
                //20191010 : null일경우 "-"로 변경
                vo = checkNull(vo); 
                                
                parkListDAO.insertParkList(vo);
    
                //2. 전기충전소 저장
                /*List<ParkEchargeListVO> voSub =  vo.getPark_echarge_list();
                
                if(voSub != null) {
                    for(int j=0; j<voSub.size(); j++){
                        ParkEchargeListVO echarge = voSub.get(j);
                        
                        ParkEchargeInfoVO  einfo = new ParkEchargeInfoVO();
        
                        einfo.setWorkDate(vo.getWorkDate());
                        einfo.setWorkDateSeq(vo.getWorkDateSeq());
                        einfo.setSrc(vo.getSrc());
                        einfo.setPark_seq(vo.getPark_seq());
                        einfo.setPark_category(vo.getPark_category());
                        einfo.setPark_echarge_cd(echarge.getPark_echarge_cd());
        
                        if(echarge.getPark_echarge_cd() != null &&  !"".equals(echarge.getPark_echarge_cd())) {
                            parkListDAO.insertParkEchargeList(einfo);   
                        }
                    }
                }*/
                

                JSONArray echargeArr =  (JSONArray) vo.getPark_echarge_list();
                                    
                if(echargeArr != null) {
                    for(int j=0; j<echargeArr.size(); j++){ 
                    
                        //JSONObject jsonObjectSub = (JSONObject) echargeArr.get(j);
                        //ParkEchargeInfoVO echarge = objectMapper.readValue(jsonObjectSub.toJSONString(), ParkEchargeInfoVO.class);
                        //--> JSONObject로 받으면 에러남
                        ParkEchargeInfoVO echarge = objectMapper.convertValue(echargeArr.get(j), ParkEchargeInfoVO.class);
                        
                        ParkEchargeInfoVO  einfo = new ParkEchargeInfoVO();
                        
                        einfo.setWorkDate(vo.getWorkDate());
                        einfo.setWorkDateSeq(vo.getWorkDateSeq());
                        einfo.setSrc(vo.getSrc());
                        einfo.setPark_seq(vo.getPark_seq());
                        einfo.setPark_category(vo.getPark_category());
                        einfo.setPark_echarge_cd(echarge.getPark_echarge_cd());
        
                        if(echarge.getPark_echarge_cd() != null &&  !"".equals(echarge.getPark_echarge_cd())) {
                            parkListDAO.insertParkEchargeList(einfo);
                        }
                        
                    }
                }
                   
                
            }catch(Exception e){
                logger.info("===PARK LIST INSERT EXCEPTION:"+e.getMessage()+"==="+vo.toString());
                continue;
            }
        }
        
        return true;

    }
    
    public boolean validationParkInfoVO(ParkInfoReqVO vo) throws Exception {

        if(StringUtil.isEmpty(vo.getPark_category())) {
            logger.info("=== PARKLIST Not Save because park_category is empty === park_seq:"+vo.getPark_seq());
            return true;
        }
        if(!StringUtil.isEmpty(vo.getPark_phone_1()) && vo.getPark_phone_1().length()>4) {
            logger.info("=== PARKLIST  Not Save because park_phone_1 over length 4 === park_seq:"+vo.getPark_seq());
            return true;
        }
        if(!StringUtil.isEmpty(vo.getPark_phone_2()) && vo.getPark_phone_2().length()>20) {
            logger.info("=== PARKLIST Not Save because park_phone_2 over length 20 === park_seq:"+vo.getPark_seq());
            return true;
        }
        if(!StringUtil.isEmpty(vo.getPark_phone_3()) && vo.getPark_phone_3().length()>20) {
            logger.info("===  PARKLIST Not Save because park_phone_3 over length 20 === park_seq:"+vo.getPark_seq());
            return true;
        }
        if(!StringUtil.isEmpty(vo.getPark_zipcode()) && vo.getPark_zipcode().length()>10) {
            logger.info("=== PARKLIST Not Save because park_zipcode over length 10 === park_seq:"+vo.getPark_seq());
            return true;
        }

        return false;       
    }
    
    
    /**
     * 운행시간 NULL일 경우 '-'로 입력
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public ParkInfoReqVO checkNull(ParkInfoReqVO vo) throws Exception {

        if(StringUtil.isEmpty(vo.getPark_biz_time())) {vo.setPark_biz_time("-");}
        if(StringUtil.isEmpty(vo.getPark_sat_biz_time())) {vo.setPark_sat_biz_time("-");}
        if(StringUtil.isEmpty(vo.getPark_sun_hol_biz_time())) {vo.setPark_sun_hol_biz_time("-");}
        
        return vo;       
    }
    
    /**
     * 디비에 저장 ForEach 사용
     * @param jsonarray
     * @param keyVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public boolean insertDataToDBForEach(JSONArray jsonarray,ParkInfoReqVO keyVO) throws Exception , PSQLException  {

        logger.debug("========insertDataToDBForEach");
        //JACKSON 사용 JSONObject로 자동전환 
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);    //park_echarge_list가  ""으로 와도 변환
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);          //echarge_list:{} 이렇게 와도 변환.[]없이
//        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
//        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
         

        try{
        List<ParkInfoReqVO> tmpList = new ArrayList<ParkInfoReqVO>(); 
        List<ParkEchargeInfoVO> tmpEchargeList = new ArrayList<ParkEchargeInfoVO>();
        int jsonarraySize = jsonarray.size();
        logger.debug("========jsonarray.size():"+jsonarraySize);
            //주차장 목록 저장
            for(int i=0; i<jsonarraySize; i++){ 

                
                    JSONObject jsonObject = (JSONObject) jsonarray.get(i);  
                    ParkInfoReqVO vo = objectMapper.readValue(jsonObject.toJSONString(), ParkInfoReqVO.class);
                    
                    //1. 주차장 정보 저장
                    //parkListDAO.insertOrUpdateParkList(vo); //[사용안함]이력없이저장 -> insertParkList변경
                    vo.setWorkDate(keyVO.getWorkDate());
                    vo.setWorkDateSeq(keyVO.getWorkDateSeq());
                    vo.setSrc(keyVO.getSrc());  //CP src
                    
                    
                    if(validationParkInfoVO(vo)) {
                        //throw new Exception();
                        continue;   //해당 ROW 저장하지 않음
                    }
                                    
                    //20191010 : null일경우 "-"로 변경
                    vo = checkNull(vo); 
                    
                    //parkListDAO.insertParkList(vo);
                    tmpList.add(vo);
                    
        
                    //2. 전기충전소 저장
                    //List<ParkEchargeListVO> voSub =  vo.getPark_echarge_list();
/*                    if(voSub != null && voSub.size()>0) {

                        logger.info("=========voSub.size():"+voSub.size());
                        
                        for(int j=0; j<voSub.size(); j++){
                            ParkEchargeListVO echarge = voSub.get(j);
                            
                            ParkEchargeInfoVO  einfo = new ParkEchargeInfoVO();
            
                            einfo.setWorkDate(vo.getWorkDate());
                            einfo.setWorkDateSeq(vo.getWorkDateSeq());
                            einfo.setSrc(vo.getSrc());
                            einfo.setPark_seq(vo.getPark_seq());
                            einfo.setPark_category(vo.getPark_category());
                            einfo.setPark_echarge_cd(echarge.getPark_echarge_cd());
            
                            if(echarge.getPark_echarge_cd() != null &&  !"".equals(echarge.getPark_echarge_cd())) {
                                //parkListDAO.insertParkEchargeList(einfo);
                                tmpEchargeList.add(einfo);
                            }
                        }
                    }
                    */

                    JSONArray echargeArr =  (JSONArray) vo.getPark_echarge_list();
                    
                    
                                        
                    if(echargeArr != null) {
                        for(int j=0; j<echargeArr.size(); j++){ 
                        
                            //JSONObject jsonObjectSub = (JSONObject) echargeArr.get(j);
                            //ParkEchargeInfoVO echarge = objectMapper.readValue(jsonObjectSub.toJSONString(), ParkEchargeInfoVO.class);
                            //--> JSONObject로 받으면 에러남
                            ParkEchargeInfoVO echarge = objectMapper.convertValue(echargeArr.get(j), ParkEchargeInfoVO.class);
                            
                            ParkEchargeInfoVO  einfo = new ParkEchargeInfoVO();
                            
                            einfo.setWorkDate(vo.getWorkDate());
                            einfo.setWorkDateSeq(vo.getWorkDateSeq());
                            einfo.setSrc(vo.getSrc());
                            einfo.setPark_seq(vo.getPark_seq());
                            einfo.setPark_category(vo.getPark_category());
                            einfo.setPark_echarge_cd(echarge.getPark_echarge_cd());
            
                            if(echarge.getPark_echarge_cd() != null &&  !"".equals(echarge.getPark_echarge_cd())) {
                                //parkListDAO.insertParkEchargeList(einfo);
                                tmpEchargeList.add(einfo);
                            }
                            
                        }
                    }
                        
                   
                   //300건 씩 insert
                   if(i > 0 && (i%300 == 0)) {

                       if(tmpList.size() > 0) {
                           parkListDAO.insertParkListForEach(tmpList);
                       }
                       if(tmpEchargeList.size() > 0) {
                           parkListDAO.insertParkEchargeListForEach(tmpEchargeList);
                       }
                       
                       logger.debug("22222222==clear i="+i);
                       tmpList.clear();
                       tmpEchargeList.clear();
                       
//                       tmpList = new ArrayList<ParkInfoVO>(); 
//                       tmpEchargeList = new ArrayList<ParkEchargeInfoVO>();                   
                   }
            }
            //나머지 저장
            if(tmpList.size() > 0) {
                parkListDAO.insertParkListForEach(tmpList);
            }
            if(tmpEchargeList.size() > 0) {
                parkListDAO.insertParkEchargeListForEach(tmpEchargeList);
            }
        
        }catch(Exception e){
            logger.info("=== Excepion: "+e.getMessage());
            e.printStackTrace();
        }                    
    
       
        return true;

    }

    
    /**
     * seq 가져오기
     * @param chvo
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public CollectHistoryVO selectWorkDateSeqParkList(CollectHistoryVO chvo) throws Exception ,PSQLException {
        CollectHistoryVO collectHistoryVO = parkListDAO.selectWorkDateSeqParkList(chvo);
        return collectHistoryVO;
    }
    
    /**
     * 적재 히스토리 저장
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void insertCollectHistory(CollectHistoryVO chvo) throws Exception {
        
        parkListDAO.insertParkListHistory(chvo);   

    }

    /**
     * 적재 히스토리 저장
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void updateCollectHistory(CollectHistoryVO chvo) throws Exception {
        parkListDAO.updateParkListHistory(chvo);         
    }
  
    
    /**
     * 일정기간 이전 데이터 delete
     * @param refDate
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void deleteParkListNotUse(String refDate) throws Exception ,PSQLException{
        
        parkListDAO.deleteParkListNotUse(refDate);   

    }

    /**
     * 제공버전 저장
     * @param parkVersionVO
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void insertParkListProvideVersion(ProvideVersionVO parkVersionVO) throws Exception, PSQLException {
        parkListDAO.insertParkListProvideVersion(parkVersionVO);
    }

    /**
     * 제공버전 상세 저장
     * @param parkVersionDetailVO
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void insertParkListProvideVersionDetail(ProvideVersionDetailVO parkVersionDetailVO) throws Exception, PSQLException {
        parkListDAO.insertParkListProvideVersionDetail(parkVersionDetailVO);  
        
    }

    /**
     * 제공버전+1 
     * @param provideService
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public ProvideVersionVO selectParkListProvideVersionUp(String provideService) throws Exception, PSQLException {
        ProvideVersionVO provideVersionVO = parkListDAO.selectParkListProvideVersionUp(provideService);
        return provideVersionVO;
    }

    /**
     * tmp 디비에 저장 ForEach (park_list_tmp 테이블에 키값없이 저장)
     * @param jsonarray
     * @param keyVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public boolean insertDataToDBForEachTmp(JSONArray jsonarray,ParkInfoReqVO keyVO) throws Exception , PSQLException  {

        logger.debug("========insertDataToDBForEach");
        //JACKSON 사용 JSONObject로 자동전환 
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);    //park_echarge_list가  ""으로 와도 변환
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);          //echarge_list:{} 이렇게 와도 변환.[]없이
//        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
//        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
         

        try{
        List<ParkInfoReqVO> tmpList = new ArrayList<ParkInfoReqVO>(); 
        //List<ParkEchargeInfoVO> tmpEchargeList = new ArrayList<ParkEchargeInfoVO>();
        int jsonarraySize = jsonarray.size();
        logger.debug("========jsonarray.size():"+jsonarraySize);
            //주차장 목록 저장
            for(int i=0; i<jsonarraySize; i++){ 

                
                    JSONObject jsonObject = (JSONObject) jsonarray.get(i);  
                    ParkInfoReqVO vo = objectMapper.readValue(jsonObject.toJSONString(), ParkInfoReqVO.class);
                    
                    //1. 주차장 정보 저장
                    vo.setWorkDate(keyVO.getWorkDate());
                    vo.setWorkDateSeq(keyVO.getWorkDateSeq());
                    vo.setSrc(keyVO.getSrc());  //CP src
                    
                    
                    if(validationParkInfoVO(vo)) {
                        //throw new Exception();
                        continue;   //해당 ROW 저장하지 않음
                    }
                    
                    //20191010 : null일경우 "-"로 변경
                    vo = checkNull(vo); 
                    
                    //parkListDAO.insertParkList(vo);
                    tmpList.add(vo);
                    
                    //TMP는 park_list만 저장
                    /*
                    JSONArray echargeArr =  (JSONArray) vo.getPark_echarge_list();                    
                    if(echargeArr != null) {
                        for(int j=0; j<echargeArr.size(); j++){ 
                            ParkEchargeInfoVO echarge = objectMapper.convertValue(echargeArr.get(j), ParkEchargeInfoVO.class);
                            
                            ParkEchargeInfoVO  einfo = new ParkEchargeInfoVO();
                            
                            einfo.setWorkDate(vo.getWorkDate());
                            einfo.setWorkDateSeq(vo.getWorkDateSeq());
                            einfo.setSrc(vo.getSrc());
                            einfo.setPark_seq(vo.getPark_seq());
                            einfo.setPark_category(vo.getPark_category());
                            einfo.setPark_echarge_cd(echarge.getPark_echarge_cd());
            
                            if(echarge.getPark_echarge_cd() != null &&  !"".equals(echarge.getPark_echarge_cd())) {
                                //parkListDAO.insertParkEchargeList(einfo);
                                tmpEchargeList.add(einfo);
                            }
                            
                        }
                    }
                    */  
                   
                   //300건 씩 insert
                   if(i > 0 && (i%300 == 0)) {

                       if(tmpList.size() > 0) {
                           parkListDAO.insertParkListForEachTmp(tmpList);
                       }
                    
                       /*
                       if(tmpEchargeList.size() > 0) {
                           parkListDAO.insertParkEchargeListForEach(tmpEchargeList);
                       }
                       */
                       logger.debug("22222222==clear i="+i);
                       tmpList.clear();
                       //tmpEchargeList.clear();
                                          
                   }
            }
            //나머지 저장
            if(tmpList.size() > 0) {
                parkListDAO.insertParkListForEachTmp(tmpList);
            }
            /*
            if(tmpEchargeList.size() > 0) {
                parkListDAO.insertParkEchargeListForEach(tmpEchargeList);
            }
            */ 
        }catch(Exception e){
            logger.info("=== Excepion: "+e.getMessage());
            e.printStackTrace();
        }                    
       
        return true;

    }

    /**
     * 주차장 건수 추출
     * @param collectHistoryVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public int selectParkListCount(CollectHistoryVO collectHistoryVO) throws Exception, PSQLException {
        int parkSeqCnt = parkListDAO.selectParkListCount(collectHistoryVO);
        return parkSeqCnt;
    }
    
}
