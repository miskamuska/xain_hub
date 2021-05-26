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
@ApiModel("添加公告实体类")
public class AddNoticeRequest {

    @ApiModelProperty(value = "公告标题")
    @NotBlank(message = "标题不能为空")
    private String noticeTheme;

    @ApiModelProperty(value = "公告内容")
    @NotBlank(message = "公告内容不能为空")
    private String noticeContent;
}
