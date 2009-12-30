<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/autocomplete.jsp"%>
<script type="text/javascript">
function removeAssessment(eID){
	if(confirm("确实要删除该评估信息吗？")){
		location.href="remove.do?model.id="+ eID;
	}
}

window.onload = function(){
    // jQuery自动装配    
    $.ajax({
		url: '${ctx}/assessment/getTitles.do',
		type: 'post',
		dataType: 'json',
		success: function(titles, textStatus){
			$("#caseTitle").autocomplete(titles, {
				mustMatch :true ,
       			matchContains: true,
        		minChars:1,
        		width:'191px', 
        		scrollHeight: 200
      		});
		}
	});
  };
</script>
<title>风险评估信息浏览</title>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">风险评估&nbsp;&gt;&nbsp;评估申请管理</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
    <td><s:form action="index.do" method="post"> 
           事件标题：<s:textfield id="caseTitle" name="model.fsCase.title" size="30"></s:textfield>      
           申请人：<s:textfield name="model.proposer.name" size="8"></s:textfield>      
           申请原因：<s:textfield name="model.askCause" ></s:textfield>     
      <input type="submit" value="查询" class="button" />
    </s:form>
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
    <ec:column width="40" property="_num" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/> 
    <ec:column width="60" property="proposer.name" title="申请人" style="text-align:center"/>
    <ec:column width="170" property="fsCase.title" title="事件标题"/>    
    <ec:column width="100" property="_1" title="专家组组长"/>
    <ec:column width="150" property="_2" title="专家组成员"/>
    <ec:column width="70" property="askDate" title="申请日期"  style="text-align:center" cell="date" />     
    <ec:column width="100" property="state" title="评估状态" style="text-align:center">
       <c:choose>
        <c:when test="${item.state eq '0'}">
          <span style="color:red">待审核</span>
        </c:when>
        <c:when test="${item.state eq '1'}">
          <span style="color:green">审核通过</span>
        </c:when>
        <c:when test="${item.state eq '2'}">
          <span style="color:#CC6600">审核未通过</span>
        </c:when>
        <c:when test="${item.state eq '3'}">
          <span style="color:blue">评估完毕</span>
        </c:when>
        <c:when test="${item.state eq '4'}">
          <span style="color:#0E8897">评估已启动</span>
        </c:when>
      </c:choose>
    </ec:column>    
    <ec:column width="150" property="_0" title="操作" style="text-align:center">
       <c:if test="${item.state eq '0' or item.state eq '2'}">
 	      <a href="edit.do?model.id=${item.id}">编辑</a>|	   
	      <a href="audit.do?model.id=${item.id}">审核</a>|       
       </c:if>   
       <c:if test="${item.state eq '1'}">
  	      <a href="start.do?model.id=${item.id}">启动</a>|	    
       </c:if>  
	   <c:choose>
	     <c:when test="${!empty item.checkResults}"> 
	        <font color="#999999">删除|</font>
	     </c:when>
	     <c:otherwise>
	        <a href="#" onClick="removeAssessment(${item.id})">删除|</a>  
	     </c:otherwise>
	   </c:choose>   
       <a href="print.do?model.id=${item.id}" target="_blank">打印</a>	   
	</ec:column>    
  </ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>