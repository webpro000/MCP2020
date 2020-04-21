package com.hpay.dincd.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : Item.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 20.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 20.     webpro000     	최초 생성
 * </pre>
 */

@XmlAccessorType(XmlAccessType.NONE)
public class Item_additional {
    
        
    @XmlElement
    float latitude = 0;
    @XmlElement
    float avgRating = 0;
    @XmlElement
    float aiScore = 0;
    @XmlElement
    String openHour = null;
    @XmlElement
    String repPhoto = null;
    @XmlElement
    String menuPhoto = null;
    @XmlElement
    String area = null;
    @XmlElement
    int rLikeCount = 0;
    @XmlElement
    String repFoodKeyword = null;
    @XmlElement
    String repKeyword = null;
    @XmlElement
    float tasteRating = 0;
    @XmlElement
    float priceRating = 0;
    @XmlElement
    float serviceRating = 0;
    @XmlElement
    String visitPurpose = null;
    @XmlElement
    String facility = null;
    @XmlElement
    String atmosphere = null;
    @XmlElement
    String regdt = null;
    @XmlElement
    String uptdt = null;
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
