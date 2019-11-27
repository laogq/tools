package com.anvy.tools.module.user.controller;


import com.anvy.tools.module.user.entity.User;
import com.anvy.tools.module.user.service.IUserService;
import com.anvy.tools.util.ResUtil;
import com.anvy.tools.vo.ResultVo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-11-22
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public ResultVo login(@RequestBody User user){
        log.debug("userName:"+ user.getName());
        log.debug("password:"+ user.getPassword());
        return userService.login(user);
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResultVo register(@RequestBody User user){
        if(StringUtils.isEmpty(user.getName())){
            return ResUtil.paramError("用户名不能为空！");
        }
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

}
