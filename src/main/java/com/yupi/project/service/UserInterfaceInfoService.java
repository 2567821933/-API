package com.yupi.project.service;

import com.example.myapicommon.model.entity.UserInterfaceInfo;

import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Lenovo
* @description 针对表【user_interface_info(user_interface_info)】的数据库操作Service
* @createDate 2025-01-04 17:11:35
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验
     * @param userInterfaceInfo
     * @param add 是否允许为空
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);


    /**
     * 用户调用次数+1, 剩余次数-1
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invoke(long interfaceInfoId, long userId);

    /**
     * 添加新的接口项
     */


}
