<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function goback(){
	history.go(-1);
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">错识信息</div>
<div class="x-toolbar">&nbsp;</div>
<div align="center"><s:form action="#" method="post"
	theme="simple">
	<s:hidden name="model.id" />
	<fieldset style="width: 510px; padding: 130px 10px 10px 10px;">
	<legend>错误信息</legend>
	<table width="500px" align="center">
		<tr>
			<td colspan="2"><%@ include file="/common/messages.jsp"%>
			</td>
		</tr>
		<tr>
			<td align="center">
				<a href="#" onclick="goback()">返回</a>
			</td>
		</tr>
	</table>
	</fieldset>
</s:form></div>
</div>
</body>
</html>