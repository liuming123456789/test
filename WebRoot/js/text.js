var errorMsg={
         "0"  :"添加成功",
		 "202":"参数异常",
		 "209":"抛异常",
		 "204":"请设置合理的密码长度",
		 "911":"学生信息添加失败",
		 "205" :"该学生已存在"
}
$(function(){
 
	$(".btnAdd").click(function(){
		 
		 // console.log($("#Provlist22").val());
		
		/*
		 * var obj = JSON.parse('{"a": "Hello", "b": "World"}'); alert(obj);
		 */
		var provinceHtml; 
		$.ajax( {
			type : 'post',
			url : '/comm/getProvList.jhtml',
			async: false,
			success : function(json) {
				var province =  json.province;
				// console.log(province);
				// alert(province);
				if (province != null) {
					provinceHtml =  '<td>';
					provinceHtml += 	'<select name="provid"  class = "provid"  onchange = "getVlaue(this.value,this)" >'; 
					provinceHtml += 		'<option value= ""> >>选择 <<  </option>';
					for(var i = 0 ; i < province.length; i++){
						provinceHtml += 		'<option  value='+ province[i].prov_id +'>'+ province[i].province_name +'</option>';
						// alert(province[i].prov_id);
					}
					provinceHtml += 	'</select>';
					provinceHtml += '</td>';
					return true;
				} else {
					alert("操作失败" + json.result);
					return false;
				}
			} 
		})
        var now = new Date();   
        var year = now.getFullYear();          
        var month = now.getMonth() + 1;      
        var day = now.getDate();  
        var clock = year + "-";   
		    if(month < 10)  
		       clock += "0";   
		       clock += month + "-";   
		    if(day < 10)  
		       clock += "0";   
		       clock += day;
// alert(clock);
		var html =  '<table border="2" id="stuInfo">';
			html += '<tr>';
			html += '<td >学生账号</td><td><input type="text" name="stuacc" class="stuacc" value="" /></td>';
			html += '<td>学生姓名</td><td><input type="text" name="stuname" class="stuname"   value=""/></td>';
			html += '<td>学生密码</td><td><input type="password" name="stupwd" class="stupwd"  /></td>';
			html += '<td> 学生出生日期</td><td><input type="text" name="stubirth" class="stubirth" value= "'+clock+'" /></td>';
			html += '<td>学生联系地址</td><td><input type="text" name="stuaddress" class="stuaddress" value="天津"/></td>';
			html += '<td>学生身份证号</td><td><input type="text" name="stuidcard" class="stuidcard" value="340"/></td>';
			html += '<td>学生性别</td>	<td><select name= "stusex" class = "stusex" ><option value="0"> 男</option><option value="1"> 女</option></select></td>';
			html += '<td>学生籍贯</td>'+  provinceHtml; 
			html += '</tr>';
			html += '</table>';
		
		$("#father").append(html);
	});
	
	$(".stubirth").datepicker( {
		dateFormat : 'yy-mm-dd',
	}).datepicker('setDate',
			$(".stubirth").val() === "" ? new Date() : $(".stubirth").val());

	$(".stuInfoSubmit")
	.click(function() {
		// alert("------------------------------------------------");
			// 检验账户为空
			var noList = [];
			$(".stuacc").each(function(){ 
				noList.push($(this).val()); 
			}); 
			 for(var i= 0 ;i<noList.length;i++){
				 // alert(111111111111111);
				if(noList[i].length===0){ 
					alert('账户为空');
					alert(i);
					// alert($('input[name="stuacc"]:eq(0)'));
					$("input[name='stuacc']:eq("+i+")").focus();
					return;
				}
			 }
			// 检验姓名为空
			 var noList2 = [];
			$(".stuname").each(function(){
				// alert($(this).val());
				noList2.push($(this).val()); 
			});
			 for(var i= 0 ;i<noList2.length;i++){
				 // alert(111111111111111);
				if(noList2[i].length===0){ 
					alert('姓名为空'); 
					$("input[name='stuname']:eq("+i+")").focus();
					return;
				}
			 }
			// 检验密码为空
			 
             var noList3 = [];
			$(".stupwd").each(function(){
				  // alert($(this).val());
				noList3.push($(this).val());
			});
			 for(var i= 0 ;i<noList3.length;i++){
				 // alert(111111111111111);
				if(noList3[i].length===0){
					alert('密码为空'); 
					$("input[name='stupwd']:eq("+i+")").focus();
					return;
				}
			 }
			// 检验出生日期为空
			 var noList4 = [];
			$(".stubirth").each(function(){
				// alert($(this).val());
				noList4.push($(this).val());
			});
			 for(var i= 0 ;i<noList4.length;i++){
				 // alert(111111111111111);
				if(noList4[i].length===0){
					alert('出生日期为空'); 
					// noList[i].focus();
					return;
				}
			 }
			// 检验联系地址为空
			 var noList5 = [];
			$(".stuaddress").each(function(){
				// alert($(this).val());
				noList5.push($(this).val());
			});
			 for(var i= 0 ;i<noList5.length;i++){
				 // alert(111111111111111);
				if(noList5[i].length===0){
					alert('地址为空'); 
					// noList[i].focus();
					return;
				}
			 }
			// 检验身份证号为空
			  var noList6 = [];
			$(".stuidcard").each(function(){
		        // alert($(this).val());
				noList6.push($(this).val());
			});
			 for(var i= 0 ;i<noList6.length;i++){
				 // alert(111111111111111);
				if(noList6[i].length===0){
					alert('身份证号为空'); 
					// noList[i].focus();
					return;
				}
			 }
			// 检验性别
			 var noList7 = [];
			 $(".stusex").each(function(){
			        // alert($(this).val());
					noList7.push($(this).val());
				});
			 for(var i= 0 ;i<noList7.length;i++){
				 // alert(111111111111111);
				if(noList7[i].length===0){
					alert('性别为空'); 
					// noList[i].focus();
					return;
				}
			 } 
			// 检验省份为空
			 var noList8 = [];
			$(".provid").each(function(){
				// alert($(this).val());
				noList8.push($(this).val());
			});
			 for(var i= 0 ;i<noList8.length;i++){
				 // alert(111111111111111);
				if(noList8[i].length===0){
					alert('省份为空'); 
					$("select[name='provid']:eq("+i+")").focus();
					return;
				}
			 }
			// 检验城市为空
			 var noList9 = [];
			$(".city_id").each(function(){
			    // alert($(this).val());
				noList9.push($(this).val());
			});
			 for(var i= 0 ;i<noList9.length;i++){
				 // alert(111111111111111);
				if(noList9[i].length===0){
					alert('城市为空'); 
					$("select[name='city_id']:eq("+i+")").focus();
					return;
				}
			 }
			var param = "stuacc=" + noList + "&stuname="
					+ noList2 + "&stupwd="
					+ noList3 + "&stubirth="
					+ noList4 + "&stuaddress="
					+ noList5 + "&stuidcard="
					+ noList6 + "&stusex="
					+ noList7
					+ "&prov_id=" + noList8 + "&city_id="
					+ noList9; 
			alert(param);
			$.ajax( {
				type : 'post',
				url : '/comm/stuInfoAdd.jhtml',
				data : param + "&date=" + new Date(),

				success : function(json) {
					if (json.result === "0") {
						alert("操作成功");
						return true;
					} else {
						alert("操作失败" + json.result);
						return false;
					}
				}

			})
				
		}) 

/*
 * $('#check').click(function(){
 * 
 * //检验性别 var noList7 = []; $(".stusex").each(function(){
 * alert($(this).attr('checked')); if($(this).attr('checked')){
 * noList7.push($(this).val()); } }); console.log(noList7); });
 */
 
})
function getVlaue(value,th){ 
	// alert($(th).html());
	var param = "prov_id="+value;
	// alert(param);
	$.ajax( {
		type : 'post',
		url : '/comm/cityListShow.jhtml',
		data : param + '&date='+ new Date(),
		success : function(jsonText) {
		// alert(111+jsonText);
		if (null == jsonText.cityList) {
			return;
		} 
		var cityMsg = "";
		cityMsg += "<select name=\"city_id\" class=\"city_id\">";
		$.each(jsonText.cityList,function(i,item) {
			if (item.isallprov === '1') {
				cityMsg += "<option value="
					+ item.city_id
					+ ">"
					+ item.city_name
					+ "</option>";
			}
		})
		cityMsg += "</select>"; 
		// alert(cityMsg);
		// alert($("#cityList").val(cityMsg));
		// $("#cityList").innerHTML
		// = cityMsg;
		if($(th).parent().parent().find(".city_id").size() == 0){
			$(th).parent().parent().append(cityMsg);
		}else{
			$(th).parent().parent().find(".city_id").remove();
			$(th).parent().parent().append(cityMsg);
		}  
	}
	}) 
}

/*
 * $(function(){ $(".btnAdd").click(function(){ var provListHtml =""; $.ajax({
 * type : 'post', url : '/comm/getProvList.jhtml', async: false, success :
 * function(json){ var provList = json.province; if(provList!=""){ provListHtml
 * +="<td>"; provListHtml += "<select name='provid' class='provid'
 * onchange='getVlaue(this.value,this)'>"; for(var i = 0;i<provList.length;i++){
 * provListHtml += "<option
 * value="+provList[i].prov_id+">"+provList[i].province_name+"</option>"; }
 * provListHtml += "</select>"; provListHtml +="</td>" return true; }else{
 * alert("无数据") return false; } } }) var now = new Date(); var year =
 * now.getFullYear(); var month = now.getMonth()+1; var day = now.getDay(); var
 * clock = year +"-"; if(month<10) clock += "0"; clock += mooth+"-"; if(day<10)
 * clock += "0"; clock += day; var html = "<table border='2' id='stuInfo'>"; }) })
 * function getVlaue(value,th){ var param = "prov_id="+value; var cityListHtml
 * =""; $.ajax({ type : 'post', url : '/comm/cityListShow.jhtml', data : param,
 * success : function(json){ var cityList = json.cityList; if(cityList!=""){
 * cityListHtml += "<select name='city_id' class='city_id' >"; for(var i = 0;i<cityList.length;i++){
 * cityListHtml += "<option
 * value="+cityList[i].city_id+">"+cityList[i].city_name+"</option>"; }
 * cityListHtml += "</select>";
 * if($(th).parent().parent().find(".city_id").size() == 0){
 * $(th).parent().parent().append(cityListHtml); }else{
 * $(th).parent().parent().find(".city_id").remove();
 * $(th).parent().parent().append(cityListHtml); } } } }) }
 */ 