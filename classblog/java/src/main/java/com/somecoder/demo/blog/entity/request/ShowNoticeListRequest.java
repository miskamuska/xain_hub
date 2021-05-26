package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("显示公告列表实体类")
public class ShowNoticeListRequest {

    @ApiModelProperty(value = "查询标题")
    private String theme;

    @ApiModelProperty(value = "查询内容")
    private String content;

    @ApiModelProperty("分页请求")
    private PostListRequest postListRequest;
}
