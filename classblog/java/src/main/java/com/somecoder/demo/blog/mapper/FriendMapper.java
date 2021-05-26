package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.somecoder.demo.blog.entity.Friend;
import com.somecoder.demo.blog.entity.response.ShowFriendListResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-26
 */
public interface FriendMapper extends BaseMapper<Friend> {

    List<ShowFriendListResponse> showFriendList(@Param("user_id") String userId);

}
