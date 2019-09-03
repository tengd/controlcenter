<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>数据采集系统</title>
<link href="<%=request.getContextPath()%>/css/easyui/base.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/easyui/login.css" rel="stylesheet">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/easyui/themes/default/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/easyui/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/easyui/jquery.easyui.min.js"></script>
<script src="<%=request.getContextPath()%>/js/system/login.js"></script>
<script>

</script>
</head>
<body class="white">
	<div class="login-hd" >
		<div class="left-bg"></div>
		<div class="right-bg"></div>
		<div class="hd-inner">
			<span class="logo" style="background:url(<%=request.getContextPath()%>/images/easyui/login_logo_w.png) no-repeat"></span> <span class="split"></span> <span
				class="sys-name">数据采集平台</span>
		</div>
	</div>
	<div class="login-bd" style="background:url(<%=request.getContextPath()%>/images/easyui/loginbg.png)" >
		<div class="bd-inner"  style="background:url(<%=request.getContextPath()%>/images/easyui/loginbg.png)" >
			<div class="inner-wrap" style="background:url(<%=request.getContextPath()%>/images/easyui/loginbg.png)" >
				<div class="lg-zone" >
					<div class="lg-box" style="width:364px;height:448px">
						<div class="lg-label">
							<h4>用户登录</h4>
						</div>

						<form id="userForm" method="post" action="loginUrl">
							<div class="lg-username input-item clearfix">
								<i class="iconfont">&#xe60d;</i> <input type="text"
									class="form-control" id="code_input" name="account"
									value="<shiro:principal/>" placeholder="账号">
							</div>
							<div class="lg-password input-item clearfix">
								<i class="iconfont">&#xe634;</i> <input type="password"
									class="form-control" name="password" id="pwd_input"
									placeholder="请输入密码">
							</div>
							<div class="lg-check clearfix">
								<div class="input-item">
									<i class="iconfont">&#xe633;</i> <input type="text"
										id="vcode_input" name="verifyCode" placeholder="验证码">
								</div>
								<div>
									<input id="vcode_img" type="image" title="点击刷新验证码"
										alt="看不清？点击刷新验证码" src="<%=request.getContextPath()%>/getValidateCodeImage"
										style="cursor: hand" onclick="refreshVcodeImg()" />
								</div>
							</div>
							<div class="tips clearfix"></div>
							<div class="enter" align="center">
								<a href="javascript:void(0);" class="purchaser" id="loginBtn"
									onClick="javascript:login()">登录</a>
							</div>
						</form>
						<div class="line line-y"></div>
						<div class="line line-g"></div>
					</div>
				</div>
				<div class="lg-poster" style="cursor:pointer" onclick="javascript:window.location.href='http://www.easy-sight.com/'" ></div>
			</div>
		</div>
	</div>
	<div class="login-ft">

	</div>
	    <c:if test="${message_login != null }">
    			<script type="text/javascript">
	    				$.messager.alert("系统提示", '${message_login}', "info");
	    		</script>
    </c:if>
</body>
</html>
