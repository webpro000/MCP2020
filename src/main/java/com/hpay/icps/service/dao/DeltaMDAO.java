package com.hpay.icps.service.dao;

import java.util.List;

import able.com.mybatis.Mapper;

import com.hpay.icps.vo.HpayStoreDeltaPackageVO;
import com.hpay.icps.vo.StoreDetailVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DeltaMDAO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 29.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 29.     O1484     	최초 생성
 * </pre>
 */

@Mapper("deltaMDAO")
public interface DeltaMDAO {
    String selectDataVersion_LastDelta()throws Exception;
    void deleteAleadyDelta(String dataVersion) throws Exception;
    void insertHpayStoreDeltaPackage(HpayStoreDeltaPackageVO voHpayStoreDeltaPackage)throws Exception;
    void insertArrHpayStoreDelta(List<StoreDetailVO> arrVO)throws Exception;
    HpayStoreDeltaPackageVO selectHpayStoreDeltaPackage(String dataVersion) throws Exception;
    List<StoreDetailVO> selectArrHpayStoreDelta(String dataVersion) throws Exception;
}
