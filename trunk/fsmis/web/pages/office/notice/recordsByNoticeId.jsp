<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript">
/**
 * 删除提交
 */
function remove(id){
    if (confirm("确认要删除该通知部门记录吗?")){
		window.location.href="${ctx}/office/receiverecord/remove.do?model.id=" + id ;
	}
}
</script>
<title>通知列表</title>
</head>
<body>
<div class="x-panel">
   <div class="x-toolbar" align="center" style="border-left: 1px; border-right: 1px; padding: 5 5 5 5;" > 
  	<font size="3"><b>通知接收纪录</b></font> 
   </div>
   <div class="x-panel-body">
     <div style="margin-left:0px; " align="center">
     <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	   doPreload="false"
	   editable="false" 
	   sortable="false"
	   rowsDisplayed="10" 
	   generateScript="true" 
	   resizeColWidth="false"
	   classic="false" 
	   width="100%" 
	   height="200px" 
	   minHeight="200"
	   toolbarContent="navigation|pagejump|extend|status">
	   <ec:row>
	   <ec:column width="30" property="_num" title="No." value="${GLOBALROWCOUNT}" style="text-align:center" />
		<ec:column width="200" property="dept.name" title="部门名称"/>
		<ec:column width="120" property="receiveDate" title="接收时间" style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" />
		<ec:column width="100" property="status" style="text-align:center" title="状态" >
		<c:if test="${item.isNew == '1'}"><font color="#C0C0C0">未读</font></c:if>
		<c:if test="${item.isNew != '1'}"><font color="green">已读</font></c:if>
		</ec:column>
		<ec:column width="100" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="#" onclick="remove(${item.id})">删除 </a>
		</ec:column>
	  </ec:row>
    </ec:table>
    </div>
  </div>
</div>
<%@include file="/common/dwrLoadingMessage.jsp" %>
</body>
</html>