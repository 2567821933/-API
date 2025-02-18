package com.zhang.zhanginterface;

import com.example.zhangclientsdk.client.ZhApiClient;

public class Main {
    public static void main(String[] args) {
        ZhApiClient zhApiClient = new ZhApiClient();
        System.out.println(zhApiClient.getNameByGet("zhang"));

    }
}
