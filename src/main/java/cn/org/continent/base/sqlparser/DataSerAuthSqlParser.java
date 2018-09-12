package cn.org.continent.base.sqlparser;

import com.baomidou.mybatisplus.plugins.parser.ISqlParser;
import com.baomidou.mybatisplus.plugins.parser.SqlInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 数据集过滤
 * @date 2018/8/27 15:09
 */
public class DataSerAuthSqlParser implements ISqlParser {
    @Override
    public SqlInfo optimizeSql(MetaObject metaObject, String s) {
        //先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if( !SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType()) ){
            return null;
        }
        // TODO: 2018/8/29
        return null;
    }
}
