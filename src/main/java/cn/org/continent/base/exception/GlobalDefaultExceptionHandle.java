package cn.org.continent.base.exception;

import cn.org.continent.base.entity.ResponseBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 全局异常处理
 * @date 2018/8/26 16:03
 */
@ControllerAdvice
@ResponseBody
public class GlobalDefaultExceptionHandle {

    //声明要捕获的异常
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus
    public ResponseBean defaultExceptionHandler(Exception e){
        e.printStackTrace();
        ResponseBean responseBean = new ResponseBean();
        responseBean.setStatus(false);
        responseBean.setMsg(e.getMessage());
        return responseBean;
    }
}
