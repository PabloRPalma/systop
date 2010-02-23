<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>文章信息列表</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function removeArticle(aID) {
    Ext.MessageBox.confirm('提示','确认要删除所选择的文章吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
        	location.href = "remove.do?model.id=" + aID;
        }
    });
  }

function view(aID){
	url = "view.do?model.id=" + aID;
	window.open(url);
}
</script>
</head>

<body>
<div class="x-panel">
<div class="x-panel-header">文章信息列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		<s:form action="index.do" method="post">
		所属栏目：
		<s:select name="model.documentType.id" listKey="id" listValue="name" list="DocumentTypeMap" headerKey=""  headerValue="选择栏目..." cssStyle="width:200px"></s:select>
		关键字：
		<s:textfield name="model.title"/>
		<input type="submit" value="查询" class="button"/>
		</s:form>
		</td>
		<td align="left"><s:form action="index.do" method="post"></s:form></td>
		<td align="right">
		<table>
			<tr>
				<td>
					<a href="edit.do">添加文章</a>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
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
		<ec:column width="35" property="_No" title="No." value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="400" property="_0" title="文章标题" ellipsis="true" onclick="view(${item.id})" style="cursor:hand" sortable="false">
			<font color="blue">${item.title}</font>
		</ec:column>
		<ec:column width="100" property="author" title="作者" sortable="false"/>
		<ec:column width="120" property="documentType.name" title="所属栏目" sortable="false"/>
		<ec:column width="120" property="_1" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a>|
			<a href="view.do?model.id=${item.id}" target="_blank">查看</a>|
			<a href="#" onclick="removeArticle(${item.id})">删除	</a>
		</ec:column>
	</ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>