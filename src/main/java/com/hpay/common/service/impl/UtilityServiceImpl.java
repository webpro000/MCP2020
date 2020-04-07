package com.hpay.common.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import javax.xml.bind.DatatypeConverter;

import able.com.service.HService;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import com.hpay.common.service.UtilityService;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : UtilityServiceImpl.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 3.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =======    ===========================
 *  2019. 05. 03.       O1484       최초 생성
 *  2019. 07. 18.       O1484       SaveFile 수정            
 * </pre>
 */
@Service("utilityService")
public class UtilityServiceImpl extends HService implements UtilityService {

    @Override
    public String sendJson(String targetURI, String json) throws Exception{
        logger.info("target : "+targetURI);
        
        HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        HttpPost postRequest = new HttpPost(targetURI); 
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-Type", "application/json");

        StringEntity entity =new StringEntity(json, "UTF-8");
        postRequest.setEntity(entity);
        
        HttpResponse response = client.execute(postRequest);

        //Response 출력
        if (response.getStatusLine().getStatusCode() == 200) {
            ResponseHandler<String> handler = new BasicResponseHandler();
            String ReturnData = handler.handleResponse(response);
            logger.info("SendJson Success : "+ReturnData);
            return ReturnData;
        } else {
            ResponseHandler<String> handler = new BasicResponseHandler();
            String ReturnData = handler.handleResponse(response);
            logger.info("SendJson Fail : "+ReturnData);
            int returnCode= response.getStatusLine().getStatusCode();
            return returnCode+"";
        }
    }
    
    @Override
    public Boolean SaveFileBinary(String physicalPath, String fileName, String[] arrValue) throws Exception {
        StringBuilder valueSum=new StringBuilder();
        for (int i=0; i<arrValue.length; i++){
            if (i==0){
                valueSum.append(arrValue[i]);
            } else {
                valueSum.append("\r\n"+arrValue[i]);
            }
        }
        byte[] value=valueSum.toString().getBytes("UTF-8");
        this.SaveFile(physicalPath, fileName, value);
        return true;        
    }

    @Override
    public Boolean SaveFile(String physicalPath, String fileName, String value) throws Exception {
        FileOutputStream fos = new FileOutputStream(physicalPath+"/"+fileName);
        fos.write(value.getBytes());
        fos.close();
        return true;
    }

    @Override
    public Boolean SaveFile(String physicalPath, String fileName, byte[] value) throws Exception {
        FileOutputStream fos = new FileOutputStream(physicalPath+"/"+fileName);
        fos.write(value);
        fos.close();
        return true;
    }

    @Override
    public byte[] ZipCompress(byte[] data) throws Exception {
        Deflater compressor = new Deflater();
        compressor.setInput(data);
        compressor.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);

        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
          int count = compressor.deflate(buf);
          bos.write(buf, 0, count);
        }
        bos.close();
        byte[] compressedData = bos.toByteArray();
        return compressedData;
    }

    @Override
    public byte[] ZipUncompress(byte[] data) throws Exception {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[204800];
        while(!inflater.finished()) 
        {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    @Override
    public byte[] GzipCompress(byte[] data) throws Exception {        
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        GZIPOutputStream gzipOS = new GZIPOutputStream(bos);
        gzipOS.write(data);
        gzipOS.close();
        byte[] result = bos.toByteArray();
        return result;
    }

    @Override
    public byte[] GzipUncompress(byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPInputStream gzipIS = new GZIPInputStream(bis);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzipIS.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] result = bos.toByteArray();
        return result;
    }
    
    @Override
    public String encodeBase64(byte[] data) throws Exception {
        return DatatypeConverter.printBase64Binary(data);
    }
    @Override
    public byte[] decodeBase64(String data) throws Exception{
        return DatatypeConverter.parseBase64Binary(data);
    }
}
