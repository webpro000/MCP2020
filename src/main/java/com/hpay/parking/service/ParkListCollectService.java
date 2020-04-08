package com.hpay.parking.service;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;

import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkInfoReqVO;
import com.hpay.parking.vo.ParkInfoVO;
import com.hpay.parking.vo.ProvideVersionDetailVO;
import com.hpay.parking.vo.ProvideVersionVO;


/**
 *
 * @ClassName   : ParkListCollectService.java
 * @Description : 주차장 Static 수집을 위한 서비스 선언 클래스
 * @author o1488
 * @since 2019. 12. 5.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 12. 5.     o1488     	최초 생성
 * </pre>
 */
public interface ParkListCollectService {
    /**
     * seq 가져오기
     * @param chvo
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public CollectHistoryVO selectWorkDateSeqParkList(CollectHistoryVO chvo) throws Exception,PSQLException;        
    /**
     * 적재 히스토리 저장
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    public void insertCollectHistory(CollectHistoryVO chvo) throws Exception,PSQLException;    
    /**
     * CP호출하고 JSON으로 변환
     * @param chvo
     * @return
     * @throws Exception
     * @throws IOException
     */
    public JSONObject reqCollectSaveFile(CollectHistoryVO chvo)  throws Exception ,IOException;    
    /**
     * 적재 히스토리 저장
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    public void updateCollectHistory(CollectHistoryVO chvo) throws Exception,PSQLException;    
    /**
     * 디비에 저장
     * @param jsonarray
     * @param keyVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public boolean insertDataToDB(JSONArray jsonarray,ParkInfoVO keyVO) throws Exception, PSQLException;    
    /**
     * 디비에 저장 ForEach 사용
     * @param jsonarray
     * @param keyVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public boolean insertDataToDBForEach(JSONArray jsonarray,ParkInfoReqVO keyVO) throws Exception, PSQLException;
    /**
     * 제공버전+1 
     * @param provideService
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public ProvideVersionVO selectParkListProvideVersionUp(String provideService) throws Exception,PSQLException;    
    /**
     * 제공버전 저장
     * @param parkVersionVO
     * @throws Exception
     * @throws PSQLException
     */
    public void insertParkListProvideVersion(ProvideVersionVO parkVersionVO) throws Exception,PSQLException;    
    /**
     * 제공버전 상세 저장
     * @param parkVersionDetailVO
     * @throws Exception
     * @throws PSQLException
     */
    public void insertParkListProvideVersionDetail(ProvideVersionDetailVO parkVersionDetailVO) throws Exception,PSQLException;
    /**
     * 일정기간 이전 데이터 delete
     * @param refDate
     * @throws Exception
     * @throws PSQLException
     */
    public void deleteParkListNotUse(String refDate) throws Exception, PSQLException;
    /**
     * tmp 디비에 저장 ForEach (park_list_tmp 테이블에 키값없이 저장)
     * @param jsonarray
     * @param keyVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public boolean insertDataToDBForEachTmp(JSONArray jsonarray,ParkInfoReqVO keyVO) throws Exception, PSQLException;
    /**
     * 주차장 건수 추출
     * @param collectHistoryVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    int selectParkListCount(CollectHistoryVO collectHistoryVO) throws Exception, PSQLException;    
}

