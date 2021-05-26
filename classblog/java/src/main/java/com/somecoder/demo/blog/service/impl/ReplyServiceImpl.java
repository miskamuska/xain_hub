package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.*;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.AddReplyRequest;
import com.somecoder.demo.blog.entity.response.ReplyIdResponse;
import com.somecoder.demo.blog.mapper.CommentMapper;
import com.somecoder.demo.blog.mapper.CommentMessageMapper;
import com.somecoder.demo.blog.mapper.PostMapper;
import com.somecoder.demo.blog.mapper.ReplyMapper;
import com.somecoder.demo.blog.service.IReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Resource
    private ReplyMapper replyMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CommentMessageMapper commentMessageMapper;

    /**
     *
     * 添加回复
     */
    @Override
    public void addReply(LoginUser loginUser, AddReplyRequest addReplyRequest) {
        // 回复
        Reply reply = new Reply();
        reply.setFromUserId(loginUser.getUserId());
        reply.setToUserId(addReplyRequest.getToUserId());
        reply.setRevert(addReplyRequest.getReply());
        reply.setCommentId(addReplyRequest.getCommentId());
        reply.setReplyId(PrefixConstant.REPLY_PREFIX + UUID.randomUUID());
        replyMapper.insert(reply);
        // 回复消息
        Comment comment = commentMapper.selectOne(
                Wrappers.lambdaQuery(Comment.class)
                        .eq(Comment::getCommentId, addReplyRequest.getCommentId())
        );
        Post post = postMapper.selectOne(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getPostId, comment.getPostId())
        );
        if (!post.getUserId().equals(loginUser.getUserId())
                && !addReplyRequest.getToUserId().equals(loginUser.getUserId())) {
            CommentMessage commentMessageReply = new CommentMessage();
            commentMessageReply.setCommentMessageId(PrefixConstant.MESSAGE_PREFIX + UUID.randomUUID());
            commentMessageReply.setPostId(comment.getPostId());
            commentMessageReply.setFromUserId(loginUser.getUserId());
            commentMessageReply.setToUserId(addReplyRequest.getToUserId());
            commentMessageReply.setOperation("回复");
            commentMessageReply.setContent(comment.getComment());
            commentMessageMapper.insert(commentMessageReply);

            CommentMessage commentMessage = new CommentMessage();
            commentMessage.setCommentMessageId(PrefixConstant.MESSAGE_PREFIX + UUID.randomUUID());
            commentMessage.setPostId(comment.getPostId());
            commentMessage.setFromUserId(loginUser.getUserId());
            commentMessage.setToUserId(post.getUserId());
            commentMessage.setOperation("评论");
            commentMessage.setContent(reply.getRevert());
            commentMessageMapper.insert(commentMessage);
        }
    }

    /**
     * 删除回复
     */
    @Override
    public void deleteReply(ReplyIdResponse replyIdResponse) {
        replyMapper.delete(
                Wrappers.lambdaQuery(Reply.class)
                        .eq(Reply::getReplyId, replyIdResponse.getReplyId())
        );
    }
}
