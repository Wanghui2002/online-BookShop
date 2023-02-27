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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
<title>管理员个人信息修改</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<div>
		<p style="font-size: 25px; margin-top: 20px; margin-left: 42px">修改个人信息</p>
	</div>
	<hr
		style="height: 10px; border: none; border-top: 10px groove skyblue;" />
	<div style="margin-top: 100px; margin-left: 430px">
		<form
			action="${pageContext.request.contextPath}/admin/ManagerServlet?op=managerInformation"
			method="post" onsubmit="return checkForm()">
			<div class="form-group">
				<label for="exampleInputEmail1">用户名</label> <input
					class="form-control" type="text" id="username" name="username"
					value="${sessionScope.admin.username}" readonly
					style="width: 700px">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">姓名</label> <input
					class="form-control" type="text" id="name" name="name"
					value="${sessionScope.admin.name}" style="width: 700px">
			</div>
			<label for="Ssex">性别:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> 
			<c:if test="${sessionScope.admin.sex =='男'}">
			<input
				type="radio" name="sex" value="男" checked="checked">男
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
				name="sex" value="女">女
			</c:if>
			<c:if test="${sessionScope.admin.sex =='女'}">
			<input
				type="radio" name="sex" value="男" >男
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
				name="sex" value="女" checked="checked">女
			</c:if> <br> 
			<div class="form-group">
				<label for="exampleInputEmail1">手机号</label> <input
					class="form-control" type="text" id="tel" name="tel"
					value="${sessionScope.admin.tel}" style="width: 700px">
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
			var name=$("#name").val();
			var tel=$("#tel").val();
			if(name==""|| name==null){
				alert("请填写姓名!");
				return false;
			}
			if($("input[type='radio']:checked").length==0){
				alert("请选择性别!");
				return false;
			}
			if(tel==null||tel==""){
				alert("请填写手机号!");
				return false;
			}
			return true;
		}
	
	</script>
</body>
</html>