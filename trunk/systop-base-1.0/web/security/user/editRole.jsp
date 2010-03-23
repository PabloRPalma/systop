<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><fmt:message key= "security.role.edit"/></title>
<%@include file="/common/meta.jsp" %>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/interface/roleAction.js"/>'> </script>
<s:head/>
</head>
<body>
<script type="text/javascript">
window.onload = function() {
<c:if test="${model.id == null}">
   $('model.name').value = "ROLE_";
</c:if>
   $('model.name').focus();
};
</script>
<s:i18n name="application">

<table align="center" width="650" border = "0">
<tr>
   <td>
   <div id="checkInfo" style="border:1px solid #b6c0db;display:none"></div>
   <%@ include file="/common/messages.jsp" %>
   </td>
</tr>
<s:form action="saveRole" theme="simple" validate="true">
<s:hidden name="model.id" />
<script>
  var roleName = '${model.name}';
</script>
<s:hidden name="model.version" />
  <tr>
    <td colspan="3" align="center">
    <fieldset>
    <legend><fmt:message key="security.role.edit"/></legend>
    <table width="100%" border="0" cellspacing="2" cellpadding="2">
    <tr>
       <td align="right" width="15%"><fmt:message key="security.role.name"/>:</td>
       <td width="40%">
       <s:textfield name="model.name" id="model.name" theme="simple" onblur="onCheckRolename(this.value)" cssStyle="width:150px" /></td>
       <td align="left" rowspan="2" valign="top">
       <div style="color:#990000">*&nbsp;角色包含一组权限，相当于权限的分类。角色名称一般以ROLE_开头，不允许重复的名字.</div></td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.role.descn"/>:</td>
       <td><s:textarea name="model.descn" theme="simple" rows="5" cols="40"/></td>
    </tr>
    
    </table>
    <s:submit value="%{getText('global.save')}" cssClass="button" />
    </fieldset>
    </td>
  </tr>
</s:form>
</table>
</s:i18n>
<script type="text/javascript">
  function onCheckRolename(name) {
    <s:if test="%{model.id == null}">
      //新建用户的时候肯定检查
      roleAction.isRoleNameInUse(name, renderCheck);
    </s:if>
    <s:else>
      if(roleName != name) {//如果不是新建用户，则只有修改之后才验证
        roleAction.isRoleNameInUse(name, renderCheck);
      }
      else{
        $('checkInfo').style.display="none";
        $('checkInfo').innerHTML = "";
      }
    </s:else>
  }
  
  function renderCheck(isUsed) {
    if(isUsed) {
      $('checkInfo').style.display="";
      $('checkInfo').innerHTML="<img src='<c:url value="/images/icons/warning.gif"/>'/><fmt:message key='security.role.nameInUse' />";
      $('model.name').focus();
    }
    else {
      $('checkInfo').style.display="none";
      $('checkInfo').innerHTML = "";
    }
  }
</script>
</body>
</html>