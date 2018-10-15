package cn.org.scrooged.service;

import cn.org.scrooged.base.entity.DataTable;
import cn.org.scrooged.base.entity.ResponseBean;
import cn.org.scrooged.base.service.IBaseService;
import cn.org.scrooged.entity.User;

import java.util.List;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 16:45
 */
public interface IUserService extends IBaseService<User> {
    /**
     * 查询所有，自己写的sql
     * @return
     */
    List<User> selectUser();

    User selectByUid(String userId);

    boolean delById(String userId);

    boolean modifyById(User user);

    ResponseBean<User> add(User user);

    List<User> selectAll();

    ResponseBean<DataTable<User>> findByPage(DataTable dataTable);
}
