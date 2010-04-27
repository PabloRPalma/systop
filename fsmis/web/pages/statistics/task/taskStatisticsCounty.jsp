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
	var csvData = "${csvData}";
	var itemList = csvData.split("\n");
	var staticInfo = "";
	
	var beginDate = document.getElementById("beginDate").value;
	var endDate = document.getElementById("endDate").value;
	if(beginDate != "" && endDate != ""){
		staticInfo += "从" + '<b>' + beginDate + '</b>'+ "到" + '<b>' + endDate + '</b>' + "这段时间内，";
	}

	if(document.getElementById("status").value == '0'){
		staticInfo += "未接收的任务，";
	}
	if(document.getElementById("status").value == '1'){
		staticInfo += "处理中的任务，";
	}
	if(document.getElementById("status").value == '2'){
		staticInfo += "已处理的任务，";
	}
	if(document.getElementById("status").value == '3'){
		staticInfo += "已退回的任务，";
	}
	
	for(var i = 0; i < itemList.length - 1; i++){
		var item = itemList[i].split(";");
		staticInfo += '<b>' + item[0] + '</b>共发生食品安全任务<b>' + item[1] + '起</b>' + '；';
		}
	document.getElementById("staticInfo").innerHTML = staticInfo;
}
</script>
</head>
<body onload="strParser()">
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
	<tr>
		<td align="left">
			<p style="line-height: 20px"><font size="2" face=宋体><br><span>&nbsp;&nbsp;&nbsp;&nbsp;任务按区县统计结果显示：</span><span id="staticInfo"></span><span>以上信息显示出了各区县食品安全任务的发生情况。</span></font></p>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "amcolumn", "750", "400",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/task/setting/statisticCounty_amcolumn.xml"));
	so.addVariable("chart_data", "${csvData}");
	so.write("flashcontent");
	// ]]>
</script>
</body>
</html>