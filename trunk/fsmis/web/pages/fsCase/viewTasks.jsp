<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>

<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
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
	height: 26;
}
</style>
</head>
<body>
<div id="tabs2"><c:forEach items="${model.taskses}" var="task"
	varStatus="varStatus">
	<div id="taskDiv${varStatus.index+1}" class="x-hide-display">
	<table id="mytable" align="left" style="border-bottom: 0">
		<tr>
			<td>
			<fieldset style="width: 800px; padding: 0px 0px 0px 0px;">
			<legend>任务${varStatus.index+1}信息</legend>
			<table width="800px" align="left">
				<tr>
					<td align="right" width="15%">任务标题：</td>
					<td colspan="4" align="left">${task.title }</td>
				</tr>
				<tr>
					<td align="right" width="12%">派遣时间：</td>
					<td width="12%"><fmt:formatDate value="${task.dispatchTime}"
						pattern="yyyy-MM-dd HH:mm" /></td>

					<td align="right" width="12%">规定完成时间：</td>
					<td width="12%"><fmt:formatDate value="${task.presetTime}"
						pattern="yyyy-MM-dd HH:mm" /></td>
					<td align="right" width="8%">事件状态：</td>
					<td width="15%"><c:if test="${task.status == '0'}">
						<font color="red">未派遣</font>
					</c:if> <c:if test="${task.status == '1'}">
						<font color="#FF9D07">已派遣</font>
					</c:if> <c:if test="${task.status == '2'}">
						<font color="green">已处理</font>
					</c:if> <c:if test="${task.status == '3'}">
						<font color="gray">已回退</font>
					</c:if> <c:if test="${task.status == '4'}">
						<font color="blue">已核实</font>
					</c:if></td>
				</tr>
				<tr>
					<td align="right" rowspan="4">任务描述：</td>
					<td rowspan="4" colspan="5"><textarea id="taskDescn"
						style="border: 1px solid #D4D0C8;" name="task.descn" cols="80"
						rows="3" readonly="readonly">${task.descn}</textarea></td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td>		
			<fieldset
				style="width: 800px; height: 160px; padding: 0px 0px 0px 0px;">
			<legend>任务${varStatus.index+1}明细</legend>
			<s:action name="getTaskDetailsByTaskId" namespace="/taskdetail"  executeResult="true" >
				<s:param name="taskId">${task.id}</s:param>
			</s:action>			
			</fieldset>
			</td>
		</tr>
	</table>
	</div>
</c:forEach></div>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs2 = new Ext.TabPanel( {
			renderTo : 'tabs2',
			anchor : '100% 100%',
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ 
			
					<c:forEach items="${model.taskses}" var="task" varStatus="varStatus">
						<c:if test="${varStatus.index>0}">,</c:if>
						{		contentEl : 'taskDiv${varStatus.index+1}',
							title : '任务${varStatus.index+1}'
						} 
					</c:forEach>					
					]
		});
	});	
</script>
</body>
</html>