<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>部门上报单体事件管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">部门上报</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		  <s:form action="index" method="post">
			&nbsp;&nbsp;事件标题：<s:textfield name="model.title"></s:textfield> &nbsp;&nbsp;
			事发时间：
			<input type="text" name="beginTime" style="width: 120px"
				value='<s:date name="beginTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
			至
			<input type="text" name="endTime" style="width: 120px"
				value='<s:date name="endTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
		  	<s:submit value="查询" cssClass="button"></s:submit>
		  </s:form></td>
		<td align="right">
		  <table>
			<tr>
				<td><a href="edit.do"><img src="${ctx}/images/icons/add.gif" /></a></td>
				<td><a href="edit.do">添加</a></td>
			</tr>
		  </table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
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
		<ec:column width="40" property="_s" title="No." style="text-align:center" value="${GLOBALROWCOUNT}" sortable="false" />
		<ec:column width="280" property="title" title="事件标题" sortable="false" >
			<a href="${ctx}/casereport/view.do?model.id=${item.id}" title="查看事件详情"><font color="blue">${item.title}</font></a>
		</ec:column>
		<ec:column width="100" property="caseType.name" title="事件类别" style="text-align:center" sortable="false"/>
		<ec:column width="110" property="caseTime" title="事发时间" style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" />
		<ec:column width="80" property="reportDept.name" title="上报部门" sortable="false" style="text-align:center" />
		<ec:column width="80" property="status" title="状态" sortable="false" style="text-align:center" >
			<c:if test="${item.status == '0'}"><font color="red">未派遣</font></c:if>
			<c:if test="${item.status == '1'}"><font color="#FF9D07">已派遣</font></c:if>
			<c:if test="${item.status == '2'}"><font color="green">已处理</font></c:if>
			<c:if test="${item.status == '3'}"><font color="gray">已回退</font></c:if>
			<c:if test="${item.status == '4'}"><font color="blue">已核实</font></c:if>
		</ec:column>
		<ec:column width="130" property="_0" title="操作" style="text-align:center" sortable="false">
     		<a href="${ctx}/casereport/edit.do?model.id=${item.id}">编辑</a> | 
			<a href="${ctx}/casereport/view.do?model.id=${item.id}">查看</a> | 
			<a href="#" onclick="removeReport(${item.id})" title="删除上报事件">删除</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
<script type="text/javascript">
function removeReport(caseId) {
    Ext.MessageBox.confirm('提示','确认要删除所选择的上报事件吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
          window.location = "${ctx}/casereport/remove.do?model.id=" + caseId;
        }
    });
  }
</script>
</body>
</html>