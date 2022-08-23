package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Ytz
 * @since 2022-08-17
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    // 保存课程基本信息
    @ApiOperation(value = "新增课程")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(
            @ApiParam(name = "courseInfo", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfo) {

        String courseId = courseService.saveCourseInfo(courseInfo);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        } else {
            return R.error().message("保存失败");
        }
    }

    // 数据回显
    // 1. 根据课程 id 查询课程基本信息
    @ApiOperation(value = "根据课程 id 查询课程信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 2. 修改课程信息接口
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(EduCourse.COURSE_NORMAL);
        courseService.updateById(course);
        return R.ok();
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);

        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        boolean result = courseService.removeCourseById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

}

