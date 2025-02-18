package com.zhang.zhanginterface.controller;


import com.example.myapicommon.exception.BusinessException;
import com.example.myapicommon.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class ServiceController {

    // 获取 zhang 这个字符串
    @GetMapping("/name")
    public String getName(HttpServletRequest request) {
        // 流量染色判断
        getAdminByBlue(request);
        return "zhang";
    }







    // 染色校验
    public void getAdminByBlue(HttpServletRequest request) {
        String header = request.getHeader("X-Traffic-Color");
        if(!"blue".equals(header)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "流量未染色");
        }
    }

}
