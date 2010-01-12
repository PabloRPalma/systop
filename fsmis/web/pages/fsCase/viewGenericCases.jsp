<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<div id="tabs3" style="margin: -1;">
<c:forEach items="${model.genericCases}"
	var="genericCase" varStatus="varStatus">
	<div id="genericCasesDiv${varStatus.index+1}" class="x-hide-display">
	<table id="mytable" align="left">
		<tr>
			<td>
			<fieldset style="width: 800px; padding: 5px 10px 5px 10px;">
			<legend>案件${varStatus.index+1}信息</legend>
			<table width="800px" align="left">
				<tr>
					<td align="right" width="15%">案件标题：</td>
					<td colspan="4" align="left">${genericCase.title }</td>
				</tr>
				<tr>
					<td align="right" width="15%">案件类别：</td>
					<td colspan="4" align="left">${genericCase.caseType.name }</td>
				</tr>
				<tr>
					<td align="right" width="15%">案件地点：</td>
					<td colspan="4" align="left">${genericCase.address }</td>
				</tr>
				<tr>
					<td align="right" width="15%">案件时间：</td>
					<td colspan="4" align="left">${genericCase.caseTime }</td>
				</tr>
				<tr>
					<td align="right" width="15%">案件报告人：</td>
					<td colspan="4" align="left">${genericCase.informer }</td>
				</tr>
				<tr>
					<td align="right" width="15%">案件报告人电话：</td>
					<td colspan="4" align="left">${genericCase.informerPhone }</td>
				</tr>
				<tr>
					<td align="right" width="15%">案件描述11：</td>
					<td colspan="4" align="left">${genericCase.descn}					
						</td>
				</tr>				
			</table>
			</fieldset>
			</td>
		</tr>		
	</table>	
	</div>
</c:forEach></div>
<script type="text/javascript">
<c:if test="${model.isMultiple eq '1'}">	
	Ext.onReady(function() {
		var tabs3 = new Ext.TabPanel( {
			renderTo : 'tabs3',
			anchor : '100% 100%',
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ 
			
					<c:forEach items="${model.genericCases}" var="genericCase" varStatus="varStatus">
						<c:if test="${varStatus.index>0}">,</c:if>
						{		contentEl : 'genericCasesDiv${varStatus.index+1}',
							title : '案件${varStatus.index+1}'
						} 
					</c:forEach>					
					]
		});
	});	
</c:if>
</script>