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
@ApiModel("添加分类实体类")
public class AddSortRequest {

    @ApiModelProperty(value = "分类标题")
    @NotBlank(message = "分类标题不能为空")
    private String sortTheme;

    @ApiModelProperty(value = "分类介绍")
    @NotBlank(message = "分类介绍不能为空")
    private String sortIntroduction;
}
