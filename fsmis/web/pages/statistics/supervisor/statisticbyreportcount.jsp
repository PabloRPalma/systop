<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp" %>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/amcharts/swfobject.js"></script>
</head>
<body>
<div class="x-panel-header">信息员举报统计</div>
<div class="x-toolbar">
<table width="100%" border="0">
	<tr>
		<s:form id="supervisorstatistic" action="statisticReportCount.do"
			method="post" target="main">
			<td width="60" align="right">所属部门：</td>
			<td class="simple" align="left" width="160">
				<div id="comboxWithTree" style="float: left;width: 100px"></div>
				<s:hidden name="deptId" id="deptId"/>
			</td>
			<td align="left">
				时间：
				<input type="text" name="beginDate" style="width: 120px"
					value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					class="Wdate" />
				至
				<input type="text" name="endDate" style="width: 120px"
					value='<s:date name="endDate" format="yyyy-MM-dd"/>'
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					class="Wdate" />
				图显：
				<s:textfield name="displayNum" cssStyle="width:30px"></s:textfield>个
				<s:submit value="查询" cssClass="button"/>
			</td>
		</s:form>
		<td align="right">
		<table>
			<tr>
				<td><span class="ytb-sep"></span></td>
				<td><a href="statisticReportCount.do">举报统计</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="statisticByDept.do">部门统计</a></td>
		</table>
		</td>
	</tr>
</table>
</div>
<table width="532" align="center">
		  <tr>
			<td colspan="4"><%@ include file="/common/messages.jsp"%></td>
		  </tr>
		</table>
<table width="100%">
	<tr>
		<td align="left">
			<div id="flashcontent"><strong>你需要更新你的flash了。</strong></div>
		</td>
		<td align="left">
			<div class="x-panel-body">
			<div style="margin-left: -3px;" align="center">
			<ec:table
				items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
				action="statisticReportCount.do" 
				useAjax="false"
				doPreload="false" 
				pageSizeList="10" 
				editable="false"
				sortable="true" 
				rowsDisplayed="10" 
				generateScript="true"
				resizeColWidth="false" 
				classic="false" 
				width="100%" 
				height="300px"
				minHeight="300"
				toolbarContent="pagejump|pagesize|status">
				<ec:row>
					<ec:column width="35" property="_No" title="名次" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
					<ec:column width="70" property="name" title="姓名" sortable="false" />
					<ec:column width="120" property="dept.name" title="所属部门" sortable="false"/>
					<ec:column width="60" property="_reportCount" title="举报次数" sortable="false">
						${fn:length(item.fsCase)}
					</ec:column>
				</ec:row>
			</ec:table></div>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "column", "500", "450",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/supervisor/column_reportcount.xml"));
	so.addVariable("chart_data", "${result}");
	so.write("flashcontent");
	// ]]>
</script>
<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var dtree = new DeptTree({
		url : '/admin/dept/deptTree.do',
		parent : '<stc:loginUserDept showPath="false" propertyName="id" showTopDept="true"></stc:loginUserDept>',
		initValue : '${model.dept.name}',
		el : 'comboxWithTree',
		innerTree :'inner-tree',
		onclick : function(nodeId) {
		  Ext.get('deptId').dom.value = nodeId;
		}
	});
	dtree.init();	
});
</script>
</body>
</html>