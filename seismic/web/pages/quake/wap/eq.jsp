<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stc" uri="/systop/common" %>
<%@ include file="common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>震中分布图</title>
</head>
<body>
<p>
<img src="http://maps.google.com/staticmap?center=${cMap.EPI_LAT},${cMap.EPI_LON}&amp;markers=${cMap.EPI_LAT},${cMap.EPI_LON},orangea&amp;zoom=6&amp;size=200x200&amp;key=<stc:googleMapId/>"/>
</p>
<p><a href="${ctx}/quake/wap/index.do?tableName=${tableName}&amp;pageCurrent=${pageCurrent}">返回</a>
</p>
</body>
</html>
