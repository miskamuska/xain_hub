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
@ApiModel("注册实体类")
public class RegisterRequest {

    @ApiModelProperty(value = "学号")
    @NotBlank(message = "请输入您的学号")
    private String studentNumber;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "请输入您的姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入您的密码")
    private String password;

    @ApiModelProperty(value = "第二次密码")
    @NotBlank(message = "请再次输入您的密码")
    private String password1;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "请输入您的邮箱")
    private String email;

}