<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>

<title>密码管理</title>
<style type="text/css">

.simple {
	font-size: 12px;
	background-color: #FFFFFF;
	padding: 4px;
	color: #0099FF;
}
input,textarea {
	border:1px #CECECE solid;
}
</style>
<script language=javascript>  
	//CharMode函数  
	//测试某个字符是属于哪一类.  
	function CharMode(iN){  
	  if (iN>=48 && iN <=57) //数字  
		  return 1;  
	  if (iN>=65 && iN <=90) //大写字母  
		  return 2;  
	  if (iN>=97 && iN <=122) //小写  
		  return 4;  
	  else  
		  return 8; //特殊字符  
	}  
	//bitTotal函数  
	//计算出当前密码当中一共有多少种模式  
	function bitTotal(num){  
	  modes=0;  
	  for (i=0;i<4;i++){  
	    if (num & 1) modes++;  
	      num>>>=1;  
	  }  
	  return modes;  
	}  
	//checkStrong函数  
	//返回密码的强度级别  
	function checkStrong(sPW){  
	  if (sPW.length<=4)  
	    return 0; //密码太短  
	  Modes=0;  
	  for (i=0;i<sPW.length;i++){  
	    //测试每一个字符的类别并统计一共有多少种模式.  
	    Modes|=CharMode(sPW.charCodeAt(i));  
	  }  
	  return bitTotal(Modes);  
	}  
	//pwStrength函数  
	//当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示不同的颜色  
	function pwStrength(pwd){  
	  O_color="#eeeeee";  
	  L_color="#FF0000";  
	  M_color="#FF9900";  
	  H_color="#33CC00";  
	  if (pwd==null||pwd==''){  
		Lcolor=Mcolor=Hcolor=O_color;  
	  }  
	  else{  
	    S_level=checkStrong(pwd);  
	    switch(S_level) {  
		case 0:  
		  Lcolor=Mcolor=Hcolor=O_color;  
		case 1:  
		  Lcolor=L_color;  
		  Mcolor=Hcolor=O_color;  
		  break;  
		case 2:  
		  Lcolor=Mcolor=M_color;  
		  Hcolor=O_color;  
		  break;  
		default:  
		  Lcolor=Mcolor=Hcolor=H_color;  
	  }  
    }  
	document.getElementById("strength_L").style.background=Lcolor;  
	document.getElementById("strength_M").style.background=Mcolor;  
	document.getElementById("strength_H").style.background=Hcolor;  
	return;  
   }  
</script>  
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">密码管理</div>
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body">
<table width="60%" align="center">
  <tr><td align="center">
	<s:form action="changePassword" theme="simple" validate="true" method="POST">
		<fieldset style="margin:10px;">
		<legend>修改密码</legend>
		<table>
		    <tr>
		    <td class="simple" style="text-align:right;">旧密码：</td>
		    <td class="simple" style="text-align:left;">
		      <s:password name="oldPassword" id="oldPassword" theme="simple" size="31"/>*</td>
		  </tr>
		  <tr>
			<td class="simple" style="text-align:right;">新密码：</td>
			<td class="simple" style="text-align:left;">
			  <s:password name="model.password" id="pwd" theme="simple" size="31" onkeyup="javascript:pwStrength(this.value);" onblur="javascript:pwStrength(this.value);"/>*</td>
			<td>
			  <table width="100" border="0" cellspacing="1" cellpadding="1" bordercolor="#cccccc" height="20" style='display:inline'>  
				<tr align="center" bgcolor="#eeeeee">  
					<td width="33%" id="strength_L">弱</td>
					<td width="33%" id="strength_M">中</td>
					<td width="33%" id="strength_H">强</td>  
				</tr>
			  </table>
		    </td>
		  </tr>
		  <tr>
		    <td class="simple" style="text-align:right;">重复密码：</td>
			<td class="simple" style="text-align:left;"><s:password name="model.confirmPwd" id="repwd" theme="simple" size="31"/>*</td>
		  </tr>
		  </table>
			</fieldset>
			<table width="100%" style="margin-bottom:10px;">
			<tr>
				<td colspan="2" align="center" class="font_white">
				<s:submit value="保存" cssClass="button"></s:submit>
				&nbsp;&nbsp;
                <input type="button" class="button" onclick="javascript:history.back(-1);" value="返回">
				</td>		
			</tr>
	      </table>
	</s:form>
   </td></tr>
 </table>
 </div>
 </div>
</body>
</html>