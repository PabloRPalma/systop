<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>专题地震列表</title>
<%@include file="/common/quake.jsp" %>
</head>
<body>
<s:form action="indexMore" method="post">
<fieldset>
		<legend>地震专题查询</legend>
<table width="100%">
	<tr>
		<td>
				创建时间:
			<input type="text" name="beginDate" style="width: 80px"
				value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" />
						</>至
			<input type="text" name="endDate" style="width: 80px"
				value='<s:date name="endDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" />
			                              专题标题:
			<s:textfield name="model.title" cssStyle="width:100px"></s:textfield>	
			<s:submit value="查询" cssClass="button"></s:submit>
		</td>
		<td align="right">
		</td>
	</tr>
</table></fieldset></s:form>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	action="indexMore.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="30,50,100,200" 
	editable="false"
	sortable="false" 
	rowsDisplayed="30" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="310px"
	minHeight="310"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_no" value="${GLOBALROWCOUNT}" title="No." style="text-align:center"/>
		<ec:column width="180" property="title" title="标题"/>
		<ec:column width="180" property="quakeTime" title="发震时刻" style="text-align:center"/>
		<ec:column width="60" property="longitude" title="经度" style="text-align:center"/>
		<ec:column width="60" property="latitude" title="纬度" style="text-align:center"/>
		<ec:column width="60" property="magnitude" title="震级" style="text-align:center"/>
		<ec:column width="160" property="location" title="地点"/>
		<ec:column width="160" property="createDate" title="创建时间" style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm"/>
	</ec:row>
</ec:table></div>
</div>
</body>
</html>