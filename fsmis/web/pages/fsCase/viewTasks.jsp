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
<script type="text/javascript"
	src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
</head>
<body>
<div id="tabs2"><c:forEach items="${model.taskses}" var="task"
	varStatus="varStatus">
	<div id="taskDiv${varStatus.index+1}" class="x-hide-display">
	<table id="mytable" align="left">
		<tr>
			<td>

			<fieldset style="width: 800px; padding: 5px 10px 5px 10px;">
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
				style="width: 800px; height: 100px; padding: 5px 10px 5px 10px;">
			<legend>任务${varStatus.index+1}明细</legend>
			<table width="800px" align="left">
				<tr>
					<td width="15%" align="center">No.</td>
					<td width="15%" align="center">部门名称</td>
					<td width="15%" align="center">派遣时间</td>
					<td width="15%" align="center">完成时间</td>
					<td width="15%" align="center">任务状态</td>
				</tr>
				<c:forEach items="${task.taskDetails}" var="detail"
					varStatus="varStatus">
					<tr>
						<td width="15%" align="center">${varStatus.index+1}</td>
						<td width="15%" align="center">${detail.dept.name }</td>
						<td width="15%" align="center"><fmt:formatDate
							value="${task.dispatchTime }" pattern="yyyy-MM-dd HH:mm" /></td>
						<td width="15%" align="center">
						<c:choose>
							<c:when test="${detail.status == '3' or detail.status == '4'}">
								<fmt:formatDate value="${detail.completionTime}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${detail.remainDays >= 0}">剩余天数${detail.remainDays}</c:when>
									<c:otherwise>逾期天数${-detail.remainDays}</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose></td>
						<td width="15%" align="center"><c:if
							test="${detail.status == '0'}">
							<font color="red">未接收</font>
						</c:if> <c:if test="${detail.status == '1'}">
							<font color="#FF9D07">已查看</font>
						</c:if> <c:if test="${detail.status == '2'}">
							<font color="green">已接收</font>
						</c:if> <c:if test="${detail.status == '3'}">
							<font color="gray">已退回</font>
						</c:if> <c:if test="${detail.status == '4'}">
							<font color="blue">已处理</font>
						</c:if></td>
					</tr>
				</c:forEach>
			</table>
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
<script type="text/javascript">
//文本编辑组件
function preFckEditor1(){
	var fckEditor = new FCKeditor( 'taskDescn' ) ;
  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
  fckEditor.ToolbarSet = 'BasicA';
  fckEditor.Height = 360;
  fckEditor.ReplaceTextarea();
}
</script>
</body>
</html>