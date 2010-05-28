<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<title>会议列表</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function remove(id){
		Ext.MessageBox.confirm('提示', '确认要删除此会议吗?删除后不能恢复!    ', function(btn){
			if(btn == 'yes'){
				location.href="${ctx}/room/remove.do?model.id=" + id;	
			} 
		});
}

</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">会议管理</div>
<div class="x-toolbar">

</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" 
	var="item" 
	retrieveRowsCallback="limit"
	sortRowsCallback="limit" 
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="false" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="430px"
	minHeight="430"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_s" title="No."
			value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center" />
		<ec:column width="202" property="name"  title="房间名称" sortable="false" />

		<ec:column width="140" property="membersCount" title="成员数量" sortable="false"/>

		<ec:column width="60" property="status" title="会议状态" mappingItem="RoomMap"
			style="text-align: center" sortable="false"/>
		<ec:column width="50" property="_o" title="操作" sortable="false" style="text-align: center">
			<a title="查看会议" href="${ctx}/room/view.do?model.id=${item.id}">
			看</a> |
			<a href="#" title="删除会议" onclick="remove(${item.id})">删 </a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>