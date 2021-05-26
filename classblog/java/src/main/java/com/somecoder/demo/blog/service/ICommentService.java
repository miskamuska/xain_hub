package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.Comment;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.request.AddCommentRequest;
import com.somecoder.demo.blog.entity.request.DeleteCommentRequest;
import com.somecoder.demo.blog.entity.request.ShowCommentRequest;
import com.somecoder.demo.blog.entity.response.ShowCommentResponse;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 评论
     *
     * @param loginUser 评论的用户
     * @param addCommentRequest 评论请求类
     * @author lishan
     * @since 2021-01-28
     */
    void addComment(LoginUser loginUser, AddCommentRequest addCommentRequest);

    /**
     * 显示评论
     *
     * @param showCommentRequest 显示评论请求
     * @return 评论列表
     * @author lishan
     * @since 2021-01-28
     */
    List<ShowCommentResponse> showComment(LoginUser loginUser, ShowCommentRequest showCommentRequest);

    /**
     * 删除评论
     *
     * @param loginUser 用户
     * @param deleteCommentRequest 删除评论请求
     * @author lishan
     * @since 2021-01-28
     */
    void deleteComment(LoginUser loginUser, DeleteCommentRequest deleteCommentRequest);
}
