package com.yupi.project.controller;


import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.service.UserService;
import com.yupi.project.service.UserSignInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sign")
public class UserSignInfoController {

    @Resource
    UserService userService;

    @Resource
    UserSignInfoService userSignInfoService;


    // 签到功能
    @GetMapping("/dosign")
    public BaseResponse<String> getDoSign(HttpServletRequest request) {
        String doSign = userSignInfoService.getDoSign(request);

        return ResultUtils.success(doSign);
    }

    // 购买积分
    @GetMapping("/addNum")
    public BaseResponse<String> addLeftNum(HttpServletRequest request, @RequestParam Long interfaceId, @RequestParam Integer num) {
        Integer integer = userSignInfoService.addInterfaceNumByInteger(userService.getLoginUser(request).getId(), interfaceId, num);
        return ResultUtils.success("购买成功");
    }
}
