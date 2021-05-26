package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("审核学生账号实体类")
public class ReviewStudentAccountsRequest {

    @ApiModelProperty(value = "待审核学号")
    private String studentId;

    @ApiModelProperty(value = "是否通过(是/否)")
    private String isPass;
}
