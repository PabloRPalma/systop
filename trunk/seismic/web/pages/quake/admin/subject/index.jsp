<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">测项配置</div>
    <div class="x-toolbar">
	<table width="99%">
		<tr>
			<td></td>
			<td align="right">
			<table>
				<tr>
					<td><a href="${ctx}/quake/admin/subject/index.do"><img
						src="${ctx}/images/icons/house.gif" /> 学科首页</a></td>
				</tr>
			</table>
			<td>
		</tr>
	</table>
</div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	 <ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="index.do"
		useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
	pageSizeList="10,20,30" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="10"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="477px"	
	minHeight="300"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"   
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="120" property="name" title="学科名称" />	
		<ec:column width="80" property="_1" title="关联测项" style="text-align:center" viewsAllowed="html" sortable="false">
		   <a href="edit.do?model.id=${item.id}"> 
		      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
		   </a>
		</ec:column>
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
</body>
</html>