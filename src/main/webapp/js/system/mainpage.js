$(document).ready(function(){
	//加载菜单项
	initHeadMenu();
});

function initHeadMenu(){
	$.ajax({
		url: "fun/getFunTreeByPermission",
		type: "get",
		cache: false,// 解决IE下有缓存的问题
		async: false,
		contentType: "application/json; charset=utf-8",
		error: function(){
			$("#nav_tree").tree("loadData", "");
			$.messager.alert("系统提示", "加载菜单数据失败！", "warning");
		},
		success: function(data){
			$("#mainpage_sidemenu").html("");
			for(var i = 0 ; i < data.length ; i++){
				var title = data[i].text;
				var text = "<li class=''><a href='#'><span class=\"iconfont sider-nav-icon\">&#xe620;</span><span class=\"sider-nav-title\">"+title+"</span><i class=\"iconfont\">&#xe642;</i></a><ul class=\"sider-nav-s\">"
				var children = data[i].children;				
				for(var j = 0 ; j < children.length ; j++){
					var url = "fun/getFunJump?url="+children[j].attributes.url;
					var subText = '<li><a href="javascript:void(0)" onclick="addContentTabs(\''+url+'\',\''+children[j].text+'\')">'+children[j].text+'</a></li>';
					text += subText;
				}
				text += "</ul></li>"
				$("#mainpage_sidemenu").append(text);
			}
		}
	});
}

function logout(){
	window.location.href = "logout";
}

function addContentTabs(url , tabname){
	if ($('.easyui-tabs1').tabs('exists', tabname)){ 
        $('.easyui-tabs1').tabs('select', tabname); 
    }else{
    	$('.easyui-tabs1').tabs('add',{
    		title:tabname ,
    		closable:true,
    		content:'<iframe class="page-iframe" src="'+url+'" frameborder="no"  id="'+url+'" border="no" height="100%" width="100%" scrolling="auto"></iframe>'
    	});   	
    }	
}

function addContentTabsForDataSourceLink(url , tabname , dataSourceId , dataSourceType){
	if ($('.easyui-tabs1').tabs('exists', tabname)){ 
        $('.easyui-tabs1').tabs('select', tabname); 
    }else{
    	$('.easyui-tabs1').tabs('add',{
    		title:tabname ,
    		closable:true,
    		content:'<iframe class="page-iframe"  src="'+url+'&dataSourceId='+dataSourceId+'&dataSourceType='+dataSourceType+'" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>'
    	});
    }
}

function closeTab(tabId){
	$(".easyui-tabs1").tabs('close' , tabId);
}

