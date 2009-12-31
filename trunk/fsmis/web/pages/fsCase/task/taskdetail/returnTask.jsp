<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
</head>
<body>
<div id="winReturnTaskDetail" class="x-hidden">
<div class="x-window-header">退回任务</div>
<div id="returnTaskDetail">
<div align="center">
<script type="text/javascript">
	$(document).ready(function() {
	$("#teturnTaskDetailFrm").validate();
});
</script>
<s:form action="doReturnTaskDetail.do" id="teturnTaskDetailFrm" method="post" theme="simple" validate="true" >
	<s:hidden name="model.id" />
	<fieldset style="width: 510px; padding: 10px 10px 10px 10px;">
	<legend>退回信息</legend>
	<table width="500px" align="center">
	    <tr>
			<td align="right" width="120">负责人：</td>
			<td align="left" width="300">
			  <s:textfield id="people" name="model.returnPeople" cols="50"  cssClass="required"/><font color="red">需单位主要负责同志确认</font>
			</td>
		</tr>
		<tr>
			<td align="right" width="120">退回原因：</td>
			<td align="left" width="420"><s:textarea id="reason" cssClass="required" name="model.returnReason" cols="50" rows="15"/><font color="red">*</font></td>
		</tr>
		
	</table>
	</fieldset>
<table width="600px" style="margin-bottom: 10px;">
<tr>
<td style="text-align: center;"><s:submit value="退回" cssClass="button" />
				<s:reset value="重置" cssClass="button"/></td>
</tr>
</table>
</s:form></div>
</div>
</div>
<script type="text/javascript"
	src="${ctx}/pages/fsCase/task/taskdetail/returnTask.js">	
</script>
</body>
</html>