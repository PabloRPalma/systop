dojo.require('jsam.Dialog');
dojo.require('jsam.Paginator');
dojo.require('jsam.Grid');
dojo.require('jsam.ArrayList');
dojo.require('dojo.parser');

dojo.addOnLoad(function(){
  //binding function for query button
  var queryButton = dojo.byId('assing_permission_btn');
  dojo.connect(queryButton,'onclick',dojo.global,function(){
	  RoleManager.queryPermissions(RoleManager.roleId, 1);
  });
    
  //binding function for save button
  var saveButton = dojo.byId('save_permission_btn');
  dojo.connect(saveButton, 'onclick', dojo.global, function() {
	  RoleManager.saveRolePermission();
  });

  //Override the paginator's query function
  var paginator = dijit.byId('permissionsPaginator');
  if(paginator) {
	  paginator.pagingFunction = function(args) {
		  RoleManager.queryPermissions(RoleManager.roleId, args.pageNo);
	  };
  }

  //override the floatingPlan's close function
  var dlg = dijit.byId("assignPermissionsDlg");
  if(dlg) {
	  dlg.onHide = function() {
		  RolePermissionAction.cancelSaveRolePermissions(RoleManager.roleId,
			  function(){});
	  };
  }
  //init grid
  var table = dijit.byId('assign_permissions');
  table.store.setData([]);
});

/**
 * RoleManager Object
 */
var RoleManager = function(){
};
/**
 * Current role id
 */
RoleManager.roleId = 0;
/**
 * PageSize
 */
RoleManager.pageSize = 10;
/**
 * Open permissions assigning dialog
 */
RoleManager.assignPermissions = function(roleId, pageNo) {
	if(!roleId || isNaN(roleId) || roleId == 0) {
		roleId = RoleManager.roleId;
	  } else {
		  RoleManager.roleId = roleId;
	  }
	  var dlg = dijit.byId('assignPermissionsDlg');
	  if (dlg) {
		  RoleManager.queryPermissions(roleId, pageNo);
		  dlg.show();
	  }
  };

  /**
   * Execute query
   */
   RoleManager.queryPermissions = function(roleId, pageNo) {
	   RolePermissionAction.getPermissionsOfRole(roleId, pageNo, RoleManager.pageSize,
		   dojo.byId('assign_permission_name').value,
		   function(page) {  //Dwr will call this function when query permissions successfully
		   var table = dijit.byId('assign_permissions');
		   if (!table) return;
		   var data = new jsam.ArrayList(page.data);
		   data.forEach(RoleManager.forEachPermissions);
		   table.store.setData(data.getItems());
		   //setup table data
		   var paginator = dijit.byId('permissionsPaginator');
		   //refresh paginator
		   if (paginator) {
			   paginator.setParams ({
				   pageNo    :page.pageNo,
				   totalCount:page.rows,
				   pageSize  :RoleManager.pageSize});
		   }
	   });
   };

   /**
    * Create checkbox for each Permission
	*/
	RoleManager.forEachPermissions = function(permission) {
		if (!permission.descn) permission.descn = '&nbsp;';
		if (!permission.name) permission.name ='&nbsp;';

		var checkBox = document.createElement('input');
		checkBox.setAttribute('type', 'checkbox');
		checkBox.defaultChecked = permission.changed;
		checkBox.checked = permission.changed;
		checkBox.style.border = "none";
		checkBox.data = permission.id; //checkBox's data is the role's ID
		dojo.connect (checkBox, 'onclick', dojo.global,
			function(e) {
			var permissionId = (e) ? e.target.data : event.srcElement.data;
			RolePermissionAction.selectPermission(permissionId, RoleManager.roleId,
				(e) ? e.target.checked : event.srcElement.checked);
		});
		permission.changed = checkBox;
	};

	RoleManager.saveRolePermission = function() {
		RolePermissionAction.saveRolePermissions(RoleManager.roleId,
			function() {
			var dlg = dijit.byId('assignPermissionsDlg');
			if (dlg) dlg.hide();
		});
	};