package com.yupi.project.controller;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.spring.util.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myapicommon.exception.BusinessException;
import com.example.myapicommon.exception.ErrorCode;
import com.example.myapicommon.model.entity.InterfaceInfo;
import com.example.myapicommon.model.entity.UserInterfaceInfo;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.model.vo.InterfaceInfVO;
import com.yupi.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {


    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;


    /**
     * 查询用户某接口的调用次数。
     * @return
     */
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfVO>> getTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        // 通过id进行分组
        Map<Long, List<UserInterfaceInfo>> collect = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.in("id", collect.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(interfaceInfoQueryWrapper);
        // 转成vo
        if(CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "参数为null");
        }
        List<InterfaceInfVO> interfaceInfVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfVO interfaceInfVO = new InterfaceInfVO();
            // spring 框架里的，可以把一个对象拷贝到另一个对象
            BeanUtil.copyProperties(interfaceInfo, interfaceInfo);
            int totallNum = collect.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfVO.setTotallNum(totallNum);
            return interfaceInfVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfVOList);
    }
}
