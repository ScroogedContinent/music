package cn.org.continent.service.impl;

import cn.org.continent.base.service.IRedisCommand;
import cn.org.continent.entity.User;
import cn.org.continent.service.IUserCacheService;
import cn.org.continent.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/27 21:25
 */
@Service
public class UserCacheServiceImpl implements IUserCacheService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private IRedisCommand redisCommand;

    @Override
    public void refreshCache() {
        doUserCache();
    }

    @Override
    public void doUserCache() {
        logger.info("--------------->>>>> userCache is start ...");
        List<User> users = userService.selectList(null);

        //先清空所有
        redisCommand.removeBlear("music*");

        for (User user : users ) {
            redisCommand.setValue("music:"+user.getSid(), user);
            //redisCommand.setValue("user:"+user.getSid(), user);
            //redisCommand.setValue("music"+user.getSid(), user, 200L);
            //redisCommand.addMap("music"+user.getSid(), );
        }

        //logger.info(((User)redisCommand.getObj("A1451220")).toString());

        logger.info("--------------->>>>> userCache is end ...");
    }
}
