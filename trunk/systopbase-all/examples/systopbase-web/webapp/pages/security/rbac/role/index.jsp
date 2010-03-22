<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<div style="display:none;" id="hiddenForm">
<s:form action="role/index" theme="simple" id="queryFrm">
<font color="white">角色名称：</font><s:textfield theme="simple" name="model.name" size="15"></s:textfield>
<font color="white">角色描述：</font><s:textfield theme="simple" name="model.descn" size="15"></s:textfield>
</s:form>
</div>
<div class="x-panel">
  <div class="x-panel-header">角色管理</div>
  <%--工具条--%>
  <div id="toolbar"></div>
  <%--角色列表--%> 
   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" 
	  retrieveRowsCallback="process" 
	  sortRowsCallback="process" 
	  action="index.do"
	  useAjax="true" doPreload="false"
	  xlsFileName="角色列表.xls" 
	  maxRowsExported="1000" 
	  pageSizeList="30,50,100,500,1000" 
	  editable="false" 
	  sortable="true"	
	  rowsDisplayed="30"	
	  generateScript="true"	
	  resizeColWidth="true"	
	  classic="false"	
	  width="100%" 	
	  height="360px"	
	  minHeight="360"
	  excludeParameters="selectedItems">
	<ec:row>
	    <ec:column width="50" property="_s" title="选择" style="text-align:center" viewsAllowed="html" sortable="false">
	       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}" class="checkbox"/>
	    </ec:column>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="200" property="name" title="角色名称" />
		<ec:column width="200" property="descn" title="角色描述" />
		<ec:column width="40" property="_0" title="操作" style="text-align:center" viewsAllowed="html" sortable="false">
		   <a href="#" onClick="RoleManager.showMe('${item.id}','${item.name}','${item.descn}')"> 
		      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
		   </a>
		</ec:column>
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
<script type="text/javascript" src="${ctx}/pages/security/rbac/role/index.js"></script>
<!-- 角色管理 -->
<div id="RoleManager" class="x-hidden">
<div id="edit_div" class="x-window-header">编辑角色</div>
<div class="x-window-body">
  <s:form id="roleForm" namespace="/security/rbac" action="role/save" method="post" validate="true"
	theme="simple">
	<s:hidden id="rId" name="model.id" />
	<s:hidden id="version" name="model.version" />
	<fieldset style="margin: 10px;height: 80px"><legend>角色基本信息</legend>
	<table align="center">
	  <tr><td height="1" colspan="2"></td></tr>
	  <tr>
	    <td>角色名称：</td>
	    <td>
	      <s:textfield id="rName" name="model.name"></s:textfield>&nbsp;<font color="red">*</font>
	    </td>
	  </tr>
	  <tr><td height="5" colspan="2"></td></tr>
	  <tr>
	    <td>角色描述：</td>
		<td align="left">
		  <s:textfield id="rDescn" name="model.descn"></s:textfield>&nbsp;<font color="red">*</font>
	    </td>
	  </tr>
	</table></fieldset>
  </s:form>
</div>
</div>
</body>
</html>