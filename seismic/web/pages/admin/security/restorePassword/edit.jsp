<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<%@include file="/common/top.jsp" %>
<table width="1002" align="center" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body">
<table align="center" width="1002"><tr><td>
<fieldset>
   <legend><img src="${ctx}/images/icons/password_title.gif" border="0"/></legend>
   <s:form action="sendPasswordMail" validate="true"  method="POST">
<table align="center" width="1002">
	<tr>
		<td align="right">请输入您的用户名：
		</td>
		<td>
		<s:textfield size="30" name="loginId" cssStyle="border:1px #cecece solid"></s:textfield>
		</td>
	</tr>
    <tr>
		<td align="right">
		请输入您注册的时候登记的电子信箱：
		</td>
		<td>
		<s:textfield size="30" name="emailAddr" cssStyle="border:1px #cecece solid"></s:textfield>
		</td>
	</tr>
	
	<tr>
	   <td></td>
	   <td>
	   <input type="submit" value="发送" class="button">
	   </td>
	</tr>
</table>
</s:form>
</fieldset></td></tr></table>
</div>

</body>
</html>