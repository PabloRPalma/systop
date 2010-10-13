<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/quake.jsp" %>
<title></title>
</head>

<body>
<fieldset>
	<legend>测震台站查询</legend>
	<s:form action="list" theme="simple" id="queryFrm">
     <table width="99%">
     <tr>
       <td>起始年限：
          <s:textfield size="7" cssClass="Wdate" id="startDate" name="model.startDate"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});" readonly="true"/>&nbsp;至&nbsp;
		  <s:textfield size="7" cssClass="Wdate" id="endDate" name="model.endDate"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});" readonly="true"/>
	   </td>
	   
     </tr>
       <tr>
         <td>
           
        台网代码:
        <s:select list="netCodes" name="model.netCode" headerKey="" headerValue="全部" cssStyle="width:100px;">
        </s:select>
        地震计类型:<s:select id="instrId" name="model.instrumentId" list="instrumentsMap" listKey="instrCode" cssStyle="width:100px;" listValue="instrName" headerKey="" headerValue="--所有类型--" />
        数采类型:<s:select id="datId" name="model.datarecordId" list="datarecordsMap" listKey="instrCode" cssStyle="width:100px;" listValue="instrName" headerKey="" headerValue="--所有类型--" />
       </td>
       <td>
	   <input value="查询" onclick="queryForList('${ctx}/quake/seismic/station/list.do', '')" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>
       <input value="台站分布图" onclick="queryForList('${ctx}/quake/seismic/station/stationGis.do', '_blank')" style="text-align: center;cursor: auto;" type="button" class="button"/>
       <input value="数据下载" onclick="downloadInXls()" size="9" style="text-align: center;cursor: auto;" type="button" class="button"/>
	   </td> 
       </tr>
       
     </table>
     </s:form>
</fieldset>
<div class="x-panel-body">
	  <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
		action="list.do"
		useAjax="true" doPreload="false"
		maxRowsExported="10000000" 
		xlsFileName="台站信息列表.xls" 
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
		<ec:column width="70" property="STA_CNAME" title="台站名称" />
		<ec:column width="70" property="STA_CODE" title="台站代码" />
		<ec:column width="70" property="NET_CODE" title="台网名称" mappingItem="networkInfoMap"/>
		<ec:column width="70" property="ROCK_TYPE" title="台基" />
		<stc:role ifAllGranted="ROLE_LONGLAT">
			<ec:column width="70" property="STA_LON" title="经度"/>
			<ec:column width="70" property="STA_LAT" title="纬度"/>		
		    <ec:column width="70" property="STA_ELEV" title="高程(m)" />
		</stc:role>
		<ec:column width="110" property="instrModel" title="地震计" />
		<ec:column width="110" property="dasModel" title="数采" />
	  </ec:row>
	</ec:table>
</div>
<script type="text/javascript">
function queryForList(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	$("#queryFrm").submit();
}
//提供测震台站查询访问地址，并重定向到第三方提供的JSP页面。
function xmlUrlPath() {
	var cUrl = '${ctx}';
	var stDate = document.getElementById("startDate").value;
	var edDate = document.getElementById("endDate").value;
	var instrId = document.getElementById("instrId").value;
	var datId = document.getElementById("datId").value;
	window.location='${ctx}'+ "/quake/seismic/station/xmlUrlJsp.do?ctxUrl="+cUrl+"&model.startDate="+stDate+"&model.endDate="+edDate+"&model.instrumentId="+instrId+"&model.datarecordId="+datId;
}
//以xls格式导出测震台站信息列表
function downloadInXls() {
	ECSideUtil.doExport('xls','测震台站信息列表.xls','','ec');
}
</script>
</body>
</html>