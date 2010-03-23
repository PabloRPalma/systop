<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href='<c:url value="/styles/dojoTable.css"/>'>
<link type="text/css" rel="stylesheet"
	href='<c:url value="/styles/fieldset.css"/>'>
<script type="text/javascript" src="<c:url value='/js/dojo/dojo.js'/>"></script>
<script src="<c:url value='/dwr/interface/cmsDojoAction.js'/>"	type="text/javascript"></script>
<script src="<c:url value='/dwr/engine.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/util.js'/>" type="text/javascript"></script>
<title></title>
</head>
<script type="text/javascript">
dojo.require("dojo.ex.FilteringTableEx");
dojo.require("dojo.ex.Paginator");

dojo.addOnLoad(function() {
    cmsDojoAction.getUnaudited(function(data) {
        var table = dojo.widget.byId("contents");
        for(var i = 0; i < data.length; i++) {
           data[i].audit = createAuditColumn(data[i]);
        }
        table.store.setData(data);
    });
});

function createAuditColumn(row) {
    var div = document.createElement('div');
    div.setAttribute('align', 'center');
    
    var pass = document.createElement('input');    
    pass.setAttribute('type', 'radio');
    pass.setAttribute('name', 'r' + row.id);
    pass.setAttribute('id', 'rPass' + row.id);
    pass.articleId = row.id;
    pass.onclick = function(e) {
        if(e) {
           audit(e.target.articleId, true);
        } else {
           audit(event.srcElement.articleId, true);
           var radio = $('rUnpass' + event.srcElement.articleId);
           radio.checked = false;
           event.srcElement.checked = true;
        }
    }
        
    div.appendChild(pass);
    div.appendChild(document.createTextNode("通过"));
    div.appendChild(document.createTextNode("      "));
    
    var unpass = document.createElement('input');    
    unpass.setAttribute('type', 'radio');
    unpass.articleId = row.id;
    unpass.setAttribute('name', 'r' + row.id);
    unpass.setAttribute('id', 'rUnpass' + row.id);
    unpass.onclick = function(e) {
        if(e) {
           audit(e.target.articleId, false);
        } else {
           audit(event.srcElement.articleId, false);
           var radio = $('rPass' + event.srcElement.articleId);
           radio.checked = false;
           event.srcElement.checked = true;
        }
    }
    div.appendChild(unpass);
    div.appendChild(document.createTextNode("未通过"));
    
    return div;
}

function audit(id, passed) {
   cmsDojoAction.audit(id, passed);
}
</script>
<body>
<div class='dojoTable' align="center">
<table dojoType="FilteringTableEx" widgetId="contents" id="contents" minRows="0"
	valueField="id" defaultDateFormat="%D" headerClass="tableHeader"
	tbodyClass="tableBody" rowAlternateClass="odd"
	headerUpClass="tableHeaderSort" headerDownClass="tableHeaderSort"
	alternateRows="true" cellpadding="0" cellspacing="0" border="0"
	class='tableRegion' onSort="sort"  style="width:500px" align="center">
	<thead>
		<tr>
		    <td field="title" dataType="String" class="tableHeader" width="70%">标题</td>	
		    <td field="audit" dataType="String" class="tableHeader" width="30%" onSort="true">审核</td>	
		</tr>
	</thead>
</table>
</div>
</body>
</html>