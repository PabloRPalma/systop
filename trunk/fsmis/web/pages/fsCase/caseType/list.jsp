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

function remove(id){
	if (confirm("确认要删除类别吗?")){
		window.location.href="${ctx}/casetype/remove.do?model.id=" + id;
		
	}
}
function removeAo(fscase,casetype){
    var str1="";
    var str2="";
    var str3="";
   if(fscase=='1'){
      str1="事件关联";
   }
   if(casetype=='1'){
      str2="子类别";
   }
	alert('此类别已有'+ str1 +','+ str2 +'，不能删除!');
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
             <a href="${ctx}/casetype/edit.do"><img src="${ctx}/images/icons/add.gif"/> 新建类别</a>
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
		<ec:column width="200" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">
			           编辑
			</a>
			<c:if test="${item.fsCases != '[]' || item.caseTypes != '[]'}">
			  <c:set var="fscase" value="0"></c:set>
			  <c:set var="casetype" value="0"></c:set>
			   <c:if test="${item.fsCases != '[]'}">
			        <c:set var="fscase" value="1"></c:set>
			   </c:if>
			    <c:if test="${item.caseTypes != '[]'}">
			        <c:set var="casetype" value="1"></c:set>
			   </c:if>
			  <a href="#" onclick="removeAo(${fscase},${casetype})">
			     <font color="#999999">删除</font>
			  </a>
			</c:if>
			<c:if test="${item.fsCases == '[]' && item.caseTypes == '[]'}">
			  <a href="#" onclick="remove(${item.id})">
			               删除
			  </a>
			</c:if>
			<a href="edit.do?model.caseType.id=${item.id}">
			                添加子类
			  </a>
			<c:if test="${item.caseTypes != '[]'}">
			  <a href="listchildType.do?model.id=${item.id}">
			               查看子类
			  </a>
			</c:if>
			<c:if test="${item.caseTypes == '[]'}">
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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