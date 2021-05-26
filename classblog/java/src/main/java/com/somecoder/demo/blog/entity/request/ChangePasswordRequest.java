package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("修改密码实体类")
public class ChangePasswordRequest {

    @ApiModelProperty(value = "原密码")
    @NotBlank(message = "请输入原密码")
    private String password;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "请输入新密码")
    private String newPassword;

    @ApiModelProperty(value = "第二次新密码")
    @NotBlank(message = "请再次输入新密码")
    private String newPasswordAgain;
}
