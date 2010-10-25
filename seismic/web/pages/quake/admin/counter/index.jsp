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
<div class="x-panel-header">网站统计</div>
<div class="x-toolbar">
<table width="99%">
	<td align="right">
	<table>
		<tr>
			<td><a href="${ctx}/quake/admin/counter/index.do"><img
				src="${ctx}/images/icons/house.gif" /> 网站统计首页</a></td>
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
		<ec:column width="140" property="name" title="统计项名称" />
		<ec:column width="140" property="pattern" title="URL匹配路径" />
		<ec:column width="140" property="hits" title="点击次数据" />
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>