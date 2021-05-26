package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("点赞实体类")
public class UpvoteRequest {

    @ApiModelProperty(value = "帖子主键")
    private String postId;
}
