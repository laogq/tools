package com.anvy.tools.module.user.mapper;

import com.anvy.tools.module.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

/**
 * @author jobob
 * @since 2019-11-22
 */
public interface UserMapper extends BaseMapper<User> {

}
