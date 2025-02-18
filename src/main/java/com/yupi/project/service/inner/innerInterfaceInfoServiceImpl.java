package com.yupi.project.service.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myapicommon.model.entity.InterfaceInfo;
import com.example.myapicommon.service.InnerInterfaceInfoService;
import com.yupi.project.service.InterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


@DubboService
public class innerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public InterfaceInfo getInterfaceInfoByUrl(String url) {
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.eq("url",url);
        InterfaceInfo one = interfaceInfoService.getOne(interfaceInfoQueryWrapper);

        return one;
    }
}
