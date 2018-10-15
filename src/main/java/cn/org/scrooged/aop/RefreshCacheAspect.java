package cn.org.scrooged.aop;

import cn.org.scrooged.base.util.SpringContextUtil;
import cn.org.scrooged.service.IUserCacheService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 增删改刷新缓存
 * @date 2018/8/28 19:05
 */
@Component
@Aspect
@Order(LOWEST_PRECEDENCE - 12)
public class RefreshCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(RefreshCacheAspect.class);

    @Pointcut("execution(* cn.org.scrooged.service..*.add*(..))")
    public void addMethods(){}

    @Pointcut("execution(* cn.org.scrooged.service..*.del*(..))")
    public void delMethods(){}

    @Pointcut("execution(* cn.org.scrooged.service..*.modify*(..))")
    public void modifyMethods(){}

    @Pointcut("addMethods() || delMethods() || modifyMethods()")
    public void refreshMethod(){}

    @AfterReturning(pointcut = "refreshMethod()")
    public void doAfterReturning(JoinPoint joinPoint) throws Throwable{
        new Thread(()->{
            //刷新缓存
            IUserCacheService userCacheService = (IUserCacheService)SpringContextUtil.getBean("userCacheServiceImpl");
            userCacheService.refreshCache();
            logger.info("...刷新了缓存...");
        }).start();
    }

}
