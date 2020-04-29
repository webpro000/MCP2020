package com.hpay.diningcd.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DincdVO2.java
 * @Description : 클래스 설명을 기술합니다.
 * @author 김진우
 * @since 2020. 4. 21.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 21.     김진우         최초 생성
 * </pre>
 */


public class DincdVO2 {
  
    
    int businessId = 0;
    
    
    private List<Item_basic> basic_data = new ArrayList();


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


    /**
     * @return the basic_data
     */
    public List<Item_basic> getBasic_data() {
        return basic_data;
    }


    /**
     * @param basic_data the basic_data to set
     */
    public void setBasic_data(List<Item_basic> basic_data) {
        this.basic_data = basic_data;
    }


    
    
    
}