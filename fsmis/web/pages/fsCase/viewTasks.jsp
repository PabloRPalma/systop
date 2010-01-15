<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="taskDiv" style="margin: -1">
<c:forEach items="${model.taskses}" var="task"	varStatus="taskStatus">
	<div id="taskDiv${taskStatus.index+1}" class="x-hide-display">
		<div>
		<table class="mytable" >
				<tr>
					<td align="right" width="6%" >任务标题：</td>
					<td colspan="5" align="left" >${task.title }</td>
				</tr>
				<tr>
					<td align="right" width="6%">派遣时间：</td>
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
					<td align="right" >任务描述：</td>
					<td colspan="5">
					<div style="overflow: auto;">
						${task.descn}
					</div>
					</td>
				</tr>
			</table>
		</div>
		<div >
			<!-- 任务明细信息的Ext GridPanel -->				
			<%@include file="viewTaskDetailsGrid.jsp" %>
		</div>
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
			
					<c:forEach items="${model.taskses}" var="task" varStatus="taskStatus">
						<c:if test="${taskStatus.index>0}">,</c:if>
						{		contentEl : 'taskDiv${taskStatus.index+1}',
							title : '任务${taskStatus.index+1}'
						} 
					</c:forEach>					
					]
		});
		
	});	
</c:if>
</script>