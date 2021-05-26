package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("重置密码请求体")
public class ResetPasswordRequest {

    @ApiModelProperty(value = "登录主键")
    private String loginId;
}
