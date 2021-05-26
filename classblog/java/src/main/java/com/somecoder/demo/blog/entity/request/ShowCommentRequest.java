package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("显示评论请求体")
public class ShowCommentRequest {

    @ApiModelProperty("文章主键")
    private String postId;
}
