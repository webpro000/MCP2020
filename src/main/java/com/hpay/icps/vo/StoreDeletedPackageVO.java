package com.hpay.icps.vo;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : StoreDeletedPackageVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 7. 1.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 7. 1.     O1484     	최초 생성
 * </pre>
 */

public class StoreDeletedPackageVO {
    private int hpay_store_delete_package_seq;
    private String format_version;
    private String data_version_delta;
    
    @JsonProperty("dataVersion")
    private String data_version;

    @NotEmpty(message="|icps.delstore2.nomandatory|icps.delstore3json.nomandatory|")
    @JsonProperty("interfaceCode")
    private String interface_code;
    @NotEmpty(message="|icps.delstore2.nomandatory|")
    @JsonProperty("initateDeleteStoreDate")
    private String work_date;
    private String work_date_seq;
    @NotEmpty(message="|icps.delstore2.nomandatory|")
    @JsonProperty("initiateCode")
    private String initiate_code;
    private String binary_delete;
    @NotEmpty(message="|icps.delstore2.nomandatory|")
    @JsonProperty("delStoreList")
    @Valid
    private List<StoreDeletedVO> arrVOStoreDelete;
    
    @NotEmpty(message="|icps.delstore2.nomandatory|")
    private String reqDeleteStoreDate;
    
    public String getReqDeleteStoreDate() {
        return reqDeleteStoreDate;
    }
    public void setReqDeleteStoreDate(String reqDeleteStoreDate) {
        this.reqDeleteStoreDate = reqDeleteStoreDate;
    }
    public List<StoreDeletedVO> getArrVOStoreDelete() {
        return arrVOStoreDelete;
    }
    public void setArrVOStoreDelete(List<StoreDeletedVO> arrVOStoreDelete) {
        this.arrVOStoreDelete = arrVOStoreDelete;
    }
    public int getHpay_store_delete_package_seq() {
        return hpay_store_delete_package_seq;
    }
    public void setHpay_store_delete_package_seq(int hpay_store_delete_package_seq) {
        this.hpay_store_delete_package_seq = hpay_store_delete_package_seq;
        if (arrVOStoreDelete != null){
            for (int i=0; i<arrVOStoreDelete.size(); i++){
                arrVOStoreDelete.get(i).setHpay_store_delete_package_seq(hpay_store_delete_package_seq);
            }            
        }
    }
    public String getFormat_version() {
        return format_version;
    }
    public void setFormat_version(String format_version) {
        this.format_version = format_version;
    }
    public String getData_version_delta() {
        return data_version_delta;
    }
    public void setData_version_delta(String data_version_delta) {
        this.data_version_delta = data_version_delta;
    }
    public String getData_version() {
        if (data_version==null){
            return null; 
        } 
        return data_version;
    }
    public void setData_version(String data_version) {
        this.data_version = data_version;
    }
    public String getInterface_code() {
        return interface_code;
    }
    public void setInterface_code(String interface_code) {
        this.interface_code = interface_code;
    }
    public String getWork_date() {
        return work_date;
    }
    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }
    public String getWork_date_seq() {
        return work_date_seq;
    }
    public void setWork_date_seq(String work_date_seq) {
        this.work_date_seq = work_date_seq;
    }
    public String getInitiate_code() {
        return initiate_code;
    }
    public void setInitiate_code(String initiate_code) {
        this.initiate_code = initiate_code;
    }
    public String getBinary_delete() {
        return binary_delete;
    }
    public void setBinary_delete(String binary_delete) {
        this.binary_delete = binary_delete;
    }
}
