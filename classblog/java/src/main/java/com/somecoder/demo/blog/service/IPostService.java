package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.Post;
import com.somecoder.demo.blog.entity.request.*;
import com.somecoder.demo.blog.entity.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 帖子表 服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface IPostService extends IService<Post> {

    /**
     * 发布帖子
     *
     * @param writePostRequest 发布帖子请求
     * @param loginUser 用户
     * @author liangkeyu
     * @since 2021-01-28
     */
    void writePost(WritePostRequest writePostRequest, LoginUser loginUser);

    /**
     * 搜索相关文章
     * @param searchPostRequest 搜索文章请求类
     * @return List<GetPostListResponse> 文章列表响应
     * @author lishan
     * @since 2021-01-28
     */
    Page<GetPostListResponse> searchPost(SearchPostRequest searchPostRequest);

    /**
     * 获取我的帖子列表
     *
     * @param getMyPostListRequest 获取我的帖子列表请求
     * @param userId 用户主键
     * @return Page<GetMyPostListResponse> 我的帖子列表响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<GetMyPostListResponse> getMyPostList(
            String userId, GetMyPostListRequest getMyPostListRequest
    );

    /**
     * 修改帖子
     *
     * @param modifyPostRequest 修改帖子请求
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    void modifyPost(String userId, ModifyPostRequest modifyPostRequest);

    /**
     * 删除我的帖子（学生端）
     *
     * @param deleteMyPostRequest 删除我的帖子请求
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    void deleteMyPost(String userId, DeleteMyPostRequest deleteMyPostRequest);

    /**
     * 删除帖子（管理员端）
     *
     * @param deletePostRequest 删除帖子请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void deletePost(DeletePostRequest deletePostRequest);

    /**
     * 置顶
     *
     * @param topRequest 置顶请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void top(TopRequest topRequest);

    /**
     * 获取文章详情
     * @param loginUser 用户
     * @param getPostDetailRequest 获取文章详情请求体
     * @return 文章详情响应类
     */
    GetPostDetailResponse getPostDetail(
            LoginUser loginUser, GetPostDetailRequest getPostDetailRequest
    );

    /**
     * 获得获赞数和文章数
     * @param loginUser 用户
     * @return 返回文章数和获赞数
     */
    GetCountResponse getCount(LoginUser loginUser);

    /**
     * 取消置顶
     * @param topRequest 置顶文章请求
     */
    void cancelTop(TopRequest topRequest);

    /**
     * 获取作者帖子列表
     *
     * @param getThePostListRequest 获取作者帖子列表请求
     * @return Page<GetMyPostListResponse> 作者帖子列表响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<GetThePostListResponse> getThePostList(GetThePostListRequest getThePostListRequest);

    /**
     * 获取作者信息
     *
     * @param getAuthorInformationRequest 获取作者信息请求
     * @return Page<GetMyPostListResponse> 作者信息响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    GetAuthorInformationResponse getAuthorInformation(GetAuthorInformationRequest getAuthorInformationRequest);

    /**
     * 上传图片
     *
     * @param multipartFile 上传文件
     * @author lishan
     * @since 2021-01-28
     */
    UploadPicResponse uploadPic(MultipartFile multipartFile) throws IOException;

    /**
     * 管理员帖子模糊搜索
     *
     * @param searchPostRequest 管理员帖子模糊搜索请求
     * @return Page<GetMyPostListResponse> 作者信息响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<ThePostListResponse> search(SearchRequest searchPostRequest);
}
