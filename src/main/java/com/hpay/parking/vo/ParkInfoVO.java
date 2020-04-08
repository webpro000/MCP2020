package com.hpay.parking.vo;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * @ClassName   : ParkInfoVO.java
 * @Description : 주차장 정보 제공 데이터 객체 클래스
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
public class ParkInfoVO implements Serializable{


   
    /**
     * Statements
     * (long)serialVersionUID 
     */
    private static final long serialVersionUID = -8316095031040688451L;
    @JsonIgnore
    private String provideService;
    @JsonIgnore
    private String workDate;
    @JsonIgnore
    private String workDateSeq;
    private String src;      //주차장 CP(PKC:파킹클라우드)
    @NotEmpty
    private String park_category;   //주차장분류(park:파킹클라우드 제휴주차장,PAGL:일반 주차장)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_division;
    @NotEmpty
    private String park_seq;        //주차장 seq
    private String infoChangeType;  //정보변경타입
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_type_cd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_latitude;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_longitude;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_zipcode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_address_1;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_address_2;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Length(min = 0, max = 4)
    private String park_phone_1;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Length(min = 0, max = 4)
    private String park_phone_2;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Length(min = 0, max = 4)
    private String park_phone_3;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_biz_hour_cd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_biz_time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_sat_biz_time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_sun_hol_biz_time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_interval_free_ny;

    //예외처리
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_app_vision_available_car;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_total_num;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_atch_url;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_basic_interval_minute;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_basic_interval_price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_additional_interval_minute;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_additional_interval_price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_oneday_price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_regular_monthly_price;
    
    //예외처리
    //@JsonInclude(JsonInclude.Include.ALWAYS)
    private String park_sat_biz_hour_cd;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_sun_hol_biz_hour_cd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_shape_ct;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String discountticket_use_ny;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String incarpayment_use_yn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String park_echarge_ny;
    //제공 시 값이 없으면 키도 생략
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ParkEchargeListVO> park_echarge_list;
    //private JSONArray park_echarge_list;
    @JsonIgnore
    private String park_echarge_list_json;
 
    


    /**
     * @param park_app_vision_available_car the park_app_vision_available_car to set
     */
    public void setPark_app_vision_available_car(String park_app_vision_available_car) {
        this.park_app_vision_available_car = park_app_vision_available_car;         
    }
    
    /**
     * @param park_sat_biz_hour_cd the park_sat_biz_hour_cd to set
     */
    public void setPark_sat_biz_hour_cd(String park_sat_biz_hour_cd) {
        this.park_sat_biz_hour_cd = park_sat_biz_hour_cd;    
    }
    
    /**
     * 예외처리 : null일 경우 0
     * @return the park_app_vision_available_car
     */
    public String getPark_app_vision_available_car() {
        return park_app_vision_available_car;
    }
    
    /**
     * 예외처리
     * @return the park_sat_biz_hour_cd
     */
    public String getPark_sat_biz_hour_cd() {
        return park_sat_biz_hour_cd;
    }
    
    /**
     * @return the provideService
     */
    public String getProvideService() {
        return provideService;
    }
    /**
     * @param provideService the provideService to set
     */
    public void setProvideService(String provideService) {
        this.provideService = provideService;
    }
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
     * @return the park_division
     */
    public String getPark_division() {
        return park_division;
    }
    /**
     * @param park_division the park_division to set
     */
    public void setPark_division(String park_division) {
        this.park_division = park_division;
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
     * @return the infoChangeType
     */
    public String getInfoChangeType() {
        return infoChangeType;
    }
    /**
     * @param infoChangeType the infoChangeType to set
     */
    public void setInfoChangeType(String infoChangeType) {
        this.infoChangeType = infoChangeType;
    }
    /**
     * @return the park_name
     */
    public String getPark_name() {
        return park_name;
    }
    /**
     * @param park_name the park_name to set
     */
    public void setPark_name(String park_name) {
        this.park_name = park_name;
    }
    /**
     * @return the park_type_cd
     */
    public String getPark_type_cd() {
        return park_type_cd;
    }
    /**
     * @param park_type_cd the park_type_cd to set
     */
    public void setPark_type_cd(String park_type_cd) {
        this.park_type_cd = park_type_cd;
    }
    /**
     * @return the park_latitude
     */
    public String getPark_latitude() {
        return park_latitude;
    }
    /**
     * @param park_latitude the park_latitude to set
     */
    public void setPark_latitude(String park_latitude) {
        this.park_latitude = park_latitude;
    }
    /**
     * @return the park_longitude
     */
    public String getPark_longitude() {
        return park_longitude;
    }
    /**
     * @param park_longitude the park_longitude to set
     */
    public void setPark_longitude(String park_longitude) {
        this.park_longitude = park_longitude;
    }
    /**
     * @return the park_zipcode
     */
    public String getPark_zipcode() {
        return park_zipcode;
    }
    /**
     * @param park_zipcode the park_zipcode to set
     */
    public void setPark_zipcode(String park_zipcode) {
        this.park_zipcode = park_zipcode;
    }
    /**
     * @return the park_address_1
     */
    public String getPark_address_1() {
        return park_address_1;
    }
    /**
     * @param park_address_1 the park_address_1 to set
     */
    public void setPark_address_1(String park_address_1) {
        this.park_address_1 = park_address_1;
    }
    /**
     * @return the park_address_2
     */
    public String getPark_address_2() {
        return park_address_2;
    }
    /**
     * @param park_address_2 the park_address_2 to set
     */
    public void setPark_address_2(String park_address_2) {
        this.park_address_2 = park_address_2;
    }
    /**
     * @return the park_phone_1
     */
    public String getPark_phone_1() {
        return park_phone_1;
    }
    /**
     * @param park_phone_1 the park_phone_1 to set
     */
    public void setPark_phone_1(String park_phone_1) {
        this.park_phone_1 = park_phone_1;
    }
    /**
     * @return the park_phone_2
     */
    public String getPark_phone_2() {
        return park_phone_2;
    }
    /**
     * @param park_phone_2 the park_phone_2 to set
     */
    public void setPark_phone_2(String park_phone_2) {
        this.park_phone_2 = park_phone_2;
    }
    /**
     * @return the park_phone_3
     */
    public String getPark_phone_3() {
        return park_phone_3;
    }
    /**
     * @param park_phone_3 the park_phone_3 to set
     */
    public void setPark_phone_3(String park_phone_3) {
        this.park_phone_3 = park_phone_3;
    }
    /**
     * @return the park_biz_hour_cd
     */
    public String getPark_biz_hour_cd() {
        return park_biz_hour_cd;
    }
    /**
     * @param park_biz_hour_cd the park_biz_hour_cd to set
     */
    public void setPark_biz_hour_cd(String park_biz_hour_cd) {
        this.park_biz_hour_cd = park_biz_hour_cd;
    }
    /**
     * @return the park_biz_time
     */
    public String getPark_biz_time() {
        return park_biz_time;
    }
    /**
     * @param park_biz_time the park_biz_time to set
     */
    public void setPark_biz_time(String park_biz_time) {
        this.park_biz_time = park_biz_time;
    }
    /**
     * @return the park_sat_biz_time
     */
    public String getPark_sat_biz_time() {
        return park_sat_biz_time;
    }
    /**
     * @param park_sat_biz_time the park_sat_biz_time to set
     */
    public void setPark_sat_biz_time(String park_sat_biz_time) {
        this.park_sat_biz_time = park_sat_biz_time;
    }
    /**
     * @return the park_sun_hol_biz_time
     */
    public String getPark_sun_hol_biz_time() {
        return park_sun_hol_biz_time;
    }
    /**
     * @param park_sun_hol_biz_time the park_sun_hol_biz_time to set
     */
    public void setPark_sun_hol_biz_time(String park_sun_hol_biz_time) {
        this.park_sun_hol_biz_time = park_sun_hol_biz_time;
    }
    /**
     * @return the park_interval_free_ny
     */
    public String getPark_interval_free_ny() {
        return park_interval_free_ny;
    }
    /**
     * @param park_interval_free_ny the park_interval_free_ny to set
     */
    public void setPark_interval_free_ny(String park_interval_free_ny) {
        this.park_interval_free_ny = park_interval_free_ny;
    }
    /**
     * @return the park_total_num
     */
    public String getPark_total_num() {
        return park_total_num;
    }
    /**
     * @param park_total_num the park_total_num to set
     */
    public void setPark_total_num(String park_total_num) {
        this.park_total_num = park_total_num;
    }
    /**
     * @return the park_atch_url
     */
    public String getPark_atch_url() {
        return park_atch_url;
    }
    /**
     * @param park_atch_url the park_atch_url to set
     */
    public void setPark_atch_url(String park_atch_url) {
        this.park_atch_url = park_atch_url;
    }
    /**
     * @return the park_basic_interval_minute
     */
    public String getPark_basic_interval_minute() {
        return park_basic_interval_minute;
    }
    /**
     * @param park_basic_interval_minute the park_basic_interval_minute to set
     */
    public void setPark_basic_interval_minute(String park_basic_interval_minute) {
        this.park_basic_interval_minute = park_basic_interval_minute;
    }
    /**
     * @return the park_basic_interval_price
     */
    public String getPark_basic_interval_price() {
        return park_basic_interval_price;
    }
    /**
     * @param park_basic_interval_price the park_basic_interval_price to set
     */
    public void setPark_basic_interval_price(String park_basic_interval_price) {
        this.park_basic_interval_price = park_basic_interval_price;
    }
    /**
     * @return the park_additional_interval_minute
     */
    public String getPark_additional_interval_minute() {
        return park_additional_interval_minute;
    }
    /**
     * @param park_additional_interval_minute the park_additional_interval_minute to set
     */
    public void setPark_additional_interval_minute(String park_additional_interval_minute) {
        this.park_additional_interval_minute = park_additional_interval_minute;
    }
    /**
     * @return the park_additional_interval_price
     */
    public String getPark_additional_interval_price() {
        return park_additional_interval_price;
    }
    /**
     * @param park_additional_interval_price the park_additional_interval_price to set
     */
    public void setPark_additional_interval_price(String park_additional_interval_price) {
        this.park_additional_interval_price = park_additional_interval_price;
    }
    /**
     * @return the park_oneday_price
     */
    public String getPark_oneday_price() {
        return park_oneday_price;
    }
    /**
     * @param park_oneday_price the park_oneday_price to set
     */
    public void setPark_oneday_price(String park_oneday_price) {
        this.park_oneday_price = park_oneday_price;
    }
    /**
     * @return the park_regular_monthly_price
     */
    public String getPark_regular_monthly_price() {
        return park_regular_monthly_price;
    }
    /**
     * @param park_regular_monthly_price the park_regular_monthly_price to set
     */
    public void setPark_regular_monthly_price(String park_regular_monthly_price) {
        this.park_regular_monthly_price = park_regular_monthly_price;
    }
    /**
     * @return the park_sun_hol_biz_hour_cd
     */
    public String getPark_sun_hol_biz_hour_cd() {
        return park_sun_hol_biz_hour_cd;
    }
    /**
     * @param park_sun_hol_biz_hour_cd the park_sun_hol_biz_hour_cd to set
     */
    public void setPark_sun_hol_biz_hour_cd(String park_sun_hol_biz_hour_cd) {
        this.park_sun_hol_biz_hour_cd = park_sun_hol_biz_hour_cd;
    }
    /**
     * @return the park_shape_ct
     */
    public String getPark_shape_ct() {
        return park_shape_ct;
    }
    /**
     * @param park_shape_ct the park_shape_ct to set
     */
    public void setPark_shape_ct(String park_shape_ct) {
        this.park_shape_ct = park_shape_ct;
    }
    /**
     * @return the discountticket_use_ny
     */
    public String getDiscountticket_use_ny() {
        return discountticket_use_ny;
    }
    /**
     * @param discountticket_use_ny the discountticket_use_ny to set
     */
    public void setDiscountticket_use_ny(String discountticket_use_ny) {
        this.discountticket_use_ny = discountticket_use_ny;
    }
    /**
     * @return the incarpayment_use_yn
     */
    public String getIncarpayment_use_yn() {
        return incarpayment_use_yn;
    }
    /**
     * @param incarpayment_use_yn the incarpayment_use_yn to set
     */
    public void setIncarpayment_use_yn(String incarpayment_use_yn) {
        this.incarpayment_use_yn = incarpayment_use_yn;
    }
    /**
     * @return the park_echarge_ny
     */
    public String getPark_echarge_ny() {
        return park_echarge_ny;
    }
    /**
     * @param park_echarge_ny the park_echarge_ny to set
     */
    public void setPark_echarge_ny(String park_echarge_ny) {
        this.park_echarge_ny = park_echarge_ny;
    }
    /**
     * @return the park_echarge_list
     */
    public List<ParkEchargeListVO> getPark_echarge_list() {
        return park_echarge_list;
    }
    /**
     * @param park_echarge_list the park_echarge_list to set
     */
    public void setPark_echarge_list(List<ParkEchargeListVO> park_echarge_list) {
        this.park_echarge_list = park_echarge_list;
    }
    /**
     * @return the park_echarge_list_json
     */
    public String getPark_echarge_list_json() {
        return park_echarge_list_json;
    }
    /**
     * @param park_echarge_list_json the park_echarge_list_json to set
     */
    public void setPark_echarge_list_json(String park_echarge_list_json) {
        this.park_echarge_list_json = park_echarge_list_json;
    }
       

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
   
}
