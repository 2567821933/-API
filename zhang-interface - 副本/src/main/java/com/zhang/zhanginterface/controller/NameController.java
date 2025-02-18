package com.zhang.zhanginterface.controller;


import com.example.zhangclientsdk.medol.User;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称： zhang
 * @author zhang
 */
@RestController
@RequestMapping("/name1")
public class NameController {



    @GetMapping("/try")
    public String getTry( HttpServletRequest request) {

        String zhang = request.getHeader("zhang");


        return "调用功能路由成功,请求头是："+zhang;
    }


    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request) {



        name = "Get 你的名字是：" + name;
        return name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {

        name = "Post 你的名字是：" + name;
        return name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");

        // 这里需要去库里面查一下是否禁用了这个点key

        // 校验accesskey,从数据库中获取
        // 2. 随机数校验
        // 3. 时间戳不超过5分钟
        // 4. 通过body， 拼接出sign验证，关于 secretKey,是从数据库中查出的。

        // 5. 判断签名一致。
        System.out.println(user.getUsername());
        // 调用成功后次数+1

        return "Post 你的名字是：" + user.getUsername();
    }
}
