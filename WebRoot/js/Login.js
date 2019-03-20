var errorMsg={
		"usernameNull":"用户名为空",
		"passwordNull":"密码为空",
		"vcodeInNull":"验证码为空",
		"207":"验证码错误", 
		"202":"参数异常",
		"213":"用户名或密码错误",
		"203":"用户名不存在",
		"205":"用户状态不正常",
		"999":"登录失败",
}





$(function(){
	$("#loginBtn").click(function(){ 
	 
		  if($("#username").trimVal()===""){ alert(errorMsg["usernameNull"]);
		  $("#username").focus(); return false; }
		  if($("#password").trimVal()===""){ alert(errorMsg["passwordNull"]);
		  $("#password").focus(); return false; }
		  if($("#vcodeIn").trimVal()===""){ alert(errorMsg["vcodeInNull"]);
		  $("#vcodeIn").focus(); return false; }
		
 
		var param ="username="+$("#username").val()+"&password="+$("#password").val()+"&vcodeIn="+$("#vcodeIn").val();
		//alert(param);
    $.ajax({ 
    	type:'post', 
    	url : '/adm/admLogin.jhtml',  
        data:param+"&date="+new Date(),
    	success:function(json){ 
     
    	if(json.result==="0"){
    		alert("--------------------------------------------");
    		// window.location.href=json.gotoUrl;
    		 window.location.href='adm/admLgnSucc.jhtml';
    		return true;
   	}else{
   		alert(errorMsg[json.result]);
    		if(json.result==="207"){
   
   						$("#Vcode").attr("src",
  								"/comm/GetVcode.jhtml?kill=df&rand=" + Math.random());
   					}return false;
    		}
    	}  
    })
 
	})
})