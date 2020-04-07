package com.hpay.icps.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : StoreDeletedVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 7. 1.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 7. 1.     O1484     	최초 생성
 * </pre>
 */

public class StoreDeletedVO {
    private int hpay_store_delete_package_seq;    
    @JsonProperty("poiId")
    private String poi_id;
    private String store_id;
    public int getHpay_store_delete_package_seq() {
        return hpay_store_delete_package_seq;
    }
    public void setHpay_store_delete_package_seq(int hpay_store_delete_package_seq) {
        this.hpay_store_delete_package_seq = hpay_store_delete_package_seq;
    }
    public String getPoi_id() {
        return poi_id;
    }
    public void setPoi_id(String poi_id) {
        this.poi_id = poi_id;
    }
    public String getStore_id() {
        return store_id;
    }
    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
