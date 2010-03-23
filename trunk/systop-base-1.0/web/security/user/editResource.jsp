<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title><fmt:message key= "security.res.edit"/></title>
<%@include file="/common/meta.jsp" %>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/interface/resourceAction.js"/>'> </script>
<s:head/>
</head>
<body>
<script type="text/javascript">
window.onload = function() {
    $('model.name').focus();
<c:if test="${model.id == null}">
    $('model.resString').value = "/";
</c:if>
}
function onResTypeChanged() {
   if($('model.resType').value == "0") {
        if($('model.resString').value == "" || $('model.resString').value=="com.systop.") {
            $('model.resString').value = "/";
        }
    } else {
        if($('model.resString').value == "" || $('model.resString').value=="/") {
            $('model.resString').value = "com.systop.";
        }
    }
    $('model.name').focus();
}
</script>
<s:i18n name="application">

<table align="center" width="750" border = "0">
<tr>
   <td>
   <div id="checkInfo" style="border:1px solid #b6c0db;display:none"></div>
   <%@ include file="/common/messages.jsp" %>
   </td>
</tr>
<s:form action="saveResource" id="saveResource" theme="simple" validate="true">
<s:hidden name="model.id" id="saveResource.id" />
<script>
  var resName = '${model.name}';
</script>
<s:hidden name="model.version" />
  <tr>
    <td colspan="3" align="center">
    <fieldset>
    <legend><fmt:message key="security.res.edit"/></legend>
    <table width="100%" border="0" cellspacing="2" cellpadding="2">
    <tr>
       <td align="right" width="15%"><fmt:message key="security.res.type"/>:</td>
       <td>
          <systop:catalogSelector catalog="res_type" name="model.resType" defaultValue="0" onchange="onResTypeChanged()"/>
       </td>
       <td align="left"></td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.res.name"/>:</td>
       <td width="40%">
       <s:textfield name="model.name" id="model.name" theme="simple" onblur="onCheckResname(this.value)" cssStyle="width:400px" /></td>
       <td align="left"><div style="color:#990000">*&nbsp;给这个资源起一个名字</div></td>
    </tr>
    <tr>
       <td align="right"><fmt:message key="security.res.str"/>:</td>
       <td><s:textfield name="model.resString" id="model.resString" theme="simple" cssStyle="width:400px" /></td>
       <td align="left" rowspan="2" valign="top"><div style="color:#990000">*&nbsp;
       URL资源是指可以访问的一个地址，函数资源是指服务器端的一个函数。可以用*作为通配符，例如，/cms/*.*表示cms目录下的所有资源。
        如果是函数资源，以com.systop.开头，如果是URL资源以/开头，不允许重复。</div></td>
    </tr>
    
    <tr>
       <td align="right"><fmt:message key="security.res.descn"/>:</td>
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
  function onCheckResname(name) {
    <s:if test="%{model.id == null}">
      //新建资源的时候肯定检查
      resourceAction.isNameInUse(name, renderCheck);
    </s:if>
    <s:else>
      if(resName != name) {//如果不是新建用户，则只有修改之后才验证
        resourceAction.isNameInUse(name, renderCheck);
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
      $('checkInfo').innerHTML="<img src='<c:url value="/images/icons/warning.gif"/>'/><fmt:message key='security.res.nameInUse' />";
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