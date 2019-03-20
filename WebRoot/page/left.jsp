<%@ page language="java"   pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%>
<s:set var ="cp" value="${pageContext.request.contextPath}"></s:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 
    
    <title>leftFrame</title>

	<meta http-equiv="Context-Type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body>
  <table> 
  <tr>
  <table align="center" height="50" width="200">
  <tr>
  <td colspan="2" height="45" width="50" >
   <img  src="${cp }/image/ico16.gif">
  </td>
  <td height="45" width="100">
   ${admVo.adm_name};[<a href="${cp}/adm/admLgnout.jhtml" target="_top">退出</a>]
  </td>
  </tr>
</table>
</tr>
<table> 
<tr>
<tr>
<td>
 <a href="${cp}/page/admPwdChange.jsp" target="mainFrame" >修改管理员密码</a>
</td>
</tr>  
<tr>
<td> 
  <a href ="${cp}/comm/provListShow.jhtm" target="mainFrame">省份列表字典</a >
 </td>
</tr>
<tr>
<td> 
  <a href ="${cp}/page/stuInfoQuery.jsp" target="mainFrame">学生信息分页查询</a >
 </td>
</tr>
<tr>
<td>
<a href ="${cp}/comm/goAddStuInfo.jhtml" target ="mainFrame">添加学生信息</a>
</td>
</tr>
<tr> 
<td>
<a href ="${cp}/page/upExcelXls.jsp" target ="mainFrame">上传excel信息</a>
</td>
 </tr> 
 <tr> 
<td>
<a href ="${cp}/page/upText.jsp" target ="mainFrame">上传text信息</a>
</td>
 </tr>
</tr>
</table>
</table>
  </body>
</html>
