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
<div class="x-panel-header">企业按部门统计</div>
<div class="x-toolbar">
<table width="100%" border="0">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td><span class="ytb-sep"></span></td>
				<td><a href="statisticFsCaseCount.do">处罚统计</a></td>
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
		<td align="center">
			<div id="flashcontent"><strong>你需要更新你的flash了。</strong></div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "column", "500", "450",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/supervisor/column_dept.xml"));
	so.addVariable("chart_data", "${result}");
	so.write("flashcontent");
	// ]]>
</script>
</body>
</html>