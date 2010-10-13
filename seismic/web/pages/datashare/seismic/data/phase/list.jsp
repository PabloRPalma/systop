<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/quake.jsp" %>
</head>
<body>
<fieldset>
	<legend>震相数据</legend>
<table width="99%">
	<tr>
		<td>
		  <table width="100%">
		  	<tr>
		  		<td align="right">发震时刻：</td>
		  		<td>${model.catalog.O_TIME}</td>
		  		<td align="right">发震时刻(1/10000秒)：</td>
		  		<td>${model.catalog.O_TIME_FRAC}</td>
		  		<td align="right">震中纬度：</td>
		  		<td>${model.catalog.EPI_LAT}</td>
		  		<td align="right">震中经度：</td>
		  		<td>${model.catalog.EPI_LON}</td>
		  		<td align="right">深度(Km)：</td>
		  		<td>${model.catalog.EPI_DEPTH}</td>
		  	</tr>
		  	<tr>
		  		<td align="right">震级值：</td>
		  		<td>${model.catalog.M}</td>
		  		<td align="right">震级：</td>
		  		<td>${model.catalog.M_SOURCE}</td>
		  		<td align="right">定位质量：</td>
		  		<td>${model.catalog.QLOC}</td>
		  		<td align="right">综合质量：</td>
		  		<td>${model.catalog.QCOM}</td>
		  		<td align="right">震中地名：</td>
		  		<td>${model.catalog.LOCATION_CNAME}</td>
		  		
		  	</tr>
		  </table>
		</td>
		<td align="right">
		<table>
			<tr>
				<td>
				<input value="数据下载" onclick="downloadInXls()" type="button" class="button"/>
				</td>
			</tr>
		</table>
		<td>
	</tr>
</table>
</fieldset>
<div class="x-panel-body">
<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="list.do"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	xlsFileName="震相数据列表.xls" 
	pageSizeList="30,60,80,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="30"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="600px"	
	minHeight="200"  
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
<script type="text/javascript">
//以xls格式导出震相数据
function downloadInXls() {
	ECSideUtil.doExport('xls','震相数据列表.xls','','ec');
}
</script>
</body>
</html>