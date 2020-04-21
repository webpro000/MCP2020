package com.hpay.dincd.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;

import able.com.service.HService;

import org.springframework.stereotype.Service;

import com.hpay.dincd.service.DincdService;
import com.hpay.dincd.service.dao.DincdDAO;
import com.hpay.common.service.dao.HpayLogMDAO;
import com.hpay.dincd.vo.DincdVO;


/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DincdServiceImpl.java
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
@Service("dincdService")
public class DincdServiceImpl implements DincdService {
    
    @Resource(name = "dincdDAO")
    private DincdDAO dincdDAO;
    

    
    
    public DincdVO selectDincdList(){

       DincdVO dincdVO = null;  
       
            try {
                return dincdVO = dincdDAO.selectDincdList();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return dincdVO;
            
           
    }
    
    public void insertTblDincdInfo(Map<String,Object> map){              
        
        dincdDAO.insertTblDincdInfo(map);
        
    }
    
    /*public void deleteTblDincdInfo() throws Exception{
        
               
        dincdDAO.deleteTblDincdInfo();
    }*/
}
