package cn.org.continent.base.util.lock.impl;

import cn.org.continent.base.util.SpringContextUtil;
import cn.org.continent.base.util.lock.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 基于redis的分布式锁实现--互斥锁
 * @date 2018/8/28 10:25
 */
public class JedisLock implements DistributedLock {

    public static Logger logger = LoggerFactory.getLogger(JedisLock.class);

    private static StringRedisTemplate stringRedisTemplate;

    /*
      分布式锁的键值
     */
    String lockKey; //锁的键值
    int expireMsecs = 10 * 1000; //锁超时，防止线程在入锁以后，无限的执行等待
    int timeoutMsecs = 10 * 1000; //锁等待，防止线程饥饿
    boolean locked = false; //是否已经获取锁

    /**
     * 获取指定键值的锁
     * @param lockKey 锁的键值
     */
    public JedisLock(String lockKey){
        this.lockKey = lockKey;
    }

    /**
     * 获取指定键值的锁，同时设置获取锁的超时时间
     * @param lockKey 锁的键值
     * @param timeoutMsecs 获取锁超时时间
     */
    public JedisLock(String lockKey, int timeoutMsecs){
        this.lockKey = lockKey;
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * 获取指定键值的锁，同时设置获取锁的超时时间和锁的过期时间
     * @param lockKey 锁的键值
     * @param timeoutMsecs 获取锁超时时间
     * @param timeoutMsecs 获取锁失效时间
     */
    public JedisLock(String lockKey, int timeoutMsecs, int expireMsecs){
        this.lockKey = lockKey;
        this.timeoutMsecs = timeoutMsecs;
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() { return lockKey; }

    /**
     * @return true acquire lock, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    @Override
    public synchronized boolean acquire() {
        int timeout = timeoutMsecs;
        if( null == stringRedisTemplate ){
            stringRedisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
        }
        try {
            while( timeout >= 0 ){
                long expires = System.currentTimeMillis() + expireMsecs + 1;
                String expiredStr = String.valueOf(expires); // 锁到期时间

                if( stringRedisTemplate.opsForValue().setIfAbsent(lockKey, expiredStr) ){
                    // lock acquired
                    locked = true;
                    return true;
                }

                String currentValueStr = stringRedisTemplate.opsForValue().get(lockKey); //redis里的时间
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断过不去
                if( null != currentValueStr && Long.parseLong(currentValueStr) < System.currentTimeMillis() ){
                    // lock is expired
                    String oldValueStr = stringRedisTemplate.opsForValue().getAndSet(lockKey, expiredStr);
                    //获取上一个锁到期时间，并设置现在的锁到期时间
                    //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                    if( null != oldValueStr && oldValueStr.equals(currentValueStr) ){
                        //如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，它才有权利获取锁
                        // lock acquired
                        locked = true;
                        return true;
                    }
                }
                timeout -= 100;
                Thread.sleep(100);
            }
        } catch (Exception e) {
            logger.error("release lock due to error", e);
        }
        return false;
    }

    /**
     * 释放锁
     */
    @Override
    public synchronized void release() {
        if( null == stringRedisTemplate ){
            stringRedisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
        }
        try {
            if(locked){
                String currentValueStr = stringRedisTemplate.opsForValue().get(lockKey); //redis里的时间
                //校验是否超过有效期，如果不在有效期内，那说明当前锁已经失效，不能进行删除锁操作
                if( null != currentValueStr && Long.parseLong(currentValueStr) > System.currentTimeMillis() ){
                    stringRedisTemplate.delete(lockKey);
                    locked = false;
                }
            }
        } catch (Exception e) {
            logger.error("release lock due to error", e);
        }
    }
}
