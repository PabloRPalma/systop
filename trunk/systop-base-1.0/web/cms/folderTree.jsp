<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<link type="text/css" rel="stylesheet" href="templates/dojo.css">
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/style.css'/>">


<div dojoType="TreeRPCController" RPCUrl=""
	widgetId="folderTreeController" DNDController="create"
	loadRemote="getChildren" doMove="moveNode"></div>
<div dojoType="TreeSelector" widgetId="treeSelector"
	select="onSelectNode"></div>

<div dojoType="TreeContextMenu" toggle="explode"
	contextMenuForWindow="false" widgetId="treeContextMenu">
<div dojoType="TreeMenuItem" treeActions="addChild"
	iconSrc="<c:url value='/images/icons/folder_add.gif'/>"
	widgetId="treeContextMenuCreate"
	caption="<fmt:message key='global.new'/>"></div>

<div dojoType="TreeMenuItem" treeActions="remove"
	iconSrc="<c:url value='/images/icons/folder_remove.gif'/>"
	widgetId="treeContextMenuRemove"
	caption="<fmt:message key='global.remove'/>"></div>

<div dojoType="TreeMenuItem" treeActions="remove"
	iconSrc="<c:url value='/images/icons/folder_edit.gif'/>"
	widgetId="treeContextMenuEdit"
	caption="<fmt:message key='global.edit'/>"></div>
<div dojoType="MenuSeparator2"></div>
<div dojoType="TreeMenuItem" widgetId="treeContextMenuRefresh"
	caption="<fmt:message key='cms.refresh'/>"></div>
</div>


<div dojoType="Tree" menu="treeContextMenu" DNDMode="between"
	selector="treeSelector" widgetId="folderTree"
	DNDAcceptTypes="folderTree" controller="folderTreeController">
<div dojoType="TreeNode" title="<fmt:message key='cms.root'/>"
	widgetId="root" isFolder="true"
	childIconSrc="<c:url value='/images/icons/home_1.gif'/>" objectId="-1"></div>
</div>



