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
@ApiModel("显示评论响应类")
public class ShowCommentResponse {

    @ApiModelProperty("评论主键")
    private String commentId;

    @ApiModelProperty("用户头像")
    private String headPicUrl;

    @ApiModelProperty("某一条评论的用户姓名")
    private String name;

    @ApiModelProperty("用户主键")
    private String userId;

    @ApiModelProperty("某一条评论")
    private String comment;

    @ApiModelProperty("评论时间")
    private String createTime;

    @ApiModelProperty("回复")
    private List<ReplyResponse> replyList;

    @ApiModelProperty("是否是我的评论")
    private Boolean isMyComment;
}


