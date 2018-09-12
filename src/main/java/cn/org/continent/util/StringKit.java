package cn.org.continent.util;

import org.springframework.util.StringUtils;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 字符串工具类
 * @date 2018/8/26 11:29
 */
public class StringKit {

    public static final String UNDERLINE = "_";

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前为空则返回null
     * 如：HelloWorld -> hello_world
     *
     * @param camelCaseStr
     * @return
     */
    public static String toUnderlineCase(String camelCaseStr){
        if( StringUtils.isEmpty(camelCaseStr) ){
            return null;
        }

        final int length = camelCaseStr.length();
        StringBuilder builder = new StringBuilder();
        char c;
        boolean isPreUpperCase = false;
        for (int i = 0; i < length; i++) {
            c = camelCaseStr.charAt(i);
            boolean isNextUpperCase = true;
            if( i < (length-1) ){
                isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i+1));
            }
            if( Character.isUpperCase(c) ){
                if( !isPreUpperCase || !isNextUpperCase ){
                    if( i >  0 ){
                        builder.append(UNDERLINE);
                    }
                }
                isPreUpperCase = true;
            }else{
                isPreUpperCase = false;
            }
            builder.append(Character.toLowerCase(c));
        }
        return builder.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前为空则返回null
     * 如：hello_world -> HelloWorld
     *
     * @param camelCaseStr
     * @return
     */
    public static String toCamelCase(String camelCaseStr){
        if( StringUtils.isEmpty(camelCaseStr) ){
            return null;
        }
        if( camelCaseStr.contains(UNDERLINE) ){
            camelCaseStr = camelCaseStr.toLowerCase();

            StringBuilder builder = new StringBuilder(camelCaseStr.length());
            boolean upperCase = false;
            for (int i = 0; i < camelCaseStr.length(); i++) {
                char c = camelCaseStr.charAt(i);

                if( c == '_'){
                    upperCase = true;
                }else if( upperCase ){
                    builder.append(Character.toUpperCase(c));
                    upperCase = false;
                }else {
                    builder.append(c);
                }
            }
            return builder.toString();
        }else{
            return camelCaseStr;
        }
    }
}
