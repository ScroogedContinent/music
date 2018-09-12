package cn.org.continent.base.util.lock;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 分布式锁接口
 * @date 2018/8/28 10:22
 */
public interface DistributedLock {

    /**
     * 获取锁
     * @return
     */
    boolean acquire();

    /**
     * 释放锁
     */
    void release();

}
