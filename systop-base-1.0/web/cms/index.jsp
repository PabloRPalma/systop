<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title><fmt:message key="cms.title"/></title>
<%@include file="/common/yahooUi.jsp"%>
<script src="../js/dojo/dojo.js" type="text/javascript"></script>
<script src="<c:url value='/js/dojo/dojoHelper.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cms/js/cmsQuery.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/interface/cmsDojoAction.js'/>"	type="text/javascript"></script>
<script src="<c:url value='/dwr/engine.js'/>" type="text/javascript"></script>
<script src="<c:url value='/dwr/util.js'/>" type="text/javascript"></script>
</head>
<body>
<style>
    html, body{	
		width: 100%;	/* make the body expand to fill the visible window */
		height: 100%;
		
		padding:0px;
		margin: 0px;
    }
	.dojoSplitPane{
		margin: 0px;
	}
	#rightPane {
		margin: 0;
	}
</style>
<script type="text/javascript">
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.LayoutContainer");
dojo.require("dojo.widget.ContentPane");
dojo.require("dojo.widget.SplitContainer");
dojo.require("dojo.ex.FilteringTableEx");
dojo.require("dojo.ex.Paginator");
dojo.hostenv.writeIncludes();

dojo.addOnLoad(function() {
   currentFolderId = "-1";
   currentFolderName = "<fmt:message key='cms.root'/>";
   editText = "<fmt:message key='global.edit'/>";
   createText = "<fmt:message key='global.new'/>";
   deleteWarn = "<fmt:message key='cms.delFolder.warn'/>";
   folderIconSrc = "<c:url value='/images/icons/foldericon.png'/>";
   });
function newArticle() {
    window.location = "editContent.jsp?parent=" + currentFolderId;
}

function delContent() {
   if(confirm("<fmt:message key='cms.delContent.warn'/>")) {
     cmsDojoAction.removeSelected(function() {
         query();//decleared in listContents.jsp
     });
   }
}
</script>

<script src="<c:url value='/cms/js/folderTree.js'/>" type="text/javascript"></script>
<div dojoType="LayoutContainer"
	layoutChildPriority='left-right'
	style="border: 0px; width: 100%; height:100%; padding: 0px;"
>
	<div dojoType="ContentPane" layoutAlign="top" style="background-image: url(../images/top-bg1.gif) ">
		<table width="90%" align="center">
		  <tr>
			<td align="right" style="color:#000">
				
				<img src="<c:url value='/images/icons/add_3.gif'/>" title="<fmt:message key='cms.newArticle.alt'/>" border="0" style="cursor:pointer" onclick="newArticle()"/>
				<span  onclick="newArticle()" style="cursor:pointer"  title="<fmt:message key='cms.newArticle.alt'/>"><fmt:message key="cms.newArticle"/></span>
				&nbsp;&nbsp;
				<img src="<c:url value='/images/icons/del_2.gif'/>" title="<fmt:message key='cms.delContent.alt'/>" onclick="delContent()" border="0" style="cursor:pointer" />
				<span  onclick="delContent()" style="cursor:pointer" title="<fmt:message key='cms.delContent.alt'/>" ><fmt:message key="cms.delContent"/></span>
			</td>
		   </tr>
		</table>
	</div>
	<div dojoType="ContentPane" layoutAlign="bottom" style="background-image: url(../images/top-bg1.gif) ">
		&nbsp;
	</div>
	<div dojoType="SplitContainer"
		orientation="horizontal"
		sizerWidth="3"
		activeSizing="0"
		layoutAlign="client"
	>
	<div dojoType="ContentPane" layoutAlign="left" sizeMin="20" sizeShare="20" style="background-color: #f7f8fd; width: 200px;overflow:auto">	
		<%@include file="folderTree.jsp" %>
	</div>
	
	<div dojoType="ContentPane" layoutAlign="client" sizeMin="60" sizeShare="80" style="background-color: #ffffff; padding: 10px;">
		<%@include file="listContents.jsp"%>  
	</div>
	</div>
	
</div>
<div id="editFolderDlg" style="visibility:hidden">
	<div class="hd" id="dlgHead" style="font:12px"></div>
	<div class="bd" id="dlgBody" style="font:12px">
	  <form id="editFolderFrm" onsubmit="return false">
			<input type="hidden" id="parentId" name="parentId"/>
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="treeNodeId" name="treeNodeId"/>
		    <br>
			<table width="100%">
			  <tr>
			     <td width="20%" align="right"><fmt:message key="cms.content.title"/>:</td>
			     <td><input id="title" name="title" type="text" size="28" class="thin-input"/></td>
			     <td width=10%" align="left"><font color="#990000">*</font></td>
			  </tr>
			  
			  <tr>
			     <td width="20%" align="right"><fmt:message key="cms.content.subtitle"/>:</td>
			     <td><input id="subtitle" name="subtitle" type="text" size="28" class="thin-input"/></td>
			     <td width=10%" align="left"><font color="#990000">*</font></td>
			  </tr>
			  
			  <tr>
			     <td width="20%" align="right"><fmt:message key="cms.content.descn"/>:</td>
			     <td><textarea id="descn" name="descn" rows="3" cols="30" class="thin-input"></textarea></td>
			     <td></td>
			  </tr>			  
			  <tr>
			     <td width="20%" align="right"><fmt:message key="cms.content.expireDate"/>:</td>
			     <td>
			     <fmt:message key='cms.content.neverExpired'/>&nbsp;
			     <input type='checkbox' checked onclick="neverExpired(this.checked)" id='neverExp'/>
			     <input dojoType="DropdownDatePicker" widgetid="expireDate" 
			       id="expireDate" name="expireDate" class="thin-input"
			       onValueChanged="onSetExpireDate"> 
			     </td>
			     <td width=10%" align="left"><font color="#990000">*</font></td>
			  </tr>
			  <tr><td height="10"></td><td></td><td></td></tr>
			  <tr>
			     <td width="20%" align="right"><fmt:message key="cms.content.prop"/>:</td>
			     <td><input type="checkbox" id="available" name="available" checked/>  <fmt:message key="cms.content.available"/></td>
			     <td width=10%" align="left"></td>
			  </tr>
			  <tr>
			     <td width="20%" align="right"></td>
			     <td><input type="checkbox" id="visible" name="visible" checked/>  <fmt:message key="cms.content.visible"/></td>
			     <td width=10%" align="left"></td>
			  </tr>
			 
			  <tr>
			  <td width="20%"></td>
			    <td>
			      <table>
			        <tr>
			          <td width="20%" align="right">
			       <button dojotype="button" onclick="onSave()"><fmt:message key='global.save'/></button> 
			        
			          </td>
			          <td>
			          </td>
			          
			        </tr>
			      </table>
			    </td>
			  </tr>
			</table>
			
		</form>
	</div>
	<div class="ft" id="dlgFoot" style="font:12px;"></div>
</div>

	

		
	


</body>
</html>