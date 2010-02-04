<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>联合整治管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function remove(id){
	if (confirm("确认要删除联合整治事件信息吗?")){
		window.location.href="${ctx}/jointTask/caseRemove.do?model.fsCase.id=" + id;	
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">联合整治事件列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="caseIndex" method="post">
			事件标题：
			<s:textfield name="model.fsCase.title" cssStyle="width:200px"></s:textfield>&nbsp;	
			事件编号：
			<s:textfield name="model.fsCase.code" cssStyle="width:70px"></s:textfield>&nbsp;
			事发时间：
			<input type="text" id="caseBeginTime" name="caseBeginTime" style="width: 120px"
				value='<s:date name="caseBeginTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'caseEndTime\')}',skin:'whyGreen',readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />&nbsp;
			至
			<input type="text" id="caseEndTime" name="caseEndTime" style="width: 120px"
				value='<s:date name="caseEndTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({minDate:'#F{$dp.$D(\'caseBeginTime\')}',skin:'whyGreen',readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />	
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
	    <td align="right">
	      <a href="${ctx}/jointTask/index.do">任务列表</a>
	    </td> 		
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	action="caseIndex.do" useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="false" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="400px"
	minHeight="400"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="30" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>	
		<ec:column width="240" property="title" title="事件标题" sortable="false"/>
		<ec:column width="90" property="code" title="事件编号" sortable="false"/>
		<ec:column width="120" property="caseType.name" title="事件类别" sortable="false"/>
		<ec:column width="120" property="caseTime" title="事发时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" sortable="false"/>
		<ec:column width="80" property="status" title="事件状态"  mappingItem="stateColorMap" style="text-align:center" sortable="false"/>
	
		<ec:column width="70" property="_6" title="查看" style="text-align:center" sortable="false">
	       <c:choose>
		     <c:when test="${!empty item.jointTaskses}"> 
                <a title="查看事件" href="${ctx}/jointTask/view.do?model.id=<c:forEach items='${item.jointTaskses}' var='jointTask'>${jointTask.id}</c:forEach>">看 </a> |       
		     </c:when>
		     <c:otherwise>
                <a title="查看事件" href="${ctx}/fscase/view.do?fsCaseId=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">看 </a> |	       
		     </c:otherwise>
		   </c:choose> 
			<a title="地图" href="#">图</a>
		</ec:column>	
		<ec:column width="90" property="_7" title="操作" style="text-align:center" sortable="false">
			<c:choose>
		     <c:when test="${item.status eq '0'}"> 
	 	       <a href="${ctx}/jointTask/addCase.do?model.fsCase.id=${item.id}">改</a> |		        
		     </c:when>
		     <c:otherwise>
	           <font color="#999999">改</font> | 	       
		     </c:otherwise>
		   </c:choose> 	
	       <c:choose>
		     <c:when test="${item.status eq '0'}"> 
	 	       <a href="${ctx}/jointTask/edit.do?model.fsCase.id=${item.id}">派</a> |		        
		     </c:when>
		     <c:otherwise>
	           <font color="#999999">派</font> | 	       
		     </c:otherwise>
		   </c:choose> 	
	       <c:choose>
		     <c:when test="${item.status eq '0'}"> 
	 	       <a href="#" onclick="remove(${item.id})">删</a>        
		     </c:when>
		     <c:otherwise>
	           <font color="#999999">删</font> 
		     </c:otherwise>
		   </c:choose> 			   	
		</ec:column>							
	</ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>
