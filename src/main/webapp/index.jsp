<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Will
  Date: 2016/8/15
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>$Title$</title>
    <script type="text/javascript" src="js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript">
        function onONon() {
            alert(1);
            var token = new Array();
            token.push("123");
            token.push("456");
            $.ajax({
                url: 'api/user/test.form',
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(token),
                timeout:30000,
                async:false,
                success: function (data) {
                    var str = JSON.stringify(data);
                    alert(str);
                },
                error:function (xhr,errorMessage,exception) {
                    if(errorMessage == "timeout"){
                        alert("请求超时检查网络");
                    }
                }
            });
        }
        function re(){
            var verifyImg = document.getElementById("verifyImg");
            verifyImg.src = "${pageContext.request.contextPath }/verifyCode/verifyCode.form?t="+Math.random();
        }
        setTimeout(re,100);
    </script>
</head>
<body>
$END$
<a href="${pageContext.request.contextPath }/api/user/login.form?loginName=will&password=admin&sign=D71DFF9DC1B6BA2158B807CCEA10FCC3"><spring:message code="login"/></a><br>
<a href="${pageContext.request.contextPath }/api/user/queryAll.form?page=1&rows=10&sign=156FC68A4F6143B337E61CB8C3937BB5"><spring:message code="seachAll"/></a><br>
<span  onclick="onONon()">查询全部</span><br>
<img id="verifyImg" src="" onclick="re(this)" >
</body>
</html>
