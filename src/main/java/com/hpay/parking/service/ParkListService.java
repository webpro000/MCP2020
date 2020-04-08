package com.hpay.parking.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;

import com.hpay.parking.vo.ParkEchargeListVO;
import com.hpay.parking.vo.ParkInfoVO;
import com.hpay.parking.vo.ParkListReqVO;
import com.hpay.parking.vo.ProvideVersionVO;
import com.hpay.parking.vo.ResultParkInfoVO;



/**
 * @ClassName   : ParkListService.java
 * @Description : 주차장 정보 제공을 위한 서비스 선언 클래스
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
public interface ParkListService {
    /**
     * 전체 주차장 목록 파일 읽어서 JSON으로 변환
     * @param FileName
     * @return
     * @throws Exception
     */
    public JSONObject readFileParkList(String FileName) throws Exception;
    /**
     * 주차장 목록(전체)
     * @param versionInfoVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public List<ParkInfoVO> selectParkList(ProvideVersionVO versionInfoVO) throws Exception,PSQLException;
    /**
     * 주차장 목록(공항주차장 전체) 20191205추가
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    List<ParkInfoVO> selectParkListAirport() throws Exception, PSQLException;
    /**
     * 주차장 목록(차분)
     * @param reqVersionInfoVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public List<ParkInfoVO> selectParkListChangeData(ProvideVersionVO reqVersionInfoVO) throws Exception,PSQLException;
    /**
     * 주차장 목록(차분) Redis
     * @param reqVersionInfoVO
     * @param diffDays
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public JSONObject getParkListChangeData(ProvideVersionVO reqVersionInfoVO, long diffDays) throws Exception,PSQLException;    
    /**
     * 전기충전소 리스트
     * @param vo
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public List<ParkEchargeListVO> selectParkEchargeList(ParkInfoVO vo) throws Exception,PSQLException;
    /**
     * 제공버전 확인
     * @param provideService
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public ProvideVersionVO selectParkListProvideVersion(String provideService) throws Exception,PSQLException;
    /**
     * 요청버전과 제공버전 체크
     * @param pvVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public ProvideVersionVO checkProvideVersion(ProvideVersionVO pvVO) throws Exception, PSQLException;    
    /**
     * 차분 저장
     * @param reqProvideVersionVO
     * @param resultParkInfoVO
     * @param diffDay
     * @throws Exception
     */
    void makeJsonDataDifference(ProvideVersionVO reqProvideVersionVO, ResultParkInfoVO resultParkInfoVO, int diffDay) throws Exception;
    /**
     * 차분 Redis저장
     * @param key
     * @param jsonObject
     * @throws Exception
     */
    void setDifferenceToRedis(String key, String jsonObject) throws Exception;
    /**
     * 이전일자 13일 버전 리스트
     * @param provideService
     * @param referenceDate
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public List<ProvideVersionVO> selectProvideVersionList(String provideService , int referenceDate) throws Exception, PSQLException ;
    /**
     * 최종 버전 추출
     * @param pvVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public ProvideVersionVO selectLastVersionInfo(ProvideVersionVO pvVO) throws Exception, PSQLException;
    /**
     * 제공 ParkList 생성
     * @param reqVO
     * @param req
     * @param versionInfoVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public ResultParkInfoVO makeResultParkList(ParkListReqVO reqVO,HttpServletRequest req, ProvideVersionVO versionInfoVO ) throws Exception, PSQLException;
    /**
     * 일자를 계산해서 차분 타입 계산
     * @param checkVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> caluateDifferenceType(ProvideVersionVO checkVO ) throws Exception;
    /**
     * Redis 삭제
     * @throws Exception
     */
    void delRedisParkingStaticDifference() throws Exception;
   
}

