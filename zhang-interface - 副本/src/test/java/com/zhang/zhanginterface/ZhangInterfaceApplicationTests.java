package com.zhang.zhanginterface;

import com.example.zhangclientsdk.client.ZhApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ZhangInterfaceApplicationTests {

    @Resource
    private ZhApiClient zhApiClient;


    @Test
    void contextLoads() {
        String zhang = zhApiClient.getNameByGet("zhang");
        System.out.println(zhang);
    }

}
