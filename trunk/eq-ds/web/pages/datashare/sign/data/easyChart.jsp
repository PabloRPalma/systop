<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="datashare.sign.data.model.EasyChartModel"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${ctx}/styles/style.css" type='text/css' rel='stylesheet'>
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js" ></script>
<title></title>
</head>
<body>

<s:form action="exportAll">
	<stc:params type="inputTag"></stc:params>
	<s:submit value="导出图片" cssClass="button" cssStyle="margin:5px;"></s:submit>
</s:form>

<s:iterator value="%{#request.models}" var="dataModel" status="st">
<%
EasyChartModel ecM = (EasyChartModel) request.getAttribute("dataModel");
String dateFmt = ecM.getDateFormat().substring(2);
%>
	<applet code="chart.ChartApplet" id="chart<s:property value='#st.index'/>"
	 archive="${ctx}/charts/applet/chart1.ext.jar" width="650" height="350">
	<param name="chart" value="time_line"/>
	<param name="seriesCount" value="18"/>
	<param name="timePlots_0" value="${dataModel.data}">
	<param name=timeFormatInput value="${dataModel.dateFormat}">
	<param name=timeFormatOut value="<%=dateFmt%>">
	<param name=timeScale value="${dataModel.timeScale}">
	<param name=lowerTime value="${dataModel.minDate}">
	<param name=upperTime value="${dataModel.maxDate}">
	<param name=autoTimeLabelsOn value="true">
	<param name=valueLabelsOn value=true>
	<param name=valueLabelStyle value=floating>
	<param name="sampleLabelStyle" value="below_and_floating">
	<param name="floatingLabelFont" value="Arial, plain, 12">
	<param name="thousandsDelimeter" value="">
	<param name=sampleDecimalCount value="4">
	<param name=sampleColors value="blue">
	<param name=connectedLinesOn value=false>
	<param name=lineWidth value=1>
	<param name=range value="${dataModel.maxRange}">
	<param name=lowerRange value="${dataModel.minRange}">
	<param name=autoLabelSpacingOn value=true>
	<param name=valueLinesOn value="true">
	<param name=valueLinesColor value=lightGray>
	<param name=rangeLabelFont value="Arial, plain, 10">
	<param name=sampleLabelFont value="Arial, plain, 10">
	<param name=sampleLabelAngle value=0>
	<param name=rangeAdjusterOn value=true>
	<param name=sampleScrollerOn value=true>
	<param name=chartBackground value=white>
	<param name=background value=white>
	<param name=chartTitle value="${dataModel.title}">
	<param name=titleFont value="黑体, normal, 16">
	<param name="zoomOn" value="true">
	
	</applet>
	<br/>
<div style="width: 800px" align="right">
	
	<a target="_blank" href="${ctx}/datashare/sign/log/list.do?model.stationId=${dataModel.stationId}&model.pointId=${dataModel.pointId}&model.itemId=${dataModel.itemId}&model.tableCategory=DLG&model.startDate=${dataModel.minDate}&model.endDate=${dataModel.maxDate}">
		<img src="${ctx}/images/icons/cog.gif">&nbsp;查看日志信息
	</a>
</div>
<hr style="border:1px solid #4D4D4D;" size="1">
<br>
</s:iterator>
</body>
</html>