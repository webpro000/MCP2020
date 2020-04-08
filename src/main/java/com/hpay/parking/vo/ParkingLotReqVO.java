package com.hpay.parking.vo;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @ClassName   : ParkingLotReqVO.java
 * @Description : 주차장 Dynamic 재차정보 요청 데이터 객체 클래스
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
public class ParkingLotReqVO {

    @NotEmpty
    @Length(min = 1, max = 30)
    private String interfaceCode;
    @NotEmpty
    @Length(min = 1, max = 1)
    private String infoListType;
    private List<PoiIdVO> poiList;
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
     * @return the infoListType
     */
    public String getInfoListType() {
        return infoListType;
    }
    /**
     * @param infoListType the infoListType to set
     */
    public void setInfoListType(String infoListType) {
        this.infoListType = infoListType;
    }
    /**
     * @return the poiList
     */
    public List<PoiIdVO> getPoiList() {
        return poiList;
    }
    /**
     * @param poiList the poiList to set
     */
    public void setPoiList(List<PoiIdVO> poiList) {
        this.poiList = poiList;
    }
    
    

    
}
