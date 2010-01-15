<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>事件信息</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
.mytable {
  border:2;
	width: 100%;
	border-collapse: collapse;
	border-collapse:   separate;   border-spacing:   10px;
	margin-top: 10px;
	margin-bottom: 10px;
}

.mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
	margin-top: 10;
	margin-bottom: 10;
	padding: 6px 6px 6px 6px;
}
#tabs{
  margin-top: -1;
}
</style>
</head>
<body>
<!-- 事件信息 -->
<div class="x-panel">
	<div class="x-panel-header">事件信息</div>
	<div class="x-toolbar">
		<table width="100%">
			<tr>
				<td width="80%" align="left"></td>
				<td align="right"><a href="#"> 地理位置</a></td>
				<td><span class="ytb-sep"></span></td>
				<td align="right"><a href="#"> 上报市级</a></td>
				<td><span class="ytb-sep"></span></td>
				<c:if test="${model.status eq '0'}">
					<td align="right"><a href="#" onclick="showChooseSendTypeWindow(${model.id})"> 任务派遣</a></td>
				</c:if>
				<c:if test="${empty model.assessmentses }">		
				<td><span class="ytb-sep"></span></td>
				<td align="right"><a href="${ctx}/assessment/edit.do?model.fsCase.id=${model.id}"> 风险评估</a></td>
				</c:if>
				<c:if test="${empty model.jointTaskses }">
				<td><span class="ytb-sep"></span></td>
				<td align="right"><a href="${ctx}/jointTask/edit.do?model.fsCase.id=${model.id}"> 联合整治</a></td>
				</c:if>
			</tr>
		</table>
	</div>
	<div id="tabs">
		<div id="fsCaseDiv" class="x-hide-display">
			<table id="fsCaseTable" width="100%" >
				<tr>
					<td align="left" width="100%">
						<table class="mytable" >		
							<tr>
								<td class="simple" width="15%"align="right">案件标题：</td>
								<td class="simple" align="left" ><s:property	value="model.title" /></td>
							</tr>
							<tr>
								<td width="10%"  align="right">案件类别：</td>
								<td align="left" ><s:property	value="model.caseType.name" /></td>
							</tr>
							<tr>
								<td width="10%"  align="right">案件地点：</td>
								<td align="left" ><s:property value="model.address" />
								</td>
							</tr>
							<tr>
								<td width="10%"  align="right">事发时间：</td>
								<td align="left" ><s:date name="model.caseTime"format="yyyy-MM-dd" /></td>
							</tr>
							<tr>
								<td width="10%"  align="right">案件报告人：</td>
								<td  align="left"><s:property value="model.informer" />
								</td>
							</tr>
							<tr>
								<td width="10%"  align="right">报告人电话：</td>
								<td  align="left"><s:property	value="model.informerPhone" /></td>
							</tr>
							<tr>
								<td width="10%"  align="right">案件描述：</td>
								<td  align="left">${model.descn}</td>
							</tr>						
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div id="general" class="x-hide-display" >
			<!-- include进来二级Tab以现实一个食品安全综合案件关联的多个一般案件 -->		
			<%@include	file="viewGenericCases.jsp"%>			
		</div>
		<div id="tasks" class="x-hide-display" style="margin: -1; border: 0;">
			<!-- include进来二级Tab以现实一个食品安全案件下的多个任务 -->		
			<%@include file="viewTasks.jsp"%>
		</div>
		<div id="sms" class="x-hide-display">
		<%@include file="viewSmsGrid.jsp" %>
		</div>
	</div>
</div>
<%@include file="chooseSendType.jsp"%>
<%@include file="viewTaskDetailResult.jsp"%>
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
				contentEl : 'fsCaseDiv',
				title : '事件信息'
			}
			<c:if test="${model.isMultiple eq '1'}">			
			,{
				contentEl : 'general',
				title : '相关单体事件'
			}
			
			</c:if>
			<c:if test="${not empty model.taskses }">			
			,{
				contentEl : 'tasks',
				title : '任务信息'
			}
			
			</c:if>			
			<s:if test="1==1">
			, {
				contentEl : 'sms',
				title : '短信收发情况'
			} 
			</s:if>
			]
		});
	});
</script>
</body>
</html>