package com.hpay.notice.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import able.com.service.HService;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpay.notice.service.dao.NoticeDAO;
import com.hpay.notice.service.NoticeService;
import com.hpay.notice.vo.NoticeVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : NoticeServiceImpl.java
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
@Service("noticeService")
public class NoticeServiceImpl  extends HService implements NoticeService{
    
    //@Resource(name="noticeDAO")
    //@Autowired
    @Resource(name="noticeDAO")
    private NoticeDAO noticeDAO;
    
      /**
       * 공지사항 목록
       * Statements
       *
       * @param vo
       * @return
       * @throws Exception
       */
      public List<NoticeVO> selectNoticeList(NoticeVO vo) throws Exception{
//          List list = new ArrayList();
//          //list.add(vo);
//          return list;
          return noticeDAO.selectNoticeList(vo);
      }
      
      /**
       * 공지사항 갯수
       * Statements
       *
       * @param vo
       * @return
       * @throws Exception
       */
      public int selectNoticeCount(NoticeVO vo) throws Exception{
//          return 1;
          return noticeDAO.selectNoticeCount(vo);
      }
}
