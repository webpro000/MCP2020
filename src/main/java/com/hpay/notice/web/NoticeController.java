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
 * </pre>
 */

@Controller
public class NoticeController extends HController {

    @Resource(name="noticeService")
    private NoticeService noticeService;
    
    @RequestMapping(path="/NOTICE/NoticeList.do")
    public @ResponseBody List<NoticeVO> selectNoticeList(NoticeVO vo, Model model, HttpServletRequest req){
        List<NoticeVO> list = new ArrayList();
        Integer cnt = 0;
        try{
            logger.info("-------------------------------------------Start");
            cnt = noticeService.selectNoticeCount(vo);
            logger.info("-------------------------------------------cnt:"+cnt);
            list = noticeService.selectNoticeList(vo);
            logger.info("-------------------------------------------list:"+list.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    @RequestMapping(path="/NOTICE/NoticeInsert.do")
    public @ResponseBody List<NoticeVO> NoticeListTEST(NoticeVO vo, Model model, HttpServletRequest req){
        List<NoticeVO> list = new ArrayList();
        Integer cnt = 0;
        try{
            logger.info("-------------------------------------------Start");
            if(vo.getTitle()!=null && !"".equals(vo.getTitle()) ){
                noticeService.insertNotice(vo);
            }
            logger.info("-------------------------------------------list:"+list.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            list = noticeService.selectNoticeList(new NoticeVO());
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
