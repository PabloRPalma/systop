dojo.require('jsam.Dialog');
dojo.require('jsam.Paginator');
dojo.require('jsam.Grid');
dojo.require('jsam.ArrayList');
dojo.require('dojo.parser');

dojo.addOnLoad(function() {
   //binding fuction for query button
    var queryButton = dojo.byId('assing_role_btn');
	dojo.connect(queryButton, 'onclick', dojo.global, function() {
        UserManager.queryRoles(UserManager.userId, 1);
    });
    
    //binding function for save buttion.
    var saveButton = dojo.byId('save_role_btn');
	dojo.connect(saveButton, 'onclick', dojo.global,  function() {
        UserManager.saveUserRole();
    });
    
    // Override the paginator's query function.
    var paginator = dijit.byId('rolesPaginator');
    if(paginator) {
        paginator.pagingFunction = function(args) {
           UserManager.queryRoles(UserManager.userId, args.pageNo);
        };
    }
    //Override the floatingPlan's close function.
    var dlg = dijit.byId("assignRolesDlg");
    if(dlg) {
        dlg.onHide = function() {
            UserRoleAction.cancelSaveUserRoles(UserManager.userId, 
                function(){});
        };
    }
    //init grid
    var table = dijit.byId('assign_roles');
    table.store.setData([]);
    
});
/**
* UserManager Object
*/
var UserManager = function() {
};
/**
 * Current user id.
 */
UserManager.userId = 0;
/**
 * PageSize.
 */
UserManager.pageSize = 10;
/**
 * Remove selected users. Change the ec form's action to Struts action.
 */
UserManager.remove = function() {
    dojo.byId('ec').action = "removeUsers.do";
    dojo.byId('ec').submit();
};
/**
 * Open roles assigning dialog.
 */
UserManager.assignRoles = function(userId, pageNo) {
    if(!userId || isNaN(userId) || userId == 0) {
        userId = UserManager.userId;
    } else {
        UserManager.userId = userId;
    }
    var dlg = dijit.byId('assignRolesDlg');
    if(dlg) {
        UserManager.queryRoles(userId, pageNo);
        dlg.show();
    }    
};
/**
 * Execute query.
 */
UserManager.queryRoles = function(userId, pageNo) {
    UserRoleAction.getRolesOfUser(userId, pageNo, UserManager.pageSize, 
        dojo.byId('assign_role_name').value,
        function(page) { //Dwr will call this function when query roles successfully.
		    var table = dijit.byId('assign_roles');
		    if(!table) return;
		    var data = new jsam.ArrayList(page.data);
			data.forEach(UserManager.forEachRoles);		    
		    table.store.setData(data.getItems());
		    //setup table data
		    var paginator = dijit.byId('rolesPaginator');
		    //refresh paginator.
		    if(paginator) {
		        paginator.setParams({
		                 pageNo    : page.pageNo,
		                 totalCount: page.rows,
		                 pageSize  : UserManager.pageSize });  
		    }    
		});
};
/**
 * Create checkbox for each Role.
 */
UserManager.forEachRoles = function(role) {
   if(!role.descn) role.descn = '&nbsp;';
   if(!role.name) role.name = '&nbsp;';
   
   var checkBox = document.createElement('input');
   checkBox.setAttribute('type', 'checkbox');
   checkBox.defaultChecked = role.changed;
   checkBox.checked = role.changed;
   checkBox.style.border = "none";
   checkBox.data = role.id; //checkBox's data is the role's ID.
   dojo.connect(checkBox, 'onclick', dojo.global,
    function(e) {
        var roleId = (e) ? e.target.data : event.srcElement.data;
        UserRoleAction.selectRole(roleId, UserManager.userId, 
            (e) ? e.target.checked : event.srcElement.checked);            
    });
   
   role.changed = checkBox;
};

UserManager.saveUserRole = function() {
    UserRoleAction.saveUserRoles(UserManager.userId, 
        function() {
             var dlg = dijit.byId('assignRolesDlg');
             if(dlg) dlg.hide();
        });
};
