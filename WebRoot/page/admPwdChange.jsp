<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>修改管理员密码</title>

		<meta http-equiv="Context-Type" content="text/html;charset=utf-8">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="/js/jquery-ui-1.8.23.min.js"></script>
		<script type="text/javascript" src="${cp}/js/Comm.js"></script>
		<script type="text/javascript" src="${cp}/js/Public.js"></script>

	</head>

	<body>
		<table width=900 cellspacing=0 cellpadding=0 border=0 align=center>
			<tr>
				<td height=300>
					<table width=600 height=400 cellspacing=0 cellpadding=0>
						<tr>
							<td style="HEIGHT: 30px" width=80>
								原密码
							</td>
							<td style="HEIGHT: 30px" width=150>
								<input id="password" name="password" style="WIDTH: 130px">
							</td>
							<td style="HEIGHT: 30px" width=370></td>
						</tr>
						<tr>
							<td style="height: 30px" width=80>
								新密码
							</td>
							<td style="height: 30px" width=150>
								<input id="pwdNew" name="pwdNew" style="width: 130px">
							</td>
							<td style="HEIGHT: 30px" width=370></td>
						</tr>
						<tr>
							<td style="height: 30px" width=80>
								二次密码
							</td>
							<td style="height: 30px" width=150>
								<input id="pwdNew2" name="pwdNew2" style="width: 130px">
							</td>
						</tr>
						
						<tr>
							<td style="height: 30px">
								<input type=button name="admPwdChangeSubmit"
									id="admPwdChangeSubmit" value="提交">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
