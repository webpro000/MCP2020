package com.hpay.icps.web;

import javax.annotation.Resource;

import able.com.service.prop.PropertyService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpay.common.service.HpayLogService;
import com.hpay.common.vo.HpayLogVO;
import com.hpay.icps.service.StoreDeletedService;


/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ScheduledTask.java
 * @Description : 매일 삭제데이터 추출해서 ICPS로 전송.
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
public class ScheduledTask {
    static Logger logger=Logger.getLogger(ScheduledTask.class);
    
    @Resource(name="hpayLogService")
    private HpayLogService hpayLogService;

    @Resource(name="storeDeletedService")
    private StoreDeletedService storeDeletedService;

    @Autowired
    PropertyService propertyService;

    public void hpayDelStoreSend() {
        logger.info("+++ Del Store Step 1 Start");
        HpayLogVO voHpayLog = hpayLogService.initTask(propertyService.getString("icps.delstore1.interfacecode"), "endpoint", propertyService.getString("icps.delstore1.targetUri"));
        try {
            logger.info("Step 1 : CheckOrder");
            if (hpayLogService.checkOrder(voHpayLog)) {
                logger.info("Step 2 : sendDeletedStoreInfoCompareWithRecentDelta");                
                String returnValue=storeDeletedService.sendDeletedStoreInfoCompareWithRecentDelta();
                if (returnValue.equals("")) {
                    hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
                    hpayLogService.update(voHpayLog);                                                    
                    logger.info("+++ Del Store Step 1 Fail");
                } else {
                    hpayLogService.setDone(voHpayLog, HpayLogService.statusDone);
                    hpayLogService.update(voHpayLog);                                                    
                    logger.info("+++ Del Store Step 1 Success");
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
            hpayLogService.setDone(voHpayLog, HpayLogService.statusFail);
            hpayLogService.update(voHpayLog);                                                    
            logger.info("+++ Del Store Step 1 Fail");
        }
    }
}