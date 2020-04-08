package com.hpay.parking.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;

import able.com.service.HService;
import able.com.service.prop.PropertyService;
import able.com.util.fmt.StringUtil;
import able.com.util.sim.FileTool;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.parking.service.ParkingLotLoadService;
import com.hpay.parking.service.ParkingUtilService;
import com.hpay.parking.service.dao.ParkingLotMDAO;
import com.hpay.parking.vo.CollectHistoryVO;
import com.hpay.parking.vo.ParkingLotVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ParkingLotLoadServiceImpl.java
 * @Description : 주차장 재차정보 수집 비즈니스 로직 구현 클래스
 * @author o1488
 * @since 2019. 6. 25.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 6. 25.     o1488     	최초 생성
 * </pre>
 */
@Service("parkingLotLoadService")
public class ParkingLotLoadServiceImpl extends HService implements ParkingLotLoadService {
	
    @Resource(name = "parkingLotDAO")
    private ParkingLotMDAO parkingLotDAO;
    
    @Resource(name = "parkingUtilService")
    private ParkingUtilService parkingUtilService;

    @Autowired
    private PropertyService propertyService;    
  
    /**
     * CP호출하여 전체 주차장 상태  Json으로 리턴
     * @param chvo
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject reqCollectToJson(CollectHistoryVO chvo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);        
        Date currDate = new Date();
        String today = sdf.format(currDate);
        String fileDownPath = propertyService.getString("parking.parklist.download.path")+today+"/";
        if(!FileTool.checkExistDirectory(fileDownPath)) {
            FileTool.createNewDirectory(fileDownPath);
        }
       
        
        String targetFileName = fileDownPath+propertyService.getString("parking.useCount.download.filename.prefix")+chvo.getWorkDate()+"_"+chvo.getWorkDateSeq()+".json";
        String strUrl = propertyService.getString("parking.cp.parkingcloud.url.parkListUseCount");
        
        List<String> listUrl = parkingUtilService.addressAliveCheck(strUrl);
        logger.info("listUrl.size()===>"+listUrl.size());
        JSONObject jsonObject = null;
        for(int i=0; i< listUrl.size(); i++){            
            strUrl = listUrl.get(i);
            jsonObject = parkingUtilService.connectParkingCloud(strUrl, targetFileName);
            //logger.info("jsonObject===>"+jsonObject);
            if(jsonObject != null) { break; }
        }
        return jsonObject;
        
    }
    
    /**
     * DB delete all 
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void deleteAllParkUseCount() throws Exception {        
        parkingLotDAO.deleteAllParkUseCount();   
    }

    /**
     * 주차장 재차정보 : Dynamic Data Redis 적재
     * CP호출하여 전체 주차장 상태 VO 변환
     * @param jsonarray
     * @param src
     * @return
     * @throws Exception
     */
    @Override
    public List<ParkingLotVO> jsonToVo(JSONArray jsonarray, String src) throws Exception, PSQLException {

                
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           
        List<ParkingLotVO> resultList = new  ArrayList<ParkingLotVO>(); 
                
        //1. ADD :  src, park_category
        for(int i=0; i<jsonarray.size(); i++){ 

            JSONObject jsonObject = (JSONObject) jsonarray.get(i);  

            ParkingLotVO vo = objectMapper.readValue(jsonObject.toJSONString(), ParkingLotVO.class);

            //src는 CP에서 받지 않으므로 set
            vo.setSrc(src);
            
            if(StringUtil.isEmpty(vo.getPark_seq())) {
                logger.info("jsonToVo SKIP because park_seq is NULL : i = "+i);
                continue;
            } 
            if(StringUtil.isEmpty(vo.getPark_category())) {
                logger.info("jsonToVo SKIP because park_category is NULL : i = "+i);
                continue;
            }
            if(StringUtil.isEmpty(vo.getStatus())) {
                logger.info("jsonToVo SKIP because status is NULL : i = "+i);
                continue;
            }
            if(StringUtil.isEmpty(vo.getUpdate_date())) {
                logger.info("jsonToVo SKIP because update_date is NULL : i = "+i);
                continue;
            }             
            
            ParkingLotVO addVo = new ParkingLotVO();
            addVo.setSrc(vo.getSrc());
            addVo.setPark_category(vo.getPark_category());
            addVo.setPark_seq(vo.getPark_seq());
            addVo.setStatus(vo.getStatus());
            addVo.setUpdate_date(vo.getUpdate_date());
            resultList.add(addVo);
        }
            
        return resultList;
    }

    /**
     * 주차장 재차정보 Redis에 저장
     * @param voList
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public int jsonToRedis(List<ParkingLotVO> voList) throws Exception, PSQLException {

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
        int successSize = 0;
        
        try {        
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           
            //2-2. 전체 지우기(Redis)
            String keyPrefix = propertyService.getString("parking.dynamic.redis.prefix"); 
            String keysPattern =keyPrefix+"*";
            Set<String> keys = jedis.keys(keysPattern);
            if(!keys.isEmpty()) {
                jedis.del(keys.toArray(new String[keys.size()]));
            }        
            logger.debug("ParkListDynamic 2-2. 전체 지우기(Redis) END");
                   
            int voListSize = voList.size();
            //3. set하기
            for(int i=0; i<voListSize; i++){ 
                
                ParkingLotVO vo = voList.get(i);
                
                //Redis 저장
                String key = keyPrefix+":"+vo.getSrc()+vo.getPark_seq()+vo.getPark_category();
                StringBuilder value = new StringBuilder();
                value.append(String.valueOf(vo.toJSON()));            
                //value.append(objectMapper.writeValueAsString(String.valueOf(vo.toJSON())));
                //vo.toJSON():{"park_category":"PARK","status":"R","update_date":"2019-06-27 00:31:50","park_seq":"669","src":"PKC"}
                jedis.set(key, value.toString());            
            }
    
            successSize = (jedis.keys(keysPattern)).size();
            logger.debug("ParkListDynamic 3-2. Redis 저장 END");
                 
        } catch (Exception e){
            throw new Exception();
            
        } finally {
        
            if (jedis != null) {
                jedis.close();
                jedis = null;
            }
            pool.close();

        }
                
        return successSize;
    }


    /**
     * 주차장 재차정보 디비에 저장
     * @param voList
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public List<ParkingLotVO> insertJsonToDb(List<ParkingLotVO> voList) throws Exception, PSQLException {
        
        //1. 전체 지우기(PostgreSQL)
        parkingLotDAO.deleteAllParkUseCount();
        logger.debug("ParkListDynamic 2-1. 전체 지우기(PostgreSQL) END");
        List<ParkingLotVO> resultList = new ArrayList<>();
        
        //2. set하기
        for(int i=0; i<voList.size(); i++){ 
            ParkingLotVO vo = voList.get(i);
            String voStatus = vo.getStatus();
            //NULL 이면 Exception
            if(StringUtil.isEmpty(vo.getPark_seq()) || StringUtil.isEmpty(voStatus) || StringUtil.isEmpty(vo.getUpdate_date())) {
                //throw new Exception();
                //저장안함
                logger.info("INSERT SKIP because park_seq or status or update_date is NULL : i = "+i);
                continue;
            }
            
            if(voStatus.length() > 10) {
                logger.info("INSERT SKIP because status length over 10 : length= "+voStatus.length());
                continue;
            }
            
            //PostgreSQL 저장
            int resultValue = parkingLotDAO.insertParkingLotList(vo);
            if(resultValue == 1){
                //logger.info("vo.getParkingStatus()===>"+vo.getParkingStatus());
                resultList.add(vo);
            } else {
                //저장안함
                logger.info("INSERT FAIL because static data does not exist("+resultValue +") park_seq:"+vo.getPark_seq());
                continue;
            }
            
        }

        logger.debug("ParkListDynamic 3-1. PostgreSQL 저장 END");
        return resultList;
    }

    
    /**
     * 작업일 SEQ 가져오기
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public CollectHistoryVO selectWorkDateSeqParkListUseCount() throws Exception {

        CollectHistoryVO collectHistoryVO = null;
        collectHistoryVO = parkingLotDAO.selectWorkDateSeqParkListUseCount();
        return collectHistoryVO;
    }

    /**
     * 적재 히스토리저장
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void insertAllParkUseCountHistory(CollectHistoryVO chvo) throws Exception {
        parkingLotDAO.insertAllParkUseCountHistory(chvo);
        
    }

    /**
     * 적재 히스토리 수정
     * @param chvo
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public void updateAllParkUseCountHistory(CollectHistoryVO chvo) throws Exception {
        parkingLotDAO.updateAllParkUseCountHistory(chvo);
        
    }

}
