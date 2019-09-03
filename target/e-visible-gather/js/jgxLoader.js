/**
 * 页面加载等待页面
 */
var height = window.screen.height-200;  
var width = window.screen.width;
var leftW = 300;
if(width>1200){
   leftW = 400;
}else if(width>1000){
   leftW = 250;
}else {
   leftW = 50;
}
var _html = "<div id='loading' style='position:absolute;left:0;width:100%;height:"+height+"px;top:0;background:#E0ECFF;opacity:1;filter:alpha(opacity=100);'>"
	+"<div style='position:absolute;  cursor1:wait;left:"+leftW+"px;top:200px;width:auto;height:16px;padding:10px 5px 10px 30px;"
	+"background:#E0ECFF url(<%=request.getContextPath()%>/images/pagination_loading.gif) no-repeat scroll 8px 10px;"
	+"border:1px solid #ccc;color:#000;'>"
	+"正在加载，请稍候...</div></div>";
window.onload = function(){
	var _mask = document.getElementById('loading');
	_mask.parentNode.removeChild(_mask);
}
document.write(_html);

//$("<div id=\"mask_div\" class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");
//$("<div id=\"mask_msg_div\" class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });