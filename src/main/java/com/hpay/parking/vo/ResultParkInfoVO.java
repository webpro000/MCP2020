package com.hpay.parking.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ClassName   : ResultParkInfoVO.java
 * @Description : 주차장 Static 제공 데이터 객체 클래스
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
public class ResultParkInfoVO implements Serializable{

    
    private static final long serialVersionUID = -8506353002150867798L;
    private String interfaceCode;
    private String resultCode ;
    private String resultMessage;
    private String versionInfo;     //제공 버전
    private List<ParkInfoVO> park_list;

    @JsonIgnore
    private String certKeyUser;
    /**
     * @return the certKeyUser
     */
    public String getCertKeyUser() {
        return certKeyUser;
    }
    /**
     * @param certKeyUser the certKeyUser to set
     */
    public void setCertKeyUser(String certKeyUser) {
        this.certKeyUser = certKeyUser;
    }    
    
    /**
     * @return the interfaceCode
     */
    public String getInterfaceCode() {
        return interfaceCode;
    }
    /**
     * @param interfaceCode the interfaceCode to set
     */
    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }
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
     * @return the resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }
    /**
     * @param resultMessage the resultMessage to set
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    /**
     * @return the versionInfo
     */
    public String getVersionInfo() {
        return versionInfo;
    }
    /**
     * @param versionInfo the versionInfo to set
     */
    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }
    /**
     * @return the park_list
     */
    public List<ParkInfoVO> getPark_list() {
        return park_list;
    }
    /**
     * @param park_list the park_list to set
     */
    public void setPark_list(List<ParkInfoVO> park_list) {
        this.park_list = park_list;
    }
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
   
    
    
    
}
