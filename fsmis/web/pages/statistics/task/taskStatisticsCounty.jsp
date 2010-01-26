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
<div class="x-panel-header">任务区县统计图</div>
<div class="x-toolbar" style="height:25px;padding:3px;">
<table width="99%">
  <tr>
    <td>
      <form id="taskStaticForm" action="${ctx}/statistics/task/statisticTaskCounty.do" method="post">
        	时间：
				<input type="text" name="beginDate" style="width: 120px" id="minDate"
					value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'maxDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					class="Wdate" />
				至
				<input type="text" name="endDate" style="width: 120px" id="maxDate"
					value='<s:date name="endDate" format="yyyy-MM-dd"/>'
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'minDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					class="Wdate" />
				&nbsp;状态:<s:select id="status" list="statusMap" cssStyle="width:60px" name="status" headerKey="" headerValue="请选择"/>
      <input type="submit" value="查询" class="button" />      
    </form>
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
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "amcolumn", "750", "550",
			"10", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/task/setting/statisticCounty_amcolumn.xml"));
	so.addVariable("chart_data", "${csvData}");
	so.write("flashcontent");
	// ]]>
</script>
</body>
</html>