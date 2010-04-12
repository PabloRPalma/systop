<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.lang.Double, com.systop.fsmis.model.Corp" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>企业分布图</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/mapid.jsp" %>
<SCRIPT language=javascript>
	var map = null;
	//初始化地图页面
	function initialize() {
		//Load Google Maps
		if (GBrowserIsCompatible()) {
			map = new GMap2(document.getElementById("map_canvas"));
			map.setCenter(new GLatLng(38.0300, 114.4900), 11);
			map.removeMapType(G_HYBRID_MAP);
			map.addControl(new GLargeMapControl());
      	}
<%
	List<Corp> corpList = (List)request.getAttribute("items"); 
	for(Corp corp : corpList){
		String cdnt[] = new String []{};
		String cdnate = corp.getCoordinate().toString();
		cdnt = cdnate.split(",");
		double lat = Double.valueOf(cdnt[0]);
		double lng = Double.valueOf(cdnt[1]);
%>
		var markerIcon = getMarkerIcon();
		showMarker(markerIcon, "<%=corp.getName()%>", "<%=corp.getAddress()%>", <%=lng%>, <%=lat%>);
<%
	}	
%>
	}
	
	//自定义地图上显示的图标
	function getMarkerIcon() {
		var baseIcon = new GIcon();
		var iconSize = 18;
		baseIcon.image = "${ctx}/images/icons/flag-32.png";
		baseIcon.iconSize = new GSize(iconSize, iconSize);
		baseIcon.iconAnchor = new GPoint(iconSize/2, iconSize/2);
		baseIcon.infoWindowAnchor = new GPoint(iconSize/2, iconSize/2);
		return baseIcon;
	}
	
	//地图上需要显示的信息，如企业或事件
	function showMarker(markerIcon, name, address, longitude, latitude){		
		var latlng = new GLatLng(latitude, longitude);
		var marker = new GMarker(latlng, markerIcon);
		map.addOverlay(marker);
		GEvent.addListener(marker, "click", function() {
			var html ="<div>" +
			"<p><p>" +
			"<b>名称：<\/b>" + name + "<br>" +
			"<b>地址：<\/b>" + address + "<br>" +
			"<\/div>";
			marker.openInfoWindowHtml(html);
		});
	}
</SCRIPT>
</head>
<body onload="initialize()" onunload="GUnload()">
<table align="center">
	<tr>
		<td>
		<div id="map_canvas"
			style="width: 850px; height: 550px; border: 2px solid #97b7e7;"></div>
		</td>
	</tr>
</table>
</body>
</html>