<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<title>栏目管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">

/**
 * 删除
 */
 function removeDocType(cID) {
	    Ext.MessageBox.confirm('提示','确认要删除所选择的栏目吗？删除后不能恢复！', function(btn){
	        if (btn == 'yes') {
	        	location.href = "remove.do?model.id=" + cID;
	        }
	    });
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
		<td>
		<s:form action="index.do" method="post">
		栏目名称：
		<s:textfield name="typeName" />
		<input type="submit" value="查询" class="button"/>
		</s:form>
		</td>
		<td align="right">
		<table>
			<tr>
				<td>
					<a href="edit.do">添加栏目信息</a>
				</td>
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
		<ec:column width="200" property="_1" title="栏目名称" style="cursor:hand" onclick="showArticles('${item.id}')">
			<font color="blue">${item.name }</font>
		</ec:column>
		<ec:column width="400" property="descn" title="描述"/>
		<ec:column width="150" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a>|
		<c:choose>
		     <c:when test="${!empty item.childDocumentTypes || !empty item.documents}"> 
		        <font color="#999999">删除</font>|
		     </c:when>
		     <c:otherwise>
		        <a href="#" onClick="removeDocType(${item.id })"">删除</a>|  
		     </c:otherwise>
		</c:choose>  
	    <c:choose>
		    <c:when test="${!empty item.childDocumentTypes}"> 
		       		<a href="index.do?model.id=${item.id}">查看下级栏目</a>
		    </c:when>
		    <c:otherwise>
		    		<font color="#999999">查看下级栏目</font>
		    </c:otherwise>
		</c:choose>
		</ec:column>
	</ec:row>
</ec:table>

</div>
</div>
</div>


</body>
</html>