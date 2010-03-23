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
<script type='text/javascript' src='<c:url value="/dwr/interface/resourceAction.js"/>'> </script>
<script type="text/javascript" src="<c:url value='/js/dojo/dojo.js'/>"></script>

<%@include file="/common/yahooUi.jsp"%>
<title><fmt:message key="security.perm.title" /></title>
</head>
<body>	
<script type="text/javascript">
dojo.require("dojo.widget.*");
dojo.require("dojo.ex.Paginator");
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	height="100%">

	<tr height="5%">
		<td height="20" bgcolor="#cecece"
			style="padding-top:2px; padding-left:10px"><img
			src="<c:url value='/images/icons/add_3.gif'/>" width="14" height="14">
		<a href="<c:url value='/security/user/editPerm.jsp' />"> <fmt:message
			key="global.new" /></a> <img
			src="<c:url value='/images/icons/del.gif'/>" width="14" height="14">
		<a href="#"><font onclick="onDelete()" style="font:12.5px">
		<fmt:message key="global.remove" /></font></a></td>
	</tr>
	<tr height="15%">
		<td align="center">
		<fieldset style="width:93%" class="fieldsetStyle"><legend><fmt:message
			key="global.query" /></legend>
		<table width="95%" align="left">
			<s:form action="queryPerms" theme="simple" name="queryPerms">
				<tr>
					<td width="70"><fmt:message key="security.perm.name" />:</td>
					<td width="150" align="left"><s:textfield name="model.name"
						theme="simple" /></td>
					<td width="60"><fmt:message key="security.perm.operation" />:</td>
					<td width="120" align="left"><systop:catalogSelector
						catalog="perm_operation" name="model.operation"
						defaultLabel="global.all" defaultValue="" /></td>
					<td width="60"><fmt:message key="security.perm.status" />:</td>
					<td width="70" align="left"><systop:catalogSelector
						catalog="perm_status" name="model.status"
						defaultLabel="global.all" defaultValue="" /></td>
					<td align="left"><a href="#"
						onclick="javascript:$(queryPerms).submit()"> <img
						src="<c:url value='/images/icons/search_1.gif'/>" border="0"></a>
					</td>
				</tr>
			</s:form>
		</table>
		</fieldset>
		</td>
	</tr>
	<tr height="80%">
		<td valign="top" align="center"><ec:table items="availableItems"
			var="item" action="#" rowsDisplayed="25" autoIncludeParameters="true"
			style="width:95%">
			<ec:exportXls fileName="AllPermissions.xls" tooltip="global.export" />
			<ec:row>
				<ec:column property="toRemove" title="global.select"
					sortable="false" viewsAllowed="html" width="30">
					<input type="checkbox" name="selectedItems" value="${item.id}"
						style='height:12px'>
				</ec:column>
				<ec:column property="rvclowcount" cell="rowCount"
					title="global.serialno" sortable="false" width="30" />
				<ec:column property="name" title="security.perm.name" width="200"></ec:column>
				<ec:column property="descn" title="security.perm.descn"></ec:column>
				<ec:column property="operation" title="security.perm.operation"
					alias="perm_operation"
					cell="com.systop.common.webapp.extremecomponents.CatalogCell"
					width="60"></ec:column>
				<ec:column property="status" alias="perm_status"
					title="security.perm.status"
					cell="com.systop.common.webapp.extremecomponents.CatalogCell"
					width="30"></ec:column>
				<ec:column property="perm_res" title="security.res" sortable="false"
					style="width:5%">
					<div style="cursor:pointer" align="center"><img
						src="<c:url value='/images/icons/resource.gif'/>"
						onclick="openPermResourcesDialog( 1, <fmt:message key='default.pagesize'/>,'${item.id}', null, null, '${item.operation}')">
					</div>
				</ec:column>
				<ec:column property="edit" title="global.edit" sortable="false"
					width="30">
					<div onclick="onEdit('${item.id}')" style="cursor:hand"
						align="center"><img
						src="<c:url value='/images/icons/modify.gif'/>"></div>
				</ec:column>
			</ec:row>
		</ec:table></td>
	</tr>
</table>
<s:form action="removePerms" name="removePerms" id="removePerms"
	theme="simple">
</s:form>
<s:form action="listPerms" name="listPerms" id="listPerms"
	theme="simple">
</s:form>
<s:form theme="simple" action="prepareEditPerm" id="prepareEditPerm"
	name="prepareEditPerm">
	<input type="hidden" value="" name="model.id" id="prepareEditPerm.id" />
</s:form>
<script>
function onEdit(id) {
 var frm = $('prepareEditPerm');
 $("prepareEditPerm.id").value = id;
 frm.submit();
}

function onDelete() {
  if(confirm('<fmt:message key="security.perm.delete.warn"/>')) {
    $('ec').action = $('removePerms').action;
    $('ec').submit();
  }
}

</script>
<!-- 用于存放jsTemplate模板 -->

<script>
 YAHOO.namespace("resources.container");
 var inited = false;//资源选择对话框初始化标记
 var currentPermId; //暂存当前选择的权限Id
 var queryParam_name;    //暂存当前输入的资源查询条件.
 var queryParam_str;    //暂存当前输入的资源查询条件.
 var queryParam_type;    //暂存当前输入的资源查询条件.
 /**
 * 打开为权限分配资源的对话框,调用Server端方法，执行查询。如果该权限已经拥有某资源，则使checkbox状态为checked.
 * @param permId 被分配资源的权限ID
 * @param pageNo 页码，从1开始
 * @param pageSize 页容量
 * @param 查询条件，如果为null,则表示返回所有资源.
 */
 function openPermResourcesDialog(pageNo, pageSize, permId, resName, resString, resType) {
   queryParam_name = (resName == null) ? "" : resName;
   queryParam_str = (resString == null) ? "" : resString;
   queryParam_type = (resName == null) ? "" : resType;
   currentPermId = permId;
   //DWR方式调用后台函数
   resourceAction.getResourcesOfPerm(permId, pageNo, pageSize, 
     resName, resString, resType, renderResourcesTable);
   
   init();//初始化对话框
   
   YAHOO.resources.container.Resources.show();  
 }
 /**
 * 初始化Yahoo UI对话框.
 */
  function init() {	
    if(inited) {
      return;//如果已经初始化，则不必再次初始化
    }
    
    YAHOO.resources.container.Resources = new YAHOO.widget.Dialog("Resources", {visible:false,
                                                                    modal:true,
                                                                    shadow:false,
                                                                    close: true,
                                                                    fixedcenter : true ,
                                                                    width:"650px" } );
	YAHOO.resources.container.Resources.render();
	YAHOO.resources.container.manager = new YAHOO.widget.OverlayManager();
	YAHOO.resources.container.manager.register([YAHOO.resources.container.Resources]);
	YAHOO.resources.container.Resources.beforeHideEvent.fire = clear;//对话框关闭之前的事件。
	inited = true;//修改初始化标记
	//YAHOO.resources.container.Resources.beforeShowEvent.fire = clear;
 }
/**
 * 返回HTML格式的表格投，包括查询表单和保存、取消按钮.
 */
 function tableHeader() {
 
 var t = {resName:"<fmt:message key='security.res.name'/>", 
          resString:"<fmt:message key='security.res.str'/>",
          select:"<fmt:message key='global.select'/>"
          };
  var data = {text:t};
  //用jsTemplate提供的API解析模板
  var result = 
    TrimPath.processDOMTemplate("permResourcesHeaderTemplate", data, null, null);
  return result;
}

 /**
 * 渲染对话框中资源表格，包括分页用的paginatior.renderResourcesTable函数被resourceAction.getResourcesOfPerm方法以
 * callback的方式调用.
 * @param page 由DWR传来的符合JSON格式的Page对象，详见com.systop.common.dao.support.Page
 * 
 */
 function renderResourcesTable(page) {
   maxPage = page.totalPageCount;

   //var resources = page.data;
   var htmlText = tableHeader();
    //用jsTemplate提供的API解析模板（另一种方式）
   htmlText = htmlText + TrimPath.processDOMTemplate("permResourcesBody", page, null, null);
   
  $("tableSpace").innerHTML = htmlText;
  
  
  var pageInfo = {//分页信息，用于构建paginator
        pageNo          :page.currentPageNo,  //页码，从1开始
        totalCount      :page.totalCount, //总页数
        data            :page.data,
        pageSize        :<fmt:message key='default.pagesize'/>,//页容量
        sortField       :"", sortDir: "",
        queryArgs       :{
          permId:    currentPermId,
          resName:   queryParam_name,
          resString: queryParam_str,
          resType:   queryParam_type}//查询参数，分页的时候同时应该执行查询
        
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
  resourceAction.getResourcesOfPerm(args.queryArgs.permId, args.pageNo, args.pageSize, 
       args.queryArgs.resName, args.queryArgs.resString, args.queryArgs.resType, 
       function(page){renderResourcesTable(page);});
}
/**
* 选中一个资源执行的事件，调用DWR后台的selectRes方法.
* @param checkbox 选择框本身
* @param permID 当前被分配资源的权限ID
*/
function onSelectRes(checkbox, permId) {
  resourceAction.selectRes(checkbox.value, permId, checkbox.checked);
}
/**
* 保存权限的资源的事件
* @param permId 当前被分配资源的权限ID
*/
function savePermResources(permId) {
  resourceAction.savePermResources(permId, onSave);
}
/**
* 保存完毕后，显示提示信息.
* @param result resourceAction.savePermResources函数的返回值
*/
function onSave(result) {

  if(result) {
    initConfirmDialog("<fmt:message key='global.success.info'/>");s=true;
  } else {
    initConfirmDialog("<fmt:message key='global.failed.info'/>");
  }
  YAHOO.resources.container.ConfirmDialog.show();
  
}

/**
* 取消分配资源
* @param permId 当前被分配资源的权限ID
*/
function cancel(permId) {
  YAHOO.resources.container.Resources.hide();
}
/**
* 打开或关闭对话框的时候，执行清空事件.
*/
function clear() {
  resourceAction.cancelSavePermResources(currentPermId);
  //currentPermId = null;
  queryParam_name = null;
  queryParam_str = null;
  queryParam_type = null;
}
/**
* 弹出一个对话框，用于显示简单的信息
* @param text 需要显示的信息
*/
function initConfirmDialog(text) {
  
  var handleYes = function() {
    YAHOO.resources.container.ConfirmDialog.hide();
  }
  var handleNo = function() {
    YAHOO.resources.container.ConfirmDialog.hide();

    YAHOO.resources.container.Resources.hide();
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
    if(YAHOO.resources.container.ConfirmDialog != null) {
      YAHOO.resources.container.ConfirmDialog.destroy();
      YAHOO.resources.container.ConfirmDialog = null;
    }
    YAHOO.resources.container.ConfirmDialog =
       new YAHOO.widget.SimpleDialog("ConfirmDialog", 
									dialogArgs);

    YAHOO.resources.container.ConfirmDialog.setHeader("<fmt:message key='global.info'/>");
    YAHOO.resources.container.ConfirmDialog.render(document.body);

  
}
</script>
<div id="Resources" style="visibility:hidden" style="width:550px">
    <div class="hd" style="font:12px"><fmt:message key="security.perm.assignResources" /></div>
    <div class="bd" id="resourcesBody" style="font:12px">
        <div id="querySpace">
            <table border="0" cellpadding="5" width="100%" cellspacing="3">
				<tr>
					<td width="12%" nowrap="nowrap"><fmt:message
						key='security.res.name' />:&nbsp;</td>
					<td width="20%"><input type='text' id='resName'
						style='border: 1px solid rgb(187, 187, 187); width: 80pt' /></td>
					<td width="12%" nowrap="nowrap"><fmt:message
						key='security.res.type' />:&nbsp;</td>
					<td width="12%"><systop:catalogSelector catalog="res_type"
						name="resType" defaultLabel="global.all" defaultValue=""></systop:catalogSelector>
					</div>
					</td>
					<td align="right" nowrap="nowrap"><input type='button'
						value='<fmt:message key='global.query'/>'
						onclick='openPermResourcesDialog(1, <fmt:message key="default.pagesize"/>, currentPermId, $F(resName), $F(resString), $F(resType))' />
					<input type='button' value='<fmt:message key='global.save'/>'
						onclick='savePermResources(currentPermId)' /> <input type='button'
						value='<fmt:message key='global.cancel'/>'
						onclick='cancel(currentPermId)' />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td nowrap="nowrap"><fmt:message key='security.res.str' />:&nbsp;</td>
					<td colspan="10"><input type='text' id='resString'
						style='border:1px solid #BBB; width: 400px' /></td>
				</tr>
			</table>
        </div>
        <div id="tableSpace" />
        
    </div>
    <div class="ft" style="font:12px;" id="resourcesFoot"></div>
</div>
<div align="center" style="padding-bottom:3px;">
    <div dojoType="Paginator" widgetId="paginator" width="100%" showMessage="true"
	 messageTemplate="<fmt:message key='global.paginator.message'/>" showFastStep="true" maxFastStep="3" 
	 messageWidth="350"/>
</div>
	         
<jsp:include page="permResTemplates.html" />
</body>
</html>
