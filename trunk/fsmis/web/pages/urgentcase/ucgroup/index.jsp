<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急指挥组维护</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
function removeUcGroup(id){
	if (confirm("确认要删除该小组吗?")){		  
		 window.location.href="remove.do?model.id=" + id;		   
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">应急指挥组维护</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="center" style="font-size: 15px">
			<s:if test="#attr.emgcType != null">
				<b>所属类别：${emgcType.name}</b>
			</s:if>
		</td>
		<td align="right">
		<table>
			<tr>
				<td><a href="${ctx}/ucgroup/edit.do"><img src="${ctx}/images/icons/add.gif" />添加</a></td>
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
	resizeColWidth="false" 
	classic="false" 
	width="100%" height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="20" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align: center" />
		<ec:column width="120" property="name" title="名称" />
		<ec:column width="200" property="display" title="显示内容" />
		<ec:column width="200" property="descn" title="描述" />
		<ec:column width="150" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a> |
			<a href="#" onClick="removeUcGroup(${item.id})">删除</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>