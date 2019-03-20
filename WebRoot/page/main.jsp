<%@ page language="java"  contentType ="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%>
 <c:set var="cp"  value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    
    <title> 学生管理系统</title> 
	<meta http-equiv="Content-Type" content="text/html;charsrt=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	--> 
	</head>
	<frameset  cols="213,*"> 
        <frame src="${cp}/page/left.jsp" name="leftFrame" id="leftFrame" scrolling="no" noresize="noresize" title="leftFrame"/>
        <frame style="mainFrame" name="mainFrame" id="mainFrame" src="${cp}/page/right.jsp"/>
	</frameset>
 
  <noframes> 
  <body >
  </body>
  </noframes>
</html>
