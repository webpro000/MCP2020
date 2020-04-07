package com.hpay.icps.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import able.com.service.HService;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.icps.service.HpayStoreAddedService;
import com.hpay.icps.service.dao.HpayStoreAddedMDAO;
import com.hpay.icps.vo.HpayStoreAddedVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayStoreAddedServiceImpl.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 9.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 9.     O1484     	최초 생성
 * </pre>
 */

@Service("hpayStoreAddedService")
public class HpayStoreAddedServiceImpl extends HService implements HpayStoreAddedService {
    @Resource(name = "hpayStoreAddedMDAO")
    private HpayStoreAddedMDAO hpayStoreAddedMDAO;


    public int setData(List<HpayStoreAddedVO> arrVOHpayStoreAdded)throws Exception{
        hpayStoreAddedMDAO.insertArrHpayStoreAdded(arrVOHpayStoreAdded);
        return 1;
    }
    
    public List<HpayStoreAddedVO> parseJsonToVO(String json)throws Exception{
        ObjectMapper mapper = new ObjectMapper();        
        JsonNode node = mapper.readTree(json);
        List<HpayStoreAddedVO> arrVOHpayStoreAdded=new ArrayList<HpayStoreAddedVO>();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd"); 
        for (int i=0; i<node.get("updStoreList").size(); i++){
            JsonNode row=node.get("updStoreList").get(i);
            HpayStoreAddedVO temp=new HpayStoreAddedVO(dt.parse(row.get("reqDate").textValue()), row.get("poiId").textValue(), row.get("storeId").textValue(), dt.parse(row.get("startDate").textValue()));
            arrVOHpayStoreAdded.add(temp);
        }
        return arrVOHpayStoreAdded;
    }
}
