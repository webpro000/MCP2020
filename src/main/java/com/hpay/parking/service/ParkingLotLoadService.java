package com.hpay.parking.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;

import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkingLotVO;



/**
 * <pre>
 * 주차장 Dynamic 적재
 * </pre>
 *
 * @ClassName   : ParkingLotLoadService.java
 * @Description : 주차장 Dynamic 적재를 위한 서비스 선언 클래스
 * @author o1488
 * @since 2019. 6. 25.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 6. 25.     o1488     	최초 생성 : 적재 서비스 분리
 * </pre>
 */
public interface ParkingLotLoadService {
    /**
     * CP호출하여 주차장 재차정보  Json으로 리턴
     * @param chvo
     * @return
     * @throws Exception
     */
    public JSONObject reqCollectToJson(CollectHistoryVO chvo) throws Exception;
    /**
     * CP호출하여 주차장 재차정보 VO 변환
     * @param jsonarray
     * @param src
     * @return
     * @throws Exception
     */
    public List<ParkingLotVO> jsonToVo(JSONArray jsonarray, String src) throws Exception;    
    /**
     * 주차장 재차정보  Redis에 저장
     * @param voList
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public int jsonToRedis(List<ParkingLotVO> voList) throws Exception, PSQLException;
    /**
     * 주차장 재차정보 디비에 저장
     * @param voList
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public List<ParkingLotVO>  insertJsonToDb(List<ParkingLotVO> voList) throws Exception, PSQLException;    
    /**
     * DB delete all 
     * @throws Exception
     * @throws PSQLException
     */
    public void deleteAllParkUseCount() throws Exception, PSQLException;    
    /**
     * 작업일 SEQ 가져오기
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public CollectHistoryVO selectWorkDateSeqParkListUseCount() throws Exception, PSQLException;
    /**
     * 적재 히스토리저장
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    public void insertAllParkUseCountHistory(CollectHistoryVO chvo) throws Exception, PSQLException;
    /**
     * 적재 히스토리 수정
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    public void updateAllParkUseCountHistory(CollectHistoryVO chvo) throws Exception, PSQLException;    
    
}

