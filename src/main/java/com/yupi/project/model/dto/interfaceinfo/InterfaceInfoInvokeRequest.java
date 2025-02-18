package com.yupi.project.model.dto.interfaceinfo;


import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */

    private Long id;

    private String userRequestParams;

    private Map<String,Object> map;

}
