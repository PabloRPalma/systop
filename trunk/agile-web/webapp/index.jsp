<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>
<%@page import="traffic.cop.CopManager"%>
<html>
<head>
<%@ include file="/inc/meta.jsp" %>
<title></title>
</head>
<body>
<stc:spring beanId="copManager"></stc:spring>
<%
CopManager cm = (CopManager) request.getAttribute("copManager");
cm.saveCop(request);
request.setAttribute("items", cm.getCops());
%>
<ec:table items="items"
			var="item" action="#" rowsDisplayed="25" autoIncludeParameters="true"
			style="width:95%">
			<ec:exportXls fileName="AllPermissions.xls" tooltip="导出excel" />
			<ec:row>
				<ec:column property="toRemove" title="选择"
					sortable="false" viewsAllowed="html" width="30">
					<input type="checkbox" name="selectedItems" value="${item.id}"
						style='height:12px'>
				</ec:column>
				<ec:column property="rvclowcount" cell="rowCount"
					title="行号" sortable="false" width="30" />
				<ec:column property="name" title="姓名" width="200"></ec:column>
				
			</ec:row>
		</ec:table>
</body>


</html>