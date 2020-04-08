package com.hpay.airparking.service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;

import com.hpay.airparking.vo.AirCollectHistoryVO;
import com.hpay.airparking.vo.AirPortParkVO;
import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkingLotVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ParkingAirLoadService.java
 * @Description : 공항주차장  적재
 * @author o1709
 * @since 2019. 12. 02.
 * @version 1.0
 * @see
 * @Modification Information
 * 
 * 
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 12. 02.     o1709         최초 생성
 * </pre>
 */

public interface ParkingAirLoadService {
    /**
     * 작업일 SEQ 가져오기
     */
    public AirCollectHistoryVO selectWorkDateSeqParkListUseCount() throws Exception, PSQLException;
    /**
     * 적재 히스토리저장
     */
    public void insertAllParkUseCountHistory(AirCollectHistoryVO chvo) throws Exception, PSQLException;
    
    /**
     * xml호출하여 인천 주차장 상태  vo에 저장(vo 변환)
     */
    public List<AirPortParkVO> IncheonxmlToVo(String src) throws Exception;  
    
    /**
     * xml호출하여 전체 주차장 상태  vo에 저장(vo 변환)
     */
    public List<AirPortParkVO> AirxmlToVo(String src ,  List<AirPortParkVO> tempList) throws Exception;  
    /**
     * DB delete all 
     */
    public void deleteAllParkUseCount_Air() throws Exception, PSQLException;   
    

    /**
     * 공항주차장 DB insert
     */
    public void  insertAirPort(List<AirPortParkVO> voList) throws Exception, PSQLException;  
    
    
    /**
     * CP호출하여 전체 주차장 상태  Redis에 저장
     */
    public int jsonToRedis(List<AirPortParkVO> voList) throws Exception, PSQLException;
    
    /**
     * 적재 히스토리 수정
     */
    public void updateAllParkUseCountHistory(AirCollectHistoryVO chvo) throws Exception, PSQLException;    
    

    /**
     * 공항주차장 재차정보  Json으로 리턴
     */
    
    public void reqCollectToJson(AirCollectHistoryVO chvo, List<AirPortParkVO> saveList) throws Exception, SocketTimeoutException;
    /**
     * Statements
     *
     * @param chvo
     * @param saveList
     * @throws Exception
     * @throws SocketTimeoutException
     */

}

