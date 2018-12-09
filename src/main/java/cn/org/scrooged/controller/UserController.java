package cn.org.scrooged.controller;

import cn.org.scrooged.base.entity.DataTable;
import cn.org.scrooged.base.entity.SearchParam;
import cn.org.scrooged.base.entity.ResponseBean;
import cn.org.scrooged.entity.User;
import cn.org.scrooged.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
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

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "查询指定人", notes = "查询指定人")
    @ApiImplicitParam(paramType = "path", name = "userId", value = "用户编号", required = true, dataType = "String")
    public User selectById(@PathVariable("userId") String userId){
        return userService.selectByUid(userId);
    }

    @DeleteMapping("/user/{userId}")
    @ApiOperation(value = "删除指定人", notes = "删除指定人")
    public boolean delById(@PathVariable("userId") String userId){
        return userService.delById(userId);
    }

    @PutMapping("/user/{userId}")
    @ApiOperation(value = "更新指定人", notes = "更新指定人")
    public boolean modifyById(@PathVariable("userId") String userId, @RequestBody User user){
        user.setUserId(userId);
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

    @GetMapping("/user/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public ResponseBean<User> login(@RequestParam @NotEmpty String userId, @RequestParam @NotEmpty String password){
        return userService.login(userId, password);
    }
}
