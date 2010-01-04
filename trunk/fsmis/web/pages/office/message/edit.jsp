<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>内部消息管理</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">发送内部消息</div>
<div class="center"><s:form action="save.do" id="save" method="post" theme="simple" validate="true">
<fieldset style="width: 800px; padding-top: 2px">
  <legend>编辑内部消息</legend>
	<s:hidden name="model.id"/>
	<table width="800px" align="center" border="0" cellpadding="3" cellspacing="3">
		<tr>
			<td align="right" width="70">收信人：</td>
			<td align="left">
				<s:select name="model.receiver.id" listKey="id" listValue="name" list="ReceiverList" headerKey=""  headerValue="选择收信人..." cssStyle="width:200px"></s:select>
			</td>
		</tr>
		<tr>
			<td align="right">内容：</td>
			<td align="left">
			<s:textarea id="content" name="model.content" cssStyle="width:550px;" />
		</tr>
	</table>
</fieldset>
<table width="500" align="center">
	<tr>
		<td style="text-align: center;" colspan="2">
			<s:submit value="保存" cssClass="button"/> 
			<s:reset value="重置" cssClass="button"/>
		</td>
	</tr>
</table>
</s:form>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</body>
</html>