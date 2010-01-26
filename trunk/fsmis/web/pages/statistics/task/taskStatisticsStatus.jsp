<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/extjs.jsp" %>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<script type="text/javascript" src="${ctx}/amcharts/swfobject.js"></script>
</head>
<body>
<div class="x-panel-header">任务状态统计图</div>
<div class="x-toolbar" style="height:25px;padding:3px;">
<table width="99%">
  <tr>
      <s:form id="supervisorstatistic" action="statisticTaskStatus.do" method="post" target="main" >
		<td width="60" align="right">所属部门：</td>
		<td class="simple" align="left" width="160">
			<div id="comboxWithTree" style="float: left;width: 100px"></div>
			<s:hidden name="deptId" id="deptId"/>
		</td>
		<td>
			时间：
				<input type="text" name="beginDate" style="width: 120px" id="minDate"
					value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'maxDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					class="Wdate" />
				至
				<input type="text" name="endDate" style="width: 120px" id="maxDate"
					value='<s:date name="endDate" format="yyyy-MM-dd"/>'
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'minDate\')}',skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					class="Wdate" />
			<s:submit value="查询" cssClass="button"/>
	     </td>     
	</s:form>
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
		<td align="center">
			<div id="flashcontent"><strong>你需要更新你的flash了。</strong></div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/ampie.swf", "ampie", "750", "550",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/pages/statistics/task/setting/flatPie.xml"));
	so.addVariable("chart_data", "${csvData}");
	so.write("flashcontent");
	// ]]>
</script>
<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var dtree = new DeptTree({
		url : '/admin/dept/deptTree.do',
		parent : '<stc:loginUserDept showPath="false" propertyName="id" showTopDept="true"></stc:loginUserDept>',
		initValue : '${deptName}',
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