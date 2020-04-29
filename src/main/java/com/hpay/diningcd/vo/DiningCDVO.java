package com.hpay.diningcd.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import com.hpay.hsptl.vo.Item;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DiningCDVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author 김진우
 * @since 2020. 4. 24.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 24.     김진우     	최초 생성
 * </pre>
 */

public class DiningCDVO {

    
    private int business_id = 0;
       
    private Map<String,Object> basic_data = new HashMap();       // 병원록록

    /**
     * @return the business_id
     */
    public int getBusiness_id() {
        return business_id;
    }

    /**
     * @param business_id the business_id to set
     */
    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    /**
     * @return the basic_data
     */
    public Map<String, Object> getBasic_data() {
        return basic_data;
    }

    /**
     * @param basic_data the basic_data to set
     */
    public void setBasic_data(Map<String, Object> basic_data) {
        this.basic_data = basic_data;
    }
    
    

    
}
