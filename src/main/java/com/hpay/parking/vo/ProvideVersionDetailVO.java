package com.hpay.parking.vo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @ClassName   : ProvideVersionDetailVO.java
 * @Description : 제공 버전 상세 데이터 객체 클래스
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
public class ProvideVersionDetailVO {

    @NotEmpty
    private String provideDate;
    @NotEmpty
    private String provideSeq;
    @NotEmpty
    private String provideService;
    private String loadSrc;
    private String loadDate;
    private String loadSeq;
    /**
     * @return the provideDate
     */
    public String getProvideDate() {
        return provideDate;
    }
    /**
     * @param provideDate the provideDate to set
     */
    public void setProvideDate(String provideDate) {
        this.provideDate = provideDate;
    }
    /**
     * @return the provideSeq
     */
    public String getProvideSeq() {
        return provideSeq;
    }
    /**
     * @param provideSeq the provideSeq to set
     */
    public void setProvideSeq(String provideSeq) {
        this.provideSeq = provideSeq;
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
     * @return the loadSrc
     */
    public String getLoadSrc() {
        return loadSrc;
    }
    /**
     * @param loadSrc the loadSrc to set
     */
    public void setLoadSrc(String loadSrc) {
        this.loadSrc = loadSrc;
    }
    /**
     * @return the loadDate
     */
    public String getLoadDate() {
        return loadDate;
    }
    /**
     * @param loadDate the loadDate to set
     */
    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }
    /**
     * @return the loadSeq
     */
    public String getLoadSeq() {
        return loadSeq;
    }
    /**
     * @param loadSeq the loadSeq to set
     */
    public void setLoadSeq(String loadSeq) {
        this.loadSeq = loadSeq;
    }
   
    

}
