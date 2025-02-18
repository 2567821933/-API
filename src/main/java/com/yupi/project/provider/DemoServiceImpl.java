package com.yupi.project.provider;


import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService{
    @Override
    public String getHello() {
        return "hello";
    }
}
