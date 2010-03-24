<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
	function onQuery(){
		${"frmQuery"}.action = 'index.do';
		${"frmQuery"}.submit();
	}
	function remove(id){
		if(confirm('您确定要删除此项吗？')){
			document.getElementById("id").value = id;

			${"frmRemove"}.submit();
		}
	}
</script>
</head>
<body>
<s:form name="frmRemove" id="frmRemove" method="post" action="remove" namespace="/datashare/admin/counter/offline">
<s:hidden id="id" name="model.id"/>
</s:form>
<div class="x-panel">
<div class="x-panel-header">离线数据下载统计</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		<td><s:form name="frmQuery" id="frmQuery" method="post">
			<table border="0">
				<tr>
					<td><s:textfield size="11" cssClass="Wdate" id="beginDate" name="beginDate"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});"/>&nbsp;至&nbsp;<s:textfield size="11" cssClass="Wdate" id="endDate" name="endDate"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});"/>&nbsp;&nbsp;数据类型：<s:textfield size="17" name="model.subject"/></td>
					<td>&nbsp;所属行业：&nbsp;<s:select list="industryMap" name="model.industry" headerValue="全部" headerKey=""></s:select></td>
					<td><input type="submit" value="查 询" class="button"
						onclick="onQuery()" /></td>
				</tr>
			</table>
		</s:form></td>
		<td align="right">
		<table>
			<tr>
				<td>
				<a href="${ctx}/datashare/admin/counter/offline/editNew.do"><img src="${ctx}/images/icons/add.gif"/>添加统计</a>
				&nbsp;<a href="${ctx}/datashare/admin/counter/offline/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 离线数据下载统计首页</a></td>
			</tr>
		</table>
		<td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center"><ec:table
	items="items" var="item" retrieveRowsCallback="limit"
	sortRowsCallback="limit" action="index.do" useAjax="true"
	doPreload="false" maxRowsExported="1000" pageSizeList="50,100,150"
	editable="false" sortable="true" rowsDisplayed="50"
	generateScript="true" resizeColWidth="true" classic="false"
	width="100%" height="277px" minHeight="200"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_0" title="序号"
			value="${GLOBALROWCOUNT}" sortable="false" />
		<ec:column width="340" property="subject" title="数据类型" />
		<ec:column width="140" property="time" title="服务时间" />
		<ec:column width="140" property="userName" title="服务对象" />
		<ec:column width="140" property="dataFlow" title="数据下载量(GB)" calc="total" calcSpan="4" calcTitle="合计" format="0.##"/>
		<ec:column width="150" property="_1" title="操作" style="text-align:center" sortable="false">
			<a href="editNew.do?model.id=${item.id}">
			       <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
			</a>
			<a href="#" onclick="remove(${item.id})">
			       <img src="${ctx}/images/icons/delete.gif" border="0" title="删除"/>
			</a>  
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>