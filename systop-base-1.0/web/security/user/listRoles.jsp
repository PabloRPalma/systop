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
	src='<c:url value="/dwr/interface/permAction.js"/>'> </script>
<script type="text/javascript" src="<c:url value='/js/dojo/dojo.js'/>"></script>
<s:action id="permAction" name="permAction" namespace="/security/user" executeResult="false"/>
<%@include file="/common/yahooUi.jsp"%>

<title><fmt:message key="security.role.title" /></title>
<script type="text/javascript">
dojo.require("dojo.widget.*");
dojo.require("dojo.ex.Paginator");
</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<tr height="5%">
		<td height="20" bgcolor="#cecece"
			style="padding-top:2px; padding-left:10px">
			<img src="<c:url value='/images/icons/add_3.gif'/>" width="14" height="14">
		       <a href="<c:url value='/security/user/editRole.jsp' />">
		       <fmt:message	key="global.new" /></a> 
		    <img src="<c:url value='/images/icons/del.gif'/>" width="14" height="14"> 
		       <a href="#"><font onclick="onDelete()" style="font:12.5px">
		       <fmt:message	key="global.remove" /></font></a>
		       
		</td>
	</tr>
	<tr height="15%">
	<td align="center">
	<fieldset class="fieldsetStyle">
    <legend><fmt:message key="global.query"/></legend>
    <table width="95%" align="left">
      <s:form action="queryRoles" theme="simple" name="queryRoles">
        <tr>
        <td width="60"><fmt:message key="security.role.name"/>:</td>
        <td width="150" align="left"><s:textfield name="model.name" theme="simple"/></td>
        
        <td align="left"><a href="#" onclick="javascript:$('queryRoles').submit()">
        <img src="<c:url value='/images/icons/search_1.gif'/>" border="0"></a>
        </td>
        </tr>
      </s:form>
    </table>
    </fieldset>
	</td>
	</tr>
	<s:form action="removeRoles" name="removeRoles" id="removeRoles" theme="simple">
	</s:form>
	<s:form action="authRoles" name="authRoles" id="authRoles" theme="simple">
	</s:form>
	<s:form action="listRoles" name="listRoles" id="listRoles" theme="simple">
	</s:form>
	<tr height="80%">
		<td valign="top" align="center">
		
		<ec:table items="availableItems" var="role"
			action="#"
			rowsDisplayed="25" autoIncludeParameters="true" style="width:95%">
			<ec:exportXls fileName="AllRoles.xls" tooltip="global.export" />
			<ec:row>
				<ec:column property="changed" title="global.select" sortable="false"
					viewsAllowed="html" width="5%"><div align="center">
					<input type="checkbox" name="selectedItems" id="selectedItems" value="${role.id}" style='height:12px'>
					</div>
				</ec:column>
				<ec:column property="rvclowcount" cell="rowCount"
					title="global.serialno" sortable="false" width="5%" style="align:center"/>
				
				<ec:column property="name" title="security.role.name" width="10%"></ec:column>
				
				<ec:column property="descn" title="security.role.descn"
					sortable="false">
					
				</ec:column>
				<ec:column property="role_perm" title="security.perm"
					sortable="false" style="width:5%">
					<div style="cursor:pointer" align="center"><img
						src="<c:url value='/images/icons/authority.gif'/>"
						onclick="openRolePermsDialog(1, <fmt:message key='default.pagesize'/>, '${role.id}', null,null,null)">
					</div>
				</ec:column>
				<ec:column property="edit" title="global.edit" sortable="false" width="5%">
					<div onclick="onEdit('${role.id}')" style="cursor:hand" align="center">
					<img src="<c:url value='/images/icons/modify.gif'/>"></div>
				</ec:column>
			</ec:row>
		</ec:table></td>
	</tr>
	<s:form theme="simple" action="prepareEditRole" id="prepareEditRole" name="prepareEditRole">
		<input type="hidden" value="" name="model.id" id="prepareEditRole.id" />
	</s:form>
</table>
<script>
function onEdit(id) {
 var frm = $('prepareEditRole');
 $("prepareEditRole.id").value = id;
 frm.submit();
}

function onDelete() {
  if(confirm('<fmt:message key="security.role.delete.warn"/>')) {
    $('ec').action = $('removeRoles').action;
    $('ec').submit();
  }
}

</script>

<script>
 YAHOO.namespace("perms.container");
 var inited = false;//权限选择对话框初始化标记
 var currentRoleId; //暂存当前选择的角色Id
 var queryParam_name;    //暂存当前输入的权限查询条件.
 var queryParam_operation;    //暂存当前输入的权限查询条件.
 var queryParam_status;    //暂存当前输入的权限查询条件.
 /**
 * 打开为角色分配权限的对话框,调用Server端方法，执行查询。如果该角色已经拥有某权限，则使checkbox状态为checked.
 * @param roleId 被分配权限的角色ID
 * @param pageNo 页码，从1开始
 * @param pageSize 页容量
 * @param 查询条件，如果为null,则表示返回所有权限.
 */
 function openRolePermsDialog(pageNo, pageSize, roleId, permName, permOperation, permStatus) {
   queryParam_name = (permName == null) ? "" : permName;
   queryParam_operation = (permOperation == null) ? "" : permOperation;
   queryParam_status = (permStatus == null) ? "" : permStatus;
   
   currentRoleId = roleId;
   //DWR方式调用后台函数
   permAction.getPermsOfRole(roleId, pageNo, pageSize, 
       permName, permOperation, permStatus, renderPermsTable);
   
   init();//初始化对话框
   inited = true;//修改初始化标记
   YAHOO.perms.container.Perms.show();  
 }
 /**
 * 初始化Yahoo UI对话框.
 */
  function init() {	
    if(inited) {
      return;//如果已经初始化，则不必再次初始化
    }
    
    YAHOO.perms.container.Perms = new YAHOO.widget.Dialog("Perms", {visible:false,
                                                                    modal:true,
                                                                    shadow:false,
                                                                    close: true,
                                                                    fixedcenter : true ,
                                                                    width:"560px" } );
	YAHOO.perms.container.Perms.render();
	YAHOO.perms.container.manager = new YAHOO.widget.OverlayManager();
	YAHOO.perms.container.manager.register([YAHOO.perms.container.Perms]);
	YAHOO.perms.container.Perms.beforeHideEvent.fire = clear;//对话框关闭之前的事件。
	//YAHOO.perms.container.Perms.beforeShowEvent.fire = clear;
 }
/**
 * 返回HTML格式的表格头，包括查询表单和保存、取消按钮.
 */
 function tableHeader() {
 var t = {permName:"<fmt:message key='security.perm.name'/>", 
          permDescn:"<fmt:message key='security.perm.descn'/>",
          select:"<fmt:message key='global.select'/>"};
          
  var data = {text:t};
  //用jsTemplate提供的API解析模板
  var result = 
    TrimPath.processDOMTemplate("rolePermsHeaderTemplate", data, null, null);
  
  return result;
}

 /**
 * 渲染对话框中权限表格，包括分页用的paginatior.renderPermsTable函数被permAction.getPermsOfRole方法以
 * callback的方式调用.
 * @param page 由DWR传来的符合JSON格式的Page对象，详见com.systop.common.dao.support.Page
 * 
 */

 function renderPermsTable(page) {
   maxPage = page.totalPageCount;

   var htmlText = tableHeader();
   htmlText = htmlText +  TrimPath.processDOMTemplate("rolePermsBody", page, null, null);
  $("tableSpace").innerHTML =  htmlText;
   resetPaginator(page);  
}

function resetPaginator(page) {
  var pageInfo = {//分页信息，用于构建paginator
        pageNo    :page.currentPageNo,  //页码，从1开始
        totalCount:page.totalCount,
        pageSize  :<fmt:message key='default.pagesize'/>,//页容量
        data      :page.data,
        queryArgs :{
            permName: $F('permName'), 
            permOperation: $F('permOperation'),
            permStatus: $F('permStatus'),
            roleId: currentRoleId}       
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
  permAction.getPermsOfRole(args.queryArgs.roleId, args.pageNo, args.pageSize, 
       args.queryArgs.permName, args.queryArgs.ermOperation, args.queryArgs.permStatus, 
       function(page){renderPermsTable(page);});
}
/**
* 选中一个权限执行的事件，调用DWR后台的selectRole方法.
* @param checkbox 选择框本身
* @param roleID 当前被分配权限的角色ID
*/
function onSelectPerm(checkbox, roleId) {
  permAction.selectPerm(checkbox.value, roleId, checkbox.checked);
}
/**
* 保存角色的权限的事件
* @param roleId 当前被分配权限的角色ID
*/
function saveRolePerms(roleId) {
  permAction.saveRolePerms(roleId, onSave);
}
/**
* 保存完毕后，显示提示信息.
* @param result permAction.saveRolePerms函数的返回值
*/
function onSave(result) {

  if(result) {
    initConfirmDialog("<fmt:message key='global.success.info'/>");s=true;
  } else {
    initConfirmDialog("<fmt:message key='global.failed.info'/>");
  }
  YAHOO.perms.container.ConfirmDialog.show();
  
}

/**
* 取消分配权限
* @param roleId 当前被分配权限的角色ID
*/
function cancel(roleId) {
  YAHOO.perms.container.Perms.hide();
}
/**
* 打开或关闭对话框的时候，执行清空事件.
*/
function clear() {
  permAction.cancelSaveRolePerms(currentRoleId);
  //currentRoleId = null;
  queryParam_name = null;
  queryParam_operation = null;
  queryParam_status = null;
}
/**
* 弹出一个对话框，用于显示简单的信息
* @param text 需要显示的信息
*/
function initConfirmDialog(text) {
  
  var handleYes = function() {
    YAHOO.perms.container.ConfirmDialog.hide();
  }
  var handleNo = function() {
    YAHOO.perms.container.ConfirmDialog.hide();

    YAHOO.perms.container.Perms.hide();
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
    if(YAHOO.perms.container.ConfirmDialog != null) {
      YAHOO.perms.container.ConfirmDialog.destroy();
      YAHOO.perms.container.ConfirmDialog = null;
    }
    YAHOO.perms.container.ConfirmDialog =
       new YAHOO.widget.SimpleDialog("ConfirmDialog", 
									dialogArgs);

    YAHOO.perms.container.ConfirmDialog.setHeader("<fmt:message key='global.info'/>");
    YAHOO.perms.container.ConfirmDialog.render(document.body);

  
}
</script>
<div id="Perms" style="visibility:hidden;width:600px;">
     <div class="hd" style="font:12px"><fmt:message key="security.role.assignPerms"/></div>
     <div class="bd" id="permsBody" style="font:12px">
          <div id="querySpace">
              <table border="0" cellpadding="5" width="100%" cellspacing="3">
    				<tr>
    					<td width="10%" nowrap="nowrap"><fmt:message key='security.perm.name'/>:
    					</td>
    					<td width="10%" nowrap="nowrap">
    						<input type='text' id='permName' style='border:1px solid #BBB'/>
    					</td>
    					<td width="10%" nowrap="nowrap"><fmt:message key='security.perm.operation'/>:</td>
    					<td width="10%" nowrap="nowrap"><systop:catalogSelector catalog="perm_operation" name="permOperation" defaultLabel="global.all" defaultValue=""/></td>
    					<td width="10%" nowrap="nowrap"><fmt:message key='security.perm.status'/>:</td>
    					<td width="10%" nowrap="nowrap"><systop:catalogSelector catalog="perm_status" name="permStatus" defaultLabel="global.all" defaultValue=""/></td>
    					<td align="right">
    						<input type='button' value='<fmt:message key="global.query"/>' 
    onclick='openRolePermsDialog(1,<fmt:message key="default.pagesize"/>,currentRoleId, $F(permName),$F(permOperation), $F(permStatus))'/>
    						<input type='button' value='<fmt:message key="global.save"/>'
   onclick='saveRolePerms(currentRoleId)'/>
    						<input type='button' value='<fmt:message key="global.cancel"/>' 
    onclick='cancel(currentRoleId)'/>&nbsp;&nbsp;
    					</td>
    				</tr>
    			</table>
    		
         </div>
         <div id="tableSpace"/>
     </div>
     <div class="ft" style="font:12px;" id="permsFoot"></div>
</div>
<div align="center" style="padding-bottom:3px;">
        <div dojoType="Paginator" widgetId="paginator" width="98%"  showMessage="true"
	         messageTemplate="<fmt:message key='global.paginator.message'/>"
	         showFastStep="true" maxFastStep="3" messageWidth="350"></div>
</div>


 <jsp:include page="rolePermTemplates.html"/></body>
</html>
