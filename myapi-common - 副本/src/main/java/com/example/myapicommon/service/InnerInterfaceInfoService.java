package com.example.myapicommon.service;

import com.example.myapicommon.model.entity.InterfaceInfo;

public interface InnerInterfaceInfoService {

    /**
     * 通过请求路径获取接口信息
     */
    InterfaceInfo getInterfaceInfoByUrl(String url);
}
