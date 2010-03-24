<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title>软件下载管理</title>
<%@include file="/common/meta.jsp" %>
<%@ taglib prefix="ec"  uri="http://www.ecside.org" %>
<script type="text/javascript" src="${ctx}/scripts/ec/ecside_msg_utf8_cn.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/ec/ecside-unpack.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/ec/cms_style.css" />
</head>
<body style="margin-top: 10px">
<table width="650" border="0" align="center">
  <tr>
  	<td align="center" style="border-bottom: 2px solid #6CA1D7;border-top: 2px solid #6CA1D7">
	  <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
		action="index.do"
		useAjax="false" doPreload="false"
		pageSizeList="10,20,50" 
		editable="false" 
		sortable="false"	
		rowsDisplayed="10"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"
		width="100%"
		height="250px"
		minHeight="250"
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:row>
			<ec:column width="50" property="_s" title="<b>序号</b>" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		    <ec:column width="250" property="_name" title="<b>软　件　名　称</b>">
		    	<a href="view.do?model.id=${item.id}" target="_blank">
		    		${item.name}
		    	</a>
		    </ec:column>
		    <ec:column width="90" property="downCount" title="<b>下载次数</b>" style="text-align:center"/>
		    <ec:column width="90" property="_size" title="<b>软件大小</b>" style="text-align:center">
		    	${item.size}&nbsp;KB
		    </ec:column>
		    <ec:column width="140" property="softCatalog.name" title="<b>软件类别</b>" style="text-align:center"/>
		    <ec:column width="80" property="_op" title="&nbsp;" style="text-align:center">
		    	<a href="down.do?model.id=${item.id}" title="下载">
		    		<img src="${ctx}/images/icons/down.gif">
				</a>
		    	<a href="view.do?model.id=${item.id}" title="查看" target="_blank">
		    		<img src="${ctx}/images/icons/zoom.gif">
				</a>
				
		    </ec:column>
		</ec:row>
	  </ec:table>
	</td>
</tr>
</table>
</body>
</html>