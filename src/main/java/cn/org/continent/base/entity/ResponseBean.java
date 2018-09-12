package cn.org.continent.base.entity;

import java.io.Serializable;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 异常响应实体类
 * @date 2018/8/26 16:52
 */
public class ResponseBean<T> implements Serializable {
    private boolean status = true;
    private String msg = "操作成功";
    /**
     * 返回前端的数据
     */
    private Object data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseBean<T>  returnData(T t){
        this.setData(t);
        return this;
    }
}
