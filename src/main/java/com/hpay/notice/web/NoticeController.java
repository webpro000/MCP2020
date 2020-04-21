package com.hpay.notice.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import able.com.web.HController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hpay.notice.service.NoticeService;
import com.hpay.notice.vo.NoticeVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : NoticeController.java
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
 *  2020. 4. 22.    webpro000       공지사항 CRUD
 * </pre>
 */
@Controller
public class NoticeController extends HController {

    @Resource(name="noticeService")
    private NoticeService noticeService;
    
    @RequestMapping(path="/NOTICE/NoticeList.do")
    public @ResponseBody List<NoticeVO> NoticeList(NoticeVO vo, Model model, HttpServletRequest req){
        //http://localhost:8080/NOTICE/NoticeList.do
        //http://localhost:8080/NOTICE/NoticeList.do?title=S&useyn=Y
        List<NoticeVO> list = new ArrayList();
        Integer cnt = 0;
        try{
            logger.info("\n-------------------------------------------Start:\n"+vo.toString());
            cnt = noticeService.selectNoticeCount(vo);
            logger.info("\n-------------------------------------------cnt:"+cnt);
            list = noticeService.selectNoticeList(vo);
            logger.info("\n-------------------------------------------list:"+list.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    @RequestMapping(path="/NOTICE/NoticeInsert.do")
    public @ResponseBody List<NoticeVO> NoticeInsert(NoticeVO vo, Model model, HttpServletRequest req){
        //http://localhost:8080/NOTICE/NoticeInsert.do?title=TestTitle22&contents=testContents22&gtype=30&regid=system2
        try{
            logger.info("\n-------------------------------------------Start:\n"+vo.toString());
            if(vo.getTitle()!=null && !"".equals(vo.getTitle()) ){
                noticeService.insertNotice(vo);
            }
            logger.info("\n-------------------------------------------End");
        }catch(Exception e){
            e.printStackTrace();
        }
        return getNoticeList(new NoticeVO());
    }
    @RequestMapping(path="/NOTICE/NoticeUpdate.do")
    public @ResponseBody List<NoticeVO> NoticeUpdate(NoticeVO vo, Model model, HttpServletRequest req){
        //http://localhost:8080/NOTICE/NoticeUpdate.do?notice_seq=5&title=TestTitle&contents=testContents&gtype=20&regid=test
        try{
            logger.info("\n-------------------------------------------Start:\n"+vo.toString());
            if(vo.getNotice_seq()!=null && vo.getNotice_seq()>0 ){
                noticeService.updateNotice(vo);
            }
            logger.info("\n-------------------------------------------End");
        }catch(Exception e){
            e.printStackTrace();
        }
        return getNoticeList(new NoticeVO());
    }


    @RequestMapping(path="/NOTICE/NoticeDelete.do")
    public @ResponseBody List<NoticeVO> NoticeDelete(NoticeVO vo, Model model, HttpServletRequest req){
        //http://localhost:8080/NOTICE/NoticeDelete.do?notice_seq=5&useyn=N
        try{
            logger.info("\n-------------------------------------------Start:\n"+vo.toString());
            if(vo.getNotice_seq()!=null && vo.getNotice_seq()>0 ){
                if(!"Y".equals(vo.getUseyn())) vo.setUseyn("N");
                noticeService.deleteNotice(vo);
            }
            logger.info("\n-------------------------------------------End");
        }catch(Exception e){
            e.printStackTrace();
        }
        return getNoticeList(new NoticeVO());
    }
    
    
    private List<NoticeVO> getNoticeList(NoticeVO vo){
        List<NoticeVO> list = new ArrayList();
        try{
            list = noticeService.selectNoticeList(vo);
            System.out.println("전체목록(getNoticeList):");
            if(list!=null){
                for(int i=0;i<list.size();i++){
                    NoticeVO rec = list.get(i);
                    System.out.println(rec);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
}
