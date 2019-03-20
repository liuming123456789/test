<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.SystemUtils"%>
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fn"  uri="/WEB-INF/fn.tld" %>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>学生管理系统登录</title> 
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
     <script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script> 
		<script type="text/javascript" src="/js/jquery-ui-1.8.23.min.js"></script>
     <script type="text/javascript" src="${cp}/js/Comm.js"></script>
      <script type="text/javascript" src="${cp}/js/Login.js"></script>
       <script type="text/javascript" src="${cp}/js/Public.js"></script>
       
  </head>
  
  <!--<%
  	HttpSession request2 = request.getSession();
  	System.out.print("==="+request2.getAttribute("SESS_VCODE"));
   %>
  
  --><body> 
     <table width=900 cellspacing=0 cellpadding=0 border=0 align=center>
        <tr>
        <td height=300>
        <table width=600 height=400 cellspacing=0 cellpadding=0>
         <tr>
         <td style="HEIGHT: 30px" width=80>
                                  登录名
         </td>
         <td style="HEIGHT: 30px" width=150>
           <input id="username" name="username" value="156" style="WIDTH:130px" >
         </td>
         <td style="HEIGHT: 30px" width=370></td>
         </tr>
         <tr>
         <td style="height:30px" width=80>
                                登录密码
         </td>
         
         <td style="height:30px" width=150>
          <input id="password" name="password" value="123456" style="width:130px">
         </td>
         <td style="HEIGHT: 30px" width=370></td>
         </tr>
         <tr>
         <td style="height:30px" width=80>
                             验证码
         </td>
         <td style="height:30px" width=150>
	         <input id="vcodeIn" name="vcodeIn" style="width:130px" value = "${ SESS_VCODE}" />
         </td>
         <td style="height:30px align:left">
          <a href="#"> <img alt="看不清！换一张" src="${cp}/comm/GetVcode.jhtml?kill=df&rand=" id="Vcode"  /></a> 
         </td>
         </tr> 
         <tr>
         <td  style="height:30px" >
         <input 
          type=image src="${cp}/image/login_button.gif" name="loginBtn" id="loginBtn" >
         </td>
         </tr>                   
        </table>
        </td> 
        </tr> 
     </table>
  </body>
</HTML>
