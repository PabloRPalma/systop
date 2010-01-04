<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件处理结果</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
	function jsonFormValue() {
		var num = document.getElementsByName('jsonValue').length;
		var val = document.getElementsByName('jsonValue')[0].value;
		for(i=1; i<num; i++) {
			val = val + ":" + document.getElementsByName('jsonValue')[i].value;
		}
		//alert(val);
		var caseId = document.getElementById('caseId').value;
		var groupId = document.getElementById('groupId').value;
		$.ajax({
			url: '${ctx}/urgentcase/saveGroupResult.do',
			type: 'post',
			dataType: 'json',
			data: {rstValue : val, caseId : caseId, groupId : groupId},
			success: function(rst, textStatus){
				window.location = '${ctx}/urgentcase/index.do';
			}
	  	 });
	}
</script>
<style type="text/css">
#mytable {
	border: 0px solid #A6C9E2;
	margin-left: 0px;
	margin-top: 0px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">编辑处理结果</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="${ctx}/urgentcase/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 返回应急事件</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
	<div id="tabs">
	<div id="basic">
	<s:hidden id="caseId" name="caseId" />
	<s:hidden id="groupId" name="groupId" />
	<table id="mytable" height="360" style="margin-top: 5px">

		<c:forEach var="entry" items="${resultMap}" begin="0" end="4"
			step="1" varStatus="status">
			<!--  
			<font size=5 color=red>
				ID:${entry.key }<br>
				Name:${entry.value }<br>
			</font>
			-->
			<tr>
				<td align="right" width="215">${entry.key }：</td>
				<td align="left" colspan="3">
					<s:textfield name="jsonValue" cssStyle="width:500px" />
					<!--  
					<c:if test="${entry.value == null}">
						<s:textfield id="title" cssStyle="width:500px" />
					</c:if>
					<c:if test="${entry.value != null}">
						${entry.value }
					</c:if>
					-->
				</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	</div>
	<table width="100%" style="margin-bottom: 0px;">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td style="text-align: center;"> 
				<a href="#" onclick="jsonFormValue()">保存</a></td>
		</tr>
	</table>
</div>
</body>
</html>