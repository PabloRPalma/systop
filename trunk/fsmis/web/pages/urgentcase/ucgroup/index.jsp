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
		<td align="left" style="font-size: 12px">
			<s:if test="#attr.ucTypeId != null">
				<b>所属类别：${ucType.name}</b>
			</s:if>
		</td>
		<td align="right">
		<table>
			<tr>
				<td><a href="${ctx}/ucgroup/edit.do?ucTypeId=${ucTypeId}"><img src="${ctx}/images/icons/add.gif" />添加</a></td>
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
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="30,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="30" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_no" value="${GLOBALROWCOUNT}" title="No." style="text-align:center"/>
		<ec:column width="120" property="name" title="名称"/>
		<s:if test="#attr.ucTypeId != null">	
			<ec:column width="30" property="descn" title="公用" style="text-align:center;">
				<c:if test="${item.isPublic eq '1'}">是</c:if>
				<c:if test="${item.isPublic eq '0'}">否</c:if>
			</ec:column>
		</s:if>
		<ec:column width="30" property="display" title="类别" style="text-align:center">
			<c:if test="${item.type eq '1'}">内部</c:if>
			<c:if test="${item.type eq '0'}">外部</c:if>
		</ec:column>
		<ec:column width="80" property="displays" title="显示内容" style="text-align:center"/>
		<ec:column width="200" property="descr" title="描述" style="text-align:center"/>
		<ec:column width="100" property="_0" title="操作" style="text-align:center" sortable="false">
			<s:if test="#attr.ucTypeId == null">
				<a href="edit.do?model.id=${item.id}">编辑</a>|
				<a href="#" onClick="removeUcGroup(${item.id})">删除</a>
			</s:if>
			<s:if test="#attr.ucTypeId != null">
				<c:if test="${item.isPublic eq '0'}">	
					<a href="edit.do?model.id=${item.id}">编辑</a>|
					<a href="#" onClick="removeUcGroup(${item.id})">删除</a>
				</c:if>
			</s:if>
		</ec:column>
		
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>