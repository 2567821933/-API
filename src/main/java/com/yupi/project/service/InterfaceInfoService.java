package com.yupi.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myapicommon.model.entity.InterfaceInfo;

/**
* @author Lenovo
* @description 针对表【interface_info(interface_info)】的数据库操作Service
* @createDate 2025-01-02 12:35:12
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
