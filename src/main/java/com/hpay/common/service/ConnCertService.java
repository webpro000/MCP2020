package com.hpay.common.service;

import java.util.Map;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ConnCertService.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 3.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 3.     O1484     	최초 생성
 * </pre>
 */

public interface ConnCertService {
    //1.인증
    /**
     * CertKey 유효한지 검사
     * @param certKey 인증키
     * @return CertificationReturnCode
     * @throws Exception
     */
    Map<String, Object>  validationCertKey(String targetURI, String json) ;
}