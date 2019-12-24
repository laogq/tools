package com.anvy.tools.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author jobob
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class User {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField(exist = false)
    private String token;

    @TableField(value = "NAME")
    private String name;

    @TableField("LOGIN_NAME")
    private String loginName;

    @TableField("EMAIL")
    private String email;

    @TableField(exist = false)
    private String originPassword;

    @TableField("PASSWORD")
    private String password;

    @TableField("DATA_STATUS")
    private String dataStatus;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;


}
