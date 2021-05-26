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
@ApiModel("发布帖子实体类")
public class WritePostRequest {

    @ApiModelProperty(value = "分类主题")
    private String sortTheme;

    @ApiModelProperty(value = "帖子标题")
    @NotBlank(message = "标题不能为空")
    private String postTheme;

    @ApiModelProperty(value = "帖子内容")
    @NotBlank(message = "内容不能为空")
    private String postContent;
}
