<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>任务列表</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
function remove(id){
	if (confirm("确认要删除此任务吗?")){
		url="${ctx}/task/remove.do?model.id=" + id;
		window.open(url,'main');
	}
}

</script>
</head>
<body>

<div class="x-panel">
<div class="x-panel-header">协调指挥&nbsp;&gt;&nbsp;任务列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="index" method="post">
           		    任务关键字：
			<s:textfield name="model.title"></s:textfield>
           			任务状态:
			<s:select name="model.status" list="stateMap" headerKey="" headerValue="--请选择--" >
			
			</s:select>
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
		<td align="right"><a href="${ctx}/task/index.do"><img
			src="${ctx}/images/icons/house.gif" />返回事件列表</a></td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center"><ec:table
	items="items" var="item" retrieveRowsCallback="limit"
	sortRowsCallback="limit" action="index.do" useAjax="false"
	doPreload="false" pageSizeList="20,50,100,200" editable="false"
	sortable="false" rowsDisplayed="20" generateScript="true"
	resizeColWidth="false" classic="false" width="100%" height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_s" title="No."
			value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center" />
		<ec:column width="290" property="title" title="任务标题" sortable="false" />
		<ec:column width="80" property="time" title="派发时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd" />
		<ec:column width="80" property="endTime" title="规定完成时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd" />
		<ec:column width="220" property="_sto" title="执行部门" sortable="false">
			<c:forEach var="std" items="${item.taskDetails}">
				【${std.dept.name}】
			</c:forEach>
		</ec:column>
		<ec:column width="130" property="_status" title="任务状态"
			style="text-align: center" sortable="false">
			<c:if test="${item.status == '0'}">
				<font color="red">未接收</font>
			</c:if>
			<c:if test="${item.status == '1'}">
				<font color="blue">已派遣</font>
			</c:if>
			<c:if test="${item.status == '2'}">
				<font color="green">已处理</font>
			</c:if>
			<c:if test="${item.status == '3'}">
				<font color="gray">已退回</font>
			</c:if>
		   &nbsp;
		  
		</ec:column>
		<ec:column width="50" property="_o" title="操作" sortable="false"
			style="text-align: center">
			<a href="${ctx}/task/view.do?model.id=${item.id}">
			查看 |
			<c:if test="${item.status != '2'}">
				<a href="#" onclick="remove(${item.id})"> 删除 </a>
			</c:if>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>