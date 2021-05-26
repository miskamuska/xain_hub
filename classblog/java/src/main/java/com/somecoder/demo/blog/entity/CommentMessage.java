package com.somecoder.demo.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CommentMessage对象", description="评论和回复信息表")
public class CommentMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "评论/回复消息主键")
    private String commentMessageId;

    @ApiModelProperty(value = "文章主键")
    private String postId;

    @ApiModelProperty(value = "发消息人")
    private String fromUserId;

    @ApiModelProperty(value = "收消息人")
    private String toUserId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "操作")
    private String operation;

    @ApiModelProperty(value = "是否已读")
    private Boolean isRead;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "笔记")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
