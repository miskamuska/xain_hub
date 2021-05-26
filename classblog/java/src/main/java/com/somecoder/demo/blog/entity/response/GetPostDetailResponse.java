package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("文章详情页响应类")
public class GetPostDetailResponse {

    @ApiModelProperty(value = "头像")
    private String headPicUrl;

    @ApiModelProperty(value = "作者姓名")
    private String name;

    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "帖子")
    private String postId;

    @ApiModelProperty(value = "文章内容")
    private String postContent;

    @ApiModelProperty(value = "文章主题")
    private String postTheme;

    @ApiModelProperty(value = "文章分类")
    private String sortTheme;

    @ApiModelProperty(value = "点赞数和点赞人响应")
    private ShowLikeResponse showLikeResponse;

    @ApiModelProperty(value = "收藏数")
    private Integer collectCount;

    @ApiModelProperty(value = "评论")
    private List<ShowCommentResponse> commentList;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isUpvote;

    @ApiModelProperty(value = "是否收藏")
    private Boolean isCollect;

    @ApiModelProperty(value = "文章发布时间")
    private String updateTime;

    @Override
    public String toString() {
        return "GetPostDetailResponse{" +
                "headPicUrl='" + headPicUrl + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", id=" + postId +
                ", postContent='" + postContent + '\'' +
                ", postTheme='" + postTheme + '\'' +
                ", sortTheme='" + sortTheme + '\'' +
                ", showLikeResponse=" + showLikeResponse +
                ", collectCount=" + collectCount +
                ", commentList=" + commentList +
                ", commentCount=" + commentCount +
                ", isUpvote=" + isUpvote +
                ", isCollect=" + isCollect +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
