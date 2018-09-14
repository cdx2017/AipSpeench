<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>

<br>
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
<h1>语音识别</h1>

<p>确保你使用的是谷歌浏览器的最新版本</p>

<p>为了保证语音质量和保护耳朵，在打开麦克风前插上耳机</p>
<button onclick="startRecording(this);">录音</button>
<button onclick="stopRecording(this);" disabled>停止</button>
<%--录音列表--%>
<h2>录音文件</h2>
<ul id="recordingslist"></ul>
<br>
<%--音频上传--%>
<h2> 请您选择需要上传的音频文件</h2>
<p style="color: #ae4c4c">注意：音频文件只支持：wav、mp3、pcm、m4a、mp4;</p>
<p style="color: #ae4c4c">           音频文件最大支持：100M.</p>
<form id="form1" name="form1" method="post" action="/route/uploadFile" enctype="multipart/form-data">
    <table border="0">
        <tr>
            <input type="text" name="username" value="${param.username}" hidden="true"/>
            <td>上传文件：</td>
            <td><input name="file" id="file" type="file" size="20" accept="audio/*"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" name="submit" value="提交">
                <input type="reset" name="reset" value="重置">
            </td>
        </tr>
    </table>
</form>
<%--运行日志--%>
<h2>运行Log</h2>
<pre id="log"></pre>

<script src="../js/main/recorder.js"></script>
<%--<script src="../js/main/jquery.js"></script>--%>
<script src="../js/main/main.js"></script>
</body>
</html>
