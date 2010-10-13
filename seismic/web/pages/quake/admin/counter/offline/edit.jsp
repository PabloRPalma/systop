<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/validator.jsp" %>
<script type="text/javascript">
	$(document).ready(function(){
		$("#saveFrm").validate();
	});
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">添加离线数据下载信息</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">
			<table>
			  <tr>
      <td><a href="${ctx}/quake/admin/counter/offline/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 离线数据管理首页</a></td>
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
<s:form id="saveFrm" action="save" theme="simple" validate="true">
<s:hidden name="model.id"/>
<fieldset style="margin:10px;">
	<legend>编辑离线数据信息</legend>
	<table>
	<tr>
		<td>数据类型：</td>
		<td>
		<s:textfield id="model.subject" name="model.subject"></s:textfield><font color="red">&nbsp;*</font>
		</td>
	</tr>
	
	<tr>
		<td>服务时间：</td>
		<td>
		<s:textfield size="20" cssClass="Wdate" id="model.time" name="model.time"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',readOnly:true});"/><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td>服务对象：</td>
		<td>
		<s:textfield name="model.userName"></s:textfield><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td>所属行业：</td>
		<td>
			<s:radio list="industryMap" cssClass="required" name="model.industry"></s:radio><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td>数据下载量：</td>
		<td>
		<s:textfield cssClass="number" name="model.dataFlow"></s:textfield><font color="red">&nbsp;*</font>(GB)
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