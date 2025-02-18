package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myapicommon.exception.BusinessException;
import com.example.myapicommon.exception.ErrorCode;
import com.example.myapicommon.model.entity.UserInterfaceInfo;
import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Lenovo
* @description 针对表【user_interface_info(user_interface_info)】的数据库操作Service实现
* @createDate 2025-01-04 17:11:35
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Resource
    UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //    private String name;
        //    private String description;
        //    private String url;
        //    private String requestHeader;
        //    private String responseHeader;
        //    private String method;



        // 创建时，所有参数必须非空
        if (add) {
            if(userInterfaceInfo.getInterfaceInfoId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
            }
        }


        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能为0");
        }

    }

    @Override
    public boolean invoke(long interfaceInfoId, long userId) {
        // 校验
        if(interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
//        // 判断是否存在调用
//        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<>();
//        userInterfaceInfoQueryWrapper.eq("interfaceInfoId",interfaceInfoId);
//        userInterfaceInfoQueryWrapper.eq("userId",userId);
//
//        UserInterfaceInfo one = getOne(userInterfaceInfoQueryWrapper);
//        if(one != null) {
//            //存在，次数+1
//            one.setTotalNum(one.getTotalNum() + 1);
//            boolean b = updateById(one);
//            if(!b) {
//                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加次数失败");
//            }
//        }else {
//            //不存在，在数据库中添加，次数为1
//            UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
//            userInterfaceInfo.setUserId(userId);
//            userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
//            userInterfaceInfo.setTotalNum(1);
//            boolean save = save(userInterfaceInfo);
//            if(!save) {
//                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"保存数据失败");
//            }
//        }
        //todo 先查询leftNum的值，确保>0



        //todo 这里需要加锁，防止高并发下的调用多次。锁，加到前面了，不用了，完成。
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .setSql("totalNum = totalNum + 1") // 总调用次数 +1
                .setSql("leftNum = leftNum - 1")   // 剩余调用次数 -1
                .gt("leftNum", 0)                  // 剩余调用次数 > 0
                .eq("userId", userId)
                .eq("interfaceInfoId", interfaceInfoId);// 根据 id 更新
        return userInterfaceInfoMapper.update(null, updateWrapper) > 0;
    }
}




