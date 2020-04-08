package com.hpay.parking.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import able.com.service.HService;
import able.com.service.prop.PropertyService;
import able.com.util.fmt.StringUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.parking.service.ParkingLotService;
import com.hpay.parking.service.ParkingUtilService;
import com.hpay.parking.service.dao.ParkingLotMDAO;
import com.hpay.parking.vo.ParkingLotVO;
import com.hpay.parking.vo.PoiIdVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @ClassName   : ParkingLotServiceImpl.java
 * @Description : 주차장 재차정보 제공 서비스 구현 클래스
 * @author ONESUN
 * @since 2019. 4. 29.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 4. 29.     ONESUN         최초 생성
 * </pre>
 */
@Service("parkingLotService")
public class ParkingLotServiceImpl extends HService implements ParkingLotService {
	
    @Resource(name = "parkingLotDAO")
    private ParkingLotMDAO parkingLotDAO;
    
    @Autowired
    PropertyService propertyService; 

    @Autowired
    ParkingUtilService parkingUtilService;
    
    /**
     * 재차정보 데이터 파일을 읽음 (수동 처리용)
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    public JSONObject readFileAllParkUseCount() throws Exception {
              
        String fileDownPath = propertyService.getString("parking.parklist.download.path");
        String fileName = fileDownPath + propertyService.getString("parking.useCount.imsi.filename");        
        
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try { 
            
            //Object obj = parser.parse(new FileReader(strFilename)); 
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF8"));
            
            StringBuffer sb = new StringBuffer();
            String jsonData = "";
            while ((jsonData = br.readLine()) != null) {
                sb.append(jsonData+"\n");
            }

            //JSON으로 전환
            jsonObject = (JSONObject) parser.parse(sb.toString());
            
            logger.debug("ParkListDynamic obj.toJSONString()==>"+jsonObject.toJSONString());
            br.close();            
                        
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
        
        
        return jsonObject;
    }
    
    /**
     * arr를 디비에 저장
     * @param src
     * @param arr
     * @throws Exception
     * @throws PSQLException
     */
    public void readFileAllParkUseCount2DB(String src, JSONArray jsonarray) throws Exception, PSQLException {

       
        //전체 삭제
        parkingLotDAO.deleteAllParkUseCount();
        
        //JACKSON 사용
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i=0; i<jsonarray.size(); i++){ 

            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JSONObject result = (JSONObject) jsonarray.get(i);  
            ParkingLotVO vo = objectMapper.readValue(result.toJSONString(), ParkingLotVO.class);
       
            //NULL 이면 Exception
            if(StringUtil.isEmpty(vo.getPark_seq()) || StringUtil.isEmpty(vo.getStatus()) || StringUtil.isEmpty(vo.getUpdate_date())) {
                continue;
            }
            vo.setSrc(src);
            parkingLotDAO.insertParkingLotList(vo); //여기여기
            
        }        
       
    }
    
    /**
     * 전체 주차장 재차대수 리스트 디비에서 가져오기
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public List<ParkingLotVO> selectAllParkUseCount() throws Exception {
        List<ParkingLotVO> result = null;
        result = (List<ParkingLotVO>) parkingLotDAO.selectAllParkUseCount();  
        return result;
    }
    
    /**
     * redis에 재차정보 저장
     *
     * @param src
     * @param listVo
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void redisSetParkingLotInfo(String src ,List<ParkingLotVO> listVo) throws Exception {
        /**** redis 설정 ****/
        String IP = propertyService.getString("redis.ip");
        String PASSWORD = propertyService.getString("redis.password");
        int PORT = propertyService.getInt("redis.port");
        int TIME_OUT = propertyService.getInt("redis.timeout"); 
        int DB_1 = propertyService.getInt("DB_1");
        
         //REDIS
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD, DB_1 );
        Jedis jedis = pool.getResource();
        
        //2-2. 전체 지우기(Redis)
        String keyPrefix = propertyService.getString("parking.dynamic.redis.prefix"); 
        String keysPattern =keyPrefix+"*";
        Set<String> keys = jedis.keys(keysPattern);
        if(!keys.isEmpty()) {
            jedis.del(keys.toArray(new String[keys.size()]));
        }
        
        //for
         for(int i=0; i< listVo.size(); i++) {
             
            ParkingLotVO vo = (ParkingLotVO)listVo.get(i);
            
            //Redis 저장
            String key = keyPrefix+":"+vo.getSrc()+vo.getPark_seq()+vo.getPark_category();
            StringBuilder value = new StringBuilder();
            value.append(String.valueOf(vo.toJSON()));            
            
            jedis.set(key, value.toString());
            
         }  
                
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
        pool.close();
    }

    
    /**
     * 전체 주차장 재차대수 리스트 Redis에서 가져오기
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public List<JSONObject> selectAllParkUseCountRedis() throws Exception {
        
        /**** redis 설정 ****/
        String IP = propertyService.getString("redis.ip");
        String PASSWORD = propertyService.getString("redis.password");
        int PORT = propertyService.getInt("redis.port");
        int TIME_OUT = propertyService.getInt("redis.timeout"); 
        int DB_1 = propertyService.getInt("DB_1");
        
        JedisPoolConfig jedisPoolConfig = null;
        JedisPool pool = null;
        Jedis jedis = null;
        List<JSONObject> resultList = new ArrayList<>();
        try {
        //REDIS
        jedisPoolConfig = new JedisPoolConfig();
        pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD, DB_1 );
        jedis = pool.getResource();       
        
        //JACKSON 사용 JSONObject로 자동전환 
        /*
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);    //park_echarge_list가  ""으로 와도 변환
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        */
        String src = propertyService.getString("parking.cp.parkingcloud.code"); //"PKC" 주차장 CP : 추가될 경우 이부분 수정필요
        //String src = parkingCpCode; // "PKC":파킹클라우드.주차장 CP 추가될 경우 이부분 수정필요
        String keyPrefix = propertyService.getString("parking.dynamic.redis.prefix");
        String keysPattern =keyPrefix+":"+src+"*";
        
        logger.info("selectUseCount:keysPattern:"+keysPattern);
        resultList = selectUseCount(jedis, keysPattern);
        logger.info("selectUseCount:resultList.size():"+resultList.size());
        
        ///////////////////////////////////////////////////////////////////
        //20191206 공항주차장 추가
        src = propertyService.getString("parking.cp.air.code"); 
        keyPrefix = propertyService.getString("parking.air.redis.prefix");
        keysPattern =keyPrefix+":"+src+"*";
        
        logger.info("selectUseCount:keysPattern:"+keysPattern);
        List<JSONObject> resultList2 = selectUseCount(jedis, keysPattern);
        logger.info("selectUseCount:resultList.size():"+resultList2.size());

        if(resultList2.size() > 0) {
            resultList.addAll(resultList2);
        }
        ///////////////////////////////////////////////////////////////////
        
        } catch (PSQLException ps) {
            ps.getStackTrace();
            
        } finally {
        
            if (jedis != null) {
                jedis.close();
                jedis = null;
            }
            if (pool != null){
                pool.close();
            }
        }
        
        return resultList;
    }

    /**
     * redis에서 재차대수 읽어오기
     * @param jedis
     * @param keysPattern
     * @return
     * @throws Exception
     */
    @Override
    public List<JSONObject> selectUseCount(Jedis jedis, String keysPattern) throws Exception,PSQLException {
        
        List<JSONObject> resultList = new ArrayList<>();
        
        Set<String> keys = jedis.keys(keysPattern);
        List<String> keysList = new ArrayList<>();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
               String data = it.next();
               keysList.add(data);
        }
        
        JSONParser parser = new JSONParser();
        for(int i=0; i<keysList.size(); i++) {
            String value = String.valueOf(jedis.get(keysList.get(i)));
            JSONObject valueObj = (JSONObject) parser.parse(value);
            
            //value==>{"park_category":"PARK","status":"N","update_date":"2019-06-26 16:08:43","park_seq":"1201","src":"PKC"}
            //valueObj.toJSONString()==>{"park_category":"PARK","status":"N","update_date":"2019-06-26 16:08:43","park_seq":"1201","src":"PKC"}
            
            //VO return일 경우 사용 
            //ParkingLotVO resultVo = objectMapper.readValue(valueObj.toJSONString(), ParkingLotVO.class);
            //ParkingLotVO resultVo = objectMapper.readValue(value, ParkingLotVO.class);
            
            resultList.add(valueObj);        
        }
        
        return resultList;
    }
    
    /**
     * POI_ID List요청인 경우
     * @param poiIdVOList
     * @return
     * @throws Exception
     */
    @Override
    public List<JSONObject> selectParkUseCountJoinPoiId(List<PoiIdVO> poiIdVOList) throws Exception {
        List<JSONObject> resultList = new ArrayList<>();
        List<String> tmpList = (List<String>) parkingLotDAO.selectParkUseCountByPoiId(poiIdVOList);  
        JSONParser parser = new JSONParser();
        for(int i=0; i<tmpList.size(); i++) {
            String tmpJson = tmpList.get(i);
            resultList.add((JSONObject)parser.parse(tmpJson));
        }
        
        return resultList;
    }
    
    /**
     * InfoListType=2 POI_ID List요청인 경우 park_seq
     * @param poiIdVOList
     * @return
     * @throws Exception
     */
    @Override
    public List<JSONObject> selectParkUseCountJoinParkSeq(List<PoiIdVO> poiIdVOList) throws Exception {
        List<JSONObject> resultList = new ArrayList<>();
        List<String> tmpList = (List<String>) parkingLotDAO.selectParkUseCountByParkSeq(poiIdVOList);  
        JSONParser parser = new JSONParser();
        for(int i=0; i<tmpList.size(); i++) {
            String tmpJson = tmpList.get(i);
            resultList.add((JSONObject)parser.parse(tmpJson));
        }
        
        return resultList;
    }
}
