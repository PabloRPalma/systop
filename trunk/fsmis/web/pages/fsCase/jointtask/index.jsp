<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/autocomplete.jsp"%>
<script type="text/javascript">
function removeJointTask(eID){
    Ext.MessageBox.confirm('提示','确实要删除该联合整治任务信息吗？', function(btn){
        if (btn == 'yes') {
    		location.href="remove.do?model.id="+ eID;
        }
    });	
}
</script>
<title>联合任务管理</title>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">联合整治管理</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
    <td><s:form action="index.do" method="post"> 
              任务标题：<s:textfield id="taskTitle" name="model.title" size="30"></s:textfield>          
      <input type="submit" value="查询" class="button" />
    </s:form>
    </td>
    <td align="right">
		<table>
		  <tr>
			<td>
			   <a href="${ctx}/jointTask/caseIndex.do?">联合整治事件</a>
			</td>	
			<td><span class="ytb-sep"></span></td>				  
			<td>
			   <a href="${ctx}/jointTask/addCase.do?">申请联合整治</a>
			</td>
			<td><span class="ytb-sep"></span></td>
			<td>
			   <a href="${ctx}/jointTask/details/expiredIndex.do">逾期未处理任务</a>
			</td>			
		  </tr>
		</table>
	</td>
  </tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
  items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
  action="index.do" 
  useAjax="false"
  doPreload="false" 
  pageSizeList="20,50,100,200" 
  editable="false"
  sortable="true" 
  rowsDisplayed="20" 
  generateScript="true"
  resizeColWidth="false" 
  classic="false" 
  width="100%" 
  height="400px"
  minHeight="400"
  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
	    <ec:column width="30" property="_num" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center" />
		
		<ec:column width="170" property="_title" title="任务标题" >
		  	   <a href="view.do?model.id=${item.id}"><font color="blue">${item.title}</font></a> 
		</ec:column>
		<ec:column width="80" property="_mt" title="牵头部门">	 						
			<c:forEach var="mtd" items="${item.taskDetailses}">
				<c:if test="${mtd.isLeader eq '1'}">
					${mtd.dept.name}
				</c:if>
			</c:forEach>
		</ec:column>
		<ec:column width="175" property="_mts" title="协办部门">
			<c:forEach var="mtd" items="${item.taskDetailses}" >
				<c:if test="${mtd.isLeader eq '0'}">
					${mtd.dept.name} 
				</c:if>				
			</c:forEach>
		</ec:column>
		<ec:column width="80" property="status"  title="任务状态" style="text-align:center" >
		  <c:if test="${item.status == '0'}"><font color="red">待审核</font></c:if>
		  <c:if test="${item.status == '1'}"><font color="blue">审核通过</font></c:if>
		  <c:if test="${item.status == '2'}"><font color="#CC6600">审核未通过</font></c:if>		  
		  <c:if test="${item.status == '4'}"><font color="green">处理完毕</font></c:if>		  
		</ec:column>
		<ec:column width="75" property="createDate" title="创建日期" style="text-align:center" cell="date" />
		<ec:column width="85" property="presetTime" title="规定完成日期" style="text-align:center" cell="date" />
		<ec:column width="45" property="_view" title="查看" style="text-align:center" sortable="false">
		   <a href="view.do?model.id=${item.id}">看</a>|
		   <a href="#" onclick="showProcessWindow('${item.status}','${item.fsCase.status}')" title="联合整治流程">流</a>
		</ec:column>	
		<ec:column width="115" property="_0" title="操作" style="text-align:center" sortable="false">
	       <c:choose>
		     <c:when test="${item.status eq '0' || item.status eq '2'}"> 
	 	       <a href="edit.do?model.id=${item.id}">改</a> |		        
		     </c:when>
		     <c:otherwise>
	           <font color="#999999">改</font> | 	       
		     </c:otherwise>
		   </c:choose>  
	       <c:choose>
		     <c:when test="${item.status eq '0' || item.status eq '2'}"> 
		       <a href="audit.do?model.id=${item.id}">审</a> |			        
		     </c:when>
		     <c:otherwise>
	           <font color="#999999">审</font> | 	       
		     </c:otherwise>
		   </c:choose>	
	       <c:choose>
	         <c:when test="${empty item.checkResults}"> 
		       <a href="#" onclick="removeJointTask(${item.id})">删</a> |        
		     </c:when>
		     <c:otherwise>
	           <font color="#999999">删</font> | 	       
		     </c:otherwise>
		   </c:choose> 	       
		    <a href="print.do?model.id=${item.id}" target="_blank">印</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
<jsp:include page="flow/process.jsp"></jsp:include>
</body>
</html>