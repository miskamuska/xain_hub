package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("添加班长请求实体类")
public class AddMonitorRequest {

    @ApiModelProperty("班长学号")
    private String studentId;

    @ApiModelProperty("班长姓名")
    private String studentName;
}
