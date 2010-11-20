<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/quake.jsp" %>
<title></title>
</head>

<body>
<table width="100%" style="margin-top: -10px;margin-bottom: -2px;">
<tr>
	<td>
<fieldset>
	<legend>测震台站查询</legend>
	<s:form action="list" theme="simple" id="queryFrm">
     <table width="100%">
     <tr>
       <td>台网:</td>
       <td><s:select list="netCodes" name="model.netCode" headerKey="" headerValue="全部" cssStyle="width:100px;"></s:select></td>
  	   <td >台站名称:</td>
  	   <td><s:textfield name="model.staName" cssStyle="width:100px;"/></td>
  	   <td>日期:</td>	
     	<td><s:textfield size="14" cssClass="Wdate" id="startDate" name="model.startDate" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});" readonly="true"/></td>	
		<td>至:</td>
		<td><s:textfield size="14" cssClass="Wdate" id="endDate" name="model.endDate" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});" readonly="true"/></td>
		<td><input value="查询" onclick="queryForList('${ctx}/quake/seismic/station/list.do', '')" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/></td>
	</tr>
       <tr>
       	 <td>台基:</td>
      	 <td><s:select list="rockTypes" name="model.rockType" headerKey="" headerValue="全部" cssStyle="width:100px;"></s:select></td>
  	     <td >台站类型:</td>
  	     <td><s:select list="staTypes" name="model.staType" headerKey="" headerValue="全部" cssStyle="width:100px;"></s:select></td>
  		 <td> 地震计:</td>
         <td><s:select id="sensorModel" name="model.sensorModel" list="sensorMap" listKey="instrModel" cssStyle="width:100px;" listValue="instrModel" headerKey="" headerValue="--所有类型--" /></td>
  	     <td >数采:</td>
  	     <td><s:select id="digitizerModel" name="model.digitizerModel" list="digitizerMap" listKey="instrModel" cssStyle="width:100px;" listValue="instrModel" headerKey="" headerValue="--所有类型--" /></td>
	   <td>
	   	  <input value="台站分布图" onclick="queryForList('${ctx}/quake/seismic/station/stationGis.do', '_blank')" style="text-align: center;cursor: auto;" type="button" class="button"/>
       	 </td>
       </tr>
     </table>
     </s:form>
</fieldset>
</td>
	<td align="right">
	<fieldset>
			<legend>下载</legend>
			<table width="100%" style="margin:0px;">
			<tr>
				<td height="55">&nbsp;
				  <input value="数据下载" onclick="downloadInXls()" size="9" style="text-align: center;cursor: auto;" type="button" class="button"/>
	   </td>
			</tr>
			</table>
		</fieldset>
	</td>
  </tr>
</table>
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
		height="280px" 
		minHeight="280"
		excludeParameters="selectedItems"
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	  <ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false" />
		<ec:column width="80" property="STA_CNAME" title="台站名称" />
		<ec:column width="80" property="STA_CODE" title="台站代码" />
		<ec:column width="80" property="NET_CODE" title="台网名称" mappingItem="networkInfoMap"/>
		<ec:column width="80" property="ROCK_TYPE" title="台基" />
		<ec:column width="80" property="STA_TYPE" title="类型" mappingItem="staTypes"/>
		<stc:role ifAllGranted="ROLE_LONGLAT">
			<ec:column width="80" property="STA_LON" title="经度"/>
			<ec:column width="80" property="STA_LAT" title="纬度"/>		
		    <ec:column width="80" property="STA_ELEV" title="高程(m)" />
		</stc:role>
		<ec:column width="120" property="SITE_NAME" title="地址" />
		<ec:column width="400" property="-8" title="操作" style="text-align:center">
			<a href="viewChannels.do?stataionId=${item.ID}" target="_blank">查看通道</a> |
			<a href="xml.do?stataionId=${item.ID}">XML</a>  
			<c:forEach var="c" items="${item.staChannels}">
			|<a href="resp.do?channelId=${c.ID}">${c.CHN_CODE}_RESP</a>
			</c:forEach>	
		</ec:column>
	  </ec:row>
	</ec:table>
</div>
<script type="text/javascript">
function queryForList(url, target) {
	$("#queryFrm").attr("action", url);
	$("#queryFrm").attr("target", target);
	$("#queryFrm").submit();
}
//以xls格式导出测震台站信息列表
function downloadInXls() {
	ECSideUtil.doExport('xls','测震台站信息列表.xls','','ec');
}
</script>
</body>
</html>