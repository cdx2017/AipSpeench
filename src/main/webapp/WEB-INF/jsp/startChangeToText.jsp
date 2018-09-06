<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>开始识别cdx </title>
</head>
<style>
    * {
        font-family: "宋体";
        font-size: 14px
    }
</style>
<body>

<div style="text-align:center;">
    <input type="button" name="submit" value="开始识别" onclick="change()">

    <form action="/route/main" method="get">
        <input type="text" name="name" value="" hidden="true"/>

        <p><input type="submit" value="返回主页"
                  style="color: #00c6ff;background-color: #ffffff;border:none;font-size: 20px;"/></p>
    </form>
</div>
<table id="table-result" width="80%" align="center" border="1">
    <tr>
        <th>result</th>
        <th>err_msg</th>
        <th>err_no</th>

    </tr>
    <tbody id="tbody-result">
    </tbody>
</table>

<script type="text/javascript">
    var tbody = window.document.getElementById("tbody-result");
    alert("正在识别，请稍后。。。");
    function change() {
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "/api/v1/AudioToText",//url
            data: {"source": "myPath", "rate": 16000},
            success: function (result) {
                var str = "";

                str += "<tr>" +
                "<td align='center'>" + result.result + "</td>" +
                "<td align='center'>" + result.err_msg + "</td>" +
                "<td align='center'>" + result.err_no + "</td>" +
                "</tr>";

                tbody.innerHTML = str;

            },
            error: function () {
                alert("异常！");
            }
        });
    }
</script>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
</body>
</html>