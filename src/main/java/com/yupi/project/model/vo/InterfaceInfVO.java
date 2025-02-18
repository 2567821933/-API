package com.yupi.project.model.vo;


import com.example.myapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 接口信息封装类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterfaceInfVO extends InterfaceInfo implements Serializable {


    /**
     * 调用接口次数
     */
    private int totallNum;

}
