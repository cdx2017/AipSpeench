package com.example.demo.controller;

import com.example.demo.entity.LearnFile;
import com.example.demo.service.AudioTextService;

import com.example.demo.service.UploadFileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author cdx
 */
@RestController
@RequestMapping("/api/v1")
public class AudioToTextController {
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private AudioTextService audioTextService;

    /**
     * 上传音频
     *
     * @param file 文件格式
     */
    @ApiOperation(value = "上传音频", notes = "上传音频")
    @ApiImplicitParam(name = "file", value = "音频格式", required = false, dataType = "String", paramType = "query")
    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestBody LearnFile file, HttpServletRequest request) {

        if (uploadFileService.uploadFile(file, request))
            return file.toString();
        else
            return "fail";
    }

    /**
     * 音频转化为文字
     *
     * @param source
     * @param rate
     * @return 返回识别结果
     */
    @ApiOperation(value = "语音识别", notes = "语音识别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "source", value = "音频来源地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rate", value = "音频转化比率", required = true, dataType = "int", paramType = "query", defaultValue = "16000")
    })
    @PostMapping(value = "/AudioToText")
    public String AudioToText(String source, int rate) {
        return audioTextService.AdToTx(source, rate);
    }

}
