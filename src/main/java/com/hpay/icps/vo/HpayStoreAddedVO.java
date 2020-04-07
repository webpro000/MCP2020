package com.hpay.icps.vo;

import java.util.Date;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayStoreAdded.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 9.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 9.     O1484     	최초 생성
 * </pre>
 */

public class HpayStoreAddedVO {
    public Date reqDate;
    public String poiId;
    public String storeId;
    public Date startDate;
    public HpayStoreAddedVO(Date reqDate, String poiId, String storeId, Date startDate){
        this.reqDate=reqDate;
        this.poiId=poiId;
        this.storeId=storeId;
        this.startDate=startDate;
    }
}