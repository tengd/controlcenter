
$(function(){
	//将验证码框绑定回车事件， 输入验证码直接回车即可登录
	$('#vcode_input').bind('keypress',function(event){          
        if(event.keyCode == 13)      
        {  
        	$("#loginBtn").trigger('click');
        	return false;
        }  
    });
})

	    
   	// 登陆事件
    	function login(){
    		var code = $("#code_input").val();
    		if(code == null || code == ""){
    			$.messager.alert("系统提示", "用户名不能为空！", "info");
    			return ;
    		}
    		var pwd = $("#pwd_input").val();
    		if(pwd == null || pwd == ""){
    			$.messager.alert("系统提示", "密码不能为空！", "info");
    			return ;
    		}
    		var vcode = $("#vcode_input").val();
    		if(vcode == null || vcode == ""){
    			$.messager.alert("系统提示", "请输入验证码！", "info");
    			return ;
    		}
    		document.getElementById('userForm').submit();
    		//return;
    	}
    	// 刷新验证码
    	function refreshVcodeImg(){
    		var vcImg = document.getElementById("vcode_img");
    		vcImg.src = vcImg.src +"?";
    	}
    	
    		