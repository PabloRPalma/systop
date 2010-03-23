<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="cms.edit"/></title>
<link type="text/css" rel="stylesheet" href="<c:url value="/styles/style.css"/>">

<script type="text/javascript" src="<c:url value='/fckEditor/fckeditor.js'/>"></script>
<script type="text/javascript" src="<c:url value='/cms/js/cmsEdit.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/dojo/dojoHelper.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/dojo/dojo.js'/>"></script>
<script src="<c:url value='/dwr/interface/cmsDojoAction.js'/>"	type="text/javascript"></script>
<script src="<c:url value='/dwr/engine.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/util.js'/>" type="text/javascript"></script>

</head>
<body>
<script type="text/javascript">
dojo.require("dojo.widget.*");
dojo.hostenv.writeIncludes();
		
function init() {
   displayContent();
   var view = dojo.widget.byId('view');
   if(view) {
      view.loadContents = displayContent; 
   }
   var backFalgStr = "<div style=' float:right; '><img src='<c:url value='/images/back.gif'/>' />" + "<a href='<c:url value='/cms/index.jsp'/>'>" + "<font color='#003366'><b><fmt:message key='global.backCmsList' /></b></font>" + "</a></div>";
  cmsDojoAction.getPath($('parentid').value, function(data) {
      //var img = "<img src='<c:url value='/images/link.gif'/>' />";
      for(i = data.length-1; i >=0 ; i--){   
         $('pathPane').innerHTML +=( "<font color='#003366'><b>" + data[i] + "</b></font> -> " );
         $('pathPane2').innerHTML +=( "<font color='#003366'><b>" + data[i] + "</b></font> -> " );
      }
      $('pathPane').innerHTML += ("<font color='#003366'><b>" + $('title').value + "</b></font>");
      <c:if test="${param.contentId != null}">
          $('pathPane2').innerHTML += ("<font color='#003366'><b>" + $('title').value + "</b></font>");
      </c:if>
      $('pathPane2').innerHTML += (backFalgStr);
      
  });
  
}

dojo.addOnLoad(   
   	function() {
   	    init();
   	}
);


        
    function displayContent() {
       $('stitle').innerHTML = $('title').value;
       if(typeof FCKeditorAPI == 'undefined') {
           var bodyStr = $('body').innerHTML;
           $('sbody').innerHTML = bodyStr.unescapeHTML();
       } else {
           var ed = FCKeditorAPI.GetInstance('body');
           var bodyStr = ed.GetXHTML();
           $('sbody').innerHTML = bodyStr;
       }
    }
    
   function onlazy(){
   alert("onlazy");
   }
   
</script>
<c:choose>
   <c:when test="${param.contentId == null}"><c:set var="selectTab" value="contentForm"/></c:when>
   <c:otherwise><c:set var="selectTab" value="view"/></c:otherwise>
</c:choose>
<div id="mainTabContainer" dojoType="TabContainer" sizeMin="20" sizeShare="70"
   style="width: 100%; height: 600px;" selectedChild="<c:out value='${selectTab}'/>">
	<div id="view" dojoType="ContentPane" label="<fmt:message key='cms.preview'/>">
	   <%@include file="viewContent.jsp" %>
	</div>		
	<div id="contentForm" dojoType="ContentPane" label="<fmt:message key='global.edit'/>">
	   <%@include file="contentForm.jsp" %>
	</div>
	
	<div id="addAttachment" style="display: none;" href="uploadForm.jsp" dojoType="ContentPane"  label="<fmt:message key='global.attachment'/>">
	   <%@include file='uploadForm.jsp' %>
	</div>
				
	
</div>
<div id="tabController" dojoType="TabController" doLayout="true" 
     containerId="mainTabContainer" labelPosition="bottom"></div>
</body>
</html>