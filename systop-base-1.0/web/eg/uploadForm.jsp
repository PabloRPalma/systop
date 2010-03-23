<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Simple upload page</title>
<script src="<c:url value='/js/dojo/dojo.js'/>" type="text/javascript"></script>
<script src='<c:url value="/eg/js/upload.js"/>'> </script>
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
function getFiles() {
   return files.toArray();
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

function showFilenames(){
var files = [];
var filenames = [];
files = getFiles();
for(i=0;i<files.length;i++){
	filenames[i]=files[i].filename;
	}
alert(filenames);	
}
</script>
<input type="button" value="getFiles" onclick="showFilenames()" />
<form id="uploadForm" name="uploadForm" action="upload.jsp"
	enctype="multipart/form-data" method="post" onsubmit="startProgress()"
	target="upload">
<p>
<h1>Web upload</h1>
</p>

<p><input class="default" type="file" id="file1" name="file1" /><br />
<input class="default" type="file" id="file2" name="file2" /><br />
<input class="default" type="file" id="file3" name="file3" /><br />
<input type="submit" value="begin upload" id="uploadbutton" /><br />
<input type='text' name='field' id='field' /> <br />
<div id="progressBar" style="display: none;">

<div id="theMeter">
<div id="progressBarText"></div>

<div id="progressBarBox">
<div id="progressBarBoxContent"></div>
</div>
</div>
</div>
</p>
</form>
<iframe src="upload.jsp" height="388" frameborder="0" name="upload"
	id="upload" frameborder="0"></iframe>

</body>
</html>
