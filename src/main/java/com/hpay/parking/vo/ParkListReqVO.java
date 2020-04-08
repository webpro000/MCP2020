package com.hpay.parking.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @ClassName   : ParkListReqVO.java
 * @Description : 주차장 리스트 요청 정보 클래스
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
public class ParkListReqVO {

    @NotEmpty
    @Length(min = 1, max = 30)
    private String interfaceCode;
    @NotEmpty
    private String versionInfo ;
    @NotEmpty
    @Length(min = 1, max = 1)
    private String infoListType;
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
     * @return the infoListType
     */
    public String getInfoListType() {
        return infoListType;
    }
    /**
     * @param infoListType the infoListType to set
     */
    public void setInfoListType(String infoListType) {
        this.infoListType = infoListType;
    }
    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ParkListReqVO [getInterfaceCode()=" + getInterfaceCode() + ", getVersionInfo()=" + getVersionInfo()
                + ", getInfoListType()=" + getInfoListType() + "]";
    }
     
    
    
}
