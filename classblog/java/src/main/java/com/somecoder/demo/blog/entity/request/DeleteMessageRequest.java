package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("删除消息实体类")
public class DeleteMessageRequest {

    @ApiModelProperty(value = "消息主键")
    private String messageId;
}
