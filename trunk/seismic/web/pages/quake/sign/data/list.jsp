<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.lang.Integer"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<%@include file="/common/quake.jsp" %>
<script type="text/javascript">

function stockChart(){
	$("#queryChartFrom").attr("action", "${ctx}/quake/sign/data/stock.do");
	setIds();
	$("#queryChartFrom").submit();
}

function appletkChart(){
	$("#queryChartFrom").attr("action", "${ctx}/quake/sign/data/easyChart.do");
	setIds();
	$("#queryChartFrom").submit();
}

function zip(){
	$("#queryChartFrom").attr("action", "${ctx}/quake/sign/data/zip.do");
	setIds();
	$("#queryChartFrom").submit();
}

function setIds(){
	var itemIds = $("#selectItemIds").attr("value");
	$("#ids").attr("value", itemIds);
}

</script>
<title>前兆数据查询结果</title>
<style type="text/css">
.bomBorder{
	border:1px;
	font-size: 15px;
	font-weight:bold;
	color: #4482d3;
	width:80px;
}
.title{
	font-size: 15px;
	font-weight:bold;
	color: #4482d3;
}
</style>
</head>
<body>

<div class="x-panel">
    <div class="x-toolbar">
    <fieldset>
      <legend>前兆数据</legend>
      <form id="queryChartFrom" method="post">
      <table width="90%">
        <tr>
          <td> 
				时间：
				<input type="text" readonly="readonly" id="startDate" name="model.startDate" 
				value="<s:date name="model.startDate" format="yyyy-MM-dd"/>" style="border:none;width:90px;"/>至
				<input type="text" readonly="readonly"  id="endDate" name="model.endDate" 
				value="<s:date name="model.endDate" format="yyyy-MM-dd"/>" style="border:none;width:90px;"/>
				<input type="hidden" id="ids" name="ids">
				<input type="hidden" id="sampleRate" name="model.sampleRate" value="${model.sampleRate}"/>
				<input type="hidden" id="tableCategory" name="model.tableCategory" value="${model.tableCategory}"/>
		 </td>
		 <td align="right">
				<s:if test="#attr.ROW_COUNTS < 20000">
					<a href="#" onclick="stockChart()" title="Flash 曲线图">
						<img src="${ctx}/images/icons/chart_curve.jpg">&nbsp;查看曲线图
					</a>
				</s:if>
				<s:else>
					<a href="#" onclick="appletkChart()" title="Java Applet 曲线图">
						<img src="${ctx}/images/icons/chart_curve.jpg">&nbsp;查看曲线图
					</a>
				</s:else>
				&nbsp;
				<a href="#" onclick="zip()" title="ZIP格式打包下载">
						<img src="${ctx}/images/icons/zip-icon.jpg">&nbsp;数据下载
				</a>
			 <s:hidden id="selectItemIds" name="selectItemIds" cssStyle="width:350px"/>
	     </td>
         <td align="right"><td>
        </tr>
      </table>
      </form>
      </fieldset>
    </div>   
    <div class="x-panel-body">
    <div style="margin:0px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
		action="list.do"
		useAjax="true" doPreload="false"
		pageSizeList="30,60,100" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="30"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%"
		height="450px"	
		minHeight="450"  
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:extendrow location="top">
			<tr class="tableHeaderH" height="50px">
				<%
					int colSize = (Integer)request.getAttribute("colSize");
					if (colSize > 1){
						String[] comTitles = (String[])request.getAttribute("comTitles");
						for(int i = 0; i < colSize/2; i++){
							out.println("<td colspan='2' style='border-bottom:0px;'>" + comTitles[i] + "</td>");
						}
					}else{
						out.println("<td>&nbsp;</td>");
					}
				%>
			</tr>
		</ec:extendrow>
	<ec:row>
		<ec:columns autoGenerateColumns="org.ecside.core.bean.AutoGenerateColumnsImpl" 
		widths="${widths}"/>
	</ec:row>
	</ec:table>
	</div>
	</div>
</div>

</body>
</html>