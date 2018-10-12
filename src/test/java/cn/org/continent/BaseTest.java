package cn.org.continent;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/10/12 11:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public abstract class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ApplicationContext ctx;

}
