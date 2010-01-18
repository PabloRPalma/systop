<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>统计短信发送列表</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>

</head>

<body>
<div class="x-panel">
<div class="x-panel-header">短信发送</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td style="padding: 5px 0px 3px 10px;"> 
			<form action="index.do" method="post" >
				 <!-- 选择查询起始时间 -->
                      起始时间
                 <input type="text" id="beginDate" name="beginDate" value="${param.beginDate}"
                  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate required"  readonly/>      
                 <!-- 选择查询结束时间 --> 
                      结束时间
                 <input type="text" id="endDate" name="endDate" value="${param.endDate}"
                  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate required"  readonly/> 
				<input type="submit" value="查询" class="button">
			</form>
		</td>
		<td align="right">
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="462px"
	minHeight="462"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="120" property="sendDate" title="创建日期"
			style="text-align:center" cell="date" />
		<ec:column width="110" property="mobileCount" style="text-align:center" title="移动发送数" />
		<ec:column width="110" property="otherCount" style="text-align:center" title="联通及其他发送数" />
		<ec:column width="110" property="_1" style="text-align:center" title="合计">
		${item.mobileCount+item.otherCount}
		</ec:column>
	</ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>