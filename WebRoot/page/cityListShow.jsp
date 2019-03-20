
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>城市字典</title>


		<meta http-equiv=" Context-Type" content="text/html;charset=utf-8">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>

		This is my JSP page.￥￥######################￥￥￥￥￥￥￥￥￥￥
		<br>
		<table>

			<tr align="center">
				<td>
					城市编号
				</td>
				<td>
					城市描述
				</td>

			</tr>
			<c:forEach items="${cityList}" var="item" varStatus="index">
				<tr>
					<td>
						<c:out value="${item.city_id}"></c:out>
					</td>
					<td>
						<c:out value="${item.city_name}"></c:out>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>