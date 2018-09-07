package com.example.demo.controller;

import com.example.demo.service.FileService;
import com.example.demo.service.UploadFileService;
import com.example.demo.service.WavCutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


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
    @Autowired
    private WavCutService wavCutService;

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

                String path = "src/main/webapp/music/" + newName;
                File file1 = new File(path);
                long t1 = wavCutService.getWavTimeLen(file1);  //总时长(秒)
                /*小于60s的文件任意上传*/
                if (t1 <= 60) {
                    fileService.saveFilePathToRedis("filepath", path);/*记录文件位置*/
                    return "/startChangeToText";
                } else {/*大于60s的需要截取 目前只能截取wav格式的音频*/
                    if (name.contains(".wav")) {
                        wavCutService.WavCut(path, "src/main/webapp/music/use." + Name[i], 0, 60);/*提前切割*/
                        fileService.saveFileTimeToRedis("time", t1);
                        return "/startChangeToText";
                    }
                }
            }
        }
        return "/fail";
    }


}
