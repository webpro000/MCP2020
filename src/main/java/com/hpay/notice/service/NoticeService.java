package com.hpay.notice.service;

import java.util.List;

import com.hpay.notice.vo.NoticeVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : NoticeService.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 7.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 7.     webpro000     	최초 생성
 * </pre>
 */

public interface NoticeService {

    public List<NoticeVO> selectNoticeList(NoticeVO vo) throws Exception;
    public int selectNoticeCount(NoticeVO vo) throws Exception;
    
    
    
    
}
