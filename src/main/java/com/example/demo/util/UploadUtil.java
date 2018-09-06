package com.example.demo.util;

import com.example.demo.entity.LearnFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.util.*;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by DX on 2018/9/5.
 */
@Component
public class UploadUtil {

    public static void uplodeFile(LearnFile lf, HttpServletRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String path = "C:/Users/DX/Desktop/" + file.getOriginalFilename();
                    // 上传
                    file.transferTo(new File(path));
                    String fileurl = "src/main/webapp/music/" + file.getOriginalFilename();
                    LearnFile learnfile = new LearnFile(file.getOriginalFilename(), fileurl, lf.getUplodetime());
                    //fileService.uplodeFile(learnfile);
                } else {
                    System.out.println("上传文件不能为空");
                }

            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("上传所花时间：" + String.valueOf(endTime - startTime) + "ms");
    }
}
