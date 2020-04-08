package com.hpay.parking.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import able.com.service.HService;
import able.com.service.prop.PropertyService;
import able.com.util.fmt.StringUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpay.parking.service.ParkListService;
import com.hpay.parking.service.ParkingUtilService;
import com.hpay.parking.service.dao.ParkListMDAO;
import com.hpay.parking.vo.ParkEchargeListVO;
import com.hpay.parking.vo.ParkInfoVO;
import com.hpay.parking.vo.ParkListReqVO;
import com.hpay.parking.vo.ProvideVersionVO;
import com.hpay.parking.vo.ResultParkInfoVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @ClassName   : ParkListServiceImpl.java
 * @Description : 주차장 Static 정보 제공 서비스 구현 클래스
 * @author ONESUN
 * @since 2019. 4. 24.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 4. 24.     ONESUN         최초 생성
 *  2019.12. 06.     ONESUN         [주차장제공]공항주차장 추가 
 * </pre>
 */
@Service("parkListService")
public class ParkListServiceImpl extends HService implements ParkListService {
	
    @Resource(name = "parkListDAO")
    private ParkListMDAO parkListDAO;
    
    @Autowired
    PropertyService propertyService; 
        
    @Resource(name = "parkingUtilService")
    ParkingUtilService parkingUtilService; 
        
      
    /**
     * 전체 주차장 목록 파일 읽어서 JSON으로 변환
     * @param FileName
     * @return
     * @throws Exception
     */
    public JSONObject readFileParkList(String fileName) throws Exception {
        
        if(StringUtil.isEmpty(fileName)){
            throw new Exception();
        }
        
        JSONParser parser = new JSONParser(); 
        JSONObject jsonData = null;
        String inData = "";
        BufferedReader in = null;
        try { 
            //FileReader 16M 파일이 안 읽혀짐
            //jsonData = (JSONObject) parser.parse(new FileReader(fileName)); 
            
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF8"));
            StringBuffer sb = new StringBuffer();
            while ((inData = in.readLine()) != null) {
                sb.append(inData+"\n");
            }
            jsonData = (JSONObject) parser.parse(sb.toString());    
            in.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
        
        return jsonData;
        
        
    }    
    
   
    
    /**
     * 14일전 데이터 delete
     */
    public void deleteParkListNotUse(String refDate) throws Exception ,PSQLException{
        
        parkListDAO.deleteParkListNotUse(refDate);   

    }

    /**
     * 주차장 목록(전체)
     * @param versionInfoVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public List<ParkInfoVO> selectParkList(ProvideVersionVO versionInfoVO) throws Exception, PSQLException {
        List<ParkInfoVO> resultVo = null;
        resultVo = (List<ParkInfoVO>) parkListDAO.selectParkList(versionInfoVO);  
        return resultVo;
    }

    /**
     * 주차장 목록(공항주차장 전체) 20191205추가
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public List<ParkInfoVO> selectParkListAirport() throws Exception, PSQLException {
        List<ParkInfoVO> resultVo = null;
        resultVo = (List<ParkInfoVO>) parkListDAO.selectParkListAirport();  
        return resultVo;
    }

    /**
     * 전기충전소 리스트
     * @param vo
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public List<ParkEchargeListVO> selectParkEchargeList(ParkInfoVO vo) throws Exception {

        List<ParkEchargeListVO> resultVo = null;
        resultVo = (List<ParkEchargeListVO>) parkListDAO.selectParkEchargeList(vo);  
        return resultVo;
    }

    /**
     * 제공버전 확인
     * @param provideService
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public ProvideVersionVO selectParkListProvideVersion(String provideService) throws Exception, PSQLException {
        ProvideVersionVO parkVersionVO = parkListDAO.selectParkListProvideVersion(provideService);         
        return parkVersionVO;
    }

    /**
     * 요청버전과 제공버전 체크
     * @param pvVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public ProvideVersionVO checkProvideVersion(ProvideVersionVO pvVO)  throws Exception, PSQLException {
        
        ProvideVersionVO resultVO = (ProvideVersionVO) parkListDAO.checkProvideVersion(pvVO);
                
        return resultVO;
    }

    /**
     * 주차장 목록(차분)
     * @param reqVersionInfoVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public List<ParkInfoVO> selectParkListChangeData(ProvideVersionVO reqVersionInfoVO) throws Exception, PSQLException {
        List<ParkInfoVO> resultVo = null;
        resultVo = (List<ParkInfoVO>) parkListDAO.selectParkListChangeData(reqVersionInfoVO);  
        return resultVo;
    }
    
    /**
     * 주차장 목록(차분) Redis
     * @param reqVersionInfoVO
     * @param diffDays
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public JSONObject getParkListChangeData(ProvideVersionVO reqVersionInfoVO, long diffDays) throws Exception, PSQLException {
        
        /**** redis 설정 ****/
        String IP = propertyService.getString("redis.ip");
        String PASSWORD = propertyService.getString("redis.password");
        int PORT = propertyService.getInt("redis.port");
        int TIME_OUT = propertyService.getInt("redis.timeout"); 
        int DB_2 = propertyService.getInt("DB_2");
        
        //REDIS
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD, DB_2 );
        Jedis jedis = pool.getResource();


        String targetKey = "ParkStaticDifferenceBeforeDay"+diffDays;
        logger.debug("ParkList Static key===>"+targetKey);
        
        JSONParser parser = new JSONParser();
        
        String value = String.valueOf(jedis.get(targetKey));
        JSONObject returnObj = (JSONObject) parser.parse(value);
        
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
        if (pool != null){
            pool.close();
        }

        return returnObj;
    }

    /**
     * 이전일자 13일 버전 리스트
     * @param provideService
     * @param referenceDate
     * @return
     * @throws Exception
     * @throws PSQLException
     */
   @Override
   public List<ProvideVersionVO> selectProvideVersionList(String provideService , int referenceDate) throws Exception, PSQLException {
       Map<Object, Object> param = new HashMap<Object, Object>();
       param.put("provideService", provideService);
       param.put("referenceDate", referenceDate);
       List<ProvideVersionVO> resultVo = null;
       resultVo = (List<ProvideVersionVO>) parkListDAO.selectProvideVersionList(param);  
       return resultVo;
   }

   /**
    * 차분 저장
    * @param reqProvideVersionVO
    * @param resultParkInfoVO
    * @param diffDay
    * @throws Exception
    */
    @Override
    public void makeJsonDataDifference(ProvideVersionVO reqProvideVersionVO, ResultParkInfoVO resultParkInfoVO, int diffDay) throws Exception {

        //날짜 차이
        //int diffDay = 2;
        String redisPrefix = propertyService.getString("parking.static.redis.prefix");
        String key = redisPrefix+diffDay;
        

        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);    //park_echarge_list가  ""으로 와도 변환
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        
        json = objectMapper.writeValueAsString(resultParkInfoVO);
        setDifferenceToRedis(key, json);
    }    
    
    /**
     * Redis 삭제
     * @throws Exception
     */
    @Override
    public void delRedisParkingStaticDifference() throws Exception {

        /**** redis 설정 ****/
        String IP = propertyService.getString("redis.ip");
        String PASSWORD = propertyService.getString("redis.password");
        int PORT = propertyService.getInt("redis.port");
        int TIME_OUT = propertyService.getInt("redis.timeout"); 
        int DBNo = propertyService.getInt("DB_2");
        
        //REDIS
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD, DBNo );
        Jedis jedis = pool.getResource();
        
        //2-2. 전체 지우기(Redis)
        String keyPrefix =  propertyService.getString("parking.static.redis.prefix"); 
        String keysPattern =keyPrefix+"*";
        Set<String> keys = jedis.keys(keysPattern);
        if(!keys.isEmpty()) {
            jedis.del(keys.toArray(new String[keys.size()]));
        }        
        logger.debug("ParkListDynamic 2-2. 전체 지우기(Redis) END");
        
        
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
        pool.close();
    }
    
    /**
     * 차분 Redis저장
     * @param key
     * @param jsonObject
     * @throws Exception
     */
    @Override
    public void setDifferenceToRedis(String key, String jsonObject) throws Exception {

        /**** redis 설정 ****/
        String IP = propertyService.getString("redis.ip");
        String PASSWORD = propertyService.getString("redis.password");
        int PORT = propertyService.getInt("redis.port");
        int TIME_OUT = propertyService.getInt("redis.timeout"); 
        int DBNo = propertyService.getInt("DB_2");
        
        
        //REDIS
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD, DBNo );
        Jedis jedis = pool.getResource();
        
        jedis.set(key, jsonObject);
        logger.debug("3. Redis 저장 END : "+key);     
            
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
        pool.close();
    }
    /**
     * 최종 버전 추출
     * @param pvVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public ProvideVersionVO selectLastVersionInfo(ProvideVersionVO pvVO) throws Exception, PSQLException {
        ProvideVersionVO versionInfo = parkListDAO.selectLastVersionInfo(pvVO);
        return versionInfo;
    }

    /**
     * 제공 ParkList 생성
     * @param reqVO
     * @param req
     * @param versionInfoVO
     * @return
     * @throws Exception
     * @throws PSQLException
     */
    @Override
    public ResultParkInfoVO makeResultParkList(ParkListReqVO reqVO, HttpServletRequest req, ProvideVersionVO versionInfoVO ) throws Exception, PSQLException {
        ResultParkInfoVO resultVO = new ResultParkInfoVO();
        List<ParkInfoVO> result = null;

        String provideService = propertyService.getString("parking.version.provideService");
        String interfaceCode =  propertyService.getString("parking.static.provide.interfaceCode");
        
        String resInfoListType = "";
        String reqInterfaceCode =  reqVO.getInterfaceCode();
        String reqVersionInfo =  reqVO.getVersionInfo();
        String reqInfoListType =  reqVO.getInfoListType();
        
        //제공파일 날짜 체크하기
        //KR_PARK_20190610_005
        ProvideVersionVO pvVO = new ProvideVersionVO();
        pvVO.setProvideFullVersion(reqVersionInfo);
        pvVO.setProvideService(provideService);
        ProvideVersionVO checkVO = (ProvideVersionVO) this.checkProvideVersion(pvVO);
        long  diffDays = 0;
        //0:전체, 1:차분
        if ("0".equals(reqInfoListType)) {
            resInfoListType = "0";
                
        } else if ("1".equals(reqInfoListType)) {
            Map<String, Object> diffMap = caluateDifferenceType(checkVO);
            resInfoListType = (String)diffMap.get("resInfoListType");
            diffDays = (long)diffMap.get("diffDays");
        } 

        if("0".equals(resInfoListType)) {
            result = (List<ParkInfoVO>) selectParkList(versionInfoVO);
        
            //ParkInfoV 갯수만큼 돌아서 park_echarge_list 를 array 로 받기
            //JSONParser jsonParser = new JSONParser();
            ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            //TypeFactory typeFactory = objectMapper.getTypeFactory();
            for(int i=0; i<result.size(); i++){
                
                ParkInfoVO tempParkInfo = (ParkInfoVO)result.get(i);
                String parkEchargeNy = tempParkInfo.getPark_echarge_ny();
                // 없으면 안줌
                if(!StringUtil.isEmpty(parkEchargeNy) && "1".equals(parkEchargeNy)){
                    
                    //List<ParkEchargeListVO> temp2 = (List<ParkEchargeListVO>)parkListService.selectParkEchargeList(tempParkInfo);
                    //tempParkInfo.setPark_echarge_list(temp2);

                    String parkEchargeListJson = tempParkInfo.getPark_echarge_list_json();
                    // json으로 저장하려다가 VO로 저장함
                    //JSONObject jsonObj2 = (JSONObject) jsonParser.parse(parkEchargeListJson);
                    //JSONArray jsonObj = (JSONArray) jsonParser.parse(parkEchargeListJson);
                    //String jsonString = jsonObj.toJSONString();                    
                    if(!StringUtils.isEmpty(parkEchargeListJson)){
                        
                         //아이파킹클라우드에서 받은 대로 제공하기 위해 : ""으로 전송 
                         if("[]".equals(parkEchargeListJson)) {
                             parkEchargeListJson = "[{\"park_echarge_cd\": \"\"}]";
                        } 
                         
//                        JSONArray jsonObj = (JSONArray) jsonParser.parse(parkEchargeListJson);
//                        tempParkInfo.setPark_echarge_list(jsonObj);
                         
                         List<ParkEchargeListVO> valueList = objectMapper.readValue(parkEchargeListJson, new TypeReference<List<ParkEchargeListVO>>() { });
                         tempParkInfo.setPark_echarge_list(valueList);
                    } 
                
                    result.set(i, tempParkInfo);
                }
                
            }  
           
            logger.info("ParkInfoVO List result : "+result.size());
            
            
            /////////////////////////////////////////////////////////////////////////////
            //20191205 공항주차장 리턴값에 추가 ONESUN            
            List<ParkInfoVO> resultAirport = (List<ParkInfoVO>) selectParkListAirport();
            //Park_echarge_ny:"N" 이므로 주차장 리스트만 add 
            logger.info("resultAirport : "+resultAirport.size());
            result.addAll(resultAirport);
            logger.info("ADD resultAirport : "+result.size());
            /////////////////////////////////////////////////////////////////////////////
            
            
            
        } else if ("1".equals(resInfoListType)) {
            
            //14일 이내 요청이면 REDIS에서 제공
            JSONObject jsonObject = (JSONObject) getParkListChangeData(checkVO ,diffDays);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            if(jsonObject != null) {
                resultVO = (ResultParkInfoVO) objectMapper.readValue(jsonObject.toJSONString(), ResultParkInfoVO.class);
            } else {
                resultVO.setInterfaceCode(reqInterfaceCode);
                resultVO.setVersionInfo(reqVersionInfo);
                resultVO.setResultCode(null);
                resultVO.setResultMessage(null);
                resultVO.setPark_list(result);                        
            }
           
            return  resultVO;                
        }

        resultVO.setInterfaceCode(interfaceCode);
        resultVO.setVersionInfo(versionInfoVO.getProvideFullVersion()); //변경된 제공 버전
        resultVO.setResultCode("OK");
        resultVO.setResultMessage("OK");
        resultVO.setPark_list(result);
        
        return resultVO;
    }
    /**
     * 일자를 계산해서 차분 타입 계산
     * @param checkVO
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> caluateDifferenceType(ProvideVersionVO checkVO ) throws Exception {
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String resInfoListType="0";
        long  diffDays = 0;
        
        //요청버전이 제공되지 않았을 경우 전체 제공 
        if(checkVO == null || StringUtil.isEmpty(checkVO.getProvideDate())) {
            //전체 제공
            resInfoListType = "0";
          
        } else {
            
            //30일 전 데이터 요청일 경우 전체 제공
            int referenceDate =  -1 * propertyService.getInt("parking.parklist.referenceDays");
             
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
            Date currTime = new Date();
            String sCurrDate = sdf.format(currTime);
            String sprovideDate = checkVO.getProvideDate(); //header 요청일자
            Date currDate = sdf.parse(sCurrDate);
            Date provideDate = sdf.parse(sprovideDate);
            
            
            logger.debug("111111currDate : "+currDate);
            logger.debug("111111제공provideDate : "+provideDate);                    
            //diffDays = provideDate.compareTo(currDate);   //유휴일만 계산됨                    
            diffDays = (provideDate.getTime() - currDate.getTime()) / (24*60*60*1000);
            logger.debug("111111diffDay : "+diffDays);
            
            //30일 이전 요청이면 전체 제공(-1일 ~ -30일) 아니면 차분 제공
            if(diffDays < referenceDate) {
                resInfoListType = "0";
            } else if (diffDays >= referenceDate) {
                resInfoListType = "1";
            }
            
            
        }                
    
        returnMap.put("resInfoListType", resInfoListType);
        returnMap.put("diffDays", diffDays);        
        return returnMap;
    }
    
}
