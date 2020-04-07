package com.hpay.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import able.com.service.HService;
import able.com.service.prop.PropertyService;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.common.service.ConnCertService;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ConnCertServiceImpl.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 3.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 3.     O1484     	최초 생성
 * </pre>
 */

@Service("connCertService")
public class ConnCertServiceImpl extends HService implements ConnCertService {
    @Autowired
    PropertyService propertyService;

    @Override
    public Map<String, Object> validationCertKey(String certKey, String interfaceCode) {
        //logger.info(propertyService.getString("certification.validationUri"));
        ///*
        HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        HttpPost postRequest = new HttpPost(propertyService.getString("certification.validationUri")); 
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-Type", "application/json");
        postRequest.addHeader("certKey", certKey); 
        try {
            StringEntity entity =new StringEntity("{\"interfaceCode\":\"hpayCertSearch\", \"interfaceCodeTarget\" : \""+interfaceCode+"\"}");
            postRequest.setEntity(entity);
            HttpResponse response = client.execute(postRequest);
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String returnValue= handler.handleResponse(response);
                logger.info(returnValue);
                ObjectMapper mapper = new ObjectMapper(); 

                Map<String, Object> map = mapper.readValue(returnValue, new TypeReference<Map<String, String>>(){}); 
                return map;
                //return map.get("resultCode").toString();
            } else {
                Map<String, Object> map = new HashMap<String, Object>(); 
                map.put("resultCode", "400");
                return map;
            }            
        } catch(Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>(); 
            map.put("resultCode", "400");
            return map;
        }        
    }
}