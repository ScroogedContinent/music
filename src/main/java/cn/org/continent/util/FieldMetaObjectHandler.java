package cn.org.continent.util;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 字段自动填充
 * @date 2018/8/23 21:36
 */
public class FieldMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object delFlg = getFieldValByName("delFlg", metaObject);//mybatis-plus版本2.0.9+
        if (delFlg == null) {
            setFieldValByName("delFlg", "1", metaObject);//mybatis-plus版本2.0.9+
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("delFlg", "1", metaObject);
    }
}
