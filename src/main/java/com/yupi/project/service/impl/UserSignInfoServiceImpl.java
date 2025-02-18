package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myapicommon.exception.BusinessException;
import com.example.myapicommon.exception.ErrorCode;
import com.example.myapicommon.model.entity.User;
import com.example.myapicommon.model.entity.UserInterfaceInfo;
import com.example.myapicommon.model.entity.UserSignInfo;
import com.yupi.project.mapper.UserSignInfoMapper;
import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.project.service.UserService;
import com.yupi.project.service.UserSignInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author Lenovo
* @description 针对表【user_sign_info(用户签到表)】的数据库操作Service实现
* @createDate 2025-02-13 19:44:57
*/
@Service
public class UserSignInfoServiceImpl extends ServiceImpl<UserSignInfoMapper, UserSignInfo>
    implements UserSignInfoService{

    @Resource
    UserService userService;

    @Resource
    UserInterfaceInfoService userInterfaceInfoService;

    // 自己引用自己造成了循环依赖问题
//    @Resource
//    UserSignInfoService userSignInfoService;

    @Override
    public String getDoSign(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        // 查找是否存在用户签到的条目
        QueryWrapper<UserSignInfo> userSignInfoQueryWrapper = new QueryWrapper<>();
        userSignInfoQueryWrapper.eq("userId", loginUser.getId());
        // 获得目标签到条目
        UserSignInfo one = getOne(userSignInfoQueryWrapper);

        // 创建要修改的签到状态


        // 更新字符
        String integralStr = "";
        if(one == null) {
            saveInUserSign(loginUser.getId());
            // 签到并添加积分
            UpdateWrapper<UserSignInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .setSql("status = 1")  //设置签到
                    .setSql("integral = integral + 100")
                    .eq("userId", loginUser.getId());
        } else {

            // 判断是否已经签到
            Long status = one.getStatus();
            if(status == 1L) {
                return "用户已签到";
            }
            // 判断是否连续签到
            int signIn = one.getSignIn()+1;
            // 倍率
            int num = signIn/5;
            if(signIn%5 == 0) {
                // 更新积分
                integralStr = 100 + 20 * num + integralStr;
            } else {
                integralStr = 100 + integralStr;
            }

            // 签到并添加积分
            UpdateWrapper<UserSignInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .setSql("status = 1")  //设置签到
                    .setSql("integral = integral + "+integralStr)
                    .eq("userId", loginUser.getId());
            boolean update = update(null, updateWrapper);
            if(!update) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建签到条目失败");
            }
        }


        return "签到成功！积分+" + integralStr + "!";
    }



    @Override
    public Boolean doUpdateSign() {
        // 条目存在
        // 签到的持续天数+1
        UpdateWrapper<UserSignInfo> userSignInfoUpdateWrapper = new UpdateWrapper<>();
        userSignInfoUpdateWrapper.setSql("status = 0")
                .setSql("signIn = signIn + 1")
                .eq("status", 1L);
        boolean update = update(userSignInfoUpdateWrapper);
        // 未签到的持续天数变成0
        UpdateWrapper<UserSignInfo> userSignInfoUpdateWrapper1 = new UpdateWrapper<>();
        userSignInfoUpdateWrapper1.setSql("signIn = 0")
                .eq("status", 0L);
        boolean update1 = update(userSignInfoUpdateWrapper1);
        if(!update || !update1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "系统更新失败");
        }

        return true;
    }

    @Override
    public Integer addInterfaceNumByInteger(Long userId, Long interfaceInfoId, Integer num) {
        //权限鉴定
        if(userId <= 0 || interfaceInfoId <= 0 || num <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数异常");
        }
        synchronized (userId) {
            // 得到对应的userSignInfo
            QueryWrapper<UserSignInfo> userSignInfoQueryWrapper = new QueryWrapper<>();
            userSignInfoQueryWrapper.eq("userId", userId);
            UserSignInfo one = getOne(userSignInfoQueryWrapper);
            if(one == null) {
                one = saveInUserSign(userId);
            }
            // 得到对应的userInterfaceInfo
            QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<>();
            userInterfaceInfoQueryWrapper.eq("userId",userId);
            userInterfaceInfoQueryWrapper.eq("interfaceInfoId",interfaceInfoId);
            UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(userInterfaceInfoQueryWrapper);
            if(userInterfaceInfo == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "该接口不存在");
            }

            // 购买次数判定
            if(num * 10 > one.getIntegral()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "购买积分不足");
            }

            // 扣积分，
            String rel = num * 10 + "";
            UpdateWrapper<UserSignInfo> userSignInfoUpdateWrapper = new UpdateWrapper<>();
            userSignInfoUpdateWrapper.setSql("integral = integral -" + rel)
                    .eq("userId",userId);
            boolean update = update(userSignInfoUpdateWrapper);
            //加次数
            UpdateWrapper<UserInterfaceInfo> userInterfaceInfoUpdateWrapper = new UpdateWrapper<>();
            userInterfaceInfoUpdateWrapper.setSql("leftNum = leftNum +" + num)
                    .eq("userId", userId);
            boolean update1 = userInterfaceInfoService.update(userInterfaceInfoUpdateWrapper);
            if(!update1 || !update) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "购买次数失败！！");
            }
        }

        return 1;
    }


    /**
     * userSignInfo 初始化
     * 返回的是保存后返回的值。
     */
    public UserSignInfo saveInUserSign(Long loginUserId) {
        UserSignInfo userSignInfo = new UserSignInfo();
        userSignInfo.setStatus(0L);
        // 创建签到条目
        userSignInfo.setUserId(loginUserId);
        userSignInfo.setSignIn(0);
        userSignInfo.setIntegral(0);
        boolean save = save(userSignInfo);
        if(!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建签到条目失败");
        }
        return userSignInfo;
    }


}




