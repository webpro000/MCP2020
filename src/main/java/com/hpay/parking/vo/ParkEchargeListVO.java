package com.hpay.parking.vo;

import java.io.Serializable;

/**
 * @ClassName   : ParkEchargeListVO.java
 * @Description : 주차장 전기충전소 리스트 정보 객체 클래스
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
public class ParkEchargeListVO  implements Serializable{

    /**
     * 자동생성
     * (long)serialVersionUID 
     */
    private static final long serialVersionUID = -7240331987171194027L;
//    private String park_category;
//    private String park_seq;
    private String park_echarge_cd;

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
