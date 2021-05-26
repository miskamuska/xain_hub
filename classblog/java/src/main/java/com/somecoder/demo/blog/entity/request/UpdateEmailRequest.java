package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@ApiModel("修改邮箱请求")
@Data
public class UpdateEmailRequest {

    @ApiModelProperty("新邮箱")
    private String newEmail;
}
