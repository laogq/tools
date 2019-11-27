package com.anvy.tools.module.user.service.impl;

import com.anvy.tools.module.user.entity.User;
import com.anvy.tools.module.user.mapper.UserMapper;
import com.anvy.tools.module.user.service.IUserService;
import com.anvy.tools.util.ResUtil;
import com.anvy.tools.vo.ResultVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-11-22
 */
@Service("UserServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo login(User user){
        String password = user.getPassword();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(s);
        QueryWrapper<User> qw = new QueryWrapper<User>(user);
        User vo = userMapper.selectOne(qw);
        if(vo != null){
            return ResUtil.success(vo);
        }
        return ResUtil.error("登录失败！");
    }


    @Override
    public ResultVo register(User user) {
        String password = user.getPassword();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(s);
        int insert = userMapper.insert(user);
        if(insert != 1){
            return ResUtil.error("注册失败！");
        }
        return ResUtil.success(insert);
    }
}
