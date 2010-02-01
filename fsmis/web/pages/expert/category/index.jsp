<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>专家类别信息浏览</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
function removeCategory(cID){
	if(confirm("确实要删除该专家类别信息吗？")){
		location.href="remove.do?model.id="+ cID;
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">专家信息&nbsp;&gt;&nbsp;专家类别管理</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
    <s:form action="index.do" method="post">
	    <td>  
	                 类别名称：<s:textfield name="model.name" ></s:textfield>      
	      <input type="submit" value="查询" class="button" />
	    </td>
	    <td style=" padding-left:5px; padding-top:5px;" align="right">   
	        <a href="${ctx}/expert/category/edit.do">新建类别</a>
	    </td>
    </s:form>
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
  pageSizeList="20,50,100,200" 
  editable="false"
  sortable="true" 
  rowsDisplayed="20" 
  generateScript="true"
  resizeColWidth="false" 
  classic="false" 
  width="100%" 
  height="400px"
  minHeight="400"
  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
  <ec:row>
    <ec:column width="40" property="_num" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/> 
    <ec:column width="220" property="name" title="类别名称" style="text-align:center"/>    
    <ec:column width="480" property="descn" title="类别描述" style="text-align:left"/>      
    <ec:column width="100" property="_0" title="操作" style="text-align:center" sortable="false">
	   <a href="edit.do?model.id=${item.id}">编辑</a> |
	   <c:choose>
	     <c:when test="${!empty item.experts}"> 
	        <font color="#999999">删除</font>
	     </c:when>
	     <c:otherwise>
	        <a href="#" onClick="removeCategory(${item.id})">删除</a>  
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