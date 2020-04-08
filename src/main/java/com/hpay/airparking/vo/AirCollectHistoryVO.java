package com.hpay.airparking.vo;

             
public class AirCollectHistoryVO {


    private String workDate;
    private String workDateSeq;
    private String src;      //주차장 CP(A:파킹클라우드)
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
