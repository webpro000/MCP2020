package com.hpay.parking.vo;

import org.hibernate.validator.constraints.NotEmpty;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ClassName   : ParkingLotVO.java
 * @Description : 주차장 Dynamic 재차정보 제공 데이터 객체 클래스
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
public class ParkingLotVO {

    @NotEmpty
    private String src;    
    private String park_category;
    @NotEmpty
    private String park_seq;
    @NotEmpty
    private String status;
    private String parkingStatus;
    private String parkingStatus_eng;
    @NotEmpty
    private String update_date;
    @JsonIgnore
    private String insert_date;
    
    /**
     * @return the insert_date
     */
    public String getInsert_date() {
        return insert_date;
    }
    /**
     * @param insert_date the insert_date to set
     */
    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * @return the parkingStatus
     */
    public String getParkingStatus() {
        return parkingStatus;
    }
    /**
     * @param parkingStatus the parkingStatus to set
     */
    public void setParkingStatus(String parkingStatus) {
        this.parkingStatus = parkingStatus;
    }
    /**
     * @return the parkingStatus_eng
     */
    public String getParkingStatus_eng() {
        return parkingStatus_eng;
    }
    /**
     * @param parkingStatus_eng the parkingStatus_eng to set
     */
    public void setParkingStatus_eng(String parkingStatus_eng) {
        this.parkingStatus_eng = parkingStatus_eng;
    }
    /**
     * @return the update_date
     */
    public String getUpdate_date() {
        return update_date;
    }
    /**
     * @param update_date the update_date to set
     */
    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }
    
    @SuppressWarnings("unchecked")
    public JSONObject toJSON(){
        JSONObject result = new JSONObject();
        
        result.put("src", src);
        result.put("park_category", park_category);
        result.put("park_seq", park_seq);
        result.put("status", status);
        result.put("parkingStatus", parkingStatus);
        result.put("update_date", update_date);
        result.put("parkingStatus_eng", parkingStatus_eng);
        
        return result;
    }
            

}
