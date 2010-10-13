<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">权限管理</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">
			<table>
			  <tr>
      <td><a href="${ctx}/security/permission/index.do"> 权限管理首页</a></td>
      <td><span class="ytb-sep"></span></td>
      <td><a href="#"> 编辑权限信息</a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </div>
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body">
<table width="600" align="center"><tr><td align="center">
<s:form action="permission/save" validate="true" theme="simple">
<s:hidden name="model.id"/>
<s:hidden name="model.version"/>
<fieldset style="margin:10px;">
	<legend>编辑权限</legend>
	<table>
	<tr>
		<td>权限类型：</td>
		<td align="left">
		URL权限<input type="hidden" value="target_url" name="model.operation"/>
		</td>
	</tr>
	<tr>
		<td>权限名称：</td>
		<td>
		<s:textfield id="model.name" name="model.name"></s:textfield><font color="red">
		&nbsp;*权限名称必须以AUTH_开头</font>
		</td>
	</tr>
	
	<tr>
		<td>权限描述：</td>
		<td>
		<s:textfield id="model.descn" name="model.descn"></s:textfield><font color="red">&nbsp;*</font>
		</td>
	</tr>
	</table>
	</fieldset>
	<table width="100%" style="margin-bottom:10px;">
	<tr>
		<td colspan="2" align="center" class="font_white">
		<s:submit value="保存" cssClass="button"></s:submit>
		</td>
	</tr>
</table>
</s:form>
 </td></tr></table>
        </div></div>
<script type="text/javascript">

$(function() {
    var ck = new Cookie();
    var value = ck.getCookie("operation");
    var op = $("#operation")[0];
    if(value) {
        for(i = 0; i < op.options.length; i++) {
           if(op.options[i].value == value) {
              op.selectedIndex = i;
              break;
           }
        }
    }
    
    $('#operation').change(function() {
        var ck = new Cookie();
        ck.setCookie("operation", this.value);
    });
});
</script>
</body>
</html>