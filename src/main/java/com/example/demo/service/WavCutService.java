package com.example.demo.service;

import com.example.demo.util.FfmpegUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by DX on 2018/9/7.
 */
@Service
public class WavCutService {
    @Autowired
    FfmpegUtil ffmpegUtil;

    /**
     * @param sourcePath 源路径
     * @param targetPath 输出路径
     * @param end        结束截取时间
     */
    public void WavCutIgnoreStart(String sourcePath, String targetPath, String end) {
        ffmpegUtil.CutAudio(sourcePath, targetPath, "0", end);
    }

    /**
     * @param sourcePath 源路径
     * @param targetPath 输出路径
     * @param start      开始截取时间 默认结束截取时间为最后一秒
     */
    public void WavCutIgnoreEnd(String sourcePath, String targetPath, String start) {
        ffmpegUtil.CutAudio(sourcePath, targetPath, start, "36000");
    }

    /**
     * @param path 传入文件路径
     * @return 文件时长 单位秒
     */
    public int getWavTimeLen(String path) {
        return ffmpegUtil.getAudioTime(path);
    }
}
