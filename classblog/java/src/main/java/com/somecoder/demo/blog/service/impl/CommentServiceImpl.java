package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.*;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.AddCommentRequest;
import com.somecoder.demo.blog.entity.request.DeleteCommentRequest;
import com.somecoder.demo.blog.entity.request.ShowCommentRequest;
import com.somecoder.demo.blog.entity.response.ReplyResponse;
import com.somecoder.demo.blog.entity.response.ShowCommentResponse;
import com.somecoder.demo.blog.mapper.*;
import com.somecoder.demo.blog.service.ICommentService;
import com.somecoder.demo.common.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ReplyMapper replyMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private CommentMessageMapper commentMessageMapper;

    @Resource
    private MessageMapper messageMapper;

    /**
     * 添加评论
     */
    @Override
    public void addComment(LoginUser loginUser, AddCommentRequest addCommentRequest) {
        // 评论
        Comment comment = new Comment();
        comment.setCommentId(PrefixConstant.COMMENT_PREFIX + UUID.randomUUID());
        comment.setComment(addCommentRequest.getComment());
        comment.setPostId(addCommentRequest.getPostId());
        comment.setUserId(loginUser.getUserId());
        commentMapper.insert(comment);
        // 评论信息
        Post post = postMapper.selectOne(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getPostId, addCommentRequest.getPostId())
        );
        if (!loginUser.getUserId().equals(post.getUserId())) {
            CommentMessage commentMessage = new CommentMessage();
            commentMessage.setCommentMessageId(PrefixConstant.MESSAGE_PREFIX + UUID.randomUUID());
            commentMessage.setPostId(addCommentRequest.getPostId());
            commentMessage.setFromUserId(loginUser.getUserId());
            commentMessage.setToUserId(post.getUserId());
            commentMessage.setOperation("评论");
            commentMessage.setContent(addCommentRequest.getComment());
            commentMessageMapper.insert(commentMessage);
        }
    }

    /**
     * 显示评论
     */
    @Override
    public List<ShowCommentResponse> showComment(LoginUser loginUser, ShowCommentRequest showCommentRequest) {
        List<ShowCommentResponse> allComment = commentMapper.getAllComment(showCommentRequest.getPostId());
        List<ReplyResponse> allReply = replyMapper.getAllReply(showCommentRequest.getPostId());
        List<ReplyResponse> allReplyList = new LinkedList<>(allReply);
        for (ShowCommentResponse showCommentResponse : allComment) {
            List<ReplyResponse> list = new ArrayList<>();
            for (Iterator<ReplyResponse> iterator = allReplyList.listIterator(); iterator.hasNext(); ) {
                ReplyResponse reply = iterator.next();
                if (showCommentResponse.getCommentId().equals(reply.getCommentId())) {
                    iterator.remove();
                    reply.setIsMyReply(reply.getFromUserId().equals(loginUser.getUserId()));
                    list.add(reply);
                }
            }
            showCommentResponse.setIsMyComment(showCommentResponse.getUserId().equals(loginUser.getUserId()));
            showCommentResponse.setReplyList(list);
        }
        return allComment;
    }

    /**
     * 删除评论
     */
    @Override
    public void deleteComment(LoginUser loginUser, DeleteCommentRequest deleteCommentRequest) {
        String commentId = deleteCommentRequest.getCommentId();

        Comment comment = commentMapper.selectOne(
                Wrappers.lambdaQuery(Comment.class)
                        .eq(Comment::getCommentId, commentId)
        );
        if (comment == null) {
            throw new CustomException("该评论不存在");
        }
        if (!comment.getUserId().equals(loginUser.getUserId()) || loginUser.getUserType() != 1) {
            throw new CustomException("您暂无此权限");
        }
        commentMapper.delete(
                Wrappers.lambdaQuery(Comment.class)
                        .eq(Comment::getCommentId, commentId)
        );
        replyMapper.delete(
                Wrappers.lambdaQuery(Reply.class)
                        .eq(Reply::getCommentId, commentId)
        );
        messageMapper.delete(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getPostId, comment.getPostId())
                        .eq(Message::getFromUserId, comment.getUserId())
        );
    }
}
