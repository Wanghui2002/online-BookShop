<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			 ;
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改管理员密码</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<div>
		<p style="font-size: 25px; margin-top: 20px; margin-left: 42px">修改登录密码</p>
	</div>
	<hr
		style="height: 10px; border: none; border-top: 10px groove skyblue;" />
	<div style="margin-top: 100px; margin-left: 400px">
		<form action="${pageContext.request.contextPath}/admin/ManagerServlet?op=managerPassword"
			method="post" onsubmit="return checkForm()">
			<div class="form-group">
				<label >旧密码</label> <input
					class="form-control" type="password" id="oldPassword" name="oldPassword"
					style="width: 700px">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">新密码</label> <input
					class="form-control" type="password" id="password" name="password"
					style="width: 700px">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">确认密码</label> <input
					class="form-control" type="password" id="repassword"
					name="repassword" style="width: 700px">
			</div>
			<div class="form-group">
				<input class="form-control" type="hidden" id="username"
					name="username" value="${sessionScope.admin.username}" readonly
					style="width: 700px">
			</div>
			<br>
			<button type="submit" class="btn btn-primary">提交</button>
			<button type="button" class="btn btn-default" data-dismiss="modal"
				style="margin-left: 100px"
				onclick="window.location='managerIndex.jsp'">关闭</button>
		</form>
	</div>
	<script type="text/javascript">
	function checkForm(){
		var oldPassword=$("#oldPassword").val();
		var password=$("#password").val();
		var repassword=$("#repassword").val();
		if(oldPassword==""){
			alert("请填写旧密码!");
			return false;
		}
		if(password==""){
			alert("请填写新密码!");
			return false;
		}
		if(repassword==""){
			alert("请填写确认密码!");
			return false;
		}
		if(password!=repassword){
			alert("两次密码不一致!");
			return false;
		}
		return true;
	}
	
	</script>
</body>
</html>