<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/inc/meta.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
	$("#commentForm").validate();
});
</script>

<style type="text/css">
#commentForm { width: 500px; }
#commentForm label { width: 250px; }
#commentForm label.error, #commentForm input.submit { margin-left: 253px; }
input[type=radio]{
margin:5px;
}
</style>
<title></title>
</head>
<body>
<table align="center">
<tr><td>
<form:form commandName="dataSourceInfo"
 id="commentForm" method="POST" action="/datasource/customdatasource/save.do">
    <fieldset>
		<legend>编辑数据源信息</legend>
		<table>
		   <tr>
		      <td width="200">数据库（必选）</td>
		      <td><form:radiobuttons path="type" items="${types}" cssStyle="border:0px;" /></td>
		   </tr>
		   <tr>
		      <td>JDBC URL (必填)</td>
		      <td><form:input path="url" cssClass="required"  cssStyle="width:200px;"/></td>
		   </tr>
		   <tr>
		      <td>数据库用户名 (必填)</td>
		      <td><form:input path="username" cssClass="required" cssStyle="width:200px;"/></td>
		   </tr>
		   <tr>
		      <td>数据库密码(可选)</td>
		      <td><form:input path="password" cssStyle="width:200px;"/></td>
		   </tr>
		   <tr>
		   <td colspan="2" align="center"><input type="submit" value="保存"></td>
		   </tr>
		   
		</table>
		
	</fieldset>
</form:form>
</td></tr>
</table>
</body>
</html>