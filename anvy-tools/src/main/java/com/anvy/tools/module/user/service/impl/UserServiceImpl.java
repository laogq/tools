package com.anvy.tools.module.user.service.impl;

import com.anvy.tools.module.user.entity.User;
import com.anvy.tools.module.user.mapper.UserMapper;
import com.anvy.tools.module.user.service.IUserService;
import com.anvy.tools.util.ResUtil;
import com.anvy.tools.vo.ResultVo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author Anvy Lao
 * @since 2019-11-22
 */
@Service("UserServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private SolrClient solrClient;

    @Override
    public ResultVo login(User user){
        String password = user.getPassword();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(s);
        QueryWrapper<User> qw = new QueryWrapper<User>(user);
        User vo = userMapper.selectOne(qw);
        if(vo != null){
            String token = this.loginToken(vo);
            return ResUtil.success(token);
        }
        return ResUtil.error("登录失败！");
    }

    private String loginToken(User user) {
        String sign = JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return sign;
    }


    @Override
    public ResultVo register(User user) {
        //验证邮箱是否存在。
        User temp = new User();
        temp.setLoginName(user.getLoginName());
        QueryWrapper<User> qw = new QueryWrapper<>(temp);
        User vo = userMapper.selectOne(qw);
        if(vo != null){
            return ResUtil.error("该用户名已被注册！");
        }
        //验证邮箱是否存在。
        temp.setLoginName(null);
        temp.setEmail(user.getEmail());
        QueryWrapper<User> qw1 = new QueryWrapper<User>(temp);
        User vo1 = userMapper.selectOne(qw1);
        if(vo1 != null){
            return ResUtil.error("该邮箱已被注册！");
        }
        String password = user.getPassword();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(s);
        int insert = userMapper.insert(user);
        if(insert != 1){
            return ResUtil.error("注册失败！");
        }
        return ResUtil.success(insert);
    }

    @Override
    public ResultVo passwdModify(User user) {
        String token = user.getToken();
        DecodedJWT decodedJWT = JWT.decode(token);
        String id = decodedJWT.getAudience().get(0);
        User data = userMapper.selectById(id);
        if(data == null){
            return ResUtil.error("token对应的用户不存在！");
        }
        String password = DigestUtils.md5DigestAsHex(user.getOriginPassword().getBytes());
        if(!password.equals(data.getPassword())){
            return ResUtil.error("原密码输入错误！");
        }
        user.setId(Long.valueOf(id));
        user.setPassword(user.getPassword());
        int i = userMapper.updateById(user);
        if(i != 1){
            return ResUtil.error("密码修改失败！");
        }
        String s = this.loginToken(user);
        return ResUtil.success(s);
    }
}
