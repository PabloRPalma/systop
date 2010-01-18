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
  action="index.do" 
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
    <ec:column width="30" property="_num" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
    <ec:column width="600" property="title" title="标题">
      <a href="${ctx}${item.path}"><font color="blue">${item.title}</font></a>
    </ec:column>
    <ec:column width="210" property="creator" title="完成人" style="text-align:center"/>          
  </ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>