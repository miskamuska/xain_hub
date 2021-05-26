package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("显示待审核学生列表实体类")
public class ShowReviewStudentRequest {

    @ApiModelProperty(value = "查询学生姓名")
    private String studentName;

    @ApiModelProperty(value = "查询学生学号")
    private String studentNumber;

    @ApiModelProperty("分页请求")
    private PostListRequest postListRequest;
}
