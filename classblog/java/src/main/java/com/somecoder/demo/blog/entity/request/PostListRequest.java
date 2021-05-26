package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("分页请求实体类")
public class PostListRequest {

    @ApiModelProperty(value = "当前页数")
    private long current;

    @ApiModelProperty(value = "当前页最大条数")
    private long size;
}
