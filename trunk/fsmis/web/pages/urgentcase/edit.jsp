<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<style type="text/css">
#mytable {
	border: 0px solid #A6C9E2;
	margin-left: 0px;
	margin-top: 0px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
}
</style>
</head>
<body>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			height : 380,
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '应急事件'
			}, {
				contentEl : 'hospital',
				title : '周边医院'
			}, {
				contentEl : 'traffic',
				title : '周边交通'
			} ]
		});
	});
</script>
<div class="x-panel">
<div class="x-panel-header">编辑应急事件</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="${ctx}/urgentcase/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 应急事件列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form id="save" action="save.do" method="post" theme="simple" validate="true">
<div id="tabs">
<div id="basic" class="x-hide-display"><s:hidden name="model.id" />
<table id="mytable" height="380">
	<tr>
		<td align="right" width="200">事件名称：</td>
		<td align="left" width="550" colspan="3"><s:textfield id="title"
			name="model.title" cssStyle="width:500px" /><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">事发时间：</td>
		<td align="left"><input type="text" name="model.caseTime"
			value='<s:property value="model.caseTime"/>'
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
			class="Wdate" /><font color="red">&nbsp;*</font></td>
		<td width="90" align="right">预案等级：</td>
        <td width="213" align="left">
        </td>
	</tr>
	<tr>
		<td align="right">事发地点：</td>
		<td align="left" colspan="3"><s:textfield id="address"
			name="model.address" cssStyle="width:500px" /><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">波及范围：</td>
		<td align="left" colspan="3"><s:textfield id="caseRange"
			name="model.caseRange" cssStyle="width:500px" /></td>
	</tr>
	<tr>
		<td align="right">受害人数：</td>
		<td align="left"><s:textfield id="harmNum"
			name="model.harmNum" cssStyle="width:50px" /></td>
		<td align="right">死亡人数：</td>
		<td align="left"><s:textfield id="deathNum"
			name="model.deathNum" cssStyle="width:50px" /></td>
	</tr>
	<tr>
		<td align="right">事件报告人：</td>
		<td align="left"><s:textfield id="reporter"
			name="model.reporter" cssStyle="width:120px" /><font
			color="red">&nbsp;*</font></td>
		<td align="right">报告人电话：</td>
		<td align="left"><s:textfield id="reporterPhone"
			name="model.reporterPhone" cssStyle="width:120px" /><font
			color="red">&nbsp;*</font></td>
	</tr>
	<tr>
		<td align="right">报告人单位：</td>
		<td align="left"><s:textfield id="reporterUnits"
			name="model.reporterUnits" cssStyle="width:500px" /></td>
	</tr>
	<tr>
		<td align="right">事件描述：</td>
		<td align="left"><s:textarea id="descn"
			name="model.descn" cssStyle="width:500px; height:150px" /></td>
	</tr>
</table>
</div>
<div id="hospital" class="x-hide-display">
<table id="mytable" height="380">
	<tr>
		<td align="right">周边医院情况：</td>
		<td align="left" style="vertical-align: top;" width="100%"><s:textarea
			id="hospitalInf" name="model.hospitalInf"
			cssStyle="width:500px; height:70px" /></td>
	</tr>
</table>
</div>
<div id="traffic" class="x-hide-display">
<table id="mytable" height="380">
	<tr>
		<td align="right">周边交通情况：</td>
		<td align="left" style="vertical-align: top;" width="100%"><s:textarea
			id="trafficInf" name="model.trafficInf"
			cssStyle="width:500px; height:70px" /></td>
	</tr>
</table>
</div>
</div>
<table width="100%" style="margin-bottom: 0px;">
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td style="text-align: center;"><s:submit value="保存"
			cssClass="button" />&nbsp;&nbsp; <s:reset value="重置"
			cssClass="button" /></td>
	</tr>
</table>
</s:form>
</div>
</body>
</html>