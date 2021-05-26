package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("未读消息数响应")
public class MessageCountResponse {

    @ApiModelProperty("未读消息数")
    private Integer messageCount;
}
