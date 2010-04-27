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
	var itemNums = new Array();
	
	for(var i = 0; i < itemList.length - 1; i++){
		var item = itemList[i].split(";");
		itemNums[i] = item[1];
		staticInfo += '<b>' + item[0] + '</b>共发生食品安全事件<b>' + item[1] + '起</b>' + '；';
		}
	if(document.getElementById("yearOrMonth").value == 2 && itemNums[0] != 0 && itemNums[1] != 0){
		if(document.getElementById("compareSort").value == 1){
			staticInfo += '<b>同比</b>';
			}else{
				staticInfo += '<b>环比</b>';
				}
		if(itemNums[0] <= itemNums[1]){
			staticInfo += "增加了" + '<b>' + (100*(itemNums[1] - itemNums[0])/itemNums[0]).toFixed(2) + "%</b>。";
			}else{
				staticInfo += "减少了" + '<b>' + (100*(itemNums[0] - itemNums[1])/itemNums[0]).toFixed(2) + "%</b>。";
				}
	}
	document.getElementById("staticInfo").innerHTML = staticInfo;
}
</script>
<link href="${ctx}/styles/treeSelect.css" type='text/css'
	rel='stylesheet'>

</head>
<body onload="strParser()">
<div class="x-panel-header">事件数量统计图</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
   <td>
   
   <form id="fscaseStaticForm" action="${ctx}/statistics/fscase/statisticByNum.do" method="post">
 <table> <tr><td>
   时间:</td><td><input type="text" name="beginDate" style="width: 90px"
				value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" /></td>
		<td>统计单位:<s:select id="yearOrMonth" list="yearOrMonthMap" cssStyle="width:40px" name="yearOrMonth" onchange="yearOrMonthChange()"/></td>
	<td>
				  <div id="tb_1">
							比较类型:<s:select id="compareSort" list="compareMap" cssStyle="width:50px" name="compareSort"/>
						</div></td>		
    <td><input type="submit" value="查询" class="button" /></td> 
    </tr>
    </table>
    </form>    
    </td> 
      
  </tr>
</table>
</div>
<table width="100%">
	<tr>
		<td align="center">
			<div id="flashcontent"><strong>你需要更新你的flash了。</strong></div>
		</td>
	</tr>
	<tr>
		<td align="left">
			<p style="line-height: 20px"><font size="2" face=宋体><br><span>&nbsp;&nbsp;&nbsp;&nbsp;事件数量统计结果显示：</span><span id="staticInfo"></span><span>以上统计信息反映了食品安全事件发生的数量和同比或环比变化情况。</span></font></p>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "amcolumn", "500", "400",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/fscase/setting/fsCaseNum.xml"));
	so.addVariable("chart_data", "${csvData}");
	so.write("flashcontent");
	// ]]>
	
</script>
<script type="text/javascript">

function yearOrMonthChange() { 
	if(document.getElementById("yearOrMonth").value=='2'){
		document.all("tb_1").style.display="block"; 
	}else{
		document.all("tb_1").style.display="none"; 
		}
} 
yearOrMonthChange();
</script>
</body>
</html>