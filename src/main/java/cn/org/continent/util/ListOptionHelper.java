package cn.org.continent.util;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/26 11:16
 */
public class ListOptionHelper {

    /**
     * 将字符串按空格分割，包括一个或多个空格 或 Tab制表符
     * @param string
     * @return
     */
    public static String[] stringToArrayByBlank(String string){
        return string.split("\\s+");
    }
}
