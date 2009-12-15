<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑部门</title>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<style type="text/css">
input[type='text'] {
    width : 200px;
}
</style>
</head>

<body>
<div class="x-panel">
    	<div class="x-panel-header">部门管理</div>
    	<div class="x-toolbar">
	    	<table width="100%">	      
		      <tr>	         
		         <td align="right">
			        <table>
			        <tr>
			        <td> <a href="${ctx}/admin/dept/index.do">
			             <img src="${ctx}/images/icons/home_1.gif" border="0"/> 部门管理首页</a>
			        </td>
			        <td><span class="ytb-sep"></span></td>
			        <td>
			         <img src="${ctx}/images/icons/users_1.gif" border="0"/>编辑部门信息</td>     
			        </tr>
			        </table>
			     </td>
		      </tr>
	       </table>
    	</div>
    	<div class="x-panel-body">
	    	<div>
				<%@ include file="/common/messages.jsp"%>
			</div>
			
			<s:form action="save" theme="simple" validate="true" method="POST">
		   		<s:hidden name="model.id"/>
		   		<s:hidden name="model.serialNo"/>
		   		<fieldset style="margin:10px;">
			    <legend>部门基本信息</legend>
				<table width="100%">
                        <tr>
                             <td>部门名称：</td>
                             <td style="text-align:left;"><s:textfield name="model.name" id="deptName" cssStyle="width:200px;"/>*</td>
                        </tr>
                        <tr>
                             <td>类型：</td>
                             <td style="text-align:left;">
                             <s:select list="#{'1':'单位','0':'部门'}" name="model.type" cssClass="required" cssStyle="width:200px;"></s:select>
                             *</td>
                        </tr>
                        <tr>
                             <td>上级部门：</td>
                             <td style="text-align:left;">
		                         <div id="comboxWithTree" style="width:200px;"></div>
		                         <s:hidden id="parentId" name="model.parentDept.id"/>
                             </td>
                        </tr>
                        
                        <tr>
                             <td>部门描述：</td>
                             <td style="text-align:left;">
                             <s:textarea name="model.descn" id="descn" theme="simple" rows="7" cols="32"
                             cssStyle="border:1px #cbcbcb solid;z-index:auto; width:200px;"/></td>
                        </tr>
                        
                </table>
                </fieldset>
                <table width="100%" style="margin-bottom:10px;">
                       <tr>
                        <td style="text-align:center;">
                            <s:submit value="保存" cssClass="button"></s:submit>
                            <s:reset value="重置" cssClass="button"></s:reset></td>
                        </tr>
                </table>
                
                </s:form>
                
    	</div>
  </div>
<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var dtree = new DeptTree({
		url : '/admin/dept/deptTree.do',
		parent : '<stc:loginUserDept showPath="false" propertyName="id" showTopDept="true"></stc:loginUserDept>',
		initValue : '${model.parentDept.name}',
		el : 'comboxWithTree',
		innerTree :'inner-tree',
		onclick : function(nodeId) {
		  Ext.get('parentId').dom.value = nodeId;
		}
	});
	dtree.init();	
	
});
</script>

</body>
</html>