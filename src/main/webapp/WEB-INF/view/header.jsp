<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<body>
	<!-- Header Start -->
	<header>
		<a href="index.html" class="logo">
			<h3 color="white">
				<font color="white">数据采集系统</font>
			</h3>
		</a>
		<div class="pull-right">
			<ul id="mini-nav" class="clearfix">
				<li class="list-box hidden-xs"><a href="#" data-toggle="modal"
					data-target="#modalMd"> <span class="text-white">当前用户：${currentUser.uName}</span></i>
				</a></li>
				<li class="list-box user-profile"><a id="drop7" href="#"
					role="button" class="dropdown-toggle user-avtar"
					data-toggle="dropdown"> <img
						src="<%=request.getContextPath()%>/Template/img/user5.png" alt="Bluemoon User">
				</a>
					<ul class="dropdown-menu server-activity">
						<li>
							<p>
								<i class="fa fa-cog text-info"></i>用户设置
							</p>
						</li>
						<li>
							<div class="demo-btn-group clearfix">
								<button onclick="logout()" class="btn btn-danger">退出</button>
							</div>
						</li>
					</ul></li>
			</ul>
		</div>
	</header>
	<!-- Header End -->

	<!-- Main Container start -->
	<div class="dashboard-container">

		<div class="container">
			<!-- Top Nav Start -->
			<div id='cssmenu'>
				<ul id="headMenuBar">
				</ul>
			</div>
</body>
<script>
	$(document).ready(function() {
		//加载菜单项
		initHeadMenu();
	});

	function initHeadMenu() {
		$
				.ajax({
					url : "fun/getFunTreeByPermission",
					type : "get",
					cache : false,// 解决IE下有缓存的问题
					async : false,
					contentType : "application/json; charset=utf-8",
					error : function() {
						$("#nav_tree").tree("loadData", "");
						$.messager.alert("系统提示", "加载菜单数据失败！", "warning");
					},
					success : function(data) {
						$("#headMenuBar").html("");
						for (var i = 0; i < data.length; i++) {
							var title = data[i].text;
							var text = "<li class=''><a href='#'><i class=\"fa fa-bar-chart-o\"></i>"
									+ title + "</a><ul>"
							var children = data[i].children;
							for (var j = 0; j < children.length; j++) {
								var subText = "<li><a href='fun/getFunJump?url="
										+ children[j].attributes.url
										+ "'>"
										+ children[j].text + "</a></li>"
								text += subText;
							}
							text += "</ul></li>"
							$("#headMenuBar").append(text);
						}
					}
				});
	}

	function logout() {
		window.location.href = "logout";
	}
</script>
</html>