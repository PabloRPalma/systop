var noEntry = "";
var addEntryNoMap = "";
var addEntryInUse = "";
var editEntryError = "";
var delEntrNone="";
/**
* init EditEntryWin
*/
function initEditEntryWin() {
	YAHOO.map.dlg.EntryEditor = new YAHOO.widget.Dialog("editEntryDlg", {visible:false,
                                                                    modal:true,
                                                                    shadow:false,
                                                                    close: true,
                                                                    fixedcenter : true ,
                                                                    width:"300px" } );
	YAHOO.map.dlg.EntryEditor.render();
	YAHOO.map.dlg.manager = new YAHOO.widget.OverlayManager();
	YAHOO.map.dlg.manager.register([YAHOO.map.dlg.EntryEditor]);
	YAHOO.map.dlg.EntryEditor.beforeHideEvent.fire = reSetEditEntryDlg;//the event at close Dial
}

/**
* to open editEntryDlg
*/
function createEntry() {
	if ($("entryMapId").value == "" || $("entryMapId").value == "-1" || $("entryMapId").value ==null) {
		 viewSimpleDlg(addEntryNoMap);
	} else {
		var dlgTextNode = document.getElementById("entryDlgHead");
	  dlgTextNode.innerHTML = createTitle;
	  YAHOO.map.dlg.EntryEditor.show();
	}
}

/**
* to edit entry
* @param obj is img 
*/
function editEntry(obj){
	mapDwrAction.getEntry(obj.id, function(entry){
		if (entry != null) {
			 var dlgTextNode = document.getElementById("entryDlgHead");
				  dlgTextNode.innerHTML = editTitle;
				  
				  DWRUtil.setValue("entryId", entry.entryId);
				  DWRUtil.setValue("viewText", entry.viewText);
				  DWRUtil.setValue("refValue", entry.refValue);
				  DWRUtil.setValue("entryDescn",entry.entryDescn);
				  
				  YAHOO.map.dlg.EntryEditor.show();
		} else {
			viewSimpleDlg(noEntry);
			//refresh table data
							 mapDwrAction.getEntryByMapId($("entryMapId").value, setEntries);
		}
	});
}

/**
* close EntryWin
*/
function closeEntryEdit() {
	YAHOO.map.dlg.EntryEditor.hide();
}

/**
* to save or update entry
*/
function onSaveEntry(){
	
	//validate vs editEntry
	if ($("viewText").value == "" || $("viewText").value == null ||
		 $("refValue").value == "" || $("refValue").value == null ) {
		viewSimpleDlg(editEntryError);
		return;
	}
	
	var entryValues = DWRUtil.getValues("editEntryFrm");
	if(entryValues.entryId == "" || entryValues.entryId == null){//id = null, is create new one
		mapDwrAction.saveEntry({viewText: entryValues.viewText, refValue: entryValues.refValue, 
					entryDescn: entryValues.entryDescn}, $("entryMapId").value,
					function(optState){
						if (optState == 22) {
							viewSimpleDlg(addEntryNoMap);
						}	else if (optState == 23){
							viewSimpleDlg(addEntryInUse + "--<font color='red'>[" + entryValues.refValue +"]</font>");
						} else {
							 //refresh table data
							 YAHOO.map.dlg.EntryEditor.hide();
							 mapDwrAction.getEntryByMapId($("entryMapId").value, setEntries);
						}
					});
	} else {// id != null is update
		mapDwrAction.updateEntry({entryId: entryValues.entryId, viewText: entryValues.viewText, 
					refValue: entryValues.refValue, entryDescn: entryValues.entryDescn},
					function(optState){
						if (optState == 23){
							viewSimpleDlg(addEntryInUse);
						} else {
							YAHOO.map.dlg.EntryEditor.hide();
							mapDwrAction.getEntryByMapId($("entryMapId").value, setEntries);
						}
					});
	}
}

/**
* to delete enteies
*/
function reMoveEntry(){
		var entryIds = new Array();
		var counts = 0;
		var removeBox = document.getElementsByName("toRemove");
		for (i = 0; i < removeBox.length; i++){
			if (removeBox[i].checked){
				entryIds[counts] = removeBox[i].value;
				counts++;
			}
		}
		
		if (entryIds.length!=0) {
			if(confirm(delWran)) {
				mapDwrAction.removeSelectEntry(entryIds, function(){
					mapDwrAction.getEntryByMapId($("entryMapId").value, setEntries);
				});
			}
		} else {
			viewSimpleDlg(delEntrNone);
		}
}

/**
* to reSet editEntry from data
*/
function reSetEditEntryDlg() {
	DWRUtil.setValue("entryId","");
  DWRUtil.setValue("viewText", "");
  DWRUtil.setValue("refValue", "");
  DWRUtil.setValue("entryDescn","");
}