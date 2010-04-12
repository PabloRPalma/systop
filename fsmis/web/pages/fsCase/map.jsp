<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/mapid.jsp" %>
<%@include file="/common/meta.jsp"%>
<html>
<head>
<title>单体事件地图标注</title>
<script type="text/javascript">
    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
        map.setCenter(new GLatLng(38.042,114.494), 12);
        map.removeMapType(G_HYBRID_MAP);
		map.addControl(new GLargeMapControl());
				
				GEvent.addListener(map, "click", function(overlay, latlng) {
					var coordinate = map.fromLatLngToDivPixel(latlng);
					if (latlng) {
						var marker = new GMarker(latlng, {draggable : true});
						map.addOverlay(marker);
						var x = latlng.lat();
						var y = latlng.lng();
						var html = "<div>"
									+ "事件标题：'${model.title}'<br>"
									+ "坐标：" + coordinate + "<br><br>"
									+ "<div align='center'><a href='#' class='button' onclick='saveData(" + ${model.id} + "," + x + "," + y + ")'> &nbsp;保存&nbsp;  </a>&nbsp;&nbsp;"
									+ "<a href='#' onclick='cancle()' class='button'> &nbsp;关闭&nbsp;  </a></div>"
									+ "</div>";
						marker.openInfoWindow(html);
						GEvent.addListener(marker, "infowindowclose", function() {
							initialize();
						});
					}
				});
				
				// 为所有标记创建指定阴影、图标尺寸灯的基础图标
		        var baseIcon = new GIcon();
		        baseIcon.shadow = "http://www.google.cn/mapfiles/shadow50.png";
		        baseIcon.iconSize = new GSize(20, 34);
		        baseIcon.shadowSize = new GSize(37, 34);
		        baseIcon.iconAnchor = new GPoint(9, 34);
		        baseIcon.infoWindowAnchor = new GPoint(9, 2);
		        baseIcon.infoShadowAnchor = new GPoint(18, 25);
	
		        // 创建信息窗口显示对应给定索引的字母的标记
		        function createMarker(lng) {
		          // Create a lettered icon for this point using our icon class
		          var letteredIcon = new GIcon(baseIcon);
		          letteredIcon.image = "http://www.google.cn/mapfiles/marker.png";
		          // 设置 GMarkerOptions 对象
		          markerOptions = { icon:letteredIcon };
		          var marker1 = new GMarker(lng, markerOptions);
	
		          GEvent.addListener(marker1, "click", function() {
		            marker1.openInfoWindowHtml("<div>"
		            		+ "事件标题：'${model.title}'<br>"
							+ "坐标：" + lng.toUrlValue(4) + "<br>");
		          });
		          return marker1;
		        }
				// 地图添加 标记
		        var coordinate = '${model.coordinate}';
		        var index = coordinate.indexOf(",");
		        var latlng1 = new GLatLng(coordinate.substring(0,index ),coordinate.substring(index+1, coordinate.length));
		        map.addOverlay(createMarker(latlng1));
	}
}

function cancle(){
	initialize();
}

function saveData(id, x, y){
	alert( id+ "," + x + "," + y);
	Ext.MessageBox.confirm('提示','确认要保存此事件信息的发生地点吗?', function(btn){
        if (btn == 'yes') {
          window.location = "${ctx}/fscase/saveMap.do?model.id=" + id + "&isMultipleCase=0&x=" + x + "&y=" + y;
        }
    });
}
</script>
</head>
<body onload="initialize()" onunload="GUnload()">
<div class="x-panel">
<div class="x-panel-header">${model.title}地理信息</div>
<table align="center">
	<tr>
		<td>
    		<div id="map_canvas" style="width: 850px; height: 550px; border: 2px solid #97b7e7;"></div>
    	</td>
    </tr>
</table>
</div>
</body>
</html>