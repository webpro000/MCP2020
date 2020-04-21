package com.hpay.dincd.service.dao;

import java.util.HashMap;
import java.util.Map;

import able.com.mybatis.Mapper;

import com.hpay.common.vo.HpayLogVO;
import com.hpay.dincd.vo.DincdVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DincdDao.java
 * @Description : 클래스 설명을 기술합니다.
 * @author 김진우
 * @since 2020. 4. 13.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 13.     김진우     	최초 생성
 * </pre>
 */

@Mapper("dincdDAO")
public interface DincdDAO {

    /**
     * Statements
     *
     * @return
     */   
    DincdVO selectDincdList() throws Exception;
    
    void insertTblDincdInfo(Map<String, Object> map);
        
    void deleteTblDincdInfo();
    

}
