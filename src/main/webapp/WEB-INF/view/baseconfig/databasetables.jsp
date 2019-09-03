<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../tags.jsp"></c:import>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>库表展示</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,title:'表名'" style="width:55%;padding:10px;">
			<table id="databasetables_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:100%;height:250px" ></table>
			<div id="databasetables_toolbar">
			<!-- 	<input id="searchUserBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入表名"  buttonAlign="right" buttonText="查询" > -->
				<a href="#" id="button_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true" onclick="deleteTable()">删除表</a>
				<a href="#" id="button_update" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" onclick="popUpdateTableDialog()">添加备注</a>
				
			</div>
	</div>	
	<!-- 表描述信息 -->
	<div data-options="region:'center',split:true,title:'表描述信息'" style="width:45%;padding:10px;">
		<div id="tabledescription_toolbar">
			<!-- 	<input id="searchUserBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入表名"  buttonAlign="right" buttonText="查询" > -->
							<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-reload',plain:true"
							onclick="refreshTableDescription()">刷新</a>	
							<a href="#" id="button_update" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" onclick="popUpdateDialog()">修改</a>
			</div>
		<table id="single_table_info_id" class="easyui-datagrid" style="width:100%;height:490px" cellspacing="0" cellpadding="0" ></table> 	
		
			<div id="tabledescription_window"  class="easyui-dialog"  title="Basic Dialog" data-options="iconCls:'icon-save'" style="width:300px;height:175px;padding:10px" buttons="#edit_popup_win_buttons">
		<table cellpadding="5">
	    		<tr>
	    			<td>字段名称:</td>
	    			<td><input class="easyui-textbox" type="text" id="updateField" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>字段描述:</td>
	    			<td><input class="easyui-textbox" type="text" id="updateComment" data-options=""></input> <input  type="text" id="updateType" style="display:none"></input></td>
	    		</tr>
	    		</table>
	</div>
	
	<div id="tableCommentUpdata_window"  class="easyui-dialog"  title="Basic Dialog" data-options="iconCls:'icon-save'" style="width:300px;height:175px;padding:10px" buttons="#edit_popup_win_buttons_table">
		<table cellpadding="5">
	    		<tr>
	    			<td>表名称:</td>
	    			<td><input class="easyui-textbox" type="text" id="window_tableName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>表描述:</td>
	    			<td><input class="easyui-textbox" type="text" id="window_tableComment" data-options=""></input></td>
	    		</tr>
	    		</table>
	</div>
		<div id="edit_popup_win_buttons" style="text-align: center;">
			<a href="javascript:void(0)" id="button_submit" class="easyui-linkbutton" iconCls="icon-save" disabled="true" plain="true" onclick="updateField()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" plain="true" onclick="clickCancelBtn()">取消</a>
		</div>
		
		<div id="edit_popup_win_buttons_table" style="text-align: center;">
			<a href="javascript:void(0)" id="button_submit" class="easyui-linkbutton" iconCls="icon-save" disabled="true" plain="true" onclick="updateTable()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" plain="true" onclick="clearTableWindow()">取消</a>
		</div>
	</div>
</body>
<script>
var currTableName = "";
$(function(){
	currTableName = "";
	handlePermissions();
	$("#tabledescription_window").dialog('close');
	$("#tableCommentUpdata_window").dialog('close');
	loadTables();
});

function loadTables(){
	var getTableNames= "<%=request.getContextPath()%>/dataBaseTables/getDataBaseTablesInfo";
	$("#databasetables_table").datagrid({
		toolbar:"#databasetables_toolbar",
		singleSelect: true,
		pagination: true,
		pageList: [10,20,30,40,50],
		fit: true,
		onClickRow:function(index,row){
			loadSingleTableInfo(row.tableName);
			currTableName = row.tableName;
		},
		onSelect:function(index,row){
			loadSingleTableInfo(row.tableName);
			currTableName = row.tableName;
		},
		   onUnselect:function(){
			   currTableName = "";
			   clearTableDescription();
		   },
		fitColumns: true,
		checkOnSelect : true,
		selectOnCheck : true,
		rownumbers: true,
		url:getTableNames,
		ctrlSelect:true,
		columns:[[
{
	field : 'ck',
	checkbox : true
}, 
					{ field:'tableName',title:'表名' },
					{ field: 'tableComment', title: '表备注' }
		]]
	});
}


function loadSingleTableInfo(tableName){
	var url = "<%=request.getContextPath()%>/dataBaseTables/getSingleTableInfo?tableName="+tableName;
	$("#single_table_info_id").datagrid({
		toolbar:"#tabledescription_toolbar",
		singleSelect:true,
		pageList: [10,20,30,40,50],
		fit: true,
		fitColumns: true,
		rownumbers: true,
		checkOnSelect : true,
		selectOnCheck : true,
		url:url,
		ctrlSelect:true,
		columns:[[
{
	field : 'ck',
	checkbox : true
},
				{ field:'field',title:'列名' },
				{ field: 'type', title: '类型' },
				{ field: 'isNULL', title: '是否为空' },
				{ field: 'key', title: '键' },
				{ field: 'defaultValue', title: '默认值' },
				{ field: 'comment', title: '描述' },
		]]
	});

}

function deleteTable(){
	var selRows = $('#databasetables_table').datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "请选择要删除的表", "info");
		return ;
	}
	$.messager.confirm('删除提示', '删除表将连带删除数据， 不可恢复， 确认删除？', function(r){
		if (r){
			var url = "<%=request.getContextPath()%>/dataBaseTables/deleteTable?tableName="+selRows[0].tableName;
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		async:false,
	    		cache:false,
	    		success:function(data){
	    			$('#databasetables_table').datagrid("reload", {});
	    			clearTableDescription();
	    		},
	    		error:function(){
	    			$.messager.alert("系统提示", "删除表出错", "info");
	    		}
	    	});
		}
	});
	 } 

function clearTableDescription(){
	$("#single_table_info_id").datagrid('loadData',{total:0,rows:[]});
}

function initUpdateWindow(row){
	var win_title = row.field+'字段描述修改';
	$("#tabledescription_window").window({title:win_title});
	$("#updateField").textbox('setValue' , row.field);
	$("#updateComment").textbox('setValue' , row.comment);
	$("#updateType").val(row.type);
}

function clearTableWindow(){
	$("#tableCommentUpdata_window").dialog('close');
	$("#window_tableName").textbox('setValue' , '');
	$("#window_tableComment").textbox('setValue' ,'');
}

function initUpdateTableWindow(row){
	var titleName = row.tableName+"表";
	$("#tableCommentUpdata_window").window({title:titleName});
	$("#window_tableName").textbox('setValue' , row.tableName);
	$("#window_tableComment").textbox('setValue' , row.tableComment);
}


function updateField(){
	var  comment = encodeURI(encodeURI($("#updateComment").val()));
			var url = '<%=request.getContextPath()%>/dataBaseTables/updateComment?tableName='+currTableName+'&comment='+comment+'&field='+$("#updateField").val()+'&type='+$("#updateType").val();
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		async:false,
	    		cache:false,
	    		success:function(data){
	    			$('#single_table_info_id').datagrid("reload", {});
	    			clearWindow();
	    		},
	    		error:function(){
	    			$.messager.alert("系统提示", "删除表出错", "info");
	    		}
	    	});
}

function updateTable(){
	var  comment = encodeURI(encodeURI($("#window_tableComment").val()));
	var url = '<%=request.getContextPath()%>/dataBaseTables/updateTableComment?tableName='+currTableName+'&comment='+comment;
	$.ajax({
		url:url,
		type:'post',
		async:false,
		cache:false,
		success:function(data){
			$('#databasetables_table').datagrid("reload", {});
			clearTableWindow();
		},
		error:function(){
			$.messager.alert("系统提示", "修改表信息出错", "info");
		}
	});
}

function clearWindow(){
	$("#tabledescription_window").dialog('close');
	$("#updateField").textbox('setValue' , '');
	$("#updateComment").textbox('setValue' ,'');
	$("#updateType").val('');
}

function popUpdateDialog(){
	var selRows = $('#single_table_info_id').datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "请选择数据", "info");
		return;
	}else{
		$("#tabledescription_window").dialog('open');
		initUpdateWindow(selRows[0]);
	}
}

function popUpdateTableDialog(){
	var selRows = $('#databasetables_table').datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "请选择数据", "info");
		return;
	}else{
		$("#tableCommentUpdata_window").dialog('open');
		initUpdateTableWindow(selRows[0]);
	}
}

function refreshTableDescription(){
	loadSingleTableInfo(currTableName);
}

function clickCancelBtn(){
	clearWindow();
}
function handlePermissions(){
	var pageUrl = 'databasetables';
	var url="<%=request.getContextPath()%>/per/getPermissionsByPageName?pageurl="+pageUrl;
	  $.ajax({
		  url:url,
			type:'post',
			async:true,
			cache:false,
			success:function(data){
				var permissions = data.split(",");
				var isHasAllPermissions = false;
				for(var i =0 ; i < permissions.length ; i++){
					if(permissions[i] == '*'){
						isHasAllPermissions = true;
						break;
					}
				}
				if(isHasAllPermissions){				
					$('.easyui-linkbutton').linkbutton('enable');
				}else{
					//如果没有含有*， 即不拥有所有权限，则根据不同权限显示按钮
					showButtonsByPermission(permissions);
				}
			}
	  });
}
function showButtonsByPermission(permissions){
	for(var i = 0 ; i < permissions.length ; i++){
		var per = permissions[i];
		switch(per){
		case 'delete' : $("#button_delete").linkbutton('enable');
		break;
		case 'update' : $("#button_update").linkbutton('enable');
		break;
		case 'submit' : $("#button_submit").linkbutton('enable');
		break;
		}
	}
}

</script>
</html>