<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>信息员管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<script type="text/javascript">
function removeSupervisor(sID){
	if(confirm("确实要删除该信息员的信息吗？")){
		location.href = "remove.do?model.id="+sID;
	}
}
</script>
</head>

<body>
<div class="x-panel">
<div class="x-panel-header">信息员管理</div>
<div class="x-toolbar">
<table width="100%" border="0">
	<tr>
		<s:form id="superVisorForm" action="index.do" method="post" target="main">
			<td width="42" align="left">姓名：</td>
		  <td width="33"><s:textfield name="model.name"
				cssStyle="width:60px" /></td>
			<td width="45" align="right">手机：</td>
		  <td width="33"><s:textfield name="model.mobile"
				cssStyle="width:60px" /></td>
			<td width="80" align="right">所属部门：</td>
			<td align="left" class="simple">			
				<div id="comboxWithTree"></div>
				<s:hidden name="model.dept.id" id="deptId"></s:hidden>
		  </td>
			<td width="80" align="right">监管区域：</td>
		  <td width="50"><s:textfield name="model.superviseRegion" 
				cssStyle="width:60px" /></td>
		  <td width="60" align="right">负责人：</td>
		  <td width="45" valign="top">
		  	是 <input type="radio" value="1" name="isLeader" id="model.isLeader" style="border: 0"/>
          </td>
          <td width="45" valign="top" >
          	否<input type="radio" value="0" name="isLeader" id="model.isLeader" style="border: 0"/>
          </td>
			<td width="72" align="left">
				<input type="submit" value="查询" class="button"/>
		  </td>
		</s:form>
		<td><a href="editNew.do">添加</a></td>
		<td><span class="ytb-sep"></span></td>
		<td><a href="#" onClick="NumWindow.show()">导出手机号</a></td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="380px"
	minHeight="380"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="35" property="_No" title="No." value="${GLOBALROWCOUNT}" style="text-align:center" />
		<ec:column width="70" property="name" title="姓名">
			<c:if test="${item.isLeader == '1'}"><font color="red" title="红色代表该监管员为负责人">${item.name}</font></c:if>
		</ec:column>
		<ec:column width="35" property="gender" title="性别" style="text-align:center"/>
		<ec:column width="55" property="code" title="编号" style="text-align:center"/>
		<ec:column width="95" property="mobile" title="手机号码" style="text-align:center"/>
		<ec:column width="120" property="dept.name" title="所属部门"/>
		<ec:column width="250" property="superviseRegion" title="监管区域" />
		<ec:column width="120" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a>|
			<a href="view.do?model.id=${item.id}" target="main">查看</a>|
			<c:if test="${not empty item.fsCase}"><font color="silver" title="该信息员已举报过案件，无法删除">删除</font></c:if>
			<c:if test="${empty item.fsCase}"><a href="#" onClick="removeSupervisor(${item.id})">删除</a></c:if>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
<table width="100%">
	<tr>
		<td align="left"><s:form action="importData"
			namespace="/supervisor" theme="simple" method="POST"
			enctype="multipart/form-data">
			<fieldset style="margin: 10px;"><legend>监管员信息导入</legend>
			<table align="left">
				<tr>
					<td>批量导入数据：</td>
					<td><s:file name="data" size="30" /></td>
					<td>&nbsp;&nbsp;<s:submit value="数据导入" cssClass="button"
						action="importData" onclick="return confirm('您是否导入监管员信息？')" />
					</td>
					<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="${ctx}/uploadFiles/template/import/SupervisorInfo.xls"><font
						color="blue">模板下载</font></a></td>				
				</tr>
				<tr>
					<s:if test="errorInfo.size() > 0">
						<td align="left"><font color="red">系统提示信息：</font></td>
						<td align="left" colspan="3"><s:iterator value="errorInfo">
							<font color="red"><s:property /></font>
							<br />
						</s:iterator></td>
					</s:if>
				</tr>
			</table>
			</fieldset>
		</s:form></td>
	</tr>
</table>	
</div>
<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var dtree = new DeptTree({
		url : '/admin/dept/deptTree.do',
		parent : '<stc:loginUserDept showPath="false" propertyName="id" showTopDept="true"></stc:loginUserDept>',
		initValue : '${model.dept.name}',
		el : 'comboxWithTree',
		innerTree :'inner-tree',
		onclick : function(nodeId) {
		  Ext.get('deptId').dom.value = nodeId;
		}
	});
	dtree.init();	
});
</script>
<!-- 查看手机号码 -->
<div id="numWindow" class="x-hidden">
<div class="x-window-header">手机号码</div>
<div class="x-window-body">
	<table width="550" cellspacing="6">
	  <tr>
	    <td align="right"><font color="green">有号码成员数：</font></td>
		<td align="left" colspan="3">
		  ${hasNum}&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">无号码成员数：</font>${noNum}
	    </td>
	  </tr>
	  <tr>
	    <td height="5" colspan="4"></td>
	  </tr>
	  <tr>
	    <td width="90px" align="right"><font color="green">成员手机号码：</font></td>
	    <td width="320px">${mobileNums}</td>
        <td colspan="2">
        </td>
	  </tr>
	</table>
	
 </div>
</div>
<!-- 查看手机号码 -->
<script type="text/javascript">
  var NumWindow = new Ext.Window({
      el: 'numWindow',
      width: 550,
      height: 250,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'false',
      buttons:[
        {text:'复制',
        	handler:function(){
        	copyNum('${mobileNums}');
        	}
      	},
        {text:'关闭',
			handler:function(){
			NumWindow.hide();
			}
		}]
  });
</script>
<script type="text/javascript">
  function copyNum(copyText) {
	var reg = new RegExp(/\<br\/>/ig);
	copyText = copyText.replace(reg, "");
    if (window.clipboardData) {
        window.clipboardData.setData("Text", copyText);
    } 
  }
</script>
</body>
</html>