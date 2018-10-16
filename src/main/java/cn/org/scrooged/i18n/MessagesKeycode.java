package cn.org.scrooged.i18n;

import cn.org.scrooged.base.i18n.IMessagesKeycode;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 国际化的配置信息
 *                不同的业务模块定义不同的枚举，分类管理
 * @date 2018/10/15 16:56
 */
public class MessagesKeycode {

    public static enum sys implements IMessagesKeycode{
        SEARCH_SUCCESS("SEARCH_SUCCESS"),
        SEARCH_FAIL("SEARCH_FAIL", 100001),
        OPERATE_FAIL("OPERATE_FAIL", 100002);

        String key;
        Integer code;

        sys(String key){
            this.key = key;
            this.code = 0;
        }

        sys(String key, Integer code){
            this.key = key;
            this.code = code;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

}
