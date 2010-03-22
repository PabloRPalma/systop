<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/calendar.jsp" %>
<script type='text/javascript' src='${ctx}/js/jquery/jquery-1.3.2.js' ></script>
<LINK href="${ctx}/css/thinCombobox.css" type='text/css' rel='stylesheet'>
<title>编辑员工</title>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">
<s:if test="model.id != null">
 编辑员工 (当前所属部门是：${model.dept.name})
</s:if>
<s:else> 
新建员工 (当前所属部门是：${model.dept.name})
</s:else>
</div>
<div class="x-panel-body">
<div><%@ include file="/common/messages.jsp"%></div>
<s:form namespace="/hr" action="employee/save" theme="simple" validate="true" method="POST" enctype="multipart/form-data" cssClass="x-form">
	<s:hidden id="model.id" name="model.id" />
	<s:hidden id="userId" name="model.user.id" />
	<s:hidden id="model.user.status" name="model.user.status" />
	<s:hidden id="model.user.version" name="model.user.version" />
	<div id="tabs" align="center">
		<div id="login" class="x-hide-display">
		<fieldset style="margin: 10px;"><legend>员工登录信息</legend>
		<table width="350">
			<tr>
				<td style="text-align: right;">登录名&nbsp;：</td>
				<td style="text-align: left;">
				<div style="clear: none; width: 100%;" id="checkMsg">
					<s:textfield name="model.user.loginId" id="loginId" cssStyle="width:150px;"/>&nbsp;
					<font color="red">*</font>
				</div>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">登录密码&nbsp;：</td>
				<td style="text-align: left;">
					<s:password name="model.user.password" id="password" cssStyle="width:150px;" />
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">重复密码&nbsp;：</td>
				<td style="text-align: left;">
					<s:password name="model.user.confirmPwd" id="confirmPwd" cssStyle="width:150px;" />
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
		</table>
		</fieldset>
		</div>
		<div id="Personal" class="x-hide-display">
		<fieldset style="margin: 10px;"><legend>个人信息</legend>
		<table width="350">
			<tr>
				<td style="text-align: right;">姓&nbsp;&nbsp;名&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.name" id="name" cssStyle="width:150px;" /> 
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">所在部门&nbsp;：</td>
				<td style="text-align: left;" valign="center">
					<div id="comboxWithTree" style="float: left;"></div>
					&nbsp;<font color="red">*</font>
					<s:hidden id="deptId" name="model.dept.id" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">职&nbsp;&nbsp;位&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.place" cssStyle="width:150px;" />
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">性&nbsp;&nbsp;别&nbsp;：</td>
				<td style="text-align: left;">
					<s:radio list="sexMap" name="model.sex" cssStyle="border:0px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">证件号码&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.credentialNo" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">出生日期&nbsp;：</td>
				<td style="text-align: left;">
					<input type="text" name="model.birthday" style="width:150px;" id="birthday"
				 		value='<s:date name="model.birthday" format="yyyy-MM-dd"/>'
				 		class="Wdate" onClick="WdatePicker({skin:'whyGreen'})" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">参加工作时间&nbsp;：</td>
				<td style="text-align: left;">
					<input type="text"
					name="model.jobDate" style="width:150px;" id="jobDate" class="Wdate"
					value='<s:date name="model.jobDate" format="yyyy-MM-dd"/>'
					onclick="WdatePicker({skin:'whyGreen'})"
					mindate="#F{$('birthday').value}" maxdate="2030-12-30" />
				</td>
			</tr>
		</table>
		</fieldset>
		</div>
		<div id="contact" class="x-hide-display">
		<fieldset style="margin: 10px;"><legend>联系方式</legend>
		<table width="350">
			<tr>
				<td style="text-align: right;">电子邮箱&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.user.email" cssStyle="width:150px;" /> 
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">Q&nbsp;&nbsp;&nbsp;&nbsp;Q&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.qq" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">M&nbsp;S&nbsp;N&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.msn" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">传&nbsp;&nbsp;真&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.fax" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">邮&nbsp;&nbsp;编&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.zip" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">手&nbsp;&nbsp;机&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.mobil" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">家庭电话&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.hTel" cssStyle="width:150px;" />
				</td>
			</tr>
		</table>
		</fieldset>
		</div>
		<div id="other" class="x-hide-display">
		<fieldset style="margin: 10px;"><legend>其它信息</legend>
		<table width="350">
			<tr>
				<td style="text-align: right;">照&nbsp;&nbsp;片&nbsp;：</td>
				<td style="text-align: left;">
					<s:file name="picture" size="10" cssStyle="border:1px #cecece solid;" />
				</td>
				<td colspan="10" width="100">
					<c:if test="${model.photo != null && model.photo != ''}">
					<a href="${ctx}${model.photo}" target="_blank"> <img
						src="${ctx}${model.photo}" width="100" height="100" border="0" title="查看大图" />
					</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">婚&nbsp;&nbsp;否&nbsp;：</td>
				<td style="text-align: left;">
					<s:radio list="marriedMap" name="model.married" cssStyle="border:0px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">民&nbsp;&nbsp;族&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.folk" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">户口所在地&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.registeredPos" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">毕业学校&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.school" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">毕业时间&nbsp;：</td>
				<td style="text-align: left;">
					<input type="text"
					name="model.graduateDate" style="width:150px;" id="graduateDate" class="Wdate"
					value='<s:date name="model.graduateDate" format="yyyy-MM-dd"/>'
					onclick="WdatePicker({skin:'whyGreen'})"
					mindate="#F{$('birthday').value}" maxdate="2030-12-30" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">政治面貌&nbsp;：</td>
				<td style="text-align: left;">
					<s:select list="politicalMap" name="model.political" cssStyle="width:155px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">专&nbsp;&nbsp;业&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.speciality" cssStyle="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">学&nbsp;&nbsp;历&nbsp;：</td>
				<td style="text-align: left;">
					<s:select list="degreeMap" name="model.degree" cssStyle="width:155px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">籍&nbsp;&nbsp;贯&nbsp;：</td>
				<td style="text-align: left;">
					<s:textfield name="model.address" cssStyle="width:150px;" />
				</td>
			</tr>
		</table>
		</fieldset>
		</div>
	</div>
	<table width="95%" style="margin-bottom: 10px;" align="center">
		<tr>
			<td style="text-align: center;"><s:submit value="保存"
				cssClass="button" /> <s:reset value="重置" cssClass="button" /></td>
		</tr>
	</table>
</s:form>
  </div>
</div>
<script>
   var initValue = '';
   <s:if test="model.dept != null">
     initValue = '${model.dept.name}';
   </s:if>
   var param = "";
   <s:if test="model.user.id != null">
   Ext.onReady(function() {
	   Ext.get('password').dom.value="*********";
	   Ext.get('confirmPwd').dom.value="*********";
   });
   </s:if>
</script>  
<script type="text/javascript" src="${ctx}/pages/hr/dept/dept-combo.js"></script>
<script type="text/javascript" src="${ctx}/pages/hr/employee/edit.js"></script>
<script type="text/javascript">
nodeIdCallback = function(nodeId) {
	Ext.get('deptId').dom.value = nodeId;
};
</script>
</body>
</html>