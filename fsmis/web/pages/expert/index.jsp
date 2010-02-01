<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>专家信息浏览</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
function removeExpert(eID){
	if(confirm("确实要删除该专家信息吗？")){
		location.href="remove.do?model.id="+ eID;
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">专家信息</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
    <s:form action="index.do" method="post">
	    <td width="8%">专家名称：</td>
	    <td width="8%"><s:textfield name="model.name" size="8"></s:textfield></td>
	    <td width="8%" style="vertical-align: middle">专家级别：</td>
        <td width="8%" style="vertical-align: middle">
           <s:select id="expertLevel" list="%{expertLevels}" name="model.level" headerKey="" headerValue="--请选择--"
	              cssStyle="width:80px;" />
        </td>	    
        <td width="8%" style="vertical-align: middle">专家类别：</td>
		<td width="8%" style="vertical-align: middle"><s:select id="categoryId"
						name="model.expertCategory.id" list="expertCateList" listKey="ecId" listValue="ecName"
						headerKey="" headerValue="--请选择--" cssStyle="width:120px" /></td>	      
	    <td width="8%"><input type="submit" value="查询" class="button" /></td>
	    <td style=" padding-left:5px; padding-top:5px;" align="right">   
	        <a href="${ctx}/expert/edit.do">添加专家</a>
	    </td>
    </s:form>
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
    <ec:column width="70" property="_1" title="专家名称" style="text-align:center"> 
       <a href="view.do?model.id=${item.id}"><font color="blue">${item.name}</font></a> 
    </ec:column>
    <ec:column width="70" property="level" title="专家级别" mappingItem="expertLevels" style="text-align:center"/>   
    <ec:column width="100" property="expertCategory.name" title="专家类别" style="text-align:left"/>  
    <ec:column width="80" property="title" title="职称" style="text-align:left"/> 
    <ec:column width="80" property="position" title="职位" style="text-align:left"/>   
    <ec:column width="100" property="mobile" title="手机" style="text-align:left"/>     
    <ec:column width="100" property="officePhone" title="办公电话" style="text-align:left"/> 
    <ec:column width="100" property="homePhone" title="家庭电话" style="text-align:left"/>                                   
    <ec:column width="100" property="_0" title="操作" style="text-align:center" sortable="false">
	   <a href="edit.do?model.id=${item.id}">编辑</a> |
	   	<c:choose>
	     <c:when test="${!empty item.asseMemberse}"> 
	        <font color="#999999">删除</font>
	     </c:when>
	     <c:otherwise>
	        <a href="#" onClick="removeExpert(${item.id})">删除</a> 
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