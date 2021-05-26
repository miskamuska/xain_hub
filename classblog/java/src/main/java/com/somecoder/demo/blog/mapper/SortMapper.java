package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.somecoder.demo.blog.entity.Sort;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface SortMapper extends BaseMapper<Sort> {
    @Select("select `sort_theme` from sort where sort_id=#{sort_id}")
    String getSortTheme(@Param("sort_id") String sortId);
}
