package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Ytz
 * @since 2022-08-17
 */
@Api(description = "小节管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "新增小节")
    @PostMapping("addVideo")
    public R addVideo(
            @ApiParam(name = "video", value = "小节对象", required = true)
            @RequestBody EduVideo video) {
        videoService.save(video);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询小节信息")
    @GetMapping("getVideInfo/{id}")
    public R getVideInfoById(
            @ApiParam(name = "id", value = "小节ID", required = true)
            @PathVariable String id){

        EduVideo video = videoService.getById(id);
        return R.ok().data("video", video);
    }

    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(
            @ApiParam(name = "video", value = "小节对象", required = true)
            @RequestBody EduVideo video){
        videoService.updateById(video);
        return R.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        boolean result = videoService.removeVideoById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

