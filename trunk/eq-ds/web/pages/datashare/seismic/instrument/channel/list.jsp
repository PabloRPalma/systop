<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<%@include file="/common/datashare.jsp" %>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-1.7.1.css"	rel="stylesheet" />
<script type="text/javascript"	src="${ctx}/scripts/jquery/bgiframe/jquery.bgiframe.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<title></title>
</head>
<script type="text/javascript">
$(function() {
	$("#dialog").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 380,
		width: 580,
		modal: true
		});
});
</script>
<body>
<fieldset>
	<legend>仪器通道参数查询</legend>
     <table width="99%">
       <tr>
         <td>
         	<s:form action="list" theme="simple" id="queryFrm">
           	台站：<s:select name="model.staCode" list="stationInfoMap" listKey="staCode" cssStyle="width:140px;" listValue="staName" headerKey="" headerValue="--所有台站--" />
         <input value="查询" onclick="queryForList('${ctx}/datashare/seismic/instrument/channel/list.do', '')" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>
         <input value="数据下载" onclick="downloadInXls()" size="9" style="text-align: center;cursor: auto;" type="button" class="button"/>
          </s:form>
         </td>
        <td align="right"><td>
       </tr>
     </table>
</fieldset>
<div class="x-panel-body">
	   <ec:table items="items" var="item" 
	    retrieveRowsCallback="limit" 
	    sortRowsCallback="limit" 
		action="list.do"
		xlsFileName="仪器通道参数信息列表.xls" 
		useAjax="true" doPreload="false"
		pageSizeList="30,100,500" 
		editable="false" 
		sortable="false"	
		rowsDisplayed="30"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%" 
		height="360px" 
		minHeight="360"
		excludeParameters="selectedItems"
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	  <ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false" />
		<ec:column width="70" property="RESPONSE" title="通道响应" style="text-align:center">
		  <a href="#" onclick="look('RES-${GLOBALROWCOUNT}')">
		  	查看
		  </a>
		  <textarea id="RES-${GLOBALROWCOUNT}" style="display: none">
		  	<s:property value="#attr.item.RESPONSE" escape="true"/>
		  </textarea>
		</ec:column>
		<ec:column width="70" property="STA_CODE" title="台站名称" mappingItem="staNameAndCodeMap" />
		<ec:column width="70" property="STA_CODE" title="台站代码" />
		<ec:column width="70" property="NET_CODE" title="台网名称" mappingItem="networkInfoMap"/>
		<ec:column width="80" property="LOC_ID" title="位置标识符" />
		<ec:column width="70" property="CHN_CODE" title="通道代码" />
		<ec:column width="110" property="instrModel" title="地震计" />
		<ec:column width="70" property="UNITOFSIGNALRES" title="响应单位" />
		<ec:column width="90" property="UNITOFCALIINPUT" title="标定输入单位" />
		<ec:column width="70" property="AZIMUTH" title="方位角" />
		<ec:column width="70" property="DIP" title="倾角" />
		<ec:column width="90" property="SAMP_RATE" title="采样率（HZ）" />
	  </ec:row>
	</ec:table>
</div>
<script type="text/javascript">
function queryForList(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	$("#queryFrm").submit();
}
//以xls格式导出数据
function downloadInXls() {
	ECSideUtil.doExport('xls','仪器通道参数信息列表.xls','','ec');
}
$(function() {
	$("#dialog").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 380,
		width: 580,
		modal: true
		});
});
//显示通道参数
function look(id) {
	$('#rescon').attr("value", $('#' + id).attr('value').replace(new RegExp('>','gm'), '>\n'));
	$('#dialog').dialog('open');
}
</script>
<div id="dialog" title="查看通道响应XML">
   <table>
       <tr>
          <td>
          	<textarea id="rescon" style="width:540;height:330" readonly="true"></textarea>
          </td>
       </tr>
   </table>
</div>
</body>
</html>