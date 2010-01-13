<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/amcharts/swfobject.js"></script>
</head>
<body>
<div class="x-panel-header">信息员举报统计</div>
<div class="x-toolbar">
<table width="100%" border="0">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td><span class="ytb-sep"></span></td>
				<td><a href="statisticReportCount.do">举报统计</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="statisticByDept.do">部门统计</a></td>
		</table>
		</td>
	</tr>
</table>
</div>
<table width="532" align="center">
		  <tr>
			<td colspan="4"><%@ include file="/common/messages.jsp"%></td>
		  </tr>
		</table>
<table width="100%">
	<tr>
		<td align="left">
			<div id="flashcontent"><strong>你需要更新你的flash了。</strong></div>
		</td>
		<td align="left">
			<div class="x-panel-body">
			<div style="margin-left: -3px;" align="center">
			<ec:table
				items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
				action="statisticReportCount.do" 
				useAjax="false"
				doPreload="false" 
				pageSizeList="10,20,50" 
				editable="false"
				sortable="true" 
				rowsDisplayed="10" 
				generateScript="true"
				resizeColWidth="false" 
				classic="false" 
				width="100%" 
				height="300px"
				minHeight="300"
				toolbarContent="pagejump|pagesize|status">
				<ec:row>
					<ec:column width="35" property="_No" title="名次" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
					<ec:column width="70" property="name" title="姓名" sortable="false" />
					<ec:column width="120" property="dept.name" title="所属部门" sortable="false"/>
					<ec:column width="60" property="reportCount" title="举报次数" sortable="false"/>
				</ec:row>
			</ec:table></div>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "column", "500", "450",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/supervisor/column_reportcount.xml"));
	so.addVariable("chart_data", "${result}");
	so.write("flashcontent");
	// ]]>
</script>
</body>
</html>