<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.lang.Double, com.systop.fsmis.model.UrgentCase" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件分布图</title>
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
	List<UrgentCase> caseList = (List)request.getAttribute("items"); 
	for(UrgentCase utcase : caseList){
		String cdnt[] = new String []{};
		String cdnate = utcase.getCoordinate();
		double lat = 0;
		double lng = 0;
		if (cdnate != null && cdnate != "") {
			cdnt = cdnate.split(",");
			lat = Double.valueOf(cdnt[0]);
			lng = Double.valueOf(cdnt[1]);
		}
%>
		var markerIcon = getMarkerIcon();
		if (<%=lng%> != 0 && <%=lat%> != 0) {
			showMarker(markerIcon, "<%=utcase.getTitle()%>", "<%=utcase.getAddress()%>", <%=lng%>, <%=lat%>);
		}
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