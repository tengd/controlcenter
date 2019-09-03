<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../tags.jsp"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>源端配置</title>
</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'采集端配置'">

		<div class="easyui-tabs" id="dataSourceTabs"
			style="width: 100%; height: 100%; margin: 0px 0px 0px 0px;">
		</div>

	</div>
</body>
<script>
var tableId = "";

$(function() {
	closeAllDialogs();	
	$("#dataSource_uuid").val("");
	handleDataSource();
	handlePermissions();
	//loadDataSourceConfigInfo("webservice");
	
	
	// 点击相应Tab才加载相应的数据，而不是页面加载时就将所有Tab的数据加载出来
	$('#dataSourceTabs').tabs({
		onSelect : function(title) {
			closeAllDialogs();
			clearInfoForm();
			switch (title) {
			case 'webservice':
				loadDataSourceConfigInfo("webservice");
				break;
			case 'ftp':
				loadDataSourceConfigInfo("ftp");
				break;
			}
		}
	});
});

function loadDataSourceConfigInfo(title){
	var getDataSources ="<%=request.getContextPath()%>/dataSourceConfig/getDataSourceConfigs?type="+title;
	tableId = title+"_table";
	console.info("tableId:"+tableId);
	$('#'+tableId).datagrid({
		toolbar : "#"+title+"_table_toolbar",
		pagination : true,
		singleSelect:true,
		pageList : [ 10, 20, 30, 40, 50 ],
		fit : true,
		checkOnSelect : true,
		selectOnCheck : true,
		fitColumns : true,
		rownumbers : true,
		url : getDataSources,
		 onCheck:function(index,row){ 
			fillInfoForm(row , title);
		 },
		columns : [ [ 
		        {field : 'uuid',hidden : true},
		        {field : 'ds_username',hidden : true},
		        {field : 'ds_password',hidden : true},
		        {field : 'port',hidden : true},
		        {field : 'ck',checkbox : true}, 
		        {field : 'ds_name',title : '公司名'}, 
		        {field : 'type',title : '数据源类型'}, 
		        {field : 'url',title : 'url'}, 
		        {field : 'creator',title : '创建者'},
		        { field: 'createdate', title: '创建时间',align:'left',formatter: function(value,row,index){
						return new Date(row.createdate).Format('yyyy-MM-dd hh:mm:ss')
					}
		        }, 
		        {field : 'updator',title : '修改者'}, 
		        { field: 'updatedate', title: '修改时间',align:'left',formatter: function(value,row,index){
					if(row.updatedate == null || row.updatedate == undefined || row.updatedate == ''){
						return '';
					}else{
						return new Date(row.updatedate).Format('yyyy-MM-dd hh:mm:ss')
					}}
		        },{ field: 'quartz_corn', title: '触发时间',align:'left',formatter: function(value,row,index){
					if(row.quartz_corn == null || row.quartz_corn == undefined || row.quartz_corn == ''){
						return '未设置';
					}else{
						return row.quartz_corn;
					}}
		        },{ field: 'Clazz', title: 'Clazz',align:'left',formatter: function(value,row,index){
					if(row.Clazz == null || row.Clazz == undefined || row.Clazz == ''){
						return '未设置';
					}else{
						return row.Clazz;
					}}
		        }] ]
	});
}

function clickAddBtn(formName , type){
	var url = "<%=request.getContextPath()%>/dataSourceConfig/addDataSourceConfig";
	var boxValue = $("#dataSourceConfig_dsName_"+type).combobox('getData');
	var dsValue = "";
	for(var i = 0 ; i < boxValue.length;i++){
		if($("#dataSourceConfig_dsName_"+type).combobox('getValue') == boxValue[i].dName){
			dsValue = boxValue[i].dValue;
		}
	}
	if(dsValue == '' || dsValue == null){
		dsValue = $("#dataSourceConfig_dsName_"+type).combobox('getValue');
	}
	$("#"+formName).form({
		url:url,
		onSubmit:function(param){
			param.uuid = $("#dataSource_uuid").val();
			param.type = type;
			param.dsValue = dsValue;
		},
		success : function(data) {
			$('#'+tableId).datagrid("reload", {});
			clearInfoForm();
			//$.messager.alert("系统提示", '数据插入成功', "info");
		},
		error:function(data){
			clearInfoForm();
			$.messager.alert("系统提示", data, "info");
		}
	});
	$("#"+formName).submit();
}

function clickDelBtn(){
	var selRows = $('#'+tableId).datagrid('getChecked');
	if(selRows.length == 0 ){
		$.messager.alert("系统提示", "请选择待删除数据！", "info");
		return;
	}
	$.messager.confirm('删除提示', '删除数据源也将删除其定时触发配置 ， 确认删除数据源？', function(r){
		if (r){
			url = "<%=request.getContextPath()%>/dataSourceConfig/delDataSourceConfig?uuid="+$("#dataSource_uuid").val();
			$.ajax({
				url :url,
				type : "post",
				cache : false,// 解决IE下有缓存的问题
				async : false,
				success : function(data) {
					$('#'+tableId).datagrid("reload", {});
					clearInfoForm();
				}
			});
		}
	});

}

function fillInfoForm(row , title){
	$("#dataSource_uuid").val(row.uuid);
	if(title == 'webservice'){
		$("#dataSourceConfig_dsName_webservice").combobox('setValue' , row.ds_name);
		$("#dataSourceConfig_url").textbox('setValue' , row.url);
	}
	if(title == 'ftp'){
		$("#ftp_url").textbox('setValue' , row.url);
		$("#ftp_port").textbox('setValue' , row.port);
		$("#ftp_username").textbox('setValue' , row.ds_username);
		$("#ftp_password").textbox('setValue' , row.ds_password);
		$("#dataSourceConfig_dsName_ftp").combobox('setValue' ,row.ds_name);
	}
	
}
function clearInfoForm(){
	$("#dataSource_uuid").val("");
	$("#dataSourceConfig_dsName_webservice").combobox('setValue' ,"");
	$("#dataSourceConfig_url").textbox('setValue' , "");
	$("#ftp_url").textbox('setValue' , "");
	$("#ftp_port").textbox('setValue' ,'');
	$("#ftp_username").textbox('setValue' , "");
	$("#ftp_password").textbox('setValue' , "");
	$("#dataSourceConfig_dsName_ftp").combobox('setValue' ,"");
}

function clickClearBtn(){
	clearInfoForm();
	$('#'+tableId).datagrid('clearSelections');
}
function validateInfoForm(dsName , dataSourceConfig_url ){
	if(dsName == "" || dsName == undefined || dsName == null ){
		$.messager.alert("系统提示", "添加数据时公司名不能为空", "info");
		return false;
	}
	if(dataSourceConfig_url == "" || dataSourceConfig_url == undefined || dataSourceConfig_url == null ){
		$.messager.alert("系统提示", "添加数据时url不能为空", "info");
		return false;
	}
	return true;
}


function clickFetchBtn(title){
	var selRows = $('#'+tableId ).datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "请选择要获取数据的数据源", "info");
		return;
	}else if(selRows[0].quartz_corn != null){
		$.messager.alert("系统提示", "手工获取数据需要将定时任务取消，请取消定时任务后再手工获取数据", "info");
		return;
	}else{
		if(title == 'webservice'){
			$("#startTime").datebox('setValue','');
			$("#timedura_window").dialog('open');
		}else if(title == 'ftp'){
			fetchFtpServerFileInfo(selRows[0].uuid);
		}
	}
}

function fetchFtpServerFileInfo(dataSourceId){	
	url = "<%=request.getContextPath()%>/gather/getFtpServerData";
	$('#'+tableId).datagrid("loading");
	$.ajax({
		url :url,
		type:'GET',
		data :{'dataSourceId':dataSourceId},
		dataType:'json',
		cache : false,// 解决IE下有缓存的问题
		async : true,
		success : function(data) {
			$('#'+tableId).datagrid("loaded");
			$.messager.alert("系统提示", data.msgContent, "info");
		}
	});
}


function fetchDataSourceDatas(){
	var selRows = $('#'+tableId ).datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "请选择要获取数据的数据源", "info");
	}else{
		$("#timedura_window").dialog('close');
		var sourceUrl = selRows[0].url;
		var startTime = $("#startTime").datebox('getValue');
		var nowDate = new Date();
		var beginDate = new Date(startTime);
		var curVal = (Date.parse(nowDate) - Date.parse(beginDate));
		if(curVal < 0){
			$.messager.alert("系统提示", "开始日期不能晚于当前日期", "info");
			return;
		}
		var boxValue = $("#dataSourceConfig_dsName_webservice").combobox('getData');
		var dsValue = "";
		for(var i = 0 ; i < boxValue.length;i++){
			if($("#dataSourceConfig_dsName_webservice").combobox('getValue') == boxValue[i].dName){
				dsValue = boxValue[i].dValue;
			}
		}
		if(dsValue == '' || dsValue == null){
			dsValue = $("#dataSourceConfig_dsName_webservice").combobox('getValue');
		}
		var url = "<%=request.getContextPath()%>/gather/getWebServiceData?url="+sourceUrl+"&startTime="+startTime+"&dataSourceId="+selRows[0].uuid+"&dsValue="+dsValue;
		$('#'+tableId).datagrid("loading");
		$.ajax({
			url:url,
			type:'post',
			async:true,
			cache:false,
			success:function(data){
				$('#'+tableId).datagrid("loaded");
				$.messager.alert("系统提示", data.msgContent, "info");	
				$("#timedura_window").dialog('close');
			},
			error:function(data){
				$('#'+tableId).datagrid("loaded");
				$.messager.alert("系统提示", "数据获取失败！", "info");
			}
		});
	}
}

function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}

function popScheduleWindow(title){
	var selRows = $('#'+tableId).datagrid('getChecked');
	if(selRows.length == 0 ){
		$.messager.alert("系统提示", "请选择需要配置的数据！", "info");
		return;
	}
	var currCj = selRows[0].ds_name;
	var comboboxValues = $("#dataSourceConfig_dsName_"+title).combobox('getData');
	var suffix = "";
	for(var i = 0 ; i < comboboxValues.length ; i++){
		if(comboboxValues[i].dName == currCj){
			suffix = comboboxValues[i].dValue;
			break;
		}		
	}
	$("#dataSourceConfig_clazz").combobox({		
		url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=JobClazz_cjd_'+suffix,
		method:'get',
		valueField:'dValue',
		textField:'dName',
		panelHeight:'auto',
		required:'true'
	});
	$("#schedule_window").dialog('open');
	$("#dataSourceConfig_clazz").combobox('setValue' , '');
	$("#dataSourceConfig_schedule").combobox('setValue' , '');
	//$("#schedule_form").clear();
}

function setSchedule(){
	var cron = $("#dataSourceConfig_schedule").combobox('getValue');	
	var clazz = $("#dataSourceConfig_clazz").combobox("getValue");
	var selRows = $('#'+tableId ).datagrid('getChecked');
	var dataSourceId = selRows[0].uuid;
	if(dataSourceId == null || dataSourceId==''){
		$.messager.alert("系统提示", "请选择需要配置的数据", "info");
		return;
	}
	if(cron == null || cron == '' || clazz == null || clazz == ''){
		$.messager.alert("系统提示", "触发时间和clazz不能为空", "info");
		return;
	}
	var  url = "<%=request.getContextPath()%>/triggerConfig/setDataSourceTriggerConfig?dataSourceId="+dataSourceId+"&triggerclazz="+clazz+"&cron="+cron;
	$('#'+tableId).datagrid("loading");
	$.ajax({
		url:url,
		type:'post',
		async:true,
		cache:false,
		success:function(data){
			$("#schedule_window").dialog('close');
			$('#'+tableId).datagrid("loaded");
			$('#'+tableId).datagrid("reload", {});
			$.messager.alert("系统提示", data.msgContent, "info");
		},
		error:function(data){
			$("#schedule_window").dialog('close');
			$('#'+tableId).datagrid("loaded");
			$('#'+tableId).datagrid("reload", {});
			$.messager.alert("系统提示",  "定时设置失败！", "info")
		}
	});
}

function showBottomRight(data){
	//IE某些版本不支持读取返回的JSON， 因此，若浏览器为IE，不显示弹窗，改用alert显示
	if (!!window.ActiveXObject || "ActiveXObject" in window)
		{
		$.messager.alert("系统提示", "提交成功！", "info");
        return;
		}
      else
        {
    	  var json = eval('(' + data + ')'); 
    		$.messager.show({
    			title:'提示',
    			msg:json.msgContent,
    			showType:'show'
    		});
        }	
}

function cancelSchedule(){
	var selRows = $('#'+tableId ).datagrid('getChecked');
	if(selRows.length == 0){
		$.messager.alert("系统提示", "数据不能为空， 请选择要取消定时的数据", "info");
		return;
	}
	if(selRows.length > 1){
		$.messager.alert("系统提示", "取消定时只能选择单条数据", "info");
		return;
	}
	$('#'+tableId).datagrid("loading");
	var  url = "<%=request.getContextPath()%>/triggerConfig/cancelSchedule?dataSourceId="+selRows[0].uuid;
	$.ajax({
		url:url,
		type:'post',
		async:true,
		cache:false,
		success:function(data){
			$("#schedule_window").dialog('close');
			$('#'+tableId).datagrid("loaded");
			$('#'+tableId).datagrid("reload", {});
			showBottomRight(data);
		},
		error:function(data){
			$("#schedule_window").dialog('close');
			$('#'+tableId).datagrid("loaded");
			$('#'+tableId).datagrid("reload", {});
			showBottomRight(data);
		}
	});
}

function closeAllDialogs(){
	$('#timedura_window').dialog('close');
	$('#schedule_window').dialog('close');
}

function handlePermissions(){
	var pageUrl = 'sourceconfig';
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
		case 'resolve' : $("#button_resolve").linkbutton('enable');
		break;

		}
	}
}

 function handleDataSource(){
	var url = "<%=request.getContextPath()%>/dicdate/getDicDatesStr?typecode=dataSource";
		  $.ajax({
			  url:url,
				type:'post',
				async:true,
				cache:false,
				success:function(data){
					var dataSources = data.split(",");
					dataSources.reverse();
					for(var i = 0 ; i < dataSources.length ; i++){
						if(dataSources[i] == 'webservice'){
							addWebServiceTab();
						}
						if(dataSources[i] == 'ftp'){
							addFtpTab();
						}
						
					}
				}
		  }); 
}

function addWebServiceTab(){
	$('#dataSourceTabs').tabs('add',{
		title:'webservice',
		closable:true,
		selected:true,
		content:"<div title=\"webservice\" style=\"padding: 10px\" id=\"webservice_tab\" ><input type=\"hidden\" id=\"dataSource_uuid\" name=\"uuid\" /><form id=\"webservice_update_form\" method=\"post\"><tr><td>地址:</td><td><input id=\"dataSourceConfig_url\" class=\"easyui-textbox\" type=\"text\" name=\"url\" data-options=\"min:0,max:999\" style=\"width:350px\" prompt=\"http://\" ></td><td>&nbsp;&nbsp;公司:</td><td><input class=\"easyui-combobox\" name=\"dsName\" select=\"\" id=\"dataSourceConfig_dsName_webservice\" editable=\"false\"data-options=\"url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=CJ',method:'get',valueField:'dValue',textField:'dName',panelHeight:'auto'\"></td><td><a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add'\" onClick=\"clickAddBtn('webservice_update_form','webservice')\">提交</a><a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-clear'\" onClick=\"clickClearBtn()\">清空</a></td></tr></form><table id=webservice_table class=\"easyui-datagrid\" cellspacing=\"0\" cellpadding=\"0\" style=\"width: 50%; height: 100%\"></table><div id=\"webservice_table_toolbar\"> <a href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-remove',plain:true\" onclick=\"clickDelBtn()\">删除</a><a href=\"#\" class=\"easyui-linkbutton\"  data-options=\"iconCls:'icon-fetch',plain:true\" onclick=\"clickFetchBtn('webservice')\">获取数据</a><a  class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'icon-time'\" onclick=\"popScheduleWindow('webservice')\">定时配置</a><a class=\"easyui-linkbutton\"iconCls=\"icon-clear\" plain=\"true\" onclick=\"cancelSchedule()\">取消定时任务</a></div><div id=\"timedura_window\"  class=\"easyui-dialog\"  title=\"开始时间设置\" data-options=\"iconCls:'icon-save'\" style=\"width:300px;height:180px;padding:10px\" buttons=\"#edit_popup_win_buttons\"><table cellpadding=\"5\">    <tr>    <td>开始时间</td>    <td><input class=\"easyui-datebox\" id=\"startTime\" data-options=\"formatter:myformatter,parser:myparser\"></input></td>    </tr>    <tr><h3>不选择时间则默认为前一天时间</h3></tr>    </table></div><div id=\"schedule_window\"  class=\"easyui-dialog\"  title=\"定时设置\" data-options=\"iconCls:'icon-save'\" style=\"width:300px;height:180px;padding:10px\" buttons=\"#schedule_popup_win_buttons\"><form id=\"schedule_form\" method=\"post\"> <table cellpadding=\"5\">    <tr>    <td>触发时间</td>    <td><input class=\"easyui-combobox\" name=\"cron\" select=\"\" id=\"dataSourceConfig_schedule\" editable=\"false\"data-options=\"url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=cron',method:'get',valueField:'dValue',textField:'dName',panelHeight:'auto',required:'true'\" /></td>    </tr>    <tr>    <td>触发对象</td>    <td><input class=\"easyui-combobox\" name=\"triggerclazz\" select=\"\" id=\"dataSourceConfig_clazz\" editable=\"false\"data-options=\"method:'get',valueField:'dValue',textField:'dName',panelHeight:'auto',required:'true'\" /></td>    </tr>       </table>    </form></div><div id=\"edit_popup_win_buttons\" style=\"text-align: center;\"><a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-save\" plain=\"true\" onclick=\"fetchDataSourceDatas()\">确定</a><a href=\"javascript:void(0)\" class=\"easyui-linkbutton\"iconCls=\"icon-cancel\" plain=\"true\" onclick=\"$('#timedura_window').dialog('close')\">取消</a></div><div id=\"schedule_popup_win_buttons\" style=\"text-align: center;\"><a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-save\" plain=\"true\" onclick=\"setSchedule()\">确定</a><a href=\"javascript:void(0)\" class=\"easyui-linkbutton\"iconCls=\"icon-cancel\" plain=\"true\" onclick=\"$('#schedule_window').dialog('close')\">取消</a></div></div>"
	});
}

function addFtpTab(){
	$("#dataSourceTabs").tabs('add' , {
		title:'ftp',
		closable:true,
		content:"<div title=\"ftp\" style=\"padding: 10px\" id=\"ftp_tab\"><form id=\"ftp_update_form\" method=\"post\"> <table cellpadding=\"5\"><tr><td>地址:</td><td><input id=\"ftp_url\" class=\"easyui-textbox\" type=\"text\" name=\"url\" data-options=\"min:0,max:999\" style=\"width:250px\" prompt=\"ftp://\"/></td><td>端口:</td><td colspan=\"3\" ><input id=\"ftp_port\" class=\"easyui-textbox\" type=\"text\" name=\"port\" style=\"width:50px\" prompt=\"21\"/></td></tr><tr><td>用户名:</td><td><input id=\"ftp_username\" class=\"easyui-textbox\" type=\"text\" name=\"ds_username\" style=\"width:250px\"/></td><td>密码:</td><td><input id=\"ftp_password\" class=\"easyui-textbox\" type=\"password\" name=\"ds_password\"/></td><td>公司:</td><td><input class=\"easyui-combobox\" name=\"dsName\" select=\"\" id=\"dataSourceConfig_dsName_ftp\" editable=\"false\"data-options=\"url:'<%=request.getContextPath()%>/dicdate/getDicDates?typecode=CJ',method:'get',valueField:'dValue',textField:'dName',panelHeight:'auto'\"></td><td><a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add'\" onClick=\"clickAddBtn('ftp_update_form','ftp')\">提交</a><a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-clear'\" onClick=\"clickClearBtn('')\">清空</a></td></tr></table></form><table id=\"ftp_table\" class=\"easyui-datagrid\" style=\"width: 100%; height: 100%\"></table><div id=\"ftp_table_toolbar\"><a href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-remove',plain:true\" onclick=\"clickDelBtn()\">删除</a><a href=\"#\" class=\"easyui-linkbutton\"  data-options=\"iconCls:'icon-fetch',plain:true\" onclick=\"clickFetchBtn('ftp')\">获取数据</a><a  class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'icon-time'\" onclick=\"popScheduleWindow('ftp')\">定时配置</a><a class=\"easyui-linkbutton\"iconCls=\"icon-clear\" plain=\"true\" onclick=\"cancelSchedule()\">取消定时任务</a></div></div>"
	});
	$("#dataSourceTabs").tabs('select' , 'webservice');
}


</script>

</html>