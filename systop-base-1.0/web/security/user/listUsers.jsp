<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<%@include file="/common/meta.jsp"%>
<script type='text/javascript' src='<c:url value="/js/template.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'> </script>

<script type='text/javascript'
	src='<c:url value="/dwr/interface/roleAction.js"/>'> </script>

<script type="text/javascript" src="<c:url value='/js/dojo/dojo.js'/>"></script>
<s:action id="userAction" name="userAction" namespace="/security/user" executeResult="false"/>
<%@include file="/common/yahooUi.jsp"%>
<title><fmt:message key="security.user.title" /></title>
<script type="text/javascript">
dojo.require("dojo.widget.*");
dojo.require("dojo.ex.Paginator");
</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0" >
	<tr>
		<td height="20" bgcolor="#cecece"
			style="padding-top:2px; padding-left:10px"><img
			src="<c:url value='/images/icons/add_3.gif'/>" border="0">
		<a href="<c:url value='/security/user/editUser.jsp' />"> <fmt:message key="global.new" /></a> <img
			src="<c:url value='/images/icons/del.gif'/>" border="0">
		<a href="#"><font onclick="onDelete()" style="font:12.5px">
		<fmt:message key="global.remove" /></font></a></td>
	</tr>
	<tr height="15%">
		<td align="center">
		<fieldset class="fieldsetStyle">
		<legend><fmt:message key="global.query" /></legend>
		<table width="95%" align="left">
			<s:form action="queryUsers" theme="simple" name="queryUsers">
				<tr>
					<td width="50"><fmt:message key="security.user.loginid" />:</td>
					<td width="150" align="left"><s:textfield name="model.loginId"
						theme="simple" /></td>
					<td width="50"><fmt:message key="security.user.name" />:</td>
					<td width="150" align="left"><s:textfield name="model.name"
						theme="simple" /></td>
					<td width="50"><fmt:message key="security.user.status" />:</td>
					<td width="70" align="left">
					<systop:catalogSelector catalog="user_status" name="model.status" defaultValue="" defaultLabel="global.all"/>
					</td>
					<td align="left"><a href="#"
						onclick="javascript:$('queryUsers').submit()"> <img
						src="<c:url value='/images/icons/search_1.gif'/>" border="0"></a>
					</td>
				</tr>
			</s:form>
		</table>
		</fieldset>
		</td>
	</tr>
	<s:form action="removeUsers" name="removeUsers" id="removeUsers"
		theme="simple">
	</s:form>
	<s:form action="listRoles" name="listRoles" id="listRoles"
		theme="simple">
		<input type="hidden" value="" name="model.id" id="listRoles.id" />
	</s:form>
	<tr height="80%">
		<td valign="top" align="center"><ec:table items="availableItems"
			var="user" action="#" rowsDisplayed="25" autoIncludeParameters="true"
			style="width:95%">
			<ec:exportXls fileName="AllUsers.xls" tooltip="global.export" />

			<ec:row>
				<ec:column property="changed" title="global.select" sortable="false"
					viewsAllowed="html" style="width:5%"><div align="center">
					<input type="checkbox" name="selectedItems" id="selectedItems"
						value="${user.id}"></div>
				</ec:column>
				<ec:column property="rvclowcount" cell="rowCount"
					title="global.serialno" sortable="false" style="width:5%" />
				<ec:column property="loginId" title="security.user.loginid" width="15%"></ec:column>
				<ec:column property="name" title="security.user.name" width="15%"></ec:column>
				<ec:column property="descn" title="security.user.descn"></ec:column>
				<ec:column property="dept.name" title="security.user.dept" width="15%"></ec:column>
				<ec:column property="status" alias="user_status" width="5%"
					title="security.user.status"
					cell="com.systop.common.webapp.extremecomponents.CatalogCell"></ec:column>
				<ec:column property="user_role" title="security.role"
					sortable="false" style="width:5%">
					<div style="cursor:pointer" align="center"><img
						src="<c:url value='/images/icons/users_1.gif'/>"
						onclick="openUserRolesDialog(1, <fmt:message key='default.pagesize'/>, '${user.id}', null)">
					</div>
				</ec:column>
				<ec:column property="edit" title="global.edit" sortable="false"
					style="width:5%">
					<div onclick="onEdit('${user.id}')" style="cursor:pointer" align="center"><img
						src="<c:url value='/images/icons/modify.gif'/>"></div>
				</ec:column>

			</ec:row>
		</ec:table></td>
	</tr>
	<s:form theme="simple" action="prepareEditUser" name="prepareEditUser"
		id="prepareEditUser">
		<input type="hidden" value="" name="model.id" id="prepareEditUser.id" />
	</s:form>
</table>
<script>
function onEdit(id) {
 var frm = $('prepareEditUser');
 $("prepareEditUser.id").value = id;
 frm.submit();
}

function onDelete() {
  if(confirm('<fmt:message key="security.delete.warn"/>')) {
    $('ec').action = $('removeUsers').action;
    $('ec').submit();
  }
}
function onAuthRoles(id){
	$('listRoles.id').value=id;
    $('listRoles').submit();
}

</script>

<script>
 YAHOO.namespace("roles.container");
 var inited = false;//角色选择对话框初始化标记
 var currentUserId; //暂存当前选择的用户Id
 var queryParam;    //暂存当前输入的角色查询条件.
 /**
 * 打开为用户分配角色的对话框,调用Server端方法，执行查询。如果该用于已经拥有某角色，则使checkbox状态为checked.
 * @param userId 被分配角色的用户ID
 * @param pageNo 页码，从1开始
 * @param pageSize 页容量
 * @param 查询条件，如果为null,则表示返回所有角色.
 */
 function openUserRolesDialog(pageNo, pageSize, userId, roleName) {
   if(roleName == null) {
     queryParam = "";
   } else {
     queryParam = roleName;
   }
   currentUserId = userId;
   //DWR方式调用后台函数
   roleAction.getRolesOfUser(userId, pageNo, pageSize, roleName, renderRolesTable);
   
   init();//初始化对话框
   inited = true;//修改初始化标记
   YAHOO.roles.container.Roles.show();  
 }
 /**
 * 初始化Yahoo UI对话框.
 */
  function init() {	
    if(inited) {
      return;//如果已经初始化，则不必再次初始化
    }
    
    YAHOO.roles.container.Roles = new YAHOO.widget.Dialog("Roles", {visible:false,
                                                                    modal:true,
                                                                    shadow:false,
                                                                    close: true,
                                                                    fixedcenter : true ,
                                                                    width:"500px" } );
	YAHOO.roles.container.Roles.render();
	YAHOO.roles.container.manager = new YAHOO.widget.OverlayManager();
	YAHOO.roles.container.manager.register([YAHOO.roles.container.Roles]);
	YAHOO.roles.container.Roles.beforeHideEvent.fire = clear;//对话框关闭之前的事件。
	//YAHOO.roles.container.Roles.beforeShowEvent.fire = clear;
 }
/**
 * 返回HTML格式的表格投，包括查询表单和保存、取消按钮.
 */
 function tableHeader() {
 var t = {roleName:"<fmt:message key='security.role.name'/>", 
          roleDescn:"<fmt:message key='security.role.descn'/>",
          select:"<fmt:message key='global.select'/>"
          };
  var data = {text:t};
  //用jsTemplate提供的API解析模板
  var result = 
    TrimPath.processDOMTemplate("userRolesHeaderTemplate", data, null, null);
  
  return result;
}

 /**
 * 渲染对话框中角色表格，包括分页用的paginatior.renderRolesTable函数被roleAction.getRolesOfUser方法以
 * callback的方式调用.
 * @param page 由DWR传来的符合JSON格式的Page对象，详见com.systop.common.dao.support.Page
 * 
 */
 function renderRolesTable(page) {
   //var roles = page.data;
   var htmlText = tableHeader();
    //用jsTemplate提供的API解析模板（另一种方式）
   
   var result = 
    htmlText = htmlText + TrimPath.processDOMTemplate("userRolesBody", page, null, null);
  $("tableSpace").innerHTML = htmlText;
  resetPaginator(page);  
}

function resetPaginator(page) {
  var pageInfo = {//分页信息，用于构建paginator
        pageNo    :page.currentPageNo,  //页码，从1开始
        totalCount:page.totalCount,
        pageSize  :<fmt:message key='default.pagesize'/>,//页容量
        data      :page.data,
        queryArgs :{roleName: $F('roleName'), userId: currentUserId}       
    };
    var paginator = dojo.widget.byId('paginator');
    paginator.setParams(pageInfo);  
}

dojo.addOnLoad( function() {
      var paginator = dojo.widget.byId('paginator');
      paginator.pagingFunction = pagingFunction;
      paginator.refreshTable = function(){};
  }
);
function pagingFunction(args) {
  
  roleAction.getRolesOfUser(args.queryArgs.userId, 
        args.pageNo, args.pageSize, args.queryArgs.roleName, 
          function(page){renderRolesTable(page);});
  
}
/**
* 选中一个角色执行的事件，调用DWR后台的selectRole方法.
* @param checkbox 选择框本身
* @param userID 当前被分配角色的用户ID
*/
function onSelectRole(checkbox, userId) {
  roleAction.selectRole(checkbox.value, userId, checkbox.checked);
}
/**
* 保存用户的角色的事件
* @param userId 当前被分配角色的用户ID
*/
function saveUserRoles(userId) {
  roleAction.saveUserRoles(userId, onSave);
}
/**
* 保存完毕后，显示提示信息.
* @param result roleAction.saveUserRoles函数的返回值
*/
function onSave(result) {

  if(result) {
    initConfirmDialog("<fmt:message key='global.success.info'/>");s=true;
  } else {
    initConfirmDialog("<fmt:message key='global.failed.info'/>");
  }
  YAHOO.roles.container.ConfirmDialog.show();
  
}

/**
* 取消分配角色
* @param userId 当前被分配角色的用户ID
*/
function cancel(userId) {
  YAHOO.roles.container.Roles.hide();
}
/**
* 打开或关闭对话框的时候，执行清空事件.
*/
function clear() {
  roleAction.cancelSaveUserRoles(currentUserId);
  //currentUserId = null;
  queryParam = null;
}
/**
* 弹出一个对话框，用于显示简单的信息
* @param text 需要显示的信息
*/
function initConfirmDialog(text) {
  
  var handleYes = function() {
    YAHOO.roles.container.ConfirmDialog.hide();
  }
  var handleNo = function() {
    YAHOO.roles.container.ConfirmDialog.hide();

    YAHOO.roles.container.Roles.hide();
  }
  var dialogArgs = { width: "300px",
			fixedcenter: true,
			visible: false,
			draggable: true,
			close: false,
			text: text,
			constraintoviewport: true,
			buttons: [ { text:"<fmt:message key='global.yes'/>", handler:handleYes, isDefault:true },
		               { text:"<fmt:message key='global.no'/>",  handler:handleNo } ]
		};
    if(YAHOO.roles.container.ConfirmDialog != null) {
      YAHOO.roles.container.ConfirmDialog.destroy();
      YAHOO.roles.container.ConfirmDialog = null;
    }
    YAHOO.roles.container.ConfirmDialog =
       new YAHOO.widget.SimpleDialog("ConfirmDialog", 
									dialogArgs);

    YAHOO.roles.container.ConfirmDialog.setHeader("<fmt:message key='global.info'/>");
    YAHOO.roles.container.ConfirmDialog.render(document.body);

  
}
</script>
<div id="Roles" style="visibility:hidden">
<div class="hd" style="font:12px"><fmt:message key="security.user.assignRoles"/></div>
<div class="bd" id="rolesBody" style="font:12px">
  <div id="querySpace">
  <table width='98%'><tr><td><fmt:message key='security.role.name'/>:
    <input type='text' id='roleName' style='border:1px solid #BBB'/>&nbsp;
    <input type='button' value='<fmt:message key='global.query'/>' 
    onclick='openUserRolesDialog(1,<fmt:message key="default.pagesize"/>, currentUserId, $F(roleName))'/>
   </td><td align='left'>
   <input type='button' value='<fmt:message key='global.save'/>'
   onclick='saveUserRoles(currentUserId)'/>&nbsp;
    <input type='button' value='<fmt:message key='global.cancel'/>' 
    onclick='cancel(currentUserId)'/>
    </td></tr></table>
  </div>
  <div id="tableSpace"></div>
</div>
<div class="ft" style="font:12px;" id="rolesFoot" align="center">
<div dojoType="Paginator" widgetId="paginator" width="100%" showMessage="true"
	 messageTemplate="<fmt:message key='global.paginator.message'/>" showFastStep="true" maxFastStep="3" 
	 messageWidth="300"/>
</div>
</div></div>
 <jsp:include page="userRoleTemplates.html"/>
</body>
</html>
