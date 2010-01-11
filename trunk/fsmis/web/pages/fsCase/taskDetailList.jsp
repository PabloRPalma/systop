<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<title>任务列表</title>
</head>
<body>
<div><%@ include file="/common/messages.jsp"%></div>
<ec:table items="items" var="item" retrieveRowsCallback="limit"
	sortRowsCallback="limit" action="index.do" useAjax="false"
	doPreload="false" pageSizeList="5,10,20,50" editable="false"
	sortable="false" rowsDisplayed="5" generateScript="true"
	resizeColWidth="false" classic="false" width="100%" height="100px"
	minHeight="100"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="30" property="_n" title="No."
			value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center" />
		<ec:column width="180" property="task" title="执行部门"
			cell="com.systop.fsmis.fscase.webapp.ec.DeptsCell">
		</ec:column>
		<ec:column width="80" property="task.dispatchTime" cell="date"
			title="发布时间" format="yyyy-MM-dd HH:mm" style="text-align:center" />
		<ec:column width="80" property="task.presetTime" cell="date"
			title="规定完成时间" format="yyyy-MM-dd HH:mm" style="text-align:center" />
		<ec:column width="80" property="completionTime" cell="date"
			title="完成时间" format="yyyy-MM-dd HH:mm" style="text-align:center">
			<c:choose>
				<c:when test="${item.status == '3' or item.status == '4'}">
					<fmt:formatDate value="${item.completionTime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${item.remainDays >= 0}">剩余天数${item.remainDays}</c:when>
						<c:otherwise>逾期天数${-item.remainDays}</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</ec:column>
		<ec:column width="45" property="status" style="text-align:center"
			title="任务状态">
			<c:if test="${item.status == '0'}">
				<font color="red">未接收</font>
			</c:if>
			<c:if test="${item.status == '1'}">
				<font color="blue">已查看</font>
			</c:if>
			<c:if test="${item.status == '2'}">
				<font color="#FF9D07">处理中...</font>
				<%--
		    <c:if test="${item.singleTask.lastTime >= 0}">
		      剩余天数${item.singleTask.lastTime}
		    </c:if>
		    <c:if test="${item.singleTask.lastTime < 0}">
		      逾期天数${-item.singleTask.lastTime}
		    </c:if> --%>
			</c:if>
			<c:if test="${item.status == '3'}">
				<font color="gray">已退回</font>
			</c:if>
			<c:if test="${item.status == '4'}">
				<font color="green">处理完毕</font>
			</c:if>
		</ec:column>
		<ec:column width="50" property="_0" title="看"
			style="text-align:center" sortable="false">
			<a
				href="${ctx}/taskdetail/view.do?taskDetailId=${item.id}&fsCaseId=${item.task.fsCase.id}&modelId=${param['modelId']}">看</a>
			<a href="#">| 图</a>
		</ec:column>
		<ec:column width="170" property="_0" title="操作"
			style="text-align:center" sortable="false">
			<c:if test="${item.status == '0' or item.status == '1'}">
				<a
					href="${ctx}/taskdetail/receiveTask.do?model.id=${item.id}&isMultipleCase=${param['isMultipleCase']}&modelId=${param['modelId']}">收
				|</a>
				<%--<a href="${ctx}/taskdetail/toReturnTaskDetail.do?model.id=${item.id}">退回1|</a> --%>
				<a href="#"
					onclick="javascript:returnTaskDetail(${item.id},'${userName}')">退
				|</a>
			</c:if>
			<c:if test="${item.status == '2'}">
				<a
					href="${ctx}/taskdetail/toDealWithTaskDetail.do?model.id=${item.id}&isMultipleCase=${param['isMultipleCase']}&modelId=${param['modelId']}">处理|</a>
			</c:if>

			<a
				href="${ctx}/taskdetail/printTaskDetail.do?model.id=${item.id}&isMultipleCase=${param['isMultipleCase']}&modelId=${param['modelId']}"
				target="_blank">印</a>
		</ec:column>
	</ec:row>
</ec:table>
</body>
</html>