package com.itmang.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmang.pojo.entity.College;
import com.itmang.pojo.entity.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {


}
