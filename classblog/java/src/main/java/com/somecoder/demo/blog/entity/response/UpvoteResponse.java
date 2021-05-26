package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("点赞响应")
public class UpvoteResponse {

    @ApiModelProperty(value = "点赞人数")
    private Integer likeCount;
}
