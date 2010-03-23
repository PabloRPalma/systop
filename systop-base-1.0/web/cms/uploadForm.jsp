<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Simple upload page</title>
<script src="<c:url value='/js/dojo/dojo.js'/>" type="text/javascript"></script>
<script src='<c:url value="/common/js/upload.js"/>'> </script>
<script src='<c:url value="/dwr/interface/UploadMonitor.js"/>'> </script>
<script src='<c:url value="/dwr/engine.js"/>'> </script>
<script src='<c:url value="/dwr/util.js"/>'> </script>
<style type="text/css">
            body { font: 11px Lucida Grande, Verdana, Arial, Helvetica, sans serif; }
            #progressBar { padding-top: 5px; }
            #progressBarBox { width: 350px; height: 20px; border: 1px inset; background: #eee;}
            #progressBarBoxContent { width: 0; height: 20px; border-right: 1px solid #444; background: #9ACB34; }
        </style>
</head>
<body>

<script>

dojo.require("dojo.collections.*");


var files = new dojo.collections.ArrayList([]);

    
function save(f) {
   files.addRange(f);
}
function clear() {
   files.clear();
}

function getFile(){
    return files;
}

function getFiles() {
    return files.toArray();
}
function getResults() {
   var results = [];
   var filesArray = files.toArray();
   
   for(i = 0; i < filesArray.length; i++) {
   
      results[i] ={id: filesArray[i].id,
                  path: filesArray[i].filename
                 };
      //alert(results[i].id + " " + results[i].path );
   }
   return results;
}

function getFilenames(){
	var files = [];
	var filenames = [];
	files = getFiles();
	for(i=0;i<files.length;i++){
		filenames[i]=files[i].filename;
	}
	
	return filenames;
}
function getContentId() {
    
    <c:if test="${param.contentId != null}">
    return ${param.contentId};
    </c:if>
    return -2;
}

function showFiles(){

upload.location.reload();

}

</script>
<!-- input type="button" value="show" onclick="showFiles()"/-->

<table>
	<tr>
		<td align="left" width="80%" style:" padding:5px">
		<form id="uploadForm" name="uploadForm" action="../common/upload.jsp"
	enctype="multipart/form-data" method="post" onsubmit="startProgress()"
	target="upload">
<fieldset>
    <legend><fmt:message key="global.attachment"/></legend>
<fmt:message key='golbal.selectUploadFile'/>:
<input class="default" type="file" id="file1" name="file1" />
<input type="submit" value="<fmt:message key='golbal.upload'/>" id="uploadbutton" /><br />
<div id="progressBar" style="display: none;">

<div id="theMeter">
<div id="progressBarText"></div>

<div id="progressBarBox">
<div id="progressBarBoxContent"></div>
</div>
</div>
</div>
</p>
</fieldset>
</form>
		
		</td>
	</tr>
</table>
<table>

        <tr><td>
		<iframe src="../common/upload.jsp" height="300" width="600" frameborder="0" name="upload"
	id="upload" frameborder="0"></iframe>
		</td></tr>
</table>


</body>
</html>
