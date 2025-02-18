package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myapicommon.exception.BusinessException;
import com.example.myapicommon.exception.ErrorCode;
import com.example.myapicommon.model.entity.InterfaceInfo;
import com.example.myapicommon.model.entity.User;
import com.example.myapicommon.model.entity.UserInterfaceInfo;
import com.example.zhangclientsdk.client.ZhApiClient;
import com.google.gson.Gson;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.*;
import com.yupi.project.constant.CommonConstant;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.yupi.project.model.enums.InterfaceInfoEnum;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  api 接口操作
 *
 * @author yupi
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceinfoService;

    @Resource
    private UserService userService;

    @Resource
    private ZhApiClient zhApiClient;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceinfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceinfoAddRequest, HttpServletRequest request) {
        if (interfaceinfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceinfoAddRequest, interfaceinfo);
        // 参数校验
        interfaceinfoService.validInterfaceInfo(interfaceinfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceinfo.setUserId(loginUser.getId());
        boolean result = interfaceinfoService.save(interfaceinfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceinfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceinfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceinfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceinfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceinfoUpdateRequest == null || interfaceinfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceinfoUpdateRequest, interfaceinfo);
        // 参数校验
        interfaceinfoService.validInterfaceInfo(interfaceinfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceinfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceinfoService.updateById(interfaceinfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfo = interfaceinfoService.getById(id);
        return ResultUtils.success(interfaceinfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceinfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceinfoQueryRequest) {
        InterfaceInfo interfaceinfoQuery = new InterfaceInfo();
        if (interfaceinfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceinfoQueryRequest, interfaceinfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceinfoQuery);
        List<InterfaceInfo> interfaceinfoList = interfaceinfoService.list(queryWrapper);
        return ResultUtils.success(interfaceinfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceinfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceinfoQueryRequest, HttpServletRequest request) {
        if (interfaceinfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceinfoQueryRequest, interfaceinfoQuery);
        long current = interfaceinfoQueryRequest.getCurrent();
        long size = interfaceinfoQueryRequest.getPageSize();
        String sortField = interfaceinfoQueryRequest.getSortField();
        String sortOrder = interfaceinfoQueryRequest.getSortOrder();
        String description = interfaceinfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceinfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceinfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "content", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceinfoPage = interfaceinfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceinfoPage);
    }

    /**
     * 发布接口，仅管理员可用
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        if(idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数异常");
        }

        InterfaceInfo interfaceInfo = interfaceinfoService.getById(idRequest.getId());
        if(interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口不存在");
        }

        //todo 判断该接口是否可以调用。
        com.example.zhangclientsdk.medol.User user = new com.example.zhangclientsdk.medol.User();
        user.setUsername("zhangyuhao");
//        String usernameByPost = zhApiClient.getUsernameByPost(user);
//        if(StringUtils.isBlank(usernameByPost)) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口调用失败");
//        }

        // 仅管理员可修改
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setId(idRequest.getId());
        interfaceInfo1.setStatus(InterfaceInfoEnum.ONLINE.getValue()); // 改成上上线接口
        boolean b = interfaceinfoService.updateById(interfaceInfo1);
        if(!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.success(b);


    }

    /**
     * 下线接口，仅管理员可用
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        // 校验该接口是否存在
        InterfaceInfo interfaceInfo = interfaceinfoService.getById(idRequest.getId());
        if(interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }

        // 将该接口设置为0
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setId(idRequest.getId());
        interfaceInfo1.setStatus(InterfaceInfoEnum.OFFLINE.getValue());
        boolean b = interfaceinfoService.updateById(interfaceInfo1);
        if(!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "关闭接口失败");
        }

        return ResultUtils.success(true);
    }


    /**
     *  测试调用，这里直接调用了目标api，需要用到网关。
     *  方式，传过来接口名称，通过名称获取地址
     */
    @PostMapping("/invoke")
    public BaseResponse<String> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                      HttpServletRequest request) {
        // 判空
       if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
           throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
       }

        // 获取请求地址
        Long id = interfaceInfoInvokeRequest.getId();
        InterfaceInfo byId = interfaceinfoService.getById(id);
        if(byId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"该接口不存在");
        }
        if(byId.getStatus() == InterfaceInfoEnum.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口已关闭");
        }
        String url = byId.getUrl();
        // 调用
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        ZhApiClient tempClient = new ZhApiClient(accessKey, secretKey);

        Gson gson = new Gson();
        com.example.zhangclientsdk.medol.User user = new com.example.zhangclientsdk.medol.User();


        // todo 这里之后要用到锁
        // 判断是否有调用次数
        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<>();
        userInterfaceInfoQueryWrapper.eq("userId",loginUser.getId());
        userInterfaceInfoQueryWrapper.eq("interfaceInfoId", byId.getId());
        UserInterfaceInfo one = userInterfaceInfoService.getOne(userInterfaceInfoQueryWrapper);
        if(one == null) {
            UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
            userInterfaceInfo.setUserId(loginUser.getId());
            userInterfaceInfo.setInterfaceInfoId(byId.getId());
            userInterfaceInfo.setLeftNum(20);
            userInterfaceInfo.setTotalNum(0);
            boolean save = userInterfaceInfoService.save(userInterfaceInfo);
            if(!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建接口调用项失败");
            }
        }else {
            Integer leftNum = one.getLeftNum();
            if(leftNum <= 0) {
                // 调用次数不足
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口调用次数不足");
            }
        }
        String rel = "";
        if(byId.getMethod().equals("GET")) {
            rel = tempClient.getRequestBody(user, byId.getUrl(), interfaceInfoInvokeRequest.getMap());
        } else if(byId.getMethod().equals("POST")){
            rel = tempClient.getRequestBodyByPost(user,byId.getUrl(), interfaceInfoInvokeRequest.getMap());
        }

//        //todo 之后用户测试接口固定方法名更改为根据测试地址来调用。
//        String usernameByPost = tempClient.getUsernameByPost(user, url);

       //  接口调用次数+1, 剩余-1
       boolean invoke = userInterfaceInfoService.invoke(byId.getId(), loginUser.getId());
        if(!invoke) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口调用次数修改失败");
        }

        return ResultUtils.success(rel);
    }

    // endregion

}
