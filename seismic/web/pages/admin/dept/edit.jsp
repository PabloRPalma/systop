<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑部门</title>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<style type="text/css">
.x-form-field-wrap .x-form-trigger{
    display: none;
}
.x-form-field-wrap .x-form-text,.x-form-field,.x-combo-noedit,.x-form-focus {
   border:1px #cecece solid;
   margin: 0 0 0 0;
   padding: 0 0 0 0;
   background: #ffffff url(${ctx}/images/exticons/drop-between.gif) no-repeat right;
}
.x-combo-list {
    border:1px solid #cecece;
    background:#ffffff;
    zoom:1;
    overflow:hidden;
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
                             <td style="text-align:left;"><s:textfield name="model.name" id="deptName" theme="simple" size="30"/>*</td>
                        </tr>
                        <tr>
                             <td>上级部门：</td>
                             <td style="text-align:left;">
		                         <div id="comboxWithTree"></div>
		                         <s:hidden id="parentId" name="model.parentDept.id"/>
                             </td>
                        </tr>
                        
                        <tr>
                             <td>部门描述：</td>
                             <td style="text-align:left;">
                             <s:textarea name="model.descn" id="descn" theme="simple" rows="7" cols="32"
                             cssStyle="border:1px #cbcbcb solid;z-index:auto;"/></td>
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

<script>
   var initValue = '';
   <s:if test="model.parentDept != null">
     initValue = '${model.parentDept.name}';
   </s:if>
</script>

<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
nodeIdCallback = function(nodeId) {
  Ext.get('parentId').dom.value = nodeId;
};
</script>
</body>
</html>