<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="operate.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">应急事件管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		<form action="${ctx}/urgentcase/index.do" method="post">
			 &nbsp;事件名称：
		    <s:textfield name="model.title"></s:textfield>&nbsp;&nbsp;
		           事发时间：
			<input type="text" name="caseBeginTime" style="width: 120px"
				value='<s:date name="caseBeginTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
			至
			<input type="text" name="caseEndTime" style="width: 120px"
				value='<s:date name="caseEndTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
			<s:submit value="查询" cssClass="button"></s:submit>
		</form>
		</td>
		<td align="right">
		  <table>
			<tr>
				<td><a href="edit.do" title="添加应急事件"><img
					src="${ctx}/images/icons/add.gif" /></a></td>
				<td valign="middle"><a href="edit.do" title="添加应急事件">添加</a></td>
			</tr>
		  </table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<ec:table
	items="items" 
	var="item" 
	retrieveRowsCallback="process"
	sortRowsCallback="process" 
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
	    <ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
		<ec:column width="180" property="title" title="事件名称" >
		  <c:if test="${item.status == null || item.status eq '0' || item.status eq '1'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=0" title="查看事件"><font color="blue">${item.title}</font></a>
		  </c:if>
		  <c:if test="${item.status eq '2' || item.status eq '3' || item.status eq '4'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=3" title="查看事件"><font color="blue">${item.title}</font></a>
		  </c:if>
		</ec:column>
		<ec:column width="180" property="address" title="事发地点"/>
		<ec:column width="110" property="caseTime" title="事发时间" style="text-align:center" format="yyyy-MM-dd HH:mm" cell="date"/>
		<ec:column width="60" property="status" title="状态" style="text-align:center">
		    <c:if test="${item.status == null}"><font color="#990099">未审核</font></c:if>
		    <c:if test="${item.status == '0'}"> <font color="red">未通过</font></c:if>
			<c:if test="${item.status == '1'}"> <font color="blue">未派遣</font></c:if>
			<c:if test="${item.status == '2'}"> <font color="green">已派遣</font></c:if>
			<c:if test="${item.status == '3'}"> <font color="green">已处理</font></c:if>
			<c:if test="${item.status == '4'}"> <font color="green">已核实</font></c:if>
		</ec:column>
		<ec:column width="60" property="_v" title="查看" style="text-align:center">
			<c:if test="${item.status != null}">
				<a href="${ctx}/urgentcase/listCheckResult.do?model.id=${item.id}">审核记录</a>
			</c:if>
			<c:if test="${item.status == null}">
				<font color="silver">无记录</font>
			</c:if>
		</ec:column>
		<ec:column width="210" property="_0" title="操作" style="text-align:center" sortable="false">
		  <c:if test="${item.status == null || item.status eq '0'}">
			<a href="${ctx}/urgentcase/edit.do?model.id=${item.id}" title="修改事件">改</a> | 
			<a href="#" onclick="showCheckWindow('${item.id}')" title="审核事件">审</a> | 
			<font color="silver">派</font> |
		  </c:if>
		  <c:if test="${item.status eq '1'}">
		  	<font color="silver">改</font> | 
		  	<font color="silver">审</font> | 
			<a href="#" onclick="showDispatchWindow('${item.id}','${item.title}')" title="任务派遣">派</a> | 
		  </c:if>
		  <c:if test="${item.status eq '2' || item.status eq '3' || item.status eq '4'}">
		  	<font color="silver">改</font> | 
		  	<font color="silver">审</font> | 
			<font color="silver">派</font> | 
		  </c:if>
		  <c:if test="${item.status == null || item.status eq '0' || item.status eq '1'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=0" title="查看事件">看</a> | 
		  </c:if>
		  <c:if test="${item.status == '2' || item.status eq '3' || item.status eq '4'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=3" title="查看事件">看</a> | 
		  </c:if>
		  	<a href="${ctx}/urgentcase/printAppForm.do?model.id=${item.id}" title="打印应急预案申请单" target="_blank">印</a> | 
			<a href="#" onclick="remove(${item.id})" title="删除事件">删</a>
		</ec:column>
	</ec:row>
</ec:table>
</div>
</div>
<script type="text/javascript">
  function remove(caseId) {
    Ext.MessageBox.confirm('提示','确认要删除所选择的应急事件吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
          window.location = "${ctx}/urgentcase/remove.do?model.id=" + caseId;
        }
    });
  }
  
  function refresh() {
    ECSideUtil.reload('ec');
  }

  function print(id){
	var url = "${ctx}/emergency/event/printEmgcApply.do?model.id=" + id;
	window.open(url);
  }
</script>
</body>
</html>