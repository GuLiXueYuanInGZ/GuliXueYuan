package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor // 无参
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService subjectService;

    // 有参构造
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData subject, AnalysisContext analysisContext) {
        // 读取每一行数据
        if (subject == null) {
            throw new GuliException(20001, "添加失败");
        }

        // 添加一级分类
        EduSubject existOneSubject = this.exitOneSubject(subjectService, subject.getOneSubjectName());
        if (existOneSubject == null) {
            // 没有一级分类
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subject.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        // 获取二级分类对应 pid
        String pid = existOneSubject.getId();

        // 添加二级分类
        EduSubject existTwoSubject = this.exitTwoSubject(subjectService, subject.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            // 二级标题不存在
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subject.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    // 判断一级分类是否重复
    private EduSubject exitOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }

    // 判断二级分类是否重复
    private EduSubject exitTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        // 读取表头信息
        System.out.println("表头信息" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 读取完成后执行操作
    }
}
