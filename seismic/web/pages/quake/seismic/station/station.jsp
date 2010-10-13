<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*, java.lang.Double, java.text.*, quake.ProvinceLatlng, quake.base.webapp.NumberFormatUtil" %>
<%@include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>台站分布图</title>

<meta content="台站分布图" name="keywords">
<meta content="台站分布图" name="description">
<meta name="verify-v1" content="tvW1k91GR2DbiCFs0EgeFLppSSL647Wpl1rNHCT+o10=" />
<style type="text/css">
	#mapTitle{font-size:20px; text-align:center}
	#gmap{height:600px; width:100%}
	#mainDiv{border:solid 1px #97B7E7}
	.legend{font-size:16px; text-align:center}
	.legend img{widht:20px;height:20px}
</style>
<%@include file="/common/mapid.jsp" %>

<SCRIPT language=javascript>

	var gmap = null;
	
	function initialize() {
	
      if (GBrowserIsCompatible()) {
        gmap = new GMap2(document.getElementById("gmap"));
        var customUI = gmap.getDefaultUI();
        customUI.maptypes.hybrid = false;
        gmap.setUI(customUI);
      }
      
      //定位本省中心
		var centerat = new GLatLng(35.2058663,107.7983805);
		var latlng = "<%=((Map)request.getAttribute("currentProvince")).get("latlng")%>";
		var province = "<%=((Map)request.getAttribute("currentProvince")).get("name")%>";
		if(latlng != "" && province != "内蒙古"){
			latlng = latlng.split(",");
			centerat = new GLatLng(latlng[0], latlng[1]);
			gmap.setCenter(centerat, 7, G_PHYSICAL_MAP);
		}else if(province == "内蒙古"){
			latlng = latlng.split(",");
			centerat = new GLatLng(latlng[0], latlng[1]);
			gmap.setCenter(centerat, 5, G_PHYSICAL_MAP);
		}else{
			gmap.setCenter(centerat, 4, G_PHYSICAL_MAP);
		}
		<%
		List<Map> stationList = (List<Map>)request.getAttribute("items");
		for(Map sta : stationList){
			double lat = Double.valueOf(sta.get("STA_LAT").toString());
			double lng = Double.valueOf(sta.get("STA_LON").toString());
		%>
			var markerIcon = getMarkerIcon("<%=sta.get("ROCK_TYPE")%>");
			showMarker(markerIcon, "<%=sta.get("STA_CNAME")%>", <%=NumberFormatUtil.format(lng, 1)%>, <%=NumberFormatUtil.format(lat, 1)%>, <%=sta.get("STA_ELEV")%>, "<%=sta.get("ROCK_TYPE") %>");
		<%}	%>
    }

/***********************************************************************
	以下请修改各个台站类型所显示的图标以及图标的大小
	此处仅为示例
	***********************************************************************/
	
	function getMarkerIcon(rockType) {
					
		var baseIcon = new GIcon();
		var iconSize = 18;
		if(rockType == "基岩") {
			baseIcon.image = "${ctx}/images/icons/jiyan.png";
		} else if (rockType == "花岗岩")	{
			baseIcon.image = "${ctx}/images/icons/huagang.png";
		}	else if (rockType == "井下")	{
			baseIcon.image = "${ctx}/images/icons/jingxia.png";
		}
		baseIcon.iconSize = new GSize(iconSize, iconSize);
		baseIcon.iconAnchor = new GPoint(iconSize/2, iconSize/2);
		baseIcon.infoWindowAnchor = new GPoint(iconSize/2, iconSize/2);

		return baseIcon;
	}
			
	function showMarker(markerIcon, name, longitude, latitude, elevation, rockType){		
		var latlng = new GLatLng(latitude, longitude);
		var marker = new GMarker(latlng, markerIcon);
		gmap.addOverlay(marker);
		GEvent.addListener(marker, "click", function() {
			var html ="<div>" +
			"<p><p>" +
			"<b>台站名称：<\/b>" + name + "<br>" +
			<stc:role ifAnyGranted="ROLE_LONGLAT">
			"<b>台站纬度：<\/b>" + latitude + "°<br>" +
			"<b>台站经度：<\/b>" + longitude + "°<br>" +			
			"<b>台站高程：<\/b>" + elevation + " 米<br>" +
			</stc:role>
			"<b>台基：<\/b>" + rockType + "<br>" +
			"<\/div>";
			marker.openInfoWindowHtml(html);
		});
	}

</SCRIPT>
</head>

<body onload="initialize();" onunload="GUnload();">
<div id="mainDiv" align="center">
	<div id="mapTitle">台站分布图</div>
	<div id="gmap"></div>
	<div class="legend">
  图例：<img src="${ctx}/images/icons/jiyan.png" />基岩
  <img src="${ctx}/images/icons/huagang.png" />花岗岩
  <img src="${ctx}/images/icons/jingxia.png" />井下
  </div>
</div>
</body>
</html>
