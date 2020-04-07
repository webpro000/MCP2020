package com.hpay.icps.vo;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayStoreDeltaPackageVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 13.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 13.     O1484     	최초 생성
 * </pre>
 */

public class HpayStoreDeltaPackageVO {
    @NotEmpty(message="|hmns.delta.package.nomandatory|")
    private String interfaceCode;
    @NotEmpty(message="|hmns.delta.package.nomandatory|")
    private String formatVersion;
    @NotEmpty(message="|hmns.delta.package.nomandatory|")
    private String dataVersion;
    
    @NotEmpty(message="|hmns.delta.package.nomandatory|")
    @JsonProperty("2WBinary")
    private String binary2W;
    
    @NotEmpty(message="|hmns.delta.package.nomandatory|")
    @JsonProperty("centerData")
    private List<StoreDetailVO> arrVOHpayStoreDelta;

    public String getInterfaceCode() {
        if (interfaceCode==null){
            return "";
        } else {
            return interfaceCode;            
        }
    }
    
    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }
    public String getFormatVersion() {
        return formatVersion;
    }
    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }
    public String getDataVersion() {
        return dataVersion;
    }
    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }
    public String getBinary2W() {
        return binary2W;
    }
    public void setBinary2W(String binary2w) {
        binary2W = binary2w;
    }
    public List<StoreDetailVO> getArrVOHpayStoreDelta() {
        return arrVOHpayStoreDelta;
    }
    public void setArrVOHpayStoreDelta(List<StoreDetailVO> arrVOHpayStoreDelta) {
        this.arrVOHpayStoreDelta = arrVOHpayStoreDelta;
    }
}
