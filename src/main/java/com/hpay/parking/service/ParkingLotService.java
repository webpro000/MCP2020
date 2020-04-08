package com.hpay.parking.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;

import com.hpay.parking.vo.ParkingLotVO;
import com.hpay.parking.vo.PoiIdVO;

import redis.clients.jedis.Jedis;



/**
 * @ClassName   : ParkingLotService.java
 * @Description : 주차장 Dynamic 재차정보를 위한 서비스 선언 클래스
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
public interface ParkingLotService {    
    /**
     * 재차정보 데이터 파일을 읽음 (수동 처리용)
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public JSONObject readFileAllParkUseCount() throws Exception, PSQLException;
    /**
     * arr를 디비에 저장
     * @param src
     * @param arr
     * @throws Exception
     * @throws PSQLException
     */
    public void readFileAllParkUseCount2DB(String src, JSONArray arr) throws Exception , PSQLException;    
    /**
     * redis에 재차정보 저장
     *
     * @param src
     * @param listVo
     * @throws Exception
     * @throws PSQLException
     */
    public void redisSetParkingLotInfo(String src, List<ParkingLotVO> listVo) throws Exception, PSQLException;    
    /**
     * 전체 주차장 재차대수 리스트 Redis에서 가져오기
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public List<JSONObject> selectAllParkUseCountRedis() throws Exception, PSQLException;
    /**
     * 전체 주차장 재차대수 리스트 디비에서 가져오기
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public List<ParkingLotVO> selectAllParkUseCount() throws Exception, PSQLException;    
    /**
     * POI_ID List요청인 경우
     * @param poiIdVOList
     * @return
     * @throws Exception
     */
    public List<JSONObject> selectParkUseCountJoinPoiId(List<PoiIdVO> poiIdVOList) throws Exception; /**
    /**
     * InfoListType=2 POI_ID List요청인 경우 park_seq
     * @param poiIdVOList
     * @return
     * @throws Exception
     */
    public List<JSONObject> selectParkUseCountJoinParkSeq(List<PoiIdVO> poiIdVOList) throws Exception;
    /**
     * redis에서 재차대수 읽어오기
     * @param jedis
     * @param keysPattern
     * @return
     * @throws Exception
     */
    List<JSONObject> selectUseCount(Jedis jedis, String keysPattern) throws Exception;    
}

