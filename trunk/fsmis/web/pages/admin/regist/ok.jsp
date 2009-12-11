<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
<!--
.Message {
	color: #FF0000;
	font-style: italic;
}

#mytable {
	border: 1px solid #A6C9E2;
	width: 50%;
	border-collapse: collapse;
}

#mytable td {
	border: 1px solid #A6C9E2;
	height: 26px;
}
.menu {
	font-size: 14px;
	font-weight: bold;
	color: #204c89;
}
a:hover{
	text-decoration:none;color:#fd0100;
} 
a{text-decoration:none;color:#286e94;
}
-->
</style>
</head>
<body>
<%@include file="/common/top.jsp" %>
<table align="center" border="0" cellspacing="0" cellpadding="0" width="1003">
	<tr>
    <td width="1003" height="35" align="left" valign="middle" background="${ctx}/ResRoot/index/images/index_02.gif">
    &nbsp;&nbsp;&nbsp;&nbsp;<span class="menu"><a href="${ctx}/index.shtml">首页</a></span>&nbsp;&nbsp;&nbsp;<span class="menu">用户注册</span>
  	</td>
  </tr>
</table>
<table align="center" width="1003" height="300px" cellspacing="0" cellpadding="0" border="0">
<tr><td>

<table width="80%" align="center" style="margin: 5px">
	<tr>
		<td height="10px"></td>
	</tr>
	<tr>
		<td align="center" height="10px"></td>
	</tr>
	<tr>
		<td align="left" valign="top">恭喜！您已经注册成功，请等待系统管理员审核，审核结果会发送到您注册的邮箱，请及时查看。</td>
	</tr>
</table>
</td></tr></table>

</body>
</html>