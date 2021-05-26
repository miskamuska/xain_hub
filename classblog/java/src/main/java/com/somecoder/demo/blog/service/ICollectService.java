package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.Collect;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.request.AddCollectRequest;
import com.somecoder.demo.blog.entity.request.PostListRequest;
import com.somecoder.demo.blog.entity.response.GetMyCollectListResponse;

import java.util.List;

/**
 * <p>
 * 收藏表 服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface ICollectService extends IService<Collect> {

    /**
     * 添加收藏
     *
     * @param addCollectRequest 被收藏的文章id
     * @param loginUser 收藏的用户
     * @author lishan
     * @since 2021-01-28
     */
    void addCollect(AddCollectRequest addCollectRequest, LoginUser loginUser);

    /**
     * 收藏数
     *
     * @param addCollectRequest 添加收藏实体类
     * @return 收藏数
     * @author lishan
     * @since 2021-01-28
     */
    int showCountCollect(AddCollectRequest addCollectRequest);

    /**
     * 显示我的收藏
     *
     * @param loginUser 用户
     * @param postListRequest 分页请求
     * @return 我的收藏列表
     * @author lishan
     * @since 2021-01-28
     */
    List<GetMyCollectListResponse> showMyCollect(PostListRequest postListRequest, LoginUser loginUser);

    /**
     * 取消收藏
     *
     * @param addCollectRequest 被取消收藏的文章id
     * @param loginUser 取消收藏的用户
     * @author lishan
     * @since 2021-01-28
     */
    void deleteCollect(AddCollectRequest addCollectRequest, LoginUser loginUser);
}
