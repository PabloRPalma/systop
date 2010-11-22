<!DOCTYPE html PgetMarkerIconUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.lang.Double, java.text.*, quake.ProvinceLatlng,quake.base.webapp.NumberFormatUtil" %>
<%@include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>震中分布图</title>
<meta content="震中分布图" name="keywords">
<meta content="震中分布图" name="description">
<meta name="verify-v1" content="tvW1k91GR2DbiCFs0EgeFLppSSL647Wpl1rNHCT+o10=" />

<link href="/ceic/style/global.css" rel="stylesheet" type="text/css" />  
<link href="style/two-column.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	#mapTitle{font-size:20px; text-align:center}
	#gmap{height:600px; width:100%}
	#mainDiv{border:solid 1px #97B7E7}
	.legend{font-size:16px; text-align:center}
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
	DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	List<Map> eqList = (List)request.getAttribute("items");

	for(Map eq : eqList){
		double lat = Double.valueOf(eq.get("EPI_LAT").toString());
		double lng = Double.valueOf(eq.get("EPI_LON").toString());
		double depth = Double.valueOf(eq.get("EPI_DEPTH").toString());
		double mag;
		String msource;
		if(eq.get("MAG_VAL")!="" && eq.get("MAG_VAL")!=null) {
			mag = Double.valueOf(eq.get("MAG_VAL").toString());
			msource = eq.get("M_SOURCE").toString();
		}else{
			mag = Double.valueOf(eq.get("M").toString());
			msource = "M";
		}
		
%>
		var markerIcon = getMarkerIcon(<%=mag%>);
		//var markerIcon = G_DEFAULT_ICON;
		showMarker(markerIcon, '<%=eq.get("LOCATION_CNAME")%>', "<%=dateFormat.format(eq.get("O_TIME"))%>", <%=NumberFormatUtil.format(lng, 2) %>, <%=NumberFormatUtil.format(lat, 2) %>,'<%=msource%>',<%=NumberFormatUtil.format(mag, 1)%>, <%=NumberFormatUtil.format(depth, 0)%>);
<%
	}	
%>
	}

	function setGMapTypes(){
		gmap.removeMapType(G_HYBRID_MAP); 
		//gmap.removeMapType(G_SATELLITE_MAP);
		gmap.addMapType(G_PHYSICAL_MAP);
	}

	function addGControls() {
		gmap.addControl(new GLargeMapControl());
		gmap.addControl(new GMapTypeControl());
		gmap.addControl(new GScaleControl());
	}

/***********************************************************************
	以下请修改不同震级的地震所显示的图标以及图标的大小
	此处仅为示例，请根据实际需要修改判断条件
	***********************************************************************/
	
	function getMarkerIcon(eqMag) {
		var baseIcon = new GIcon();
		var iconSize;

		if(eqMag < 3) {
			iconSize=11;
			baseIcon.image = "${ctx}/images/icons/0-3.png";
		}	else if (eqMag >= 3 & eqMag < 5) {
			iconSize=15;
			baseIcon.image = "${ctx}/images/icons/3-5.png";
		} else if (eqMag >= 5 & eqMag < 6)	{
			iconSize=19;
			baseIcon.image = "${ctx}/images/icons/5-6.png";
		}	else if (eqMag >= 6)	{
			iconSize=23;
			baseIcon.image = "${ctx}/images/icons/6+.png";
		}
		baseIcon.iconSize = new GSize(iconSize, iconSize);
		baseIcon.iconAnchor = new GPoint(iconSize/2, iconSize/2);
		baseIcon.infoWindowAnchor = new GPoint(iconSize/2, iconSize/2);

		return baseIcon;
	}
			
	function showMarker(markerIcon,eqLocation, eqTime, eqLng, eqLat, eqMsource, eqMag, eqDep){		
					var latlng = new GLatLng(eqLat, eqLng);
					var marker = new GMarker(latlng, markerIcon);
					gmap.addOverlay(marker);
	
					GEvent.addListener(marker, "click", function() {
					 var html ="<div>" +
					 "<p><p>" +
					 "<b>发震时刻：<\/b>" + eqTime + "<br>" +
					 "<stc:role ifAnyGranted='ROLE_LONGLAT'><b>纬度：<\/b>" + eqLat + "°<br>" +
					 "<b>经度：<\/b>" + eqLng + "°<br></stc:role>" +
					 //"<b>位置：<\/b>" + formatLatLng(eqLat, eqLng) + "<br>" +
					 "<b>深度：<\/b>" + eqDep + " 千米<br>" +
					 "<b>震级：<\/b>" + eqMsource + " " + eqMag + "<br>" +
					 "<b>参考位置：<\/b>" + eqLocation + "<br>" +
					 "<\/div>";
						marker.openInfoWindowHtml(html);
					});
	}

</SCRIPT>
</head>

<!--***********************************************************************
	以下请修改各个震中分类所显示的图标，以及所需的地图大小等内容
	此处仅为示例
	其中<body onload="showMap();" onunload="GUnload();">，<div id="gmap"等内容请保留
	***********************************************************************-->

<body onload="initialize();" onunload="GUnload();">
<div id="mainDiv">
	<div id="mapTitle">震中分布图</div>
	<div id="gmap"></div>
	<div class="legend">
      图例：
        <img src="${ctx}/images/icons/0-3.png" /> 小于3级
        <img src="${ctx}/images/icons/3-5.png" /> 3-5级
        <img src="${ctx}/images/icons/5-6.png" /> 5-6级
        <img src="${ctx}/images/icons/6+.png" /> 6级以上 &nbsp;
    </div>
</div>

</body>
</html>
