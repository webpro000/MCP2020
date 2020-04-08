package com.hpay.parking.vo;

import java.io.Serializable;

/**
 * @ClassName   : ParkEchargeInfoVO.java
 * @Description : 주차장 전기충전소 정보 객체 클래스
 * @author o1488
 * @since 2019. 12. 5.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 12. 5.     o1488     	최초 생성
 * </pre>
 */
public class ParkEchargeInfoVO implements Serializable{

   
    private static final long serialVersionUID = 7578494139654033024L;
    private String workDate;
    private String workDateSeq;
    private String src;      //주차장 CP(PKC:파킹클라우드)
    private String park_seq;
    private String park_category;
    private String park_echarge_cd;
    /**
     * @return the workDate
     */
    public String getWorkDate() {
        return workDate;
    }
    /**
     * @param workDate the workDate to set
     */
    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }
    /**
     * @return the workDateSeq
     */
    public String getWorkDateSeq() {
        return workDateSeq;
    }
    /**
     * @param workDateSeq the workDateSeq to set
     */
    public void setWorkDateSeq(String workDateSeq) {
        this.workDateSeq = workDateSeq;
    }
    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }
    /**
     * @param src the src to set
     */
    public void setSrc(String src) {
        this.src = src;
    }
    /**
     * @return the park_seq
     */
    public String getPark_seq() {
        return park_seq;
    }
    /**
     * @param park_seq the park_seq to set
     */
    public void setPark_seq(String park_seq) {
        this.park_seq = park_seq;
    }
    
    /**
     * @return the park_category
     */
    public String getPark_category() {
        return park_category;
    }
    /**
     * @param park_category the park_category to set
     */
    public void setPark_category(String park_category) {
        this.park_category = park_category;
    }
    /**
     * @return the park_echarge_cd
     */
    public String getPark_echarge_cd() {
        return park_echarge_cd;
    }
    /**
     * @param park_echarge_cd the park_echarge_cd to set
     */
    public void setPark_echarge_cd(String park_echarge_cd) {
        this.park_echarge_cd = park_echarge_cd;
    }
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    

}
