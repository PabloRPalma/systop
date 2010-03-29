<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/amcharts/swfobject.js"></script>
<script type="text/javascript">
function strParser(){
	var csvData = "${csvData}";
	var itemList = csvData.split("\n");
	var staticInfo = "";
	var sumRet = 0;
	
	var beginDate = document.getElementById("beginDate").value;
	var endDate = document.getElementById("endDate").value;
	if(beginDate != "" && endDate != ""){
		staticInfo += "从" + '<b>' + beginDate + '</b>'+ "到" + '<b>' + endDate + '</b>' + "这段时间内，";
	}

	for(var i = 0; i < itemList.length - 1; i++){
		var item = itemList[i].split(";");
		sumRet += Number(item[1]);
	}
	for(var i = 0; i < itemList.length - 1; i++){
		var item = itemList[i].split(";");
		staticInfo += '<b>' + item[0] + '</b>共发生<b>' + item[1] + '起</b>' + '，';
		if(sumRet != 0){
			staticInfo += '占比' + '<b>' + 100*(item[1]/sumRet).toPrecision(4) + "%</b>；";
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
<div class="x-panel-header">事件派遣环节统计图</div>
<div class="x-toolbar" style="height:25px;padding:3px;">
<table width="99%">
  <tr>
    <td>
      <form id="fscaseStaticForm" action="${ctx}/statistics/fscase/statisticBySource.do" method="post">
  <table border="0"><tr>
                       <td>  部门:</td>
				<td><div id="comboxWithTree" class="required"></div>
				<s:hidden name="deptId" id="deptId"></s:hidden></td>    
      <td>  &nbsp; 时间:
			<input type="text" name="beginDate" style="width: 90px" id="beginDate"
				value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" />
			至
			<input type="text" name="endDate" style="width: 90px" id="endDate"
				value='<s:date name="endDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" /></td>
    <td> &nbsp;&nbsp;<input type="submit" value="查询" class="button" /></td>
      </tr></table>     
    </form>
    </td>    
  </tr>
</table>
</div>
<table width="100%">
	<tr>
	<td align="left" width="30%">
	&nbsp;
	</td>
		<td align="right" width="70%">
			<div id="flashcontent"><strong>你需要更新你的flash了。</strong></div>
		</td>
	</tr>
	<tr>
		<td align="left" width="30%">
		&nbsp;
		</td>
		<td align="left" width="70%">
			<br><span>&nbsp;&nbsp;&nbsp;&nbsp;按事件来源统计结果显示：</span><span id="staticInfo"></span>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/ampie.swf", "ampie", "645", "550",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/fscase/setting/fsCaseByStatus.xml"));
	so.addVariable("chart_data", "${csvData}");
	so.write("flashcontent");
	// ]]>
</script>
<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var dtree = new DeptTree({
		url : '/admin/dept/deptTree.do',
		parent : '<stc:loginUserDept showPath="false" propertyName="id" showTopDept="true"></stc:loginUserDept>',
		initValue : '${deptName}',
		el : 'comboxWithTree',
		innerTree :'inner-tree',
		onclick : function(nodeId) {
		  Ext.get('deptId').dom.value = nodeId;
		}
	});
	dtree.init();	
	
});
</script>
</body>
</html>