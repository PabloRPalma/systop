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
  height="362px"
  minHeight="362"
  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
 <ec:row>
    <ec:column width="40" property="_num" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
    <ec:column width="100" property="isLeader" title="牵头/协办" style="text-align:center">
  		<c:if test="${item.isLeader eq '1'}">
			牵头
		</c:if>
		<c:if test="${item.isLeader eq '0'}">
			协办
        </c:if>
    </ec:column>
	<ec:column width="140" property="dept.name" title="部门名称" style="text-align:center"/> 
	<ec:column width="80" property="jointTask.createDate" title="创建时间" cell="date" style="text-align:center"/>
	<ec:column width="100" property="receiveTime" title="接收时间" cell="date" style="text-align:center"/>		
	<ec:column width="100" property="completionTime" title="完成时间" cell="date" style="text-align:center"/>	
	<ec:column width="130" property="status" title="任务状态" style="text-align:center">
			<c:if test="${item.status == '0'}"><font color="red">未接收</font></c:if>
			<c:if test="${item.status == '1'}"><font color="blue">已查看</font></c:if>
			<c:if test="${item.status == '2'}">
		      <c:if test="${item.isLeader eq '1'}">
				   <font color="#FF9D07">处理中...</font>
				   	<c:choose>
						<c:when test="${item.jointTask.remainDays >= 0}">剩余${item.jointTask.remainDays}天</c:when>
						<c:otherwise>逾期${-item.jointTask.remainDays}天</c:otherwise>
					</c:choose>
			   </c:if>
			   <c:if test="${item.isLeader eq '0'}">
			         <font color="blue">已查看</font>
			   </c:if>
			</c:if>			
			<c:if test="${item.status == '4'}"><font color="green">已处理</font></c:if>	
	</ec:column>	
	<ec:column width="150" property="result" title="任务结果" style="text-align:center"/>	       
  </ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>