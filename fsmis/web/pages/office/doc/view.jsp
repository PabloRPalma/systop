<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div class="x-panel">
<div align="center">
	<table width="800px" align="center" cellpadding="3" cellspacing="5">
		<tr>
			<td align="center" colspan="2" style="padding-top: 20px"><h1>${model.title }</h1></td>
		</tr>
		<tr>
			<td align="right">作者：</td>
			<td align="left">${model.author }</td>
		</tr>
		<tr>
			<td align="right">所属栏目：</td>
			<td align="left"><s:property value="model.documentType.name"/></td>
		</tr>
		<tr>
			<td align="left" colspan="2" style="border-top: 1px solid black; padding-top: 5px;">
				${model.content}
			</td>
		</tr>
	</table>
</div>
</div>
</body>
</html>