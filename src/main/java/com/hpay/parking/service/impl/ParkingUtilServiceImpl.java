package com.hpay.parking.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import able.com.service.HService;
import able.com.service.prop.PropertyService;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpay.common.service.ConnRedisService;
import com.hpay.parking.service.ParkingUtilService;


/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ParkingUtilServiceImpl.java
 * @Description : 주차장 관련 공통사용함수 구현 클래스
 * @author o1488
 * @since 2019. 5. 21.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 21.     o1488     	최초 생성
 * </pre>
 */
@Service("parkingUtilService")
public class ParkingUtilServiceImpl extends HService implements ParkingUtilService {

    @Autowired
    PropertyService propertyService;
    
    @Resource
    ConnRedisService connRedisService;
    
    /**
     * CP IP저장
     * @param cpAdress
     * @throws Exception
     */
    @Override
    public void setCpAdressRedis(String tempUrl) throws Exception {

        int dbNum = propertyService.getInt("DB_3");
        String keyAdress = propertyService.getString("parking.cp.parkingcloud.address.key");

        //전체 지우기(Redis)        
        String keysPattern =keyAdress+"*";        
        connRedisService.delAll(dbNum,  keysPattern );
        
        //set
        InetAddress[] inetAddress = InetAddress.getAllByName(tempUrl);
        List<String[]> arrList= new ArrayList<String[]>(); 
        for(int i=0; i<inetAddress.length; i++) {
            logger.info("inetAddress[i].getHostAddress()==>"+inetAddress[i].getHostAddress());
            String[] arrStr = new String[2];
            arrStr[0] = keyAdress+i;
            arrStr[1] = inetAddress[i].getHostAddress();
            arrList.add(arrStr);
        }
        
        connRedisService.setHash(dbNum, keyAdress, arrList);
        
    }
    public  Map<String, String> getCpAdressRedis(String keyAdress) throws Exception {

        int dbNum = propertyService.getInt("DB_3");

        //전체 가져오기
        Map<String, String> addrMap = connRedisService.getHash(dbNum, keyAdress);
        
        return addrMap;
    }
    
    /**
     * URL 서버접속 가능 여부 체크
     * @param strUrl
     * @return
     * @throws Exception
     */
    @Override
    public List<String> addressAliveCheck(String strUrl) throws Exception {
        
        /////////////////////////////////////////////
        //TEST:Because "Name or service not known" -- host파일에 등록이 되어있어야 찾음
        //접속 전에 체크
        String tempUrl = propertyService.getString("parking.cp.parkingcloud.url");
        List<String> returnUrl = new ArrayList<String>();
        InetAddress inetaddr = null;
        try {
           inetaddr = InetAddress.getByName(tempUrl);
           logger.info("inetaddr.getHostAddress() = " + inetaddr.getHostAddress());
           /*
           if(!StringUtils.isEmpty(inetaddr.getHostAddress())) {
               //REDIS에 IP 전부 저장
               logger.info("IP SAVE inetaddr.getHostName() = " + inetaddr.getHostName());
               setCpAdressRedis(tempUrl);
           }
           */
           returnUrl.add(strUrl);
        
        } catch (UnknownHostException e) {
            logger.info("##UnknownHostException:"+e.getMessage());
            e.printStackTrace();
            
            String keyAdress = propertyService.getString("parking.cp.parkingcloud.ip1");
            strUrl = strUrl.replaceFirst(tempUrl, keyAdress);
            returnUrl.add(strUrl);
            /*
            String keyAdress = propertyService.getString("parking.cp.parkingcloud.address.key");
            //REDIS에서 IP 가져오기
            Map<String, String> addrMap  = getCpAdressRedis(keyAdress);
            for(int i=0; i<addrMap.size(); i++) {
                String ipAddress = addrMap.get(keyAdress+i);
                logger.info("###keyAdress+i ==>"+keyAdress+i);
                logger.info("###ip1 ==>"+ipAddress);
                strUrl = strUrl.replaceFirst(tempUrl, ipAddress);
                returnUrl.add(strUrl);
            }
            */
        }
        
        return returnUrl;
     }
    
    /**
     * 파킹클라우드 접속
     * @param strUrl
     * @param fileName
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject connectParkingCloud(String strUrl, String fileName) throws Exception, SocketTimeoutException {
        /*
        /////////////////////////////////////////////
        //TEST:Because "Name or service not known" -- host파일에 등록이 되어있어야 찾음
        //접속 전에 체크
        String tempUrl = propertyService.getString("parking.cp.parkingcloud.url");
        InetAddress inetaddr = null;
        try {
           inetaddr = InetAddress.getByName(tempUrl);
           logger.info("inetaddr.getHostAddress() = " + inetaddr.getHostAddress());
           if(!StringUtils.isEmpty(inetaddr.getHostAddress())) {
               logger.info("IP SAVE inetaddr.getHostName() = " + inetaddr.getHostName());
               setCpAdressRedis(tempUrl);
          }
        } catch (UnknownHostException e) {
            logger.info("##UnknownHostException:"+e.getMessage());
            e.printStackTrace();
            
            String ip1 = propertyService.getString("parking.cp.parkingcloud.ip1");
            strUrl = strUrl.replaceFirst(tempUrl, ip1);
        }
      */
       /////////////////////////////////////////////
        
         String appKey = propertyService.getString("parking.cp.parkingcloud.appkey");
         String secretKey = propertyService.getString("parking.cp.parkingcloud.secretkey");
        
         String timeStamp = String.valueOf(System.currentTimeMillis());
         
         /* signature 구하는 방법 중 2)사용*/
         //1) 파킹클라우드 확인 : 아래 방법은 아님(base64로 return되기 때문)
         //String signature = SHA256PasswordEncoder.encryptPassword(appKey+timeStamp+secretKey);
         //
         //2) signature : shr256 해시값 byte 배열구한 후  소문자 16진수 문자열(hexdigest)변환 -> signature
         String signature =  this.getSignature(appKey, timeStamp, secretKey);
         //
         //3) apach 종속
         //String signature = DigestUtils.sha256Hex(orgSignature);

         logger.info("####CPUrl===>"+strUrl); 

         //String USER_AGENT = req.getHeader("User-Agent");
         String USER_AGENT = "HPAY";
         
         //http client 생성
         CloseableHttpClient httpClient = null;
         JSONObject jsonObj = null;
         String jsonData = "";
         BufferedReader br = null;
         BufferedWriter bw = null;
         StringBuffer sb = new StringBuffer();
         
         try {
             httpClient = HttpClients.createDefault();
             /** GET start ****************************************************/
             //get 메서드와 URL 설정
             HttpGet httpGet = new HttpGet(strUrl);        
             //agent 정보 설정
             httpGet.addHeader("User-Agent", USER_AGENT);
             httpGet.addHeader("Accept-Charset", "utf-8");
             httpGet.addHeader("Accept-Language", "ko");
             httpGet.addHeader("appKey", appKey);
             httpGet.addHeader("timeStamp", timeStamp);
             httpGet.addHeader("signature", signature);
    
             //10초 : 10*1000 (파킹클라우드timout 9초)
             RequestConfig requestConfig = RequestConfig.custom()
               .setSocketTimeout(10*1000)
               .setConnectTimeout(10*1000)
               .setConnectionRequestTimeout(10*1000)
               .build();
             httpGet.setConfig(requestConfig);
    
             //get 요청
             CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
             /** GET end ****************************************************/
             
             /** POST start ***************************************************
             //post 메서드와 URL 설정
             HttpPost httpPost = new HttpPost(strUrl);
    
             //10초
             RequestConfig requestConfig = RequestConfig.custom()
               .setSocketTimeout(10*1000)
               .setConnectTimeout(10*1000)
               .setConnectionRequestTimeout(10*1000)
               .build();
             httpPost.setConfig(requestConfig);
             
             //agent 정보 설정
             httpPost.addHeader("User-Agent", USER_AGENT);
             httpPost.addHeader("Content-Type", "application/json");
             httpPost.addHeader("Accept", "application/json");
             httpPost.addHeader("appKey", appKey);
             httpPost.addHeader("timeStamp", timeStamp);
             httpPost.addHeader("signature", signature);
    //         httpPost.addHeader("Accept-Charset", "utf-8");
    //         httpPost.addHeader("Accept-Language", "ko");
             
            
             //post 요청
             CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
             /** POST end ****************************************************/
    
             /** POST2 start ****************************************************/
    //         HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
    //         HttpPost postRequest = new HttpPost(strUrl); 
    //         postRequest.setHeader("Accept", "application/json");
    //         postRequest.setHeader("Content-Type", "application/json");
    //         postRequest.addHeader("appKey", appKey);
    //         postRequest.addHeader("timeStamp", timeStamp);
    //         postRequest.addHeader("signature", signature);
    //         
    //         HttpResponse httpResponse = client.execute(postRequest);
             /** POST2 end ****************************************************/
    
                
             
             String resStatusCode = String.valueOf(httpResponse.getStatusLine().getStatusCode());
             if("200".equals(resStatusCode)){
                 
                 br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                 //sb = new StringBuffer();
                 bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
                 
                 while ((jsonData = br.readLine()) != null) {
                     bw.write(jsonData+"\n");//출력
                     sb.append(jsonData+"\n");               
                     }
          
                 bw.flush();//남아있는 데이터를 모두 출력시킴
                 bw.close();//스트림을 닫음
                 br.close();
    
             } else {
                 //throw new Exception("OTHER:"+resStatusCode);
                 logger.debug("OTHER:"+resStatusCode);
             }
                      
             httpClient.close();
             
    
             logger.debug("CP데이터 JSONObject 전환 START");
             //JSON으로 전환
             JSONParser jsonParser = new JSONParser();
             jsonObj = (JSONObject) jsonParser.parse(sb.toString());
    
             logger.debug("CP데이터 JSONObject 전환 END");
             
         } catch (SocketTimeoutException e) {
             logger.info("connectParkingClou::SocketTimeoutException!!!");
             throw new SocketTimeoutException();
         } catch (Exception e) {
             logger.info("connectParkingCloud::Exception!!!");
             throw new Exception();
         } finally {

             if(bw != null) {bw.close();}
             if(br != null) {br.close();}
             if(httpClient != null) {httpClient.close();}
         }
         
         
        return jsonObj;
    } 
    
    /**
     * 파킹클라우드 요청 암호화 문자열 해쉬키 만들기
     * @param appKey
     * @param timeStamp
     * @param secretKey
     * @return
     * @throws Exception
     */
    @Override
    public String getSignature(String appKey, String timeStamp, String secretKey) throws Exception {        
        
        String orgSignature = appKey+timeStamp+secretKey;
        byte[] encodedhash = this.messageDigest("SHA-256", orgSignature);
        if(encodedhash == null) {
            throw new Exception();
        }
        String signature =  this.bytesToHex(encodedhash);        

        return signature;
    }
    
    /**
     * MessageDigest 호출
     * @param algorithm
     * @param org
     * @return
     * @throws Exception
     */
    @Override
    public byte[] messageDigest(String algorithm, String org) throws Exception {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] encodedhash = digest.digest( org.getBytes(StandardCharsets.UTF_8));
        
        return encodedhash;    
    }

    /**
     * 16진수 문자열(hexdigest)변환
     * @param hash
     * @return
     * @throws Exception
     */
    @Override
    public String bytesToHex(byte[] hash) throws Exception {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1){ hexString.append('0'); }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 사용자 아이피 가져오기
     * @param req
     * @return
     */
    @Override 
    public String getClientIp(HttpServletRequest req){
        String clientip = req.getHeader("X-Forwarded-For");
        if (clientip == null || clientip.length() == 0 || clientip.equalsIgnoreCase("unknown")) { 
            clientip = req.getHeader("Proxy-Client-IP"); 
        } 
        if (clientip == null || clientip.length() == 0 || clientip.equalsIgnoreCase("unknown")) { 
            clientip = req.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (clientip == null || clientip.length() == 0 || clientip.equalsIgnoreCase("unknown")) { 
            clientip = req.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (clientip == null || clientip.length() == 0 || clientip.equalsIgnoreCase("unknown")) { 
            clientip = req.getHeader("HTTP_X_FORWARDED_FOR"); 
        } 
        if (clientip == null || clientip.length() == 0 || clientip.equalsIgnoreCase("unknown")) { 
            clientip = req.getRemoteAddr(); 
        }
        return clientip;
    }
    
  
    

}
