package cn.org.continent.base.boot;

import cn.org.continent.base.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 项目统一后处理 实现IMusicApplicationRunner
 * @date 2018/8/27 20:20
 */
@Component
@Order(LOWEST_PRECEDENCE-10)
public class MusicBootApplicationRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
       Map<String, IMusicApplicationRunner> beansOfType = SpringContextUtil.getApplicationContext().getBeansOfType(IMusicApplicationRunner.class);

        for ( Map.Entry<String, IMusicApplicationRunner> runnerEntry : beansOfType.entrySet() ) {
            logger.info("服务启动类 [" + runnerEntry.getValue().getClass() + "]  ----->>> 开始处理");
            runnerEntry.getValue().run(args);
            logger.info("服务启动类 [" + runnerEntry.getValue().getClass() + "]  ----->>> 结束处理");
        }
    }
}
