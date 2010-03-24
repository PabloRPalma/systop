<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<title></title>
<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:true, parseOnLoad: true"></script>
<script type="text/javascript" src="${ctx}/pages/admin/security/permission/listPermissions.js"></script>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/PermissionResourceAction.js"></script>
<style type="text/css">
		@import "${ctx}/js/dojo/jsam/resources/Dialog.css"; 
		@import "${ctx}/js/dojo/jsam/resources/Grid.css"; 
		@import "${ctx}/js/dojo/jsam/resources/Paginator.css";
</style>
<%@include file="/common/ec.jsp" %>
</head>
<body>
<script type="text/javascript">
function onRemove() {
var selectitem = document.getElementsByName("selectedItems");
  var j=0;
  for (var i = 0; i < selectitem.length; i ++) {
	if (selectitem[i].checked) {
	  j++;
    }
  }
  if (j>0) {
    if(confirm("是否确定删除权限？")) {
      $('ec').action = "removePermissions.do";
      $('ec').submit();
    }
  }else {
    alert("请选择要删除的记录!");
  }
}
</script>
<div class="x-panel">
  <div class="x-panel-header">权限管理</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 
        <s:form action="listPermissions" theme="simple">
	         权限名称：<s:textfield theme="simple" name="model.name" size="15"></s:textfield>
	         权限描述：<s:textfield theme="simple" name="model.descn" size="15"></s:textfield>
	        &nbsp;&nbsp;<s:submit value="查询" cssClass="button"></s:submit>
         </s:form>
         </td>
         <td align="right">
         <table>
           <tr>
         <td><a href="${ctx}/admin/security/permission/listRoles.do"><img src="${ctx}/images/icons/home_1.gif"/> 权限管理首页</a></td>
         <td><span class="ytb-sep"></span></td>
         <td><a href="${ctx}/admin/security/permission/newPermission.do"><img src="${ctx}/images/icons/authority.gif"/> 新建权限</a></td>
         <td><span class="ytb-sep"></span></td>
         <td><a href="#" onclick="onRemove()"><img src="${ctx}/images/icons/delete.gif"/> 删除权限</a>
         </tr>
         </table>
         <td>
       </tr>
     </table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
		<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
			action="listPermissions.do"
			useAjax="true" doPreload="false"
			xlsFileName="权限列表.xls" 
			maxRowsExported="1000" 
			pageSizeList="5,10,20,100" 
			editable="false" 
			sortable="true"	
			rowsDisplayed="10"	
			generateScript="true"	
			resizeColWidth="true"	
			classic="false"	
			width="100%" 	
			height="277px"	
			minHeight="200"  
			>
		 	<ec:row>
			    <ec:column width="50" property="_s" title="选择" style="text-align:center" viewsAllowed="html">
			       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}"/>
			    </ec:column>
				<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" />
				<ec:column width="150" property="name" title="权限名称" />
				<ec:column width="150" property="operation" title="权限类型"  mappingItem="operations" />
				<ec:column width="150" property="descn" title="权限描述" />	
				<ec:column property="_0" title="资源" style="text-align:center" viewsAllowed="html">
				 <a href="#" onclick="javascript:PermissionManager.assignResources(${item.id}, 1)">
				       <img src="${ctx}/images/icons/resource.gif" border="0" title="分配资源"/>
				   </a>
				</ec:column>	
				<ec:column width="50" property="_0" title="操作" style="text-align:center" viewsAllowed="html">
				   <a href="editPermission.do?model.id=${item.id}"> 
				      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
				   </a>
				</ec:column>
			</ec:row>
		</ec:table>
	</div>
  </div>
</div>
<%--资源选择对话框--%>
<div id="assignResourcesDlg" dojoType="jsam.Dialog" title="资源列表" resizable="true"
    style="width: 450px; height: 380px; display: none;" >
    <div style="width:100%;">
		<div id="container" class="dialogQueryForm" align="center">
			资源名称：<input type="text" name="resource_name" id="assign_resource_name">
			<input type="button" id="assing_resource_btn" value="查询" class="button">
			<input type="button" id="save_resource_btn" value="保存" class="button">
		</div>
		
		<table dojoType="jsam.Grid" id="assign_resources"
				keyField="id" datePattern="yyyy-MM-dd"
				alternateRows="true" cellpadding="0"
				cellspacing="0" useCustomerSort="false" style="width:100%;margin-top:12px;">
				<thead>
					<tr>
						<td field="name" width="40%" class="samGridHeader">
						资源名称</td>
						<td field="descn" width="50%" class="samGridHeader">
						描述</td>
						<td field="changed" width="10%" style="text-align:center" class="samGridHeader">
						选择</td>				
					</tr>
				</thead>
				<tfoot>
					<tr>
					    <td colspan="20" class="samGridHeader">
							 <div dojoType="jsam.Paginator" id="resourcesPaginator" showMessage="true" 
						     cssStyle="width:100%; padding:1px;border:none"
							 messageTemplate="<fmt:message key='global.paginator.message'/>" 
							 showFastStep="true" maxFastStep="5" style="width:100%;"
							 messageWidth="160">
						     </div>
					    </td>
					</tr>
				</tfoot>
		</table>
	</div>
</div>	
</body>
</html>