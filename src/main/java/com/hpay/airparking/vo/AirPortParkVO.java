package com.hpay.airparking.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.simple.JSONObject;

import com.hpay.airparking.vo.airport.Body;
import com.hpay.airparking.vo.airport.Header;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : airportParkData.java
 * @Description : 클래스 설명을 기술합니다.
 * @author waveM
 * @since 2019. 10. 7.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 10. 7.     waveM      최초 생성
 * </pre>
 */
@XmlRootElement(name="response")
public class AirPortParkVO {

    private String ParkSeq;
    private String src;
    private String park_category;
    private String status;
    private String parkingstatus;
    private String update_date;
    private String insert_date;
    private String parkingstatus_eng;
    private String parkingsresult;    

    public AirPortParkVO(){
        super();
    }

    
    @XmlElement(name="header")
    private Header header;
    
    public Header getHeader() {
        return header;
    }
    
    @XmlElement(name="body")
    private Body body;


    public Body getBody() {
        return body;
    }

    
    
    /**
     * Statements
     *
     * @param resultCode
     */
    public void setresultCode(String resultCode) {
        // TODO Auto-generated method stub
        
    }
    public void setParkSeq(String ParkSeq) {
        this.ParkSeq = ParkSeq;       
    }
    public void setsrc(String src) {
        this.src = src;       
    }
    public void setpark_category(String park_category) {
        this.park_category = park_category;       
    }
    public void setstatus(String status) {
        this.status = status;       
    }
    public void setparkingstatus(String parkingstatus) {
        this.parkingstatus = parkingstatus;       
    }
    public void setupdate_date(String update_date) {
        this.update_date = update_date;       
    }
    public void setinsert_date(String insert_date) {
        this.insert_date = insert_date;       
    }
    public void setparkingstatus_eng(String parkingstatus_eng) {
        this.parkingstatus_eng = parkingstatus_eng;       
    }
    
    public void setparkingsresult(String parkingsresult) {
        this.parkingsresult = parkingsresult;       
    }
    
    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }
    /**
     * @return the park_category
     */
    public String getPark_category() {
        return park_category;
    }
    
    /**
     * @return the park_seq
     */
    public String getPark_seq() {
        return ParkSeq;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @return the parkingStatus
     */
    public String getParkingStatus() {
        return parkingstatus;
    }    
    /**
     * @return the parkingStatus_eng
     */
    public String getParkingStatus_eng() {
        return parkingstatus_eng;
    }
    /**
     * @return the update_date
     */
    public String getUpdate_date() {
        return update_date;
    }    
    
    public String getparkingsresult() {
        return parkingsresult;
    }    
    
    
    
    
    @SuppressWarnings("unchecked")
    public JSONObject toJSON(){
        JSONObject result = new JSONObject();
        
        result.put("src", src);
        result.put("park_category", park_category);
        result.put("park_seq", ParkSeq);
        result.put("status", status);
        result.put("parkingStatus", parkingstatus);
        result.put("update_date", update_date);
        result.put("parkingStatus_eng", parkingstatus_eng);
        
        return result;
    }
    
}
