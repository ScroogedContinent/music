package cn.org.continent.base.boot;

import org.springframework.boot.ApplicationArguments;

import java.io.Serializable;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 服务启动后处理接口
 * @date 2018/8/27 20:29
 */
public interface IMusicApplicationRunner extends Serializable {

    void run(ApplicationArguments arguments);

}
