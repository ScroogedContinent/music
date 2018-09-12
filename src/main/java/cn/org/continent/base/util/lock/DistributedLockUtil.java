package cn.org.continent.base.util.lock;

import cn.org.continent.base.util.lock.impl.JedisLock;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 分布式锁获取帮助类
 * @date 2018/8/28 11:12
 */
public class DistributedLockUtil {
    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @param lockKey
     * @return
     */
    public static DistributedLock getDistributedLock(String lockKey){
        lockKey = assembleKey(lockKey);
        JedisLock lock = new JedisLock(lockKey);
        return lock;
    }

    /**
     * 正式环境、测试环境公用一个redis时，避免key相同造成的影响
     * @param lockKey
     * @return
     */
    private static String assembleKey(String lockKey) {
        return String.format("lock_%s", lockKey);
    }

    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @param lockKey
     * @param timeoutMsecs 指定获取超时时间
     * @return
     */
    public static DistributedLock getDistributedLock(String lockKey, int timeoutMsecs){
        lockKey = assembleKey(lockKey);
        JedisLock lock = new JedisLock(lockKey, timeoutMsecs);
        return lock;
    }

    /**
     * 获取分布式锁
     * 默认获取锁10s超时，锁过期时间60s
     * @param lockKey
     * @param timeoutMsecs 指定获取超时时间
     * @param expireMsecs 指定锁过期时间
     * @return
     */
    public static DistributedLock getDistributedLock(String lockKey, int timeoutMsecs, int expireMsecs){
        lockKey = assembleKey(lockKey);
        JedisLock lock = new JedisLock(lockKey, timeoutMsecs, expireMsecs);
        return lock;
    }
}
