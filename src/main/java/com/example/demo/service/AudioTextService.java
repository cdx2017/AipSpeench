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
    FfmpegUtil ffmpegUtil;
    @Autowired
    VoiceToTextUtil voiceToTextUtil;

    /**
     * @param source 文件来源地址
     * @param rate   比率
     * @return 转化文本
     */
    public ChangeResultEntity AdToTx(String source, int rate) {
        String target = "src/main/webapp/music/change.pcm";

        if (source.contains(".pcm")) {
            target = source;
            return voiceToTextUtil.PcmToString(target, rate);
        } else {
            ffmpegUtil.changeAudioToPcm(source, target);
            return voiceToTextUtil.PcmToString(target, rate);
        }
    }

    public static void main(String[] args) {
        int rate = 16000;//比率
        String filepath = "C:/Users/DX/Desktop/music/use1.pcm";//mp3文件地址
        AudioTextService audioTextService = new AudioTextService();
        System.out.println(audioTextService.AdToTx(filepath, rate));
    }

}
