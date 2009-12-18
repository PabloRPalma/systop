<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>
<title>事件类别列表</title>
<script type="text/javascript">
function removeNo(){
	alert("此类别存在事件关联，不能删除！");
}
function remove(id){
	if (confirm("确认要删除类别吗?")){
		url="${ctx}/caseType/remove.do?model.id=" + id;
		window.open(url,'main');
	}
}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">事件类别列表</div>
  <div class="x-toolbar">
     <table width="100%">
       <tr>
             <td style=" padding-left:5px; padding-top:5px;" align="right">   
             <a href="${ctx}/caseType/edit.do"><img src="${ctx}/images/icons/add.gif"/> 新建类别</a>
             	</td>
       </tr>
     </table>
   </div>
   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
     <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	   action="index.do" 
	   useAjax="true"
	   doPreload="false"
	   pageSizeList="20,50,100" 
	   editable="false" 
	   sortable="true"
	   rowsDisplayed="20" 
	   generateScript="true" 
	   resizeColWidth="true"
	   classic="false" 
	   width="100%" 
	   height="460px" 
	   minHeight="460"
	   toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	   <ec:row>
		<ec:column width="40" property="_o" title="No." value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="350" property="name" title="类别名称" />
		<ec:column width="150" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">
			      <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
			</a>
			<c:if test="${item.genericCases != '[]'}">
			<a href="#" onclick="removeNo()">
			     <img src="${ctx}/images/icons/delete.gif" /></a>
			</a>
			</c:if>
			<c:if test="${item.genericCases == '[]'}">
			<a href="#" onclick="remove(${item.id})">
			     <img src="${ctx}/images/icons/delete.gif" /></a>
			</a>
			</c:if>
		</ec:column>
 		
	  </ec:row>
    </ec:table>
    </div>
  </div>
</div>
<%@include file="/common/dwrLoadingMessage.jsp" %>
</body>
</html>