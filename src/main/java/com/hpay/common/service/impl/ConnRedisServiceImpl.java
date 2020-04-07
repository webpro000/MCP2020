package com.hpay.common.service.impl;

import java.util.List;
import java.util.Map;

import able.com.service.HService;
import able.com.service.prop.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpay.common.service.ConnRedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ConnRedisServiceImpl.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 3.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 3.     O1484       최초 생성
 * </pre>
 */

@Service("connRedisService")
public class ConnRedisServiceImpl extends HService implements ConnRedisService {   
    @Autowired
    PropertyService propertyService;

    private JedisPool pool;
    private Jedis jedis;
    
    private void open(int dbNum){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        pool = new JedisPool(
                jedisPoolConfig , 
                propertyService.getString("redisIp"), 
                propertyService.getInt("redisPort"), 
                propertyService.getInt("redisTimeout"), 
                propertyService.getString("redisPassword"));
        jedis = pool.getResource();
        jedis.select(dbNum);
    }
    
    private void close(){
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
        pool.close();                
    }
    
    @Override
    public void set(int dbNum,  String key, String value){
        open(dbNum);
        jedis.set(key, value);
        close();        
    }
    @Override
    public void setHash(int dbNum,  String key, List<String[]> value){
        open(dbNum);
        for(int i =0; i<value.size(); i++){
            jedis.hset(key, value.get(i)[0], value.get(i)[1]);
        }        
        close();        
    }
    
    @Override
    public void setHash(int dbNum,  String key, List<String[]> value, int expireSec){
        open(dbNum);
        for(int i =0; i<value.size(); i++){
            jedis.hset(key, value.get(i)[0], value.get(i)[1]);
        }
        jedis.expire(key, expireSec);
        close();        
    }    
    @Override
    public void del(int dbNum,  String key ){
        open(dbNum);
        jedis.del(key);
        close();
    }
    
    @Override
    public Map<String, String> getHash(int dbNum,  String key){
        open(dbNum);
        Map<String, String> inRedis = jedis.hgetAll(key);
        close();        
        return inRedis;
    }
}