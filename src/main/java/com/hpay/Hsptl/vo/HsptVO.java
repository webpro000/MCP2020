package com.hpay.Hsptl.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HsptVO.java
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

public class HsptVO {
    private String resultCode;      // 결과코드
    private String resultMag;       // 결과메시지
    private List<Item> Items = new ArrayList();       // 병원록록
    /**
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }
    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    /**
     * @return the resultMag
     */
    public String getResultMag() {
        return resultMag;
    }
    /**
     * @param resultMag the resultMag to set
     */
    public void setResultMag(String resultMag) {
        this.resultMag = resultMag;
    }
    /**
     * @return the items
     */
    public List<Item> getItems() {
        return Items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        Items = items;
    }
    
    
    
    
}
