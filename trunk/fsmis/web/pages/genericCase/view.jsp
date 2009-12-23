<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<link href="${ctx}/styles/datashare.css" type='text/css' rel='stylesheet'>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(function(){
	var hasReward = $('#rewardContent').val();
	var punishListSize = $('#psRecordSize').val();
	if (hasReward == "" || hasReward == null) {
		$('#reward').hide();
	}
	if (punishListSize == 0) {
		$('#punish').hide();
	}
	// Tabs
	$('#tabs').tabs();
});
</script>
<title>事件列表</title>
<style type="text/css">
#mytable {
	border: 1px solid #A6C9E2;
	margin-left: -21px;
	margin-top: -10px;
	margin-right: -21px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 26;
}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header"></div>
<div class="x-toolbar">
	<table width="99%">
	<tr>
	    <td width="75%" align="left"><a href="${ctx}/genericcase/index.do"><img
					src="${ctx}/images/icons/house.gif" />首页列表</a>
		</td>
		<td align="right"><a href="#"> 地理位置</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="#"> 上报市级</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="#"> 任务派遣</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="#"> 联合整治</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="#"> 查看退回</a></td>
	</tr>
	</table>
</div>
<div class="x-panel-body">
<div id="tabs">
<s:hidden id="psRecordSize" name="psRecordSize" />
<ul>
	<li><a href="#tabs-1">基本信息</a></li>
	<li><a href="#tabs-2">事件描述</a></li>
	<li id="reward"><a href="#tabs-3">任务信息</a></li>
	
</ul>
<div id="tabs-1" style="margin-bottom: -16px;">
  <table id="mytable" height="320">
	<tr>
		<td width="800">
		<table width="800" align="left" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="simple" width="300" align="right">事件标题：</td>
				<td class="simple" align="left" colspan="3"><s:textfield id="title"
					name="model.title" cssStyle="width:370px" readonly="true" theme="simple" /></td>
			</tr>
			<tr>
				<td align="right">事件类别：</td>
				<td align="left" colspan="3"><s:textfield id="address"
					name="model.caseType.name" cssStyle="width:370px" cssClass="inputBorder"
					readonly="true" />
			</tr>
			<tr>
				<td align="right">事发地点：</td>
				<td align="left" colspan="3"><s:textfield
					id="address" name="model.address" cssStyle="width:370px"
					cssClass="inputBorder" readonly="true" /></td>
			</tr>
			<tr>
				<td align="right">事发时间：</td>
				<td align="left" colspan="3"><input type="text"
					name="model.eventDate"
					value='<s:date name="model.eventDate" format="yyyy-MM-dd"/>'
					class="inputBorder" style="height: 16px"
					readonly="readonly" /></td>
			</tr>
			<tr>
				<td align="right">事件报告人：</td>
				<td width="149" align="left"><s:textfield
					id="informer" name="model.informer"
					cssClass="inputBorder" readonly="true" /></td>
			</tr>
			<tr>
				<td align="right">报告人电话：</td>
				<td width="149" align="left"><s:textfield id="informerPhone"
					name="model.informerPhone" cssClass="inputBorder" readonly="true" /></td>
			</tr>
		</table>
		</td>
	</tr>
  </table>
</div>
<div id="tabs-2" style="margin-bottom: -16px;">
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
			${model.descn}</div>
	  </td>
	</tr>
  </table>
</div>
<div id="tabs-3" style="margin-bottom: -16px;">
  <s:hidden id="rewardContent" name="model.integrityRecord" />
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
		</div>
	  </td>
	</tr>
  </table>
</div>

</div>
</div>
</div>
</body>
</html>