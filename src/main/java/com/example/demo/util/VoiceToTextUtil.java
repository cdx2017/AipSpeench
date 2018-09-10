package com.example.demo.util;

import com.baidu.aip.speech.AipSpeech;
import com.example.demo.Entity.ChangeResultEntity;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by DX on 2018/9/3.
 */
@Component
public class VoiceToTextUtil {

    //设置APPID/AK/SK
    public static final String APP_ID = "11764328";
    public static final String API_KEY = "EbGcW08I9eYG9aINvQD2CsGq";
    public static final String SECRET_KEY = "9gDNasSqzZiI8YKW6ve3pwlTvguDRfZc";

    /**
     * @param PcmPath 文件路径
     * @param PcmRate 文件的频率
     * @return 转换后的字符串
     */
    public static ChangeResultEntity PcmToString(String PcmPath, int PcmRate) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //.setHttpProxy("http://vop.baidu.com/server_api",80);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "log4j.properties");

        // 调用接口
        JSONObject res = client.asr(PcmPath, "pcm", PcmRate, null);
        String result,corpus_no;
        if ("0".equals(res.get("err_no").toString())) {
            result = res.get("result").toString();
            corpus_no=res.get("corpus_no").toString();
        } else {
            result = "null";
            corpus_no="null";
        }
        ChangeResultEntity changeResultEntity = new ChangeResultEntity(result, res.get("err_msg").toString(),
                res.get("err_no").toString(), res.get("sn").toString(), corpus_no);
        return changeResultEntity;
    }

    public static void main(String[] args) {
        System.out.println(VoiceToTextUtil.PcmToString("C:/Users/DX/Desktop/music/use.wav", 16000).toString());
    }
}