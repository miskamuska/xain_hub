package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("回复请求体")
public class AddReplyRequest {

    @ApiModelProperty("评论主键")
    private String commentId;

    @ApiModelProperty("被回复的用户")
    private String toUserId;

    @ApiModelProperty("回复内容")
    private String reply;
}
