/*
package com.qianlq.eurekacustomer.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.JedisCluster;



@Repository("redisCacheService")
public class RedisCacheServiceImpl implements RedisCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheServiceImpl.class);


    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public <T> String addByKey(String key, T value) throws IOException {

        String object2JsonString = null;
        if(value instanceof String){
            object2JsonString =value.toString();
        }else{
            object2JsonString = JSON.toJSONString(value);
        }
        String result = jedisCluster.set(key, object2JsonString);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("addByKey信息为:[key={},value={},result={}]",key,value,result);
        }
        return result;
    }

    @Override
    public <T> String addExByKey(String key, int seconds, T value) throws IOException {
        String object2JsonString=null;
        if(value instanceof String){
            object2JsonString =value.toString();
        }else{
            object2JsonString = JSON.toJSONString(value);
        }
        String result = jedisCluster.setex(key, seconds, object2JsonString);
        if(LOGGER.isDebugEnabled()){
            LOGGER.info("addExByKey信息为:[key={},value={},result={}]",key,value,result);
        }
        return result;
    }

    @Override
    public <T> String addExAtByKey(String key, long unixTime, T value) throws IOException {
        String result = addByKey(key, value);
        Long expireAt = expireAtByKey(key, unixTime);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("addExAtByKey信息为:[key={},unixTime={},result={},expireAt={}]",key,unixTime,result,expireAt);
        }
        return result;
    }

    @Override
    public Long expireAtByKey(String key, long unixTime) throws IOException {
        Long result = jedisCluster.expireAt(key, unixTime);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("expireAtByKey信息为:[key={},unixTime={},result={}]",key,unixTime,result);
        }
        return result;
    }

    @Override
    public Long expireByKey(String key, int seconds) throws IOException {
        Long result = jedisCluster.expire(key, seconds);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("expireByKey信息为:[key={},seconds={},result={}]",key,seconds,result);
        }
        return result;
    }

    @Override
    public Long incrementBy(String key) throws IOException {
        return incrementBy(key,null);
    }

    @Override
    public Long incrementExAtBy(String key, long unixTime) throws IOException {
        Long result = incrementBy(key,null);
        Long expireAtByKey = expireAtByKey(key, unixTime);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("incrementExAtBy信息为:[key={},unixTime={},expireAtByKey={},result={}]",key,unixTime,expireAtByKey,result);
        }
        return result;
    }

    private Long incrementBy(String key, Long defaultValue) throws IOException {
        long result = 0L;
        if(null==defaultValue){
            result = jedisCluster.incr(key);
        }else{
            result = jedisCluster.incrBy(key, defaultValue);
        }
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("incrementBy defaultValue信息为:[key={},defaultValue={},result={}]",key,defaultValue,result);
        }
        return result;
    }

    @Override
    public <T> Long addListKey(Map<String, T> map) throws IOException {
        Long sum = (long) 0;
        Iterator<Entry<String, T>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, T> entry = iterator.next();
            String key = entry.getKey();
            T value = entry.getValue();
            addByKey(key, value);
            sum = sum + 1;
        }
        return sum;
    }

    @Override
    public Long getLong(String key) throws IOException {
        String result = jedisCluster.get(key);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("getLong信息为:[key={},result={}]",key,result);
        }
        return result==null?0:Long.valueOf(result);
    }

    @Override
    public String getString(String key) throws IOException {
        String result = jedisCluster.get(key);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("getObject信息为:[key={},result={}]",key,result);
        }
        return result;
    }

    @Override
    public  <V> V getObject(String key,Class<V> clazz) throws IOException {
        String value = jedisCluster.get(key);
        if (value == null || StringUtils.isEmpty(value)) {
            return null;
        }
        V result = JSON.parseObject(value, clazz);
        if(LOGGER.isDebugEnabled()){
            LOGGER.info("getObjectClass信息为:[key={},result={}]",key,result);
        }
        return result;
    }

    @Override
    public Boolean existsByKey(String key) throws IOException {
        Boolean result = jedisCluster.exists(key);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("existsByKey信息为:[key={},result={}]",key,result);
        }
        return result==null?false:result;
    }

    @Override
    public Long ttlByKey(String key) throws IOException {
        long result = jedisCluster.ttl(key);
        if(result==-1){
            result=Integer.MAX_VALUE;
        }else if(result==-2){
            result=0;
        }else if(result>0){
        }
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("ttlByKey信息为:[key={},result={}]",key,result);
        }
        return result;
    }

    @Override
    public Long deleteByKey(String key) throws IOException {

        Long result = jedisCluster.del(key);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("deleteByKey信息为:[key={},result={}]",key,result);
        }
        return result;
    }

    @Override
    public Long batchDelete(List<String> keyList) throws IOException {
        Long sum = (long) 0;
        Long result = (long) 0;
        for (int i = 0; i < keyList.size(); i++) {
            result = deleteByKey(keyList.get(i));
            sum = sum + result;
        }
        return sum;
    }

}
*/
