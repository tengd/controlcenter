<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统初使数据字典</title>
<c:import url="../tags.jsp"></c:import>
</head>
<body>
		<div id="edit_popup_win" class="easyui-dialog" data-options="modal:true,closed:true"
		style="height: 200px;width: 350px;padding-top: 5px;" buttons="#edit_popup_win_buttons">
				<form id="edit_popup_win_form" style="padding: 0;margin: 0;" method="post">
						<table>
							<tr>
								<td style="width: 80px;text-align: right;"><label>字典类型代码:</label></td>
								<td>
										<input id="typecodeId" name="typecode"  class="easyui-validatebox" style="width:200px;"  data-options="required:true" >  
								</td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>名称:</label></td>
								<td><input id="nameId" name="dName" style="width:200px;"  class="easyui-validatebox"  data-options="required:true" ></td>
							</tr>
							<tr>
								<td style="width: 80px;text-align: right;"><label>值:</label></td>
								<td><input id="valueId" name="dValue" style="width: 200px" class="easyui-validatebox" ></td>
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
		
		
		
		
		
		<table id="dicdate_table"  class="easyui-datagrid" data-options="iconCls: 'icon-ok',rownumbers: true,animate: true,collapsible: true,fitColumns: true" cellspacing="0" cellpadding="0"  style="width:50%;height:250px" ></table>
		<div id="dicdate_table_toolbar">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="clickAddBtn()">添加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="clickUpdBtn()">编辑</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="clickDelBtn()">删除</a>
			<input id="searchBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:200px"  prompt="请输入字典类型代码"  buttonAlign="right" buttonText="查询" >
		</div>
	    
</body>
<script type="text/javascript">
		$(function(){
			$("#edit_popup_win").dialog("close");
			$('#dicdate_table').datagrid({    
				title: "系统初使数据字典管理",
				toolbar:"#dicdate_table_toolbar",
				singleSelect: true,
				pagination: true,
				pageList: [15,30,45,60,75],
				fit: true,
				fixed:true,
				fitColumns: true,
				rownumbers: true,
				url: '<%=request.getContextPath()%>/dicdate/getSDicDatesByTypeCodeOrName',
				columns:[[
							{ field:'uuid',hidden:true },
							{ field: 'typecode', title: '字典类型代码' },
							{ field: 'dName', title: '名称' },
							{ field: 'dValue', title: '值', }
				]]
			}); 
			$('#searchBtnId').textbox({    
				onClickButton:function clickQueryBtn(){
					queryData(1);
				}
			});
		});
	  /* 关闭按钮 */
	  function clickCancelBtn(){
		  $("#edit_popup_win").dialog("close");
	  }
	 /* 保存按钮 */ 
	 function clickSaveBtn(){
		 $("#edit_popup_win_form").form('submit',{
			 	 url:'<%=request.getContextPath()%>/dicdate/doDicDate',
			 	 onSubmit:function(){
			 		var isValid = $(this).form('validate');//验证表单中的一些控件的值是否填写正确，比如某些文本框中的内容必须是数字 
			          if (!isValid) { 
			          } 
			          return isValid; // 如果验证不通过，返回false终止表单提交 
			 	},
			 	success:function(){
			 		/* if(data){ */
			 			$("#edit_popup_win").dialog("close");
				 		$('#dicdate_table').datagrid('reload');    
			 			$.messager.alert("系统提示", "新增字典信息成功!", "info");
			 			
			 /* 		}else{
			 			$.messager.alert("系统提示", "新增字典信息失败,请联系管理员!", "info");
			 		} */
			 	
			    } ,
			    error:function(){
			    	$.messager.alert("系统提示", "新增字典信息失败,请联系管理员!", "info");
			    }
		 });
	 }
	/* 添加工具条*/
	function clickAddBtn(){
		   $("#edit_popup_win").dialog("open").window("setTitle", "添加初使化数据");
		   $("#edit_popup_win_form").form("clear");
	 }
	/*  编辑按钮 */
	function clickUpdBtn(){
		// 获取当前选中数据
		var rows = $("#dicdate_table").datagrid("getSelections");
		if(rows == null || rows.length == 0){
			$.messager.alert("系统提示", "请选择要编辑的数据!", "info");
			return ;
		}
		$("#edit_popup_win_form").form("clear");
		// 设置form值
		$("#edit_popup_win_form").form("load", rows[0]);
		// 弹出窗
		$("#edit_popup_win").dialog("open").window("setTitle", "编辑字典值");
	}
	/* 删除按钮 */ 
	function clickDelBtn(){
		// 获取当前选中数据
		var rows = $("#dicdate_table").datagrid("getSelections");
		if(rows == null || rows.length == 0){
			$.messager.alert("系统提示", "请选择要删除的数据!", "info");
			return ;
		}
		$.messager.confirm("注意", "此用户如果删除后恢复需要一个慢长的过程！", function(yn){
			if(yn){
				var uuid = rows[0]["uuid"];
				$.ajax({
					url: "<%=request.getContextPath()%>/dicdate/delDicDate?uuid="+ uuid,
					type: "post",
					cache: false,// 解决IE下有缓存的问题
					async: false,
					contentType:"text/html;charset=utf-8",
					error: function(data){
						$.messager.alert("系统提示",data.msgCode+":"+data.msgContent, "error");
						$('#dicdate_table').datagrid('reload');
					},
					success: function(data){
						$.messager.alert("系统提示", data.msgCode+":"+data.msgContent, "info");
						$('#dicdate_table').datagrid('reload');
					}
				});
			}
		});
	}
	/* 查询 */
	function queryData(flag){
		var queryValue = $("#searchBtnId").textbox("getValue");
		console.info(queryValue);
		var params = $("#dicdate_table").datagrid("options").queryParams;
		params.queryValue = queryValue;
		// 重新复制查询参数
		$("#dicdate_table").datagrid("options").queryParams = params;
		// 重新load
		$("#dicdate_table").datagrid({
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
		
</script>
</html>