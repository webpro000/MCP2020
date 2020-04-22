package com.hpay.diningcd.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DincdJsonVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author 김진우
 * @since 2020. 4. 16.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 16.     김진우     	최초 생성
 * </pre>
 */

public class DincdJsonVO {
    private int businessId = 0;
    private List<Map<String,Object>> basic_data = new ArrayList();      //결과메세지
    private List<Map<String,Object>> additionnal_data = new ArrayList();
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
    public List<Map<String, Object>> getBasic_data() {
        return basic_data;
    }
    /**
     * @param basic_data the basic_data to set
     */
    public void setBasic_data(List<Map<String, Object>> basic_data) {
        this.basic_data = basic_data;
    }
    /**
     * @return the additionnal_data
     */
    public List<Map<String, Object>> getAdditionnal_data() {
        return additionnal_data;
    }
    /**
     * @param additionnal_data the additionnal_data to set
     */
    public void setAdditionnal_data(List<Map<String, Object>> additionnal_data) {
        this.additionnal_data = additionnal_data;
    }
    

}
