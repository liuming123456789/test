<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset = utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>addMoreStu</title>

		<meta http-equiv="Context-Type" content="text/html;charset = utf-8">
		<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="/js/jquery-ui-1.8.23.min.js"></script>
		<script type="text/javascript" src="${cp}/js/text.js"></script>
		<script type="text/javascript" src="${cp }/js/Public.js"></script>
		<link href="${cp }/css/jquery-ui-1.8.23.custom.css" rel="stylesheet"
			type="text/css" />
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<input type='button' class='btnAdd' value='添加' />
		<div id="father">
			<table border="2" id="stuInfo">
				<!--<div id ="stuInfo">
<fieldset> 
-->
				<form action="" method="post" name="conform" class="conform"
					id="form">
					<tr>
						<td>
							学生账号
						</td>
						<td>
							<input type="text" name="stuacc" class="stuacc" />
						</td>
						<td>
							学生姓名
						</td>
						<td>
							<input type="text" name="stuname" class="stuname"   />
						</td>
						<td>
							学生密码
						</td>
						<td>
							<input type="password" name="stupwd" class="stupwd" />
						</td>
						<td>
							学生出生日期
						</td>
						<td>
							<input type="text" name="stubirth" class="stubirth"
								value="<c:out value="${stubirth}"/>" />
						</td>
						<td>
							学生联系地址
						</td>
						<td>
							<input type="text" name="stuaddress" class="stuaddress"
								value="天津" />
						</td>
						<td>
							学生身份证号
						</td>
						<td>
							<input type="text" name="stuidcard" class="stuidcard" value="340" />
						</td>
						<td>
							学生性别
						</td>
						<td>
							<select name="stusex" class="stusex">
								<option value="0">
									男
								</option>
								<option value="1">
									女
								</option>
							</select>
						</td>
						<td>
							学生籍贯
						</td>
						<td>
							<select name="provid" class="provid"
								onchange="getVlaue(this.value,this)">
								<option value=<c:out value=""/>>
									>> 选择 <<
								</option>
								<c:forEach items="${provList}" var="item">
									<option value=<c:out value="${item.prov_id}"/>>
										<c:out value="${item.province_name}" />
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<!--<input type='button' class='btnDel' value='删除' onclick = "$(this).parent().remove();"/> 
</fieldset>
</div>
-->
			</table>
		</div>
		<input type="button" class="stuInfoSubmit" value="提交" />
		<!--<input type="hidden"  id="Provlist"  value=""/>

  -->
		<input type="hidden"  id="Provlist22"  value="${provList}"/>
  
		<!--<input type= "button" id = "check"  value="check"/>
  -->
	</body>
</html>