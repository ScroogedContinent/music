package cn.org.scrooged.mapper;

import cn.org.scrooged.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 16:38
 */
public interface IUserMapper extends BaseMapper<User> {
    List<User> selectUser();
}
