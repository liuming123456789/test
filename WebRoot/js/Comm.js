 var errorMsg={
		 "passwordNull":"原密码为空",
		 "pwdNewNull" :"新密码为空",
		 "pwdNew2Null" :"二次密码为空",
		 "202":"参数异常",
		 "204":"请设置合理的密码长度",
		 "206":"两次输入密码长度不一致",
		 "209":"原用户密码不正确",
		 "911":"登陆失败",
		 "999":"抛异常",
 }
$(function() {

	// 刷新验证码
	$("#Vcode").click(
			function() { 
				$("#Vcode").attr("src",
						"/comm/GetVcode.jhtml?kill=df&rand=" + Math.random());
			})
	$("#admPwdChangeSubmit").click(
			function(){
/* if($("#password").trimVal()===""){
 alert(errorMsg["passwordNull"]);
 $("#password").focus();
 return false;
 }
 if($("#pwdNew").trimVal()===""){
 alert(errorMsg["pwdNewNull"]);
 $("#pwdNew").focus();
 return false;
 }
 if($("#pwdNew2").trimVal()===""){
 alert(errorMsg["pwdNew2Null"]);
 $("#pwdNew2").focus();
 return false;
 }*/
			  var param ="password="+$("#password").val()+"&pwdNew="+$("#pwdNew").val()+"&pwdNew2="+$("#pwdNew2").val(); 
		  $.ajax({
			  type:'post',
			  url:'/adm/admPwdChange.jhtml',
			  data:param+"&date="+new Date(),
			 
			  success:function(json){
			      
			    if(json.result==="0"){
			        alert("密码修改成功");
		            return true;
			    }else{
			    	alert(errorMsg[json.result]);
			    	return false;
			    }
		      } 
		 }) 	
 
		 
	 }) 
	 $("#upFileSubmit").click(function(){
		 
	 })
})
