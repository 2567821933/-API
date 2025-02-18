package com.yupi.project.service.inner;

import com.example.myapicommon.model.entity.User;
import com.example.myapicommon.service.InnerUserService;
import com.yupi.project.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


@DubboService
public class innerUserServiceImpl implements InnerUserService {

    @Resource
    UserService userService;

    /**
     * 获取ak对应的用户
     * @param accessKey
     * @return
     */
    @Override
    public User getUserByAccessKey(String accessKey) {
        return userService.getUserByAK(accessKey);
    }
}
