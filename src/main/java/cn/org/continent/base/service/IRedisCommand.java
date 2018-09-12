package cn.org.continent.base.service;

import java.util.List;
import java.util.Map;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description Redis执行方法
 * @date 2018/8/28 13:51
 */
public interface IRedisCommand<K, V> {
    /**
     * 根据指定o获取Object
     * @param obj
     * @return
     */
    Object getObj(Object obj);

    /**
     * 设置obj缓存
     * @param key
     * @param value
     */
    void setValue(final K key, V value);

    /**
     * 写入缓存
     * @param key
     * @param value
     * @param expireTime 过期时间(s)
     */
    void setValue(final K key, V value, Long expireTime);

    /**
     * 删除Obj缓存
     * @param obj
     */
    void delObj(Object obj);

    /**
     * 模糊移除，支持*号等匹配移除
     * @param blears
     */
    void removeBlear(K...blears);

    /**
     * 模糊移除，支持*号等匹配移除
     * @param blear
     */
    void removeBlear(K blear);

    /**
     * 添加map
     * @param key
     * @param map
     */
    void addMap(K key, Map<K, V> map);

    /**
     * 向key对应的map中添加缓存对象
     * @param key cache对象key
     * @param field map对应的key
     * @param value 值
     */
    void addMap(K key, K field, Object value);

    /**
     * 向key对应的map中添加缓存对象
     * @param key cache对象key
     * @param field map对应的key
     * @param value 值
     * @param time 过期时间-整个map的过期时间
     */
    void addMap(K key, K field, V value, long time);

    /**
     * 获取map缓存中的某个对象
     * @param key map中对应的key
     * @param field map中该对象的key
     * @param <T>
     * @return
     */
    <T> T getMapField(K key, K field);

    /**
     * 获取map对应key的value
     * @param key map中对应的key
     * @return
     */
    List<V> getMapFieldValue(K key);
}
