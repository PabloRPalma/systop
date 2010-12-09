<?xml version="1.0" encoding="utf-8"?>
<%@ page contentType="application/vnd.wap.xhtml+xml;charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"     "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@ taglib prefix="stc" uri="/systop/common" %>
<%@ include file="common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
