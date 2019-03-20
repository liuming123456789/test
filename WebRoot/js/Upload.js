 var errorMsg ={
		 "2003": "文件为2003版本",
		 "2010": "文件为2010版本",
		 "000": "文件不是excel", 
		 "202":"参数异常",
		 "209":"抛异常",
		 "204":"请设置合理的密码长度",
		 "911":"学生信息添加失败",
		 "205" :"该学生已存在",
 }
function upload(){
	if ($('#xlsfile').val() === "") {
		alert("文件为空");
		return false;
	} 
	//因为new FormData的参数需要一个HTMLElement类型的数据，而jQuery得到的是个HTMLElement的集合，哪怕只有一个元素。所以需要用[]取其第一个元素
	var param = new FormData($("#upExcelForm")[0]);
	$.ajax({
		type : 'post',
		url : "/upLoad/excelUpLoad.jhtml",
		data : param,
		cache : false,
		processData : false,   
		contentType : false,
		success : function(json) {  
				// alert(json);
				if (json.result === "0") {
					alert("[操作成功!]");
					return true;
				} else {
					alert(errorMsg[json.result]);  
				}  
	    }
	}); 
}
 function textUpLoad(){
	// alert(44444444444);
		if ($('#textfile').val() === "") {
			alert("文件为空");
			return false;
		}
		var param = new FormData($("#upTextForm")[0]);
		$.ajax({
			type : 'post',
			url : "/upLoad/textUpLoad.jhtml",
			data : param,
			cache : false,
			processData : false,
			contentType : false,
			success : function(json) {  
					// alert(json);
					if (json.result === "0") {
						alert("[操作成功!]");
						return true;
					} else {
						alert(errorMsg[json.result]);  
					}  
		    }
		}); 
 }
/* $(function(){ 
	 $("#upTextBtn").click(function(){
		// alert(44444444444);
			if ($('#textfile').val() === "") {
				alert("文件为空");
				return false;
			}
			var param = new FormData($("#upTextForm")[0]);
			$.ajax({
				type : 'post',
				url : "/upLoad/textUpLoad.jhtml",
				data : param,
				cache : false,
				processData : false,
				contentType : false,
				success : function(json) {  
						// alert(json);
						if (json.result === "0") {
							alert("[操作成功!]");
							return true;
						} else {
							alert(errorMsg[json.result]);  
						}  
			    }
			}); 
	 })		
 })	 */