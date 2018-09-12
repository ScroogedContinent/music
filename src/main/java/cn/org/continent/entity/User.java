package cn.org.continent.entity;

import cn.org.continent.annotation.Fulltext;
import cn.org.continent.annotation.OrderField;
import cn.org.continent.constant.DbConstant;
import cn.org.continent.entity.enums.SexEnum;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 16:09
 */
@TableName("student")
public class User extends Model<User> implements Serializable {
    @TableId
    private String sid;
    @Fulltext
    @OrderField(value = DbConstant.ORDER_DESC)
    private String sname;
    @Fulltext
    private SexEnum sex;
    private String age;

    //逻辑删除字段
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String delFlg;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    //表示该字段不对外显示
    @JsonIgnore
    @Length(min = 1, max = 1)
    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    @Override
    protected Serializable pkVal() {
        return getSid();
    }

    @Override
    public String toString() {
        return "User{" +
                "sid='" + sid + '\'' +
                ", sname='" + sname + '\'' +
                ", sex=" + sex +
                ", age='" + age + '\'' +
                ", delFlg='" + delFlg + '\'' +
                '}';
    }
}
