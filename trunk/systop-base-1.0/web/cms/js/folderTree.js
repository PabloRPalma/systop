var currentFolderId = -1;
var currentFolderName = "";
var editText = "";
var createText = "";
var deleteWarn = "";
var folderIconSrc = "";


YAHOO.namespace("folder.dlg");


/**
*
*/
dojo.addOnLoad(function() {
  
  //
  YAHOO.folder.dlg.Editor = new YAHOO.widget.Dialog("editFolderDlg", {visible:false,
                                                                    modal:true,
                                                                    shadow:false,
                                                                    close: true,
                                                                    fixedcenter : true ,
                                                                    width:"300px" } );
    YAHOO.folder.dlg.Editor.render();
    YAHOO.folder.dlg.manager = new YAHOO.widget.OverlayManager();
    YAHOO.folder.dlg.manager.register([YAHOO.folder.dlg.Editor]);
    YAHOO.folder.dlg.Editor.beforeHideEvent.fire = resetDlg;//
    //
    var rootNode = dojo.widget.manager.getWidgetById('root');
    rootNode.childIcon.style["width"] = "14px";
    rootNode.childIcon.style["height"] = "14px";
    //
    loadRemoteChildren(rootNode);
  
    //
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
* 
*/
function removeClicked(treeNode) {
  if(treeNode.objectId != -1) {
	if(confirm(deleteWarn)) {
	  cmsDojoAction.removeFolder(treeNode.objectId,
	  function(){treeNode.tree.removeNode(treeNode);});  
    }	
  }
}

/**
* 
*/
function editClicked(treeNode) {
  if(treeNode.objectId != -1) {
    cmsDojoAction.get(treeNode.objectId, function(folder){
  
    var dlgTextNode = document.getElementById("dlgHead");
    dlgTextNode.innerHTML = treeNode.title + '--' + editText;
    
    DWRUtil.setValue("treeNodeId", treeNode.widgetId);
    DWRUtil.setValue("title", folder.title);
    DWRUtil.setValue("subtitle", folder.subtitle);
    DWRUtil.setValue("id", folder.id);
    DWRUtil.setValue("descn",folder.descn);

    
    $('visible').checked = (folder.visible == '1') ? true : false;
    $('available').checked = (folder.available == '1') ? true : false;

    var datePicker = dojo.widget.byId("expireDate");
    if(folder.expireDate) {
      datePicker.setDate(folder.expireDate);
      $('neverExp').checked = false;
    } else {
      datePicker.inputNode.value="";
    }
    
    YAHOO.folder.dlg.Editor.show();
    });
  }
}

/**
*
*/
function createClicked(treeNode) {
  var dlgTextNode = document.getElementById("dlgHead");
  dlgTextNode.innerHTML = treeNode.title + '--' + createText;
  
  $('parentId').value = treeNode.objectId;
  $('treeNodeId').value = treeNode.widgetId;
    
  YAHOO.folder.dlg.Editor.show();
}

/**
*
*/
function buildChildNode(parent, nodeData) {
  node = dojo.widget.createWidget(parent.widgetType, nodeData);
  node.childIconSrc = folderIconSrc;
  parent.addChild(node);
  node.childIcon.style["width"] = "16px";
  node.childIcon.style["height"] = "16px";
  
  return node;
}
/**
*
*/
function loadRemoteChildren(parent) {

  cmsDojoAction.getFoldersByParentId(parent.objectId, function(children) {
     for(var i=0; i<children.length; i++) {
       buildChildNode(parent, children[i]);
     }   
     parent.state = parent.loadStates.LOADED;
   });
   parent.expand();   
}
/**
*
*/
function getChildren(node, sync, callObj, callFunc) {
  nodeJSON = this.getInfo(node);
  var children = loadRemoteChildren(node);
}

/**
*
*/
function onSave() {
  var datePicker = dojo.widget.byId("expireDate");
  var date = datePicker.getDate();
  if(date == "" || datePicker.inputNode.value == "") {
    date = null;
  } else {
    date = Date.parse(date);
  }
  var available;
  var visible;

  $('available').checked ? available = '1' : available = '0';
  $('visible').checked ? visible = '1' : visible = '0';
 
  
  var formValues = DWRUtil.getValues("editFolderFrm");
    
  if(formValues.id == "" || !formValues.id){//
    cmsDojoAction.saveFolder(formValues.parentId,
      {title: formValues.title,
       subtitle: formValues.subtitle,
       descn: formValues.descn,
       available: available,
       visible: visible,
       expireDate: date},
      function(newId) {
         var treeNode = dojo.widget.byId(formValues.treeNodeId);
         if(treeNode) {//
           if(treeNode.state == treeNode.loadStates.LOADED) {
              var child = 
                  buildChildNode(treeNode, {title:formValues.title,
                                            objectId:newId, 
                                            isFolder:true});
              child.isFolder = true;
           }
         }
         
      });
   }
   else { //
     cmsDojoAction.updateFolder({id: formValues.id,
                                 title: formValues.title,
                                 subtitle: formValues.subtitle,
                                 descn: formValues.descn,
                                 available: available,
                                 visible: visible,
                                 expireDate: date}, 
        function(id){
          var treeNode = dojo.widget.byId(formValues.treeNodeId);
            if(treeNode) {//
              treeNode.edit({title:formValues.title});
            }
       });
   }
   
   YAHOO.folder.dlg.Editor.hide();
}

/**
*
*/
function refreshClicked(node) {
  //
  if(node.objectId != -1) {
	   cmsDojoAction.get(node.objectId, function(folder) {
	    node.edit({title: folder.title, objectId: folder.id})
	  });
	  //
	  if(!node.children || node.children.length == 0) {
	    return;
	  }
	  //
	  //dojo.lang.forEach(node.children...
	  //
	  var nodes = new Array;
	  for(i = 0; i < node.children.length; i ++) {
	    //hasChildren
	    nodes[i] = {body : node.children[i],    
	                hasChildren : (node.children[i].chilren && node.children[i].chilren.length > 0)};
	  }
	  dojo.lang.forEach(nodes, function(elem){elem.body.tree.removeNode(elem.body);});
	  //
	  loadRemoteChildren(node);
  }
}
/**
*Drag and Drop
*/
function moveNode(child, newParent, index) {
  alert("childId=" + child.objectId + ";\nnewParentId=" + newParent.objectId + ";\nIndex=" + index);
}
/**
*
*/
function onSelectNode(message) {
  var node = message.source;
  if(node) {
    currentFolderName = node.title;
    currentFolderId = node.objectId;
    cms.clearFilter();
    cms.folderId = node.objectId;
    cms.query(1);
  } 
  
  //doSelect(message, 'treeSelector');//
}
/**
* Copy dojoTree
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
*DatePicker
*/
function neverExpired(never) {
  if(never) {
    resetDataPicker();
  }
}
/**
*DataTimePicker
*/
function resetDataPicker() {
  var datePicker = dojo.widget.byId("expireDate");
  datePicker.value = 'today';
  datePicker.inputNode.value="";
}
/**
*
*/
function onSetExpireDate(date) {   

  if(date && dojo.widget.byId("expireDate").inputNode.value != "") {
    $('neverExp').checked = false;
  } else {
    $('neverExp').checked = true;
  }
}

/**
*
*/
function resetDlg() {
  DWRUtil.setValues({parentId: "",
                     id: "",
                     title: "",
                     treeNodeId: ""}, "editFolderFrm");
  resetDataPicker();//
  DWRUtil.setValue("title", "");
  DWRUtil.setValue("subtitle", "");
  DWRUtil.setValue("descn","");
  $('neverExp').checked = true;
  $('available').checked = true;
  $('visible').checked = true;
}