package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像方法
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R uploadOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        // 获取上传文件 MultipartFile
        // 返回上传到 oss 的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().message("文件上传成功").data("url", url);
    }













}
