package com.example.demo.util;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.springframework.stereotype.Component;

import java.io.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


/**
 * Created by DX on 2018/9/3.
 */
/**
 * 音频转换PCM文件方法
 *
 * @throws Exception
 */
@Component
public class AudioConvertPcmUtil {

    /**
     * @param source 音频来源
     * @param target 输出路径
     * @return
     */
    public static boolean convertMP32Pcm(String source, String target) {
        try {
            //获取文件的音频流，pcm的格式
            AudioInputStream audioInputStream = getPcmAudioInputStream(source);
            //将音频转化为  pcm的格式保存下来
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(target));
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    private static AudioInputStream getPcmAudioInputStream(String filepath) {
        File audio = new File(filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(audio);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }


//============================================================================================测试
    public void test() throws Exception {
        String mp3filepath = "C:/Users/DX/Desktop/music/你好.mp3";/*mp3文件地址*/
        String pcmfilepath = "C:/Users/DX/Desktop/music/你好.pcm";/*pcm文件输出地址*/
        AudioConvertPcmUtil mp3ConvertPCM = new AudioConvertPcmUtil();
        mp3ConvertPCM.convertMP32Pcm(mp3filepath, pcmfilepath);
        VoiceToTextUtil voiceToText = new VoiceToTextUtil();

        System.out.println(voiceToText.PcmToString(pcmfilepath, 16000));
    }
    public static void main(String[] args) {
        AudioConvertPcmUtil audioConvertPcmUtil = new AudioConvertPcmUtil();
        try {
            audioConvertPcmUtil.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//=======================================================================================================

}


