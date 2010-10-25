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
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">网站统计</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		<td><s:form name="frmQuery" id="frmQuery" method="post">
			<table border="0">
				<tr>
					<td><s:textfield size="11" cssClass="Wdate" id="beginDate" name="beginDate"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});"/>&nbsp;至&nbsp;
		  <s:textfield size="11" cssClass="Wdate" id="endDate" name="endDate"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});"/></td>
					<td><input type="submit" value="查 询" class="button"
						onclick="onQuery()" /></td>
				</tr>
			</table>
		</s:form></td>
		<td align="right">
		<table>
			<tr>
				<td><a href="${ctx}/quake/admin/counter/set/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 网站统计首页</a></td>
			</tr>
		</table>
		<td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center"><ec:table
	items="items" var="process" retrieveRowsCallback="process"
	sortRowsCallback="process" action="index.do" useAjax="true"
	doPreload="false" maxRowsExported="1000" pageSizeList="50,100,150"
	editable="false" sortable="true" rowsDisplayed="50"
	generateScript="true" resizeColWidth="true" classic="false"
	width="100%" height="277px" minHeight="200"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_0" title="序号"
			value="${GLOBALROWCOUNT}" sortable="false" />
		<ec:column width="140" property="month" title="访问时间" />
		<ec:column width="140" property="hits" title="点击次数" calc="total" calcSpan="2"  calcTitle="总计"/>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>