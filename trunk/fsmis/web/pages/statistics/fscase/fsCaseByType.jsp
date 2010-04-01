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
	var types = "${types}";
	var itemList = csvData.split("\n");
	var typelist = types.split(";");
	var staticInfo = "";
	var isNull = true;
	for(var i = 0; i < itemList.length - 1; i++){
		var item = itemList[i].split(";");
		for(var n = 1; n < item.length; n++){
			if(item[n] != 0){
				isNull = false;
				}
		}
		if(!isNull){
			staticInfo += '<b>' + item[0] + '</b>，';
			for(var j = 0; j < item.length - 1; j++){
				if(item[j+1] != 0){
					staticInfo += '<b>' + typelist[j] + '</b>类别共发生食品安全事件<b>' + item[j+1] + '</b>起；'; 
					}
				}
		}
	}
	
	document.getElementById("staticInfo").innerHTML = staticInfo;
}
</script>
<link href="${ctx}/styles/treeSelect.css" type='text/css'
	rel='stylesheet'>
<style type="text/css">
	input[type="text"]{
	width:180px;
}
</style>
</head>
<body onload="strParser()">
<div class="x-panel-header">事件类别统计图</div>
<div class="x-toolbar" style="height:25px;padding:3px;">
<table width="99%">
  <tr>
    <td>
      <form id="fscaseStaticForm" action="${ctx}/statistics/fscase/statisticByType.do" method="post">
       时间:
			<input id="beginDate" type="text" name="beginDate" style="width: 90px"
				value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate"/>
					至
			<input id="endDate" type="text" name="endDate" style="width: 90px"
				value='<s:date name="endDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" />
				&nbsp;统计单位:<s:select id="yearOrMonth" list="yearOrMonthMap" cssStyle="width:40px" name="yearOrMonth"/>
      <input type="submit" value="查询" class="button" />      
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
			<p style="line-height: 20px"><font size="2" face=宋体><br><span>&nbsp;&nbsp;&nbsp;&nbsp;事件按类别统计结果显示：</span><span id="staticInfo"></span><span>以上统计信息反映了各个类别的食品安全事件发生的数量情况。</span></font></p>
		</td>
	</tr>
</table>

<script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("/fsmis/amcharts/amcolumn.swf", "amcolumn", "830", "400", "8", "#FFFFFF");
		so.addVariable("path", "${ctx}/amcharts/");
		so.addVariable("settings_file",
				encodeURIComponent("/fsmis/pages/statistics/setting/fsCaseByType.xml"));
		var charset="<settings> <type>bar</type><data_type>csv</data_type><font>Tahoma</font><depth>10</depth><angle>45</angle><column>"+
    "<type>stacked</type><width>50</width><spacing>0</spacing><grow_time>1</grow_time>"+
    "<grow_effect>regular</grow_effect><data_labels><![CDATA[<b>{value}</b>]]></data_labels>"+
     "<balloon_text><![CDATA[{title}: {value}次]]></balloon_text></column>"+
  "<line><data_labels><![CDATA[{value}]]></data_labels><balloon_text><![CDATA[{value}]]></balloon_text></line>"+ 
  "<background><border_alpha>10</border_alpha></background><plot_area><margins><left>50</left><top>60</top><right>40</right><bottom>65</bottom></margins></plot_area>"+
"<grid><category><alpha>5</alpha></category><value><alpha>5</alpha></value></grid>"+
  "<values><value><min>0</min></value></values>"+
  "<axes><category><width>1</width></category><value><width>1</width></value></axes>"+
  "<balloon><alpha>80</alpha><text_color>#000000</text_color><corner_radius>5</corner_radius><border_width>1</border_width><border_alpha>60</border_alpha><border_color>#000000</border_color></balloon>"+
  "<legend><width>850</width></legend>"+
  "<labels><label lid='0'><x>268</x><y>25</y><text><![CDATA[<b></b>]]></text></label></labels>"+
  "<graphs>"+"${graph}"+ 
  "</graphs><guides></guides></settings>";
		
		so.addVariable("chart_settings", encodeURIComponent(charset));
		so.addVariable("chart_data", "${csvData}");
		so.addVariable("preloader_color", "#000000");
		so.write("flashcontent");
		// ]]>
	</script>
</body>
</html>