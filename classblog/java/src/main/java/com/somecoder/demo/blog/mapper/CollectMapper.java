package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.somecoder.demo.blog.entity.Collect;
import com.somecoder.demo.blog.entity.dto.CountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 收藏表 Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface CollectMapper extends BaseMapper<Collect> {

//    IPage<GetPostListResponse> showMyCollect(IPage<GetPostListResponse> page, @Param("user_id")String userId);
    List<CountDto> getCollectCount(@Param("postIdLists")List<String> postIdLists);

}
