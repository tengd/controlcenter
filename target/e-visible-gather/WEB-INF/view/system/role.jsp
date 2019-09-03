<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<c:import url="../tags.jsp"></c:import>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:31px;background:#e9f6fe;">
		<div style="margin-left:40%;">
			<a id="award_btn_id" href="#" style="height:29px;width:100px;" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="clickAwardBtn()">授予</a>  
			<a id="move_btn_id" href="#" style="height:29px;width:100px;"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="clickMoveBtn()">移回</a>
		</div>  
	</div>  
     <!-- collapsed:true, 收缩-->
	<div data-options="region:'west',split:true,title:'角色'" style="width:350px;padding:10px;background:#e9f6fe;">
	
	
					<div id="role_edit_popup_win" class="easyui-dialog" data-options="modal:true,closed:true"
							style="height: 150px;width: 350px;padding-top: 5px;" buttons="#role_edit_popup_win_buttons">
								<form id="edit_popup_win_form" style="padding: 0;margin: 0;" method="post">
								    <input id="uuid_input" name="uuid" style="display:none;">
										<table>
												<tr>
													<td style="width: 80px;text-align: right;"><label>角色:</label></td>
													<td>
															<input id="rname_input_id" name="rName"  class="easyui-validatebox" style="width:200px;" data-options="required:true" >  
													</td>
												</tr>
												<tr>
													<td style="width: 80px;text-align: right;"><label>备注:</label></td>
													<td><input id="remark_input_id" name="remark" style="width:200px;"  class="easyui-validatebox" ></td>
												</tr>
										</table>
										
							</form>
				</div>
				
				<div id="role_edit_popup_win_buttons" style="text-align: center;">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-save" plain="true" onclick="clickSaveBtn()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" plain="true" onclick="clickCancelBtn()">关闭</a>
				</div>
				
				
				
				<table id="role_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:100%;height:250px" ></table>
				<div id="role_table_toolbar">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="clickAddBtn()">添加</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="clickUpdBtn()">编辑</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="clickDelBtn()">删除</a>
					<input id="searchRoleBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入角色"  buttonAlign="right" buttonText="查询" >
				</div>
	</div>
	<div data-options="region:'center',split:true,title:'用户'" style="width:150px;padding:10px;">
			<table id="user_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:100%;height:250px" ></table>
			<div id="user_table_toolbar">
				<input id="searchUserBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入用户名"  buttonAlign="right" buttonText="查询" >
			</div>
	</div>
	<div data-options="region:'east',split:true,title:'授予'" style="width:350px;padding:10px;background:#A9FACD;">
			<table id="user_role_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:100%;height:250px" ></table>
	</div>
</body>
<script type="text/javascript">
		/* 获取权限 */
		var getRolesUrl="<%=request.getContextPath()%>/role/getRoles";
		/* 获取用户 */
		var getUsers= "<%=request.getContextPath()%>/user/getUsersByNotLocked";
		/* 获取用户角色 */
		var getUserRoles="<%=request.getContextPath()%>/userRole/getUserRoles";
		$(function(){
					$("#role_table").datagrid({
						title: "角色管理",
						toolbar:"#role_table_toolbar",
						singleSelect: false,
						pagination: true,
						pageList: [15,30,45,60,75],
						fit: true,
						fitColumns: true,
						rownumbers: true,
						url: getRolesUrl,
						ctrlSelect:true,
						columns:[[
									{ field:'id',checkbox:true },
									{ field:'uuid',hidden:true },
									{ field: 'rName', title: '角色' },
									{ field: 'remark', title: '备注' }
						]]
				});
				/* 查询角色 */
				$('#searchRoleBtnId').textbox({    
						onClickButton:function clickQueryBtn(){
							queryRoleData(1);
						}
				});
				$("#user_table").datagrid({
					toolbar:"#user_table_toolbar",
					singleSelect: false,
					pagination: true,
					pageList: [10,20,30,40,50],
					fit: true,
					fitColumns: true,
					rownumbers: true,
					url: getUsers,
					ctrlSelect:true,
					columns:[[
								{ field:'id',checkbox:true },
								{ field:'uuid',hidden:true },
								{ field: 'uName', title: '姓名' }
					]]
				});
				/* 查询用户 */
				$('#searchUserBtnId').textbox({    
						onClickButton:function clickQueryBtn(){
							queryUserData(1);
						}
				});
				$("#user_role_table").datagrid({
					singleSelect: false,
					pagination: true,
					pageList: [10,20,30,40,50],
					fit: true,
					fitColumns: true,
					rownumbers: true,
					url: getUserRoles,
					ctrlSelect:true,
					columns:[[
								{ field:'id',checkbox:true },
								{ field:'uuid',hidden:true },
								{ field: 'userid', title: '用户' },
								{ field: 'roleid', title: '角色' }
					]]
				});
		});
	
		/* 添加工具按钮 */
		function clickAddBtn(){
			   $("#role_edit_popup_win").dialog("open").window("setTitle", "添加角色");
			   $("#edit_popup_win_form").form("clear");
		}
		
		/*  关闭按钮 */
		function clickCancelBtn(){
			 $("#role_edit_popup_win").dialog("close");
		}
		/* 保存按钮 */
		function clickSaveBtn(){
			 $("#edit_popup_win_form").form('submit',{
			 	 url:'<%=request.getContextPath()%>/role/doRole',
			 	 onSubmit:function(){
			 		var isValid = $(this).form('validate');//验证表单中的一些控件的值是否填写正确，比如某些文本框中的内容必须是数字 
			          if (!isValid) { 
			          } 
			          return isValid; // 如果验证不通过，返回false终止表单提交 
			 	},
			 	success:function(data){
			 			$("#role_edit_popup_win").dialog("close");
				 		$('#role_table').datagrid('reload');    
			 			$.messager.alert("系统提示", "成功!", "info");
			    } ,
			    error:function(date){
			    	$("#role_edit_popup_win").dialog("close");
			    	$("#role_table").datagrid('reload');
	    			$.messager.alert("提示", data.msgCode+data.msgContent+ ',请联系管理员');
			    }
		 });
	}
	/* 更新角色 */
	function clickUpdBtn(){
		// 获取当前选中数据
		var rows = $("#role_table").datagrid("getSelections");
		if(rows == null || rows.length == 0){
			$.messager.alert("系统提示", "请选择要编辑的数据!", "info");
			return ;
		}
		$("#edit_popup_win_form").form("clear");
		// 设置form值
		$("#edit_popup_win_form").form("load", rows[0]);
		// 弹出窗
		$("#role_edit_popup_win").dialog("open").window("setTitle", "编辑角色");
	}
	/* 删除角色 */
	function clickDelBtn(){
		// 获取当前选中数据
		var rows = $("#role_table").datagrid("getSelections");
		if(rows == null || rows.length == 0){
			$.messager.alert("系统提示", "请选择要删除的数据!", "info");
			return ;
		}
		$.messager.confirm("注意", "确定删除此角色吗！", function(yn){
			if(yn){
				var uuid = rows[0]["uuid"];
				$.ajax({
					url: "<%=request.getContextPath()%>/role/delRole?uuid="+ uuid,
					type: "post",
					cache: false,// 解决IE下有缓存的问题
					async: false,
					contentType:"application/json; charset=utf-8",
					error: function(data){
						$.messager.alert("系统提示",data.msgCode+":"+data.msgContent, "error");
						$('#role_table').datagrid('reload');
						$('#user_role_table').datagrid('reload');
					},
					success: function(data){
						$.messager.alert("系统提示", data.msgCode+":"+data.msgContent, "info");
						$('#role_table').datagrid('reload');
						$('#user_role_table').datagrid('reload');
					}
				});
			}
		});
	}
	/* 查询角色 */
	function queryRoleData(flag){
		var rname = $("#searchRoleBtnId").textbox("getValue");
		console.info(rname);
		var params = $("#role_table").datagrid("options").queryParams;
		params.rname = rname;
		// 重新复制查询参数
		$("#role_table").datagrid("options").queryParams = params;
		// 重新load
		$("#role_table").datagrid({
			onLoadSuccess: function(data){
				if(data == null || data.total == 0){
					if(flag == 1)
						$.messager.alert("系统提示", "查询结果为空！", "info");
				}
			},
			onLoadError: function(){
				if(flag == 1)
					$.messager.alert("系统提示", "查询数据失败！", "info");
			}
		});
	}
	/* 查询用户 */
	function queryUserData(flag){
		var uname = $("#searchUserBtnId").textbox("getValue");
		console.info(uname);
		var params = $("#user_table").datagrid("options").queryParams;
		params.uname = uname;
		// 重新复制查询参数
		$("#user_table").datagrid("options").queryParams = params;
		// 重新load
		$("#user_table").datagrid({
			onLoadSuccess: function(data){
				if(data == null || data.total == 0){
					if(flag == 1)
						$.messager.alert("系统提示", "查询结果为空！", "info");
				}
			},
			onLoadError: function(){
				if(flag == 1)
					$.messager.alert("系统提示", "查询数据失败！", "info");
			}
		});
	}
	/* 授予 */
	function clickAwardBtn(){
		/* 选中角色表中的值 */
		var select_role_table_values=$("#role_table").datagrid("getChecked");
		/* 角色Ids */
		var roleIds=[];
		/* 选中用户表中值 */
		var select_user_table_values=$("#user_table").datagrid("getChecked");
		var userIds=[];
		$.each(select_role_table_values, function(index, item){
			roleIds.push(item.uuid);
    	});
		$.each(select_user_table_values, function(index, item){
			userIds.push(item.uuid);
    	});
		/* 参数对象 */
		var param={
				roleIds:roleIds.join(","),
				userIds:userIds.join(",")
		}
		
		var jsonText=JSON.stringify(param);
		console.info(jsonText);
		$.ajax({
    		type:"post",
    		url: "<%=request.getContextPath()%>/userRole/addUserRoles",
    		data: jsonText,
			dataType: "JSON",
    		cache: false,// 解决IE下有缓存的问题
			async: false,
    		contentType:"application/json; charset=utf-8",
    		success: function(data){
    			$("#user_role_table").datagrid('reload');
    			$.messager.alert("提示", data.msgCode+data.msgContent);
    		},
    		error: function(data){
    			$("#user_role_table").datagrid('reload');
    			$.messager.alert("提示", data.msgCode+data.msgContent+ ',请联系管理员');
			}
    	});
		
		
	}
	
	/* 移回 */
	function clickMoveBtn(){
		/* 选中用户角色关系值 */
		var select_user_role_table_values=$("#user_role_table").datagrid("getChecked");
		var user_roleIds=[];
		$.each(select_user_role_table_values, function(index, item){
			user_roleIds.push(item.uuid);
    	});
		/* 参数对象 */
		var param={
				userRoleIds:user_roleIds.join(",")
		}
		var jsonText=JSON.stringify(param);
		console.info(jsonText);
		$.ajax({
    		type:"post",
    		url: "<%=request.getContextPath()%>/userRole/delUserRoles",
    		data: jsonText,
			dataType: "JSON",
    		cache: false,// 解决IE下有缓存的问题
			async: false,
    		contentType:"application/json; charset=utf-8",
    		success: function(data){
    			$("#user_role_table").datagrid('reload');
    			$.messager.alert("提示移回", data.msgCode+data.msgContent);
    		},
    		error: function(data){
    			$("#user_role_table").datagrid('reload');
    			$.messager.alert("提示移回", data.msgCode+data.msgContent+ ',请联系管理员');
			}
    	});
	}
	
	
	
	
	
		
</script>
</html>