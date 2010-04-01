<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/amcharts/swfobject.js"></script>
<script type="text/javascript">
function strParser(){
	var result = "${result}";
	var itemList = result.split("\n");
	var staticInfo = "";
	for(var i = 0; i < itemList.length - 1; i++){
		var item = itemList[i].split(";");
		staticInfo += '<b>' + item[0] + '</b>共有企业<b>' + item[1] + '个</b>' + '；';
		}
	document.getElementById("staticInfo").innerHTML = staticInfo;
}
</script>
</head>
<body  onload="strParser()">
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
	<tr>
		<td align="left">
			<p style="line-height: 20px"><font size="2" face=宋体><br><span>&nbsp;&nbsp;&nbsp;&nbsp;企业按部门统计结果显示：</span><span id="staticInfo"></span><span>以上信息显示出了各区县部门企业的数量。</span></font></p>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "column", "500", "400",
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