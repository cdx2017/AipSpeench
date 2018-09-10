package com.example.demo.controller;

import com.example.demo.Entity.ChangeResultEntity;
import com.example.demo.service.AudioTextService;
import com.example.demo.service.RedisFileService;
import com.example.demo.service.WavCutService;
import io.swagger.annotations.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author cdx
 */
@Controller
@RequestMapping("/api/v1")
public class AudioToTextController {

    @Autowired
    private AudioTextService audioTextService;

    @Autowired
    private RedisFileService redisFileService;

    @Autowired
    private WavCutService wavCutService;

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
    public Object AudioToText(String source, int rate) {
        JSONArray jsonArray = new JSONArray();
        if ("myPath".equals(source)) {
            Long time = redisFileService.getFileTimeFromRedis("time");
            String filepath = redisFileService.getFilePathFromRedis("filepath");

            if (time > 60) {
                String newfilepath = redisFileService.getFilePathFromRedis("newfilepath");
                ChangeResultEntity changeResultEntity=audioTextService.AdToTx(newfilepath, rate);
                if("3303".equals(changeResultEntity.getErr_no())){//wav音频采样率有问题，无法切割
                    changeResultEntity.setResult("wav音频采样率有问题，无法切割!");
                    return changeResultEntity;
                }
                jsonArray.add(changeResultEntity);
                int i = 1;
                for (Long t = time / 60; t > 0; t--, i++) {
                    if (t >= 2) {
                        wavCutService.WavCut(filepath, newfilepath, 60 * i, 60 * i + 60);
                        jsonArray.add(audioTextService.AdToTx(redisFileService.getFilePathFromRedis("newfilepath"), rate));
                    } else {
                        wavCutService.WavCut(filepath, newfilepath, 60 * i);
                        jsonArray.add(audioTextService.AdToTx(redisFileService.getFilePathFromRedis("newfilepath"), rate));
                        break;
                    }
                }
                //String resultArry[] = result.split("<>");

                return jsonArray;
            } else {
                return audioTextService.AdToTx(filepath, rate);
            }
        } else {
            return audioTextService.AdToTx(source, rate);
        }
    }

}
