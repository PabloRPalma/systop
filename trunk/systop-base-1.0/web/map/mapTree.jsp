<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link type="text/css" rel="stylesheet" href="templates/dojo.css">
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/style.css'/>">
	
<div dojoType="TreeRPCController" RPCUrl=""
	widgetId="mapTreeController" DNDController="create"
	loadRemote="getChildren" doMove="moveNode"></div>
<div dojoType="TreeSelector" widgetId="treeSelector"
	select="onSelectNode" dbselect="onSelectNode"></div>
<!-- ContextMenu -->
<div dojoType="TreeContextMenu" toggle="explode"
	contextMenuForWindow="false" widgetId="treeContextMenu">
<!-- create -->
<div id="treeContextMenuCreate" dojoType="TreeMenuItem" treeActions="addChild"
	iconSrc="<c:url value='/images/icons/folder_add.gif'/>"
	widgetId="treeContextMenuCreate"
	caption="<fmt:message key='global.new'/>"></div>
<!-- delete -->
<div dojoType="TreeMenuItem" treeActions="remove"
	iconSrc="<c:url value='/images/icons/folder_remove.gif'/>"
	widgetId="treeContextMenuRemove"
	caption="<fmt:message key='global.remove'/>"></div>
<!-- edit -->
<div dojoType="TreeMenuItem" treeActions="remove"
	iconSrc="<c:url value='/images/icons/folder_edit.gif'/>"
	widgetId="treeContextMenuEdit"
	caption="<fmt:message key='global.edit'/>"></div>
<div dojoType="MenuSeparator2"></div>
<div dojoType="TreeMenuItem" widgetId="treeContextMenuRefresh"
	caption="<fmt:message key='cms.refresh'/>"></div>
</div>

<div style="display:inline; float:left">
		<!-- tree -->
		<div dojoType="Tree" menu="treeContextMenu" DNDMode="between"
			selector="treeSelector" widgetId="mapTree" DNDAcceptTypes="mapTree"
			controller="mapTreeController">
		<!-- tree root -->
		<div dojoType="TreeNode" title="<fmt:message key='map.tree.title'/>" style="padding-left:100"
			widgetId="root" isFolder="true"
			childIconSrc="<c:url value='/images/icons/folder.gif'/>" objectId="-1"></div>
		</div>
</div>
		<div style="display:inline; float:left; padding-left:20px">
			<a href="#" onclick="createClicked()" ><img src="../images/icons/add_0.gif" title="<fmt:message key='global.new'/>"/></a>
			<a href="#" onclick="quickReFresh()"><img src="../images/icons/refresh02.gif" title="<fmt:message key='cms.refresh'/>"/></a>
		</div>

