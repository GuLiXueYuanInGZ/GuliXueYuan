package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Ytz
 * @since 2022-08-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        List<ChapterVo> finalChapterList = new ArrayList<>();

        // 1. 根据 courseId 获取对应的章节信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId).orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(chapterWrapper);

        // 2. 根据 courseId 获取对应的小节信息
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId).orderByAsc("sort", "id");
        List<EduVideo> videos = videoService.list(videoWrapper);

        // 3. 封装数据
        for (int i = 0; i < chapters.size(); i++) {
            EduChapter chapter = chapters.get(i);

            // 创建章节 Vo 对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            // 封装章节对应的小节信息
            List<VideoVo> videoVos = new ArrayList<>();
            for (int j = 0; j < videos.size(); j++) {
                EduVideo video = videos.get(j);

                if (video.getChapterId().equals(chapter.getId())) {
                    // 创建小节 Vo 对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }

            // 将封装号的小节保存至对应的章节下
            chapterVo.setChildren(videoVos);
            finalChapterList.add(chapterVo);
        }

        return finalChapterList;
    }

    @Override
    public boolean removeChapterById(String id) {
        //根据id查询是否存在视频，如果有则提示用户尚有子节点
        if(videoService.getCountByChapterId(id)){
            throw new GuliException(20001,"该分章节下存在视频课程，请先删除视频课程");
        }

        int result = baseMapper.deleteById(id);
        return result > 0;
    }
}
