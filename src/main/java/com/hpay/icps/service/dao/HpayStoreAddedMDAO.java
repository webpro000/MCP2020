package com.hpay.icps.service.dao;

import java.util.List;

import able.com.mybatis.Mapper;

import com.hpay.icps.vo.HpayStoreAddedVO;


/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayStoreAddedMDAO.java
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

@Mapper("hpayStoreAddedMDAO")
public interface HpayStoreAddedMDAO {
    void insertArrHpayStoreAdded(List<HpayStoreAddedVO> arrVO) throws Exception;
}
