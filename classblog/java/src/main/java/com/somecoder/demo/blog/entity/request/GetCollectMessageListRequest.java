package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("获取收藏消息实体类")
public class GetCollectMessageListRequest {

    @ApiModelProperty(value = "分页请求参数")
    private PostListRequest pageRequest;
}
