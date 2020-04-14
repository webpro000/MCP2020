package com.hpay.Hsptl.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import able.com.web.HController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hpay.Hsptl.vo.HsptVO;
import com.hpay.Hsptl.vo.Item;
import com.hpay.notice.vo.NoticeVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : TestController.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 14.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 14.     webpro000     	최초 생성
 * </pre>
 */
@RestController
public class TestController  extends HController {
    
    @RequestMapping(path="/TEST/HsptlList.do")
    public @ResponseBody HsptVO HsptlList(NoticeVO vo, Model model, HttpServletRequest req){
        HsptVO Hvo=new HsptVO();
        try{
            logger.info("-------------------------------------------Start");
            
            Item item1=new Item();
            item1.setRnum(100);
            item1.setDutyAddr("서울시");
            item1.setDutyDiv("변원분류");
            Hvo.getItems().add(item1);
            
            Item item2=new Item();
            item2.setRnum(101);
            item2.setDutyAddr("서울시11");
            item2.setDutyDiv("변원분류11");
            Hvo.getItems().add(item2);
            
            Hvo.setResultCode("OK");
            Hvo.setResultMag("성공입니다.");

            logger.info("-------------------------------------------End");
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return Hvo;
    }
    
    
    
}
