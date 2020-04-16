package com.hpay.notice.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import able.com.service.prop.PropertyService;
import able.com.web.HController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hpay.common.service.HpayLogService;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.icps.service.StoreDeletedService;
//import com.hpay.notice.service.dao.NoticeDAO;
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

    @Resource(name="storeDeletedService")
    private StoreDeletedService storeDeletedService;
    
    @Resource(name="noticeService")
    private NoticeService noticeService;
    
    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;
    
    
    @Autowired
    PropertyService propertyService;
    
    @RequestMapping(path="/NOTICE/NoticeList.do")
    public @ResponseBody List<NoticeVO> selectNoticeList(NoticeVO vo, Model model, HttpServletRequest req){
        //NoticeVO vo = new NoticeVO();
        List<NoticeVO> list = new ArrayList();
        Integer cnt = 0;
        try{
            logger.info("-------------------------------------------Start");
            cnt = noticeService.selectNoticeCount(vo);
            logger.info("-------------------------------------------cnt:"+cnt);
            list = noticeService.selectNoticeList(vo);
            logger.info("-------------------------------------------list:"+list.toString());
            logger.info("+++sendDeltaToMCP : Start");
            HpayLogVO voHpayLog = hpayLogService.init(propertiesService.getString("hmns.delta.interfacecode"));
            hpayLogService.update(voHpayLog);
            
            String responseBody="";
           
            logger.info("+++sendDeltaToMCP : Success : " +responseBody); 
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
  /*  @RequestMapping(value="/cmdHpayDelStoreSend2.do", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    public @ResponseBody String delstore1() {        
        return storeDeletedService.sendDeletedStoreInfoCompareWithRecentDelta();
    }*/
    
    
    
}
