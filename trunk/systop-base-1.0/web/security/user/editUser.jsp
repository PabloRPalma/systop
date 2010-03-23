<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><fmt:message key= "security.user.edit"/></title>
<%@include file="/common/meta.jsp" %>

<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/interface/userAction.js"/>'> </script>
<script type="text/javascript" src='<c:url value="/dwr/interface/deptDojoAction.js"/>'></script>

<script src="<c:url value='/js/prototype.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/dojo/dojo.js'/>" type="text/javascript"></script>

<link type="text/css" rel="stylesheet" href="/security/dept/templates/dojo.css">
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/style.css'/>">

<script>


dojo.require("dojo.lang.*");
dojo.require("dojo.widget.Tree");
dojo.require("dojo.widget.TreeRPCController");
dojo.require("dojo.widget.TreeSelector");
dojo.require("dojo.widget.TreeNode");
dojo.require("dojo.widget.TreeContextMenu");

dojo.require("dojo.widget.FloatingPane");
dojo.require("dojo.widget.ResizeHandle");


/**
* 设置根目录style，并且加载第一级目录
*/
dojo.addOnLoad(function() {
  
    //设置根节点style
    var rootNode = dojo.widget.manager.getWidgetById('root');
    rootNode.childIcon.style["width"] = "14px";
    rootNode.childIcon.style["height"] = "14px";
    //加载第一级目录
    loadRemoteChildren(rootNode);
    
    var dlg = dojo.widget.byId("treeDlg");
    dlg.closeWindow = function() {
        dlg.hide();
    }
  }
);



/**
* 在给定的父节点下，建立子节点.
*/
function buildChildNode(parent, nodeData) {
  node = dojo.widget.createWidget(parent.widgetType, nodeData);
  
  node.childIconSrc = '<c:url value="/images/icons/foldericon.png"/>';
  //isFolder是node一个内置属性，缺省为false在树中是不会显示的，为true时才会显示出来
  node.isFolder = true;
  parent.addChild(node);
  node.childIcon.style["width"] = "16px";
  node.childIcon.style["height"] = "16px";
  
  return node;
}


function onDoubleClick(id, title){
  DWRUtil.setValue("model.dept.name",title);
  DWRUtil.setValue("model.dept.id",id);
  colseDlg();
}

function colseDlg() {
  var treeDlg = dojo.widget.byId('treeDlg');
  if(treeDlg) {
     treeDlg.hide();
  }
}
/**
* 根据给定的父节点，展开子节点。远程调用DWR函数。
*/
function loadRemoteChildren(parent) {

  deptDojoAction.getDeptsByParentId(parent.objectId, function(children) {
     for(var i=0; i<children.length; i++) {
       var title = "<font ondblclick='onDoubleClick("
	       + children[i].objectId + ",\""
	       + children[i].title
	       + "\")'>"
	       + children[i].title
	       + "</font>";
      
       children[i].title = title;
       buildChildNode(parent, children[i]);
     }   
     parent.state = parent.loadStates.LOADED;
   });
   parent.expand();   
}
/**
* 展开子节点
*/
function getChildren(node, sync, callObj, callFunc) {
  nodeJSON = this.getInfo(node);
  var children = loadRemoteChildren(node);
}
</script>
</head>
<body>

<s:i18n name="application">

<table align="center" width="650" border = "0">
<tr>
   <td>
   <div id="checkInfo" style="border:1px solid #b6c0db;display:none"></div>
   <%@ include file="/common/messages.jsp" %>
   </td>
</tr>
<s:form action="saveUser" theme="simple" validate="true">
<s:hidden name="model.id" />
<script>
  var loginId = '${model.loginId}';
  window.onload = function() {
      $('model.loginId').focus();
  };
</script>
<s:hidden name="model.version" />
<s:hidden name="model.dept.id"/>
  <tr>
    <td colspan="3" align="center">
    <fieldset>
    <legend><fmt:message key="security.user.edit"/></legend>
    <table width="100%" border="0" cellspacing="2" cellpadding="2">
    <tr>
       <td align="right" width="15%"><fmt:message key="security.user.loginid"/>:</td>
       <td width="40%">
       <s:textfield name="model.loginId" id="model.loginId" theme="simple" onblur="onCheckUsername(this.value)" cssStyle="width:150px" /></td>
       <td align="left"><div style="color:#990000">*&nbsp;用户登录时输入的名字，不允许重复。</div></td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.user.password"/>:</td>
       <td><s:password name="model.password" theme="simple" cssStyle="width:150px" /></td>
       <td align="left"><div style="color:#990000">*</div></td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.user.confirmPwd"/>:</td>
       <td><s:password name="confirmPwd" theme="simple"  cssStyle="width:150px"/></td>
       <td align="left"><div style="color:#990000">*&nbsp;重复输入密码，以确保准确。</div>
       </td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.user.name"/>:</td>
       <td><s:textfield name="model.name" theme="simple" cssStyle="width:150px"/></td>
       <td align="left"><div style="color:#990000">&nbsp;</div>
       </td>
    </tr>
    
    <tr>
       <td align="right"><fmt:message key="security.user.dept"/>:</td>
       <td><s:textfield name="model.dept.name" theme="simple" cssStyle="width:150px" disabled="true"/>
       <img src="<c:url value='/images/icons/dept.gif'/>" onclick='openDeptsDialog()'> </td>
       <td align="left"><div style="color:#990000">*&nbsp;
       用户所属部门，点击<img src="<c:url value='/images/icons/dept.gif'/>" border="0"/>选择。</div>
       
       </td>
    </tr>
    
    <tr>
       <td align="right"><fmt:message key="security.user.status"/>:</td>
       <td>
       <systop:catalogSelector catalog="user_status" name="model.status" defaultValue="1"/>
       </td>
       <td align="left">
       
       </td>
    </tr>
    </table>
    <s:submit value="%{getText('global.save')}" cssClass="button" />
    </fieldset>
    </td>
  </tr>
</s:form>
</table>
</s:i18n>
<script type="text/javascript">

  function onCheckUsername(name) {
    <s:if test="%{model.id == null}">
      //新建用户的时候肯定检查
      userAction.isLoginIdInUse(name, renderCheck);
    </s:if>
    <s:else>
      if(loginId != name) {//如果不是新建用户，则只有修改之后才验证
        userAction.isLoginIdInUse(name, renderCheck);
      }
      else{
        $('checkInfo').style.display="none";
        $('checkInfo').innerHTML = "";
      }
    </s:else>
  }
  
  function renderCheck(isUsed) {
    if(isUsed) {
      $('checkInfo').style.display="";
      $('checkInfo').innerHTML="<img src='<c:url value="/images/icons/warning.gif"/>'/><fmt:message key='security.user.idInUse' />";
      $('model.loginId').focus();
    }
    else {
      $('checkInfo').style.display="none";
      $('checkInfo').innerHTML = "";
    }
  }
  

  
  function openDeptsDialog() {
      var treeDlg = dojo.widget.byId('treeDlg');
      if(treeDlg) {
          treeDlg.show();
      }
  }

</script>


<div id="deptTree1">
<div dojoType="TreeRPCController" RPCUrl=""
	widgetId="deptTreeController" DNDController="create"
	loadRemote="getChildren" doMove="moveNode"></div>
<div dojoType="TreeSelector" widgetId="treeSelector"></div>
<!-- 上下文菜单 -->
<div dojoType="TreeContextMenu" toggle="explode"
	contextMenuForWindow="false" widgetId="treeContextMenu">
</div>

<div dojoType="ModalFloatingPane" id="treeDlg" title="<fmt:message key='security.user.addDept' />"
	style="width: 400px; height: 200px;"
	bgColor="white" bgOpacity="0.5" toggle="fade" toggleDuration="1">
		<!-- 树形目录 -->
	<div dojoType="Tree" menu="treeContextMenu" DNDMode="between"
		selector="treeSelector" widgetId="deptTree"
		DNDAcceptTypes="deptTree" controller="deptTreeController">
		<!-- 树形目录根节点 -->
	<div dojoType="TreeNode" title="<fmt:message key='cms.root'/>"
		widgetId="root" 
		childIconSrc="<c:url value='/images/icons/home_1.gif'/>" objectId="-1"></div>
	</div>
</div>


</body>
</html>