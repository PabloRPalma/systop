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
		  	    <td>
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
	height="280px"	
	minHeight="280"
	toolbarContent="navigation|pagejump|pagesize|extend|status"     
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="60" property="Organization_code" title="机构代码" />	
		<ec:column width="60" property="Instr_model" title="仪器型号" />
		<ec:column width="60" property="Instru_sn" title="仪器序列号" />	
		<ec:column width="60" property="Mac" title="MAC地址" />
		<ec:column width="60" property="Instr_ver" title="版本号" />	
		<ec:column width="60" property="Use_type" title="用途类型" />	
		<ec:column width="155" property="Ondate" title="启用时间" />
		<ec:column width="155" property="Offdate" title="终止时间" />
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