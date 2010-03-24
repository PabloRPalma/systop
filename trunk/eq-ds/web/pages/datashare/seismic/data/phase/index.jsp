<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/datashare.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
	$("#queryFrm").validate();
});
</script>
</head>
<body>
<fieldset>
		<legend>地震目录查询</legend>
		<s:form id="queryFrm" action="listPhase" namespace="/datashare/seismic/data/catalog" theme="simple">
  			<s:hidden name="model.tableName"/>
  			<s:hidden name="model.clcName"/>
  			<s:hidden name="model.magTname"/>
  			<s:hidden name="model.phaseTname"/>
  			<s:hidden name="model.disType"/>
		<table width="99%" align="center">
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
		  	      <s:submit value="查询" cssClass="button"/>
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
		  	      
		  	    </td>
	  	  </tr>
		</table>
		</s:form>
</fieldset>
<div class="x-panel-body">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="listPhase.do"
	useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
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
		<ec:column width="70" property="_1" title="震相数据" style="text-align:center" viewsAllowed="html" sortable="false">
		   <a href="#" onclick="phase('${ctx}/datashare/seismic/data/phase/list.do', '${item.ID}', '${item.EQ_TIME}', '${item.O_TIME_FRAC}', '${item.EPI_LAT}', '${item.EPI_LON}', '${item.EPI_DEPTH}', ${item.M}, '${item.M_SOURCE}', '${item.QLOC}', '${item.QCOM}', '${item.LOCATION_CNAME}')" title="查看震相数据"> 
		      查看
		   </a>
		</ec:column>
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
function phase(url, id, O_TIME, O_TIME_FRAC, EPI_LAT, EPI_LON, EPI_DEPTH, M, M_SOURCE, QLOC, QCOM, LOCATION_CNAME) {
	$("#phaseFrm").attr("action", url);
	$("#catId").attr("value", id);
	$("#O_TIME").attr("value", O_TIME);
	$("#O_TIME_FRAC").attr("value", O_TIME_FRAC);
	$("#EPI_LAT").attr("value", EPI_LAT);
	$("#EPI_LON").attr("value", EPI_LON);
	$("#EPI_DEPTH").attr("value", EPI_DEPTH);
	$("#M").attr("value", M);
	$("#M_SOURCE").attr("value", M_SOURCE);
	$("#QLOC").attr("value", QLOC);
	$("#QCOM").attr("value", QCOM);
	$("#LOCATION_CNAME").attr("value", LOCATION_CNAME);
	$("#phaseFrm").submit();
}
</script>
<s:form id="phaseFrm" action="" namespace="/datashare/seismic/data/phase" method="post" target="_blank">
	<s:hidden id="model.tableName" name="model.tableName" value="%{model.phaseTname}"/>
	<s:hidden id="catId" name="model.catId"/>
	<s:hidden id="O_TIME" name="model.catalog.O_TIME"/>
	<s:hidden id="O_TIME_FRAC" name="model.catalog.O_TIME_FRAC"/>
	<s:hidden id="EPI_LAT" name="model.catalog.EPI_LAT"/>
	<s:hidden id="EPI_LON" name="model.catalog.EPI_LON"/>
	<s:hidden id="EPI_DEPTH" name="model.catalog.EPI_DEPTH"/>
	<s:hidden id="M" name="model.catalog.M"/>
	<s:hidden id="M_SOURCE" name="model.catalog.M_SOURCE"/>
	<s:hidden id="QLOC" name="model.catalog.QLOC"/>
	<s:hidden id="QCOM" name="model.catalog.QCOM"/>
	<s:hidden id="LOCATION_CNAME" name="model.catalog.LOCATION_CNAME"/>
</s:form>
</body>
</html>