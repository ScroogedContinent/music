package cn.org.continent;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 16:08
 */
@EnableTransactionManagement
@MapperScan(basePackages = {"cn.org.continent.mapper"})
@SpringBootApplication
public class MusicApplication {
    private static Logger logger = LoggerFactory.getLogger(MusicApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(MusicApplication.class, args);

        logger.info("----------当野心追赶不上才华，便静下心来努力----------");

        logger.info("==================   服务启动成功!  ==================");
    }
}
