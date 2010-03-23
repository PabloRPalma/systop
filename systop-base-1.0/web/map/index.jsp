<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="../js/dojo/dojo.js"/>" type="text/javascript"></script>
<%@include file="/common/yahooUi.jsp"%>
<link type="text/css" rel="stylesheet" href="templates/dojo.css">
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/style.css'/>">
<script src="<c:url value='/js/prototype.js'/>" type="text/javascript"></script>
<script src="<c:url value="/dwr/interface/mapDwrAction.js"/>" type="text/javascript"></script>
<script src="<c:url value="/dwr/engine.js"/>" type="text/javascript"></script>
<script src="<c:url value="/dwr/util.js"/>" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
YAHOO.namespace("map.dlg");
	dojo.require("dojo.widget.SplitContainer");
	dojo.require("dojo.widget.ContentPane");
	dojo.require("dojo.ex.Paginator");
	
	dojo.addOnLoad(function() {
		sysPrompt = "<fmt:message key='global.sysPrompt'/>";
		createTitle =  "<fmt:message key='global.new'/>";
		editTitle = "<fmt:message key='global.edit'/>";
		rootUnEdit = "<fmt:message key='map.root.unEdit'/>";
		rootUnDel = "<fmt:message key='map.root.unDelete'/>";
		delWran = "<fmt:message key='global.delete.warn'/>";
		noSubNode = "<fmt:message key='map.delete.noSubNode'/>";
		addTitleInUse = "<fmt:message key='map.add.titleInUse'/>";
		addSignInUse = "<fmt:message key='map.add.signInUse'/>";
		editTitleInUse = "<fmt:message key='map.edit.titleInUse'/>";
		editSignInUse = "<fmt:message key='map.edit.signInUse'/>";
		imgTitle = "<fmt:message key='entry.imgTitle'/>";
		sysCatalog =  "<fmt:message key='map.tree.title'/>";
		noMap = "<fmt:message key='map.noMap'/>";
		noMapRefresh = "<fmt:message key='map.noMapRefresh'/>";
		delMustNoEntry = "<fmt:message key='map.del.noEntry'/>";
		editMapError = "<fmt:message key='map.edit.error'/>";
		
		noEntry = "<fmt:message key='entry.noEntry'/>";
		addEntryNoMap = "<fmt:message key='entry.add.noMap'/>";
		addEntryInUse = "<fmt:message key='entry.add.entryInUse'/>";
		editEntryError = "<fmt:message key='entry.edit.error'/>"; 
		delEntrNone = "<fmt:message key='entry.del.none'/>"; 
		
		backConfirm = "<fmt:message key='map.backData.confirm'/>";
		backSuccess = "<fmt:message key='map.backData.success'/>";
		backFail = "<fmt:message key='map.backData.fail'/>";
		revertconfim = "<fmt:message key='map.revertData.confim'/>";
		revertSucess = "<fmt:message key='map.revertDate.success'/>";
		revertFail = "<fmt:message key='map.revertDate.fail'/>";
   });
</script>
<script src="<c:url value="js/mapTree.js"/>" type="text/javascript"></script>
<script src="<c:url value="js/editEntry.js"/>" type="text/javascript"></script>
<script src="<c:url value="js/mapInfoOption.js"/>" type="text/javascript"></script>
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

<title><fmt:message key="map.index.title"/></title>
</head>
<body>
<div dojoType="LayoutContainer"
	layoutChildPriority='left-right'
	style="border: 0px; width: 100%; height:100%; padding: 0px;">
	<div dojoType="ContentPane" layoutAlign="top"
		 style="background-image: url(../images/top-bg1.gif); padding-right:60px;" align="right">
		<table border="0"><tr>
				<td><a href="#" onclick="backupData()">
					<img src="../images/icons/backup.gif" width="15" height="15" style="padding-top:2px;"/>
					<fmt:message key='global.backup'/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><a href="#" onclick="revertData()">
					<img src="../images/icons/revert.gif" width="15" height="15" style="padding-top:2px;"/>
					<fmt:message key='global.revert'/></a></td>
			</tr></table>
	</div>
	<div dojoType="ContentPane" layoutAlign="bottom" style="background-image: url(../images/top-bg1.gif) ">
		&nbsp;
	</div>
	<div dojoType="SplitContainer"
		orientation="horizontal"
		sizerWidth="3"
		activeSizing="0"
		layoutAlign="client">
		<div dojoType="ContentPane" layoutAlign="left" sizeMin="20" sizeShare="20" 
			style="padding-left:10px; padding-top:10px; #f7f8fd; width: 200px;overflow:auto">	
			<%@include file="mapTree.jsp" %>
		</div>
		
		<div dojoType="ContentPane" layoutAlign="client" sizeMin="60" sizeShare="80" 
			style="background-color: #ffffff; padding: 10px;overflow:auto">
			<%@include file="listMaps.jsp"%>  
		</div>
	</div>
</div>

<!-- editMapDlg start -->
<div id="editMapDlg" style="visibility:hidden">
	<div class="hd" id="dlgHead" style="font:12px"></div>
	<div class="bd" id="dlgBody" style="font:12px">
			<form id="editMapFrm" onsubmit="return false">
			<table width="100%">
				<tr>
					<td><input type="hidden" id="mapId" name="mapId" /></td>
					<td><input type="hidden" id="treeNodeId" name="treeNodeId" /></td>
				</tr>
				<tr>
					<td width="25%" align="right"><fmt:message key='map.title'/>:&nbsp;</td>
					<td width="75%" height="21">
					<input id="mapTitle" name="mapTitle" type="text" style="width:150px;"
						class="thin-input" /><font color="#990000">&nbsp;*</font></td>
				</tr>
				<tr>
					<td width="25%" align="right"><fmt:message key='map.sign'/>:&nbsp;</td>
					<td width="75%">
					<input id="mapSign" name="mapSign" type="text" style="width:150px;"
						class="thin-input"/><font color="#990000">&nbsp;*</font></td>
				</tr>
				<tr>
					<td width="25%" align="right"><fmt:message key='map.descn'/>:&nbsp;</td>
					<td width="75%">
					<textarea id="mapDescn" name="descn" rows="3"  style="width:150px;"
						class="thin-input"></textarea></td>
				</tr>
			</table>
			<table width="100%" cellspacing="5">
				<tr>
					<td width="50%" align="right">
						<button dojotype="button" onclick="onSave()" /><fmt:message key='global.save'/></button>
					</td>
					<td width="50%" align="left">
						<button dojotype="button"	onclick="onClose()"/><fmt:message key='global.close'/></button>
					</td>
				</tr>
			</table>
			</form>
	</div>
	<div class="ft" id="dlgFoot" style="font:12px;"></div>
</div>
<!-- tree nodeId -->
<input type="hidden" id="treeNodeId_MapInfo" name="treeNodeId"/>
<!-- editMapDlg end -->
<input type="hidden" id="entryMapId" name="entryMapId"/>
<!-- editEntryDlg start -->
<div id="editEntryDlg" style="visibility:hidden">
	<div class="hd" id="entryDlgHead" style="font:12px"></div>
	<div class="bd" id="entryDlgBody" style="font:12px">
			<form id="editEntryFrm" onsubmit="return false">
			<table width="100%">
				<tr>
					<td></td>
					<td><input type="hidden" id="entryId" name="entryId" /></td>
				</tr>
				<tr>
					<td width="25%" align="right"><fmt:message key="entry.viewText"/>:&nbsp;</td>
					<td width="75%" height="21">
					<input id="viewText" name="viewText" type="text" style="width:150px;"
						class="thin-input" /><font color="#990000">&nbsp;*</font></td>
				</tr>
				<tr>
					<td width="25%" align="right"><fmt:message key="entry.refValue"/>:&nbsp;</td>
					<td width="75%">
					<input id="refValue" name="refValue" type="text" style="width:150px;"
						class="thin-input"/><font color="#990000">&nbsp;*</font></td>
				</tr>
				<tr>
					<td width="25%" align="right"><fmt:message key="entry.descn"/>:&nbsp;</td>
					<td width="75%">
					<textarea id="entryDescn" name="entryDescn" rows="3"  style="width:150px;"
						class="thin-input"></textarea></td>
				</tr>
			</table>
			<table width="100%" cellspacing="5">
				<tr>
					<td width="50%" align="right">
						<button dojotype="button" onclick="onSaveEntry()" /><fmt:message key='global.save'/></button>
					</td>
					<td width="50%" align="left">
						<button dojotype="button"	onclick="closeEntryEdit()"/><fmt:message key='global.close'/></button>
					</td>
				</tr>
			</table>
			</form>
	</div>
	<div class="ft" id="dlgFoot" style="font:12px;"></div>
</div>
<!-- editEntryDlg end -->
<script type="text/javascript">
//reset $("entryMapId").value
$("entryMapId").value="";
$("treeNodeId").value="";
</script>
</body>
</html>