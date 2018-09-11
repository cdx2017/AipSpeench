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
        ChangeResultEntity errorCutResultEntity = new ChangeResultEntity("切割失败", "音频切割发生错误", "500", "null", "null");
        JSONArray jsonArray = new JSONArray();
        if ("myPath".equals(source)) {
            int time = redisFileService.getFileTimeFromRedis("time");
            String filepath = redisFileService.getFilePathFromRedis("filepath");

            if (time > 60) {
               // String middlePath=""
                String newpath = redisFileService.getFilePathFromRedis("newpath");
                wavCutService.WavCutIgnoreEnd(filepath, newpath, "0");//备份上传的音频用来切割
                String cutpath = redisFileService.getFilePathFromRedis("cutpath");
               /* ChangeResultEntity changeResultEntity = audioTextService.AdToTx(cutpath, rate);
                if ("3303".equals(changeResultEntity.getErr_no())) {//wav音频采样率有问题，无法切割
                    changeResultEntity.setResult("wav音频采样率有问题，无法切割!");
                    return changeResultEntity;
                }
                jsonArray.add(changeResultEntity);*/
                int i = 1;
                for (int t = time / 60 + 1; t > 0; t--, i++) {
                    if (t >= 2) {
                        wavCutService.WavCutIgnoreStart(newpath, cutpath, "60");//分割出要识别的部分
                        System.out.println("第" + i + "分钟：");//第i+1分钟识别
                        jsonArray.add(audioTextService.AdToTx(cutpath, rate));
                        wavCutService.WavCutIgnoreEnd(newpath, cutpath, "60");//分割出剩下的音频
                        wavCutService.WavCutIgnoreEnd(cutpath, newpath, "0");//将原音频用剩下部分音频覆盖

                    } else {
                        System.out.println("最后1分钟：");//最后一分钟识别
                        jsonArray.add(audioTextService.AdToTx(newpath, rate));
                    }
                }
            } else {
                System.out.println("开始识别：");//一分钟内识别
                jsonArray.add(audioTextService.AdToTx(filepath, rate));
            }
            System.out.println("识别完成！");
            return jsonArray;
        } else {
            System.out.println("开始识别：");//一分钟内识别
            jsonArray.add(audioTextService.AdToTx(source, rate));
            System.out.println("识别完成！");
            return jsonArray;
        }

    }

}