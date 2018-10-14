package cn.org.continent.base.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 国际化使用工具类
 * @date 2018/10/14 16:19
 */
public class MessagesUtils {
    public static MessagesUtils init(){
        return new MessagesUtils();
    }

    /**
     * 根据消息key获取当前语言环境下的消息
     *
     * @param messageKey
     * @param params
     * @return
     */
    public String getMessage(String messageKey, Object...params){
        if(StringUtils.isEmpty(messageKey)){
            return "";
        }
        MessageSource messageSource = (MessageSource) SpringContextUtil.getBean("messageSource");
        String msg = messageSource.getMessage(messageKey, params, messageKey, getLocal());
        return msg;
    }

    /**
     * 获取当前环境的本地语言
     *
     * @return
     */
    public static Locale getLocal() {
        return LocaleContextHolder.getLocale();
    }
}
