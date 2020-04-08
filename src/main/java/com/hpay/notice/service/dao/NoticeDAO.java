package com.hpay.notice.service.dao;

import java.util.List;

import able.com.mybatis.Mapper;

import com.hpay.notice.vo.NoticeVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : NoticeDAO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 9.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 9.     webpro000     	최초 생성
 * </pre>
 */

@Mapper("noticeDAO")
public interface NoticeDAO {
    List<NoticeVO> selectNoticeList(NoticeVO vo) throws Exception;
    int selectNoticeCount(NoticeVO vo) throws Exception;
    
}
