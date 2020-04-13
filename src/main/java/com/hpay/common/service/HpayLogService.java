package com.hpay.common.service;

import com.hpay.common.vo.HpayLogVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayLogService.java
 * @Description : 서비스 로그관련 서비스 선언 클래스
 * @author O1484
 * @since 2019. 6. 19.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 6. 19.     O1484      최초 생성
 * </pre>
 */

public interface HpayLogService {
    String statusDone="done";
    String statusFail="fail";
    String statusActive="active";
    String statusStandby="standby";
    String statusStandbyDone="standbyDone";
    
    String typeIp="ip";
    String typeUrl="url";
    String typeSrc="src";
    String typeInterfaceCode="interfaceCode";
    String typeCertKeyUser="cert_key_user";

    /**
     * 로그서비스 시작
     *
     * @param interfaceCode
     * @return
     */
    HpayLogVO init(String interfaceCode);
    /**
     * 로그서비스 시작
     *
     * @param interfaceCode
     * @param targetType
     * @param target
     * @return
     */
    HpayLogVO init(String interfaceCode, String targetType, String target);
    /**
     * 로그서비스 시작 (Async)
     *
     * @param interfaceCode
     * @param targetType
     * @param target
     * @param method
     * @return
     */
    HpayLogVO init(String interfaceCode, String targetType, String target, String method);
    /**
     * 로그서비스 시작 (서버 경합)
     *
     * @param interfaceCode
     * @param targetType
     * @param target
     * @return
     */
    HpayLogVO initTask(String interfaceCode, String targetType, String target);

    /**
     * 서버 경합 결과 체크
     *
     * @param voHpayLog
     * @return
     */
    Boolean checkOrder(HpayLogVO voHpayLog);
    
    /**
     * 인증키 결과 세팅
     *
     * @param voHpayLog
     * @param targetType
     * @param target
     */
    void setTarget(HpayLogVO voHpayLog, String targetType, String target);
    /**
     * 건수 세팅(제공건수-성공건수=실패건수) 
     *
     * @param voHpayLog
     * @param countTotal
     * @param countSuccess
     */
    void setCount(HpayLogVO voHpayLog, int countTotal, int countSuccess);
    
    /**
     * 로그서비스 상태 저장
     *
     * @param voHpayLog
     * @param Status
     */
    void setDone(HpayLogVO voHpayLog, String Status);
    /**
     * 로그서비스 상태 저장 에러 메세지 제외
     *
     * @param voHpayLog
     * @param Status
     * @param errCode     
     */
    
    void setDone(HpayLogVO voHpayLog,String Status, String errCode);  
    /**
     * 로그서비스 결과 저장
     *
     * @param voHpayLog
     * @param Status
     * @param errCode
     * @param errMsg
     */
    void setDone(HpayLogVO voHpayLog, String Status, String errCode, String errMsg);
    
    /**
     * 로그서비스 종료
     *
     * @param voHpayLog
     */
    
    
    void update(HpayLogVO voHpayLog);

    /*
    void setDone()throws Exception;
    void setFail();
    void setFail(String errCode, String errMsg)throws Exception;
    */
    
    
}