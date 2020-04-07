package com.hpay.icps.service.dao;

import java.util.List;

import able.com.mybatis.Mapper;

import org.apache.ibatis.annotations.Param;

import com.hpay.icps.vo.StoreDeletedPackageVO;
import com.hpay.icps.vo.StoreDeletedVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : DeleteMDAO.java
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

@Mapper("storeDeletedMDAO")
public interface StoreDeletedMDAO {
    List<StoreDeletedVO> searchStoreDelete(String data_version)throws Exception;
    
    String selectTodayLastSeq(@Param("interface_code") String interface_code,  @Param("work_date") String work_date) throws Exception;
    int insertStoreDeletedPackage(StoreDeletedPackageVO vo) throws Exception;
    void updateStoreDeletedPackage(StoreDeletedPackageVO vo) throws Exception;
    void insertArrStoreDeleted(List<StoreDeletedVO> arrVOStoreDelete) throws Exception;
    String selectRelatedDeltaVersion(@Param("req_delete_store_date") String reqDeleteStoreDate) throws Exception;
    void updateStoreDelta_DeletedDate(@Param("date_deleted") String date_deleted, @Param("poi_id") String poi_id, @Param("data_version") String data_version) throws Exception;
    StoreDeletedPackageVO selectLastDeletedBinary() throws Exception;
    StoreDeletedPackageVO selectLastInitiatedDeletedPackage(@Param("data_version_delta") String data_version_delta) throws Exception;
    StoreDeletedPackageVO selectTodayIniticatedBinaryPackage() throws Exception;
    List<StoreDeletedVO> selectStoreDeleted_byId(@Param("hpay_store_delete_package_seq") int hpay_store_delete_package_seq) throws Exception;
    

}
