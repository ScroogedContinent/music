package cn.org.scrooged.base.jackson;

import java.io.Serializable;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2019/6/13 21:44
 */
public interface IKeyName extends Comparable<IKeyName>{

    /**
     * 默认的键值对根节点
     */
    String DEFAULT_ROOT_VALUE = "root";

    /**
     * 获取键值对的代码
     * @return 返回键值对的代码
     */
    Serializable getCode();

    /**
     * 获取键值对的名称
     * @return 名称
     */
    String getName();

    /**
     * 键值对的排序信息，将会在渲染时产生影响
     * @return 返回排序的位置
     */
    default Integer getOrder() {return 0;}

    /**
     * 获取父代码，默认为没有
     * @return 获取父代码
     */
    default String getParentCode() {return DEFAULT_ROOT_VALUE;}

    /**
     * 比较两个键值对的大小
     * @param o 另一个键值对对象
     * @return 返回比较后的结果
     */
    @Override
    default int compareTo(IKeyName o) {
        if (this == o) {
            return 0;
        }
        Integer order1 = getOrder();
        if (null == order1) {
            return -1;
        }
        Integer order2 = o.getOrder();
        if (null == order2) {
            return 1;
        }
        return order1.compareTo(order2);
    }
}
