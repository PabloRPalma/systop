<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>企业地图标注</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/mapid.jsp" %>
<script type="text/javascript">
	function initialize() {
		if (GBrowserIsCompatible()) {
			var map = new GMap2(document.getElementById("map_canvas"));
			map.setCenter(new GLatLng(38.0300, 114.4900), 11);
			map.removeMapType(G_HYBRID_MAP);
			map.addControl(new GLargeMapControl());

			var coordinate = document.getElementById("coordinate").value;
			var corpName = document.getElementById("corpname").value;
			
			if (coordinate != null && coordinate != '') {
				coordinate = coordinate.split(",");

				var markerIcon = getMarkerIcon();
				//showMarker(markerIcon, corpName, coordinate[1], coordinate[0]);
				
				var latlng = new GLatLng(coordinate[0], coordinate[1]);
				var dmarker = new GMarker(latlng, markerIcon);
				
				map.addOverlay(dmarker);
			
				GEvent.addListener(dmarker, "click", function() {
					var html ="<div>" +
					"<p><p>" +
					"<b>名称：<\/b>" + corpName + "<br>" +
					"<\/div>";
					dmarker.openInfoWindowHtml(html);
				});
			}
			
			GEvent.addListener(map, "click", function(overlay, latlng) {
				var coordinate = map.fromLatLngToDivPixel(latlng);
				if (latlng) {
					var marker = new GMarker(latlng, {draggable : true});
					map.addOverlay(marker);
					var x = latlng.lat();
					var y = latlng.lng();
					var html = "<div>"
								+ "企业名称："+ corpName +"<br>"
								+ "坐标：" + coordinate + "<br><br>"
								+ "<div align='center'><a href='#' class='button' onclick='saveData(" + x + "," + y + ")'>&nbsp;保存&nbsp;</a>&nbsp;&nbsp;"
								+ "<a href='#' class='button' onclick='cancle()'>&nbsp;关闭&nbsp;</a></div>"
								+ "</div>";
					marker.openInfoWindow(html);
					GEvent.addListener(marker, "infowindowclose", function() {
						initialize();
					});
				}
			});
		}
	}
	
	function cancle(){
		initialize();
	}

	function getMarkerIcon() {
		var baseIcon = new GIcon();
		var iconSize = 18;
		baseIcon.image = "${ctx}/images/icons/flag-32.png";
		baseIcon.iconSize = new GSize(iconSize, iconSize);
		baseIcon.iconAnchor = new GPoint(iconSize/2, iconSize/2);
		baseIcon.infoWindowAnchor = new GPoint(iconSize/2, iconSize/2);	
		return baseIcon;
	}
	
	//保存企业在地图上的标注信息
	function saveData(x, y){
		//alert( x+ "," + y);
		var coordinate = x + "," + y;
		var corpId = document.getElementById("corpid").value;
		$.ajax({
			url: '${ctx}/corp/saveMapInfo.do',
			type: 'post',
			dataType: 'json',
			data: {corpId : corpId, coordinate : coordinate},
			success: function(rst, textStatus){
				initialize();
			}
	  	 });
	}
</script>
</head>
<body onload="initialize()" onunload="GUnload()">
<s:hidden id="corpid" name="model.id"/>
<s:hidden id="corpname" name="model.name"/>
<s:hidden id="coordinate" name="model.coordinate"/>
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