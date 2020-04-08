package com.hpay.airparking.vo.airport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : Item.java
 * @Description : 클래스 설명을 기술합니다.
 * @author waveM
 * @since 2019. 10. 4.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 10. 4.     waveM      최초 생성
 * </pre>
 */

@XmlRootElement(name="item")
public class Item {
    //공항 영문 명
    @XmlElement(name="aprEng")
    private String aprEng;
    
    //공항 국문명
    @XmlElement(name="aprKor")
    private String aprKor;
    
    //공항 주차장 이름 ex)국내선 제1주차장
    @XmlElement(name="parkingAirportCodeName")
    private String parkingAirportCodeName;
    
    //총 주차면적
    @XmlElement(name="parkingFullSpace")
    private String parkingFullSpace;
    
    //요청일짜
    @XmlElement(name="parkingGetdate")
    private String parkingGetdate;
    
    //요청시간
    @XmlElement(name="parkingGettime")
    private String parkingGettime;
    
    //주차장 들어오는 차량
    @XmlElement(name="parkingIincnt")
    private String parkingIincnt;
    
    //주차장 나가는 차량
    @XmlElement(name="parkingIoutcnt")
    private String parkingIoutcnt;
    
    //현재 주차된 차량 수
    @XmlElement(name="parkingIstay")
    private String parkingIstay;

    @XmlElement(name="datetm")
    private String datetm;
    @XmlElement(name="floor")
    private String floor;
    @XmlElement(name="parking")
    private String parking;
    @XmlElement(name="parkingarea")
    private String parkingarea;

    
    public String getDatetm() {
        return datetm;
    }

    public String getFloor() {
        return floor;
    }

    public String getParking() {
        return parking;
    }

    public String getParkingarea() {
        return parkingarea;
    }
    
    
    public String getAprEng() {
        return aprEng;
    }

    public String getAprKor() {
        return aprKor;
    }

    public String getParkingAirportCodeName() {
        return parkingAirportCodeName;
    }

    public String getParkingFullSpace() {
        return parkingFullSpace;
    }

    public String getParkingGetdate() {
        return parkingGetdate;
    }

    public String getParkingGettime() {
        return parkingGettime;
    }

    public String getParkingIincnt() {
        return parkingIincnt;
    }

    public String getParkingIoutcnt() {
        return parkingIoutcnt;
    }

    public String getParkingIstay() {
        return parkingIstay;
    }
    
    
}
