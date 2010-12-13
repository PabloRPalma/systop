<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="getPhaseList.do"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	xlsFileName="震相数据列表.xls" 
	pageSizeList="30,60,80,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="20"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="360px"	
	minHeight="360"  
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"     
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="80" property="NET_CODE" title="台网代码"/>
		<ec:column width="80" property="STA_CODE" title="台站代码"/>
		<ec:column width="80" property="CHN_CODE" title="通道代码"/>	
		<ec:column width="80" property="REC_TYPE" title="记录类型"/>	
		<ec:column width="80" property="PHASE_NAME" title="震相名称"/>	
		<ec:column width="160" property="PHASE_TIME" title="震相到时" cell="date" format="yyyy-MM-dd HH:mm:ss"/>	
		<ec:column width="100" property="PHASE_TIME_FRAC" title="1/10000秒"/>	
		<ec:column width="100" property="AMP_TYPE" title="振幅类型"/>	
		<ec:column width="60" property="AMP" title="振幅" cell="quake.seismic.data.phase.webapp.cell.PhaseNumCell"/>	
		<ec:column width="80" property="PERIOD" title="周期" cell="quake.base.webapp.DoubleCell"/>	
		<ec:column width="40" property="WEIGHT" title="权重"/>	
		<ec:column width="80" property="CLARITY" title="初动清晰度"/>	
		<ec:column width="80" property="WSIGN" title="初动方向"/>	
		<ec:column width="120" property="RESI" title="残差"/>	
		<ec:column width="60" property="MAG_NAME" title="震级名"/>	
		<ec:column width="120" property="MAG_VAL" title="震级值" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>	
		<ec:column width="120" property="DISTANCE" title="震中距" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>	
		<ec:column width="120" property="AZI" title="方位角"/>	
		<ec:column width="120" property="S_P" title="S-P(sec)"/>		
	</ec:row>  
</ec:table>
</div>
</div>
</div>
</body>
</html>