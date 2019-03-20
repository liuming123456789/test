var errorMsg={
         "0"  :"添加成功",
		 "202":"参数异常",
		 "209":"抛异常",
		 "911":"学生信息删除失败",
}
$(function() {
	// 按钮提交
	$("#stuInfoQueryBtn").click(function() { 
		$("#pageNo").val("1");
		// alert(pageNo);
		// document.getElementById("stuInfoQueryBtn").disabled =false;
			$("#stuInfoQueryForm").submit(); 
		})
	// 首页提交
	$("#stuInfoQueryFirst").click(function() {
		$("#pageNo").val("1");
		$("#stuInfoQueryForm").submit();
	})
	// 上页提交
	$("#stuInfoQuerylast").click(function() {
		var pageNo = parseInt($("#pageNo").val(), 10);
		// alert(pageNo);
			if (pageNo === 1) {
				return false;
			} else {
				pageNo -= 1;
				$("#pageNo").val(pageNo);
				$("#stuInfoQueryForm").submit();
			}
		})
	// 下页提交
	$("#stuInfoQuerynext").click(function() {
		var pageNo = parseInt($("#pageNo").val(), 10);
		var pageCount = parseInt($("#pageCount").val(), 10);
		// alert(pageNo);
			if (pageNo === pageCount) {
				return false;
			} else {
				pageNo += 1;
				$("#pageNo").val(pageNo);
				$("#stuInfoQueryForm").submit();
			}
		})
	// 尾页提交
	$("#stuInfoQueryend").click(function() {
		var pageNo = parseInt($("#pageNo").val(), 10);
		var pageCount = parseInt($("#pageCount").val(), 10);
		// alert(pageNo);
			if (pageNo === pageCount) {
				return false;
			} else {
				pageNo = pageCount;
				$("#pageNo").val(pageNo);
				$("#stuInfoQueryForm").submit();
			}
		})
	// 跳转提交
	$("#stuInfoQueryGo").click(function() {
		// alert("跳转");
			var goPageNo = parseInt($("#stuInfoQueryGopageGo").val(), 10);
// alert(goPageNo);
			var pageNo = parseInt($("#pageNo").val(), 10);
			// alert(pageNo + "----------------------");
			var pageCount = parseInt($("#pageCount").val(), 10);
			// console.log(pageNo);
			// console.log(goPageNo);
			// console.log(pageNo === goPageNo);
			if (pageNo === goPageNo) {
				alert('当前页不做跳转');
				// console.log("当前页")
				return false;
			} else if (goPageNo < 1 || goPageNo > pageCount) {
				alert('页码填写错误!');
				return false;
			} else {
				$("#pageNo").val(goPageNo);
				$("#stuInfoQueryForm").submit();
			}
		})
		// 批量删除
	$("#batchDelStu").click(function(){
        var noList = [];
       
        $('input[name="checkbox_stu_id"]:checked').each(function() {
        	if($(this).attr('checked')) {
        		noList.push($(this).val());
        	}
        })  ;

            console.log(noList);
            if(noList.length===0){
            	alert('请选择要批量删除的学生');
            	return ;
            }
            if(!confirm("确定要删除这些学生"+noList))
            		return false;
            console.log("ceshi");
            var param = "stu_id="+noList;
            $.ajax({
            	type:'post',
            	url :'/comm/batchDelStu.jhtml',
            	data :param+'&date='+new Date(),
            	success : function(json){
            	if(json.result==="0"){
            		alert('操作成功');
            		$("#stuInfoQueryForm").submit();
            		return true;
            	}else{
            		alert('操作失败'+json.result);
            		return false;
            	} 
            }
            }) 
	})
	// 单个删除
	$(".delStu").click(function(){
		// 单删除按钮和checkbox选中不选中没什么关系
		/*
		 * if($('input[name="checkbox_stu_id"]').attr('checked')){
		 * console.log('错误'); }
		 */
		// console.log('错误-----------------');
		if(!confirm("确定要删除该学生"+$(this).attr("stuId")))
			return false;
	   var param="stu_id="+$(this).attr("stuId");
	   console.log(param);
	  $.ajax({ 
		   type:'post',
		   url : '/comm/batchDelStu.jhtml',
		   data : param +'&date='+new Date(),
		   success : function(json){
			if(json.result==="0"){
        		alert('操作成功');
        		$("#stuInfoQueryForm").submit();
        		return true;
        	}else{
        		alert('操作失败'+json.result);
        		return false;
        	} 
	       } 
	  })
	})
	// 重置密码
		$(".stuPwdChange").click(function(){
		// 单删除按钮和checkbox选中不选中没什么关系
		/*
		 * if($('input[name="checkbox_stu_id"]').attr('checked')){
		 * console.log('错误'); }
		 */
		// console.log('错误-----------------');
			
			if(!confirm("确定要重置该学生密码"+$(this).attr("stuId")))
				return false;
		   var param="stu_id="+$(this).attr("stuId");
	   console.log(param);
	  $.ajax({ 
		   type:'post',
		   url : '/comm/batchChangeStuPwd.jhtml',
		   data : param +'&date='+new Date(),
		   success : function(json){
			if(json.result==="0"){
        		alert('操作成功');
        		$("#stuInfoQueryForm").submit();
        		return true;
        	}else{
        		alert('操作失败'+json.result);
        		return false;
        	} 
	       } 
	  })
	})
	//批量下载
		/*$("#stucard").click(function(){
			alert(000000000000000000);
			alert($("#cs").attr("href"));
	})*/
})