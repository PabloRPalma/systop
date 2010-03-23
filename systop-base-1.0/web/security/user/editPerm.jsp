<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title><fmt:message key= "security.perm.edit"/></title>
<%@include file="/common/meta.jsp" %>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/interface/permAction.js"/>'> </script>
<s:head/>
</head>
<body>
<script type="text/javascript">
window.onload = function() {
    $('model.operation').focus();
    <c:if test="${model.id == null}">
    $('model.name').value = "AUTH_";
    </c:if>
};
function onPermTypeChange() {
    if($('model.operation').value == "0") {
        if($('model.name').value == "" || $('model.name').value=="ACL_") {
            $('model.name').value = "AUTH_";
        }
    } else {
        if($('model.name').value == "" || $('model.name').value=="AUTH_") {
            $('model.name').value = "ACL_";
        }
    }
    $('model.name').focus();
}
</script>
<s:i18n name="application">

<table align="center" width="650" border = "0">
<tr>
   <td>
   <div id="checkInfo" style="border:1px solid #b6c0db;display:none"></div>
   <%@ include file="/common/messages.jsp" %>
   </td>
</tr>
<s:form action="savePerm" id="savePerm" theme="simple" validate="true">
<s:hidden name="model.id" id="savePerm.id" />
<script>
  var permName = '${model.name}';
</script>
<s:hidden name="model.version" />
  <tr>
    <td colspan="3" align="center">
    <fieldset>
    <legend><fmt:message key="security.perm.edit"/></legend>
    <table width="100%" border="0" cellspacing="2" cellpadding="2">
    <tr>
       <td align="right" width="15%"><fmt:message key="security.perm.operation"/>:</td>
       <td  width="30%">
       <systop:catalogSelector catalog="perm_operation" name="model.operation" onchange="onPermTypeChange()"/>
       </td>
       <td align="left"></td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.perm.name"/>:</td>
       <td>
       <s:textfield name="model.name" id="model.name" theme="simple" onblur="onCheckPermname(this.value)" cssStyle="width:300px"/></td>
       <td align="left" rowspan="3" valign="top">
       <div style="color:#990000">*&nbsp;权限包含一组可以访问的资源。
       如果是函数权限，则以ACL_开头，如果是URL权限则以AUTH_开头。</div></td>
    </tr>
      
    
    <tr>
       <td align="right"><fmt:message key="security.perm.status"/>:</td>
       <td>
       <systop:catalogSelector catalog="perm_status" name="model.status" defaultValue="1"/>
       </td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.perm.descn"/>:</td>
       <td><s:textarea name="model.descn" theme="simple" cols="40" rows="5"/></td>
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
  function onCheckPermname(name) {
    <s:if test="%{model.id == null}">
      //新建权限的时候肯定检查
      permAction.isNameInUse(name, renderCheck);
    </s:if>
    <s:else>
      if(permName != name) {//如果不是新建权限，则只有修改之后才验证
        permAction.isNameInUse(name, renderCheck);
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
      $('checkInfo').innerHTML="<img src='<c:url value="/images/icons/warning.gif"/>'/><fmt:message key='security.perm.nameInUse' />";
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