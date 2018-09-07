package com.example.demo.controller;

import com.example.demo.service.AudioTextService;

import com.example.demo.service.FileService;
import com.example.demo.service.UploadFileService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author cdx
 */
@Controller
@RequestMapping("/api/v1")
public class AudioToTextController {

    @Autowired
    private AudioTextService audioTextService;

    @Autowired
    private FileService fileService;

    /**
     * 音频转化为文字
     *
     * @param source
     * @param rate
     * @return 返回识别结果
     */
    @ApiOperation(value = "语音识别", notes = "语音识别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "source", value = "音频来源地址", required = true, dataType = "String", paramType = "query", defaultValue = "src/main/webapp/music/"),
            @ApiImplicitParam(name = "rate", value = "音频转化比率", required = true, dataType = "int", paramType = "query", defaultValue = "16000")
    })
    @PostMapping(value = "/AudioToText")
    @ResponseBody
    public String AudioToText(String source, int rate) {
        if ("myPath".equals(source)) {
            return audioTextService.AdToTx(fileService.getFilePathFromRedis("filepath"), rate);
        } else {
            return audioTextService.AdToTx(source, rate);
        }
    }

}
