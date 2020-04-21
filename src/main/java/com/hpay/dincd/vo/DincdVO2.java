package com.hpay.dincd.vo;

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
 *  2020. 4. 21.     김진우     	최초 생성
 * </pre>
 */
@XmlRootElement(name="HospitalResult")      // HsptVO 리턴타입을 xml 으로 하며, RootElement를 <HospitalResult>~</HospitalResult> 형태로...
@XmlAccessorType(XmlAccessType.NONE)
public class DincdVO2 {
  
    @XmlElement
    int businessId = 0;
    
    @XmlElement(name="BasicData")
    private List<Item_basic> basic_data = new ArrayList();
    
    @XmlElement(name="AdditionalData")
    private List<Item_additional> additionnal_data = new ArrayList();

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

    /**
     * @return the additionnal_data
     */
    public List<Item_additional> getAdditionnal_data() {
        return additionnal_data;
    }

    /**
     * @param additionnal_data the additionnal_data to set
     */
    public void setAdditionnal_data(List<Item_additional> additionnal_data) {
        this.additionnal_data = additionnal_data;
    }

   
    
}
