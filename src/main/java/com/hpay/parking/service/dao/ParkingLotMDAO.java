package com.hpay.parking.service.dao;

import java.util.List;

import able.com.mybatis.Mapper;

import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkingLotVO;
import com.hpay.parking.vo.PoiIdVO;

/**
 *
 * @ClassName   : ParkingLotDAO.java
 * @Description : 주차장 Dynamic 재차정보 클래스
 * @author ONESUN
 * @since 2019. 4. 29.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 4. 29.     ONESUN     	최초 생성
 * </pre>
 */
@Mapper("parkingLotDAO")
public interface ParkingLotMDAO {

	
    /**
     * Park List(Use Count) insert or Update
     * @return 
     */
    public int insertParkingLotList(ParkingLotVO vo) throws Exception;    
    
    /**
     * Park List insert key (work_date, work_date_seq)
     */
    public CollectHistoryVO selectWorkDateSeq() throws Exception;
    
    /**
     * Park List(Use Count) Collect History
     */
    public void insertAllParkUseCountHistory(CollectHistoryVO vo) throws Exception;

    /**
     * Park List(Use Count) Collect History
     */
    public void updateAllParkUseCountHistory(CollectHistoryVO vo) throws Exception;
    
    /**
     * Park List insert key (work_date, work_date_seq)
     */
    public CollectHistoryVO selectWorkDateSeqParkListUseCount() throws Exception;
    
    /**
     * Park List 
     */
    public  List<ParkingLotVO> selectAllParkUseCount() throws Exception;
   
    /**
     * Park Info 
     */
    public  ParkingLotVO selectParkUseCount() throws Exception;
    
    /**
     *  delete all
     */
    public void deleteAllParkUseCount() throws Exception;

    /**
     * List요청일 경우 . 요청한 갯수만 리턴
     */
    public List<String> selectParkUseCountByPoiId(List<PoiIdVO> poiIdVOList) throws Exception;

    /**
     * park_seq 로 조회
     */
    public List<String> selectParkUseCountByParkSeq(List<PoiIdVO> poiIdVOList);
}

