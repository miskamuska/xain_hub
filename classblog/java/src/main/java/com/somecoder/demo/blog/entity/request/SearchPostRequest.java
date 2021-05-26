package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("搜索文章实体类")
public class SearchPostRequest {

    @ApiModelProperty("文章主题关键字")
    private String keyword;

    @ApiModelProperty("文章主键")
    private String sortId;

    @ApiModelProperty("分页请求")
    private PostListRequest postListRequest;
}
