package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("获取文章详情请求类")
public class GetPostDetailRequest {

    @ApiModelProperty(value = "文章主键")
    private String postId;
}
