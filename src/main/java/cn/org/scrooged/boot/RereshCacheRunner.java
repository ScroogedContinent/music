package cn.org.scrooged.boot;

import cn.org.scrooged.base.boot.IMusicApplicationRunner;
import cn.org.scrooged.base.util.SpringContextUtil;
import cn.org.scrooged.service.IUserCacheService;
import cn.org.scrooged.service.impl.UserCacheServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 刷新用户信息
 * @date 2018/8/27 21:19
 */
@Component
@Order(1)
public class RereshCacheRunner implements IMusicApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationArguments arguments) {
        logger.info("--------------->>>>> RereshCacheRunner is start ...");
        IUserCacheService userCacheService = SpringContextUtil.getBean(UserCacheServiceImpl.class);
        userCacheService.refreshCache();
        logger.info("--------------->>>>> RereshCacheRunner is start ...");
    }
}
