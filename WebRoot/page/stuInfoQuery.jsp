<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.23.min.js"></script>
<script type="text/javascript" src="${cp}/js/Query.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>分页查询</title>

		<meta http-equiv="Context-Type" content="text/html;charset=utf-8">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script language="javascript">
	function selectAll() {
		var obj = document.stuInfoQueryForm.elements;
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].name == "checkbox_stu_id") {
				obj[i].checked = true;
			}
		}
	}
	function unselectAll() {
		var obj = document.stuInfoQueryForm.elements;
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].name == "checkbox_stu_id") {
				if (obj[i].checked == true) {
					obj[i].checked = false;
				} else {
					obj[i].checked = true;
				}

			}
		}
	}
	function clrselectAll() {
		var obj = document.stuInfoQueryForm.elements;
		for(i in obj){
			console.log(obj);
		}
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].name == "checkbox_stu_id") {
				obj[i].checked = false;
			}
		}
	}
</script>
	</head>

	<body>
		<form action="${cp}/comm/stuInfoQuery.jhtml" method="post"
			name="stuInfoQueryForm" id="stuInfoQueryForm">
			<input type="hidden" name="pageNo" id="pageNo"
				value="${empty pageNo ? '1' : pageNo}" />
			<input type="hidden" name="pageCount" id="pageCount"
				value="${empty pageCount ? '0' : pageCount}" />
			<table>
				<tr>
					<td>
						身份证号
					</td>
					<td>
						<input name="stucard" id="stucard"
							value="<c:out value="${stucard}"/>" />
					</td>
				</tr>
				</form>
				<tr>
					<td>
						<input type="button" name="stuInfoQueryBtn" value="查询"
							id="stuInfoQueryBtn" />
					</td>
				</tr>
				<table border="2">
					<c:if test="${!empty stuList}">
						<span>选择:<a href="#" onClick=
	"selectAll()"
>全选</a> <a
							href="#" onClick=
	unselectAll();;
>反选</a> <a href="#"
							onClick=
	clrselectAll();;
> 清空</a> </span>
						<input type="button" name="batchDelStu" id="batchDelStu"
							value="批量删除" />
<!--						  <input type="button" name="stuDow" id="stuDow"-->
<!--							value="下载" />	--> 
 						<a href="${href1}">下载为excel</a>
 						<a href="${href2}">下载为csv</a>
						<a href="${href3}">下载为text</a>	 
					</c:if>
					<c:choose>
						<c:when test="${empty stuList}">
						</c:when>
						<c:when test="${!empty stuList}">
							<tr align="center">
								<td>
									学号
								</td>
								<td>
									序号
								</td>
								<td>
									账号
								</td>
								<td>
									状态
								</td>
								<td>
									身份证号
								</td>
								<td>
									姓名
								</td>
								<td>
									省份
								</td>
								<td>
									城市
								</td>
								<td>
									性别
								</td>
								<td>
									联系地址
								</td>
								<td>
									出生日期
								</td>
							</tr>
							<c:forEach items="${stuList}" var="item" varStatus="index">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_stu_id"
											id="checkbox_stu_id"
											value=' <c:out value="${item.stu_id}" />' />
									</td>
									<td>
										<c:out value="${index.count}"></c:out>
									</td>
									<td>
										<c:out value="${item.stu_acc}"></c:out>
									</td>
									<td>
										<c:choose>
											<c:when test="${item.stu_state eq '0'}">可用</c:when>
											<c:when test="${item.stu_state eq '1'}">禁用</c:when>
										</c:choose>
									</td>
									<td>
										<c:out value="${item.stu_card}"></c:out>
									</td>
									<td>
										<c:out value="${item.stu_name}"></c:out>
									</td>
									<td>
										<c:out value="${item.provVo.province_name}"></c:out>
									</td>
									<td>
										<c:out value="${item.cityVo.city_name}"></c:out>
									</td>
									<td>
										<c:choose>
											<c:when test="${item.stu_sex eq '0'}">男 </c:when>
											<c:when test="${item.stu_sex eq '1'}">女</c:when>
										</c:choose>
									</td>
									<td>
										<c:out value="${item.stu_address}"></c:out>
									</td>
									<td>
										<c:out value="${item.stu_birth}"></c:out>
									</td>
									<td>
										<c:if test="${!empty stuList}">
											<input type="button" class="delStu" stuId="${item.stu_id}"
												name="delStu" value="删除" />
											<input type="button" class="stuPwdChange" name="stuPwdChange"
												stuId="${item.stu_id}" value="重置密码" />
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>
				</table>
				<table Border="2">
					<tr>
						<td>
							共
							<span><c:out value="${pageCount}"></c:out> </span>页|第
							<span> <c:out value="${pageNo}"></c:out> </span>页
						</td>
						<td>
							<a href="#" name="stuInfoQueryFirst" id="stuInfoQueryFirst">首页
							</a><a href="#" name="stuInfoQuerylast" id="stuInfoQuerylast">上页
							</a><a href="#" name="stuInfoQuerynext" id="stuInfoQuerynext">下页
							</a><a href="#" name="stuInfoQueryend" id="stuInfoQueryend">尾页 </a>跳到
						</td>
						<td>
							<input size="1" name="stuInfoQueryGopageGo"
								id="stuInfoQueryGopageGo" type="text" />
						</td>
						<td>
							<input type="submit" name="stuInfoQueryGo" id="stuInfoQueryGo"
								value="跳页" />
						</td>
					</tr>
				</table>
			</table>
	</body>
</html>
