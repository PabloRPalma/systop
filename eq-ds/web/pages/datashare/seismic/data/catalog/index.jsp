<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/datashare.jsp" %>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-1.7.1.css"	rel="stylesheet" />
<script type="text/javascript"	src="${ctx}/scripts/jquery/bgiframe/jquery.bgiframe.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(function() {
	$("#queryFrm").validate();
	$("#dialog").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 300,
		width: 500,
		modal: false
		});
	$('#look').click(function() {
			$('#dialog').dialog('open');
		});
});

</script>
</head>
<body>
<fieldset>
		<legend>地震目录查询</legend>
		<s:form id="queryFrm" action="list" namespace="/datashare/seismic/data/catalog" theme="simple">			
  			<s:hidden name="model.tableName"/>
  			<s:hidden name="model.magTname"/>
  			<s:hidden name="model.phaseTname"/>
  			<s:hidden name="model.clcName"/>
  			<s:hidden name="model.clDescn"/>
  			<s:hidden name="model.disType"/>
		<table width="99%" style="margin:0px;">
			<tr>
				<td align="right" title="(YYYY-MM-DD)">发震日期：</td>
		  	    <td>
		  	      <input type="text" id="startDate" name="model.startDate" 
			       value="<s:date name="model.startDate" format="yyyy-MM-dd"/>"
			       onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'now\'}',skin:'whyGreen'})" style="width:80px;">
		  	    </td>
		  	    <td>至</td>
		  	    <td>
		  	      <input type="text" id="endDate" name="model.endDate" 
			       value="<s:date name="model.endDate" format="yyyy-MM-dd"/>"
			       onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'now',skin:'whyGreen'})" style="width:80px;">
		  	    </td>
		  	    <td align="right">震级：</td>
		  	     <td>
		  	      <s:textfield name="model.startM" cssStyle="width:80px;" cssClass="number" title="大于等于1的正数"/>
		  	    </td>
		  	    <td>至</td>
		  	     <td>
		  	      <s:textfield name="model.endM" cssStyle="width:80px;" cssClass="number" title="大于等于1的正数"/>
		  	    </td>
		  	    <td>
		  	      <input type="button" value="查询" onclick="exportData('${ctx}/datashare/seismic/data/catalog/list.do', '')"  class="button"/>
		  	      <input type="button" value="WKF" onclick="exportData('${ctx}/datashare/seismic/data/catalog/exportWkf.do', '_blank')" class="button"/>
	  	      	  <input type="button" value="EQT" onclick="exportData('${ctx}/datashare/seismic/data/catalog/exportEqt.do', '_blank')" class="button"/>
	  	      	  <%-->
	  	      	  <input type="button" value="震中分布图" onclick="exportData('${ctx}/pages/datashare/seismic/data/catalog/demoGis.jsp', '_blank')" class="button"/>
	  	      	  --%>
	  	      	  <input type="button" value="震中分布图" onclick="exportData('${ctx}/datashare/seismic/data/catalog/showGis.do', '_blank')" class="button"/>
		  	    </td>
			</tr>
			<tr>
				<td align="right">纬度(°)：</td> 
		  	    <td>
		  	      <s:textfield name="model.startLat" cssStyle="width:80px;" cssClass="number" title="度.度，-90至90"/>
		  	    </td>
		  	    <td>至</td>
		  	     <td>
		  	      <s:textfield name="model.endLat" cssStyle="width:80px;" cssClass="number" title="度.度，-90至90"/>
		  	    </td>
		  	    <td align="right">经度(°)：</td>
		  	    <td>
		  	      <s:textfield name="model.startLon" cssStyle="width:80px;" cssClass="number" title="度.度，-180至180"/> 
		  	    </td>
		  	    <td>至</td>
		  	    <td>
		  	      <s:textfield name="model.endLon" cssStyle="width:80px;" cssClass="number" title="度.度，-180至180"/>
		  	    </td>
		  	    <td>
		  	      <input value="数据下载" onclick="downloadInXls()" size="9" style="text-align: center;cursor: auto;" type="button" class="button"/>
		  	      <input type="button" id="look" value="简介" class="button"/>
		  	    </td>
	  	  </tr>
		</table>
		</s:form>		
</fieldset>
<div class="x-panel-body">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="list.do"
	useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
	xlsFileName="地震目录列表.xls" 
	pageSizeList="30,60,80,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="30"
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="477px"	
	minHeight="300"
	toolbarContent="navigation|pagejump|pagesize|extend|status"     
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="160" property="O_TIME" title="发震时刻" sortable="true">${item.EQ_TIME}</ec:column>	
		<ec:column width="80" property="EPI_LAT" title="震中纬度" cell="datashare.base.webapp.DoubleCell"/>	
		<ec:column width="80" property="EPI_LON" title="震中经度" cell="datashare.base.webapp.DoubleCell"/>
		<c:if test="${model.magTname != ''}">
			<ec:column width="40" property="ML" title="ML" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Ms" title="Ms" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Mb" title="Mb" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="MB" title="MB" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Ms7" title="Ms7" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Mw" title="Mw" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>
			<ec:column width="40" property="M" title="M" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>	
		</c:if>
		<c:if test="${model.magTname == ''}">
			<ec:column width="40" property="M" title="${model.disType}" cell="datashare.seismic.data.catalog.webapp.cell.EMCell"/>		
		</c:if>
		<ec:column width="80" property="EPI_DEPTH" title="深度(Km)" cell="datashare.seismic.data.catalog.webapp.cell.DepthFomat"/>	
		<ec:column width="60" property="QLOC" title="定位质量" />	
		<ec:column width="60" property="QCOM" title="综合质量" />	
		<ec:column width="120" property="LOCATION_CNAME" title="震中地名" />
	</ec:row>   
</ec:table>
</div>
<script type="text/javascript">
function exportData(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	$("#queryFrm").submit();
}
//以xls格式导出测震台站信息列表
function downloadInXls() {
	ECSideUtil.doExport('xls','地震目录列表.xls','','ec');
}
</script>
<div id="dialog" title="查看目录简介">
   <table>
       <tr>
          <td align="center">${model.clcName}</td>
       </tr>
       <tr>
          <td>&nbsp;&nbsp;&nbsp;&nbsp;${model.clDescn}</td>
       </tr>
   </table>
</div>
</body>
</html>