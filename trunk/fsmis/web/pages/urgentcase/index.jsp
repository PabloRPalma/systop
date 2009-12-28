<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
/**
 * 删除提交
 */
function remove(id){
	if (confirm("确认要删除所选择的事件信息吗?")){
		var from = document.getElementById("ec");
		var url ="${ctx}/emergency/event/removeEmgcEvent.do?model.id="+id;
		window.open(url,'main');
	}
}

function print(id){
	var url = "${ctx}/emergency/event/printEmgcApply.do?model.id=" + id;
	window.open(url);
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">应急事件管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		<form action="${ctx}/urgentcase/index.do" method="post">
			 事件名称：
		    <s:textfield name="model.title"></s:textfield>
			<s:submit value="查询" cssClass="button"></s:submit>
		</form>
		</td>
		<td align="right">
		  <table>
			<tr>
				<td><a href="edit.do" title="添加应急事件"><img
					src="${ctx}/images/icons/add.gif" /></a></td>
				<td valign="middle"><a href="edit.do" title="添加应急事件">添加</a></td>
			</tr>
		  </table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" 
	var="item" 
	retrieveRowsCallback="process"
	sortRowsCallback="process" 
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
	    <ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
		<ec:column width="180" property="title" title="事件名称" />
		<ec:column width="180" property="address" title="事发地点"/>
		<ec:column width="110" property="caseTime" title="事发时间" style="text-align:center"  parse="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" cell="date"/>
		<ec:column width="70" property="plansLevel" title="预案等级"/>
		<ec:column width="60" property="status" title="状态" style="text-align:center">
		    <c:if test="${item.status == null}"><font color="#990099">未审核</font></c:if>
		    <c:if test="${item.status == '0'}"> <font color="red">未派遣</font></c:if>
			<c:if test="${item.status == '1'}"> <font color="#CED513">已派遣</font></c:if>
			<c:if test="${item.status == '2'}"> <font color="blue">已处理</font></c:if>
			<c:if test="${item.status == '4'}"> <font color="green">已核实</font></c:if>
		</ec:column>
		<ec:column width="60" property="status" title="查看" style="text-align:center">
		</ec:column>
		<ec:column width="130" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="${ctx}/urgentcase/edit.do?model.id=${item.id}">编辑</a> | 
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}">查看</a> |
			<a href="#" onclick="remove(${item.id})">删除</a>
		</ec:column>
	</ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>