package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("某一条评论下的回复")
public class ReplyResponse {

    @ApiModelProperty("该回复所在的评论")
    private String commentId;

    @ApiModelProperty("回复主键")
    private String replyId;

    @ApiModelProperty("回复的用户头像")
    private String fromHeadPicUrl;

    @ApiModelProperty("回复的用户")
    private String fromName;

    @ApiModelProperty("回复的用户主键")
    private String fromUserId;

    @ApiModelProperty("被回复的用户头像")
    private String toHeadPicUrl;

    @ApiModelProperty("被回复的用户")
    private String toName;

    @ApiModelProperty("被回复的用户主键")
    private String toUserId;

    @ApiModelProperty("回复内容")
    private String revert;

    @ApiModelProperty("回复时间")
    private String createTime;

    @ApiModelProperty("是否是我的回复")
    private Boolean isMyReply;

    @Override
    public String toString() {
        return "ReplyResponse{" +
                "commentId=" + commentId +
                ", fromHeadPicUrl='" + fromHeadPicUrl + '\'' +
                ", fromName='" + fromName + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", toHeadPicUrl='" + toHeadPicUrl + '\'' +
                ", toName='" + toName + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", revert='" + revert + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
