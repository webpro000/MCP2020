package com.hpay.diningcd.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : Item.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 20.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 20.     webpro000      최초 생성
 * </pre>
 */

public class Item {
    
              // 속성
    private int businessId = 0;

    /**
     * @return the businessId
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     * @param businessId the businessId to set
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }
    
    
    
    
}
