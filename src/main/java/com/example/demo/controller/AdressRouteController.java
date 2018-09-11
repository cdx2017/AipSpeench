package com.example.demo.controller;

import com.example.demo.service.RedisFileService;
import com.example.demo.service.UploadFileService;
import com.example.demo.service.WavCutService;
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
    private RedisFileService redisFileService;
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
            String newName = "origin." + Name[i];//原音频
            String dirPath = "src/main/webapp/music/";//音频存放目录路径

            if (uploadFileService.uploadFile(file.getInputStream(), newName, dirPath)) {
                String path = "src/main/webapp/music/" + newName;//原音频路径
                int t1 = wavCutService.getWavTimeLen(path);  //总时长(秒)
                redisFileService.saveFilePathToRedis("filepath", path);/*记录文件位置*/
                /*小于60s的文件任意上传*/
                if (t1 <= 60) {
                    redisFileService.saveFileTimeToRedis("time", t1);
                    return "/startChangeToText";
                } else {/*大于60s的需要截取 目前只能截取wav格式的音频*/
                    if (name.contains(".wav")) {
                        String newpath = "src/main/webapp/music/new." + Name[i];//被裁剪的音频路径
                        String cutpath = "src/main/webapp/music/use." + Name[i];//使用的识别音频路径
                        redisFileService.saveFilePathToRedis("newpath", newpath);
                        redisFileService.saveFilePathToRedis("cutpath", cutpath);
                        redisFileService.saveFileTimeToRedis("time", t1);
                        return "/startChangeToText";
                    }
                }
            }
        }
        return "/fail";
    }


}
