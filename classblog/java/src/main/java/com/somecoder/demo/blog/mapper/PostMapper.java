package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.Post;
import com.somecoder.demo.blog.entity.dto.PostListQueryDto;
import com.somecoder.demo.blog.entity.response.GetPostDetailResponse;
import com.somecoder.demo.blog.entity.response.GetPostListResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 帖子表 Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 获取文章列表
     * @param postQueryPage 列表分页
     * @param postListQueryDto 文章列表参数
     * @return 文章详情响应
     */
    Page<GetPostListResponse> getPostList(Page<Post> postQueryPage, @Param("postListQueryDto") PostListQueryDto
            postListQueryDto);

    /**
     * 获取文章详情
     * @param postId 文章主键
     * @return 文章详情响应
     */
    GetPostDetailResponse getPostDetail(@Param("post_id") String postId);

    /**
     * 获取文章分类名
     * @param postId 文章主键
     * @return 分类名
     */
    @Select("select `post_theme` from post where post_id=#{post_id}")
    String getPostTheme(@Param("post_id") String postId);
}
