<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>通知部门记录</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>

</head>
<body>
<div class="x-panel">
<div class="x-panel-header">部门接收通知列表</div>
<div class="x-toolbar" style="padding-left: 20px">
<table width="99%">
	<tr>
		<td align="left">
			<s:form action="index" method="post">
						标题：
			<s:textfield name="title"></s:textfield>	
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form>
		</td>
		<td align="right">
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	action="index.do" useAjax="false"
	doPreload="false" 
	pageSizeList="20" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="true" 
	classic="false" 
	width="100%" height="360px"
	minHeight="360"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align: center" />
		<ec:column width="150" property="notice.title" title="标题" />
		<ec:column width="80" property="notice.publisher.name" title="发布人" style="text-align:center"/>
		<ec:column width="80" property="notice.pubDept.name" title="发布部门" style="text-align:center"/>
		<ec:column width="120" property="notice.createTime" title="发布时间" style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm"/>
		<ec:column width="120" property="receiveDate" title="接收时间" style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" />
		<ec:column width="80" property="_7" title="状态" style="text-align:center">
			<c:if test="${item.isNew == '1'}"><font color="#C0C0C0">未读</font></c:if>
			<c:if test="${item.isNew != '1'}"><font color="green">已读</font></c:if>
		</ec:column>
		<ec:column width="100" property="_0" title="操作" style="text-align:center" sortable="false">
			<a  href="view.do?model.id=${item.id}" target="_blank">查看</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>