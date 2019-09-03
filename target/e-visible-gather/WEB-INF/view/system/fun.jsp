<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能管理</title>
<c:import url="../tags.jsp"></c:import>
</head>
<body>
		<div id="edit_popup_win" class="easyui-dialog" data-options="modal:true,closed:true"
				style="height: 300px;width:100%;padding-top: 5px;" buttons="#edit_popup_win_buttons">
				<form id="edit_popup_win_form" style="padding: 0;margin: 0;" method="post">
						<table>
							<tr>
								<td style="width: 80px;text-align: right;"><label>功能名称:</label></td>
								<td>
										<input id="fNameId" name="fName" style="width:200px;"  class="easyui-validatebox"> 
								</td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>简称:</label></td>
								<td><input id="fNameJxId" name="fNameJx" style="width:200px;" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>功能类型:</label></td>
								<td><input id="funTypeid" name="funType"  class="easyui-combobox"  style="width:200px;" /></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>排序号:</label></td>
								<td><input id="sortIndexId" name="sortIndex" style="width: 200px"  class="easyui-numberbox" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>父节点:</label></td>
								<td><input id="paterIdId" name="paterId"  class="easyui-combobox"  style="width: 200px;" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>连接映射URL:</label></td>
								<td >
										<input id="urlId" name="url"  style="height:60px;width:200px;" class="easyui-textbox" > 
								</td>
							</tr>
						</table>
						<input id="uuid_input" name="uuid" style="display:none;">
				</form>
		</div>
		<div id="edit_popup_win_buttons" style="text-align: center;">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-save" plain="true" onclick="clickSaveBtn()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" plain="true" onclick="clickCancelBtn()">关闭</a>
		</div>
		<table id="fun_table" class="easyui-treegrid" toolbar="#fun_table_toolbar" title="功能结构管理"
					data-options="iconCls: 'icon-ok',rownumbers: true,animate: true,collapsible: true,fitColumns: true,url:'<%=request.getContextPath()%>/fun/getSfuns',method: 'get',idField: 'uuid',treeField: 'fName'">
				<thead>
					<tr>
						<th data-options="field:'fName',width:200">功能名称</th>
		                <th data-options="field:'fNameJx',width:60">功能简称</th>
		                <th data-options="field:'funType',width:80">类型</th>
		                <th data-options="field:'sortIndex',width:80">排序号</th>
		                <th data-options="field:'url',width:80">连接URL</th>
					</tr>
				</thead>
		</table>
		<div id="fun_table_toolbar">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="clickAddBtn()">添加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="clickUpdBtn()">编辑</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="clickDelBtn()">删除</a>
			<input id="searchBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入功能名称"  buttonAlign="right" buttonText="查询" >
		</div>
</body>
<script type="text/javascript">
		$(function(){
			 $('#searchBtnId').textbox({    
					onClickButton:function clickQueryBtn(){
						queryData(1);
					}
			 });
		});
		/* 添加工具条 */
		function clickAddBtn(){
			 $("#edit_popup_win").dialog("open").window("setTitle", "添加功能");
			 $("#edit_popup_win_form").form("clear");
			 $("#funTypeid").combobox("clear");
			 /* 获取字典表中的 功能类型 */
			 $("#funTypeid").combobox({
					   	url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=FUN',    
					    valueField:'dValue',    
					    textField:'dName'
			 }); 
			 $("#paterIdId").combobox("clear");
			 $("#paterIdId").combobox({
				 	url:'<%=request.getContextPath()%>/fun/getPaterFuns',    
				    valueField:'uuid',    
				    textField:'fName'
			 });
		}
		/* 关闭按钮 */
		function clickCancelBtn(){
			 $("#edit_popup_win").dialog("close");
		}
		/* 保存按钮 */
		function clickSaveBtn(){
			 $("#edit_popup_win_form").form('submit',{
			 	 url:'<%=request.getContextPath()%>/fun/doFun',
			 	 onSubmit:function(){
			 		var isValid = $(this).form('validate');//验证表单中的一些控件的值是否填写正确，比如某些文本框中的内容必须是数字 
			          if (!isValid) { 
			          } 
			          return isValid; // 如果验证不通过，返回false终止表单提交 
			 	},
			 	success:function(){
			 		/* if(data){ */
			 			$("#edit_popup_win").dialog("close");
				 		$('#fun_table').treegrid('reload');  
			 			$.messager.alert("系统提示", "添加功能信息成功!", "info");
			 			
			 	/* 	}else{
			 			
			 		} */
			 	
			    }  ,
			    error:function(){
			    	$.messager.alert("系统提示", "添加功能信息失败,请联系管理员!", "info");
			    }
		 });
		}
		/*  编辑按钮 */
		function clickUpdBtn(){
			// 获取当前选中数据
			var rows = $("#fun_table").datagrid("getSelections");
			if(rows == null || rows.length == 0){
				$.messager.alert("系统提示", "请选择要编辑的数据!", "info");
				return ;
			}
			$("#edit_popup_win_form").form("clear");		
			//加载功能类型
		    $("#funTypeid").combobox("clear");
			loadFunType(rows[0].funType);
			// 设置form值
			  $("#edit_popup_win_form").form("load", rows[0]);
			//父节点
			 $("#paterIdId").combobox("clear");
			loadPaterFuns(rows[0].fName,rows[0].paterId);
			// 弹出窗
			$("#edit_popup_win").dialog("open").window("setTitle", "编辑功能");
		}
		//加载父节点
		function loadPaterFuns(fName, paterId){
			 $("#paterIdId").combobox({
				 	url:'<%=request.getContextPath()%>/fun/getPaterFuns',   
					method: "get",
				    valueField: "uuid",
					textField: "fName", 
					editable: false,
					onBeforeLoad: function(data){
						if(paterId != null && paterId != ""){
							$("#paterIdId").combobox("setValue", paterId);
							$("#paterIdId").combobox("setText", fName);
						}
					}
				 	
			 });
		}
		//加载功能类型
		function loadFunType(dValue){
			 $("#funTypeid").combobox({
				   	url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=FUN',    
				    valueField:'dValue',    
				    textField:'dName',
				    editable: false,
				    method: "get",
				    onBeforeLoad: function(data){
						if(dValue != null && dValue != "")
							$("#funTypeid").combobox("setValue", dValue);
					}
			 }); 
		}
		
		
		
		/* 删除按钮 */ 
		function clickDelBtn(){
			// 获取当前选中数据
			var rows = $("#fun_table").datagrid("getSelections");
			if(rows == null || rows.length == 0){
				$.messager.alert("系统提示", "请选择要删除的数据!", "info");
				return ;
			}
			$.messager.confirm("注意", "确定删除此功能，删除系统不会恢复!", function(yn){
				if(yn){
					var uuid = rows[0]["uuid"];
					$.ajax({
						url: "<%=request.getContextPath()%>/fun/delFun?uuid="+ uuid,
						type: "post",
						cache: false,// 解决IE下有缓存的问题
						async: false,
						contentType:"text/html;charset=utf-8",
						error: function(data){
							$.messager.alert("系统提示",data.msgCode+":"+data.msgContent, "error");
							$('#fun_table').treegrid('reload');
						},
						success: function(data){
							$.messager.alert("系统提示", data.msgCode+":"+data.msgContent, "info");
							$('#fun_table').treegrid('reload');
						}
					});
				}
			});
		}
		
		/* 查询 */
		function queryData(flag){
			var value = $("#searchBtnId").textbox("getValue");
			console.info(value);
			var params = $("#fun_table").treegrid("options").queryParams;
			params.value = value;
			// 重新复制查询参数
			$("#fun_table").treegrid("options").queryParams = params;
			// 重新load
			$("#fun_table").treegrid({
				onLoadSuccess: function(data){
					if(data == null || data.total == 0){
						if(flag == 1)
							$.messager.alert("系统提示", "查询数据成功！", "info");
					}
				},
				onLoadError: function(){
						$.messager.alert("系统提示", "查询数据失败！", "info");
				}
			});
		}
</script>
</html>