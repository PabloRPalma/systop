<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急指挥派遣类别维护</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">

/**
 * 删除提交
 */
 function removeUcType(ucID){
 	if(confirm("确实要删除该类别吗？")){
 		location.href="remove.do?model.id="+ucID;
 	}
 }
 </script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">应急指挥派遣类别维护</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td></td>
		<td align="right">
		<table>
			<tr>
				<td><a href="edit.do"><img
					src="${ctx}/images/icons/add.gif" />添加</a></td>
			</tr>
		</table>
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
	width="100%" height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align: center" />
		<ec:column width="300" property="name" title="名称" />
		<ec:column width="200" property="remark" title="备注" />
		<ec:column width="150" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a> |
			<a href="${ctx}/ucgroup/index.do?ucTypeId=${item.id}">查看组</a> |
			<a href="#" onClick="removeUcType(${item.id})">删除</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>