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
			
			GEvent.addListener(map, "click", function(overlay, latlng) {
				var coordinate = map.fromLatLngToDivPixel(latlng);
				if (latlng) {
					var corpName = document.getElementById("corpname").value;
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
	
	function saveData(x, y){
		alert( x+ "," + y);
	}
</script>
</head>
<body onload="initialize()" onunload="GUnload()">
<s:hidden id="corpid" name="model.id"/>
<s:hidden id="corpname" name="model.name"/>
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