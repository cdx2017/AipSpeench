package com.example.demo.util;

import it.sauronsoftware.jave.*;

import java.io.File;

/**
 * Created by DX on 2018/9/7.
 */
public class Test {
    public static void main(String[] args) {
        File source = new File("C:/Users/DX/Desktop/music/dengni.mp4");
        File target = new File("C:/Users/DX/Desktop/music/8.mp3");
        //convertAmr2MP3(source,target);
        /*AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(64000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(22050));*/
        VideoAttributes video = new VideoAttributes();
        video.setCodec("mp4");
        video.setBitRate(new Integer(16000));
        video.setFrameRate(new Integer(15));
        video.setSize(new VideoSize(400, 300));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        //attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(source, target, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static boolean convertAmr2MP3(File src, File target) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("adpcm_ima_smjpeg");
        //audio.setCodec("libmp3lame");
        Encoder encoder = new Encoder();
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        try {
            encoder.encode(src, target, attrs);
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        return false;
    }

}
