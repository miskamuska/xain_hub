package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.somecoder.demo.blog.entity.Upvote;
import com.somecoder.demo.blog.entity.dto.CountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 点赞表 Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface UpvoteMapper extends BaseMapper<Upvote> {

    List<String> getAllUpvote(@Param("post_id")String postId);

    List<CountDto> getUpvoteCount(@Param("postIdLists")List<String> postIdLists);
}
