<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="panel-fit">
<head>
<c:import url="../tags.jsp"></c:import>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>触发解析</title>
</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'触发解析'">
	<div id="triggerTabs" class="easyui-tabs"
		data-options="tools:'#tab-tools'" style="width: 100%; height: 100%">

		<div title="webservice" data-options="closable:true"
			style="padding: 1px">

					<table id="webservice_table" class="easyui-datagrid" cellspacing="0"
						cellpadding="0" style="width: 100%; height: 100%"></table>
					<div id="baseData_table_toolbar">
						<a href="#" id="button_resolve" class="easyui-linkbutton" data-options="iconCls:'icon-man',plain:true,disabled:true" onclick="resolveBaseData('webservice')">解析</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="showXmlText('webservice')">格式化文本</a>
						<a id="button_lock"  href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-lock',disabled:true" onclick="lockData()">锁</a>
						<a href="#"  id="button_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',disabled:true,plain:true" style="width:80px;height:22px" onclick="deleteBaseData()">删除数据</a>
						<input id="searchBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:250px"  prompt="表名、公司名、公司代码查询"  buttonAlign="right" buttonText="查询" >
						<!-- <a  class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-time'" onclick="popScheduleWindow()">定时配置</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="cancelSchedule()">取消定时任务</a>	 -->			

				</div>
	</div>
	
	       <div title="ftp" style="padding: 1px;" data-options="closable:true" style="padding:1px;">
	       <div class="easyui-tabs" id="ftpTabs"  style="width:100%;height:750px">
	       				<div title="已解析文件" style="padding:10px;">
						<table id="ftp_resolved_table" class="easyui-datagrid" cellspacing="0"
								cellpadding="0" style></table>
						<div id="ftpbaseData_resolved_table_toolbar">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="showXmlText('ftp')">解析文本</a>
						</div>
				</div>
				<div title="未解析文件" style="padding:10px;" data-options="fit:true,plain:true" >
				<table id="ftp_table" class="easyui-datagrid" cellspacing="0"
						cellpadding="0" style=""></table>
						<div id="ftpbaseData_table_toolbar">
						<a href="#" id="button_resolve_ftp" class="easyui-linkbutton" data-options="iconCls:'icon-man',plain:true,disabled:true" onclick="resolveBaseData('ftp')">解析</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="showXmlText('ftp')">解析文本</a>
						<a id="button_lock"  href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-lock',disabled:true" onclick="lockData()">锁</a>
						<a href="#" class="easyui-linkbutton" id="button_delete_ftp" data-options="iconCls:'icon-remove',disabled:true,plain:true" style="width:80px;height:22px" onclick="deleteBaseData()">删除数据</a>						
						<input id="searchFtpBtnId" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:250px"  prompt="输入表名查询"  buttonAlign="right" buttonText="查询" >
						<!-- <a  class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-time'" onclick="popScheduleWindow()">定时配置</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="cancelSchedule()">取消定时任务</a>	 -->			
					</div>
				</div>
			</div>					
		</div>
	</div>
	<div id="baseDataXmlTextDialog_IE" class="easyui-dialog" title="xmlText内容" style="width:80%;height:100%;max-width:800px;padding:10px" 
		data-options="iconCls:'icon-save',onResize:function(){$(this).dialog('center');}">
		<xmp id="xmlContent_IE"></xmp>
	</div>
	<div id="baseDataXmlTextDialog" class="easyui-dialog" title="xmlText内容" style="width:80%;height:100%;max-width:800px;padding:10px" 
		data-options="iconCls:'icon-save',onResize:function(){$(this).dialog('center');}">
		<p id="xmlContent"></p>
	</div>
	</div>		
</body>

<script type="text/javascript">
var tableId = "";
var dataSourceType = "<%=request.getParameter("dataSourceType")%>";
$(function() {
	handlePermissions();
	$("#baseDataXmlTextDialog").dialog("close");
	$("#baseDataXmlTextDialog_IE").dialog("close");
	$('#schedule_window').dialog('close');
    //只在从datasource页面点击链接按钮时， 才根据DataSourceId和type具体显示basedata， 其余时候只根据type来显示basedata
    if(dataSourceType == '' || dataSourceType == 'null' || dataSourceType == null || dataSourceType == undefined){
    	loadResolveTriggerInfo('webservice' , '','');    	
    }else if(dataSourceType == 'ftp'){
    	loadFtpBaseDataTable('ftp' , '','');
    	$('#triggerTabs').tabs('select' , 'ftp');
    }else if(dataSourceType == 'webservice'){    	
    	loadResolveTriggerInfo('webservice' , '','');
    	$('#triggerTabs').tabs('select' , 'webservice');
    }
	// 点击相应Tab才加载相应的数据，而不是页面加载时就将所有Tab的数据加载出来
	$('#triggerTabs').tabs({
		onSelect : function(title) {
			//alert("select tab "+title);
			switch (title) {
			case 'webservice':
				loadResolveTriggerInfo(title , '','');
				break;
			case 'ftp':loadFtpBaseDataTable('ftp_resolved' , '','');
				break;
			}
		}
	});
	
	$('#ftpTabs').tabs({
		onSelect : function(title) {
			//alert("select tab "+title);
			switch (title) {
			case '未解析文件':
				loadFtpBaseDataTable("ftp" , '','');
				break;
			case '已解析文件':loadFtpBaseDataTable('ftp_resolved' , '','');
				break;
			}
		}
	});
	
	$('#searchBtnId').textbox({    
		onClickButton:function clickQueryBtn(){
			searchWebserviceTable();
		}
	});
	
	$('#searchFtpBtnId').textbox({   	
		onClickButton:function clickQueryBtn(){
			searchFtpTable();
		}
	});
});


/**
 * 加载webservice Tab的信息
 */
function loadResolveTriggerInfo(title , dataSourceId , tableName) {
	tableId = title+"_table";
	console.info("tableId:"+tableId+"dataSourceId = "+dataSourceId);
	var getBaseData = "<%=request.getContextPath()%>/gather/getBaseData?dataSourceId="+dataSourceId+"&tableName="+tableName;	
	$('#'+tableId).datagrid({
		toolbar : "#baseData_table_toolbar",
		singleSelect : false,
		pagination : true,
		pageList : [ 20, 30, 40, 50 ],
		fit : true,
		checkOnSelect : false,
		selectOnCheck : false,
		fitColumns : true,
		rownumbers : true,
		url : getBaseData,
		columns : [ [ {
			field : 'uuid',
			hidden : true
		}, {
			field : 'datasourceConfigId',
			hidden : true
		},{
			field : 'ck',
			checkbox : true
		}, {
			field : 'companyName',
			title : '公司名'
		}, {
			field : 'companyCode',
			title : '公司编号'
		}, {
			field : 'token',
			title : 'TOKEN'
		}, {
			field : 'tableName',
			title : '表名'
		}, { field: 'pushTime', title: '采推时间',align:'left',formatter: function(value,row,index){
			return new Date(row.pushTime).Format('yyyy-MM-dd hh:mm:ss')
		}}, { field: 'isLocked', title: '是否被锁定',formatter: function(value,row,index){
			if(row.isLocked==0){
				return '&nbsp;<a style="width:40px;height:20px;color:blue;" >锁定</a>';
			}else{
				return '&nbsp;<a style="width:40px;height:20px;color:red;">解锁</a>';
			}
		}
},{ field: 'analysisTime', title: '解析时间',align:'left',formatter: function(value,row,index){
	if(row.analysisTime== '' || row.analysisTime == null || row.analysisTime == 'undefined'){
		return '未解析';
	}else{
		return new Date(row.analysisTime).Format('yyyy-MM-dd hh:mm:ss');
	}}
},{ field: 'isSucc', title: '解析是否成功',align:'left',formatter: function(value,row,index){
	if(row.analysisTime== '' || row.analysisTime == null || row.analysisTime == 'undefined'){
		return '&nbsp;<a>未解析</a>';
	}else if (row.isSucc== '1'){
		return '&nbsp;<a style="width:40px;height:20px;color:blue;">解析成功</a>';
	}else{
		return '&nbsp;<a style="width:40px;height:20px;color:red;">解析失败</a>';
	}}
}] ]
	});
}

function loadFtpBaseDataTable(title , dataSourceId , fileName){
	tableId = title+"_table";
	var resolve_sign = 'false';
	var toolbar = "ftpbaseData_table_toolbar"
	if(title == 'ftp_resolved'){
		resolve_sign = 'true';
		toolbar = "ftpbaseData_resolved_table_toolbar";
	}
	getBaseData = "<%=request.getContextPath()%>/gather/getFtpBaseData?resolve_sign="+resolve_sign+"&fileName="+fileName;
	$('#'+tableId).datagrid({
		toolbar : "#"+toolbar,
		singleSelect : false,
		pagination : true,
		pageList : [ 20, 30, 40, 50 ],
		fit : true,
		checkOnSelect : false,
		selectOnCheck : false,
		fitColumns : true,
		rownumbers : true,
		url : getBaseData,
		columns : [ [ {
			field : 'uuid',
			hidden : true
		},
		{
			field : 'datasourceId',
			hidden : true
		},{
			field : 'ck',
			checkbox : true
		}, {
			field : 'filename',
			title : '文件名'
		}, {
			field : 'storepath',
			title : '路径'
		}, {
			field : 'type',
			title : '文件类型'
		}, {
			field : 'tableName',
			title : '表名'
		}, {
			field : 'datasourceName',
			title : '数据源名称'
		}, { field: 'storedate', title: '采推时间',align:'left',formatter: function(value,row,index){
			return new Date(row.storedate).Format('yyyy-MM-dd hh:mm:ss')
		}}, { field: 'lockSign', title: '是否被锁定',formatter: function(value,row,index){
			if(row.lockSign==false){
				return '&nbsp;<a style="width:40px;height:20px;color:blue;" >锁定</a>';
			}else{
				return '&nbsp;<a style="width:40px;height:20px;color:red;"> 解锁 </a>';
			}
		}
}] ]
	});
}

function resolveBaseData(type) {
	var selRows = $('#'+tableId).datagrid('getChecked');
	var isDataResolved =  checkIsDataLocked(selRows);
	if (isDataResolved) {
		$.messager.alert("系统提示", "选择数据存在锁定，请解锁！", "info");
		return;
	}
	if (selRows.length == 0) {
		$.messager.alert("系统提示", "请选择数据", "info");
	} else {
		$('#'+tableId).datagrid("loading");
		var arr = new Array();
		for (var i = 0; i < selRows.length; i++) {
			arr[i] = selRows[i].uuid;
		}
		var url = "<%=request.getContextPath()%>/gather/resolveBaseData?uuids=";
		if(type == 'ftp'){
			url = "<%=request.getContextPath()%>/gather/resolveFtpFileData?uuids=";
		}
		//解析数据时， 将解析按钮、删除按钮设为禁用， 以免重复解析数据或者其他错误
		 $("#button_resolve , #button_resolve_ftp").linkbutton('disable');
		 $("#button_delete").linkbutton('disable');
		 $("#button_delete_ftp").linkbutton('disable');
		$.ajax({
			url : url + arr,
			type : "post",
			cache : false,// 解决IE下有缓存的问题
			async : true,
			success : function(data) {
				$.messager.alert("系统提示",data.msgContent, "info");
				$("#"+tableId).datagrid("reload", {});
				$("#button_resolve , #button_resolve_ftp").linkbutton('enable');
				 $("#button_delete").linkbutton('enable');
				 $("#button_delete_ftp").linkbutton('enable');
			},error:function(data){
				$("#"+tableId).datagrid("loaded");
				$.messager.alert("系统提示",data.msgContent, "info");
				$("#"+tableId).datagrid("reload", {});
			}
		});
	}
}

function checkIsDataLocked(selRows) {
	var flag = false;
	for (var i = 0; i < selRows.length; i++) {
		if (selRows[i].isLocked == '1') {
			flag = true;
			break;
		}
	}
	return flag;
}

function showXmlText(title) {
	var selRows = $('#'+tableId).datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "请选择数据", "info");
		return;
	}
	if(selRows.length >1){	
		$.messager.alert("系统提示", "格式化xml时只能选择单条数据", "info");
		return;
	}		
	
		var uuid = selRows[0].uuid;
		var url;
		if(title == 'webservice'){
			url ="<%=request.getContextPath()%>/gather/getBaseDataXmlText?uuid=" + uuid;
		}else if(title == 'ftp'){
			url = "<%=request.getContextPath()%>/gather/getFtpFileContent?uuid=" + uuid;
		}
		$('#'+tableId).datagrid("loading");
		$.ajax({
			url : url,
			type : "post",
			cache : false,// 解决IE下有缓存的问题
			async : true,
			success : function(data) {
				$("#"+tableId).datagrid("loaded");
				if (!!window.ActiveXObject || "ActiveXObject" in window){
					$("#baseDataXmlTextDialog_IE").dialog("open");
					//xml格式化组件只能在IE使用,若为IE浏览器，格式化，其他浏览器原样显示
					var xmlText = formatXml(data.xmlText);
					$("#xmlContent_IE").text(xmlText);
				}else{
					$("#baseDataXmlTextDialog").dialog("open");
					$("#xmlContent").text(data.xmlText);
				}
				
			}
		});
	

}

function lockData(){
	var selRows = $('#'+tableId).datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "未选择数据", "info");
	}
	for(var i = 0 ; i < selRows.length ; i++){
		var arr = new Array();
		for (var i = 0; i < selRows.length; i++) {
			arr[i] = selRows[i].uuid;
		}
		var dataSourceId = '';
		if(tableId == 'webservice_table'){			
			dataSourceId = selRows[0].datasourceConfigId;
		}else if(tableId == 'ftp_table'){
			dataSourceId = selRows[0].datasourceId;
		}
		$.ajax({
			url : "<%=request.getContextPath()%>/gather/lockData?uuids="+ arr+"&dataSourceId="+dataSourceId,
			type : "post",
			cache : false,// 解决IE下有缓存的问题
			async : false,
			success : function(data) {
				$("#"+tableId).datagrid("reload", {});
			}
		});
	}
}


function showBottomRight(data){
	//IE某些版本不支持读取返回的JSON， 因此，若浏览器为IE，不显示弹窗，改用alert显示
	if (!!window.ActiveXObject || "ActiveXObject" in window)
		{
		$.messager.alert("系统提示", "提交成功！", "info");
        return;
		}
    	 var json = eval('(' + data + ')'); 
    		$.messager.show({
    			title:'提示',
    			msg:json.msgContent,
    			showType:'show'
    		});
}

function formatXml(str){      
    //去除输入框中xmll两端的空格。   
      str = str.replace(/^\s+|\s+$/g,"");   
      var source = new ActiveXObject("Msxml2.DOMDocument");   
     //装载数据   
      source.async = false;   
      source.loadXML(str);      
      // 装载样式单   
      var stylesheet = new ActiveXObject("Msxml2.DOMDocument");   
      stylesheet.async = false;   
      stylesheet.resolveExternals = false;   
      stylesheet.load("<%=request.getContextPath()%>/template/format.xsl");   
        
      // 创建结果对象   
      var result = new ActiveXObject("Msxml2.DOMDocument");   
      result.async = false;   
        
      // 把解析结果放到结果对象中方法1   
      source.transformNodeToObject(stylesheet, result);   
      if(result.xml==''||result.xml==null){  
           alert('xml报文格式错误，请检查');  
           return false;  
          }  
      var finalStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" +result.xml;  
      return finalStr; 
}  

function handlePermissions(){
	var pageUrl = 'resolvetrigger';
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
				if(!isHasAllPermissions){
					//如果没有含有*， 即不拥有所有权限，则根据不同权限显示按钮
					showButtonsByPermission(permissions);
				}else{
					$('.easyui-linkbutton').linkbutton('enable');
				}
			}
	  });
}
function showButtonsByPermission(permissions){
	for(var i = 0 ; i < permissions.length ; i++){
		var per = permissions[i];
		switch(per){
			case 'lock' : $("#button_lock").linkbutton('enable');
			break;
			case 'resolve' : $("#button_resolve , #button_resolve_ftp").linkbutton('enable');
			break;
			case 'delete' :$("#button_delete").linkbutton('enable');
			break;
		}
	}
}

function searchWebserviceTable(){
	loadResolveTriggerInfo('webservice' , '' , $("#searchBtnId").textbox('getValue'));
}

function searchFtpTable(){
	loadFtpBaseDataTable('ftp','',$("#searchFtpBtnId").textbox('getValue'));
}

function deleteBaseData(){
	var selRows = $('#'+tableId).datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "未选择数据", "info");
	}
	$.messager.confirm('删除提示', '确认删除原始数据？', function(r){
		if(r){
			for(var i = 0 ; i < selRows.length ; i++){
				var arr = new Array();
				for (var i = 0; i < selRows.length; i++) {
					arr[i] = selRows[i].uuid;
				}
				if(tableId == 'webservice_table'){			
					dataType = 'webservice'
				}else if(tableId == 'ftp_table'){
					dataType = 'ftp'
				}
				$.ajax({
					url : "<%=request.getContextPath()%>/gather/deleteData?uuids="+ arr+"&dataType="+dataType,
					type : "post",
					cache : false,// 解决IE下有缓存的问题
					async : false,
					success : function(data) {
						$("#"+tableId).datagrid("reload", {});
					}
				});
			}
		}
	});

}
</script>
</html>