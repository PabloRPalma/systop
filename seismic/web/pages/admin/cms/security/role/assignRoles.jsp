<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:true, parseOnLoad: true"></script>
<script type="text/javascript" src="${ctx}/pages/admin/security/user/listUsers.js"></script>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/UserRoleAction.js"></script>
<style type="text/css">
		@import "${ctx}/js/dojo/jsam/resources/Dialog.css"; 
		@import "${ctx}/js/dojo/jsam/resources/Grid.css"; 
		@import "${ctx}/js/dojo/jsam/resources/Paginator.css";
</style>
<div id="assignRolesDlg" dojoType="jsam.Dialog" title="<fmt:message key="roleSelectot.title"/>" resizable="true"
    style="width: 450px; height: 380px; display:none;" >
  <div style="width:100%;">
	  <div id="container" class="dialogQueryForm" align="center">
    <div class="x-panel">
      <div class="x-toolbar" style="border-left:1px #a9bfd3 solid;border-right:1px #a9bfd3 solid;">
        <table width="100%">
	  <tr>
	    <td>
	      <fmt:message key="roleSelector.roleName"/>:&nbsp;<input type="text" name="role_name" id="assign_role_name">&nbsp;&nbsp;	   
	      <input type="button" id="assing_role_btn" value="<fmt:message key="global.query"/>" class="button">&nbsp;&nbsp;
	      <input type="button" id="save_role_btn" value="<fmt:message key="global.ok"/>" class="button">
	    </td>
	  </tr>
	</table>
      </div>
    </div>
  </div>
	<table dojoType="jsam.Grid" id="assign_roles"
	     keyField="id" datePattern="yyyy-MM-dd"
		 alternateRows="true" cellpadding="0"
		 cellspacing="0" useCustomerSort="false" style="width:100%;margin-top:12px;">
	  <thead>
		<tr>
		  <td field="name" width="40%" class="samGridHeader"><fmt:message key="roleSelector.roleName"/></td>
		  <td field="descn" width="50%" class="samGridHeader"><fmt:message key="roleSelector.des"/></td>
		  <td field="changed" width="10%" style="text-align:center" class="samGridHeader"><fmt:message key="global.select"/></td>				
		</tr>
	  </thead>
	  <tfoot>
		<tr>
		  <td colspan="20" class="samGridFooter">
			<div dojoType="jsam.Paginator" id="rolesPaginator" showMessage="true" 
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