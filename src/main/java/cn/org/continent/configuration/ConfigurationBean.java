package cn.org.continent.configuration;

import cn.org.continent.base.sqlparser.DataSerAuthSqlParser;
import cn.org.continent.util.FieldMetaObjectHandler;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.parser.ISqlParser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 22:03
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.baomidou.cloud.service.*.mapper*")
public class ConfigurationBean {
    @Bean
    public FieldMetaObjectHandler getFieldMetaObjectHandler(){
        return new FieldMetaObjectHandler();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");

        List<ISqlParser> sqlParsers = new ArrayList<>();//加载实现了ISqlParser接口的解析器
        sqlParsers.add(new DataSerAuthSqlParser());

        paginationInterceptor.setSqlParserList(sqlParsers);
        return new PaginationInterceptor().setDialectType("mysql");
    }
}
