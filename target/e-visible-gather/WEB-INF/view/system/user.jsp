<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<c:import url="../tags.jsp"/>
</head>
<body>

		<div id="edit_popup_win" class="easyui-dialog" data-options="modal:true,closed:true"
		style="height: 300px;width: 350px;padding-top: 5px;" buttons="#edit_popup_win_buttons">
				<form id="edit_popup_win_form" style="padding: 0;margin: 0;" method="post">
						<table>
							<!-- <tr>
								<td style="width: 80px;text-align: right;"><label>用户类型:</label></td>
								<td>
										<input id="sdicdateId_input" name="sDicDateId"  class="easyui-combobox" style="width:200px;height:25px;">  
								</td>
							</tr> -->
							<tr>
								<td style="width: 80px;text-align: right;"><label>工号:</label></td>
								<td><input id="jobNum" name="jobNum" style="width:200px;"  class="easyui-validatebox"  data-options="required:true" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>用户名:</label></td>
								<td><input id="unameId" name="uName" style="width:200px;"  class="easyui-validatebox"  data-options="required:true" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>密码:</label></td>
								<td><input id="passId" name="pas" style="width: 200px" type="password"  class="easyui-validatebox"  data-options="required:true" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>电话:</label></td>
								<td><input id="phoneId" name="phone" style="width: 200px"  class="easyui-validatebox" data-options="required:true" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>地址:</label></td>
								<td><input id="addressId" name="address" style="width: 200px"  class="easyui-validatebox" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>邮件:</label></td>
								<td><input id="emailId" name="email" style="width: 200px"  class="easyui-validatebox" data-options="validType:'email'"></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>微信号:</label></td>
								<td><input id="wecharNoId" name="wecharNo" style="width: 200px"  class="easyui-validatebox" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>QQ号:</label></td>
								<td><input id="qqNOId" name="qqNo" style="width: 200px"  class="easyui-validatebox"></td>
							</tr>
						</table>
						<input id="uuid_input" name="uuid" style="display:none;">
				</form>
		</div>
		
		
		<div id="edit_popup_win_buttons" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-save" plain="true" onclick="clickSaveBtn()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" plain="true" onclick="clickCancelBtn()">取消</a>
		</div>
		
		
		
		
		
		<table id="user_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:50%;height:250px" ></table>
		<div id="user_table_toolbar">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="clickAddBtn()">添加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="clickUpdBtn()">编辑</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="clickDelBtn()">删除</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-man',plain:true" onclick="clickAccountBtn()">用户账号</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-lock',plain:true" onclick="clickLockedBtn()">用户锁</a>
			<input id="searchBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入用户名"  buttonAlign="right" buttonText="查询" >
		</div>
		
		<!-- 用户账号 -->
		<div id="account_popup_win" class="easyui-dialog" data-options="modal:true,closed:true" style="height: 500px;width: 800px;padding-top: 5px;">
				
		</div>
		
		<table id="account_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:50%;height:250px" ></table>
		<div id="account_table_toolbar">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="clickAddAccountBtn()">添加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="clickUpdAccountBtn()">编辑</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="clickDelAccountBtn()">删除</a>
		</div>
		
		
	    
</body>
<script type="text/javascript">
		var getUsers= "<%=request.getContextPath()%>/user/getUsers";
		$(function(){
			$("#edit_popup_win").dialog("close");
			$("#account_popup_win").dialog("close");
			$('#user_table').datagrid({    
				title: "用户管理",
				toolbar:"#user_table_toolbar",
				//iconCls:'icon-edit',
				singleSelect: true,
				pagination: true,
				pageList: [15,30,45,60,75],
				fit: true,
				fitColumns: true,
				rownumbers: true,
				url: getUsers,
				columns:[[
							{ field:'uuid',hidden:true },
						/* 	{ field: 'sDicDateId', title: '类型' }, */
							{ field: 'uName', title: '姓名' },
							{ field: 'account', title: '工号' },
							{ field: 'pas', hidden:true},
							{field:'salt',title:'TOKEN'},
							{ field: 'phone', title: '手机号码' },
							{ field: 'address', title: '地址' },
							{ field: 'email', title: '邮件' },
							{ field: 'qqNo', title: 'QQ' },
							{ field: 'locked', title: '是否被锁定',formatter: function(value,row,index){
											if(row.locked==0){
												return row.locked+'&nbsp;<a style="width:40px;height:20px;color:blue;" >锁定</a>';
											}else{
												return row.locked+'&nbsp;<a style="width:40px;height:20px;color:red;">解锁</a>';
											}
										}
							 }
				]]
			}); 
			$('#searchBtnId').textbox({    
				onClickButton:function clickQueryBtn(){
					queryData(1);
				}
			});
		});
		/* 用户锁定与解锁 */
		function clickLockedBtn(){
			// 获取当前选中数据
			var rows = $("#user_table").datagrid("getSelections");
			if(rows == null || rows.length == 0){
				$.messager.alert("系统提示", "请选择要锁用户!", "info");
				return ;
			}
			var uuid = rows[0]["uuid"];
			var locked=rows[0]["locked"];
			if(uuid==undefined&&locked==undefined&&uuid==null&&locked==null){
				$.messager.alert("系统提示", "用户id锁为空，请联系管理员!", "info");
				return ;
			}
			var url;
			if(locked==0){
				url="<%=request.getContextPath()%>/user/updateUserLocked?uuid="+ uuid+"&&locked=1"
			}else{
				url="<%=request.getContextPath()%>/user/updateUserLocked?uuid="+ uuid+"&&locked=0"
			}
			$.ajax({
					url:url ,
					type: "post",
					cache: false,// 解决IE下有缓存的问题
					async: false,
					dataType: "JSON",
					contentType:"text/html;charset=utf-8",
					error: function(data){
						$.messager.alert("系统提示",data.msgCode+":"+data.msgContent, "error");
						$('#user_table').datagrid('reload');
					},
					success: function(data){
						$.messager.alert("系统提示", data.msgCode+":"+data.msgContent, "info");
						$('#user_table').datagrid('reload');
					}
			});
		
				
		}
		
		
		/* 添加工具条*/
	   function clickAddBtn(){
		   $("#edit_popup_win").dialog("open").window("setTitle", "添加用户");
		   $("#edit_popup_win_form").form("clear");
		   /* 获取字典表中的用户类型 */
		   $("#sdicdateId_input").combobox({
				   url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=U',    
				    valueField:'uuid',    
				    textField:'dName'
		   });
	   }
	  /* 关闭按钮 */
	  function clickCancelBtn(){
		  $("#edit_popup_win").dialog("close");
	  }
	 /* 保存按钮 */ 
	 function clickSaveBtn(){
		 $("#edit_popup_win_form").form('submit',{
			 	url:'<%=request.getContextPath()%>/user/doUser',
			 	dataType: "JSON",
				contentType:"text/html; charset=UTF-8",
			 	onSubmit:function(){
			 		var isValid = $(this).form('validate');//验证表单中的一些控件的值是否填写正确，比如某些文本框中的内容必须是数字 
			          if (!isValid) { 
			          } 
			          return isValid; // 如果验证不通过，返回false终止表单提交 
			 	},
			 	success:function(){
			 		
				 		$('#user_table').datagrid('reload');   
				 		$("#edit_popup_win").dialog("close");
				 		var data = eval('(' + data + ')');  
			 			$.messager.alert("系统提示", "新增用户信息成功！", "info");
			 			
			 	
			    },
			    onLoadError:function(none){
			    	$('#user_table').datagrid('reload');   
			 		$("#edit_popup_win").dialog("close");
			    	$.messager.alert("系统提示", none, "error");
			    }
		 });
	 }
	/*  编辑按钮 */
	function clickUpdBtn(){
		// 获取当前选中数据
		var rows = $("#user_table").datagrid("getSelections");
		if(rows == null || rows.length == 0){
			$.messager.alert("系统提示", "请选择要编辑的数据!", "info");
			return ;
		}
		$("#edit_popup_win_form").form("clear");
		// 设置form值
		$("#edit_popup_win_form").form("load", rows[0]);
		$("#sdicdateId_input").combobox("disable");
		// 弹出窗
		$("#edit_popup_win").dialog("open").window("setTitle", "编辑用户");
	}
	/* 删除按钮 */ 
	function clickDelBtn(){
		// 获取当前选中数据
		var rows = $("#user_table").datagrid("getSelections");
		if(rows == null || rows.length == 0){
			$.messager.alert("系统提示", "请选择要删除的数据!", "info");
			return ;
		}
		$.messager.confirm("注意", "此用户如果删除后恢复需要一个慢长的过程！", function(yn){
			if(yn){
				var uuid = rows[0]["uuid"];
				$.ajax({
					url: "<%=request.getContextPath()%>/user/delUser?uuid="+ uuid,
					type: "post",
					cache: false,// 解决IE下有缓存的问题
					async: false,
					dataType: "JSON",
					contentType:"text/html;charset=utf-8",
					error: function(data){
						$.messager.alert("系统提示",data.msgCode+":"+data.msgContent, "error");
						$('#user_table').datagrid('reload');
					},
					success: function(data){
						$.messager.alert("系统提示", data.msgCode+":"+data.msgContent, "info");
						$('#user_table').datagrid('reload');
					}
				});
			}
		});
	}
	/* 查询 */
	function queryData(flag){
		var uname = $("#searchBtnId").textbox("getValue");
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
	var getAccount= "<%=request.getContextPath()%>/account/getAccount";
	/* 用户账号 */
	function clickAccountBtn(){
		$("#account_popup_win").dialog("open").window("setTitle", "用户账户");
		$('#account_table').datagrid({    
			title: "用户账号管理",
			toolbar:"#account_table_toolbar",
			singleSelect: true,
			pagination: true,
			pageList: [10,20,30,40,50],
			fit: true,
			fitColumns: true,
			rownumbers: true,
			url: getAccount,
			columns:[[
						{ field:'uuid',hidden:true }
			]]
		}); 
	}
		
</script>
</html>