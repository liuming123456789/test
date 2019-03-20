<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>上传text</title>

		<meta http-equiv="Context-Type" content="text/html;charset=utf-8">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="/js/jquery-ui-1.8.23.min.js"></script>
        <script type="text/javascript" src=" ${cp}/js/Upload.js"></script>
	</head>
	<body>
		<form id="upTextForm"  method="POST"  enctype="multipart/form-data" action="/upLoad/textUpLoad.jhtml">
			<p>
				<input type="file" name="file" id="textfile"  value="选择文件" />
				<br />
				<input type="button"  id="upTextBtn" onclick="textUpLoad()" name = "upTextBtn" value="上传text" /> 
			</p>
			<!--<p>
                  <input type="button" name="Download" value="下载excle"/>
            </p>              
          -->
		</form>
	</body>
</html>
