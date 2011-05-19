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
<table width="100%" style="margin-top: -10px;margin-bottom: -2px;">
  <tr><s:form id="queryFrm" action="listRound" namespace="/quake/seismic/data/catalog" theme="simple">
	<td>
		<fieldset>
		<legend>${model.clcName}地震目录查询(圆形区域)</legend>
  			<s:hidden name="model.tableName"/>
  			<s:hidden name="model.magTname"/>
  			<s:hidden name="model.phaseTname"/>
  			<s:hidden name="model.clcName"/>
  			<s:hidden name="model.clDescn"/>
  			<s:hidden name="model.disType"/>
  			<s:hidden name="model.isRoundQuery"/>
		<table width="100%" style="margin:0px;">
			<tr>
				<td align="right" title="(YYYY-MM-DD)">日期：</td>
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
		  	    <td align="right">经度：</td>
		  	    <td>
		  	      <s:textfield id="startLon" name="model.startLon" cssStyle="width:70px;" onblur="checkLon(this.value)" /> 
		  	    </td>
		  	    <td align="right">纬度：</td> 
		  	    <td>
		  	      <s:textfield id="startLat" name="model.startLat" cssStyle="width:70px;" onblur="checkLat(this.value)" />
		  	    </td>
		  	    <td align="right">半径：</td> 
		  	    <td>
		  	      <s:textfield name="model.range" cssStyle="width:80px;" cssClass="number" title="距中心点(经纬度)的距离,单位 ：千米(km)"/>km
		  	    </td>
			</tr>
			<tr>
				<td align="right">震级：</td>
		  	     <td>
		  	      <s:textfield name="model.startM" cssStyle="width:70px;" cssClass="number" />
		  	    </td>
		  	    <td>至</td>
		  	    <td>
		  	      <s:textfield name="model.endM" cssStyle="width:70px;" cssClass="number" />
		  	    </td>
		  	    <td align="right">地名：</td>
				<td><s:textfield name="model.location" cssStyle="width:70px;"/></td>
				<td>
		  	      	序列：
		  	    </td>
		  	    <td>
		  	      	<s:textfield name="model.sequenName" cssStyle="width:70px;"/>
		  	    </td>
		  	    <td align="right">类型：</td>
				<td><s:select list="EqTypesMap" name="model.eqType" headerKey="" headerValue="--请选择类型--" cssStyle="width:100px;">
        			</s:select>
        		</td>
		  	    <td>
		  	      <input type="button" value="查询" onclick="exportData('${ctx}/quake/seismic/data/catalog/listRound.do', '')"  class="button"/>&nbsp;
		  	      <input type="button" value="震中分布图" onclick="exportData('${ctx}/quake/seismic/data/catalog/showGis.do', '_blank')" class="button"/>
		  	    </td>
	  	  </tr>
		</table>
				<span id="lonSpan"></span>
	   </fieldset>
	</td>
	<td align="right">
		<fieldset>
			<legend>下载</legend>
			<table width="100%" style="margin:0px;">
			<tr>
				<td height="24">&nbsp;
				  <input type="button" value="基本目录" onclick="exportDataConfirm('${ctx}/quake/seismic/data/catalog/exportBasicVlm.do', '_blank')" class="button"/>
				  <input type="button" value="WKF" onclick="exportDataConfirm('${ctx}/quake/seismic/data/catalog/exportWkf.do', '_blank')" class="button"/>
	  	      	  <input type="button" value="EQT" onclick="exportDataConfirm('${ctx}/quake/seismic/data/catalog/exportEqt.do', '_blank')" class="button"/>
		  	    </td> 
			</tr>
			<tr>
				<td height="24">&nbsp;
		  	      <input type="button" value="完全目录" onclick="exportDataConfirm('${ctx}/quake/seismic/data/catalog/exportFullVlm.do', '_blank')" class="button"/>
		  	      <input type="button" value="XLS" onclick="downloadInXls()" class="button" style="width: 40px;"/>
				  <input type="button" value="Q01" onclick="exportDataConfirm('${ctx}/quake/seismic/data/catalog/exportQ01.do', '_blank')" class="button" style="width: 37px;"/>
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
	action="listRound.do"
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
	height="280px"	
	minHeight="280"
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
			<ec:column width="40" property="mb" title="mb" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="mB" title="mB" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Ms7" title="Ms7" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
			<ec:column width="40" property="Mw" title="Mw" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>
			<ec:column width="40" property="M" title="M" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>	
		</c:if>
		<c:if test="${model.magTname == ''}">
			<ec:column width="40" property="M" title="${model.disType}" cell="quake.seismic.data.catalog.webapp.cell.EMCell"/>		
		</c:if>
		<ec:column width="40" property="EPI_DEPTH" title="深度" cell="quake.seismic.data.catalog.webapp.cell.DepthFomat"/>	
		<ec:column width="80" property="Eq_type" title="地震类型" />	
		<ec:column width="60" property="_QLOC" title="定位质量" >
			<c:if test="${item.QLOC == 'nul'}"> </c:if>
			<c:if test="${item.QLOC != 'nul'}"> ${item.QLOC} </c:if>	
		</ec:column>
		<ec:column width="60" property="Loc_stn" title="定位台数" />	
		<ec:column width="130" property="LOCATION_CNAME" title="震中地名" />
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
	if(checkLon("") && checkLat("")){
		$("#queryFrm").attr("action", url);
		$("#queryFrm").attr("target", target);
		$("#queryFrm").submit();
	}
}
function exportDataConfirm(url, target) {
	if(checkLon("") && checkLat("")){
		$("#queryFrm").attr("action", url);
		$("#queryFrm").attr("target", target);
		if(confirm("确定要下载地震目录相关数据吗?")){
			$("#queryFrm").submit();
		}
	}
}
//以xls格式导出测震台站信息列表
function downloadInXls() {
	ECSideUtil.doExport('xls','地震目录列表.xls','','ec');
}
//经度判断,-180.00000~180.00000
function checkLon(lonVal) {
	var startLon = lonVal;
	var sLonVal = startLon.replace(/\s/g,"");
	if(lonVal == "" || lonVal == null) {
		startLon = document.getElementById("startLon").value;
		sLonVal = startLon.replace(/\s/g,"");
	}
	
	if(isNaN(Number(sLonVal)) || (sLonVal < -180) || (sLonVal > 180)) {
		document.getElementById("lonSpan").innerHTML = "<font color='red'>请输入合法的经度！(范围：-180~180)</font>";
		return false;
	}else {
		document.getElementById("lonSpan").innerHTML = "";
		return true;
	}
}
//纬度判断,-90.00000~90.00000
function checkLat(latVal) {
	var startLat = latVal;
	var sLatVal = startLat.replace(/\s/g,"");
	if(latVal == "" || latVal == null) {
		startLat = document.getElementById("startLat").value;
		sLatVal = startLat.replace(/\s/g,"");
	}
	
	if(isNaN(Number(sLatVal)) || (sLatVal < -90) || (sLatVal > 90)) {
		document.getElementById("lonSpan").innerHTML = "<font color='red'>请输入合法的纬度！(范围：-90~90)</font>";
		return false;
	}else {
		document.getElementById("lonSpan").innerHTML = "";
		return true;
	}
}
</script>
</script>
</body>
</html>