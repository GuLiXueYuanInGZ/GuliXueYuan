package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Ytz
 * @since 2022-08-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public boolean removeVideoById(String id) {
        //删除视频资源 TODO

        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean removeByCourseId(String id) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        int count = baseMapper.delete(queryWrapper);
        return count > 0;
    }
}
