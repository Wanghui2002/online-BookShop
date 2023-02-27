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
<title>添加工作人员</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<div>
		<p style="font-size: 25px; margin-top: 20px; margin-left: 42px">添加工作人员</p>
	</div>
	<hr
		style="height: 10px; border: none; border-top: 10px groove skyblue;" />
	<div style="margin-top: 100px; margin-left: 430px">
		<form
			action="${pageContext.request.contextPath}/admin/ManagerServlet?op=addAdmin"
			method="post" onsubmit="return checkForm()">
			<div class="form-group">
				<label for="exampleInputEmail1">用户名</label> <input
					class="form-control" type="text" id="username" name="username"
					placeholder="请输入用户名"
					style="width: 700px">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">密码</label> <input
					class="form-control" type="password" id="password" name="password"
					placeholder="请输入密码"
					style="width: 700px">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">姓名</label> <input
					class="form-control" type="text" id="name" name="name"
					placeholder="请输入姓名" style="width: 700px">
			</div>
			<label for="Ssex">性别:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input type="radio" name="sex" value="男" checked="checked">男
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
					name="sex" value="女">女
			<div class="form-group">
				<label for="exampleInputEmail1">手机号</label> <input
					class="form-control" type="text" id="tel" name="tel"
					placeholder="请输入手机号" style="width: 700px">
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
		var username=$("#username").val();
		var password=$("#password").val();
		var name=$("#name").val();
		var tel=$("#tel").val();
		if(username==""){
			alert("请填写用户名");
			return false;
		}
		if(password==""){
			alert("请填写密码");
			return false;
		}
		if(name==""){
			alert("请填写姓名");
			return false;
		}
		if(tel==""){
			alert("请填写手机号");
			return false;
		}
		return true;
		
	}
	
	</script>
</body>
</html>