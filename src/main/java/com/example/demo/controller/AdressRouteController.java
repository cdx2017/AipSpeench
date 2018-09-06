package com.example.demo.controller;

import com.example.demo.service.FileService;
import com.example.demo.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author cdx
 */
@Controller
@RequestMapping("/route")
public class AdressRouteController {

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/main")
    public String main1() {
        return "main";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }

    @GetMapping("/startChangeToText")
    public String startChangeToText() {
        return "startChangeToText";
    }

    /**
     * 上传音频
     *
     * @param file 文件
     */
    @PostMapping(value = "/uploadFile")
    public String uploadFile(MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        /*这里只列出了一些常见格式，理论上音频视频格式都可以*/
        if (name.contains(".wav") || name.contains(".mp3") || name.contains(".pcm") || name.contains(".m4a") || name.contains(".mp4")) {
            String[] Name = name.split("\\.");
            int i = Name.length - 1;
            String newName = "origin." + Name[i];
            if (uploadFileService.uploadFile(file.getInputStream(), newName)) {
                fileService.saveFileToRedis("filename", newName);
                return "startChangeToText";
            }
        }
        return "fail";
    }


}
