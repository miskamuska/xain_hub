package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lishan
 * @since 2021-01-28
 */
@ApiModel("忘记密码实体类")
@Data
public class ForgetPwdRequest {

    @ApiModelProperty(value = "学号")
    private String loginId;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
