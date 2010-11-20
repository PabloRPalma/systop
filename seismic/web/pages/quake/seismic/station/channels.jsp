<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/quake.jsp" %>
<title></title>
</head>
<body>
<br>
<table align="center">
<tr>
<td>
<font size="6">台网:${netName}&nbsp;&nbsp;&nbsp;&nbsp;台站:${staName}</font>
</td>
</tr>
</table>
<br>
<div class="x-panel-body">
	  <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
		action="list.do"
		useAjax="true" doPreload="false"
		maxRowsExported="10000000" 
		xlsFileName="通道信息列表.xls" 
		pageSizeList="30,100,500" 
		editable="false" 
		sortable="false"	
		rowsDisplayed="30"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%" 
		height="400px" 
		minHeight="400"
		excludeParameters="selectedItems"
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	  <ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false" />
		<ec:column width="60" property="CHN_CODE" title="通道代码" />
		<ec:column width="60" property="STA_CODE" title="台站代码" />
		<ec:column width="60" property="NET_CODE" title="台网名称" mappingItem="networkInfoMap"/>
		<ec:column width="90" property="LOC_ID" title="位置标识符"/>
		<ec:column width="60" property="UNITOFSIGNALRES" title="响应单位" />
		<ec:column width="90" property="UNITOFCALIINPUT" title="标定输入单位"/>
		<ec:column width="70" property="AZIMUTH" title="方位角(度)"/>
		<ec:column width="60" property="DIP" title="倾角(度)"/>		
		<ec:column width="80" property="SAMP_RATE" title="采样率(HZ)" />
		<ec:column width="120" property="ONDATE" title="启用时间" cell="date" format="yyyy-MM-dd HH:ss"/>		
		<ec:column width="120" property="OFFDATE" title="终止时间" cell="date" format="yyyy-MM-dd HH:ss"/>
		<ec:column width="100" property="REMARK" title="备注" />
	  </ec:row>
	</ec:table>
</div>
</body>
</html>