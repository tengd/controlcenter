<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
<c:import url="../tags.jsp"></c:import>
</head>
<body >
			<div id="permission_popup_win" class="easyui-dialog" data-options="modal:true,closed:true"
							style="height: 400px;width: 600px;padding-top: 5px;" buttons="#permission_popup_win_buttons">
								<form id="permission_popup_win_form" style="padding: 0;margin: 0;" method="post">
										<table>
												<tr>
														<td style="width: 80px;text-align: right;"><lable>功能选择:</lable></td>
														<td><select id="fun_select_id"   name="fun_uuid"  class="easyui-combotree" style="width:450px;" data-options="required:true"></select></td>
												</tr>
												<tr>
													<td style="width: 80px;text-align: right;"><lable>角色选择:</lable></td>
													<td ><input id="role_select_id" name="role_uuid" style="width:430px;" data-options="required:true"/> </td>
												</tr>
												<tr>
													<td style="width: 80px;text-align: right;"><lable>权限:</lable><p style="color:red;">目前页面中的权限-所有</p></td>
													<td >
														   <input id="s_dic_date_id" name="s_dic_date_value" style="width:450px;" data-options="required:true"/>
													</td>
												</tr>
												<tr>
													<td style="text-align:center;" colspan="2">
														<a href="javascript:void(0)" style="margin-left: 20%;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'"onclick="createBtnClick()">生成规则</a>
													</td>
												</tr>
												<tr>
													<td style="width: 80px;text-align: right;"><label>权限:</label></td>
													<td><input id="permission_input_id" name="permission" data-options="multiline:true" style="height:60px;width:450px;"  class="easyui-textbox" ></td>
												</tr>
												
												<tr>
													<td style="width: 80px;text-align: right;"><label>权限名称编写:</label></td>
													<td >
															<input id="pName_input_id" name="pName" data-options="multiline:true" style="height:60px;width:450px;" class="easyui-textbox" > 
													</td>
												</tr>
												
										</table>
										<input id="uuid_input" name="uuid" style="display:none;">
							</form>
				</div>
				
				<div id="permission_popup_win_buttons" style="text-align: center;">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-save" plain="true" onclick="clickSaveBtn()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" plain="true" onclick="clickCancelBtn()">关闭</a>
				</div>
				
			<table id="permission_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:50%;height:250px"  ></table>
			<div id="permission_table_toolbar">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" plain="true" onclick="clickAddBtn()">添加</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"  plain="true" onclick="clickDelBtn()">删除</a>
					<input id="searchPermissionBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入权限"  buttonAlign="right" buttonText="查询" >
			</div>
</body>
<script type="text/javascript">
		var getPerUrl="<%=request.getContextPath()%>/per/getPermissions"
		$(function(){
				$("#permission_table").datagrid({
					title: "权限管理",
					toolbar:"#permission_table_toolbar",
					singleSelect: false,
					pagination: true,
					pageList: [15,30,45,60,75],
					fit: true,
					fitColumns: true,
					rownumbers: true,
					url: getPerUrl,
					ctrlSelect:true,
					columns:[[
								{ field:'id',checkbox:true },
								{ field:'uuid',hidden:true },
								{ field: 'pName', title: '名称' },
								{ field: 'permission', title: '权限' }
					]]
			});
			/* 查询权限 */
				$('#searchPermissionBtnId').textbox({    
						onClickButton:function clickQueryBtn(){
							queryPermissionData(1);
						 }
				});
			
		});
		
		/* 查询权限 */
		function queryPermissionData(flag){
			var pname = $("#searchPermissionBtnId").textbox("getValue");
			console.info(pname);
			var params = $("#permission_table").datagrid("options").queryParams;
			params.pname = pname;
			// 重新复制查询参数
			$("#permission_table").datagrid("options").queryParams = params;
			// 重新load
			$("#permission_table").datagrid({
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
		/* 添加按钮 */
		function clickAddBtn(){
			 $("#permission_popup_win").dialog("open").window("setTitle", "添加权限");
			 $("#permission_popup_win_form").form("clear");
			 /* 功能选择 */
			$('#fun_select_id').combotree({    
				    url:'<%=request.getContextPath()%>/fun/getFunTree',
				    required: true
			});  
			/* 角色选择 */
			$('#role_select_id').combogrid({
				panelWidth:300, 
				idField:'uuid',    
				textField:'rName',
				
				pagination: true,
				pageList: [10,20,30,40,50],
				fit: true,
				fitColumns: true,
				rownumbers: true,
				url: '<%=request.getContextPath()%>/role/getRoles',
				columns:[[
							{ field:'uuid',hidden:true },
							{ field: 'rName', title: '角色' },
							{ field: 'remark', title: '备注' }
				]]
			});  
			  /* 获取字典表中的用户类型 */
			   $("#s_dic_date_id").combobox({
					   url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=P', 
					   multiple:true,
					   separator:',',
					   valueField:'dValue',    
					   textField:'dName'
			   }); 
		}
		/*  关闭按钮 */
		function clickCancelBtn(){
			 $("#permission_popup_win").dialog("close");
		}
		/*生成权限*/
		function createBtnClick(){
			/* 选择功能树值 */
			var fun_uuid = $('#fun_select_id').combotree('tree').tree('getSelected').id;
			var fun_name=$('#fun_select_id').combotree('tree').tree('getSelected').text;
			/* 选择角色表格值 */
			var role_uuid= $('#role_select_id').combogrid('grid').datagrid('getSelected').uuid;
			var role_name=$('#role_select_id').combogrid('grid').datagrid('getSelected').rName;
			/* 选择权限值 */
			var s_dic_date_value=$('#s_dic_date_id').combobox("getValues");
			console.log(s_dic_date_value);
			//var txtValue=
			/* 权限规则 */
			$("#permission_input_id").textbox({
				value:fun_name+":"+role_name+":"+s_dic_date_value
			});
			 $("#pName_input_id").textbox({
				 value:fun_name+"功能-"+role_name+"(角色)-拥有"
			 });
		}
	
			
			
		 /* 添加权限 */
		function clickSaveBtn(){
				 $("#permission_popup_win_form").form('submit',{
				 	 url:'<%=request.getContextPath()%>/per/addSPermission',
				 	 onSubmit:function(){
				 		var isValid = $(this).form('validate');//验证表单中的一些控件的值是否填写正确，比如某些文本框中的内容必须是数字 
				          if (!isValid) { 
				          } 
				          return isValid; // 如果验证不通过，返回false终止表单提交 
				 	},
				 	success:function(data){
					 	$("#permission_popup_win").dialog("close");
						$('#permission_table').datagrid('reload');    
						var data = eval('(' + data + ')');  
					 	$.messager.alert("系统提示", data.msgCode+":"+data.msgContent, "info");
				    },
				    onLoadError:function(none){
				    	$.messager.alert("系统提示", none, "error");
				    }
			 });
		}
		 /* 删除 */
		 function clickDelBtn(){
				/* 选中用户角色关系值 */
				var select_permission_table_values=$("#permission_table").datagrid("getChecked");
				var permissionIds=[];
				$.each(select_permission_table_values, function(index, item){
					permissionIds.push(item.uuid);
		    	});
				/* 参数对象 */
				var param={
						permission_ids:permissionIds.join(",")
				}
				var jsonText=JSON.stringify(param);
				console.info(jsonText);
				$.ajax({
		    		type:"post",
		    		url: "<%=request.getContextPath()%>/per/delSPermission",
		    		data: jsonText,
					dataType: "JSON",
		    		cache: false,// 解决IE下有缓存的问题
					async: false,
		    		contentType:"application/json; charset=utf-8",
		    		success: function(data){
		    			$.messager.alert("提示删除", data.msgCode+":"+data.msgContent);
		    			$("#permission_table").datagrid('reload');
		    		},
		    		error: function(data){
		    			$.messager.alert("提示删除", data.msgCode+":"+data.msgContent+ ',请联系管理员');
		    			$("#permission_table").datagrid('reload');
					}
		    	});
		 }
</script>
</html>