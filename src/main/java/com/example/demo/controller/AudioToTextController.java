package com.example.demo.controller;

import com.example.demo.service.AudioTextService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author cdx
 */
@RestController
@RequestMapping("/api/v1")
public class AudioToTextController {
    @Autowired
    private AudioTextService audioTextService;

    /**
     * 请求上传音频
     *
     * @param imageStyle 验证码类型  （staticCode 静态验证码、arithmeticCode 算数验证码、sortCode 排序验证码）
     */
   /* @ApiOperation(value = "请求发送图形验证码", notes = "使用arithmeticCode、sortCode 分别获取算术验证码、排序验证码、其他则获取静态验证码")
    @ApiImplicitParam(name = "imageStyle", value = "图形验证码风格", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/requestImgCode/{imageStyle}")
    public void requestImgCode(@PathVariable String imageStyle, HttpServletResponse response, HttpSession session)
            throws IOException {
        String sessionId = session.getId();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        imageService.getImageCode(sessionId, imageStyle, response.getOutputStream());
    }*/

    /**
     * 音频转化为文字
     *
     * @param source
     * @param target
     * @param rate
     * @return 返回识别结果
     */
    @ApiOperation(value = "语音识别", notes = "语音识别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "source", value = "音频来源地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "target", value = "音频输出地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rate", value = "音频转化比率", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/AudioToText")
    public String AudioToText(String source, String target, int rate) {
        return audioTextService.AdToTx(source, target, rate);
    }

}
