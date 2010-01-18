<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
</head>
<body>
<div class="x-panel">

<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
  items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
  action="listCheckResult.do" 
  useAjax="false"
  doPreload="false" 
  pageSizeList="5" 
  editable="false"
  sortable="true" 
  rowsDisplayed="5" 
  generateScript="true"
  resizeColWidth="false" 
  classic="false" 
  width="100%" 
  height="192px"
  minHeight="192"
  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
 <ec:row>
    <ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
	<ec:column width="100" property="checker.name" title="审核人" style="text-align:center"/>
	<ec:column width="120" property="checkTime" title="审核时间" style="text-align:center" format="yyyy-MM-dd HH:mm" cell="date"/>
	<ec:column width="70" property="isAgree" title="是否同意" style="text-align:center">
		<c:if test="${item.isAgree == '1'}"><font color="green">同意</font></c:if>
	    <c:if test="${item.isAgree == '0'}"><font color="red">不同意</font></c:if>
	</ec:column>
	<ec:column width="510" property="result" title="具体意见" sortable="false"/>
 </ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>