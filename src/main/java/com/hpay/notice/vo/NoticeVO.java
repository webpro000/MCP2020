package com.hpay.notice.vo;

import java.sql.Date;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : NotiveVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 7.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 7.     webpro000       최초 생성
 *  2020. 4. 22.    webpro000       공지사항 CRUD
 * </pre>
 */
public class NoticeVO {
    private Integer notice_seq;
    private String title;
    private String contents;
    private Integer gtype=0;
    private String regid = "system";    // 기본 값.
    private Date regdt;
    private Date uptdt;
    private String useyn;
    
    
//    /**
//     * Statements
//     *
//     */
//    public NoticeVO() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
    /**
     * @return the notice_seq
     */
    public Integer getNotice_seq() {
        return notice_seq;
    }
    /**
     * @param notice_seq the notice_seq to set
     */
    public void setNotice_seq(Integer notice_seq) {
        this.notice_seq = notice_seq;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the contents
     */
    public String getContents() {
        return contents;
    }
    /**
     * @param contents the contents to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
    /**
     * @return the regid
     */
    public String getRegid() {
        return regid;
    }
    /**
     * @param regid the regid to set
     */
    public void setRegid(String regid) {
        this.regid = regid;
    }
    /**
     * @return the regdt
     */
    public Date getRegdt() {
        return regdt;
    }
    /**
     * @param regdt the regdt to set
     */
    public void setRegdt(Date regdt) {
        this.regdt = regdt;
    }
    /**
     * @return the uptdt
     */
    public Date getUptdt() {
        return uptdt;
    }
    /**
     * @param uptdt the uptdt to set
     */
    public void setUptdt(Date uptdt) {
        this.uptdt = uptdt;
    }
    /**
     * @return the useyn
     */
    public String getUseyn() {
        return useyn;
    }
    /**
     * @param useyn the useyn to set
     */
    public void setUseyn(String useyn) {
        this.useyn = useyn;
    }
    /**
     * @return the gtype
     */
    public Integer getGtype() {
        return gtype;
    }
    /**
     * @param gtype the gtype to set
     */
    public void setGtype(Integer gtype) {
        this.gtype = gtype;
    }
    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "NoticeVO [notice_seq=" + notice_seq + ", title=" + title + ", contents=" + contents + ", gtype=" + gtype
                + ", regid=" + regid + ", regdt=" + regdt + ", uptdt=" + uptdt + ", useyn=" + useyn + "]";
    }
    
    
}
