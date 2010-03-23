<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/dojoTable.css"/>">
<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/fieldset.css"/>">


<script type="text/javascript">
var cms = null;
CMSQuery.prepareEdit = function(contentId, folderId) {
    window.location = "editContent.jsp?contentId=" + contentId + "&parent=" + currentFolderId;   
}

CMSQuery.onSelect = function(id, checkbox) {
    cmsDojoAction.selectContent(id, checkbox.checked);
}

dojo.addOnLoad(function() {
    cms = new CMSQuery('contents', 'paginator', cmsDojoAction, <fmt:message key='default.pagesize' />);
    cms.typeIcons = [
      {src:"<c:url value='/images/icons/doc.gif'/>", alt: "<fmt:message key='cms.content.type.text'/>"},
      {src:"<c:url value='/images/icons/foldericon.png'/>", alt: "<fmt:message key='cms.content.type.folder'/>"},];
    cms.editIconSrc ="<c:url value='/images/icons/edit_1.gif'/>";
    var table = dojo.widget.byId('contents');
    if(table) {
         table.onSort = function(e) {
            table._onSort(e);
            cms.query(cms.pageNo);
         };
    }
    var paginator = dojo.widget.byId('paginator');
    if(paginator) {
        paginator.pagingFunction = function(args) {
           cms.query(args.pageNo);
        };
    }
});

function query() {
  var qr = dojo.byId('queryRange');
  if(qr.selectedIndex == 0) {
      cms.folderId = null;
  } else {
      cms.folderId = currentFolderId;
  }
  cms.query(1);
}

</script>
<form id="preEdit" action="<c:url value='/cms/editContent.jsp'/>" method="post">
  <input type="hidden" id="contentId">
</form>

<div align="center">
<fieldset class="fieldsetStyle" style="width:800px">
		<legend><fmt:message key="global.query" /></legend>
		<table width="100%" cellpadding="0" cellspacing="0">
		  <tr>
		    <td width="95%" align="left">
		    <fmt:message key="cms.content.title"/>:<input id="query_title" type="text" style="width:180px">
		    <fmt:message key="cms.content.createTime"/>:<input dojoType="DropdownDatePicker" widgetid="query_createTime" 
			       id="query_createTime" name="query_createTime">
			<fmt:message key="cms.content.updateTime"/>:<input dojoType="DropdownDatePicker" widgetid="query_updateTime" 
			       id="query_updateTime" name="query_updateTime">	
			<fmt:message key="cms.content.type"/>:<systop:catalogSelector catalog="content_type" name="query_type" defaultLabel="global.all"></systop:catalogSelector>
		    </td>
			<td></td>
		  </tr>
		  <tr>
		  <td align="left">
		  <fmt:message key="global.query.range"/>:
		  <select id="queryRange">
		     <option><fmt:message key="cms.all"/></option>
		     <option><fmt:message key="cms.currentFolder"/></option>
		  </select>
		  </td>
		  <td> <button dojotype="button" onclick="javascript:query()"><fmt:message key='global.query'/></button></td>
		  </tr>
		</table>
</fieldset>
</div>

<div class='dojoTable' align="center">
<table dojoType="FilteringTableEx" widgetId="contents" id="contents" minRows="0"
	valueField="id" defaultDateFormat="%D" headerClass="tableHeader"
	tbodyClass="tableBody" rowAlternateClass="odd"
	headerUpClass="tableHeaderSort" headerDownClass="tableHeaderSort"
	alternateRows="true" cellpadding="0" cellspacing="0" border="0"
	class='tableRegion' onSort="sort"  style="width:800px" align="center">
	<thead>
		<tr>
		    
			<th field="title" dataType="String" class="tableHeader" width="35%"><fmt:message
				key='cms.content.title' /></th>
			
			<th field="createTime" dataType="String" class="tableHeader"><fmt:message
				key='cms.content.createTime' /></th>
			<th field="author" dataType="String" class="tableHeader"><fmt:message
				key='cms.content.author' /></th>
			<th field="updateTime" dataType="String" class="tableHeader"><fmt:message
				key='cms.content.updateTime' /></th>
			<th field="updater" dataType="String" class="tableHeader"><fmt:message
				key='cms.content.updater' /></th>
			<th field="expireDate" dataType="String" class="tableHeader"><fmt:message
				key='cms.content.expireDate' /></th>
			<th field="type" dataType="String" class="tableHeader" width="30"><fmt:message
				key='cms.content.type' /></th>
			<th field="audited" dataType="String" class="tableHeader" width="60"><fmt:message
				key='cms.content.audited' /></th>
		    <th field="changed" dataType="String" class="tableHeader" width="30" noSort="true" align="center">
		        <fmt:message key='global.select' /></th> 
		</tr>
	</thead>
</table>

</div>
<div align='center'>
<div dojoType="Paginator" id="paginator" showMessage="true" cssStyle="width:796px; padding:1px;margin:4px"
	 messageTemplate="<fmt:message key='global.paginator.message'/>" showFastStep="true" maxFastStep="5" 
	 messageWidth="480"></div>
</div>

