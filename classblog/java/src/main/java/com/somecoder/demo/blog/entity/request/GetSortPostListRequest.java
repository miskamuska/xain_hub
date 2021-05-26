package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("获取分类帖子列表实体类")
public class GetSortPostListRequest {

    @ApiModelProperty("搜索关键字")
    private String keyword;

    @ApiModelProperty(value = "分类主键")
    private String sortId;

    @ApiModelProperty(value = "分页请求参数")
    private PostListRequest pageRequest;
}
