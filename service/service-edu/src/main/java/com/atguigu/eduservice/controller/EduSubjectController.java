package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Ytz
 * @since 2022-08-12
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin // 跨域
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类
    @ApiOperation(value = "Excel 批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        // 1. 获取上传的 Excel 文件
        subjectService.importSubjectData(file, subjectService);
        return R.ok();
    }

    // 课程分类数据接口
    @ApiOperation(value = "显示课程分类")
    @GetMapping("getAllSubjects")
    public R listAllSubjects() {
        List<OneSubjectVo> allSubjectList = subjectService.listAllSubjects();

        return R.ok().data("list", allSubjectList);
    }


}

