<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<%@include file="/common/quake.jsp" %>
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
	<legend>场地响应查询</legend>
     <table width="99%">
       <tr>
         <td>
         	<s:form action="list" theme="simple" id="queryFrm">
           	台站：<s:select name="model.staCode" list="stationInfoMap" listKey="staCode" cssStyle="width:140px;" listValue="staName" headerKey="" headerValue="--所有台站--" />
         <input value="查询" onclick="queryForList('${ctx}/quake/seismic/instrument/stasite/list.do', '')" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>
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
		xlsFileName="仪器场地响应信息列表.xls" 
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
		<ec:column width="90" property="STA_CODE" title="台站名称" mappingItem="staNameAndCodeMap" />
		<ec:column width="90" property="STA_CODE" title="台站代码" />
		<ec:column width="90" property="NET_CODE" title="台网名称" />
		<ec:column width="90" property="SITE" title="场地响应" style="text-align:center">
		  <a href="#" onclick="look('RES-${GLOBALROWCOUNT}')">
		  	查看
		  </a>
		  <textarea id="RES-${GLOBALROWCOUNT}" style="display: none">
		  	<s:property value="#attr.item.SITE" escape="true"/>
		  </textarea>
		</ec:column>
		<ec:column width="90" property="EVENT_NUM" title="地震事件个数" />
		<ec:column width="90" property="CREATE_DATE" title="生成日期" cell="date" format="yyyy-MM-dd" style="text-align: center"/>
		<ec:column width="70" property="OPERATOR" title="操作者" />
		<ec:column width="70" property="REMARK" title="备注" />
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
	ECSideUtil.doExport('xls','仪器场地响应信息列表.xls','','ec');
}
//显示场地响应
function look(id) {
	$('#rescon').attr("value", $('#' + id).attr('value').replace(new RegExp('>','gm'), '>\n'));
	$('#dialog').dialog('open');
}
</script>
<div id="dialog" title="查看场地响应XML">
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