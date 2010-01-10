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
	var tabItemsStr = "[{contentEl : 'basic',title : '案件信息'}";
</script>
<c:if test="${1==1}">
	<script type="text/javascript">
	tabItemsStr += ",{contentEl : 'descr',title : '任务信息'}";
</script>
</c:if>
<c:if test="${1==1}">
	<script type="text/javascript">
	tabItemsStr += ",{contentEl : 'reward',title : '任务处理情况'}";
	tabItemsStr +="{contentEl : 'punish',title : '短信收发情况'} ]";
	//alert(tabItemsStr);
</script>
</c:if>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			activeTab : ${param['modelId']},
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '案件信息'
			}
			<c:if test="${model.isMultiple eq '1'}">			
			,{
				contentEl : 'general',
				title : '相关一般案件'
			}
			
			</c:if>
			<c:if test="${not empty model.taskses }">			
			,{
				contentEl : 'descr',
				title : '任务信息'
			}
			
			</c:if>
			
			<s:if test="not empty  model.taskses[0].taskDetails">
			, {
				contentEl : 'reward',
				title : '任务处理情况'
			}
			</s:if>
			<s:if test="1==1">
			, {
				contentEl : 'punish',
				title : '短信收发情况'
			} 
			</s:if>
			]
		});
	});
</script>
<s:if test="">
</s:if>
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
<c:forEach items="model.taskses" var="task" begin="0" end="1">
	<c:if test=""></c:if>
</c:forEach>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">案件信息</div>
<div class="x-toolbar">
<table width="100%">
	<tr>
		<td width="75%" align="left"><a
			href="${ctx}/genericcase/index.do"><img
			src="${ctx}/images/icons/house.gif" />首页列表</a></td>
		<td align="right"><a href="#"> 地理位置</a></td>
		<td><span class="ytb-sep"></span></td>
		<td align="right"><a href="#"> 上报市级</a></td>
		<td><span class="ytb-sep"></span></td>
		<c:if test="${model.status eq '0'}">
			<td align="right"><a
				href="${ctx}/sendType/chooseSendType.do?caseId=${model.id}&modelId=${param['modelId']}&isMultipleCase=${param['isMultipleCase']}"> 任务派遣</a></td>
			<td><span class="ytb-sep"></span></td>
		</c:if>
		<td align="right"><a href="${ctx}/assessment/edit.do?model.fsCase.id=${model.id}"> 风险评估</a></td>
		<td></td>
		<td align="right"><a href="#"> 联合整治</a></td>
	</tr>
</table>
</div>
<div id="tabs">
<div id="basic" class="x-hide-display">
<table id="mytable">
	<tr>
		<td width="800" align="left">
		<fieldset style="width: 800px; padding: 5px 10px 5px 10px;">
			<legend>案件信息</legend>
		<table width="400" align="left" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="simple" width="100" align="right">案件标题：</td>
				<td class="simple" align="left" width="149"><s:property
					value="model.title" /></td>
			</tr>
			<tr>
				<td width="100" align="right">案件类别：</td>
				<td align="left" width="149"><s:property
					value="model.caseType.name" /></td>
			</tr>
			<tr>
				<td width="100" align="right">案件地点：</td>
				<td align="left" width="149"><s:property value="model.address" />
				</td>
			</tr>
			<tr>
				<td width="100" align="right">事发时间：</td>
				<td align="left" width="149"><s:date name="model.caseTime"
					format="yyyy-MM-dd" /></td>
			</tr>
			<tr>
				<td width="100" align="right">案件报告人：</td>
				<td width="149" align="left"><s:property value="model.informer" />
				</td>
			</tr>
			<tr>
				<td width="100" align="right">报告人电话：</td>
				<td width="149" align="left"><s:property
					value="model.informerPhone" /></td>
			</tr>
			<tr>
				<td width="100" align="right">案件描述：</td>
				<td width="149" align="left">${model.descn}</td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>
</table>
</div>
<div id="general" class="x-hide-display">
<table id="mytable" height="520">
	<tr>
		<td height="500" align="left" valign="top">
		<!-- include进来二级Tab以现实一个食品安全综合案件关联的多个一般案件 -->
		<%--@include	file="viewGenericCases.jsp" --%>
		</td>
	</tr>
</table>
</div>
<div id="descr" class="x-hide-display">
<table id="mytable" height="400">
	<tr>
		<td height="400" align="left" valign="top">
		<!-- include进来二级Tab以现实一个食品安全案件下的多个任务 -->
		<%@include	file="viewTasks.jsp"%>
		</td>
	</tr>
</table>
</div>
<div id="reward" class="x-hide-display">
<table id="mytable" height="520">
	<tr>
		<td height="500" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;"></div>
		</td>
	</tr>
</table>
</div>
<div id="punish" class="x-hide-display">
<table id="mytable" height="520">
	<tr>
		<td height="500" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
		<table>
		<tr>
		<td>No.</td>
		<td>电话号码</td>
		<td>短信内容</td>
		<td>类别</td>
		<td>状态</td>
		</tr>
		<c:forEach items="${model.smsReceiveses}" var="smsReceive" varStatus="varStatus">
		<tr>
		<td>${varStatus.index+1}</td>
		<td>${smsReceive.mobileNum }</td>
		<td>${smsReceive.content}</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		</tr>
		</c:forEach>
		</table>
		</div>
		</td>
	</tr>
</table>
</div>
</div>
</div>
</body>
</html>