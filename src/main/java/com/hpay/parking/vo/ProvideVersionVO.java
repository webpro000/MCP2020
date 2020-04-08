package com.hpay.parking.vo;

/**
 * @ClassName   : ProvideVersionVO.java
 * @Description : 제공버전 데이터 객체 클래스
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
public class ProvideVersionVO {


    //@NotEmpty
    private String provideDate;
    //@NotEmpty
    private String provideSeq;
    //@NotEmpty
    private String provideService;
    //@NotEmpty
    private String provideFullVersion;
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
     * @return the provideFullVersion
     */
    public String getProvideFullVersion() {
        return provideFullVersion;
    }
    /**
     * @param provideFullVersion the provideFullVersion to set
     */
    public void setProvideFullVersion(String provideFullVersion) {
        this.provideFullVersion = provideFullVersion;
    }
    
    


}
