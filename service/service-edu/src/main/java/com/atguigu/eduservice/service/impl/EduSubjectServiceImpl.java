package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.ExcelSubjectData;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.atguigu.eduservice.entity.vo.TwoSubjectVo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Ytz
 * @since 2022-08-12
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<OneSubjectVo> listAllSubjects() {
        // 最终分到的数据列表
        List<OneSubjectVo> finalSubjectList = new ArrayList<>();

        // 获取一级分类数据
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);

        // 获取二级分类数据
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id", "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        // 封装数据
        for (int i = 0; i < oneSubjects.size(); i++) {
            // 获取一级分类
            EduSubject oneSubject = oneSubjects.get(i);

            // 创建一级分类对象
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            BeanUtils.copyProperties(oneSubject, oneSubjectVo); // 将 oneSubject 复制到 oneSubjectVo

            // 封装二级分类数据
            List<TwoSubjectVo> twoSubjectVos = new ArrayList<>();
            for (EduSubject twoSubject : twoSubjects) {
                // 获取二级分类
                // 判断是否隶属于当前一级分类
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    // 将该二级分类封装
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(twoSubject, twoSubjectVo);
                    twoSubjectVos.add(twoSubjectVo);
                }
            }

            // 添加对应的二级分类列表
            oneSubjectVo.setChildren(twoSubjectVos);

            // 添加至最终集合
            finalSubjectList.add(oneSubjectVo);
        }

        return finalSubjectList;
    }

    // 添加课程分类
    // poi 读取 Excel 内容
    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            // 1. 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 读取文件
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
