<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>案件列表</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'basic', title: '事件信息'},
            {contentEl:'descr', title: '任务信息'},
            {contentEl:'reward', title: '任务处理情况'},
            {contentEl:'punish', title: '短信收发情况'}
        ]
    });
});
</script>
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
	height: 26;
}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">事件信息</div>
<div class="x-toolbar">
    <table width="100%">
     <tr>
	    <td width="75%" align="left"><a href="${ctx}/genericcase/index.do"><img
					src="${ctx}/images/icons/house.gif" />首页列表</a>
		</td>
		<td align="right"><a href="#"> 地理位置</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="#"> 上报市级</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="${ctx}/sendtype/send.do?fsCase.id=${model.id}"> 任务派遣</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="#"> 联合整治</a></td>
	</tr>
    </table>
</div>
<div id="tabs">
<div id="basic" class="x-hide-display">
  <table id="mytable" height="320">
	<tr>
		<td width="800">
		<table width="400" align="left" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="simple" width="300" align="right">事件标题：</td>
				<td class="simple" align="left" width="149" >
				   <s:property value="model.title" />
				</td>
			</tr>
			<tr>
				<td width="300" align="right">事件类别：</td>
				<td align="left" width="149" >
					<s:property value="model.caseType.name" />
				</td>
			</tr>
			<tr>
				<td width="300" align="right">事发地点：</td>
				<td align="left" width="149" >
					<s:property value="model.address" />
				</td>
			</tr>
			<tr>
				<td width="300" align="right">事发时间：</td>
				<td align="left" width="149" >
				    <s:date name="model.caseTime" format="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td width="300" align="right">事件报告人：</td>
				<td width="149" align="left">
				    <s:property value="model.informer" />
				</td>
			</tr>
			<tr>
				<td width="300" align="right">报告人电话：</td>
				<td width="149" align="left">
					 <s:property value="model.informerPhone" />
				</td>
			</tr>
			<tr>
				<td width="300" align="right">事件描述：</td>
				<td width="149" align="left">
					 ${model.descn}
				</td>
			</tr>
		</table>
		</td>
	</tr>
  </table>
</div>
<div id="descr" class="x-hide-display">
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
		</div>
	  </td>
	</tr>
  </table>
</div>
<div id="reward" class="x-hide-display">
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
		</div>
	  </td>
	</tr>
  </table>
</div>
<div id="punish" class="x-hide-display">
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;"></div>
	  </td>
	</tr>
  </table>
</div>
</div>
</div>

</body>
</html>