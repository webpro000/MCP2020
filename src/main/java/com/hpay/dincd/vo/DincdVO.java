package com.hpay.dincd.vo;

import java.sql.Date;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DincdVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author 김진우
 * @since 2020. 4. 13.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 13.     김진우     	최초 생성
 * </pre>
 */

public class DincdVO {
    
    int businessId = 0;
    String name = null;
    String address = null;
    String telephone =  null;
    float longitude = 0;
    float latitude = 0;
    float avgRating = 0;
    float aiScore = 0;
    String openHour = null;
    String repPhoto = null;
    String menuPhoto = null;
    String area = null;
    int rLikeCount = 0;
    String repFoodKeyword = null;
    String repKeyword = null;
    float tasteRating = 0;
    float priceRating = 0;
    float serviceRating = 0;
    String visitPurpose = null;
    String facility = null;
    String atmosphere = null;
    String regdt = null;
    String uptdt = null;
    /**
     * @return the businessId
     */
    public int getBusinessId() {
        return businessId;
    }
    /**
     * @param businessId the businessId to set
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
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
    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    /**
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    /**
     * @return the avgRating
     */
    public float getAvgRating() {
        return avgRating;
    }
    /**
     * @param avgRating the avgRating to set
     */
    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }
    /**
     * @return the aiScore
     */
    public float getAiScore() {
        return aiScore;
    }
    /**
     * @param aiScore the aiScore to set
     */
    public void setAiScore(float aiScore) {
        this.aiScore = aiScore;
    }
    /**
     * @return the openHour
     */
    public String getOpenHour() {
        return openHour;
    }
    /**
     * @param openHour the openHour to set
     */
    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }
    /**
     * @return the repPhoto
     */
    public String getRepPhoto() {
        return repPhoto;
    }
    /**
     * @param repPhoto the repPhoto to set
     */
    public void setRepPhoto(String repPhoto) {
        this.repPhoto = repPhoto;
    }
    /**
     * @return the menuPhoto
     */
    public String getMenuPhoto() {
        return menuPhoto;
    }
    /**
     * @param menuPhoto the menuPhoto to set
     */
    public void setMenuPhoto(String menuPhoto) {
        this.menuPhoto = menuPhoto;
    }
    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }
    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }
    /**
     * @return the rLikeCount
     */
    public int getrLikeCount() {
        return rLikeCount;
    }
    /**
     * @param rLikeCount the rLikeCount to set
     */
    public void setrLikeCount(int rLikeCount) {
        this.rLikeCount = rLikeCount;
    }
    /**
     * @return the repFoodKeyword
     */
    public String getRepFoodKeyword() {
        return repFoodKeyword;
    }
    /**
     * @param repFoodKeyword the repFoodKeyword to set
     */
    public void setRepFoodKeyword(String repFoodKeyword) {
        this.repFoodKeyword = repFoodKeyword;
    }
    /**
     * @return the repKeyword
     */
    public String getRepKeyword() {
        return repKeyword;
    }
    /**
     * @param repKeyword the repKeyword to set
     */
    public void setRepKeyword(String repKeyword) {
        this.repKeyword = repKeyword;
    }
    /**
     * @return the tasteRating
     */
    public float getTasteRating() {
        return tasteRating;
    }
    /**
     * @param tasteRating the tasteRating to set
     */
    public void setTasteRating(float tasteRating) {
        this.tasteRating = tasteRating;
    }
    /**
     * @return the priceRating
     */
    public float getPriceRating() {
        return priceRating;
    }
    /**
     * @param priceRating the priceRating to set
     */
    public void setPriceRating(float priceRating) {
        this.priceRating = priceRating;
    }
    /**
     * @return the serviceRating
     */
    public float getServiceRating() {
        return serviceRating;
    }
    /**
     * @param serviceRating the serviceRating to set
     */
    public void setServiceRating(float serviceRating) {
        this.serviceRating = serviceRating;
    }
    /**
     * @return the visitPurpose
     */
    public String getVisitPurpose() {
        return visitPurpose;
    }
    /**
     * @param visitPurpose the visitPurpose to set
     */
    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }
    /**
     * @return the facility
     */
    public String getFacility() {
        return facility;
    }
    /**
     * @param facility the facility to set
     */
    public void setFacility(String facility) {
        this.facility = facility;
    }
    /**
     * @return the atmosphere
     */
    public String getAtmosphere() {
        return atmosphere;
    }
    /**
     * @param atmosphere the atmosphere to set
     */
    public void setAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
    }
    /**
     * @return the regdt
     */
    public String getRegdt() {
        return regdt;
    }
    /**
     * @param regdt the regdt to set
     */
    public void setRegdt(String regdt) {
        this.regdt = regdt;
    }
    /**
     * @return the uptdt
     */
    public String getUptdt() {
        return uptdt;
    }
    /**
     * @param uptdt the uptdt to set
     */
    public void setUptdt(String uptdt) {
        this.uptdt = uptdt;
    }
   
    
    
}
