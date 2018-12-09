package cn.org.scrooged.entity;

import cn.org.scrooged.annotation.Fulltext;
import cn.org.scrooged.annotation.OrderField;
import cn.org.scrooged.constant.DbConstant;
import cn.org.scrooged.entity.enums.SexEnum;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;



/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 16:09
 */
@Getter
@Setter
@Accessors(chain = true) //链式赋值
@ToString //重写了toString方法
@TableName("user")
public class User extends Model<User> implements Serializable {
    @TableId
    private String userId;
    @Fulltext
    @OrderField(value = DbConstant.ORDER_DESC)
    private String username;
    @Fulltext
    private SexEnum sex;
    private String age;

    @TableField(fill = FieldFill.INSERT)
    private String password;

    //逻辑删除字段
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String delFlg;

    //表示该字段不对外显示
    @JsonIgnore
    @Length(min = 1, max = 1)
    public String getDelFlg() {
        return delFlg;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    protected Serializable pkVal() {
        return getUserId();
    }

}
