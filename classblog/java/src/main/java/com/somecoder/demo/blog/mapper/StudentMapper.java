package com.somecoder.demo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.somecoder.demo.blog.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select `name` from student where user_id=#{user_id}")
    String getStudentName(@Param("user_id") String userId);
}
