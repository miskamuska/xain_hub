package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("获取作者文章列表")
public class GetThePostListRequest {

    @ApiModelProperty("用户主键")
    private String userId;

    @ApiModelProperty("搜索关键字")
    private String keyword;

    @ApiModelProperty(value = "分页请求参数")
    private PostListRequest pageRequest;
}
