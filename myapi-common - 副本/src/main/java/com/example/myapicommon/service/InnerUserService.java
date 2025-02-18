package com.example.myapicommon.service;

import com.example.myapicommon.model.entity.User;

public interface InnerUserService {

    /**
     * 获取accessKey所属的用户信息
     * @param accessKey
     * @return
     */
    User getUserByAccessKey(String accessKey);
}
