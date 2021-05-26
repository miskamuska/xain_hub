package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("分类帖子列表响应")
public class SortPostListResponse {

    @ApiModelProperty(value = "作者姓名")
    private String name;

    @ApiModelProperty(value = "帖子主键")
    private String postId;

    @ApiModelProperty(value = "文章内容")
    private String postContent;

    @ApiModelProperty(value = "文章主题")
    private String postTheme;

    @ApiModelProperty(value = "文章分类")
    private String sortTheme;

    @ApiModelProperty(value = "是否置顶")
    private boolean isTop;

    @ApiModelProperty(value = "文章最后更新时间")
    private String updateTime;
}
