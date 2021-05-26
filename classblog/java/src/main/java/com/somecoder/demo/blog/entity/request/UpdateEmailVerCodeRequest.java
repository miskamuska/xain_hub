package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("修改邮箱验证验证码")
public class UpdateEmailVerCodeRequest {

    @ApiModelProperty("邮箱")
    private String newEmail;

    @ApiModelProperty("验证码")
    private String code;
}
