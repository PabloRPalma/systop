<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>用户确认信息</title>
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
<div class="x-panel">
<div><%@ include file="/common/messages.jsp"%></div>
<table width="50%" align="center">
	<tr>
		<td height="5px"></td>
	</tr>
	<tr>
		<td align="center">以下是您填写的注册信息，请确认</td>
	</tr>
</table>
<s:form action="regist" theme="simple" id="save"
	method="POST">
	<s:hidden name="model.id" id="uId" />
	<s:hidden name="model.version" />
	<s:hidden name="model.status" />
	<s:hidden name="model.userType" />
	<s:hidden name="model.registTime" />
	<s:hidden name="model.password" />
	<table id="mytable" align="center">
		<tr>
			<td class="simple" width="280px" align="right">登录名：</td>
			<td class="simple">&nbsp;<s:textfield name="model.loginId" readonly="true"
				theme="simple" size="25" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">用户类别：</td>
			<td class="simple">&nbsp;
				<%= ((java.util.Map) request.getAttribute("userLevelMap")).get(request.getAttribute("model.level")) %>
				<s:hidden name="model.level" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">真实姓名：</td>
			<td class="simple">&nbsp;<s:textfield name="model.name" readonly="true"
				 theme="simple" size="25" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">性&nbsp;别：</td>
			<td class="simple">&nbsp;
				<%= ((java.util.Map) request.getAttribute("sexMap")).get(request.getAttribute("model.sex")) %>
				<s:hidden name="model.sex" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">学&nbsp;历：</td>
			<td class="simple">&nbsp;
				<%= ((java.util.Map) request.getAttribute("degreeMap")).get(request.getAttribute("model.degree")) %>
				<s:hidden name="model.degree" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">职务/职称：</td>
			<td class="simple">&nbsp;<s:textfield name="model.post" theme="simple" readonly="true"
				size="25" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">联系电话：</td>
			<td class="simple">&nbsp;<s:textfield name="model.mobile" readonly="true"
				 theme="simple" size="25" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">电子信箱：</td>
			<td class="simple">&nbsp;<s:textfield name="model.email" id="email" readonly="true"
				theme="simple" size="25" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">工作单位：</td>
			<td class="simple">&nbsp;<s:textfield name="model.unit" readonly="true"
				 theme="simple" size="25" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">行业内外：</td>
			<td class="simple">&nbsp;
				 <%= ((java.util.Map) request.getAttribute("industryMap")).get(request.getAttribute("model.industry")) %>
				<s:hidden name="model.industry" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">单位性质：</td>
			<td class="simple">&nbsp;
				<%= ((java.util.Map) request.getAttribute("unitkindMap")).get(request.getAttribute("model.unitKind")) %>
				<s:hidden name="model.unitKind" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">省份：</td>
			<td class="simple">&nbsp;
				<%= ((java.util.Map) request.getAttribute("provinceMap")).get(request.getAttribute("model.province")) %>
				<s:hidden name="model.province" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">通信地址：</td>
			<td class="simple">&nbsp;<s:textfield name="model.address" readonly="true"
				 theme="simple" size="25" /></td>
		</tr>
		<tr>
			<td class="simple" align="right">邮&nbsp;编：</td>
			<td class="simple">&nbsp;<s:textfield name="model.zip" readonly="true"
				 theme="simple" size="25" /></td>
		</tr>
	</table>
	<table width="100%" style="margin-bottom: 5px;">
		<tr>
			<td colspan="2" align="center" class="font_white"><s:submit
				value="确认" cssClass="button"></s:submit>&nbsp;&nbsp; 
				<input value="返回" onclick="javascript:history.go(-1)" type="button" class="button" /></td>
		</tr>
	</table>
</s:form></div>
<%@include file="/common/foot.jsp" %>
</body>
</html>