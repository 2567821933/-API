package com.yupi.project.service;

import com.example.myapicommon.model.entity.UserSignInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author Lenovo
* @description 针对表【user_sign_info(用户签到表)】的数据库操作Service
* @createDate 2025-02-13 19:44:57
*/
public interface UserSignInfoService extends IService<UserSignInfo> {
    /**
     * 签到功能
     * true 签到成功
     * false 用户已签到
     */
    String getDoSign(HttpServletRequest request);

    /**
     * 连续签到更新
     */
    Boolean doUpdateSign();

    /**
     * 积分转换成接口调用功能次数（10 转换成 1次）
     * userId 用户id
     * interfaceInfoId 接口Id
     * num 转化的次数
     */

    Integer addInterfaceNumByInteger(Long userId, Long interfaceInfoId, Integer num);
}
