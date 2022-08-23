package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Ytz
 * @since 2022-08-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVoById(String id);
}
