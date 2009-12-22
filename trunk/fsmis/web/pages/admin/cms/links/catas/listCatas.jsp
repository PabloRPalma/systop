<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title>链接类别管理</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/dwr.jsp" %>
<%@include file="/common/ec.jsp" %>
<script type='text/javascript' src='${ctx}/dwr/interface/LinkCatasAction.js'></script>
<script type="text/javascript">
function remove(id, name) {
  if (!confirm('您确定删除名称为："' + name + '" 的链接类别吗？')) {
    return;
  }
  
  LinkCatasAction.dwrRemove(id,
  	function (msg) {
  	  if (msg == 'success') {
  	    ECSideUtil.reload('ec');
  	  } else if(msg == 'error') {
  	    alert('"' + name + '" 链接类别下存在链接不能删除。');
  	  }
  	}
  );
}
</script>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">链接类别管理</div>
    	<div class="x-toolbar">
    		<table width="100%" border="0" >
    			<TR>
    				<TD align="right">
    					<a href="${ctx}/admin/links/newCatas.do">
	    				<img src="${ctx}/images/icons/add.gif"/>添加类别</a>
	    				&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="${ctx}/admin/links/listLink.do">
						<img src="${ctx}/images/icons/link.gif">链接管理</a>
    				</TD>
    			</TR>
    		</table>
    	</div>
    	<div class="x-panel-body">
    	    <div style="margin-left:-3px;" align="center">
		    <ec:table items="items" var="item" retrieveRowsCallback="process"
				action="listCatas.do"
				useAjax="true" doPreload="false"
				pageSizeList="10,20,50,100"
				editable="false" 
				sortable="true"	
				rowsDisplayed="10"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="true"
				width="100%" 	
				height="360px"	
				minHeight="360"  
				toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
				<ec:row>
				    <ec:column width="40" property="_s" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
					<ec:column width="120" property="name" title="类别名称" />
					<ec:column width="150" property="descn" title="类别描述"  sortable="false"/>
					<ec:column width="80" property="_0" title="操作" style="text-align:center" sortable="false">
					   <a href="editCatas.do?model.id=${item.id}">
					     <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
					   </a>
					   <a href="#" onclick="remove(${item.id},'${item.name}')">
					     <img src="${ctx}/images/icons/delete.gif" border="0" title="删除"/>
					   </a>		  
					</ec:column>
				</ec:row>
				</ec:table>
			</div>
    	</div>
</div>
</body>
</html>