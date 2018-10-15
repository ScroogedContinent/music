package cn.org.scrooged;

import cn.org.scrooged.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/24 14:21
 */
public class BeanTest extends BaseTest{

    @Autowired
    private UserServiceImpl userService;

    String context = null;
    Map<String, String> map = new HashMap<>();

    @Before
    public void setup(){
        context = "we will go to ${where} at ${date yyyy-MM-dd HH:mm:ss}, ${where} then play ${play}!";
        map.put("where", "beijing");
    }

    @Test
    public void isHave(){
        //ApplicationContext apc =
        String cont = userService.placeHolder(context, map);
        Assert.assertNotNull(cont);
        Assert.assertTrue("true", !cont.contains("$"));
        logger.info(cont);
    }
}
