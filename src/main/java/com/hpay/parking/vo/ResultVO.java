package com.hpay.parking.vo;

/**
 * @ClassName   : ResultVO.java
 * @Description : 결과 코드와 메시지만 리턴하는 데이터 객체 클래스
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
public class ResultVO {

    
    private String interfaceCode;
    private String resultCode ;
    private String resultMessage;
    /**
     * @return the interfaceCode
     */
    public String getInterfaceCode() {
        return interfaceCode;
    }
    /**
     * @param interfaceCode the interfaceCode to set
     */
    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }
    /**
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }
    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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
