package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.Upvote;
import com.somecoder.demo.blog.entity.request.CancelTheUpvoteRequest;
import com.somecoder.demo.blog.entity.request.GetMyUpvoteListRequest;
import com.somecoder.demo.blog.entity.request.ShowLikeRequest;
import com.somecoder.demo.blog.entity.request.UpvoteRequest;
import com.somecoder.demo.blog.entity.response.GetMyUpvoteResponse;
import com.somecoder.demo.blog.entity.response.ShowLikeResponse;
import com.somecoder.demo.blog.entity.response.UpvoteResponse;

/**
 * <p>
 * 点赞表 服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface IUpvoteService extends IService<Upvote> {

    /**
     * 点赞
     *
     * @param userId 用户主键
     * @param upvoteRequest 点赞请求
     * @return 点赞列表
     * @author liangkeyu
     * @since 2021-01-28
     */
    UpvoteResponse likePost(String userId, UpvoteRequest upvoteRequest);

    /**
     * 取消点赞
     *
     * @param userId 用户主键
     * @param cancelTheUpvoteRequest 取消点赞请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void cancelTheUpvote(String userId, CancelTheUpvoteRequest cancelTheUpvoteRequest);

    /**
     * 显示点赞
     *
     * @param showLikeRequest 显示点赞请求
     * @return 点赞列表
     * @author liangkeyu
     * @since 2021-01-28
     */
    ShowLikeResponse showLike(ShowLikeRequest showLikeRequest);

    /**
     * 获取我的点赞列表
     *
     * @param userId 用户主键
     * @param getMyUpvoteListRequest 获取点赞列表请求
     * @return 点赞列表
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<GetMyUpvoteResponse> getMyUpvoteList(String userId, GetMyUpvoteListRequest getMyUpvoteListRequest);
}
