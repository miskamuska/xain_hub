package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("修改密码请求体")
public class UpdatePwdRequest {

    @ApiModelProperty(value = "登录主键")
    @NotBlank(message = "请输入您的账号")
    private String loginId;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入密码")
    private String pwd1;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请再次输入密码")
    private String pwd2;

}
