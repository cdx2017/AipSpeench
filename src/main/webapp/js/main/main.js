/**
 * Created by DX on 2018/9/4.
 */
/*打印日志*/
function __log(e, data) {
    log.innerHTML += "\n" + e + " " + (data || '');
}

var audio_context;
var recorder;
var form;

/*设备环境初始化*/
function startUserMedia(stream) {
    var input = audio_context.createMediaStreamSource(stream);
    __log('媒体流创建.');

    // 如果你想让音频直接反馈，请取消注释
    //input.connect(audio_context.destination);
    //__log('Input connected to audio context destination.');

    recorder = new Recorder(input);
    __log('录音设备初始化.');
}

/*开始录音*/
function startRecording(button) {
    alert("开始录音，点击结束按钮会自动下载已经录好的音频！");
    recorder && recorder.record();
    button.disabled = true;
    button.nextElementSibling.disabled = false;
    __log('正在录音...');
}

/*结束录音*/
function stopRecording(button) {
    recorder && recorder.stop();
    button.disabled = true;
    button.previousElementSibling.disabled = false;
    __log('录音停止.');

    // 创建下载链接
    createDownloadLink();

    recorder.clear();
}

/*创建下载链接*/
function createDownloadLink() {
    recorder && recorder.exportWAV(function (blob) {
        var url = URL.createObjectURL(blob);
        var li = document.createElement('li');
        var au = document.createElement('audio');
        var hf = document.createElement('a');

        au.controls = true;
        au.src = url;
        hf.href = url;
        hf.download = new Date().toISOString() + '.wav';
        hf.innerHTML = hf.download;
        li.appendChild(au);
        li.appendChild(hf);
        recordingslist.appendChild(li);
        forceDownload(url, hf.download);
    });
}


function forceDownload(blob, filename) {
    var a = document.createElement('a');
    a.download = filename;
    a.href = blob;
    a.click();
}


/*页面初始化*/
window.onload = function init() {
    try {
        // webkit shim
        window.AudioContext = window.AudioContext || window.webkitAudioContext;
        navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia;
        window.URL = window.URL || window.webkitURL;

        audio_context = new AudioContext;
        __log('音频环境设置.');
        __log('用户设备：' + (navigator.getUserMedia ? '可用.' : '不存在!'));
    } catch (e) {
        alert('这个浏览器不支持网络音频设备!');
    }

    navigator.getUserMedia({audio: true}, startUserMedia, function (e) {
        __log('无音频输入: ' + e);
    });

};
