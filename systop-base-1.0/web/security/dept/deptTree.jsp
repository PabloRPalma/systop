<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/styles/dojoTable.css"/>">

</head>
<body>
<%@include file="/common/yahooUi.jsp"%>
<link type="text/css" rel="stylesheet" href="templates/dojo.css">
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/style.css'/>">


<script>


YAHOO.namespace("dept.dlg");

/**
* 设置根目录style，并且加载第一级目录
*/
dojo.addOnLoad(function() {
  //初始化YahooUI对话框
  YAHOO.dept.dlg.Editor = new YAHOO.widget.Dialog("editDeptDlg", {visible:false,
                                                                    modal:true,
                                                                    shadow:false,
                                                                    close: true,
                                                                    fixedcenter : true ,
                                                                    width:"300px" } );
	YAHOO.dept.dlg.Editor.render();
	YAHOO.dept.dlg.manager = new YAHOO.widget.OverlayManager();
	YAHOO.dept.dlg.manager.register([YAHOO.dept.dlg.Editor]);
	YAHOO.dept.dlg.Editor.beforeHideEvent.fire = resetDlg;//对话框关闭之前的事件。
    //设置根节点style
    //var rootNode = dojo.widget.manager.getWidgetById('root');
    var rootNode = dojo.widget.byId('root');
    rootNode.childIcon.style["width"] = "14px";
    rootNode.childIcon.style["height"] = "14px";
    //加载第一级目录
    loadRemoteChildren(rootNode);
    
    dojo.event.topic.subscribe('nodeSelected',
        function (message) { onSelectNode(message);  });
        
    //var treeSelector = dojo.widget.manager.getWidgetById('treeSelector');    
    //dojo.event.connect(treeSelector,'select','onSelectNode');   
    //dojo.event.connect(treeSelector,'dblselect','onSelectNode');   
  
    //注册菜单事件
    dojo.event.topic.subscribe('treeContextMenuCreate/engage',
	 	function (menuItem) { createClicked( menuItem.getTreeNode()); });
	
    dojo.event.topic.subscribe('treeContextMenuRemove/engage',
        function (menuItem) { removeClicked( menuItem.getTreeNode()); });
        
    dojo.event.topic.subscribe('treeContextMenuEdit/engage',
        function (menuItem) { editClicked( menuItem.getTreeNode()); });
        
    dojo.event.topic.subscribe('treeContextMenuRefresh/engage',
        function (menuItem) { refreshClicked(menuItem.getTreeNode());});
  }
);


/**
* 新建部门菜单事件
*/
function createClicked(treeNode) {
  var dlgTextNode = document.getElementById("dlgHead");
  dlgTextNode.innerHTML = treeNode.title + '--' + '<fmt:message key="global.new" />';
  
  $('parentId').value = treeNode.objectId;
  $('treeNodeId').value = treeNode.widgetId;
    
  YAHOO.dept.dlg.Editor.show();
}



/**
* 编辑目录菜单事件
*/
function editClicked(treeNode) {
  if(treeNode.objectId != -1) {
	    deptDojoAction.get(treeNode.objectId, function(dept){
	  
	    var dlgTextNode = document.getElementById("dlgHead");
	    dlgTextNode.innerHTML = treeNode.title + '--' + '<fmt:message key="global.edit" />';
	    
	    DWRUtil.setValue("treeNodeId", treeNode.widgetId);
	    DWRUtil.setValue("name", dept.name);
	    DWRUtil.setValue("id", dept.id);
	    DWRUtil.setValue("descn",dept.descn);
	    
	    YAHOO.dept.dlg.Editor.show();
	  });
  }
}


/**
* 删除部门菜单事件
*/
function removeClicked(treeNode) {  
  if(treeNode.objectId != -1) {
	  if(confirm('<fmt:message key="global.delete.warn"/>')) {
	      deptDojoAction.removeDept(treeNode.objectId,
	      function(subDeptNum) {
	        if(subDeptNum == 0) {
	          treeNode.tree.removeNode(treeNode); 
	        } else {
	          alert('<fmt:message key="dept.delete.warn"/>');
	        } 
	      });  
	    } 
	  }
}

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


/**
* 根据给定的父节点，展开子节点。远程调用DWR函数。
*/
function loadRemoteChildren(parent) {

  deptDojoAction.getDeptsByParentId(parent.objectId, function(children) {
     for(var i=0; i<children.length; i++) {
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
  $('deptMsg').style.display = 'none';
}

/**
* 调用DWR函数，保存编辑结果
*/
function onSave() {
 
  var formValues = DWRUtil.getValues("editDeptFrm");
    
  if(formValues.id == "" || !formValues.id){//当id不存在时，表示新建
    deptDojoAction.saveDept(formValues.parentId,
      {name: formValues.name,
       descn: formValues.descn},
      function(newId) {
         var treeNode = dojo.widget.byId(formValues.treeNodeId);
         if(treeNode) {//当前节点下加入一个新节点
           if(treeNode.state == treeNode.loadStates.LOADED) {
              var child = 
                  buildChildNode(treeNode, {title:formValues.name,
                                            objectId:newId,
                                            object:formValues.descn,
                                            isFolder:true});
              //child.isFolder = true;
           }
         }
         
      });
   }
   else { //如果id存在则表示更新
     deptDojoAction.updateDept({id: formValues.id,
                                name: formValues.name,
                                descn: formValues.descn}, 
        function(id){
          var treeNode = dojo.widget.byId(formValues.treeNodeId);
            if(treeNode) {//更新节点
              treeNode.edit({title:formValues.name,
                             object:formValues.descn});
            }
       });
   }
   
   YAHOO.dept.dlg.Editor.hide();
}

/**
* 刷新树形节点
*/
function refreshClicked(node) {
  if(node.objectId != -1) {
	//更新当前节点
	deptDojoAction.get(node.objectId, function(dept) {
	node.edit({title: dept.name, objectId: dept.id})
	});
  }
  //如果没有子节点，则直接返回
  if(!node.children || node.children.length == 0) {
    return;
  }
  //首先要删除所有子节点，删除的时候要将子节点们复制到一个数组
  //如果不这样，而采用dojo.lang.forEach(node.children...
  //则相当于修改了循环下标
  var nodes = new Array;
  for(i = 0; i < node.children.length; i ++) {
    //hasChildren用于标记节点是否已经展开，如果展开则加载子节点，但是，本次并未实现该功能
    nodes[i] = {body : node.children[i],    
                hasChildren : (node.children[i].chilren && node.children[i].chilren.length > 0)};
  }
  dojo.lang.forEach(nodes, function(elem){elem.body.tree.removeNode(elem.body);});
  //重新加载子节点
  loadRemoteChildren(node);
}
/**
* 移动（Drag and Drop）一个节点到新的节点下
*/
function moveNode(child, newParent, index) {
  alert("childId=" + child.objectId + ";\nnewParentId=" + newParent.objectId + ";\nIndex=" + index);
}

var files = new dojo.collections.ArrayList();

function dblselect(message) {
alert();
}

/**
* 选中一个节点的时候触发的事件。
*/
function onSelectNode(message){
var node = message.node;
var table = dojo.widget.byId('files');
  if(node) {
  $('deptMsg').style.display = 'block';
    DWRUtil.setValue('deptMsg',node.object);
    
    deptDojoAction.getSubDeptsByParentId(node.objectId, function(subDept) {
     for(var i=0; i<subDept.length; i++) {
       var file = {};
       file.no = i+1;
       file.filename = subDept[i].name;
       if(subDept[i].descn) {
       file.deptDesc = subDept[i].descn;
       } else {
       file.deptDesc = "";
       }
      
       files.add(file);
     }
     table.store.setData(files.toArray());
     files.clear();
   });
   
  } 
 
}

/**
* Copy dojo的源代码，使得Tree恢复选中状态
*/
function doSelect(message, treeSelectorId) {
  var node = message.source;
  var e = message.event;
  
  var selector = dojo.widget.byId(treeSelectorId);
  if (selector.selectedNode === node) {
		if(e.ctrlKey || e.shiftKey || e.metaKey){
			selector.deselect();
			return;
		}
		dojo.event.topic.publish(this.eventNames.dblselect, { node: node });
		return;
	}

	if (selector.selectedNode) {
		selector.deselect();
	}
	selector.doSelect(node);
	dojo.event.topic.publish(selector.eventNames.select, {node: node} );
}

/**
* 复选框选中事件。选中复选框，则清空DatePicker
*/
function neverExpired(never) {
  if(never) {
    resetDataPicker();
  }
}


/**
* 重新设置对话框
*/
function resetDlg() {
  DWRUtil.setValues({parentId: "",
                     id: "",
                     treeNodeId: ""}, "editDeptFrm");
  DWRUtil.setValue("name", "");
  DWRUtil.setValue("descn","");
}
</script>


<div dojoType="TreeRPCController" RPCUrl=""
	widgetId="deptTreeController" DNDController="create"
	loadRemote="getChildren" doMove="moveNode"></div>

<!-- 上下文菜单 -->
<div dojoType="TreeContextMenu" toggle="explode"
	contextMenuForWindow="false" widgetId="treeContextMenu"><!-- 新建目录 -->
<div dojoType="TreeMenuItem" treeActions="addChild"
	iconSrc="<c:url value='/images/icons/folder_add.gif'/>"
	widgetId="treeContextMenuCreate"
	caption="<fmt:message key='global.new'/>"></div>
<!-- 删除目录 -->
<div dojoType="TreeMenuItem" treeActions="remove"
	iconSrc="<c:url value='/images/icons/folder_remove.gif'/>"
	widgetId="treeContextMenuRemove"
	caption="<fmt:message key='global.remove'/>"></div>
<!-- 编辑目录 -->
<div dojoType="TreeMenuItem" treeActions="remove"
	iconSrc="<c:url value='/images/icons/folder_edit.gif'/>"
	widgetId="treeContextMenuEdit"
	caption="<fmt:message key='global.edit'/>"></div>
<div dojoType="MenuSeparator2"></div>
<div dojoType="TreeMenuItem" widgetId="treeContextMenuRefresh"
	caption="<fmt:message key='cms.refresh'/>"></div>
</div>

<div dojoType="TreeSelector" widgetId="treeSelector" eventNames="select:nodeSelected;dblselect:nodeSelected"></div>
<!-- 树形目录 -->
<div dojoType="Tree" menu="treeContextMenu" DNDMode="between"
	selector="treeSelector" widgetId="deptTree"
	DNDAcceptTypes="deptTree" controller="deptTreeController">
	<!-- 树形目录根节点 -->
<div dojoType="TreeNode" title="<fmt:message key='dept.root'/>"
	widgetId="root" isFolder="true"
	childIconSrc="<c:url value='/images/icons/home_1.gif'/>" objectId="-1"></div>
	
</div>





</body>
</html>
