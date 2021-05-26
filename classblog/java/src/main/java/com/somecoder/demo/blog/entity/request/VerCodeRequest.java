package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("验证码实体类")
public class VerCodeRequest {

    @ApiModelProperty("验证码")
    @NotBlank(message = "请输入验证码")
    private String code;
}
