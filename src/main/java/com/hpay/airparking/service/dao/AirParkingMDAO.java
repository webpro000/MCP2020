package com.hpay.airparking.service.dao;

import java.util.HashMap;
import java.util.List;

import able.com.mybatis.Mapper;

import org.postgresql.util.PSQLException;

import com.hpay.airparking.vo.AirCollectHistoryVO;
import com.hpay.airparking.vo.AirPortParkVO;
import com.hpay.parking.vo.ParkingLotVO;
import com.hpay.parking.vo.PoiIdVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : AirParkingMDAO.java
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
@Mapper("AirParkingMDAO")
public interface AirParkingMDAO {
    
    
    /**
     * Park List insert key (work_date, work_date_seq)
     */
    public AirCollectHistoryVO selectWorkDateSeqParkListUseCount() throws Exception;
    /**
     * Park List(Use Count) Collect History
     */
    public void insertAllParkUseCountHistory(AirCollectHistoryVO vo) throws Exception;
    
    /**
     *  delete all
     */
    public void deleteAllParkUseCount_Air() throws Exception;
    
    
    /**
     *  공항주차장 디비저장 :공항주차장  Data DB 적재
     * 
     */
    void insertAirPort(HashMap<String, Object> vo) throws Exception;

    public int insertAirPort(AirPortParkVO vo) throws Exception;    
    
    
    //public List<AirPortParkVO>  insertAirPort(List<AirPortParkVO> voList) throws Exception, PSQLException;   
    
    /**
     * park_seq 로 조회
     */
    
    public String selectParkToalNum(String parkname) throws Exception;
    

    /**
     * Park List(Use Count) Collect History
     */
    public void updateAllParkUseCountHistory(AirCollectHistoryVO vo) throws Exception;

}

