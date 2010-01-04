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
<div class="x-panel">
<div class="x-panel-header">协调指挥&nbsp;&gt;&nbsp;单体任务接收&nbsp;&gt;&nbsp;任务列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="index" method="post">
           		        任务标题：
			<s:textfield name="model.task.title"></s:textfield>
			                   派发时间:开始
			<input type="text" name="taskBeginTime" style="width: 140px"
				value='<s:date name="taskBeginTime" format="yyyy-MM-dd HH:mm:ss"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				class="Wdate" />
			                    结束:
			<input type="text" name="taskEndTime" style="width: 140px"
				value='<s:date name="taskEndTime" format="yyyy-MM-dd HH:mm:ss"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				class="Wdate" />
           			任务状态:
			<s:select name="model.status" list="stateMap" headerKey="" headerValue="--请选择--" >
			
			</s:select>
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
		<td align="right"><a href="${ctx}/taskdetail/index.do"><img
			src="${ctx}/images/icons/house.gif" />返回事件列表</a></td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	 action="index.do" 
	 useAjax="false" 
	 doPreload="false" 
	 pageSizeList="20,50,100,200" 
	 editable="false" 
	 sortable="false" 
	 rowsDisplayed="20" 
	 generateScript="true" 
	 resizeColWidth="false" 
	 classic="false" 
	 width="100%" 
	 height="460px" 
	 minHeight="460" 
	 toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_n" title="No."
			value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center" />
		<ec:column width="300" property="task.title" title="任务标题" />
		<ec:column width="200" property="task" title="执行部门" cell="com.systop.fsmis.fscase.webapp.ec.DeptsCell">
			
		</ec:column>
		<ec:column width="80" property="task.dispatchTime" cell="date"
			title="发布时间" format="yyyy-MM-dd" style="text-align:center" />
		<ec:column width="80" property="completionTime" cell="date"
			title="完成时间" format="yyyy-MM-dd" style="text-align:center" />
		<ec:column width="80" property="task.presetTime" cell="date"
			title="规定完成时间" format="yyyy-MM-dd" style="text-align:center" />
		<ec:column width="100" property="status" style="text-align:center"
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
		<ec:column width="150" property="_0" title="查看"
			style="text-align:center" sortable="false">
			<a	href="${ctx}/taskdetail/view.do?taskDetailId=${item.id}&fsCaseId=${item.task.fsCase.id}&modelId=${param['modelId']}">查看|</a>		
				<a href="#">地图位置</a>	
		</ec:column>
				<ec:column width="150" property="_0" title="操作"
			style="text-align:center" sortable="false">
			<c:if test="${item.status == '0' or item.status == '1'}">
				<a href="${ctx}/taskdetail/receiveTask.do?model.id=${item.id}">接收|</a>
				<%--<a href="${ctx}/taskdetail/toReturnTaskDetail.do?model.id=${item.id}">退回1|</a> --%>
				<a href="#" onclick="javascript:returnTaskDetail(${item.id},'${userName}')">退回|</a>
			</c:if>
			<c:if test="${item.status == '2'}">
				<a href="${ctx}/taskdetail/toDealWithTaskDetail.do?model.id=${item.id}">处理|</a>
				<a href="#" onclick="dealWithTaskDetail(${item.id})">处理2|</a>
			</c:if>
		
			<a href="#" target="_blank">打印</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
<jsp:include page="returnTaskDetail.jsp"></jsp:include>
</body>
</html>