<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>语音识别cdx</title>
        <style type='text/css'>
            ul {
                list-style: none;
            }
            #recordingslist audio {
                display: block;
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>

        <h1>语音识别</h1>
        <p>确保你使用的是谷歌浏览器的最新版本</p>
        <p>为了保证语音质量和保护耳朵，在打开麦克风前插上耳机</p>

        <button onclick="startRecording(this);">录音</button>
        <button onclick="stopRecording(this);" disabled>停止</button>
        <button onclick="DownloadWav();" >下载</button>
        <h2>录音文件</h2>
        <ul id="recordingslist"></ul>

        <h2>运行Log</h2>
        <pre id="log"></pre>

        <script src="js/main/recorder.js"></script>
        <script src="js/main/main.js"></script>
    </body>
</html>
