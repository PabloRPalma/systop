<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/quake.jsp" %>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-1.7.1.css"	rel="stylesheet" />
<script type="text/javascript"	src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
</head>
<body>
<fieldset>
<legend>数据总览</legend>
	<table width="99%" style="margin:0px;">
		<tr>
			<td align="left">		
				<form action="index.do" id="idx" method="GET">请选择台站：
					<s:select list="stations" listKey="STATIONID" listValue="STATIONNAME"
					headerKey=""
					headerValue="请选择..." name="model.stationId" 
					onchange="$('#idx').submit()"></s:select>
				</form>
			</td>
		</tr>
	</table>
</fieldset>

   <div class="x-panel-body">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="index.do"
	useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
	pageSizeList="30,60,150" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="30"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="500px"	
	minHeight="240"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"   
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		
		<ec:column width="60" property="STATION_NAME" title="台站名称"/>
		<ec:column width="60" property="STATION_ID" title="台站ID"/>
		<ec:column width="120" property="ITEM_NAME" title="测项分量名称"/>
		<ec:column width="80" property="ITEM_ID" title="测项分量ID"/>
		<ec:column width="60" property="POINT_ID" title="测点代码"/>
		<ec:column width="50" property="SAMPLE_RATE" title="采样率"/>
		<ec:column width="80" property="IS_DIGITAL" title="数字化观测" mappingItem="digitalMapping"/>
		<ec:column width="140" property="START_DATE" title="数据起始时间" cell="quake.base.webapp.DateTimeCell"/>
		<ec:column width="140" property="END_DATE" title="数据结束时间" cell="quake.base.webapp.DateTimeCell"/>
		<ec:column width="60" property="_1" title="备注" style="text-align:center">
		<s:if test="item.NOTE != null">
		    <a href="#" onclick="note('${item.NOTE}')">查看</a>
		</s:if>
		<s:else>
		<span style="color:#CCC" title="没有数据">查看</span>
		</s:else>
		</ec:column>
		
	</ec:row>
	</ec:table>
  </div>
  <div id="dialog" title="查看备注">
   <div id="note"></div>
  </div>
<script type="text/javascript">
$(function() {
	$("#dialog").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 300,
		width: 500,
		modal: false
		});
    	
});
function note(str) {
    $('#note').html(str);
    $('#dialog').dialog('open');
}
</script>
</body>
</html>