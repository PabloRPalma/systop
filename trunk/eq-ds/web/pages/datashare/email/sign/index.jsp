<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/datashare.jsp" %>


</head>
<body>
<fieldset>
<legend>前兆数据订阅</legend>
	<table width="99%" style="margin:0px;">
		<tr>
			<td></td>
			<td align="right">
			<table>
				<tr>
					<td>
					  每个用户可有${signMailMaxSize}个订阅项
					  <s:if test="items.size() < signMailMaxSize">
						<a href="editNew.do">
						<img src="${ctx}/images/icons/add.gif" /> 添加订阅项
						</a>
					  </s:if>
					</td>
				</tr>
			</table>
			<td>
		</tr>
	</table>
  </fieldset>

   <div class="x-panel-body">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="index.do"
	useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
	pageSizeList="10,20,30" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="10"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="477px"	
	minHeight="240"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"   
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="40" property="_1" title="操作" sortable="false" style="text-align:center">
		   <a href="remove.do?model.id=${item.id}" id="cancel" onClick="return confirm('确定要撤销这个订阅项吗?');"> 
		      <img src="${ctx}/images/icons/delete.gif" style="border:0px" title="撤销"/>
		   </a>
		</ec:column>
		<ec:column width="60" property="stationId" title="台站名称"/>
		<ec:column width="130" property="itemId" title="测项分量"/>
		<ec:column width="60" property="pointId" title="测点代码"/>
		<ec:column width="70" property="sampleRate" title="采样率"/>
		<ec:column width="70" property="dataType" title="数据类型"/>
		<ec:column width="150" property="emailAddr" title="电子邮件"/>
		<ec:column width="60" property="_2" title="审核状态">
		   <c:if test="${item.state == '0'}">
		      <span style="color:red">未审核</span>
		   </c:if>
		   <c:if test="${item.state == '1'}">
		      <span style="color:green">审核通过</span>
		   </c:if>
		</ec:column>
		<ec:column width="120" property="lastSendDate" title="最后发送时间" cell="datashare.base.webapp.DateTimeCell" />
	</ec:row>
	</ec:table>
  </div>
</body>
</html>