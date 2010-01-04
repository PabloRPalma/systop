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
<div class="x-panel-header">内部消息</div>
<div class="center">
<fieldset style="width: 800px; padding-top: 2px">
  <legend>内部消息</legend>
	<s:hidden name="model.id"/>
	<table width="800px" align="center" border="0" cellpadding="3" cellspacing="3">
		<tr>
			<td align="right" width="70">发信人：</td>
			<td align="left">
				<s:textfield id="content" name="model.sender.name" cssStyle="width:200px;" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="70">收信人：</td>
			<td align="left">
				<s:textfield id="content" name="model.receiver.name" cssStyle="width:200px;" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="70">创建时间：</td>
			<td align="left">
				<s:textfield id="content" name="model.createTime"  cssStyle="width:200px;" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="70">接收时间：</td>
			<td align="left">
				<s:textfield id="content" name="model.receiveTime" cssStyle="width:200px;" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="70">收信人：</td>
			<td align="left">
				<s:textarea id="content" name="model.receiver.name" cssStyle="width:500px;" readonly="true"/>
			</td>
		</tr>
	</table>
</fieldset>
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