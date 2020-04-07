package com.hpay.icps.service;

import com.hpay.icps.vo.StoreDeletedPackageVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : StoreDeletedService.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 7. 1.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 7. 1.     O1484     	최초 생성
 * </pre>
 */

public interface StoreDeletedService {
    String sendDeletedStoreInfoCompareWithRecentDelta();
    void receiveDeleteStoreInit(StoreDeletedPackageVO voStoreDeletedPackage) throws Exception;
    StoreDeletedPackageVO getLastDelStore3();
    String sendToICPSLastDeletedBinary(StoreDeletedPackageVO voStoreDeletedPackage);
    String parseVOToJson(String jsonFormat,  StoreDeletedPackageVO voStoreDeletePackage);
}