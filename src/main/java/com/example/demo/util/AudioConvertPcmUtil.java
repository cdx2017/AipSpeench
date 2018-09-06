package com.example.demo.util;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
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


    /**
     * WAV转PCM文件
     *
     * @param wavfilepath wav文件路径
     * @param pcmfilepath pcm要保存的文件路径及文件名
     * @return
     */
    public static String convertAudioFiles(String wavfilepath, String pcmfilepath) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        try {
            fileInputStream = new FileInputStream(wavfilepath);
            fileOutputStream = new FileOutputStream(pcmfilepath);
            byte[] wavbyte = InputStreamToByte(fileInputStream);
            byte[] pcmbyte = Arrays.copyOfRange(wavbyte, 44, wavbyte.length);
            fileOutputStream.write(pcmbyte);
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(fileOutputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return pcmfilepath;
    }

    /**
     * 输入流转byte二进制数据
     *
     * @param fis
     * @return
     * @throws IOException
     */
    private static byte[] InputStreamToByte(FileInputStream fis) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        long size = fis.getChannel().size();
        byte[] buffer = null;
        if (size <= Integer.MAX_VALUE) {
            buffer = new byte[(int) size];
        } else {
            buffer = new byte[8];
            for (int ix = 0; ix < 8; ++ix) {
                int offset = 64 - (ix + 1) * 8;
                buffer[ix] = (byte) ((size >> offset) & 0xff);
            }
        }
        int len;
        while ((len = fis.read(buffer)) != -1) {
            byteStream.write(buffer, 0, len);
        }
        byte[] data = byteStream.toByteArray();
        IOUtils.closeQuietly(byteStream);
        return data;
    }




    //============================================================================================测试
   /* public void test() throws Exception {
        String mp3filepath = "C:/Users/DX/Desktop/music/你好.mp3";*//*mp3文件地址*//*
        String pcmfilepath = "C:/Users/DX/Desktop/music/你好.pcm";*//*pcm文件输出地址*//*
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
    }*/
//=======================================================================================================

}


