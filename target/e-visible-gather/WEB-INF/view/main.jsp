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
<link href="<%=request.getContextPath()%>/css/easyui/platform.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/easyui/easyui.css">

</head>
<body>
	<div class="container">
		<div id="pf-hd">
			<div class="pf-logo">
				<img src="<%=request.getContextPath()%>/images/easyui/main/main_logo.png" alt="logo">
			</div>




			<div class="pf-user">
				<div class="pf-user-photo">
					<img src="<%=request.getContextPath()%>/images/easyui/main/user.png" alt="">
				</div>
				<h3 class="pf-user-name ellipsis">${currentUser.uName}</h3>
				<i class="iconfont xiala">&#xe607;</i>

				<div class="pf-user-panel">
					<ul class="pf-user-opt">
						<li class="pf-logout"><a href="javascript:logout()"> <i
								class="iconfont">&#xe60e;</i> <span class="pf-opt-name">退出</span>
						</a></li>
					</ul>
				</div>
			</div>

		</div>

		<div id="pf-bd">
			<div id="pf-sider">
			<div>
				<h2 class="pf-model-name">
					<span class="iconfont">&#xe64a;</span> <span class="pf-name">功能树</span>
					<a onclick="refreshMenu()" class="easyui-linkbutton">刷新菜单</a>
					<span class="toggle-icon"></span>
				</h2>
</div>
				<ul id="mainpage_sidemenu" class="sider-nav">

				</ul>
			</div>

			<div id="pf-page" id="mainpage_tabBar">
				<div class="easyui-tabs1" style="width: 100%; height: 100%;" id="mainpage_tabs">
				</div>

			</div>
		</div>

		<div id="pf-ft">
			<div class="system-name">
				<i class="iconfont">&#xe6fe;</i> <span>数据采集平台&nbsp;v1.0</span>
			</div>
			<div class="copyright-name">
				<span>CopyRight&nbsp;2017&nbsp;&nbsp;深圳市榕时代科技有限公司&nbsp;版权所有</span> <i
					class="iconfont">&#xe6ff;</i>
			</div>
		</div>
	</div>
	<div id="menu_test" class="easyui-menu" style="width:120px;">
<div onclick="refreshMenu()">刷新菜单</div>
</div>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/easyui/jquery.easyui.min.js"></script>
	<!-- <script type="text/javascript" src="js/menu.js"></script> -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/easyui/main.js"></script>
	<script src="<%=request.getContextPath()%>/js/system/mainpage.js"></script>
	<!--[if IE 7]>
      <script type="text/javascript">
        $(window).resize(function(){
          $('#pf-bd').height($(window).height()-76);
        }).resize();
        
      </script>
    <![endif]-->


	<script type="text/javascript">
	$(function(){
		$("#pf-sider").bind('contextmenu',function(e){
			e.preventDefault();
			$('#menu_test').menu('show', {
				left:e.pageX,
				top:e.pageY
			});
		});
	});
		$('.easyui-tabs1')
				.tabs(
						{
							tabHeight : 44,
							onSelect : function(title, index) {
								var currentTab = $('.easyui-tabs1').tabs(
										"getSelected");
								if (currentTab.find("iframe")
										&& currentTab.find("iframe").size()) {
									currentTab.find("iframe").attr(
											"src",
											currentTab.find("iframe").attr(
													"src"));
								}
							}
						})
		$(window).resize(function() {
			$('.tabs-panels').height($("#pf-page").height() - 46);
			$('.panel-body').height($("#pf-page").height() - 76)
		}).resize();

		var page = 0, pages = ($('.pf-nav').height() / 70) - 1;

		if (pages === 0) {
			$('.pf-nav-prev,.pf-nav-next').hide();
		}
		$(document).on('click', '.pf-nav-prev,.pf-nav-next', function() {

			if ($(this).hasClass('disabled'))
				return;
			if ($(this).hasClass('pf-nav-next')) {
				page++;
				$('.pf-nav').stop().animate({
					'margin-top' : -70 * page
				}, 200);
				if (page == pages) {
					$(this).addClass('disabled');
					$('.pf-nav-prev').removeClass('disabled');
				} else {
					$('.pf-nav-prev').removeClass('disabled');
				}

			} else {
				page--;
				$('.pf-nav').stop().animate({
					'margin-top' : -70 * page
				}, 200);
				if (page == 0) {
					$(this).addClass('disabled');
					$('.pf-nav-next').removeClass('disabled');
				} else {
					$('.pf-nav-next').removeClass('disabled');
				}

			}
		})

		// setTimeout(function(){
		//    $('.tabs-panels').height($("#pf-page").height()-46);
		//    $('.panel-body').height($("#pf-page").height()-76)
		// }, 200)
		
		function refreshMenu(){
			initHeadMenu();
		}
	</script>

</body>
</html>

