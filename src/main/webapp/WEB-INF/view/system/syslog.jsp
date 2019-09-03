<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>日志管理</title>
<c:import url="../tags.jsp"></c:import>
</head>
<body>
	<table id="syslog_table_id" class="easyui-datagrid" style="width:100%;height:100%"></table> 
	<div id="syslog_table_toolbar">
			开始时间:<input id="startDateId" class="easyui-datetimebox" name="startDate" data-options="showSeconds:false" style="width: 150px">
			结束时间:<input id="endDateId" class="easyui-datetimebox" name="endDate" data-options="showSeconds:false" style="width: 150px">
			<a id="searchBtnId" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="clickDelBtn()">删除</a>
			<a id="refreshBtnId" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-mini-refresh'">刷新</a>
	</div>

		
</body>

<script type="text/javascript">
//获得日志
var logUrl="<%=request.getContextPath()%>/sysLog/getSysLogs";
//清除日志
var logUrlDel="<%=request.getContextPath()%>/sysLog/delSysLogs";

$(function(){
	$('#syslog_table_id').datagrid({   
		title: "系统日志管理",
		toolbar:"#syslog_table_toolbar",
		singleSelect: false,
		pagination: true,
		pageList: [17,34,68,136,272],
		fit: true,
		fitColumns: true,
		rownumbers: true,
		url: logUrl,
		ctrlSelect:true,
		columns:[[
					{ field:'id',checkbox:true },
					{ field:'uuid',hidden:true },
					{ field: 'uName',title: '用户', },
					{ field: 'funName',title:'功能名称'},
					{ field: 'visitTime', title: '访问时间',align:'left',formatter: function(value,row,index){
						return new Date(row.visitTime).Format('yyyy-MM-dd hh:mm:ss')
					}},
					{ field: 'visitOs', title: '操作系统' },
					{ field: 'logDescribe', title: '日志描述' }
		]]
	}); 
	//init();
});
	/* 时间初使化
	function init() {
			// -1day
			var sd = (new Date()).getTime() - (1 * 24 * 3600);
			$('#startDateId').datetimebox('setValue', new Date(sd).Format('yyyy-MM-dd HH:mm'));
			$('#endDateId').datetimebox('setValue', new Date().Format('yyyy-MM-dd HH:mm'));
	} */
	//查询
	$('#searchBtnId').bind('click',function(){
		query();
	});
	function query(){
		var startDate=$('#startDateId').datetimebox('getValue');
		var endDate=$('#endDateId').datetimebox('getValue');
		var url="<%=request.getContextPath()%>/sysLog/getSysLogs?startDate="+startDate+"&endDate="+endDate;
		$('#syslog_table_id').datagrid({url: url}).datagrid('load');
	}
	/* 刷新 */
	$('#refreshBtnId').bind('click', function(){
		$('#syslog_table_id').datagrid('reload');
    });
	
	//清除日志
	function clickDelBtn(){
		// 获取当前选中数据
		var select_syslog_table_values = $("#syslog_table_id").datagrid("getChecked");
		var sysLogIds=[];
		$.each(select_syslog_table_values, function(index, item){
			sysLogIds.push(item.uuid);
    	});
		var param={
			uuids:sysLogIds.join()
		}
		var jsonText=JSON.stringify(param);
		console.info(jsonText);
		$.ajax({
    		type:"post",
    		url: logUrlDel,
    		data: jsonText,
			dataType: "JSON",
    		cache: false,// 解决IE下有缓存的问题s
			async: false,
    		contentType:"application/json; charset=utf-8",
    		success: function(data){
    			$("#syslog_table_id").datagrid('reload');
    			$.messager.alert("提示", data.msgCode+data.msgContent);
    		},
    		error: function(data){
    			$("#syslog_table_id").datagrid('reload');
    			$.messager.alert("提示", data.msgCode+data.msgContent+ ',请联系管理员');
			}
    	});
	}

</script>



</html>

