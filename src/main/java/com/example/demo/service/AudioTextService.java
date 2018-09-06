package com.example.demo.service;

import com.example.demo.util.AudioConvertPcmUtil;
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
    AudioConvertPcmUtil audioConvertPcmUtil;
    @Autowired
    FfmpegUtil ffmpegUtil;
    @Autowired
    VoiceToTextUtil voiceToTextUtil;

    /**
     * @param source 文件来源地址
     * @param rate   比率
     * @return 转化文本
     */
    public String AdToTx(String source, int rate) {
        String target = "src/main/webapp/music/change.pcm";
        /*由于无法实现wav转pcm，这种方式舍弃*/
        /*if (source.contains(".pcm")) {
            target = source;
        } else if (source.contains(".wav")) {
            audioConvertPcmUtil.convertAudioFiles(source, target);//毫无效果wav转的pcm（舍弃）
        } else {
            audioConvertPcmUtil.convertMP32Pcm(source, target);//mp3转pcm
        }*/
        /*通过*/
        if (source.contains(".pcm")) {
            target = source;
            return voiceToTextUtil.PcmToString(target, rate);
        } else {
            ffmpegUtil.changeAmrToMp3(source, target);
            return voiceToTextUtil.PcmToString(target, rate);
        }
    }

    /*public static void main(String[] args) {
        int rate = 16000;//比率
        String filepath = "src/main/webapp/music/origin.pcm";//mp3文件地址
        AudioTextService audioTextService = new AudioTextService();
        System.out.println(audioTextService.AdToTx(filepath, rate));
    }*/

}
