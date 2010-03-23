var createTitle = "";
var editTitle = "";
var rootUnEdit = "";
var rootUnDel = "";
var delWran = "";
var noSubNode = "";
var addTitleInUse = "";
var addSignInUse = "";
var editTitleInUse = "";
var editSignInUse = "";
var imgTitle = "";
var sysCatalog = "";
var noMap = "";
var delMustNoEntry = "";
var editMapError = "";

YAHOO.namespace("map.dlg");

dojo.require("dojo.lang.*");
dojo.require("dojo.collections.*");
dojo.require("dojo.widget.Tree");
dojo.require("dojo.widget.TreeSelector");
dojo.require("dojo.widget.TreeRPCController");
dojo.require("dojo.widget.TreeNode");
dojo.require("dojo.widget.TreeContextMenu");
dojo.require("dojo.ex.FilteringTableEx");

//use to load entryTable data
var entries = new dojo.collections.ArrayList();
  
/**
* set style,and add the first root
*/
dojo.addOnLoad(function() {
		//init Dialog
		initDialog();
		//init SimpleDialog 
		initSimpleDialog();
		//set root's style
		var rootNode = dojo.widget.manager.getWidgetById('root');
		rootNode.childIcon.style["width"] = "14px";
		rootNode.childIcon.style["height"] = "14px";
		//load the root
		loadAllMaps(rootNode);
		  
		//register menu's event
		dojo.event.topic.subscribe('treeContextMenuCreate/engage',
		function (menuItem) { createClicked(); });
			
		dojo.event.topic.subscribe('treeContextMenuRemove/engage',
		    function (menuItem) { removeClicked( menuItem.getTreeNode()); });
		        
		dojo.event.topic.subscribe('treeContextMenuEdit/engage',
		    function (menuItem) { editClicked( menuItem.getTreeNode()); });
		        
		dojo.event.topic.subscribe('treeContextMenuRefresh/engage',
		    function (menuItem) { refreshClicked(menuItem.getTreeNode());});
		});

/**
*init Dialog
*/
function initDialog(){
  //init YahooUI Dialog box
  YAHOO.map.dlg.Editor = new YAHOO.widget.Dialog("editMapDlg", {visible:false,
                                                                    modal:true,
                                                                    shadow:false,
                                                                    close: true,
                                                                    fixedcenter : true ,
                                                                    width:"300px" } );
  //init editEntry win                                                                    
	initEditEntryWin();                                                                   
	YAHOO.map.dlg.Editor.render();
	YAHOO.map.dlg.manager = new YAHOO.widget.OverlayManager();
	YAHOO.map.dlg.manager.register([YAHOO.map.dlg.Editor]);
	YAHOO.map.dlg.Editor.beforeHideEvent.fire = resetDlg;//the event at close Dialog box before
}

/**
*init SimpleDialog 
*/
function initSimpleDialog(){
	// Define various event handlers for Dialog
	var handleYes = function() {
		this.hide();
	};
	var handleNo = function() {
		this.hide();
	};
	
	// Instantiate the Dialog
	YAHOO.map.dlg.sysSimpDlg = new YAHOO.widget.SimpleDialog("sysSimpDlg", 
																							 { width: "300px", 
							                                   fixedcenter: true, 
							                                   modal:true,
							                                   visible: false, 
							                                   draggable: false, 
							                                   close: true, 
							                                   constraintoviewport: true, 
								 															   buttons: [ { text:"Yes", handler:handleYes, isDefault:true }]
																							 } );
	YAHOO.map.dlg.sysSimpDlg.setHeader("");
	YAHOO.map.dlg.sysSimpDlg.render(document.body);																							 
}

/**
*view SimpleDlg
*/
function viewSimpleDlg(str){
	YAHOO.map.dlg.sysSimpDlg.setHeader(sysPrompt);
	YAHOO.map.dlg.sysSimpDlg.show();
	YAHOO.map.dlg.sysSimpDlg.setBody("<img src='../images/icons/error01.gif'>  " + str);
}

/**
* creat new Map[SystopMap]
*/
function createClicked() {
  var dlgTextNode = document.getElementById("dlgHead");
  //dlgTextNode.innerHTML = treeNode.title + '--' + createTitle;
  dlgTextNode.innerHTML = sysCatalog + '--' + createTitle;
  
  //$('parentId').value = treeNode.objectId;
  $('treeNodeId').value = "root"
  YAHOO.map.dlg.Editor.show();
}

/**
* edit Map[SystopMap]
*/
function editClicked(treeNode) {
	if (treeNode.objectId == "-1") {
		viewSimpleDlg(rootUnEdit);
		return;
	}
  mapDwrAction.getMap(treeNode.objectId, 
  		function(sysMap){
  			if (sysMap != null) {
				  var dlgTextNode = document.getElementById("dlgHead");
				  dlgTextNode.innerHTML = treeNode.title + '--' + editTitle;
				  
				  DWRUtil.setValue("treeNodeId", treeNode.widgetId);
				  DWRUtil.setValue("mapId", sysMap.mapId);
				  DWRUtil.setValue("mapTitle", sysMap.mapTitle);
				  DWRUtil.setValue("mapSign", sysMap.mapSign);
				  DWRUtil.setValue("mapDescn",sysMap.mapDescn);
				  
				  YAHOO.map.dlg.Editor.show();
  			} else {
  				viewMapInfo(treeNode.objectId);
  				setMapId_NodeId("", "")
  				viewSimpleDlg(noMap);
  				treeNode.tree.removeNode(treeNode);
  			}
		 }
	);
}

/**
* delete Map[Systop-Map]
*/
function removeClicked(treeNode) {  
	if (treeNode.objectId == "-1") {
		viewSimpleDlg(rootUnDel);
		return;
	}
  if(confirm(delWran)) {
  	mapDwrAction.getEntryByMapId(treeNode.objectId, function(entryList){
  			//delete must no netry
	  		if (entryList == null || entryList.length ==0) {
	  			mapDwrAction.removeMap(treeNode.objectId,
				      function() {
				          treeNode.tree.removeNode(treeNode);
				          viewMapInfo("-1");
				          setMapId_NodeId("", "");
				      });  
	  		} else {
	  			viewSimpleDlg(delMustNoEntry);
	  		}
  	});
  }
}

/**
* creat new subNode for appointed parentNode
*/
function buildChildNode(parent, nodeData) {
  node = dojo.widget.createWidget(parent.widgetType, nodeData);
  //node.childIconSrc ="../images/icons/tips.gif";
  //"isFolder" is attribute with Node,default is false.
  //node.isFolder = true;
  parent.addChild(node);
  node.childIcon.style["width"] = "11px";
  node.childIcon.style["height"] = "12px";
  
  return node;
}
/**
* outspread the parnetNode'subNode,And call DWR's function.
*/
function loadAllMaps(parent) {

  mapDwrAction.getAllMaps(function(children) {
     for(var i=0; i<children.length; i++) {
       buildChildNode(parent, children[i]);
     }   
     parent.state = parent.loadStates.LOADED;
   });
   parent.expand();   
}
/**
* outspread subNode
*/
function getChildren(node, sync, callObj, callFunc) {
  nodeJSON = this.getInfo(node);
  var children = loadAllMaps(node);
}

/**
* call DWR's function,and save with edit data.
*/
function onSave() {
	//validate vs editMap
	if ($("mapTitle").value == "" || $("mapTitle").value == null ||
		 $("mapSign").value == "" || $("mapSign").value == null ) {
		viewSimpleDlg(editMapError);
		return;
	}
	
  var formValues = DWRUtil.getValues("editMapFrm");
  var treeNode = dojo.widget.byId(formValues.treeNodeId);
  if(formValues.mapId == "" || !formValues.mapId){//id = null, is create new one
    mapDwrAction.saveMap({mapTitle: formValues.mapTitle,  mapSign: formValues.mapSign, mapDescn: formValues.mapDescn},
      function(newId) {
	      	if (newId == -11) {
	      		viewSimpleDlg(addTitleInUse + "--<font color='red'>[" + formValues.mapTitle + "]</font>");
	      		return ;
	      	}
	      	if (newId == -12) {
	      		viewSimpleDlg(addSignInUse +"--<font color='red'>[" + formValues.mapSign + "]</font>");
	      		return ;
	      	}
	      	if(treeNode) {//add a new node for currently parentNode.
		        if(treeNode.state == treeNode.loadStates.LOADED) {
		            var child = 
		                buildChildNode(treeNode, {title:formValues.mapTitle,
		                                          objectId:newId,
		                                          isFolder:false});
		             //child.isFolder = true;
		             __onSelectNode(child);
		             __doSelect(child, "treeSelector");
		             YAHOO.map.dlg.Editor.hide();
		         }
		       }
     	 }
      );
   }
   else { //id!=null && id!="". is update
     mapDwrAction.updateMap({mapId: formValues.mapId,
                                mapTitle: formValues.mapTitle,
                                mapSign: formValues.mapSign,
                                mapDescn: formValues.mapDescn}, 
        function(id){
        		if (id == -11) {
	      			viewSimpleDlg(editTitleInUse + "--<font color='red'>[" + formValues.mapTitle + "]</font>");
		      		return ;
		      	}
		      	if (id == -12) {
		      		viewSimpleDlg(editSignInUse + "--<font color='red'>[" + formValues.mapSign + "]</font>");
		      		return ;
		      	}
            if(treeNode) {//update node
              treeNode.edit({title:formValues.mapTitle});
              if (treeNode.objectId == $("entryMapId").value) {
              	viewMapInfo(treeNode.objectId);
              }
              YAHOO.map.dlg.Editor.hide();
            }
       });
   }
}

function onClose(){
	YAHOO.map.dlg.Editor.hide();
}

/**
* refurbish node
*/
function refreshClicked(node) {
	
  //refurbish currently node
  if (node.objectId != "-1") {
  	 mapDwrAction.getMap(node.objectId, 
	  function(sysMap) {
		  	if (sysMap != null) {
			    node.edit({title: sysMap.mapTitle, objectId: sysMap.mapId});
			    if (node.objectId == $("entryMapId").value) {
			    	viewMapInfo(node.objectId);
			    }
		  	} else {
		  		viewMapInfo(node.objectId);
		  		viewSimpleDlg(noMap);
		  		node.tree.removeNode(node);
		  	}
	  	});
  }
 
  //if no subNode ,return.
  if(!node.children || node.children.length == 0) {
    return;
  }
  //first delete all subNode,and copy all subNode to tother array.
  var nodes = new Array;
  for(i = 0; i < node.children.length; i ++) {
    nodes[i] = {body : node.children[i],    
                hasChildren : (node.children[i].chilren && node.children[i].chilren.length > 0)};
  }
  dojo.lang.forEach(nodes, function(elem){elem.body.tree.removeNode(elem.body);});
  //reload subNode
  loadAllMaps(node);
  //var rootNode = dojo.widget.manager.getWidgetById('root');
  __onSelectNode(node);
	__doSelect(node, "treeSelector");
}
/**
* move node
*/
function moveNode(child, newParent, index) {
  //alert("childId=" + child.objectId + ";\nnewParentId=" + newParent.objectId + ";\nIndex=" + index);
}

/**
* select node.
*/
function onSelectNode(message) {
  var node = message.source;
  __onSelectNode(node);
  doSelect(message, 'treeSelector');//shi tree huifu xuan zhong zhuang tai
}

/** private */
function __onSelectNode(node) {
	if(node) {
  	// entryMapId use to add entry
		setMapId_NodeId(node.objectId, node.widgetId);  	
  	
  	//load enties table data
    mapDwrAction.getEntryByMapId(node.objectId, setEntries);
    // view map info
    viewMapInfo(node.objectId);
  } 
}

/**
* to set entries. entries use to set EntryTable
*/
function setEntries(entrList) {
	//get data table
	var entryTable = dojo.widget.byId('entryTable');
	for (i = 0; i < entrList.length; i++) {
		var entry = {};
 		entry.toRemove = "<input type='checkbox' id='toRemove' name='toRemove' value='" + entrList[i].entryId + "'>"
 		entry.viewText = entrList[i].viewText;
 		entry.refValue = entrList[i].refValue;
 		entry.entryDescn = entrList[i].entryDescn;
 		//the method editEntry(obj) id editEntry.js
 		entry.entryEdit = "<a href='#'><img id='" + entrList[i].entryId + "' src='../images/icons/edit_3.gif' title='" +
 											 imgTitle + "' onclick='editEntry(this)'/></a>";
 		entries.add(entry);
	}
	entryTable.store.setData(entries.toArray());
	entries.clear();
}
/**
* Copy dojo source code,Tree hui fu xuan zhong zhuang tai
*/
function doSelect(message, treeSelectorId) {
  var node = message.source;
  __doSelect(node,treeSelectorId);
}

/** private */
function __doSelect(node,treeSelectorId){
	var selector = dojo.widget.byId(treeSelectorId);
  if (selector.selectedNode === node) {
		//if(e.ctrlKey || e.shiftKey || e.metaKey){
		//	selector.deselect();
		//	return;
		//}
		dojo.event.topic.publish(selector.eventNames.dbselect, { node: node });
		return;
	}

	if (selector.selectedNode) {
		selector.deselect();
	}
	selector.doSelect(node);
	dojo.event.topic.publish(selector.eventNames.select, {node: node} );
}

/**
*/
function neverExpired(never) {
  if(never) {
    resetDataPicker();
  }
}


/**
* reset Dialog box
*/
function resetDlg() {
  DWRUtil.setValues({mapId: "",
                     treeNodeId: ""}, "editEntryFrm");
  DWRUtil.setValue("mapTitle", "");
  DWRUtil.setValue("mapSign", "");
  DWRUtil.setValue("mapDescn","");
}

/**
*view MapInfo in page
*/
function viewMapInfo(mapId) {
	if (mapId != "-1"){
		mapDwrAction.getMap(mapId, 
  		function(sysMap){
  			if (sysMap != null) {
  				$("mapInfoImg").src="../images/icons/info-02.gif";
  				$("mapInfoTable").style.display="";
  				$("sysCatalogInfo").style.display="none";
  				$("onMapDiv").style.display="none";
	  			var mapTitle = $("mapTitleDiv");
	  			var mapSign = $("mapSignDiv");
	  			var mapDescn = $("mapDescnDiv");
	  			mapTitle.innerHTML="<font color='red'>" + sysMap.mapTitle + "</font>";
	  			mapSign.innerHTML=sysMap.mapSign;
	  			mapDescn.innerHTML=sysMap.mapDescn;
  			} else {
  				viewSimpleDlg(noMap);
  				setMapId_NodeId("", "");
  				$("mapInfoImg").src="../images/icons/error.gif";
  				$("onMapDiv").style.display="";
  				$("mapInfoTable").style.display="none";
  				$("sysCatalogInfo").style.display="none";
  			}
			}	);
	} else {
			$("mapInfoImg").src="../images/icons/info-02.gif";
			$("sysCatalogInfo").style.display="";
			$("mapInfoTable").style.display="none";
			$("onMapDiv").style.display="none";
			
	}
}

/**set value*/
function setMapId_NodeId(mapId, nodeId) {
	$("entryMapId").value=mapId;
	$("treeNodeId_MapInfo").value=nodeId;
}

/**to refresh rootnode*/
function quickReFresh(){
	var rootNode = dojo.widget.manager.getWidgetById('root');
	refreshClicked (rootNode);
}