package com.hpay.parking.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

/**
 * @ClassName   : ParkingUtilService.java
 * @Description : 주차장 Utility 서비스 선언 클래스
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
public interface ParkingUtilService {
    /**
     * URL 서버접속 가능 여부 체크
     * @param strUrl
     * @return
     * @throws Exception
     */
    public List<String> addressAliveCheck(String strUrl) throws Exception;
    /**
     * 파킹클라우드 접속
     * @param strUrl
     * @param fileName
     * @return
     * @throws Exception
     */
    public JSONObject connectParkingCloud(String strUrl, String fileName) throws Exception;
    /**
     * 파킹클라우드 요청 암호화 문자열 해쉬키 만들기
     * @param appKey
     * @param timeStamp
     * @param secretKey
     * @return
     * @throws Exception
     */
    public String getSignature(String appKey, String timeStamp, String secretKey) throws Exception;    
    /**
     * MessageDigest 호출
     * @param algorithm
     * @param org
     * @return
     * @throws Exception
     */
    public byte[] messageDigest(String algorithm, String org) throws Exception;
    /**
     * 16진수 문자열(hexdigest)변환
     * @param hash
     * @return
     * @throws Exception
     */
    public String bytesToHex(byte[] hash) throws Exception;
    /**
     * 사용자 아이피 가져오기
     * @param req
     * @return
     */
    public String getClientIp(HttpServletRequest req);
    /**
     * CP IP저장
     * @param cpAdress
     * @throws Exception
     */
    void setCpAdressRedis(String cpAdress) throws Exception;
}

