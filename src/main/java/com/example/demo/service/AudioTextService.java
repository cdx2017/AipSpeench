package com.example.demo.service;

import com.example.demo.Entity.ChangeResultEntity;
import com.example.demo.util.FfmpegUtil;
import com.example.demo.util.VoiceToTextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by DX on 2018/9/3.
 */
@Service
public class AudioTextService {

    @Autowired
    private RedisFileService redisFileService;

    /**
     * @param source 文件来源地址
     * @param rate   比率
     * @return 转化文本
     */
    public ChangeResultEntity AdToTx(String source, int rate, String target) {
        if (source.contains(".pcm")) {
            target = source;
            return VoiceToTextUtil.PcmToString(target, rate);
        } else {
            FfmpegUtil.changeAudioToPcm(source, target, redisFileService.getFilePathFromRedis("ffmpegPath"));
            return VoiceToTextUtil.PcmToString(target, rate);
        }
    }

    /*public static void main(String[] args) {
        int rate = 16000;//比率
        String filepath = "C:/Users/DX/Desktop/music/use1.pcm";//mp3文件地址
        AudioTextService audioTextService = new AudioTextService();
        System.out.println(audioTextService.AdToTx(filepath, rate,""));
    }*/

}
