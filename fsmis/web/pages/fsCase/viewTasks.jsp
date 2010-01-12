<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<div id="taskDiv" style="margin: -1">
<c:forEach items="${model.taskses}" var="task"
	varStatus="varStatus">
	<div id="taskDiv${varStatus.index+1}" class="x-hide-display">
	<table id="mytable" align="left" style="border-bottom: 0">
		<tr>
			<td>			
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
			</td>
		</tr>
		<tr>
			<td>					
			<s:action name="getTaskDetailsByTaskId" namespace="/taskdetail"  executeResult="true" >
				<s:param name="taskId">${task.id}</s:param>
			</s:action>			
			</td>
		</tr>
	</table>
	</div>
</c:forEach></div>
<script type="text/javascript">
<c:if test="${not empty model.taskses }">	
	Ext.onReady(function() {
		var taskDiv = new Ext.TabPanel( {
			renderTo : 'taskDiv',
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
</c:if>
</script>