package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("修改帖子实体类")
public class ModifyPostRequest {

    @ApiModelProperty(value = "帖子主键")
    private String postId;

    @ApiModelProperty(value = "分类主题")
    private String sortTheme;
}
