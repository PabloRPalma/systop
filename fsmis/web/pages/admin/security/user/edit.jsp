<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>

<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<style type="text/css">
<!--
.Message {
	color: #FF0000;
	font-style: italic;
}
#mytable {
	border: 1px solid #A6C9E2;
	border-collapse: collapse;
}

#mytable td {
	border: 1px solid #A6C9E2;
	height: 26px;
}
-->
</style>
<title>编辑用户</title>
</head>
<body>


<div class="x-panel-body" style="margin-top: 20px;">
<s:form action="user/save" id="save" validate="true" method="POST">
	<s:hidden name="model.id" id="uId" />
	<s:hidden name="model.version" />
	<s:hidden name="model.status" />
	<s:hidden name="selfEdit" />
	<table align="center" style="margin-bottom:2px;" width="400">
		<tr><td>
   			<%@ include file="/common/messages.jsp"%>
	   	</td></tr>
	</table>
	<table id="mytable" align="center" width="450">
		<tr>
			<td colspan="2" align="center" bgcolor="#E6F4F7" style="padding-top: 5px;">
				<b>用 户 信 息 编 辑</b>
			</td>
		</tr>
		<tr>
			<td align="right" width="100" bgcolor="#E6F4F7">
				<b>登 录 名：</b>
			</td>
			<td width="350">&nbsp;
				<s:if test="model.isSys == 1">
				  <s:textfield name="model.loginId" id="loginId" size="25" cssClass="userName required" title="系统用户不可修改用户名！" disabled="true"/>
				</s:if>
				<s:else>
				  <s:textfield name="model.loginId" id="loginId" size="25" cssClass="userName required"/>&nbsp;<font color="red">*</font>
				</s:else>
				<span id="denglu">&nbsp;登录名不能重复</span>
			</td>
		</tr>
		
		<tr>
			<td align="right" bgcolor="#E6F4F7">
				<b>登录密码：</b>
			</td>
			<td class="simple">&nbsp;
				<s:password name="model.password" id="pwd" cssStyle="width:152px;" cssClass="pwdCheck required"/>&nbsp;<font color="red">*</font>
				<span id="pswdck">&nbsp;密码应大于六位</span>
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7">
				<b>确认密码：</b>
			</td>
			<td class="simple">&nbsp;
				<s:password name="model.confirmPwd" id="repwd" cssStyle="width:152px;" cssClass="passwordCheck required"/>&nbsp;<font color="red">*</font>
				<span id="pswd">&nbsp;请再次输入密码</span>
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7">
				<b>真实姓名：</b>
			</td>
			<td class="simple">&nbsp;
				<s:textfield name="model.name" cssClass="required" size="25"/>&nbsp;<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td align="right" width="100" bgcolor="#E6F4F7"><b>性　　别：</b></td>
			<td class="simple">&nbsp;
				<s:radio list="sexMap" name="model.sex" cssStyle="border:0px;" />
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7"><b>所属部门：</b></td>
			<td class="simple">
				<div id="comboxWithTree" style="float:left;margin-left:6px;"></div>
				&nbsp;<font color="red" style="margin-left:2px;">*</font>
				<s:hidden name="model.dept.id" id="deptId" cssClass="deptCheck"></s:hidden>
				<span id="deptTip">&nbsp;请选择所属部门</span>
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7"><b>手　　机：</b></td>
			<td class="simple">&nbsp;
				<s:textfield name="model.mobile" id="mobile" cssClass="mobileCheck" size="25"/>
				<font color="red" style="margin-left:0px;">*</font>
				<span id="mobileTip">用于短信接收</span>
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7"><b>固定电话：</b></td>
			<td class="simple">&nbsp;
				<s:textfield name="model.hTel" size="25" />
				<span>&nbsp;&nbsp;办公或家庭电话</span>
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7"><b>电子邮箱：</b></td>
			<td class="simple">&nbsp;
				<s:textfield name="model.email" id="email" size="25" cssClass="regEmail"/>
				<span id="usemail">&nbsp;&nbsp;常用的电子邮箱</span>
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7"><b>职务/职称：</b></td>
			<td class="simple">&nbsp;
				<s:textfield name="model.post" size="25" />
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7"><b>邮　　编：</b></td>
			<td class="simple">&nbsp;
				<s:textfield name="model.zip" size="25" />
				<span>&nbsp;</span>
			</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#E6F4F7"><b>通信地址：</b></td>
			<td class="simple">&nbsp;
				<s:textfield name="model.address" size="45" />
				<span>&nbsp;&nbsp;详细地址</span>
			</td>
		</tr>
	</table>
	<table width="100%" style="margin-bottom: 10px;">
		<tr>
			<td align="center" class="font_white">
				<s:submit value="保存" cssClass="button"/>&nbsp;&nbsp; 
				<s:reset value="清空" cssClass="button"/>
			</td>
		</tr>
	</table>
</s:form>
</div>
<script type="text/javascript">
$(function() {
    //验证密码一致性
	$.validator.addMethod("passwordCheck", function(value, element) {
        var res;
        var pwd1 = document.getElementById('pwd').value;
  		var pwd2 = document.getElementById('repwd').value;
  		if(pwd1 != null & pwd2 != null & pwd1 != '' & pwd2 != '') {
  			if(pwd1 != pwd2) {
  				res = "err";
  				document.getElementById('pswd').innerHTML = '<font color="red">'+'两次密码不一致。'+'</font>';
  			}else{
  				res = "ok";
  				document.getElementById('pswd').innerHTML = '&nbsp;请再次输入密码';
  			}
  		}
        return res != "err";
    },"");
    //验证密码
	$.validator.addMethod("pwdCheck", function(value, element) {
        var res;
        var pwd1 = document.getElementById('pwd').value;
  		if(pwd1.length < 6) {
  			res = "err";
  			document.getElementById('pswdck').innerHTML = '<font color="red">'+'密码小于6位。'+'</font>';
  		}else{
  			res = "ok";
  			document.getElementById('pswdck').innerHTML = '&nbsp;密码应大于六位';
  		}
        return res != "err";
    },"");
    //验证用户名
	$.validator.addMethod("userName", function(value, element) {
        var res;
        var userName = document.getElementById('loginId').value;
        var userId = document.getElementById('uId').value;
     	/**
     	var re = /^(?!_)(?!.*?_$)[a-zA-Z0-9_-\u4e00-\u9fa5]+$/;
     	var result = re.exec(userName);
     	if (result != null) {
       		document.getElementById('denglu').innerHTML = '请填写登录名(仅限字母、数字、横线和下划线。)';
     	}else {
        	document.getElementById('denglu').innerHTML = '<font color="red">'+'仅限字母、数字、横线和下划线，请重新输入。'+'</font>';
     	}
     	*/
     	if (userName != null && userName != ""){
     	   var u = encodeURI('${ctx}/security/user/checkName.do');
      	   $.ajax({
			 url: u,
			 type: 'post',
			 async : false,
			 dataType: 'json',
			 data: {userName : userName, userId : userId},
			 success: function(rst, textStatus){
				res = rst.result;
				if (rst.result == "exist") {
       	   			document.getElementById('denglu').innerHTML ='登录名<font color="red"><b>'+userName+'</b></font>已存在!';
       	    	}else {
       	    		document.getElementById('denglu').innerHTML = '&nbsp;登录名不能重复';
       	    	}
			 }
	  	   });
	  	   return res != "exist";
	  	 }
  	  },"");
	//部门验证
	$.validator.addMethod("deptCheck", function(value, element) {
        var res;
        var deptId = document.getElementById('deptId').value;
  		if(deptId == null || deptId == '') {
			res = "err";
			document.getElementById('deptTip').innerHTML = '&nbsp;<font color="red">'+'未选择所属部门'+'</font>';
  		}else{
  			res = "od";
			document.getElementById('deptTip').innerHTML = '&nbsp;'+'请选择所属部门';
  		}
        return res != "err";
    },"");

	//手机验证
	$.validator.addMethod("mobileCheck", function(value, element) {
        var res;
        var mobile = document.getElementById('mobile').value;
  		if(mobile != null && mobile.length > 0) {
  	  	  	if(isNaN(mobile)){
	  	  	  	res = "err";
				document.getElementById('mobileTip').innerHTML = '<font color="red">手机号格式错误</font>';
  	  	  	}else{
	  	  		if (mobile.length != 11){
					res = "err";
					document.getElementById('mobileTip').innerHTML = '<font color="red">手机号长度错误:</font>' + mobile.length;
	  	  	  	}else {
	  	  	  		res = "ok";
		  	  		document.getElementById('mobileTip').innerHTML = '用于短信接收';
	  	  	  	} 
  	  	  	}
  		}else{
  			res = "err";
			document.getElementById('mobileTip').innerHTML = '<font color="red">请填写手机号!</font>';
  		}
        return res != "err";
    },"");
    
  	  //验证用户邮箱
	$.validator.addMethod("regEmail", function(value, element) {
        var res;
        var emailStr = document.getElementById('email').value;
        var userId = document.getElementById('uId').value;
		var r = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if (emailStr != null && emailStr.length > 0){
			if (r.test(emailStr)) {
				document.getElementById('usemail').innerHTML = '&nbsp;&nbsp;常用的电子邮箱';
		      		$.ajax({
					url: '${ctx}/security/user/checkEmail.do',
					type: 'post',
					async : false,
					dataType: 'json',
					data: {emailStr : emailStr, userId : userId},
					success: function(rst, textStatus){
						res = rst.result;
						if (rst.result == "exist") {
		      	   		  		document.getElementById('usemail').innerHTML = '<b><font color="red">' + emailStr + '</font>已存在!</b>';
		      	   		}
					}
				});//异步调用结束
				return res != "exist";
			} else {
				document.getElementById('usemail').innerHTML = '<font color="red">邮件地址格式错误!</font>';
				return false;
			}
		}else{
			document.getElementById('usemail').innerHTML = '&nbsp;&nbsp;常用的电子邮箱';
		}
		return true;
  	  },"");
	}); 
	<c:if test="${model.id != null}">
	$(function() {
	    $('#pwd').val("*********");
	    $('#repwd').val("*********");
	}); 
	</c:if>

	/** ready */
	$(document).ready(function() {
		$("#save").validate();
	});
</script>

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
</body>
</html>