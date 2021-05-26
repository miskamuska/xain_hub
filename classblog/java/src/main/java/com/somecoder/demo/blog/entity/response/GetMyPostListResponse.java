package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * 2021/1/28
 */
@Data
@ApiModel("获取我的帖子响应")
public class GetMyPostListResponse {

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

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "点赞数")
    private Integer upvoteCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;
}
