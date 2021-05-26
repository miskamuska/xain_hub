package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.CommentMessage;
import com.somecoder.demo.blog.entity.Letter;
import com.somecoder.demo.blog.entity.Message;
import com.somecoder.demo.blog.entity.Student;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.DeleteMessageRequest;
import com.somecoder.demo.blog.entity.request.GetCollectMessageListRequest;
import com.somecoder.demo.blog.entity.request.GetCommentMessageListRequest;
import com.somecoder.demo.blog.entity.request.GetUpvoteMessageListRequest;
import com.somecoder.demo.blog.entity.response.GetCollectMessageListResponse;
import com.somecoder.demo.blog.entity.response.GetCommentMessageListResponse;
import com.somecoder.demo.blog.entity.response.GetUpvoteMessageListResponse;
import com.somecoder.demo.blog.entity.response.MessageCountResponse;
import com.somecoder.demo.blog.mapper.*;
import com.somecoder.demo.blog.service.IMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private CommentMessageMapper commentMessageMapper;

    @Resource
    private LetterMapper letterMapper;

    /**
     *
     * 点赞消息列表
     */
    @Override
    public Page<GetUpvoteMessageListResponse> getUpvoteMessageList(
            String userId, GetUpvoteMessageListRequest getUpvoteMessageListRequest
    ) {
        LambdaQueryWrapper<Message> messageListQuery =
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getToUserId, userId)
                        .eq(Message::getOperation, CommonConstant.UPVOTE)
                        .orderByDesc(Message::getCreateTime);
        // 分页
        Page<Message> messageListQueryPage = new Page<>();
        BeanUtils.copyProperties(getUpvoteMessageListRequest.getPageRequest(), messageListQueryPage);
        Page<Message> messagePage = messageMapper.selectPage(messageListQueryPage,messageListQuery);
        List<Message> messageList = messagePage.getRecords();
        Page<GetUpvoteMessageListResponse> getUpvoteMessageListResponsePage = new Page<>();
        BeanUtils.copyProperties(messagePage, getUpvoteMessageListResponsePage);
        // 点赞信息列表
        List<GetUpvoteMessageListResponse> getUpvoteMessageListResponseList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        messageList.forEach(message -> {
            GetUpvoteMessageListResponse getUpvoteMessageListResponse = new GetUpvoteMessageListResponse();
            Student student = studentMapper.selectOne(
                    Wrappers.lambdaQuery(Student.class)
                            .eq(Student::getUserId, message.getFromUserId())
            );
            BeanUtils.copyProperties(message, getUpvoteMessageListResponse);
            getUpvoteMessageListResponse.setKey("url");
            getUpvoteMessageListResponse.setCreateTime(dtf.format(message.getCreateTime()));
            getUpvoteMessageListResponse.setValue(student.getHeadPicUrl());
            getUpvoteMessageListResponse.setPostTheme(postMapper.getPostTheme(message.getPostId()));
            getUpvoteMessageListResponse.setName(student.getName());
            getUpvoteMessageListResponse.setMessageContent("点赞了您的文章");
            getUpvoteMessageListResponseList.add(getUpvoteMessageListResponse);
        });
        getUpvoteMessageListResponsePage.setRecords(getUpvoteMessageListResponseList);
        return getUpvoteMessageListResponsePage;
    }

    /**
     *
     * 收藏消息列表
     */
    @Override
    public Page<GetCollectMessageListResponse> getCollectMessageList(
            String userId, GetCollectMessageListRequest getCollectMessageListRequest
    ) {
        LambdaQueryWrapper<Message> messageListQuery =
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getToUserId, userId)
                        .eq(Message::getOperation, CommonConstant.COLLECT)
                        .orderByDesc(Message::getCreateTime);
        // 分页
        Page<Message> messageListQueryPage = new Page<>();
        BeanUtils.copyProperties(getCollectMessageListRequest.getPageRequest(), messageListQueryPage);
        Page<Message> messagePage = messageMapper.selectPage(messageListQueryPage,messageListQuery);
        List<Message> messageList = messagePage.getRecords();
        Page<GetCollectMessageListResponse> getCollectMessageListResponsePage = new Page<>();
        BeanUtils.copyProperties(messagePage, getCollectMessageListResponsePage);
        // 点赞信息列表
        List<GetCollectMessageListResponse> getCollectMessageListResponseList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        messageList.forEach(message -> {
            GetCollectMessageListResponse getCollectMessageListResponse = new GetCollectMessageListResponse();
            Student student = studentMapper.selectOne(
                    Wrappers.lambdaQuery(Student.class)
                            .eq(Student::getUserId, message.getFromUserId())
            );
            BeanUtils.copyProperties(message, getCollectMessageListResponse);
            getCollectMessageListResponse.setKey("url");
            getCollectMessageListResponse.setCreateTime(dtf.format(message.getCreateTime()));
            getCollectMessageListResponse.setValue(student.getHeadPicUrl());
            getCollectMessageListResponse.setPostTheme(postMapper.getPostTheme(message.getPostId()));
            getCollectMessageListResponse.setName(student.getName());
            getCollectMessageListResponse.setMessageContent("收藏了您的文章");
            getCollectMessageListResponseList.add(getCollectMessageListResponse);
        });
        getCollectMessageListResponsePage.setRecords(getCollectMessageListResponseList);
        return getCollectMessageListResponsePage;
    }

    /**
     *
     * 评论和回复消息列表
     */
    @Override
    public Page<GetCommentMessageListResponse> getCommentMessageList(
            String userId, GetCommentMessageListRequest getCommentMessageListRequest
    ) {
        LambdaQueryWrapper<CommentMessage> commentMessageListQuery =
                Wrappers.lambdaQuery(CommentMessage.class)
                        .eq(CommentMessage::getToUserId, userId)
                        .eq(CommentMessage::getOperation, CommonConstant.COMMENT)
                        .orderByDesc(CommentMessage::getCreateTime)
                        .or()
                        .eq(CommentMessage::getToUserId, userId)
                        .eq(CommentMessage::getOperation, CommonConstant.REPLY)
                        .orderByDesc(CommentMessage::getCreateTime);
        // 分页
        Page<CommentMessage> commentMessageListQueryPage = new Page<>();
        BeanUtils.copyProperties(getCommentMessageListRequest.getPageRequest(), commentMessageListQueryPage);
        Page<CommentMessage> commentMessagePage =
                commentMessageMapper.selectPage(commentMessageListQueryPage, commentMessageListQuery);
        List<CommentMessage> commentMessageList = commentMessagePage.getRecords();
        Page<GetCommentMessageListResponse> getCommentMessageListResponsePage = new Page<>();
        BeanUtils.copyProperties(commentMessagePage, getCommentMessageListResponsePage);
        // 点赞信息列表
        List<GetCommentMessageListResponse> getCommentMessageListResponseList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        commentMessageList.forEach(commentMessage -> {
            GetCommentMessageListResponse getCommentMessageListResponse = new GetCommentMessageListResponse();
            Student student = studentMapper.selectOne(
                    Wrappers.lambdaQuery(Student.class)
                            .eq(Student::getUserId, commentMessage.getFromUserId())
            );
            BeanUtils.copyProperties(commentMessage, getCommentMessageListResponse);
            getCommentMessageListResponse.setKey("url");
            getCommentMessageListResponse.setCreateTime(dtf.format(commentMessage.getCreateTime()));
            getCommentMessageListResponse.setValue(student.getHeadPicUrl());
            getCommentMessageListResponse.setPostTheme(postMapper.getPostTheme(commentMessage.getPostId()));
            getCommentMessageListResponse.setName(student.getName());
            if (commentMessage.getOperation().equals(CommonConstant.REPLY)) {
                getCommentMessageListResponse.setMessageContent("回复了您的评论");
            } else {
                getCommentMessageListResponse.setMessageContent("评论了您的文章");
            }
            getCommentMessageListResponseList.add(getCommentMessageListResponse);
        });
        getCommentMessageListResponsePage.setRecords(getCommentMessageListResponseList);
        return getCommentMessageListResponsePage;
    }

    /**
     * 删除消息
     */
    @Override
    public void deleteMessage(String userId, DeleteMessageRequest deleteMessageRequest) {
        messageMapper.delete(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getMessageId, deleteMessageRequest.getMessageId())
        );
    }

    /**
     * 消息总数
     */
    @Override
    public MessageCountResponse countMessage(String userId) {
        int messageCount = messageMapper.selectCount(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getToUserId, userId)
                        .eq(Message::getIsRead, 0)
        );
        int commentCount = commentMessageMapper.selectCount(
                Wrappers.lambdaQuery(CommentMessage.class)
                        .eq(CommentMessage::getToUserId, userId)
                        .eq(CommentMessage::getIsRead, 0)
        );
        int letterCount = letterMapper.selectCount(
                Wrappers.lambdaQuery(Letter.class)
                        .eq(Letter::getToUserId, userId)
                        .eq(Letter::getIsRead, 0)
        );
        int count = messageCount + letterCount + commentCount;
        MessageCountResponse messageCountResponse = new MessageCountResponse();
        messageCountResponse.setMessageCount(count);
        return messageCountResponse;
    }

    /**
     * 评论和回复消息数
     */
    @Override
    public MessageCountResponse countCommentAndReply(String userId) {
        int messageCount = commentMessageMapper.selectCount(
                Wrappers.lambdaQuery(CommentMessage.class)
                        .eq(CommentMessage::getOperation, CommonConstant.COMMENT)
                        .eq(CommentMessage::getToUserId, userId)
                        .eq(CommentMessage::getIsRead, 0)
                        .or()
                        .eq(CommentMessage::getOperation, CommonConstant.REPLY)
                        .eq(CommentMessage::getToUserId, userId)
                        .eq(CommentMessage::getIsRead, 0)
        );
        MessageCountResponse messageCountResponse = new MessageCountResponse();
        messageCountResponse.setMessageCount(messageCount);
        return messageCountResponse;
    }

    /**
     * 点赞消息数
     */
    @Override
    public MessageCountResponse countUpvote(String userId) {
        int messageCount = messageMapper.selectCount(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getOperation, CommonConstant.UPVOTE)
                        .eq(Message::getToUserId, userId)
                        .eq(Message::getIsRead, 0)
        );
        MessageCountResponse messageCountResponse = new MessageCountResponse();
        messageCountResponse.setMessageCount(messageCount);
        return messageCountResponse;
    }

    /**
     * 收藏消息数
     */
    @Override
    public MessageCountResponse countCollect(String userId) {
        int messageCount = messageMapper.selectCount(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getOperation, CommonConstant.COLLECT)
                        .eq(Message::getToUserId, userId)
                        .eq(Message::getIsRead, 0)
        );
        MessageCountResponse messageCountResponse = new MessageCountResponse();
        messageCountResponse.setMessageCount(messageCount);
        return messageCountResponse;
    }

    /**
     * 清空评论和回复消息数
     */
    @Override
    public void clearCommentAndReplyCount(String userId) {
        commentMessageMapper.update(
                null,
                Wrappers.lambdaUpdate(CommentMessage.class)
                        .eq(CommentMessage::getOperation, CommonConstant.COMMENT)
                        .eq(CommentMessage::getToUserId, userId)
                        .set(CommentMessage::getIsRead, 1)
        );
        commentMessageMapper.update(
                null,
                Wrappers.lambdaUpdate(CommentMessage.class)
                        .eq(CommentMessage::getOperation, CommonConstant.REPLY)
                        .eq(CommentMessage::getToUserId, userId)
                        .set(CommentMessage::getIsRead, 1)
        );
    }

    /**
     * 清空点赞消息数
     */
    @Override
    public void clearUpvoteCount(String userId) {
        messageMapper.update(
                null,
                Wrappers.lambdaUpdate(Message.class)
                        .eq(Message::getOperation, CommonConstant.UPVOTE)
                        .eq(Message::getToUserId, userId)
                        .set(Message::getIsRead, 1)
        );
    }

    /**
     * 清空收藏消息数
     */
    @Override
    public void clearCollectCount(String userId) {
        messageMapper.update(
                null,
                Wrappers.lambdaUpdate(Message.class)
                        .eq(Message::getOperation, CommonConstant.COLLECT)
                        .eq(Message::getToUserId, userId)
                        .set(Message::getIsRead, 1)
        );
    }
}
