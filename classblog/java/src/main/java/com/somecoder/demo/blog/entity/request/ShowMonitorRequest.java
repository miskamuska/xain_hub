package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("显示班长实体类")
public class ShowMonitorRequest {

    @ApiModelProperty("账号")
    private String loginId;
}
