package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("删除评论实体类")
public class DeleteCommentRequest {

    @ApiModelProperty(value = "评论主键")
    private String commentId;
}
