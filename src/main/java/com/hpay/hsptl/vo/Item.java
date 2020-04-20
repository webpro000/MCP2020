package com.hpay.hsptl.vo;

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
 *  2020. 4. 20.     webpro000     	최초 생성
 * </pre>
 */

@XmlAccessorType(XmlAccessType.NONE)
public class Item {
    
    @XmlAttribute               // 속성
    private int rnum;
    @XmlElement                 // 요소(항목)
    private String dutyAddr;
    @XmlElement                 // 요소(항목)
    private String dutyDiv;
    
    
    /**
     * @return the rnum
     */
    public int getRnum() {
        return rnum;
    }
    /**
     * @param rnum the rnum to set
     */
    public void setRnum(int rnum) {
        this.rnum = rnum;
    }
    /**
     * @return the dutyAddr
     */
    public String getDutyAddr() {
        return dutyAddr;
    }
    /**
     * @param dutyAddr the dutyAddr to set
     */
    public void setDutyAddr(String dutyAddr) {
        this.dutyAddr = dutyAddr;
    }
    /**
     * @return the dutyDiv
     */
    public String getDutyDiv() {
        return dutyDiv;
    }
    /**
     * @param dutyDiv the dutyDiv to set
     */
    public void setDutyDiv(String dutyDiv) {
        this.dutyDiv = dutyDiv;
    }
    
    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Item [rnum=" + rnum + ", dutyAddr=" + dutyAddr + ", dutyDiv=" + dutyDiv + "]";
    }
    
    
    
}
