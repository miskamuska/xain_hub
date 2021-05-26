package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("删除帖子（管理员端）")
public class DeletePostRequest {

    @ApiModelProperty(value = "帖子主键")
    private String postId;
}
