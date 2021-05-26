package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("评论请求类")
public class AddCommentRequest {

    @ApiModelProperty("被评论的文章主键")
    private String postId;

    @ApiModelProperty("评论内容")
    private String comment;
}
