<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/js/prototype.js'/>"></script>
<style type="text/css">
<!--
input {
	font-size: 12px;
	border: 1px solid #425E8D;
}

td {font-size: 12px}
-->
</style>
</head>
<body bgcolor="#FFFFFF">
<script type="text/javascript">
function onLogin() {
   var frm = document.getElementById('loginForm');
   if(frm) {
       frm.submit();
   }
}
</script>
<!-- ImageReady Slices (login.psd) -->
<br>
<br>
<br>
<table align="center" width="350" cellpadding="2" cellspacing="2">
<tr><td align="left">
<c:if test="${param.login_error == 'code_error'}">
   <div style="border:1px solid #a9a9a9; width: 350; padding: 2px; margin: 2px">
       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;验证码输入错误！
   </div>
</c:if> 
<c:if test="${param.login_error == 'user_psw_error' || param.login_error == '1'}">
   <div style="border:1px solid #a9a9a9; width: 350; padding: 2px; margin: 2px">
       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;用户名或密码错误，请重试！
   </div>
</c:if> 
<c:if test="${param.login_error == 'too_many_user_error'}">
    <div style="border:1px solid #a9a9a9; width: 350; padding: 2px; margin: 2px">
       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;该用户已经在其他地方登录了，请稍候。
   </div>
</c:if>
</td>
</tr>
</table>
<br>
<div align="center">
<table width="350" cellpadding="0" cellspacing="0">
	<tr height="20px">
		<td style="color:#FFF;font:14px;" align="center"
			background="<c:url value='/images/icons/bgslice_glossydarkblue.jpg'/>">
		系统登录</td>
	</tr>
	<tr>
		<td>
		<table width="350" border="0" align="center" cellpadding="2"
			style="border:#9a9a9a solid 1px" cellspacing="2">

			<form name="loginForm" id="loginForm" method="post"
				action='<c:url value="/j_security_check"/>'>
			<tr>
				<td align="left">用户名:</td>
				<td align="left"><input name="j_username" id="j_username" type="text"
					style="width:100px" /></td>
				<td></td>
			</tr>

			<tr>
				<td align="left">密&nbsp;&nbsp;码:</td>
				<td align="left"><input name="j_password" id="j_password" type="password"
					style="width:100px" /></td>
				<td></td>
			</tr>
			<tr>
				<td valign="top" align="left">验证码:</td>
				<td align="left"><input type="text" name="j_captcha_response"
					style="width: 60px;" autocomplete="off"> 
					<img id="captcha" src="<c:url value="/captcha.jpg" />" align="top">
					&nbsp;&nbsp;
					<a href="#" style="color:#425E8D; text-decoration: none" 
					onclick="$('captcha').src='<c:url value="/captcha.jpg" />';">
					    [刷新图片]</a>
					</td>
				<td></td>
			</tr>

			<tr>
				<td colspan="3" align="left"><input type="checkbox" name="rememberme"
					style="border: 0px;margin:0px;padding:0px">保存我的信息</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="登录">
				</td>
			</tr>
			</form>

		</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>
