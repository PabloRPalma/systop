<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/common/taglibs.jsp"%>
<script src='<c:url value="/dwr/interface/UploadMonitor.js"/>'> </script>
<html>
<head><title>Done</title>
<script src="<c:url value='/js/dojo/dojo.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/interface/UploadMonitor.js'/>"	type="text/javascript"></script>
<script src="<c:url value='/dwr/interface/cmsDojoAction.js'/>"	type="text/javascript"></script>
<script src="<c:url value='/dwr/engine.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/util.js'/>" type="text/javascript"></script>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/dojoTable.css"/>">
</head>
<body>

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

/**
 *
 */
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

/**
 *
 */
function prepareDel(e) {
  if(e.target) {
      if(confirm("<fmt:message key='cms.delAttachment.warn' />")) {
          cmsDojoAction.prepareRemoveAttachment(e.target.id, function(filename){
              //alert(filename);
              if(filename) {
                  var index = contains(filename);
                  if(index != -1) {
                  	  files.removeAt(index);
                  	  parent.clear();
                  	  parent.save(files.toArray());
                  }
              }
              var table = dojo.widget.byId('files');
              if(table) {
                  table.store.setData(files.toArray());
              }
              
          });
      }
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

function getContentId() {
    return parent.getContentId();
}

function loadTable(onDel){
    var table = dojo.widget.byId('files');
    if(table) {     
        files.forEach( function(file){
            var delBtn = document.createElement('img');
            delBtn.src = "<c:url value='/images/icons/delete.gif'/>";
            delBtn.value = file.filename;
            delBtn.id = file.id;
            delBtn.style.cursor = "pointer";
            dojo.event.connect(delBtn, "onclick", dj_global, onDel);
            file.del = delBtn;
            if(onDel == "onDel") {
            	var link = document.createElement('a');
            	var href = "<c:url value='/uploadFiles'/>" + "/" + file.filename;
            	link.innerHTML = file.filename;
            	link.setAttribute('href', href);            
                file.link = link;
                file.id = -2;
                file.desc = "<fmt:message key="cms.upload.prepareUpload"/>";
            } else {
                file.link = file.filename;
            }
        });
        if(onDel == "prepareDel") {
            table.store.setData(files.toArray());
            parent.clear();
            parent.save(files.toArray());
        } else {
            parent.save(files.toArray());
            files.clear();
            files.addRange(parent.getFiles());
            table.store.setData(files.toArray());
        }
    }
}
dojo.addOnLoad(function() {   
    var contentId = getContentId();
    if(files.count == 0 && contentId != -2) {
        cmsDojoAction.getAttachments(contentId, function(data){
            if(data) {
        	    for(i = 0; i < data.length; i++) {
        	      var file = {};
        	      file.filename = data[i].path;
        	      file.desc = "<fmt:message key="cms.upload.formDB"/>";
        	      file.id = data[i].id;
        	      files.add(file);
        		} 
        		//alert(files);
        		loadTable("prepareDel");      		
        	}  	
        });        
    } else {
            loadTable("onDel");     
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
	class='tableRegion' style="width:80%">
	<thead>
		<tr>
		    
			<th field="link" dataType="String" class="tableHeader" ><fmt:message key="golbal.uploadFilename"/></th>
			<th field="desc" dataType="String" class="tableHeader" align="center"><fmt:message key="golbal.uploadFileDesc"/></th>
			<th field="del" dataType="String" class="tableHeader" width="%10" align="center"><fmt:message key="global.remove"/></th>
		</tr>
	</thead>
</table>

</div>

</body>
</html>