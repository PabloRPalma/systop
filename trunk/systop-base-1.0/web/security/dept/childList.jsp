<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/dojoTable.css"/>">
<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/fieldset.css"/>">

</head>
<body>
<script type="text/javascript">
//dojo.require("dojo.collections.*");
</script>

<fieldset class="fieldsetStyle" style="width:680px">
		<legend><fmt:message key='dept.msg'/> :</legend>

<table cellspacing="2">
  <tr>
    <td width="10%"></td>
    <td valign="top"><div id='deptMsg' style="display:none;"></div></td>
  </tr>
</table>



</fieldset>
<div class='dojoTable' align="center" style="width:680px">
<table dojoType="FilteringTableEx" widgetId="files" id="files" minRows="0"
	valueField="filename" headerClass="tableHeader"
	tbodyClass="tableBody" rowAlternateClass="odd"
	headerUpClass="tableHeaderSort" headerDownClass="tableHeaderSort"
	alternateRows="true" cellpadding="0" cellspacing="0" border="0"
	class='tableRegion' style="width:700px">
	<thead>
		<tr>
		    <th field="no" dataType="String" class="tableHeader" align="center" width='3%'></th>
			<th field="filename" dataType="String" class="tableHeader" align="center" width='15%'><fmt:message key="dept.subList"/></th>
			<th field="deptDesc" dataType="String" class="tableHeader" align="center" ><fmt:message key='dept.subDesc'/></th>
		</tr>
	</thead>
</table>
</div>
</body>
</html>
