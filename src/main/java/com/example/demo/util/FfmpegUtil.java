package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用Ffmpeg工具处理音频文件
 * Created by DX on 2018/9/5.
 */
@Component
public class FfmpegUtil {

    /**
     * 将音频文件转化为 pcm格式
     *
     * @param sourcePath    输入音频
     * @param targetPath    输出位置
     * @param ffmpegbinPath ffmpeg bin 目录
     */
    public static void changeAudioToPcm(String sourcePath, String targetPath, String ffmpegbinPath) {
        //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
        //wav转pcm
        //Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -y -i " + sourcePath + " -acodec pcm_s16le -f s16le -ac 1 -ar 16000 " + targetPath);
        //mp3转pcm
        //Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -y -i " + sourcePath + " -acodec pcm_s16le -f s16le -ac 1 -ar 16000 " + targetPath);
        Runtime runtime = Runtime.getRuntime();
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpegbinPath);
        commands.add("-y");
        commands.add("-i");
        commands.add(sourcePath);
        commands.add("-acodec");
        commands.add("pcm_s16le");
        commands.add("-f");
        commands.add("s16le");
        commands.add("-ac");
        commands.add("1");
        commands.add("-ar");
        commands.add("16000");
        commands.add(targetPath);
        StringBuffer test = new StringBuffer();
        for (int i = 0; i < commands.size(); i++)
            test.append(commands.get(i) + " ");
        System.out.println(test);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process process = builder.start();
            /**等待线程结束*/
            process.waitFor();
            System.out.println("change to pcm success");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            runtime.freeMemory();
        }
    }

    /**
     * 将音频切割 从测试来看切割结果如下：0--t;或者t--end
     *
     * @param sourcePath    音频来源
     * @param targetPath    输出位置
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param ffmpegbinPath ffmpeg bin 目录
     */
    public static void CutAudio(String sourcePath, String targetPath, String startTime, String endTime, String ffmpegbinPath) {
        /*执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，
           -acodec copy output.mp3 重新编码并复制到新文件中 -ss 开始截取的时间点 ,-t 截取音频时间长度*/
        //Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -y -i " + sourcePath + " -vn -acodec copy -ss " + startTime + " -t " + endTime + " " + targetPath);
        Runtime runtime = Runtime.getRuntime();
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpegbinPath);
        commands.add("-y");
        commands.add("-i");
        commands.add(sourcePath);
        commands.add("-vn");
        commands.add("-acodec");
        commands.add("copy");
        commands.add("-ss");
        commands.add(startTime);
        commands.add("-t");
        commands.add(endTime);
        commands.add(targetPath);
        StringBuffer test = new StringBuffer();
        for (int i = 0; i < commands.size(); i++)
            test.append(commands.get(i) + " ");
        System.out.println(test);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            /**等待线程结束*/
            process.waitFor();
            /** 休眠6秒后，关闭ffmpeg程序*/
           /* Thread.sleep(1000);
            if (process.isAlive()) {
                process.destroyForcibly();
            }*/
            System.out.println("audio cut success");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            runtime.freeMemory();
        }

    }


    /**
     * 获取视频总时间
     *
     * @param video_path    视频路径
     * @param ffmpegbinPath ffmpeg bin 目录
     * @return
     */
    public static int getAudioTime(String video_path, String ffmpegbinPath) {
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpegbinPath);
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            String result = sb.toString();
            //从视频信息中解析时长
            String regexDuration;
            if (result.contains("Invalid data")) {
                System.out.println(result);
                return 0;
            }
            if (!sb.toString().contains("start:")) {
                regexDuration = "Duration: (.*?), bitrate: (\\d*) kb\\/s";
            } else {
                regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            }
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getTimelen(m.group(1));
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 时间格式转化
     *
     * @param timelen 格式:"00:00:10.68"
     * @return
     */
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }


    public static void main(String[] args) {
        String path = "C:\\Users\\DX\\Desktop\\music\\147s.wav";
        String sPath = "C:\\Users\\DX\\Desktop\\music\\use.wav";
        String tPath = "C:\\Users\\DX\\Desktop\\music\\use1.wav";
        String pcmPath = "C:\\Users\\DX\\Desktop\\music\\q.pcm";

        try {
            //new FfmpegUtil().changeAudioToPcm(tPath, pcmPath, "ffmpeg/bin/ffmpeg");
            new FfmpegUtil().CutAudio(path, sPath, "0", "200", "ffmpeg/bin/ffmpeg");
            new FfmpegUtil().CutAudio(sPath, tPath, "60", "200", "ffmpeg/bin/ffmpeg");
            new FfmpegUtil().CutAudio(tPath, sPath, "0", "200", "ffmpeg/bin/ffmpeg");
            //System.out.println(new FfmpegUtil().getAudioTime(pcmPath),"ffmpeg/bin/ffmpeg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
