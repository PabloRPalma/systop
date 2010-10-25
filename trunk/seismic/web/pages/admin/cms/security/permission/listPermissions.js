dojo.require('jsam.Dialog');
dojo.require('jsam.Paginator');
dojo.require('jsam.Grid');
dojo.require('jsam.ArrayList');
dojo.require('dojo.parser');

dojo.addOnLoad(function() {
   //binding fuction for query button
    var queryButton = dojo.byId('assing_resource_btn');
	dojo.connect(queryButton, 'onclick', dojo.global, function() {
        PermissionManager.queryResources(PermissionManager.permissionId, 1);
    });
    
    //binding function for save buttion.
    var saveButton = dojo.byId('save_resource_btn');
	dojo.connect(saveButton, 'onclick', dojo.global,  function() {
        PermissionManager.savePermissionResource();
    });
    
    // Override the paginator's query function.
    var paginator = dijit.byId('resourcesPaginator');
    if(paginator) {
        paginator.pagingFunction = function(args) {
           PermissionManager.queryResources(PermissionManager.permissionId, args.pageNo);
        };
    }
    //Override the floatingPlan's close function.
    var dlg = dijit.byId("assignResourcesDlg");
    if(dlg) {
        dlg.onHide = function() {
            PermissionResourceAction.cancleSavePermissionResources(PermissionManager.permissionId, 
                function(){});
        };
    }
    //init grid
    var table = dijit.byId('assign_resources');
    table.store.setData([]);
    
});

/**
* PermissionManager Object
*/
var PermissionManager = function() {
};
/**
 * Current permission id.
 */
PermissionManager.permissionId = 0;
/**
 * PageSize.
 */
PermissionManager.pageSize = 10;
/**
 * Remove selected permissions. Change the ec form's action to Struts action.
 */
PermissionManager.remove = function() {
    dojo.byId('ec').action = "removePermissions.do";
    dojo.byId('ec').submit();
};
/**
 * Open resources assigning dialog.
 */
PermissionManager.assignResources = function(permissionId, pageNo) {
    if(!permissionId || isNaN(permissionId) || permissionId == 0) {
        permissionId = PermissionManager.permissionId;
    } else {
        PermissionManager.permissionId = permissionId;
    }
    var dlg = dijit.byId('assignResourcesDlg');
    if(dlg) {
        PermissionManager.queryResources(permissionId, pageNo);
        dlg.show();
    }    
};

/**
 * Execute query.
 */
PermissionManager.queryResources = function(permissionId, pageNo) {
    PermissionResourceAction.getResourceOfPermission(permissionId, pageNo, PermissionManager.pageSize, 
        dojo.byId('assign_resource_name').value,
        function(page) { //Dwr will call this function when query resources successfully.
		    var table = dijit.byId('assign_resources');
		    if(!table) return;
		    var data = new jsam.ArrayList(page.data);
			data.forEach(PermissionManager.forEachResources);		    
		    table.store.setData(data.getItems());
		    //setup table data
		    var paginator = dijit.byId('resourcesPaginator');
		    //refresh paginator.
		    if(paginator) {
		        paginator.setParams({
		                 pageNo    : page.pageNo,
		                 totalCount: page.rows,
		                 pageSize  : PermissionManager.pageSize });  
		    }  
		});
};

/**
 * Create checkbox for each Resource.
 */
PermissionManager.forEachResources = function(resource) {
   
   if(!resource.descn) resource.descn = '&nbsp;';
   if(!resource.name) resource.name = '&nbsp;';
   
   var checkBox = document.createElement('input');
   checkBox.setAttribute('type', 'checkbox');
   checkBox.defaultChecked = resource.changed;
   checkBox.checked = resource.changed;
   checkBox.style.border = "none";
   checkBox.data = resource.id; //checkBox's data is the resource's ID.
   dojo.connect(checkBox, 'onclick', dojo.global,
    function(e) {
        var resourceId = (e) ? e.target.data : event.srcElement.data;
        PermissionResourceAction.selectResource(resourceId, PermissionManager.permissionId, 
            (e) ? e.target.checked : event.srcElement.checked);            
    });
   
   resource.changed = checkBox;
};

PermissionManager.savePermissionResource = function() {
    PermissionResourceAction.savePermissionResources(PermissionManager.permissionId, 
        function() {
             var dlg = dijit.byId('assignResourcesDlg');
             if(dlg) dlg.hide();
        });
};