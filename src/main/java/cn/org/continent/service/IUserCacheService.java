package cn.org.continent.service;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 缓存redis
 * @date 2018/8/27 21:22
 */
public interface IUserCacheService {

    void refreshCache();

    void doUserCache();
}
