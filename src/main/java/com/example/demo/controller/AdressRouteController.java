package com.example.demo.controller;

import com.example.demo.Entity.AipInitPath;
import com.example.demo.dao.AipInitPathDao;
import com.example.demo.service.DeleteFileService;
import com.example.demo.service.RedisFileService;
import com.example.demo.service.UploadFileService;
import com.example.demo.service.WavCutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


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
    @Autowired
    private AipInitPath aipInitPath;
    @Autowired
    private AipInitPathDao aipInitPathDao;
    @Autowired
    private DeleteFileService deleteFileService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login_in")
    public
    @ResponseBody
    Object loginin(@RequestBody AipInitPath aipInitUser) {

        if (aipInitUser.getUsername() != null && !("").equals(aipInitUser.getUsername())) {
            aipInitPath = aipInitPathDao.getAllByUsePlace(aipInitUser.getUsername());
            if (aipInitPath != null && aipInitPath.getPassword().equals(aipInitUser.getPassword())) {
                aipInitPath.setNextgo("/route/main?username=" + aipInitPath.getUsername());
                return aipInitPath;
            }
        }
        return null;
    }

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(HttpServletRequest request, @RequestParam(value = "username", required = true) String username) {
        if ("".equals(username) || username.isEmpty()) {
            return "login";
        }
        String filepath = redisFileService.getFilePathFromRedis(username + "filepath");
        if (filepath != null && !("").equals(filepath)) {
            deleteFileService.delTempChild(filepath);
        }
        //获取真实ip
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
        redisFileService.saveFilePathToRedis(username, ip);//sessionID+name获取唯一标识
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
    public String uploadFile(HttpServletRequest request, MultipartFile file,
                             @RequestParam(value = "username", required = true) String username) throws Exception {

        if ("".equals(username) || username.isEmpty()) {
            return "login";
        }
        //获取真实ip
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
        System.out.println(ip);
        if (!ip.equals(redisFileService.getFilePathFromRedis(username))) {//如果同一个用户名有多个ip登录
            return "fail";
        }

        String name = file.getOriginalFilename();
        /*这里只列出了一些常见格式，理论上音频视频格式都可以*/
        if (name.contains(".wav") || name.contains(".mp3") || name.contains(".pcm") || name.contains(".m4a") || name.contains(".mp4")) {
            String[] Name = name.split("\\.");
            int i = Name.length - 1;
            aipInitPath = aipInitPathDao.getAllByUsePlace(username);
            String dirPath = aipInitPath.getDirPath();//音频存放目录路径
            String ffmpegPath = aipInitPath.getFfmpeg_path();//ffmpeg bin目录
            redisFileService.saveFilePathToRedis("ffmpegPath", ffmpegPath);
            String newName = username + "_origin." + Name[i];//原音频 例= cdx_origin.wav
            if (uploadFileService.uploadFile(file.getInputStream(), newName, dirPath)) {
                String path = dirPath + newName;//原音频路径
                int t1 = wavCutService.getWavTimeLen(path);  //总时长(秒)
                System.out.println(t1);
                redisFileService.saveFilePathToRedis(username + "filepath", path);/*记录文件位置*/
                /*小于60s的文件任意上传*/
                if (t1 <= 60) {
                    redisFileService.saveFileTimeToRedis(username + "time", t1);
                    return "/startChangeToText";
                } else {/*大于60s的需要截取 目前只能截取wav格式的音频*/
                    //if (name.contains(".wav")) {
                    String newpath = dirPath + username + "_new." + Name[i];//被裁剪的音频路径(存放目录加音频名字)
                    String cutpath = dirPath + username + "_use." + Name[i];//使用的识别音频路径
                    redisFileService.saveFilePathToRedis(username + "newpath", newpath);//复制上传音频，避免连续上传
                    redisFileService.saveFilePathToRedis(username + "cutpath", cutpath);//用来识别的部分
                    redisFileService.saveFileTimeToRedis(username + "time", t1);
                    return "/startChangeToText";
                    //}
                }
            }
        }
        return "/fail";
    }

}
