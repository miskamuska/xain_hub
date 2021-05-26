package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("删除学生账号实体类")
public class DeleteStudentRequest {

    @ApiModelProperty(value = "用户主键")
    private String userId;
}
