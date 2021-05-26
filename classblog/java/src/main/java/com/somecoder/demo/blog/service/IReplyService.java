package com.somecoder.demo.blog.service;

import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.request.AddReplyRequest;
import com.somecoder.demo.blog.entity.response.ReplyIdResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface IReplyService extends IService<Reply> {
    /**
     * 回复
     *
     * @param loginUser 登录用户
     * @param addReplyRequest 回复请求
     * @author lishan
     * @since 2021-01-28
     */
    void addReply(LoginUser loginUser, AddReplyRequest addReplyRequest);

    void deleteReply(ReplyIdResponse replyIdResponse);
}
