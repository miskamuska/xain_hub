package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("注册实体类（加验证码）")
public class RegisterCodeRequest {

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("注册实体类")
    private RegisterRequest registerRequest;
}
