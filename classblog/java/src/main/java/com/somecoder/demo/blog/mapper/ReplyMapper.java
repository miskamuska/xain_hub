package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.somecoder.demo.blog.entity.Reply;
import com.somecoder.demo.blog.entity.response.ReplyResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface ReplyMapper extends BaseMapper<Reply> {
    List<ReplyResponse> getAllReply(@Param("post_id")String postId);
}
