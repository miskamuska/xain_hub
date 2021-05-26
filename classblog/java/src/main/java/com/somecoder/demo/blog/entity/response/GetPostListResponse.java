package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021/1/28
 */
@Data
@ApiModel("获取文章列表响应")
public class GetPostListResponse {

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
    private Boolean isTop;

    @ApiModelProperty(value = "点赞数")
    private int upvoteCount;

    @ApiModelProperty(value = "评论数")
    private int commentCount;

    @ApiModelProperty(value = "文章发布时间")
    private String updateTime;
}
