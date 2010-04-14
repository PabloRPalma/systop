<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件地图标注</title>
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
					var marker = new GMarker(latlng, {draggable : true});
					map.addOverlay(marker);
					var x = latlng.lat();
					var y = latlng.lng();
					var html = "<div>" + 
								"<b>名称：<\/b>${model.title}<br>" + 
								"<b>地址：<\/b>${model.address}<br><br>" + 
								"<div align='center'><a href='#' class='button' onclick='saveData(" + x + "," + y + ")'>&nbsp;保存&nbsp;</a>&nbsp;&nbsp;"
								+ "<a href='#' class='button' onclick='cancle()'>&nbsp;关闭&nbsp;</a></div>"
								+ "</div>";
					marker.openInfoWindow(html);
					GEvent.addListener(marker, "infowindowclose", function() {
						initialize();
					});
				}
			});
			
			// 创建信息窗口显示对应给定索引的字母的标记
	        function createMarker(lng) {
	          markerOptions = getMarkerIcon();
	          var marker1 = new GMarker(lng, markerOptions);
	          GEvent.addListener(marker1, "click", function() {
	        	var html ="<div>" +
					"<p><p>" +
					"<b>名称：<\/b>${model.title} <br>" + 
					"<b>地址：<\/b>${model.address} <br>" + 
					"<\/div>";
	            marker1.openInfoWindowHtml(html);
	          });
	          return marker1;
	        }
	     	// 地图添加 标记
	        var coordinate = '${model.coordinate}';
	        if (coordinate != null && coordinate != '') {
	        	var cndata = coordinate.split(",");
	        	var latlng1 = new GLatLng(cndata[0],cndata[1]);
	        	map.addOverlay(createMarker(latlng1));
	        }
		}
	}

	//自定义显示图标
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
		var caseId = document.getElementById("caseid").value;
		$.ajax({
			url: '${ctx}/urgentcase/saveMapInfo.do',
			type: 'post',
			dataType: 'json',
			data: {caseId : caseId, coordinate : coordinate},
			success: function(rst, textStatus){
				//initialize();
				window.location = "${ctx}/urgentcase/index.do";
			}
	  	 });
	}

	function cancle(){
		initialize();
	}
</script>
</head>
<body onload="initialize()" onunload="GUnload()">
<s:hidden id="caseid" name="model.id"/>
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