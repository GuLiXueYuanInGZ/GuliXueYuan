package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.mysql.cj.util.StringUtils;
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

    @Autowired
    private VodClient vodClient;

    @ApiOperation(value = "新增小节")
    @PostMapping("addVideo")
    public R addVideo(
            @ApiParam(name = "video", value = "小节对象", required = true)
            @RequestBody EduVideo video) {
        System.out.println("添加小节" + video);
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
        System.out.println("更新小节" + video);
        videoService.updateById(video);
        return R.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        // 根据小节 id 查询视频 id
        String videoSourceId = videoService.getById(id).getVideoSourceId();

        // 删除视频资源
        if (!StringUtils.isNullOrEmpty(videoSourceId)) {
            R r = vodClient.removeVideo(videoSourceId);
            if (r.getCode() == 20001) {
                throw new GuliException(20001, r.getMessage());
            }
        }

        boolean result = videoService.removeVideoById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

