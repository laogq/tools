package com.anvy.tools.module.user.service;

import com.anvy.tools.module.user.entity.User;
import com.anvy.tools.vo.ResultVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-11-22
 */
public interface IUserService extends IService<User> {

    ResultVo login(User user);

    ResultVo register(User user);

    ResultVo passwdModify(User user);
}
