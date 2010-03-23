<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title><fmt:message key= "security.res.title"/></title>
<%@include file="/common/meta.jsp" %>
<s:head/>
</head>
<body>
<table width="100%" border="0" cellpadding="0"
	cellspacing="0">

<tr height="5%">
		<td height="20" bgcolor="#cecece"
			style="padding-top:2px; padding-left:10px">
			<img src="<c:url value='/images/icons/add_3.gif'/>" width="14" height="14">
		       <a href="<c:url value='/security/user/editResource.jsp' />">
		       <fmt:message	key="global.new" /></a> 
		    <img src="<c:url value='/images/icons/del.gif'/>" width="14" height="14"> 
		       <a href="#"><font onclick="onDelete()" style="font:12.5px">
		       <fmt:message	key="global.remove" /></font></a>
		    
		</td>
	</tr>
		<tr height="15%">
	<td align="center">
	<fieldset style="width:93%">
    <legend><fmt:message key="global.query"/></legend>
    <table width="95%" align="left">
      <s:form action="queryResources" theme="simple" name="queryResources">
        <tr>
        <td width="70"><fmt:message key="security.res.name"/>:</td>
        <td width="10%" align="left"><s:textfield name="model.name" theme="simple"/></td>
        <td width="80"><fmt:message key="security.res.str"/>:</td>
        <td width="300" align="left"><s:textfield name="model.resString" theme="simple" cssStyle="width:300px"/></td>
        <td width="70"><fmt:message key="security.res.type"/>:</td>
        <td width="10%" align="left">
        <systop:catalogSelector catalog="res_type" name="model.resType" defaultLabel="global.all" defaultValue=""/>
        </td>
        <td align="left"><a href="#" onclick="javascript:$(queryResources).submit()">
        <img src="<c:url value='/images/icons/search_1.gif'/>" border="0"></a>
        </td>
        </tr>
      </s:form>
    </table>
    </fieldset>
	</td>
	</tr>
	<tr height="80%">
	   <td valign="top" align="center" >
	   <ec:table items="availableItems" var="item"
			action="#"
			rowsDisplayed="25" autoIncludeParameters="true" style="width:95%">
			<ec:exportXls fileName="AllUsers.xls" tooltip="global.export" />
			<ec:row>
			    <ec:column property="toRemove" title="global.select" sortable="false"
					viewsAllowed="html" width="30">
					<input type="checkbox" name="selectedItems" value="${item.id}" style='height:12px'>
				</ec:column>
				<ec:column property="rvclowcount" cell="rowCount"
					title="global.serialno" sortable="false" width="30"/>
				<ec:column property="name" title="security.res.name" width="300"></ec:column>
				<ec:column property="resString" title="security.res.str"></ec:column>
				<ec:column property="resType" title="security.res.type" alias="res_type" cell="com.systop.common.webapp.extremecomponents.CatalogCell" width="80"></ec:column>
				<ec:column property="edit" title="global.edit" sortable="false" width="30">
					<div onclick="onEdit('${item.id}')" style="cursor:hand">
					<img src="<c:url value='/images/icons/modify.gif'/>"></div>
				</ec:column>
			</ec:row>
	   </ec:table>
	   </td>
	</tr>
</table>
<s:form action="removeResources" name="removeResources" id="removeResources" theme="simple">
	</s:form>
	<s:form action="listResources" name="listResources" id="listResources" theme="simple">
	</s:form>
<s:form theme="simple" action="prepareEditResource" id="prepareEditResource" name="prepareEditResource">
		<input type="hidden" value="" name="model.id" id="prepareEditResource.id" />
	</s:form>
<script>
function onEdit(id) {
 var frm = $('prepareEditResource');
 $("prepareEditResource.id").value = id;
 frm.submit();
}

function onDelete() {
  if(confirm('<fmt:message key="security.res.delete.warn"/>')) {
    $('ec').action = $('removeResources').action;
    $('ec').submit();
  }
}

</script>

</body>
</html>