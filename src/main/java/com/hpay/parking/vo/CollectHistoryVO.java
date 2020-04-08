package com.hpay.parking.vo;

             
/**
 * @ClassName   : CollectHistoryVO.java
 * @Description : 주차장 수집 히스토리 데이터 객체 클래스
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
public class CollectHistoryVO {


    private String workDate;
    private String workDateSeq;
    private String src;      //주차장 CP(PKC:파킹클라우드)
    private String result;
    private String resultMessage;
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
     * @return the result
     */
    public String getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
    /**
     * @return the resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }
    /**
     * @param resultMessage the resultMessage to set
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    
    

}
