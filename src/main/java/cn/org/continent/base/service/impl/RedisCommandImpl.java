package cn.org.continent.base.service.impl;

import cn.org.continent.base.service.IRedisCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/28 13:55
 */
@Service
public class RedisCommandImpl implements IRedisCommand<String, Object> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 根据指定o获取Object
     * @param obj
     * @return
     */
    @Override
    public Object getObj(Object obj) {
        return redisTemplate.opsForValue().get(obj);
    }

    @Override
    public void setValue(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public void setValue(String key, Object value, Long expireTime) {
        redisTemplate.boundValueOps(key).set(value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 删除Obj缓存
     * @param obj
     */
    @Override
    public void delObj(Object obj) {
        redisTemplate.delete(obj);
    }

    @Override
    public void removeBlear(String... blears) {
        for (String blear : blears ) {
            removeBlear(blear);
        }
    }

    @Override
    public void removeBlear(String blear) {
        redisTemplate.delete(redisTemplate.keys(blear));
    }

    @Override
    public void addMap(String key, Map<String, Object> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }

    @Override
    public void addMap(String key, String field, Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    @Override
    public void addMap(String key, String field, Object value, long time) {
        redisTemplate.boundHashOps(key).put(field, value);
        redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
    }

    @Override
    public <T> T getMapField(String key, String field) {
        return (T)redisTemplate.boundHashOps(key).get(field);
    }

    @Override
    public List<Object> getMapFieldValue(String key) {
        return redisTemplate.boundHashOps(key).values();
    }
}
