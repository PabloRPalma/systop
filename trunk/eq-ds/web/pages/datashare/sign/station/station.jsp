<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*, java.lang.Double, datashare.base.webapp.NumberFormatUtil" %>
<%@include file="/common/taglibs.jsp"%>
<%@page import="datashare.base.webapp.NumberFormatUtil"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>台站分布图</title>

<meta content="台站分布图" name="keywords">
<meta content="台站分布图" name="description">
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
		//Load Google Maps
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

	List<Map> stationList = (List)request.getAttribute("items"); 
	/***********************************************************************
	stationList是从数据库查询获得的数据，以List存放Station对象。
	参考Station.java得到Station类的构造
	请在此处插入查询代码，给stationList赋值
	***********************************************************************/
	
	for(Map station : stationList){
		double lat = Double.valueOf(station.get("LATITUDE").toString());
		double lng = Double.valueOf(station.get("LONGITUDE").toString());
%>
		var markerIcon = getMarkerIcon("<%=station.get("STATIONBASEROCK")%>");
		showMarker(markerIcon, "<%=station.get("STATIONNAME")%>", <%=NumberFormatUtil.format(lng, 1)%>, <%=NumberFormatUtil.format(lat, 1)%>, <%=station.get("ALTITUDE")%>, "<%=station.get("STATIONBASEROCK") %>");
<%
	}	
%>
	}

/***********************************************************************
	以下请修改各个台站类型所显示的图标以及图标的大小
	此处仅为示例
	***********************************************************************/
	
	function getMarkerIcon(rockType) {
					
		var baseIcon = new GIcon();
		var iconSize = 18;
		baseIcon.image = "${ctx}/images/icons/flag-32.png";
		
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
			"<stc:role ifAnyGranted='ROLE_LONGLAT'><b>台站纬度：<\/b>" + latitude + "°<br>" +
			"<b>台站经度：<\/b>" + longitude + "°<br>" +
			"<b>台站高程：<\/b>" + elevation + " 米<br></stc:role>" +
			"<b>台基：<\/b>" + rockType + "<br>" +
			"<\/div>";
			marker.openInfoWindowHtml(html);
		});
	}

</SCRIPT>
</head>

<!--***********************************************************************
	以下请修改各个台站类型所显示的图标，以及所需的地图大小等内容
	此处仅为示例
	其中<body onload="showMap();" onunload="GUnload();">，<div id="gmap"等内容请保留
	***********************************************************************-->


<body onload="initialize();" onunload="GUnload();">
<div id="mainDiv">
	<div id="mapTitle">台站分布图</div>
	<div id="gmap"></div>
	<div class="legend">
    图例：<img src="${ctx}/images/icons/flag-32.png" />台站
    </div>
</div>
</body>
</html>
