package com.hpay.airparking.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;

import able.com.service.HService;
import able.com.service.prop.PropertyService;
import able.com.util.fmt.StringUtil;
import able.com.util.sim.FileTool;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.airparking.service.ParkingAirLoadService;

import com.hpay.airparking.service.dao.AirParkingMDAO;
import com.hpay.airparking.vo.AirCollectHistoryVO;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import com.hpay.airparking.vo.AirPortParkVO;
import com.hpay.airparking.vo.airport.Item;
import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkingLotVO;

import com.hpay.parking.service.ParkingUtilService;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ParkingAirLoadServiceImpl.java
 * @Description : 공항주차장  적재
 * @author o1709
 * @since 2019. 12. 02.
 * @version 1.0
 * @see
 * @Modification Information
 * 
 * 
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 12. 02.     o1709         최초 생성
 * </pre>
 */
@Service("ParkingAirLoadService")
public class ParkingAirLoadServiceImpl extends HService implements ParkingAirLoadService {
	
    @Resource(name = "AirParkingMDAO")
    private AirParkingMDAO AirParkingMDAO;

    @Autowired
    private PropertyService propertyService;    
  
    @Resource(name = "parkingUtilService")
    private ParkingUtilService parkingUtilService;
    

    /*
     * 인천공항주차장 정보 : xml VO로 변환
     */
    @Override
    public List<AirPortParkVO> IncheonxmlToVo(String src) throws Exception , SocketTimeoutException {
        
        //인천공항 실시간 주차장 정보 요청
        String incheonAirUrl = propertyService.getString("parking.cp.air.url.parkList");
        String serviceKey = propertyService.getString("parking.cp.air.secretkey");

        String serviceKey_Deco = URLDecoder.decode(serviceKey.toString(),"UTF-8"); 
 
        String url = incheonAirUrl+serviceKey_Deco;      
 
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        
        MultiValueMap<String, String> parm = null;
        
        factory.setReadTimeout(10000);
        factory.setConnectTimeout(10000);
            
        RestTemplate requestSend =  new RestTemplate(factory);
        HttpHeaders headers = new HttpHeaders();
        
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parm,headers);
        
        //한글깨지는 것을 방지하기 위한 설정 값 추가
        requestSend.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
     
        //인천공항공사 데이터 요청 ResponseEntity<String>
        AirPortParkVO resValue = requestSend.getForObject(url,AirPortParkVO.class); //
        
        ResponseEntity<String>  resValue_file = requestSend.getForEntity(url, String.class); //get 요청을 보내고 ResponseEntity로 반환
        
        //파일에 저장
        String filejson = resValue_file.getBody(); 
        
       // logger.info("[air]filejson :: "+filejson);
        
        List<AirPortParkVO> tempList = new ArrayList<AirPortParkVO>();
    
        logger.info("[air]IncheonAir api ResultCode :: "+resValue.getHeader().getResultCode());
        logger.info("[air]IncheonAir api ResultMsg :: "+resValue.getHeader().getResultMsg());
        
        
        //System.out.println("[air]IncheonAir api ResultCode "+resValue.getHeader().getResultCode());
        //System.out.println("[air]IncheonAir api ResultMsg "+resValue.getHeader().getResultMsg());
        
        try {
                
            if(resValue.getHeader().getResultCode().equals("00")) {
    
                
                String park_seq;
                double d_status;
    
                
                if(resValue.getBody().getItems().getItem().length > 0)
                {     
                    AirPortParkVO tempAirPark = null; 
                    
                    //DB 저장을 위한 데이터 변환 loop
                    for(int i=0;i<resValue.getBody().getItems().getItem().length;i++){
                        Item it = resValue.getBody().getItems().getItem()[i];
                    
                        tempAirPark = new AirPortParkVO(); //hip 영역에 만듬? 
                        //file 저장값
                        
                      
                        //디비에서 키값가져옴
                        park_seq = AirParkingMDAO.selectParkToalNum(it.getFloor());
                        
                        logger.info("<주차장seq>: "+park_seq+" <주차장>: "+it.getFloor()+"   <주차현황시간>: "+it.getDatetm().substring(0, 14)+"  <주차수>: "+ it.getParking()+"   <주차면수>: "+it.getParkingarea());
                        
                        tempAirPark.setParkSeq(park_seq);
                        tempAirPark.setpark_category("PAIR");
                        tempAirPark.setupdate_date(it.getDatetm().substring(0, 14));
                        tempAirPark.setsrc("AIR");
                        
                        
                            //null, 공백체크
                            if ((it.getParking() == null || it.getParking().equals(""))&&(it.getParkingarea() == null || it.getParkingarea().equals("")))
                            {
                                tempAirPark.setstatus("");
                                tempAirPark.setparkingstatus("대 (전체  면)");
                                tempAirPark.setparkingstatus_eng(" (Total )");
                                
                            }
                            else
                            {
                                
                                d_status =  Double.parseDouble(it.getParking())/Double.parseDouble(it.getParkingarea())*100;
                                
                                //주차가능상태정보:혼잡C-70%/보통N-50%/여유R-30%
                                if((int)d_status>=70)
                                {
                                    tempAirPark.setstatus("C");
                                }
                                else if((int)d_status<=70 && (int)d_status>=40 )
                                {
                                    tempAirPark.setstatus("N");
                                }
                                else
                                {
                                    tempAirPark.setstatus("R");
                                }
                                    
                                tempAirPark.setparkingstatus(it.getParking()+"대 (전체 "+it.getParkingarea()+"면)");
                                tempAirPark.setparkingstatus_eng(it.getParking()+" (Total "+it.getParkingarea()+")");
                            }
                            
                            tempAirPark.setparkingsresult(filejson);
                            
                            tempList.add(tempAirPark);
                            
                            //System.out.println("<주차장seq>: "+park_seq+" <주차장>: "+it.getFloor()+"   <주차현황시간>: "+it.getDatetm().substring(0, 14)+"  <주차수>: "+ it.getParking()+"   <주차면수>: "+it.getParkingarea()+"   <주차상태정보> : "+d_status);
                            
                        }
                    }
                }
                else
                { 
                    logger.info("[air]IncheonAir api get list zero...");
                }         
        
        } catch (SocketTimeoutException e) {
            logger.info("connectIncheonAirXML::SocketTimeoutException!!!"+e.getMessage());
            throw new SocketTimeoutException();
        } catch (Exception e) {          
            logger.info("connectIncheonAirXML::Exception!!!"+e.getMessage());
            throw new Exception();
        } finally {
        }
        
        return tempList;
    }
    

    /*
     * 인천공항주차장 정보 : xml VO로 변환
     */
    @Override
    public List<AirPortParkVO> AirxmlToVo(String src,  List<AirPortParkVO> tempList) throws Exception , SocketTimeoutException {
        
        //인천공항 실시간 주차장 정보 요청
        //전국공항 CP 실시간 주자장 정보 요청           
        String nationAirUrl = propertyService.getString("parking.cp.airall.url.parkList");
        String serviceKey = propertyService.getString("parking.cp.air.secretkey");
        String serviceKey_Deco = URLDecoder.decode(serviceKey.toString(),"UTF-8");
       
        String url_all = nationAirUrl+serviceKey_Deco;   
    
        HttpComponentsClientHttpRequestFactory factory_all = new HttpComponentsClientHttpRequestFactory();
        //RestTemplate는 ClientHttpRequestFactory로 부터 ClientHttpRequest를 가져와서 요청을 보낸다.
        
        MultiValueMap<String, String> parm_all = null;
        
        factory_all.setReadTimeout(10000); 
        factory_all.setConnectTimeout(10000); //연결시간 초괗
            
        RestTemplate requestSend_all =  new RestTemplate(factory_all);
        HttpHeaders headers_all = new HttpHeaders();
        
        HttpEntity<MultiValueMap<String, String>> entity_all = new HttpEntity<MultiValueMap<String, String>>(parm_all,headers_all);   
        //HttpEntity 에 헤더 담아줌
        
        //한글깨지는 것을 방지하기 위한 설정 값 추가
        requestSend_all.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        
        //한국공항공사 데이터 요청 ResponseEntity<String>
        AirPortParkVO resValue_all = requestSend_all.getForObject(url_all,AirPortParkVO.class);
     
        
        ResponseEntity<String>  resValue_file = requestSend_all.getForEntity(url_all, String.class);
        //파일에 저장
        String filejson = resValue_file.getBody(); 
        
        
       // logger.info("[airALL]filejson :: "+filejson);
        
        
        String park_seq_all;

        double d_status_all;
        
        
        
        logger.info("[air]AirALL api ResultCode :: "+resValue_all.getHeader().getResultCode());
        logger.info("[air]AirALL api ResultMsg :: "+resValue_all.getHeader().getResultMsg());
  
        //System.out.println("[air]AirALL api ResultCode "+resValue_all.getHeader().getResultCode());
        //System.out.println("[air]AirALL api ResultMsg "+resValue_all.getHeader().getResultMsg());
        
        try {
                
            //전국항공사 데이터요청 성공시
            if(resValue_all.getHeader().getResultCode().equals("00"))
            {        

                AirPortParkVO tempAirPark = null;
                
                for(int i=0;i<resValue_all.getBody().getItems().getItem().length;i++){
                    
                    
                    Item it_all = resValue_all.getBody().getItems().getItem()[i];
                    
                    
                    tempAirPark = new AirPortParkVO();
                    tempAirPark.setupdate_date(it_all.getParkingGetdate().replaceAll("-","")+it_all.getParkingGettime().replaceAll(":",""));
                    tempAirPark.setinsert_date(it_all.getParkingGetdate().replaceAll("-","")+it_all.getParkingGettime().replaceAll(":",""));
                    
                    //디비에서 키값가져옴
                    park_seq_all = AirParkingMDAO.selectParkToalNum(it_all.getAprKor()+" "+it_all.getParkingAirportCodeName());
                    
                    
                    logger.info("<주차장seq>: "+park_seq_all+" <주차장>: "+it_all.getAprKor()+"/"+it_all.getParkingAirportCodeName()+"   <주차현황시간>: "+it_all.getParkingGetdate().replaceAll("-","")+it_all.getParkingGettime().replaceAll(":","")+"  <주차수>: "+ it_all.getParkingIstay()+"  <주차면수>: "+it_all.getParkingFullSpace());
                    
                    tempAirPark.setParkSeq(park_seq_all);
                    tempAirPark.setsrc("AIR");
                    tempAirPark.setpark_category("PAIR");
                        
                        //null, 공백체크
                        if ((it_all.getParkingIstay() == null || it_all.getParkingIstay().equals(""))&&(it_all.getParkingFullSpace() == null || it_all.getParkingFullSpace().equals("")) )
                        {
                            
                            tempAirPark.setstatus("");
                            tempAirPark.setparkingstatus("대 (전체 면)");
                            tempAirPark.setparkingstatus_eng(" (Total )");
                        }

                        else
                        {
                            
                            d_status_all =  Double.parseDouble(it_all.getParkingIstay())/Double.parseDouble(it_all.getParkingFullSpace())*100;
                            
                            //주차가능상태정보:혼잡C-70%/보통N-50%/여유R-30%
                            if((int)d_status_all>=70)
                            {
                                tempAirPark.setstatus("C");                    
                            }
                            else if((int)d_status_all<=70 && (int)d_status_all>=40 )
                            {
                                tempAirPark.setstatus("N");                    
                            }
                            else
                            {
                                tempAirPark.setstatus("R");          
                            }
                            
                            tempAirPark.setparkingstatus(it_all.getParkingIstay()+"대 (전체 "+it_all.getParkingFullSpace()+"면)");
                            tempAirPark.setparkingstatus_eng(it_all.getParkingIstay()+" (Total "+it_all.getParkingFullSpace()+")");

                        }
                        
                        tempAirPark.setparkingsresult(filejson);
                        tempList.add(tempAirPark);
                   
                    //System.out.println("<주차장seq>: "+park_seq_all+" <주차장>: "+it_all.getAprKor()+"/"+it_all.getParkingAirportCodeName()+"   <주차현황시간>: "+it_all.getParkingGetdate().replaceAll("-","")+it_all.getParkingGettime().replaceAll(":","")+"  <주차수>: "+ it_all.getParkingIstay()+"   <주차면수>: "+it_all.getParkingFullSpace()+"   <주차상태정보> : "+d_status_all);
                }
            }
            else
            { 
                logger.info("[air]allAir api get list zero...");
            }         
        
        } catch (SocketTimeoutException e) {
            logger.info("connectALLAirXML::SocketTimeoutException!!!"+e.getMessage());
            throw new SocketTimeoutException();
        } catch (Exception e) {          
            logger.info("connectALLAirXML::Exception!!!"+e.getMessage());
            throw new Exception();
        } finally {
        }
        
        return tempList;
    }
    
    /*
     * 공항주차장 정보 :  Data Redis 적재
     */
    @Override
    public int jsonToRedis(List<AirPortParkVO> tempList) throws Exception, PSQLException {

        /**** redis 설정 ****/
        String IP = propertyService.getString("redis.air.ip");
        logger.info("IP는"+IP);
        String PASSWORD = propertyService.getString("redis.air.password");
        int PORT = propertyService.getInt("redis.air.port");
        int TIME_OUT = propertyService.getInt("redis.air.timeout"); 
        int DB_1 = propertyService.getInt("DB_1");
        
     
        
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD, DB_1 );
        Jedis jedis = pool.getResource();
        int successSize = 0;
        
        try {        
            logger.info("try진입");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           
            //2-2. 전체 지우기(Redis)
            String keyPrefix = propertyService.getString("parking.air.redis.prefix"); 
            String keysPattern =keyPrefix+"*";
            Set<String> keys = jedis.keys(keysPattern);
            if(!keys.isEmpty()) {
                jedis.del(keys.toArray(new String[keys.size()]));
            }        
            logger.info("AirParkList 6. 전체 지우기(Redis) END");

            int voListSize = tempList.size();
            
            //3. set하기
            AirPortParkVO vo = null;
            
            for(int i=0; i<voListSize; i++){ 
                vo = new AirPortParkVO(); 
                vo = tempList.get(i);
                
                //날짜변환
                String Udate = vo.getUpdate_date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date redate = sdf.parse(Udate);                
                SimpleDateFormat  sdf2 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                vo.setupdate_date(sdf2.format(redate));
                
                //Redis 저장
                String key = keyPrefix+":"+vo.getSrc()+vo.getPark_seq()+vo.getPark_category();
                StringBuilder value = new StringBuilder();
                value.append(String.valueOf(vo.toJSON()));            
                //value.append(objectMapper.writeValueAsString(String.valueOf(vo.toJSON())));
                jedis.set(key, value.toString());
            }
    
            successSize = (jedis.keys(keysPattern)).size();
            logger.info("AirParkList 6. Redis 저장 END");
            
                 
        } catch (Exception e){
            e.printStackTrace();
            /*throw new Exception();*/
            
        } finally {
        
            if (jedis != null) {
                jedis.close();
                jedis = null;
            }
            pool.close();

        }                
        return successSize;
    }
    /*
     * 공항주차장 디비저장 :공항주차장  Data DB 적재
     */
    @Override
    public void insertAirPort(List<AirPortParkVO> tempList) throws Exception, PSQLException {
        
        //1. 전체 지우기(PostgreSQL)        
        AirParkingMDAO.deleteAllParkUseCount_Air();
        // TODO Auto-generated method stub
        Object[] getEvObj = tempList.toArray();
        //DB arry 저장을 위한 map 생성
        HashMap<String,Object> cList = new HashMap<String,Object>();

        try {
            cList.put("Air_list", getEvObj);
            AirParkingMDAO.insertAirPort(cList);      
            
        } catch (Exception e) {
             logger.error("insertIncheonAirPort_[{}] : {}", "inserIncheontAirPort insert", e.getMessage());
        }
        logger.debug("AirPark PostgreSQL 저장 END");
    }
    
  
    /**
     * 전체 주차장 상태  파일으로 저장
     * @param strUrl
     * @param fileName
     * @return
     * @throws Exception
     */

    @Override
    public void reqCollectToJson(AirCollectHistoryVO airvo, List<AirPortParkVO> saveList)  throws Exception, SocketTimeoutException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);        
        Date currDate = new Date();
        String today = sdf.format(currDate);
        String fileDownPath = propertyService.getString("airparking.airparklist.download.path")+today+"/";
        //String fileDownPath ="D:\\";
        if(!FileTool.checkExistDirectory(fileDownPath)) {
            FileTool.createNewDirectory(fileDownPath);
        }
        
        //System.out.println(fileDownPath);
       
        BufferedWriter bw = null;
       
        try
        {            
            String targetFileName = fileDownPath+propertyService.getString("airparking.useCount.download.filename.prefix")+airvo.getWorkDate()+"_"+airvo.getWorkDateSeq()+".json";       
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFileName), "UTF-8"));
            //fileoutputstream = 주어진 File 객체가 가리키는 파일을 쓰기 위한 객체를 생성 기존의 파일이 존재할 때는 그 내용을 지우고 새로운 파일을 생성
            //            
            
            
            
            int saveListSize = saveList.size();
            
            AirPortParkVO vo_file = null;
            
            for(int i=0; i< saveListSize ; i++){   
                vo_file = new AirPortParkVO(); 
                vo_file = saveList.get(i);
                
                StringBuilder value = new StringBuilder();
                value.append(String.valueOf(vo_file.getparkingsresult()));            
                bw.write( value.toString()+"\n");//출력
             }
             bw.flush();//남아있는 데이터를 모두 출력시킴
             bw.close();//스트림을 닫음

        
        } catch (SocketTimeoutException e) {
            logger.info("AirParkingFile::SocketTimeoutException!!!"+e);
            throw new SocketTimeoutException();
        } catch (Exception e) {
            logger.info("AirParkingFile::Exception!!!"+e);
            throw new Exception();
        } finally {

            if(bw != null) {bw.close();}

        }
        
    }
 
    /*
     * 키 가져오기
     */
    @Override
    public AirCollectHistoryVO selectWorkDateSeqParkListUseCount() throws Exception {

        AirCollectHistoryVO AirCollectHistoryVO = null;
        AirCollectHistoryVO = AirParkingMDAO.selectWorkDateSeqParkListUseCount();
        return AirCollectHistoryVO;
    }
    /*
     * 적재 이력 저장
     */
    @Override
    public void insertAllParkUseCountHistory(AirCollectHistoryVO chvo) throws Exception {
        AirParkingMDAO.insertAllParkUseCountHistory(chvo);
    }
    /*
     * 적재 이력 업데이트
     */
    @Override
    public void updateAllParkUseCountHistory(AirCollectHistoryVO chvo) throws Exception {
        AirParkingMDAO.updateAllParkUseCountHistory(chvo);
        
    }    
    /*
     * delete all
     */
    @Override
    public void deleteAllParkUseCount_Air() throws Exception {        
        AirParkingMDAO.deleteAllParkUseCount_Air();   
    }
}
