package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Ytz
 * @since 2022-08-17
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 统计 ChapterId 为 id 的 Video 的数量
     * @param id
     * @return
     */
    boolean getCountByChapterId(String id);

    boolean removeVideoById(String id);
}
