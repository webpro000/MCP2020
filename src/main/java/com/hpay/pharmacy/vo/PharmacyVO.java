package com.hpay.pharmacy.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : PharmacyVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 16.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 16.     webpro000     	최초 생성
 * </pre>
 */

@XmlAccessorType(XmlAccessType.NONE)
public class PharmacyVO {
    
    @XmlAttribute
    private Integer id;
    
    @XmlElement
    private String name;
    @XmlElement
    private String address;
    
    
    
    
    /**
     * Statements
     *
     */
    public PharmacyVO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * Statements
     *
     * @param id
     * @param name
     * @param address
     */
    public PharmacyVO(Integer id, String name, String address) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
    }
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PharmacyVO [id=" + id + ", name=" + name + ", address=" + address + "]";
    }
    
    
    
}
