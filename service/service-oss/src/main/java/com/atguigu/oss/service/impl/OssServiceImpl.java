package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 工具类获取值
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        try {
            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 随机生成 uuid
            String uuid = UUID.randomUUID().toString();

            // 按 年/月/日 分文件夹
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 拼接路径
            fileName = datePath + "/" + uuid + fileName;

            // 创建PutObject请求
            ossClient.putObject(bucketName, fileName, inputStream);

            // 将上传之后的文件路径返回
            return "https://" + bucketName + "." + endPoint + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
