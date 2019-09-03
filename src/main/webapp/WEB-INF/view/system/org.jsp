<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>组织管理</title>
<c:import url="../tags.jsp"></c:import>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:31px;background:#e9f6fe;">
		<div style="margin-left:40%;">
			<a id="award_btn_id" href="#" style="height:29px;width:100px;" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="clickAwardBtn()">授予</a>  
			<a id="move_btn_id" href="#" style="height:29px;width:100px;"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="clickMoveBtn()">移回</a>
		</div>  
	</div>
	
	
	<!-- 用户 -->
	<div data-options="region:'west',split:true,title:'用户'" style="width:250px;padding:10px;">
			<table id="user_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:100%;height:250px" ></table>
			<div id="user_table_toolbar">
				<input id="searchUserBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:150px"  prompt="请输入用户名"  buttonAlign="right" buttonText="查询" >
			</div>
	</div>
	
	
	<!-- 组织 -->
	<div data-options="region:'center',split:true,title:'组织'" style="width:250px;padding:10px;">
		
		<table id="org_table_id" class="easyui-treegrid" style="width:100%;height:490px" cellspacing="0" cellpadding="0" ></table> 
		<div id="org_table_toolbar">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="clickAddBtn()">添加</a>
				<!-- <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="clickUpdBtn()">编辑</a> -->
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="clickDelBtn()">删除</a>
				<!-- <input id="searchBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:200px"  prompt="请输入组织机构名称"  buttonAlign="right" buttonText="查询" > -->
		</div>
		<div id="edit_popup_win" class="easyui-dialog" data-options="modal:true,closed:true"
			style="height:200px;width:400px;padding-top: 5px;" buttons="#edit_popup_win_buttons">
				<form id="edit_popup_win_form" style="padding: 0;margin: 0;" method="post">
						<table>
							<tr>
								<td style="width: 60px;text-align: right;"><label>父组织:</label></td>
								<td>
									<select id="org_select_id"   name="org_uuid"  class="easyui-combotree" style="width:200px;"   data-options="required:true,editable:true"></select>  
								</td>
							</tr>
							<tr>
								<td style="width: 60px;text-align: right;"><label>组织编码:</label></td>
								<td><input id="orgCode_id" name="orgCode" style="width:200px;"  class="easyui-validatebox"  data-options="required:true,disabled:false" ></td>
							</tr>
							<tr>
								<td style="width: 60px;text-align: right;"><label>组织名称:</label></td>
								<td><input id="orgName_id" name="orgName" style="width:200px;"  class="easyui-validatebox"  data-options="required:true" ></td>
							</tr>
							<tr>
								<td style="width: 10px;text-align: right;"><label>排序号:</label></td>
								<td><input id="sortIndex_id" name="sortIndex" style="width:200px;"  class="easyui-validatebox"  data-options="required:true" ></td>
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
		
	</div>
		
		
		<!-- 用户授予组织关系 -->
	   <div data-options="region:'east',split:true,title:'用户授予组织'" style="width:350px;padding:10px;background:#A9FACD;">
			<table id="user_org_table"  class="easyui-datagrid" cellspacing="0" cellpadding="0"  style="width:100%;height:250px" ></table>
	   </div>
</body>

<script type="text/javascript">
/* 组织 combotree*/
var orgTreeNode="<%=request.getContextPath()%>/org/getOrgTreeNode";
/* 组织 */
var orgurl="<%=request.getContextPath()%>/org/getTreeOrgs";
/* 获取用户 */
var getUsers= "<%=request.getContextPath()%>/user/getUsersByNotLocked";

/* 操作组织 */
var saveURL="<%=request.getContextPath()%>/org/doOrg"; 

$(function(){
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
		
		$('#org_table_id').treegrid({   
			title: "组织机构管理",
			toolbar:"#org_table_toolbar",
		    url:orgurl,    
		    idField:'uuid',    
		    treeField:'orgName', 
		   columns:[[    
				{field:'uuid',hidden:true },
		        {field:'orgCode',title:'组织码',width:100,align:'right'},    
		        {field:'orgName',title:'组织名称',width:200},
		        {field:'sortIndex',title:'排序号',width:200},
		        { field:'parentCode',hidden:true },  
		        { field:'parentId',hidden:true },  
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
		
});

//添加组织
function clickAddBtn(){
	$("#edit_popup_win").dialog("open").window("setTitle", "添加组织");
	$("#edit_popup_win_form").form("clear");
	
	//获得组织信息
	$("#org_select_id").combotree({
		url:orgTreeNode,
		required: true
	});
		
}


	
//删除组织
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
				url: "<%=request.getContextPath()%>/org/delOrg？uuid="+ uuid,
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



//保存
function clickSaveBtn(){
	//获得选中的组织ID
	var selectNode=selectNode=$('#org_select_id').combotree('tree').tree('getSelected');
	var orgCode=$('#orgCode_id').val();
	var orgName=$('#orgName_id').val();
	var sortIndex=$('#sortIndex_id').val();
	var id="";
	var parentCode="";
	alert($('#org_select_id').combo('getText'));
	console.info(selectNode);
	if((selectNode!=undefined)&&($('#org_select_id').combo('getText')!=null&&$('#org_select_id').combo('getText')!="")){
		id=selectNode.id;
		parentCode=selectNode.parentCode;
	}
	
	alert("id:"+id+"parentCode:"+parentCode+"orgCode:"+orgCode+"orgName:"+orgName+"sortIndex:"+sortIndex);	
	var fdata={
			id:id,
			orgCode:orgCode,
			orgName:orgName,
			parentCode:parentCode,
			sortIndex:sortIndex
	}
	
	console.info(fdata);
	var jsonText=JSON.stringify(fdata);
	$.ajax({
		url:saveURL,
		type:'post',
		cache:false,	//解决IE下缓存的问题
		async:false,
		data:jsonText,
		contentType:"application/json;charset=utf-8",
		error:function(data){
			$('#org_table_id').treegrid('reload');
			$.messager.alert("系统提示","保存失败!","error");
		},
		success:function(data){
			$('#org_table_id').treegrid('reload');
			$.messager.alert("系统提示","保存成功!","info");
		}
		
	});
	
}


//关闭
function clickCancelBtn(){
	 $("#edit_popup_win").dialog("close");
}

</script>



</html>

