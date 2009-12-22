<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<script type="text/javascript">
function remove(id) {
  if (confirm('您确定删除此信息吗？')) {
  	document.getElementById("id").value = id;
   // $("id").value = id;
    document.getElementById("frmRem").submit();
  }
}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">软件类别管理</div>
  <div class="x-toolbar">
  	<table width="99%">
  	  <tr>
  		<td align="right">
			<a href="addcatas.do">
				<img src="${ctx}/images/icons/add.gif">添加类别
			</a>
			&nbsp;&nbsp;
			<a href="${ctx}/admin/software/index.do">
				<img src="${ctx}/images/icons/compressed.gif" class="icon">软件管理
			</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
  	  </tr>
  	</table>
  </div>
  <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	  <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
		action="listcatas.do"
		useAjax="false" doPreload="false"
		pageSizeList="20,50,100,200" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="20"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"
		width="100%" 	
		height="477px"	
		minHeight="470"  
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:row>
			 <ec:column width="40" property="_s" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
		    <ec:column width="250" property="name" title="软件类别名称"/>
		    <ec:column width="250" property="description" title="软件类别描述" sortable="false"/>
		    <ec:column width="100" property="_0" title="操作">
		    <a href="editcatas.do?model.id=${item.id}">
			       <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
			   </a>
			    <a href="#" onclick="remove(${item.id})">
			       <img src="${ctx}/images/icons/delete.gif" border="0" title="删除"/>
			   </a>  
			   </ec:column>
		</ec:row>
	  </ec:table>
	</div>
  </div>
</div>
<s:form id="frmRem" action="delcatas.do" namespace="/admin/software" method="post">
  <s:hidden id="id" name="model.id"/>
</s:form>
</body>
</html>