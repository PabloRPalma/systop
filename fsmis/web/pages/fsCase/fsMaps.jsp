<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.lang.Double, com.systop.fsmis.model.FsCase" %>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/mapid.jsp" %>
<%@include file="/common/meta.jsp"%>
<html>
<head>
<title>单体事件地理分布图</title>
<script type="text/javascript">
    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
        map.setCenter(new GLatLng(38.042,114.494), 12);
        map.removeMapType(G_HYBRID_MAP);
		map.addControl(new GLargeMapControl());
		map.disableDoubleClickZoom();
				
				// 为所有标记创建指定阴影、图标尺寸灯的基础图标
		        var baseIcon = new GIcon();
		        baseIcon.shadow = "http://www.google.cn/mapfiles/shadow50.png";
		        baseIcon.iconSize = new GSize(20, 34);
		        baseIcon.shadowSize = new GSize(37, 34);
		        baseIcon.iconAnchor = new GPoint(9, 34);
		        baseIcon.infoWindowAnchor = new GPoint(9, 2);
		        baseIcon.infoShadowAnchor = new GPoint(18, 25);
	
		        // 创建信息窗口显示对应给定索引的字母的标记
		        function createMarker(lng, title, addr) {
		          // Create a lettered icon for this point using our icon class
		          var letteredIcon = new GIcon(baseIcon);
		          letteredIcon.image = "http://www.google.cn/mapfiles/marker.png";
		          // 设置 GMarkerOptions 对象
		          markerOptions = { icon:letteredIcon };
		          var marker1 = new GMarker(lng, markerOptions);
	
		          GEvent.addListener(marker1, "click", function() {
		            marker1.openInfoWindowHtml("<div>"
		            		+ "事件标题："+ title + "<br>"
		            		+ "事件地址："+ addr + "<br>"
							+ "坐标：" + lng.toUrlValue(4) + "<br>");
		          });
		          return marker1;
		        }
				// 地图添加 标记
		        <%
		    	List<FsCase> fcList = (List)request.getAttribute("items"); 
		    	for(FsCase fc : fcList){
		    		String cdnt[] = new String []{};
		    		String cdnate = fc.getCoordinate().toString();
		    		cdnt = cdnate.split(",");
		        %>
			        var latlng = new GLatLng(<%=cdnt[0]%>, <%=cdnt[1]%>);
			        map.addOverlay(createMarker(latlng, '<%=fc.getTitle()%>', '<%=fc.getAddress()%>'));
			    <%    
				}
		    	%>
	}
}
</script>
</head>
<body onload="initialize()" onunload="GUnload()">
<div class="x-panel">
<div class="x-panel-header">地理分布图</div>
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