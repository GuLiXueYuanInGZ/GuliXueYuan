package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Ytz
 * @since 2022-08-12
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<OneSubjectVo> listAllSubjects();

    void importSubjectData(MultipartFile file, EduSubjectService subjectService);
}
