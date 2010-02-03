<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>已接收内部消息管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function remove(aID){
	Ext.MessageBox.confirm('提示','确认要删除所选择的消息吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
        	location.href = "remove.do?model.id=" + aID;
        }
    });	
}

function view(aID){
	url = "view.do?model.id=" + aID;
	window.open(url);
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">已接收的内部消息</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
			<s:form action="received.do" method="post">
				 从：
			 	 <input type="text" name="createTimeBegin" style="width: 140px"
					value='<s:date name="createTimeBegin" format="yyyy-MM-dd HH:mm:ss"/>'
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					class="Wdate" /> 
			 	 至：
			 	 <input type="text" name="createTimeEnd" style="width: 140px"
					value='<s:date name="createTimeEnd" format="yyyy-MM-dd HH:mm:ss"/>'
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					class="Wdate" />
				<s:submit value="查询" cssClass="button"></s:submit>
			</s:form>
		</td>
		<td align="right">
		<table>
			<tr>
				<td align="right">
				<a href="${ctx}/office/message/sended.do">已发送的内部消息</a></td>
				<td><span class="ytb-sep"></span></td>
				<td align="right">
				<a href="${ctx}/office/message/edit.do">发送内部消息</a></td>					
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="35" property="_No" title="No." value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="100" property="sender.name" title="发信人" sortable="false" style="text-align:center"/>
		<ec:column width="250" property="content" title="内容" sortable="false" ellipsis="true"/>
		<ec:column width="130" property="createTime" title="发送时间" sortable="false" style="text-align:center" format="yyyy-MM-dd HH:mm:ss" cell="date"/>
		<ec:column width="60" property="isNew" title="状态" sortable="false" style="text-align:center">
			 <c:if test="${item.isNew == '0'}"> <font color="green">已读</font></c:if>
			<c:if test="${item.isNew == '1'}"> <font color="red">未读</font></c:if>
		</ec:column>
		<ec:column width="120" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="reply.do?model.id=${item.id}">回复</a>|
			<a href="view.do?model.id=${item.id}">查看</a>|
			<a href="#" onclick="remove(${item.id})">删除	</a>
		</ec:column>
	</ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>