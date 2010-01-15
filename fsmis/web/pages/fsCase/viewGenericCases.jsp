<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<div id="genericCasesDiv" style="margin: -1;">
<c:forEach items="${model.genericCases}"
	var="genericCase" varStatus="varStatus">
	<div id="genericCasesDiv${varStatus.index+1}" class="x-hide-display">
	<table class="mytable" align="left">
		<tr>
			<td>
			<table width="800px" align="left">
				<tr>
					<td align="right" width="15%">事件标题：</td>
					<td colspan="4" align="left">${genericCase.title }</td>
				</tr>
				<tr>
					<td align="right" width="15%">事件类别：</td>
					<td colspan="4" align="left">${genericCase.caseType.name }</td>
				</tr>
				<tr>
					<td align="right" width="15%">事件地点：</td>
					<td colspan="4" align="left">${genericCase.address }</td>
				</tr>
				<tr>
					<td align="right" width="15%">事件时间：</td>
					<td colspan="4" align="left">${genericCase.caseTime }</td>
				</tr>
				<tr>
					<td align="right" width="15%">事件报告人：</td>
					<td colspan="4" align="left">${genericCase.informer }</td>
				</tr>
				<tr>
					<td align="right" width="15%">报告人电话：</td>
					<td colspan="4" align="left">${genericCase.informerPhone }</td>
				</tr>
				<tr>
					<td align="right" width="15%">事件描述：</td>
					<td colspan="4" align="left">${genericCase.descn}					
						</td>
				</tr>				
			</table>
			</td>
		</tr>		
	</table>	
	</div>
</c:forEach></div>
<script type="text/javascript">
<c:if test="${model.isMultiple eq '1'}">	
	Ext.onReady(function() {
		var tabs3 = new Ext.TabPanel( {
			renderTo : 'genericCasesDiv',
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
							title : '事件${varStatus.index+1}'
						} 
					</c:forEach>					
					]
		});
	});	
</c:if>
</script>