package com.yupi.project.mapper;

import com.example.myapicommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【user_interface_info(user_interface_info)】的数据库操作Mapper
* @createDate 2025-01-04 17:11:35
* @Entity com.yupi.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




