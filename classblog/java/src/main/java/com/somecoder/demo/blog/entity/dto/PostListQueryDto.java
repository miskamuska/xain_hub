package com.somecoder.demo.blog.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
public class PostListQueryDto {

    @ApiModelProperty("文章主题关键字")
    private String keyword;

    @ApiModelProperty("分类主键")
    private String sortId;
}
