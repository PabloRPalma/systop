<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/quake.jsp" %>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-1.7.1.css"	rel="stylesheet" />
<script type="text/javascript"	src="${ctx}/scripts/jquery/bgiframe/jquery.bgiframe.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(document).ready(function() {
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
<table width="100%" style="margin-top: -10px;margin-bottom: -2px;">
  <tr><s:form id="queryFrm" action="listPhase" namespace="/quake/seismic/data/catalog" theme="simple">
	<td>
		<fieldset>
		<legend>${model.clcName}震相数据查询</legend>
  			<s:hidden name="model.tableName"/>
  			<s:hidden name="model.magTname"/>
  			<s:hidden name="model.phaseTname"/>
  			<s:hidden name="model.clcName"/>
  			<s:hidden name="model.clDescn"/>
  			<s:hidden name="model.disType"/>
		<table width="100%" style="margin:0px;">
			<tr>
				<td align="right" title="(YYYY-MM-DD)">日&nbsp;&nbsp;期：</td>
		  	    <td>
		  	      <input type="text" id="startDate" name="model.startDate" 
			       value="<s:date name="model.startDate" format="yyyy-MM-dd"/>"
			       onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'now\'}',skin:'whyGreen'})" style="width:70px;">
		  	    </td>
		  	    <td>至</td>
		  	    <td>
		  	      <input type="text" id="endDate" name="model.endDate" 
			       value="<s:date name="model.endDate" format="yyyy-MM-dd"/>"
			       onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'now',skin:'whyGreen'})" style="width:70px;">
		  	    </td>
		  	    <td align="right">震&nbsp;&nbsp;级：</td>
		  	     <td>
		  	      <s:textfield name="model.startM" cssStyle="width:70px;" cssClass="number" title="大于等于1的正数"/>
		  	    </td>
		  	    <td>至</td>
		  	     <td>
		  	      <s:textfield name="model.endM" cssStyle="width:70px;" cssClass="number" title="大于等于1的正数"/>
		  	    </td>
		  	    <td align="right">类&nbsp;&nbsp;型：</td>
				<td><s:select list="EqTypesMap" name="model.eqType" headerKey="" headerValue="--请选择类型--" cssStyle="width:100px;">
        			</s:select>
        		</td>
		  	    <td>
		  	      	序列标识：<s:textfield name="model.sequenName" cssStyle="width:100px;"/>
		  	    </td>
			</tr>
			<tr>
				<td align="right">纬&nbsp;&nbsp;度：</td> 
		  	    <td>
		  	      <s:textfield name="model.startLat" cssStyle="width:70px;" cssClass="number" title="度.度，-90至90"/>
		  	    </td>
		  	    <td>至</td>
		  	     <td>
		  	      <s:textfield name="model.endLat" cssStyle="width:70px;" cssClass="number" title="度.度，-90至90"/>
		  	    </td>
		  	    <td align="right">经&nbsp;&nbsp;度：</td>
		  	    <td>
		  	      <s:textfield name="model.startLon" cssStyle="width:70px;" cssClass="number" title="度.度，-180至180"/> 
		  	    </td>
		  	    <td>至</td>
		  	    <td>
		  	      <s:textfield name="model.endLon" cssStyle="width:70px;" cssClass="number" title="度.度，-180至180"/>
		  	    </td>
		  	    <td align="right">地&nbsp;&nbsp;名：</td>
				<td><s:textfield name="model.location" cssStyle="width:100px;"/></td>
		  	    <td>
		  	      	定位台数：<s:textfield name="locStn" cssStyle="width:100px;" title="大于等于数"/>
		  	    </td>
	  	  </tr>
	  	  <tr>
				<td align="right">台站名称：</td> 
		  	    <td colspan="3">
		  	      <s:textfield name="model.staNetCode" cssStyle="width:163px;" cssClass="string" title="台网代码/台站代码，如：HE/XIL"/>
		  	    </td>
		  	    
		  	    <td align="right">震相名称：</td>
		  	    <td colspan="3">
		  	      <s:textfield name="model.phaseName" cssStyle="width:163px;" cssClass="string"/> 
		  	    </td>
		  	    
		  	    <td align="right">震相类型：</td>
				<td>
					<s:select list="phaseTypeMap" name="model.phaseType" headerKey="" headerValue="--请选择类型--" cssStyle="width:100px;">
        			</s:select>
				</td>
		  	    <td>&nbsp;&nbsp;&nbsp;
		  	      <input type="button" value="查询" onclick="exportData('${ctx}/quake/seismic/data/catalog/listPhase.do', '')"  class="button"/>&nbsp;
		  	      <input type="button" value="震中分布图" onclick="exportData('${ctx}/quake/seismic/data/catalog/showGis.do', '_blank')" class="button"/>
		  	    </td>
	  	  </tr>
		</table>
				
	   </fieldset>
	</td>
	<td align="right">
		<fieldset>
			<legend>下载</legend>
			<table width="100%" style="margin:0px;">
			<tr>
				<td height="75">&nbsp;
		  	      <input type="button" value="观测报告" onclick="exportBulletin('${ctx}/quake/seismic/data/catalog/exportBulletin.do', '_blank')" class="button"/>
				  <input type="button" id="look" value="简介" class="button"/>
				</td>
			</tr>
			</table>
		</fieldset>
	</td></s:form>
  </tr>
</table>
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
	height="263px"	
	minHeight="263"
	toolbarContent="navigation|pagejump|pagesize|extend|status"     
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="160" property="O_TIME" title="发震时刻" sortable="true">${item.EQ_TIME}</ec:column>	
		<ec:column width="60" property="EPI_LAT" title="震中纬度" cell="quake.base.webapp.DoubleCell"/>	
		<ec:column width="60" property="EPI_LON" title="震中经度" cell="quake.base.webapp.DoubleCell"/>	
		<c:if test="${model.magTname != ''}">
			<ec:column width="40" property="ML" title="ML" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Ms" title="Ms" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Mb" title="Mb" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="MB" title="MB" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Ms7" title="Ms7" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Mw" title="Mw" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>
			<ec:column width="40" property="M" title="M" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>	
		</c:if>
		<c:if test="${model.magTname == ''}">	
			<ec:column width="40" property="M" title="${model.disType}" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>	
		</c:if>
		<ec:column width="70" property="EPI_DEPTH" title="深度(Km)" cell="quake.seismic.data.catalog.webapp.cell.DepthFomat"/>
		<ec:column width="60" property="QLOC" title="定位质量" />	
		<ec:column width="60" property="QCOM" title="综合质量" />	
		<ec:column width="100" property="LOCATION_CNAME" title="震中地名" />
		<ec:column width="75" property="_1" title="震相" style="text-align:center" viewsAllowed="html" sortable="false">
		   <a href="#" onclick="phase('${ctx}/quake/seismic/data/phase/list.do', '${item.ID}', '${item.EQ_TIME}', '${item.O_TIME_FRAC}', '${item.EPI_LAT}', '${item.EPI_LON}', '${item.EPI_DEPTH}', ${item.M}, '${item.M_SOURCE}', '${item.QLOC}', '${item.QCOM}', '${item.LOCATION_CNAME}')" title="查看震相数据"> 
		      	查看</a> | 
		   <a href="#" onclick="exportSingleBulletin('${ctx}/quake/seismic/data/catalog/exportSingleBulletin.do?model.qcId=${item.ID}', '_blank')" title="下载震相数据"> 
		      	下载
		   </a>
		</ec:column>
	</ec:row>   
</ec:table>
</div>
<div id="dialog" title="${model.clcName}目录简介">
   <table>
       <tr>
          <td>&nbsp;&nbsp;&nbsp;&nbsp;${model.clDescn}</td>
       </tr>
   </table>
</div>
<script type="text/javascript">
function exportData(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	$("#queryFrm").submit();
}
/**
 * 下载震相数据
 */
function exportBulletin(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	if(confirm("请确保地震目录条数小于50")){
		$("#queryFrm").submit();
	}
}
/**
 * 单个震相数据下载
 */
function exportSingleBulletin(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	if(confirm("确定下载该地震目录的观测报告吗?")){
		$("#queryFrm").submit();
	}
}
function phase(url, id, O_TIME, O_TIME_FRAC, EPI_LAT, EPI_LON, EPI_DEPTH, M, M_SOURCE, QLOC, QCOM, LOCATION_CNAME){
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
<s:form id="phaseFrm" action="" namespace="/quake/seismic/data/phase" method="post" target="_blank">
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