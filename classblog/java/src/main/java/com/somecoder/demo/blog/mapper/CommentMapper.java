package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.somecoder.demo.blog.entity.Comment;
import com.somecoder.demo.blog.entity.dto.CountDto;
import com.somecoder.demo.blog.entity.response.ShowCommentResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<ShowCommentResponse> getAllComment(@Param("post_id")String postId);

    List<CountDto> getCommentCount(@Param("postIdLists")List<String> postIdLists);
}
