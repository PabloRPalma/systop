<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,datashare.sign.data.model.Criteria"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<title></title>
</head>
<body>
<%
	String host = request.getLocalAddr();
	if ("127.0.0.1".equals(host)) {
		host = "localhost";
	}
	List<Criteria> criteriaList = (List<Criteria>) request .getAttribute("criteriaList");
	String comId = "";
	for (Criteria c : criteriaList) {
		comId = c.getStationId() + "_" + c.getPointId() + "_"
				+ c.getItemId();
%>
<applet id="<%=comId%>" code="datashare.sign.data.webapp.SignApplet"
	ARCHIVE="${ctx}/charts/applet/applet-lineA.jar,${ctx}/charts/applet/jfreechart.jar,${ctx}/charts/applet/jcommon.jar"
	width="800" height="500" align="middle">
	<param name="host" value="<%=host%>" />
	<param name="port" value="<%=request.getLocalPort()%>" />
	<param name="contextPath" value="${ctx}" />
	<param name="methodId" value="<%=c.getMethodId() %>" />
	<param name="itemId" value="<%=c.getItemId() %>" />
	<param name="pointId" value="<%=c.getPointId() %>" />
	<param name="sampleRate" value="<%=c.getSampleRate() %>" />
	<param name="tableCategory" value="<%=c.getTableCategory() %>" />
	<param name="stationId" value="<%=c.getStationId() %>" />
	<param name="startDate" value="<%=request.getAttribute("start") %>" />
	<param name="endDate" value="<%=request.getAttribute("end") %>" />
	<param name="title" value="<%=request.getAttribute(comId + "_title") %>" />

</applet>

<br/>
<div style="width: 800px" align="right">
	<a href="${ctx}/pages/datashare/sign/data/security/readme.jsp">
		<img style="width:18px;height:18px" src="${ctx}/images/exticons/icon-question.gif">&nbsp;无法保存曲线图
	</a>
	<a href="${ctx}/datashare/sign/log/list.do?model.stationId=<%=c.getStationId() %>&model.pointId=<%=c.getPointId() %>&model.itemId=<%=c.getItemId() %>&model.tableCategory=DLG&model.startDate=<%=request.getAttribute("start")%>&model.endDate=<%=request.getAttribute("end")%>">
		<img src="${ctx}/images/icons/cog.gif">&nbsp;查看日志信息
	</a>
</div>
<hr style="border:1px solid #4D4D4D;" size="1">
<br>
<%
	}
%>

</body>
</html>