<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>处理页面操作</title>
<style>
html {
  margin: 0;
  padding: 0;
  background-color: white;
}

body,
html {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

#svgContainer {
  width: 640px;
  height: 512px;
  background-color: white;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
}
</style>
</head>
<body style="background-color: rgb(240, 243, 239);">

	&nbsp;&nbsp;&nbsp;&nbsp;${message}
	<h7>3秒后跳转到主页面，若没有跳转请点击<a href="${pageContext.request.contextPath}/client/ClientServlet?op=category">这里</a> </h7>
	<%
		response.setHeader("refresh", "0;URL=../client/ClientServlet?op=category");
	%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bodymovin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/data.js"></script>

<div id="svgContainer"></div>

<script type="text/javascript">
var svgContainer = document.getElementById('svgContainer');
var animItem = bodymovin.loadAnimation({
  wrapper: svgContainer,
  animType: 'svg',
  loop: true,
  animationData: JSON.parse(animationData)
});
</script>

<div style="text-align:center;margin:10px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
</html>