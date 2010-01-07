<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件审核记录</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">应急事件审核记录</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>&nbsp;事件名称：${model.title}</td>
		<td align="right">
		  <table>
			<tr>
				<td align="right"><a href="${ctx}/urgentcase/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 应急事件列表</a></td>
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
	action="listCheckResult.do" 
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
		<ec:column width="100" property="checker.name" title="审核人" style="text-align:center"/>
		<ec:column width="120" property="checkTime" title="审核时间" style="text-align:center" format="yyyy-MM-dd HH:mm" cell="date"/>
		<ec:column width="70" property="isAgree" title="是否同意" style="text-align:center">
			<c:if test="${item.isAgree == '1'}"><font color="green">同意</font></c:if>
		    <c:if test="${item.isAgree == '0'}"><font color="red">不同意</font></c:if>
		</ec:column>
		<ec:column width="500" property="result" title="具体意见" sortable="false"/>
	</ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>