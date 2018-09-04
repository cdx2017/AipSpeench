package com.example.demo.service;

import com.example.demo.util.AudioConvertPcmUtil;
import com.example.demo.util.VoiceToTextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by DX on 2018/9/3.
 */
@Service
public class AudioTextService {

    @Autowired
    AudioConvertPcmUtil audioConvertPcmUtil;
    @Autowired
    VoiceToTextUtil voiceToTextUtil;

    /**
     * @param source 文件来源地址
     * @param target 文件输出地址
     * @param rate   比率
     * @return 转化文本
     */
    public String AdToTx(String source, String target, int rate) {

        if (source.contains(".pcm")) {
            target = source;
        } else {
            audioConvertPcmUtil.convertMP32Pcm(source, target);
        }

        return voiceToTextUtil.PcmToString(target, rate);
    }

    public static void main(String[] args) {
        int rate = 16000;/*比率*/
        /*String filepath = "C:/Users/DX/Desktop/music/你好.mp3";*//*mp3文件地址*//*
        String pcmfilepath = "C:/Users/DX/Desktop/music/你好.pcm";*//*pcm文件输出地址*/
        String filepath = "C:/Users/DX/Desktop/music/1.wav";/*mp3文件地址*/
        String pcmfilepath = "C:/Users/DX/Desktop/music/1.pcm";/*pcm文件输出地址*/
        AudioTextService audioTextService = new AudioTextService();
        System.out.println(audioTextService.AdToTx(filepath, pcmfilepath, rate));
    }

}
