<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/quake.jsp" %>
<script type="text/javascript">
$(function() {
	$("#queryFrm").validate();
});
</script>
</head>
<body>
<fieldset>
		<legend>仪器查询</legend>
		<s:form id="queryFrm" action="index" namespace="/quake/seismic/instrument" theme="simple">			
		<table width="99%" style="margin:0px;">
			<tr>
				<td style="vertical-align: text-top;">
					台网：<s:select list="netCodes" name="model.netCode" headerKey="" headerValue="--请选择台网--" cssStyle="width:100px;"></s:select>
					仪器类型：<s:select list="instrTypes" name="model.instrType" headerKey="" headerValue="--请选择类型--" cssStyle="width:100px;"></s:select>
					用途类型：<s:select list="useTypes" name="model.useType" headerKey="" headerValue="--请选择用途--" cssStyle="width:100px;"></s:select>
        			启用时间：<input type="text" id="startDate" name="model.startDate" 
			       		value="<s:date name="model.startDate" format="yyyy-MM-dd"/>"
			       		onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'now\'}',skin:'whyGreen'})" style="width:80px;">
        			&nbsp;至&nbsp;
        			<input type="text" id="endDate" name="model.endDate" 
			       		value="<s:date name="model.endDate" format="yyyy-MM-dd"/>"
			       		onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'now',skin:'whyGreen'})" style="width:80px;">
        			<input type="button" value="查询" onclick="exportData('${ctx}/quake/seismic/instrument/index.do', '')"  class="button"/>	
        		</td>
			</tr>
		</table>
		</s:form>		
</fieldset>
<div class="x-panel-body">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="index.do"
	useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
	xlsFileName="仪器列表.xls" 
	pageSizeList="30,60,80,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="30"
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="310px"	
	minHeight="310"
	toolbarContent="navigation|pagejump|pagesize|extend|status"     
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="60" property="Organization_code" title="机构代码" />	
		<ec:column width="80" property="Instr_type" title="仪器类型" mappingItem="instrTypes"/>
		<ec:column width="120" property="Instr_model" title="仪器型号" />
		<ec:column width="120" property="Instru_sn" title="仪器序列号" />	
		<ec:column width="80" property="Mac" title="MAC地址" />
		<ec:column width="60" property="Instr_ver" title="版本号" />	
		<ec:column width="80" property="Use_type" title="用途类型" mappingItem="UseTypes"/>	
		<ec:column width="120" property="Ondate" title="启用时间" cell="date" format="yyyy-MM-dd HH:ss"/>
		<ec:column width="120" property="Offdate" title="终止时间" cell="date" format="yyyy-MM-dd HH:ss"/>
		<ec:column width="80" property="staName" title="所在台站" />
	</ec:row>   
</ec:table>
</div>
<script type="text/javascript">
function exportData(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	$("#queryFrm").submit();
}
//以xls格式导出仪器信息列表
function downloadInXls() {
	ECSideUtil.doExport('xls','仪器列表.xls','','ec');
}
</script>
</body>
</html>