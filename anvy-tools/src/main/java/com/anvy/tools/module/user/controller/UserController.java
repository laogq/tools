package com.anvy.tools.module.user.controller;


import com.anvy.tools.module.user.entity.User;
import com.anvy.tools.module.user.service.IUserService;
import com.anvy.tools.util.ResUtil;
import com.anvy.tools.vo.ResultVo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author anvy
 * @since 2019-11-22
 */
@Api(tags = "用户管理")
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ResponseBody
    public ResultVo login(@RequestBody User user){
        log.debug("userName:"+ user.getName());
        log.debug("password:"+ user.getPassword());
        return userService.login(user);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ResponseBody
    public ResultVo register(@RequestBody User user){

        if(StringUtils.isEmpty(user.getLoginName())){
            return ResUtil.paramError("登录用户名不能为空！");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return ResUtil.paramError("用户密码不能为空！");
        }
        if(StringUtils.isEmpty(user.getEmail())){
            return ResUtil.paramError("用户邮箱不能为空！");
        }
        return userService.register(user);
    }

    @RequestMapping(value = "/passwdMod",method = RequestMethod.POST)
    @ApiOperation(value = "密码修改",notes = "密码修改")
    @ResponseBody
    public ResultVo passwdMod(@RequestBody User user){

        if(StringUtils.isEmpty(user.getToken())){
            return ResUtil.paramError("登录token不能为空！");
        }
        if(StringUtils.isEmpty(user.getOriginPassword())){
            return ResUtil.paramError("用户原密码不能为空！");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return ResUtil.paramError("用户新密码不能为空！");
        }
        return userService.passwdModify(user);
    }
}
