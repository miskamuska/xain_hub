package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("获取我的收藏列表响应")
public class GetMyCollectListResponse {

    @ApiModelProperty(value = "作者姓名")
    private String name;

    @ApiModelProperty(value = "帖子")
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
    private Integer upvoteCount;

    @ApiModelProperty(value = "收藏数")
    private Integer collectCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "文章发布时间")
    private String updateTime;

    @ApiModelProperty(value = "当前页")
    private Long current;

    @ApiModelProperty(value = "当前页数目")
    private Long size;

    @ApiModelProperty(value = "总页数")
    private Long pages;

    @ApiModelProperty(value = "文章总数")
    private Long total;
}
