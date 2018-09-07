package com.example.demo.service;

import com.example.demo.util.WavCutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by DX on 2018/9/7.
 */
@Service
public class WavCutService {
    @Autowired
    WavCutUtil wavCutUtil;

    /**
     * @param sourcePath 源路径
     * @param targetPath 输出路径
     * @param start 开始截取时间
     * @param end 结束截取时间
     * @return
     */
    public Boolean WavCut(String sourcePath, String targetPath, int start, int end) {
        return wavCutUtil.cut(sourcePath,targetPath,start,end);
    }

    /**
     * @param sourcePath 源路径
     * @param targetPath 输出路径
     * @param start 开始截取时间 默认结束截取时间为最后一秒
     * @return
     */
    public Boolean WavCut(String sourcePath, String targetPath, int start) {
        return wavCutUtil.cut(sourcePath,targetPath,start,-100);
    }

    /**
     * @param file 传入文件
     * @return 文件时长 单位秒
     */
    public Long getWavTimeLen(File file){
        return wavCutUtil.getTimeLen(file);
    }
}
