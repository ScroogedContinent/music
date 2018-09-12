package cn.org.continent.entity.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 可以将数据库存放的与其对应的一起显示出去
 * @date 2018/8/23 19:11
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SexEnum implements IEnum {
    MALE("0", "男"),
    FEMALE("1", "女");

    private String value;
    private String desc;

    SexEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }
}
