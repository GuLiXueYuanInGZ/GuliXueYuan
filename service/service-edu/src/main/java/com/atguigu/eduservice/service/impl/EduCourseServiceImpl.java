package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Ytz
 * @since 2022-08-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfo) {
        // 1. 保存课程基本信息
        EduCourse course = new EduCourse();
        course.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfo, course);
        int insert = baseMapper.insert(course);
        if (insert <= 0) {
            throw new GuliException(20001, "课程信息保存失败");
        }

        // 2. 保存课程描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescription.setId(course.getId());
        boolean save = courseDescriptionService.save(courseDescription);
        if (!save) {
            throw new GuliException(20001, "课程详情信息保存失败");
        }

        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        // 查询课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        if (eduCourse == null) {
            throw new GuliException(20001, "数据不存在");
        }
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 查询课程描述
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 修改课程信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if (update != 1) {
            throw new GuliException(20001, "课程信息保存失败");
        }

        // 2. 修改课程描述
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = courseDescriptionService.updateById(courseDescription);
        if (!b) {
            throw new GuliException(20001, "课程详情信息保存失败");
        }
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {
        //根据id删除所有视频
        boolean b = videoService.removeByCourseId(id);

        //根据id删除所有章节
        boolean b1 = chapterService.removeByCourseId(id);
        if (b && b1) {
            int result = baseMapper.deleteById(id);
            return result > 0;
        }

        return false;
    }
}
