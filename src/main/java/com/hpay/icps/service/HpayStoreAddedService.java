package com.hpay.icps.service;

import java.util.List;

import com.hpay.icps.vo.HpayStoreAddedVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayStoreAddedService.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 9.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 9.     O1484     	최초 생성
 * </pre>
 */

public interface HpayStoreAddedService {
    /**
     * 데이터 저장
     * @param voHpayStoreAdded :  저장할 VO
     * @return SetDataReturnCode
     * @throws Exception
     */    
    int setData(List<HpayStoreAddedVO> arrVoHpayStoreAdded)throws Exception;
    /**
     * JSON string 을 VO에 파싱
     * @param json :  json 스트링
     * @return HpayStoreAddedVO 
     * @throws Exception
     */    
    List<HpayStoreAddedVO> parseJsonToVO(String json)throws Exception;
}
