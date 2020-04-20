package com.hpay.hsptl.vo;

import java.util.*;

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
 * @ClassName   : HsptVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 20.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 20.     webpro000     	최초 생성
 * </pre>
 */
@XmlRootElement(name="HospitalResult")      // HsptVO 리턴타입을 xml 으로 하며, RootElement를 <HospitalResult>~</HospitalResult> 형태로...
@XmlAccessorType(XmlAccessType.NONE)
public class HsptVO {
    
    @XmlElement
    private String resultCode;      // 결과코드
    
    @XmlElement
    private String resultMag;       // 결과메시지
    
    @XmlElement(name="Hospital")    // Element 명을 Hospital 으로 지정.
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
