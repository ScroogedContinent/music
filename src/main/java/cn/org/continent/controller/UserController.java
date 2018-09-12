package cn.org.continent.controller;

import cn.org.continent.base.entity.DataTable;
import cn.org.continent.base.entity.SearchParam;
import cn.org.continent.base.entity.ResponseBean;
import cn.org.continent.entity.User;
import cn.org.continent.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 16:14
 */
@Api(value = "Music 基本服务", tags = {"Music 基本服务"})
@RestController
@RequestMapping("/music")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 自己写的mapper.xml查询
     * @return
     */
    @GetMapping("/user/all")
    @ApiOperation(value = "查询所有人", notes = "查询所有人，自己写的sql")
    public List<User> showAll(){
        return userService.selectUser();
    }

    @GetMapping("/user/{sid}")
    @ApiOperation(value = "查询指定人", notes = "查询指定人")
    @ApiImplicitParam(paramType = "path", name = "sid", value = "用户编号", required = true, dataType = "String")
    public User selectById(@PathVariable("sid") String sid){
        return userService.selectByUid(sid);
    }

    @DeleteMapping("/user/{sid}")
    @ApiOperation(value = "删除指定人", notes = "删除指定人")
    public boolean delById(@PathVariable("sid") String sid){
        return userService.delById(sid);
    }

    @PutMapping("/user/{sid}")
    @ApiOperation(value = "更新指定人", notes = "更新指定人")
    public boolean modifyById(@PathVariable("sid") String sid, @RequestBody User user){
        user.setSid(sid);
        return userService.modifyById(user);
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public ResponseBean<User> delById(@RequestBody User user){
        return userService.add(user);
    }

    @GetMapping("/user")
    @ApiOperation(value = "查询所有人", notes = "查询所有人")
    public List<User> selectAll(){
        return userService.selectAll();
    }

    @GetMapping("/user/page")
    @ApiOperation(value = "分页查询所有人", notes = "分页查询所有人")
    public ResponseBean<DataTable<User>> pageSearch(SearchParam searchParam){
        return userService.findByPage(new DataTable(searchParam));
    }
}
