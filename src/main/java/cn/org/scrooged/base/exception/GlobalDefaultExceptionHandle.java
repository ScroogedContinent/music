package cn.org.scrooged.base.exception;

import cn.org.scrooged.base.entity.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 全局异常处理
 * @date 2018/8/26 16:03
 */
@ControllerAdvice
@ResponseBody
public class GlobalDefaultExceptionHandle {

    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandle.class);

    //声明要捕获的异常
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus
    public ResponseBean defaultExceptionHandler(Exception e, HttpServletRequest request){
        e.printStackTrace();
        ResponseBean responseBean = new ResponseBean();
        responseBean.setStatus(false);
        responseBean.setMsg(e.getMessage());
        return responseBean;
    }
}
