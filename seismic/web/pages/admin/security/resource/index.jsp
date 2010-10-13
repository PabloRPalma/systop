<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/pages/admin/security/resource/resource.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/ResourceAction.js"></script>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<s:form action="resource/remove" method="POST" id="removeForm" validate="false"></s:form>
<div class="x-panel">
  <div class="x-panel-header">资源管理</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 
        <s:form action="resource/index" theme="simple">
	         资源名称：<s:textfield theme="simple" name="model.name" size="15"/>
	         资源字符串：<s:textfield theme="simple" name="model.resString" size="25"/>
	        &nbsp;&nbsp;<s:submit value="查询" cssClass="button"/>
         </s:form>
         </td>
         <td align="right">
         <table>
          	  <tr>
         <td><a href="#" onclick="importResources()"><img src="${ctx}/images/icons/import.gif"/> 导入资源</a></td>
         <td><span class="ytb-sep"></span></td>
         <td><a href="${ctx}/security/resource/editNew.do"><img src="${ctx}/images/icons/add.gif"/> 新建资源</a></td>
         <td><span class="ytb-sep"></span></td>
         <td><a href="#" onclick="onRemove()"><img src="${ctx}/images/icons/delete.gif"/> 删除资源</a></td>
         </tr>
            </table>
          <td>
        </tr>
      </table>
    </div>   
    <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	  action="index.do"
	  useAjax="true" doPreload="false"
	  maxRowsExported="1000" 
	  pageSizeList="10,20,50,100" 
	  editable="false" 
	  sortable="true"	
	  rowsDisplayed="10"	
	  generateScript="true"	
	  resizeColWidth="true"	
	  classic="false"	
	  width="100%" 	
	  height="350px"	
	  minHeight="300"  
	  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
	    <ec:column width="50" property="_s" title="选择" style="text-align:center" viewsAllowed="html" sortable="false">
	       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}" class="checkbox"/>
	    </ec:column>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}"  sortable="false"/>
		<ec:column width="150" property="name" title="资源名称" />
		<ec:column width="250" property="resString" title="资源字符串" />
		<ec:column width="150" property="resType" title="资源类型" mappingItem="resourceTypes" />
		<ec:column width="100" property="descn" title="资源描述" />		
		<ec:column width="40" property="_0" title="操作" style="text-align:center" viewsAllowed="html" sortable="false">
		   <a href="edit.do?model.id=${item.id}"> 
		      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
		   </a>
		</ec:column>
	</ec:row>
	</ec:table>
	</div>
  </div>
</div>
<%@include file="/common/dwrLoadingMessage.jsp" %>
</body>
</html>