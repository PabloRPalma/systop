<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List,datashare.sign.data.model.Criteria"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${ctx}/charts/amstock/swfobject.js"></script>
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<script type="text/javascript">
function amGetZoom(chart_id, from, to) {
   document.getElementById('from_' + chart_id).value = from;
   document.getElementById('to_' + chart_id).value = to;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>

<s:form action="exportAll">
	<stc:params type="inputTag"></stc:params>
	<s:submit value="导出图片" cssClass="button" cssStyle="margin:5px;"></s:submit>
</s:form>
<div align="center">
<s:iterator value="criteriaList" var="criteria"> 
	<div id="${criteria.stationId}_${criteria.pointId}_${criteria.itemId}">
		<strong>You need to upgrade your Flash Player(您需要升级Flash播放器)</strong>
	</div>
	<script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("${ctx}/charts/amstock/amstock.swf", "amstock", "800", "500", "8", "#FFFFFF");
		so.addVariable("path", "");
		so.addVariable("chart_id", "${criteria.stationId}_${criteria.pointId}_${criteria.itemId}");
		so.addVariable("settings_file", 
		encodeURIComponent("${ctx}/datashare/sign/data/stockSettings.do${criteria}"));
		so.write("${criteria.stationId}_${criteria.pointId}_${criteria.itemId}");
		// ]]>
	</script>
	<div style="width: 800px; padding-top:5px" align="right">
		<input type="hidden" id="from_${criteria.stationId}_${criteria.pointId}_${criteria.itemId}">
		<input type="hidden" id="to_${criteria.stationId}_${criteria.pointId}_${criteria.itemId}">
		<a href="#" onclick="queryLog('${criteria.stationId}_${criteria.pointId}_${criteria.itemId}')">
			<img src="${ctx}/images/icons/cog.gif">&nbsp;查看日志信息
		</a>
	</div>
<hr style="border:1px solid #4D4D4D;" size="1">
<br>
</s:iterator>
<form id="queryLogFrom" action="${ctx}/datashare/sign/log/list.do" method="get" target="_blank">
	<input type="hidden" id="stationId" name="model.stationId" />
	<input type="hidden" id="pointId" name="model.pointId" />
	<input type="hidden" id="itemId" name="model.itemId" />
	<input type="hidden" id="tableCategory" name="model.tableCategory" value="DLG" />
	<input type="hidden" id="startDate" name="model.startDate" />
	<input type="hidden" id="endDate" name="model.endDate" />
</form>
<script type="text/javascript">
	/**
	 * comId为[staionId_pointId_itemId]
	 */
	function queryLog(comId){
		var idArray = comId.split("_");
		if (idArray.length != 3){
			alert("查询参数错误!");
			return;
		}
		document.getElementById("stationId").value=idArray[0];
		document.getElementById("pointId").value=idArray[1];
		document.getElementById("itemId").value=idArray[2];
		
		var fromData = document.getElementById("from_" + comId).value;
		var toData = document.getElementById("to_" + comId).value;
		
		document.getElementById("startDate").value = fromData;
		document.getElementById("endDate").value = toData;
		
		var from = document.getElementById("queryLogFrom")
		from.submit();
	}
</script>
</div>
</body>
</html>