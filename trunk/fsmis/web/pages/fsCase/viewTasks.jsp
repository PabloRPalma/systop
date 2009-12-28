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
	<table id="mytable" height="320">
		<tr>
			<td>
			<div style="line-height: 20px; padding: 2px 2px 2px 2px;">
			<div>
			<fieldset style="width: 800px; padding: 2px 2px 2px 2px;">
			<legend>任务${varStatus.index+1}信息</legend>
			<table width="800px" align="left">
				<tr>
					<td align="right" width="20%">任务标题：</td>
					<td align="left" colspan="3">${task.title }</td>
				</tr>

				<tr>
					<td align="right" width="10%">派遣时间：</td>
					<td align="left" width="20%"><s:date name="task.dispatchTime"
						format="yyyy-MM-dd HH:mm:ss" /></td>

					<td align="right" width="10%">规定完成时间：</td>
					<td align="left" width="20%"><s:date name="task.presetTime"
						format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<td align="right" width="10%">事件状态：</td>
					<td align="left" colspan="3"><c:if
						test="${task.status == '0'}">
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
					<td align="right" width="10%">任务描述：</td>
					<td align="left" colspan="3"><textarea id="descn"
						disabled="disabled" name="task.descn" cols="60" rows="4">${task.descn}</textarea></td>
				</tr>
			</table>
			</fieldset>

			</div>
			</div>

			<div>
			<fieldset style="width: 800px; padding: 2px 2px 2px 2px;">
			<legend>任务明细</legend>
			<table width="800px" align="left">
			<tr>
				<td>No.</td>
				<td>部门名称</td>
				<td>派遣时间</td>
				<td>完成时间</td>
				<td>任务状态</td>
			</tr>
			<c:forEach items="${task.taskDetails}" var="detail" varStatus="varStatus">
			<tr>
			<td>${varStatus.index+1}</td>
			<td>${detail.dept.name }</td>
			<td>
			${task.dispatchTime }
			
			</td>
			<td>${detail.completionTime}</td>
			<td>
			<c:if test="${detail.status == '0'}">
						<font color="red">未接收</font>
					</c:if> <c:if test="${detail.status == '1'}">
						<font color="#FF9D07">已查看</font>
					</c:if> <c:if test="${detail.status == '2'}">
						<font color="green">已接收</font>
					</c:if> <c:if test="${detail.status == '3'}">
						<font color="gray">已退回</font>
					</c:if> <c:if test="${detail.status == '4'}">
						<font color="blue">已处理</font>
					</c:if>
			</td> 
			</tr>
			</c:forEach>
			</table>
			</fieldset>
			</div>
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