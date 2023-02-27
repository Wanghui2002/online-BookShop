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
<title>网上在线图书销售管理系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

	<!-- 导航条 -->
<div id="daohang">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                        aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span> <span
                        class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
                <a href="${pageContext.request.contextPath}/client/ClientServlet?op=category"><img style="background-size:contain;height: 50px;width: 200px"
                                                       src="${pageContext.request.contextPath}/img/logo.png" />
                </a>
                <c:if test="${!empty user}">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您：<span
                    style="color: red; font-weight: bold">${sessionScope.user.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;网上书店伴您畅游书海。
                    <img src="/img/gerenzx.png"
                         style="margin-left: 1000px"/>&nbsp;&nbsp;
                    <a href="/personalCenter.jsp" style="color: black">个人中心</a>
                </c:if>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse"
                 id="bs-example-navbar-collapse-1">
                <c:if test="${empty user}">
                    <div style="float: right; margin-right: 7px; margin-top: 10px">
                        <!-- 登录 -->
                        <button type="button" class="btn btn-default btn-sm"
                                data-toggle="modal" data-target="#myModal">登录
                        </button>
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
                             aria-labelledby="myModalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <h4 class="modal-title" id="myModalLabel">登录</h4>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/client/ClientServlet?op=login"
                                          method="post">
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label for="exampleInputName2">用户名 </label> <input
                                                    type="text" name="username" class="form-control"
                                                    id="exampleInputName2" placeholder="请输入用户名">
                                            </div>
                                            <br>
                                            <div class="form-group">
                                                <label for="exampleInputPassword1">密码</label> <input
                                                    type="password" name="password" class="form-control"
                                                    id="exampleInputPassword1" placeholder="请输入密码">
                                            </div>
                                            <br>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default"
                                                    data-dismiss="modal">关闭
                                            </button>
                                            <button type="submit" class="btn btn-primary">登录</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- 注册 -->
                        <button type="button" class="btn btn-default btn-sm"
                                data-toggle="modal" data-target="#myModal1">注册
                        </button>

                        <div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
                             aria-labelledby="myModalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <h4 class="modal-title" id="myModalLabel">注册</h4>
                                    </div>
                                    <form name="register-form" action="${pageContext.request.contextPath}/client/ClientServlet?op=register"
                                            method="post" onsubmit = "return checkform();">
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label for="exampleInputName2">用户名称:</label> <input
                                                    type="text" id="username" name="username"
                                                    class="form-control" 
                                                    placeholder="请输入用户名称">
                                            </div>
                                            <span id="s1"></span> <br>
                                            <div class="form-group">
                                                <label for="exampleInputPassword1">密码:</label> <input
                                                    type="password" name="password" class="form-control"
                                                    id="password" placeholder="请输入密码">
                                            </div>
                                            <div class="form-group">
                                                <label for="sub-password">确认密码:</label> <input
                                                    type="password"  class="form-control"
                                                    id="sub-password" placeholder="请输入确认密码">
                                            </div>
                                            <div class="form-group">
                                                <label for="exampleInputName2">姓名:</label> <input
                                                    type="text" name="name" class="form-control"
                                                    id="name" placeholder="请输入姓名">
                                            </div>
                                            <label for="Ssex">性别:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <input type="radio" name="sex" value="男">男
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
                                                type="radio" name="sex" value="女">女 <br>
                                            <div class="form-group">
                                                <label for="exampleInputName2">手机号:</label> <input
                                                    type="text" name="tel" class="form-control"
                                                    id="tel" placeholder="请输入手机号">
                                            </div>
                                            <div class="form-group">
                                                <label for="exampleInputName2">地址:</label> <input
                                                    type="text" name="address" class="form-control"
                                                    id="address" placeholder="请输入地址">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default"
                                                    data-dismiss="modal">关闭
                                            </button>
                                            <button type="submit" class="btn btn-primary" id="add-register">注册</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${!empty user}">
                    <div style="float: right; margin-right: 7px; margin-top: 10px">
                        <a class="btn btn-default btn-sm" target="_parent"
                           href="../client/ClientServlet?op=layout"
                           role="button">注销</a>
                    </div>
                    <div style="float: right; margin-right: 7px; margin-top: 10px">
                        <a class="btn btn-default btn-sm" target="_parent"
                           href="${pageContext.request.contextPath}/showCart.jsp" role="button">购物车</a>
                    </div>
                    <div style="float: right; margin-right: 7px; margin-top: 10px">
                        <a class="btn btn-default btn-sm" target="_parent"
                           href="${pageContext.request.contextPath}/client/ClientServlet?op=showfavorite"
                           role="button">收藏夹</a>
                    </div>
                </c:if>
                <div style="float: right; margin-right: 7px; margin-top: 10px">
                    <a class="btn btn-default btn-sm"
                       href="${pageContext.request.contextPath}/admin/managerLogin.jsp"
                       role="button">管理员入口</a>
                </div>
            </div>
        </div>
    </nav>
</div>
<script type="text/javascript">
  function checkform(){
	 if($("#username").val()==""||$("#username").val()==null){
		alert("请填写用户名");
		return  false;
	}
	if($("#password").val()==""||$("#password").val()==null){
		alert("请填写密码");
		return false;
	}
	if($("#sub-password").val()==""||$("#sub-password").val()==null){
		alert("请填写确认密码");
		return false;
	}
	if($("#password").val()!=$("#sub-password").val()){
		alert("两次密码不一致");
		return false;
	}
	if($('input[type="radio"]:checked').length==0){
		alert("请选择性别!");
		return false;
	}
	if($("#name").val()==""|| $("#name").val()==null){
		alert("请填写姓名!");
		return false;
	}
	if($("#tel").val()==""){
		alert("请填写手机号!");
		return false;
	}
	if($("#address").val()==""){
		alert("请填写地址!");
		return false;
	} 
	return true;
  }

</script>
</body>
</html>