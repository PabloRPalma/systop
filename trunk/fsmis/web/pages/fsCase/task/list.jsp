<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>任务列表</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<script type="text/javascript">
function remove(id){
		Ext.MessageBox.confirm('提示', '确认要删除此任务吗?删除后不能恢复!    ', function(btn){
			if(btn == 'yes'){
				location.href="${ctx}/task/remove.do?model.id=" + id+"&modelId=1&isMultipleCase=${isMultipleCase}";	
			} 
		});
}

</script>
</head>
<body>
<div><%@ include file="/common/messages.jsp"%></div>
<div class="x-panel">
<div class="x-panel-header">协调指挥&nbsp;&gt;&nbsp;${param['isMultipleCase']
eq 0?'单体任务':'多体任务'}管理&nbsp;&gt;&nbsp;${param['isMultipleCase'] eq
0?'单体任务':'多体任务'}列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="index" method="post">
			<s:hidden name="isMultipleCase"></s:hidden>
           		        任务标题：
			<s:textfield name="model.title" cssStyle="width:100px"></s:textfield>
			                   派发时间:
			<input type="text" name="taskBeginTime" style="width: 120px"
				value='<s:date name="taskBeginTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
			                    至:
			<input type="text" name="taskEndTime" style="width: 120px"
				value='<s:date name="taskEndTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
           			任务状态:
			<s:select name="model.status" list="stateMap" headerKey=""
				headerValue="--请选择--">

			</s:select>
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" 
	var="item" 
	retrieveRowsCallback="limit"
	sortRowsCallback="limit" 
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
		<ec:column width="40" property="_s" title="No."
			value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center" />
		<ec:column width="230" property="title" tipTitle="${item.title}" title="任务标题" sortable="false" />
		<ec:column width="100" property="dispatchTime" title="派发时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" />
		<ec:column width="100" property="presetTime" title="规定完成时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" />
		<ec:column width="100" property="closedTime" title="完成时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm">
			<c:if test="${item.status eq '1'}">
			<c:choose>
						<c:when test="${item.remainDays >= 0}">剩余天数${item.remainDays}</c:when>
						<c:otherwise>逾期天数${-item.remainDays}</c:otherwise>
					</c:choose>
			</c:if>	
		</ec:column>
		<ec:column width="140" property="taskDetails" title="执行部门"
			cell="com.systop.fsmis.fscase.webapp.ec.DeptsCell">
		</ec:column>
		<ec:column width="60" property="_status" title="任务状态"
			style="text-align: center" sortable="false">
			<c:if test="${item.status == '0'}"><font color="red">未接收</font></c:if>
			<c:if test="${item.status == '1'}"><font color="blue">已派遣</font></c:if>
			<c:if test="${item.status == '2'}"><font color="green">已处理</font>	</c:if>
			<c:if test="${item.status == '3'}"><font color="gray">已退回</font></c:if>  
		</ec:column>
		<ec:column width="83" property="_o" title="操作" sortable="false" style="text-align: center">
			<a title="查看任务" href="${ctx}/fscase/view.do?fsCaseId=${item.fsCase.id}&modelId=${modelId}&taskId=${item.id}">
			看</a> |
			<%--a title="修改任务" href="${ctx}/task/modify.do?model.id=${item.id}&modelId=1&isMultipleCase=${isMultipleCase}">改</a> | --%>
			<a href="#" title="删除任务" onclick="remove(${item.id})">删 </a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>