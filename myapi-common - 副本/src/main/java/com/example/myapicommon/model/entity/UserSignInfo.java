package com.example.myapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户签到表
 * @TableName user_sign_info
 */
@TableName(value ="user_sign_info")
@Data
public class UserSignInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 签到用户id
     */
    private Long userId;

    /**
     * 连续签到天数
     */
    private Integer signIn;

    /**
     * 积分数
     */
    private Integer integral;

    /**
     * 0未签到 1签到
     */
    private Long status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}