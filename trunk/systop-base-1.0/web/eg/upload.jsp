<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<script src='<c:url value="/dwr/interface/UploadMonitor.js"/>'> </script>
<script src='<c:url value="/dwr/engine.js"/>'> </script>
<script src='<c:url value="/dwr/util.js"/>'> </script>
<html>
<head><title>Done</title>
<script src="<c:url value='/js/dojo/dojo.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/interface/UploadMonitor.js'/>"	type="text/javascript"></script>
<script src="<c:url value='/dwr/engine.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/util.js'/>" type="text/javascript"></script>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/dojoTable.css"/>">
</head>
<body>
<%=request.getParameter("field")%>
<script>
dojo.require("dojo.widget.*");
dojo.require("dojo.event.*");
dojo.require("dojo.collections.*");
dojo.require("dojo.ex.FilteringTableEx");
dojo.hostenv.writeIncludes();

var files = new dojo.collections.ArrayList([
<%
//use java code to build a JSON object.
Object obj = request.getAttribute("uploaded_files");
if(obj != null) {
  java.util.List<String> files = (java.util.List<String>) obj;
  for(int i = 0; i < files.size(); i ++) {
    out.print("{filename:'" + files.get(i) + "'}");
    if(i != (files.size() - 1) || files.get(i).equals("")) {
      out.print(",");
    }
  }
}
%>
]);
function onDel(e) {
  if(e.target) {
      UploadMonitor.removeUploadFiles([e.target.value], function(filenames) {
          if(filenames) {
             for(i = 0; i < filenames.length; i++) {
                 var index = contains(filenames[i]);
                 if(index != -1) {
                     files.removeAt(index);
                     parent.clear();
                     parent.save(files.toArray());
                 }
             }
          }
          var table = dojo.widget.byId('files');
          if(table) {
              table.store.setData(files.toArray());
          }
      });
  }
}

function contains(filename) {
    var array = files.toArray();
    for(i = 0; i < array.length; i++) {
       if(array[i].filename == filename) {
           return i;
       }
    }
    
    return -1;
}


dojo.addOnLoad(function() {
    var table = dojo.widget.byId('files');
    if(table) {
        files.forEach( function(file){
            var delBtn = document.createElement('img');
            delBtn.src = "<c:url value='/images/icons/del_2.gif'/>";
            delBtn.value = file.filename;
            delBtn.style.cursor = "pointer";
            dojo.event.connect(delBtn, "onclick", dj_global, "onDel");
            file.del = delBtn;
            var link = document.createElement('a');
            var href = "<c:url value='/uploadFiles'/>" + "/" + file.filename;
            link.innerHTML = file.filename;
            link.setAttribute('href', href);            
            file.link = link;
        });
        parent.save(files.toArray());
        table.store.setData(parent.getFiles());
        
    }
}
);



</script>
<div class='dojoTable'>
<table dojoType="FilteringTableEx" widgetId="files" id="files" minRows="0"
	valueField="filename" headerClass="tableHeader"
	tbodyClass="tableBody" rowAlternateClass="odd"
	headerUpClass="tableHeaderSort" headerDownClass="tableHeaderSort"
	alternateRows="true" cellpadding="0" cellspacing="0" border="0"
	class='tableRegion' style="width:200px">
	<thead>
		<tr>
		    
			<th field="link" dataType="String" class="tableHeader" width="80%">File</th>
			<th field="del" dataType="String" class="tableHeader" width="20%">Remove</th>
		</tr>
	</thead>
</table>

</div>

</body>
</html>