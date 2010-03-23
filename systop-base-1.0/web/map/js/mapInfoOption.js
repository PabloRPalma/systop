var backConfirm;
var backSuccess;
var backFail;
var revertconfim;
var revertSucess;
var revertFail;
function mapInfoEdit() {
	var treeNode = dojo.widget.manager.getWidgetById($("treeNodeId_MapInfo").value);
	if (treeNode) {
		editClicked(treeNode);
	} else {
		viewSimpleDlg(noMap);
	}
}

function mapInfoRemove() {
	var treeNode = dojo.widget.manager.getWidgetById($("treeNodeId_MapInfo").value);
	if (treeNode) {
		removeClicked(treeNode);
	} else {
		viewSimpleDlg(noMap);
	}
}

function mapInfoRefresh() {
	var treeNode = dojo.widget.manager.getWidgetById($("treeNodeId_MapInfo").value);
	if (treeNode) {
		refreshClicked(treeNode);
	} else {
		viewSimpleDlg(noMap);
	}
}

function backupData() {
	if(confirm(backConfirm)) {
		mapDwrAction.backup(function(isOk) {
			if (isOk) {
				viewSimpleDlg(backSuccess);	
			} else {
				viewSimpleDlg(backFail);
			}
		});
	}
}

function revertData() {
	if(confirm(revertconfim)) {
		mapDwrAction.revert(function(isOk) {
			if (isOk) {
				var handleYes = function() {
					this.hide()
					location.reload();
				};
				YAHOO.map.dlg.revertDlg = new YAHOO.widget.SimpleDialog("revertDlg", 
																									 { width: "300px", 
									                                   fixedcenter: true, 
									                                   modal:true,
									                                   visible: true, 
									                                   draggable: false, 
									                                   close: true, 
									                                   constraintoviewport: true, 
										 															   buttons: [ { text:"Yes", handler:handleYes, isDefault:true }]
																									 } );
				YAHOO.map.dlg.revertDlg.setHeader(sysPrompt);
				YAHOO.map.dlg.revertDlg.render(document.body);	
				YAHOO.map.dlg.revertDlg.setBody("<img src='../images/icons/error01.gif'>  " + revertSucess);													
			} else {
				viewSimpleDlg(revertFail);
			}
			//location.reload();
		});
	}
}
