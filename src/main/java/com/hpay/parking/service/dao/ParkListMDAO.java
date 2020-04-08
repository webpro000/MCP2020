package com.hpay.parking.service.dao;

import java.util.List;
import java.util.Map;

import able.com.mybatis.Mapper;

import org.postgresql.util.PSQLException;

import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkEchargeInfoVO;
import com.hpay.parking.vo.ParkEchargeListVO;
import com.hpay.parking.vo.ParkInfoReqVO;
import com.hpay.parking.vo.ParkInfoVO;
import com.hpay.parking.vo.ProvideVersionDetailVO;
import com.hpay.parking.vo.ProvideVersionVO;

/**
 *
 * @ClassName   : ParkListDAO.java
 * @Description : 주차장 정보 데이터 객체 클래스
 * @author ONESUN
 * @since 2019. 4. 24.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 4. 24.     ONESUN     	최초 생성
 * </pre>
 */
@Mapper("parkListDAO")
public interface ParkListMDAO {
	
	/**
     * Park List insert or Update : 5/7 사용안함으로 변경  : insertParkList 대신 사용
     */
    public void insertOrUpdateParkList(ParkInfoVO vo) throws Exception, PSQLException ; 
    /**
     * Park List insert or Update
     */
    public void insertParkList(ParkInfoReqVO vo) throws Exception ,PSQLException;    
    /**
     * Park List insert key (work_date, work_date_seq)
     */
    public CollectHistoryVO selectWorkDateSeqParkList(CollectHistoryVO chvo) throws Exception,PSQLException;    
    /**
     * Echarge List insert
     */
    public void insertParkEchargeList(ParkEchargeInfoVO vo) throws Exception, PSQLException ;    
    /**
     * Park List Collect History
     */
    public void insertParkListHistory(CollectHistoryVO vo) throws Exception, PSQLException ;    
    /**
     * Park List Collect History
     */
    public void updateParkListHistory(CollectHistoryVO vo) throws Exception, PSQLException ;    
     /**
      * 14일전 데이터 delete
     * @param refDate 
      */
     public void deleteParkListNotUse(String refDate) throws Exception, PSQLException ;    
     /**
     * Park List 
     * @param versionInfoVO 
     */
    public  List<ParkInfoVO> selectParkList(ProvideVersionVO versionInfoVO) throws Exception, PSQLException ;
    /**
     * Park List Airport
     * @param versionInfoVO 
     */
    public  List<ParkInfoVO> selectParkListAirport() throws Exception, PSQLException ;    
    /**
     * Park Echarge List 
     */
    public  List<ParkEchargeListVO> selectParkEchargeList(ParkInfoVO vo) throws Exception, PSQLException;
    /**
     * Park Echarge List 
     */
    public  ProvideVersionVO selectParkListProvideVersion(String provideService) throws Exception, PSQLException;
    /**
     * 제공버전 저장
     */
    public void insertParkListProvideVersion(ProvideVersionVO parkVersionVO) throws Exception, PSQLException;
    /**
     * 제공버전에 대한 리스트 상세 버전 저장
     */
    public void insertParkListProvideVersionDetail(ProvideVersionDetailVO parkVersionDetailVO) throws Exception, PSQLException;
    /**
     * Statements
     * @param provideService 
     *
     * @return
     */
    public ProvideVersionVO selectParkListProvideVersionUp(String provideService) throws Exception, PSQLException;
    /**
     * ParkList Insert Using ForEach 
     *
     * @param tmpList
     */
    public void insertParkListForEach(List<ParkInfoReqVO> tmpList);
    /**
     * EchargeList Insert Using ForEach 
     *
     * @param tmpEchargeList
     */
    public void insertParkEchargeListForEach(List<ParkEchargeInfoVO> tmpEchargeList);  
    /**
     * Park List 차분
     * @param versionInfoVO 
     */
    public  List<ParkInfoVO> selectParkListChangeData(ProvideVersionVO versionInfoVO) throws Exception, PSQLException ;
    /**
     * 요청버전과 제공버전체크
     *
     * @param reqVersionInfo
     * @return
     */
    public ProvideVersionVO checkProvideVersion(ProvideVersionVO pvVO) throws Exception, PSQLException ;
    /**
     * 제공버전 목록
     */
    public List<ProvideVersionVO> selectProvideVersionList(Map<Object, Object> param) throws Exception, PSQLException ;
    /**
     * 제공 최종버전 추출
     */
    public ProvideVersionVO selectLastVersionInfo(ProvideVersionVO pvVO) throws Exception, PSQLException ;
    /**
     * [TMP]ParkList Insert Using ForEach
     */
    public void insertParkListForEachTmp(List<ParkInfoReqVO> tmpList);
    /**
     * 주차장 리스트 Count
     */
    public int selectParkListCount(CollectHistoryVO vo) throws Exception, PSQLException ;


    
}

