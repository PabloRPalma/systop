<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<title>栏目管理</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">

/**
 * 删除
 */
function removeDocType(cID){
	if(confirm("确实要删除该栏目吗？")){
		location.href = "remove.do?model.id=" + cID;
	}
}

function showArticles(cID){
	location.href = "indexArticles.do?model.id=" + cID;
}

</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">栏目管理列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td></td>
		<td align="right">
		<table>
			<tr>
				<td><span class="ytb-sep"></span></td>
				<td><a href="edit.do"><img
					src="${ctx}/images/icons/add.gif" /> 添加栏目信息</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	action="index.do" useAjax="false"
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
		<ec:column width="35" property="_No" title="No." style="text-align:center" sortable="false" value="${GLOBALROWCOUNT}"/>
		<ec:column width="200" property="name" title="栏目名称" style="cursor:hand" onclick="showArticles('${item.id}')"/>
		<ec:column width="400" property="descn" title="描述"/>
		<ec:column width="70" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">
				<img src="${ctx}/images/icons/modify.gif" title="编辑"></img>
			</a>
			<a href="#">
				<img src="${ctx}/images/icons/delete.gif" title="删除" onclick="removeDocType(${item.id })"/>
			</a>
			<a href="index.do?model.id=${item.id}">
				<img src="${ctx}/images/icons/resource.gif" title="查看下级栏目"/>
			</a>
		</ec:column>
	</ec:row>
</ec:table>

</div>
</div>
</div>


</body>
</html>