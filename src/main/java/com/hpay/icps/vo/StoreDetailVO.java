package com.hpay.icps.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayStoreDeltaVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 13.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 13.     O1484      최초 생성
 * </pre>
 */

public class StoreDetailVO {
    private String poiId;
    private String poiCode;
    private String poiCodeDesc;
    private String poiCodeDesc_eng;
    private String fName;
    private String fName_eng;
    private String storeName;
    private String storeName_eng;
    private String stationPhone;
    private String src;
    private String parkCategory;
    private String address;
    private String address_eng;
    private String sanBun;
    private String primaryBun;
    private String secondaryBun;
    private String addressStr;
    private String addressStr_eng;
    private String lat;
    private String lon;
    private String storeId;
    private String referId;
    private String selfType;

    public String getPoiCodeDesc_eng() {
        return poiCodeDesc_eng;
    }

    public void setPoiCodeDesc_eng(String poiCodeDesc_eng) {
        this.poiCodeDesc_eng = poiCodeDesc_eng;
    }

    public String getfName_eng() {
        return fName_eng;
    }

    public void setfName_eng(String fName_eng) {
        this.fName_eng = fName_eng;
    }

    public String getStoreName_eng() {
        return storeName_eng;
    }
    public void setStoreName_eng(String storeName_eng) {
        this.storeName_eng = storeName_eng;
    }

    public String getAddress_eng() {
        return address_eng;
    }

    public void setAddress_eng(String address_eng) {
        this.address_eng = address_eng;
    }

    public String getAddressStr_eng() {
        return addressStr_eng;
    }

    public void setAddressStr_eng(String addressStr_eng) {
        this.addressStr_eng = addressStr_eng;
    }
    
    @JsonProperty("7LvParcelId")
    private String parcelId7lv;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getParkCategory() {
        return parkCategory;
    }

    public void setParkCategory(String parkCategory) {
        this.parkCategory = parkCategory;
    }

    public String getOriginJson() {
        return originJson;
    }

    public void setOriginJson(String originJson) {
        this.originJson = originJson;
    }

    private String dataVersion;
    private String originJson;

    /*
    public String[] getOrderForJson(){
        String[] returnValue=new String[]{
                "poiCode", "poiCodeDesc", "fName", "storeName", "stationPhone"
                , "address", "sanBun", "primaryBun", "secondaryBun", "addressStr"
                , "lat", "lon", "storeId", "referId", "selfType"
                , "premium_gasoline", "park_biz_hour_cd", "park_biz_time", "park_sat_biz_time", "park_sun_hol_biz_time"
                , "park_interval_free_ny", "park_basic_interval_minute", "park_basic_interval_price", "park_additional_interval_minute", "park_additional_interval_price"
                , "park_oneday_price", "park_regular_monthly_price", "park_sat_biz_hour_cd", "park_sun_hol_biz_hour_cd", "fure_OperationInfoList"
                , "7LvParcelId"};
        return returnValue;
    }
    */
    public String getByColumnName(String columnName){
        String targetData=null;
        switch(columnName){
            case "poiId":
                targetData=poiId;
                break; 
            case "poiCode":
                targetData=poiCode;
                break; 
            case "poiCodeDesc":
                targetData=poiCodeDesc;
                break; 
            case "fName":
                targetData=fName;
                break; 
            case "storeName":
                targetData=storeName;
                break; 
            case "stationPhone":
                targetData=stationPhone;
                break; 
            case "address":
                targetData=address;
                break; 
            case "sanBun":
                targetData=sanBun;
                break; 
            case "primaryBun":
                targetData=primaryBun;
                break; 
            case "secondaryBun":
                targetData=secondaryBun;
                break; 
            case "addressStr":
                targetData=addressStr;
                break; 
            case "lat":
                targetData=lat;
                break; 
            case "lon":
                targetData=lon;
                break; 
            case "storeId":
                targetData=storeId;
                break; 
            case "referId":
                targetData=referId;
                break; 
            case "selfType":
                targetData=selfType;
                break; 
            
            case "7LvParcelId":
                targetData=parcelId7lv;
                break; 
            case "poiCodeDesc_eng":
                targetData=poiCodeDesc_eng;
                break; 
            case "fName_eng":
                targetData=fName_eng;
                break; 
            case "storeName_eng":
                targetData=storeName_eng;
                break; 
            case "address_eng":
                targetData=address_eng;
                break; 
            case "addressStr_eng":
                targetData=addressStr_eng;
                break; 
            case "src":
                targetData=src;
                break; 
            case "park_category":
                targetData=parkCategory;
                break;
        }
        return targetData;
    }
    

    public String getPoiId() {
        if (poiId==null){
            return "";
        } else {
            return poiId;            
        }
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getPoiCode() {
        if (poiCode==null){
            return "";
        } else {
            return poiCode;            
        }
    }

    public void setPoiCode(String poiCode) {
        this.poiCode = poiCode;
    }


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSanBun() {
        return sanBun;
    }

    public void setSanBun(String sanBun) {
        this.sanBun = sanBun;
    }

    public String getPrimaryBun() {
        return primaryBun;
    }

    public void setPrimaryBun(String primaryBun) {
        this.primaryBun = primaryBun;
    }

    public String getSecondaryBun() {
        return secondaryBun;
    }

    public void setSecondaryBun(String secondaryBun) {
        this.secondaryBun = secondaryBun;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public String getSelfType() {
        return selfType;
    }

    public void setSelfType(String selfType) {
        this.selfType = selfType;
    }

    public String getParcelId7lv() {
        return parcelId7lv;
    }

    public void setParcelId7lv(String parcelId7lv) {
        this.parcelId7lv = parcelId7lv;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getPoiCodeDesc() {
        return poiCodeDesc;
    }

    public void setPoiCodeDesc(String poiCodeDesc) {
        this.poiCodeDesc = poiCodeDesc;
    }
    
    public String getPark_category() {
        if (parkCategory==null){
            return "";
        } else {
            return parkCategory;            
        }
    }

    public void setPark_category(String park_category) {
        this.parkCategory = park_category;
    }

}