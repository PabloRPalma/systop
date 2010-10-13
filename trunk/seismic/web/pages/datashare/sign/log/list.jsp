<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/quake.jsp" %>
<title></title>
<script type="text/javascript">
	function querSignLog(){
		document.getElementById("querySignLog").submit();
	}
</script>
</head>
<body>

<div class="x-panel">
  <div class="x-panel-header">
  	<span style="font-size:13px; font-weight:bold; color:#0071BD">前兆数据日志</span>
  </div>
    <div class="x-toolbar">
      <table width="100%">
        <tr>
          <td align="left"> 
	        <form id="querySignLog" action="${ctx}/quake/sign/log/list.do" method="post">
	           <input type="hidden" name="model.stationId" value="${model.stationId}"/>
	           <input type="hidden" name="model.pointId" value="${model.pointId}"/>
	           <input type="hidden" name="model.itemId" value="${model.itemId}"/>
	           <input type="hidden" name="model.tableCategory" value="${model.tableCategory}"/>
		       从<input type="text" id="startDate" name="model.startDate" 
		       value="<s:date name="model.startDate" format="yyyy-MM-dd"/>"
		       onclick="WdatePicker({minDate:'2002-01-01',maxDate:'#F{$dp.$D(\'endDate\')||\'now\'}',skin:'whyGreen'})" size="10">
		       到 <input type="text" id="endDate" name="model.endDate" 
		       value="<s:date name="model.endDate" format="yyyy-MM-dd"/>"
		       onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'now',skin:'whyGreen'})" size="10">
		       <input type="button" value="查询" class="button" onclick="querSignLog()"/>
	         </form>
        	</td>
         <td align="right"><td>
        </tr>
      </table>
    </div>   
    <div class="x-panel-body">
    <div style="margin-left:-3px;" align="left">
    <table width="100%" style="border:1px solid #97B7E7;margin-left:0px; margin-bottom: -1px;">
    	<tr>
    		<td>
	    		<span style="color:#0071BD">台站名称：</span>${stationName}&nbsp;&nbsp;&nbsp;&nbsp;
	    		<span style="color:#0071BD">测点名称：</span>${pointName}&nbsp;&nbsp;&nbsp;&nbsp;
	    		<span style="color:#0071BD">测项分量名称：</span>${itemName}
	    	</td>
    	</tr>
    </table>
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
		action="list.do"
		useAjax="true" doPreload="false"
		maxRowsExported="10000000" 
		pageSizeList="50,100,500" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="50"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%" 	
		height="477px"	
		minHeight="300"  
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" />
		
		<ec:column width="100" property="startDate" title="开始时间"/>
		<ec:column width="100" property="endDate" title="结束时间"/>
		<ec:column width="80" property="evtId" title="事件编号"/>
		<ec:column width="150" property="evtDesc" title="事件描述"/>
		<ec:column width="80" property="logPerson" title="录入人"/>
		<ec:column width="70" property="isProcessed" title="是否处理"/>
		<ec:column width="70" property="isAutoProcessed" title="自动处理"/>
		<ec:column width="160" property="processSoftWare" title="处理软件名称"/>
		<ec:column width="80" property="ProNullNum" title="置空个数"/>
		<ec:column width="80" property="evtPeson" title="预处理人员"/>
		<ec:column width="200" property="evtProcessing" title="预处理依据"/>
		<ec:column width="100" property="proDate" title="处理时间"/>
		<ec:column width="200" property="proDesc" title="处理描述"/>
		<ec:column width="200" property="evtNote" title="备注"/>
	</ec:row>
	</ec:table>
	</div>
	</div>
</div>

</body>
</html>