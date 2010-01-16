<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>上报事件</title>
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
			height : 400,
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '事件信息'
			}, {
				contentEl : 'hospital',
				title : '处理结果'
			}, {
				contentEl : 'traffic',
				title : '处理企业'
			} ]
		});
	});
</script>
<div class="x-panel">
<div class="x-panel-header">上报事件</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="${ctx}/casereport/index.do">
				上报事件列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form id="save" action="save.do" method="post" theme="simple"
	validate="true">
<div id="tabs" style="margin-top: -1px;margin-left: -1px;margin-right: -1px;">
<div id="basic" class="x-hide-display"><s:hidden name="model.id" />
	<table id="mytable" height="360" style="margin-top: 5px; width: 800px"
		align="center">
		<tr>
			<td width="163" height="30" align="right">事件标题：</td>
			<td align="left" colspan="3"><s:textfield id="title" readonly="true"
				name="model.title" cssStyle="width:550px" cssClass="required" /></td>
		</tr>
		<tr>
			<td align="right" height="30">事发时间：</td>
			<td width="263" align="left">${model.caseTime}</td>
			<td width="102" align="right">事件类型：</td>
			<td width="252" align="left">
				<c:if test="${model.caseType != null}">
					${model.caseType.name}
				</c:if>
			</td>
		</tr>
		<tr>
			<td align="right" >事发地点：</td>
			<td align="left" colspan="3"><s:textfield id="address" readonly="true"
				name="model.address" cssStyle="width:550px" cssClass="required" /></td>
		</tr>
		<tr>
			<td align="right" height="30">举&nbsp;&nbsp;报&nbsp;&nbsp;人：</td>
			<td align="left"><s:textfield id="informer" readonly="true"
				name="model.informer" cssStyle="width:175px" />
			</td>
			<td align="right" height="30">举报人电话：</td>
			<td align="left"><s:textfield id="informerPhone" readonly="true"
				name="model.informerPhone" cssStyle="width:175px" />
			</td>
		</tr>
		<tr>
			<td align="right">事件描述：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea readonly="true"
				id="descn" name="model.descn" cssStyle="width:550px; height:230px"
				cssClass="required" /></td>
		</tr>
	</table>
</div>
<div id="hospital" class="x-hide-display">
	<table id="mytable" height="360" style="margin-top: 5px; width: 800px"
		align="center">
		<s:hidden name="taskDetail.id" />
		<s:hidden name="task.id" />
		<tr>
			<td width="163" height="28" align="right">处&nbsp;&nbsp;理&nbsp;&nbsp;人：</td>
			<td align="left" colspan="3"><s:textfield id="processor" readonly="true"
				name="taskDetail.processor" cssStyle="width:550px" cssClass="required" /></td>
		</tr>
		<tr>
			<td align="right" height="33">调查时间：</td>
			<td width="263" align="left">${task.dispatchTime}</td>
			<td width="102" align="right">结案时间：</td>
			<td width="252" align="left">${taskDetail.completionTime}</td>
		</tr>
		<tr>
			<td align="right">处理过程：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea readonly="true"
				id="process" name="taskDetail.process" cssStyle="width:550px; height:90px"
				cssClass="required" /></td>
		</tr>
		<tr>
			<td align="right">处理结果：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea readonly="true"
				id="result" name="taskDetail.result" cssStyle="width:550px; height:90px"
				cssClass="required" /></td>
		</tr>
		<tr>
			<td align="right">处理依据：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea readonly="true"
				id="basis" name="taskDetail.basis" cssStyle="width:550px; height:90px"
				cssClass="required" /></td>
		</tr>
	</table>
</div>
<div id="traffic" class="x-hide-display">
	<table id="mytable" style="margin-top: 5px; width: 800px"
		align="center">
	  <s:hidden name="corp.id" />
		<tr>
			<td width="165" align="right" height="26">单位名称：</td>
			<td width="623" align="left"><s:textfield id="companyName" readonly="true"
				name="corpName" cssStyle="width:550px" cssClass="required"/></td>
		</tr>
		<tr>
			<td align="right" height="26"> 单位地址：</td>
			<td align="left"><s:textfield id="companyAddress" readonly="true"
				name="corp.address" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">法人名称：</td>
			<td align="left"><s:textfield id="legalPerson" readonly="true"
				name="corp.legalPerson" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">生产许可证：</td>
			<td align="left"><s:textfield id="produceLicense" readonly="true"
				name="corp.produceLicense" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">经营许可证：</td>
			<td align="left"><s:textfield id="businessLicense" readonly="true"
				name="corp.businessLicense" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">卫生许可证：</td>
			<td align="left"><s:textfield id="sanitationLicense" readonly="true"
				name="corp.sanitationLicense" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="20">经营范围：</td>
			<td align="left">
				<s:textarea id="operateDetails" name="corp.operateDetails" cssStyle="width:550px; height:180px" readonly="true"/>
			</td>
		</tr>
	</table>
</div>
</div>
</s:form></div>
</body>
</html>