package cn.org.scrooged.base.exception;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 自定义异常类
 * @date 2018/8/27 11:29
 */
public class MusicDefineException extends RuntimeException{
    public MusicDefineException(String message){
        super(message);
    }
}
