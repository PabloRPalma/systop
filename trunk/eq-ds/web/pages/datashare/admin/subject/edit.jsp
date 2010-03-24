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
  <div class="x-panel-header">关联测项</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">   
			<table>
			  <tr>
			      <td><a href="${ctx}/datashare/admin/subject/index.do"><img
									src="${ctx}/images/icons/house.gif" /> 学科首页</a></td> 
			      <td><span class="ytb-sep"></span></td>
			      <td><a href="#">关联测项</a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </div>
<div class="x-panel-body">
<table width="60%" align="center"><tr><td align="center">
<s:form action="save" namespace="/datashare/admin/subject" validate="true" theme="simple">
	<s:hidden name="model.id"/>
	<s:hidden name="model.name"/>
	<fieldset style="margin:10px;">
	<legend>关联测项</legend>
	<table>
	<tr>
		<td>学科名称：</td>
		<td width="200">
			&nbsp;<s:property value="name"/>
		</td>
	</tr>
	<tr>
		<td>测项大类：</td>
		<td width="200">
		   <s:checkboxlist name="model.methodIds" list="methodAll" listKey="id" listValue="name" templateDir="templates" theme="eqds"/>
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
</body>
</html>