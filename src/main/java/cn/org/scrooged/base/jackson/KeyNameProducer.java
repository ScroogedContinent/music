package cn.org.scrooged.base.jackson;

import java.util.Arrays;
import java.util.List;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2019/6/13 21:54
 */
public interface KeyNameProducer {

    /**
     * 根据编号获取键值对信息
     * @param no 编号
     * @return 返回键值对信息
     */
    IKeyName[] keyNames(String no);

    /**
     * 根据编号获取键值对信息，返回list
     * 该方法已被废弃，推荐使用{@link KeyNameProducer#keyNames(String)}
     * @param no 编号
     * @return 返回编号信息
     */
    @Deprecated
    default List<? extends IKeyName> keyNameList(String no) {return Arrays.asList(keyNames(no));}
}
