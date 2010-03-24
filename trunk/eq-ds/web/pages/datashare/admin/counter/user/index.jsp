<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">注册用户统计</div>
<div class="x-toolbar">
<table width="99%">
	<td align="right">
	<table>
		<tr>
			<td><a href="${ctx}/datashare/admin/counter/user/index.do"><img
				src="${ctx}/images/icons/house.gif" /> 注册用户统计首页</a></td>
		</tr>
	</table>
	<td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
    action="index.do" 
    useAjax="true"
	doPreload="false" 
	maxRowsExported="1000" 
	pageSizeList="5,10,20,100"
	editable="false" 
	sortable="true" 
	rowsDisplayed="10"
	generateScript="true" 
	resizeColWidth="true" 
	classic="false"
	width="100%" 
	height="277px" 
	minHeight="200"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" />
		<ec:column width="140" property="_1" title="用户类型">
			<s:if test='#attr.item.level=="ROLE_NORMAL"'>普通用户</s:if>
			<s:elseif test='#attr.item.level=="ROLE_SENIOR"'>高级用户</s:elseif>
		</ec:column>
		<ec:column width="140" property="c" title="注册用户数" calc="total" calcSpan="2" calcTitle="注册总人数"/>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>