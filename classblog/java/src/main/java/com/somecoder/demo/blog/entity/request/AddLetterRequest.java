package com.somecoder.demo.blog.entity.request;

/**
 * @author ：lishan
 * @date ：Created in 2021/2/26 9:25
 * @description：TODO
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("添加私信请求类")
public class AddLetterRequest {
    @ApiModelProperty("接收私信的人")
    private String toUserId;

    @ApiModelProperty("私信内容")
    private String content;
}
