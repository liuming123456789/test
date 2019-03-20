var errorMsg={
         "0"  :"添加成功",
		 "202":"参数异常",
		 "209":"抛异常",
		 "204":"请设置合理的密码长度",
		 "911":"学生信息添加失败",
		 "205" :"该学生已存在"
}
$(function() {	
	$("#stubirth").datepicker( {
		dateFormat : 'yy-mm-dd',
	}).datepicker('setDate',
			$("#stubirth").val() === "" ? new Date() : $("#stubirth").val());

	$("#stuInfoSubmit")
			.click(function() {
				// alert("------------------------------------------------");
					// 检验账户为空
					if ($("#stuacc").trimVal() === "") {
						alert('账户为空');
						$("#stuacc").focus();
						return false;
					}

					// 检验姓名为空
					if ($("#stuname").trimVal() === "") {
						alert('姓名为空');
						$("#stuname").focus();
						return false;
					}
					// 检验密码为空
					if ($("#stupwd").trimVal() === "") {
						alert('密码为空');
						$("#stupwd").focus();
						return false;
					}
  
					// 检验出生日期为空
					if ($("#stubirth").trimVal() === "") {
						alert('出生日期为空');
						$("#stubirth").focus();
						return false;
					}
					// 检验联系地址为空
					if ($("#stuaddress").trimVal() === "") {
						alert('联系地址为空');
						$("#stuacc").focus();
						return false;
					}
					// 检验省份证号为空
					if ($("#stuidcard").trimVal() === "") {
						alert('身份证号为空');
						$("#stuidcard").focus();
						return false;
					}
					var boolCheck = $('input:radio[name="stusex"]').is(
							":checked");
					if (!boolCheck) {
						alert('操作失败:需选择学生性别');
						return;
					}
 
					/*
					 * var noListProv = [];
					 * $('input[name="prov_id"]:checked').each(function() { if
					 * ($(this).attr('checked')) {
					 * noListProv.push($(this).val()); } }); if
					 * (noListProv.length === 0) { alert("操作失败：学生身份不能为空")
					 * return; }
					 */
					/*
					 * boolCheck = $('input:[name="prov_id"]').is(":checked");
					 * if (!boolCheck) { alert('操作失败：请选择省份'); return; }
					 */
					if ($("#provid").trimVal() === "") {
						alert('选择省份为空');
						return false;
					} 
 
 
					if ($("#city_id").trimVal() === "") {
						alert('选择城市为空');
						return false;
					} 
					var param = "stuacc=" + $("#stuacc").val() + "&stuname="
							+ $("#stuname").val() + "&stupwd="
							+ $("#stupwd").val() + "&stuidcard="
							+ $("#stuidcard").val() + "&stuaddress="
							+ $("#stuaddress").val() + "&stubirth="
							+ $("#stubirth").val() + "&stusex="
							+ $('input[name="stusex"]:checked').val()
							+ "&prov_id=" + $("#provid").val() + "&city_id="
							+ $("#city_id").val();
				 
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
				
								// 省市联动
				 	$("#provid")
					.change(function() {
						 // ($("#provid").val());
							/*
							 * if ($("#provid").val() === '') {
							 * document.getElementById("cityList").innerHTML =
							 * ''; return; }
							 */
							var param = "prov_id="
									+ $("#provid").val();
							$
									.ajax( {
										type : 'post',
										url : '/comm/cityListShow.jhtml',
										data : param + '&date='
												+ new Date(),
										success : function(jsonText) {
											if (null == jsonText.cityList) {
												return;
											}

											var cityMsg = "";
											cityMsg += "<select name=\"city_id\" id=\"city_id\">";
											$
													.each(
															jsonText.cityList,
															function(i,
																	item) {
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
											document
													.getElementById("cityList").innerHTML = cityMsg;
										}
									})
						}) 		
})
 