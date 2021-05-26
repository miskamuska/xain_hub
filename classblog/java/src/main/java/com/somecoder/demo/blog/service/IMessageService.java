package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.request.DeleteMessageRequest;
import com.somecoder.demo.blog.entity.request.GetCollectMessageListRequest;
import com.somecoder.demo.blog.entity.request.GetCommentMessageListRequest;
import com.somecoder.demo.blog.entity.request.GetUpvoteMessageListRequest;
import com.somecoder.demo.blog.entity.response.GetCollectMessageListResponse;
import com.somecoder.demo.blog.entity.response.GetCommentMessageListResponse;
import com.somecoder.demo.blog.entity.response.GetUpvoteMessageListResponse;
import com.somecoder.demo.blog.entity.response.MessageCountResponse;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface IMessageService extends IService<Message> {
    /**
     * 获取点赞消息列表
     *
     * @param userId 用户主键
     * @param getUpvoteMessageListRequest 获取点赞消息列表请求
     * @return Page<GetUpvoteMessageListResponse> 点赞消息列表
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<GetUpvoteMessageListResponse> getUpvoteMessageList(
            String userId, GetUpvoteMessageListRequest getUpvoteMessageListRequest
    );

    /**
     * 获取收藏消息列表
     *
     * @param userId 用户主键
     * @param getCollectMessageListRequest 获取收藏消息列表请求
     * @return Page<GetCollectMessageListResponse> 收藏消息列表
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<GetCollectMessageListResponse> getCollectMessageList(
            String userId, GetCollectMessageListRequest getCollectMessageListRequest
    );

    /**
     * 获取评论消息列表
     *
     * @param userId 用户主键
     * @param getCommentMessageListRequest 获取评论消息列表请求
     * @return Page<GetCommentMessageListResponse> 收藏消息列表
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<GetCommentMessageListResponse> getCommentMessageList(
            String userId, GetCommentMessageListRequest getCommentMessageListRequest
    );

    /**
     * 删除消息
     *
     * @param userId 用户主键
     * @param deleteMessageRequest 删除消息请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void deleteMessage(String userId, DeleteMessageRequest deleteMessageRequest);

    /**
     * 未读消息总数
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    MessageCountResponse countMessage(String userId);

    /**
     * 未读评论和回复消息数
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    MessageCountResponse countCommentAndReply(String userId);

    /**
     * 点赞消息数
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    MessageCountResponse countUpvote(String userId);

    /**
     * 收藏消息数
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    MessageCountResponse countCollect(String userId);

    /**
     * 清空评论和回复消息数
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    void clearCommentAndReplyCount(String userId);

    /**
     * 清空点赞消息数
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    void clearUpvoteCount(String userId);

    /**
     * 清空收藏消息数
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    void clearCollectCount(String userId);
}
