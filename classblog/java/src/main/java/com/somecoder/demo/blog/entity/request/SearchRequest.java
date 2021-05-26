package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("管理员帖子模糊搜索实体类")
public class SearchRequest {

    @ApiModelProperty(value = "帖子标题")
    private String postTheme;

    @ApiModelProperty("分页请求")
    private PostListRequest postListRequest;
}
