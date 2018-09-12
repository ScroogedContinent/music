package cn.org.continent.util;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/3 12:44
 */
public final class CharSequenceUtils {
    private CharSequenceUtils(){
    }

    /**
     * 替换字符序列中满足条件的部分
     *
     * @param origin    原始字符串
     * @param pattern   表达式
     * @param replacer  替换函数
     * @return 返回替换后的字符串
     */
    public static StringBuilder replaceAll(CharSequence origin, Pattern pattern, Function<Matcher, CharSequence> replacer){
        StringBuilder builder = new StringBuilder();
        Matcher matcher = pattern.matcher(origin);
        int last = 0;
        while (matcher.find()){
            builder.append(origin, last, matcher.start()).append(replacer.apply(matcher));
            last = matcher.end();
        }
        int length = origin.length();
        //如果没有匹配到字符串末尾，需要追加后面的字符串；如果没有匹配到，保证字符创的完整性
        if(last < length){
            builder.append(origin, last, length);
        }
        return builder;
    }
}
