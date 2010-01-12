<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>编辑成员信息</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">编辑成员信息</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="${ctx}/member/index.do">
				成员列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center">
  <s:form id="save" action="save.do" method="post" theme="simple" validate="true">
	<s:hidden name="model.id" />
	<fieldset style="width: 610px; padding: 10px 10px 10px 10px;">
	<legend>成员信息</legend>
	<table width="600px" align="center">
		<tr>
			<td align="right" width="80">姓&nbsp;名：</td>
			<td align="left" width="520"><s:textfield id="name"
				name="model.name" cssStyle="width:400px" cssClass="required"/><font color="red">&nbsp;*</font>
			</td>
		</tr>
		<tr>
			<td align="right" width="80">单&nbsp;位：</td>
			<td align="left" width="520"><s:textfield id="dept"
				name="model.dept" cssStyle="width:400px" cssClass="required"/><font color="red">&nbsp;*</font>
			</td>
		</tr>
		<tr>
			<td align="right" width="80">职&nbsp;位：</td>
			<td align="left" width="520"><s:textfield id="jobs"
				name="model.jobs" cssStyle="width:400px" /></td>
		</tr>
		<tr>
			<td align="right" width="80">固&nbsp;话：</td>
			<td align="left" width="520"><s:textfield id="phone"
				name="model.phone" cssStyle="width:400px" /></td>
		</tr>
		<tr>
			<td align="right" width="80">手&nbsp;机：</td>
			<td align="left" width="520"><s:textfield id="mobile"
				name="model.mobile" cssStyle="width:400px" /></td>
		</tr>
		<tr>
			<td align="right" width="80">备&nbsp;注：</td>
			<td align="left" width="520"><s:textarea id="descn"
				name="model.descn" cssStyle="width:400px;height:100px" /></td>
		</tr>
	</table>
	</fieldset>
	<table width="100%" style="margin-bottom: 0px;">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td style="text-align: center;">
				<s:submit value="保存" cssClass="button" />&nbsp;&nbsp;
				<s:reset value="重置" cssClass="button" /></td>
		</tr>
	</table>
</s:form></div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>