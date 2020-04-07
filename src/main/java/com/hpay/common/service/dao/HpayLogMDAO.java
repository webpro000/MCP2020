package com.hpay.common.service.dao;
import able.com.mybatis.Mapper;
import com.hpay.common.vo.HpayLogVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayLogServiceMDAO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 6. 19.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 6. 19.     O1484     	최초 생성
 * </pre>
 */

@Mapper("hpayLogMDAO")
public interface HpayLogMDAO {
    int selectMaxTodayWorkSeq(HpayLogVO voHpayLog) throws Exception;
    String selectStandbyOrder(HpayLogVO voHpayLog)throws Exception;
    int insertHpayLog(HpayLogVO voHpayLog) throws Exception;
    void updateHpayLog(HpayLogVO voHpayLog)throws Exception;
}
