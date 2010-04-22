<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include  file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统登录</title> 
<LINK href="${ctx}/styles/style.css" type='text/css' rel='stylesheet'>
<link type="text/css" href="scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />
<script type="text/javascript" src="scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="scripts/jquery/ui/jquery-ui-1.7.1.js"></script>

<style type="text/css">
body {
	margin: 0px;
	font-family: "宋体";
	font-size: 12px;
	color: #12588E;
}
.bt {
	font-family: "宋体";
	font-size: 14px;
	font-weight: bold;
}


.inputborder {
	border: 1px solid #88ABDC;
	height: 20px;
	line-height:18px;
	color:#C2060F
}
.STYLE2 {font-family: "黑体"; font-size: 24px;}
.STYLE3 {
	color: #11588E;
	font-weight: bold;
}
.STYLE4 {color: #11588E; font-weight: bold; font-size: 14px; }
.username{
    background-image:url(${ctx}/images/icons/user.gif);
	background-position: 1px 1px;
	background-repeat:no-repeat;
	padding-left:20px;
	height:20px;
	width:170px;
	border: 1px solid #88ABDC;
	color:#C2060F;
}
.password{
    background-image:url(${ctx}/images/icons/lock_go.gif);
	background-position: 1px 1px;
	background-repeat:no-repeat;
	padding-left:20px;
	height:20px;
	width:170px;
	border: 1px solid #88ABDC;
	color:#C2060F;
}
.menu {
	font-size: 14px;
	font-weight: bold;
	color:#286e94;
}
</style>
</head>
<body bgcolor="#FFFFFF">
<!-- 
<br><br><br>
<table align="center" width="350" cellpadding="2" cellspacing="2">
<tr><td align="left">
<c:if test="${param.login_error == 'user_psw_error' || param.login_error == '1'}">
   <div style="border:1px solid #a9a9a9; width: 350; padding: 2px; margin: 2px">
       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;用户名或密码错误，请重试！
   </div>
</c:if> 
<c:if test="${param.login_error == 'too_many_user_error'}">
    <div style="border:1px solid #a9a9a9; width: 350; padding: 2px; margin: 2px">
       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;该用户已经在其他地方登录了，请稍候。
   </div>
</c:if>
</td>
</tr>
</table>
<table width="500" height="372" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" background="images/background.gif"><table width="500" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="81">&nbsp;</td>
      </tr>
      <tr>
        <td height="145"><form id="loginForm" action='${ctx}/j_security_check' method="post" >
          <br />
          <table width="255" height="106" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="76"><span class="STYLE3">用户名：</span></td>
              <td width="159"><input name="j_username" id="j_username" type="text" size="16" class="username" /></td>
            </tr>
            <tr>
              <td><span class="STYLE3">密　码：</span></td>
              <td><input name="j_password" id="j_password" type="password" size="16" class="password" /></td>
            </tr>
            <tr>
            	<td colspan="2"><img src="${ctx}/images/icons/key.gif" style="width:16px;height:16px;">
            	<a href="${ctx}/restorePassword/edit.do">忘记密码?</a></td>
            </tr>
          </table>
        </form></td>
      </tr>
      <tr>
        <td height="78">
        	<div align="center">
        		<img src="images/login.gif" id="signOn" width="92" height="22" style="CURSOR:hand;cursor:pointer""/> &nbsp;&nbsp;&nbsp;&nbsp;
        		<img src="images/register.gif" id="reg" width="92" height="22" style="CURSOR:hand;cursor:pointer"/>
        	</div>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<br><br><br>

<script type="text/javascript">
$(function() {
   $('#signOn').click(function() {
       $('#loginForm').submit();
   });
   $('#reg').click(function(){
       window.location.href="${ctx}/regist/registMemo.do";
   });
});
</script>
 -->

<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#8AB2ED">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" height="31" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" style="padding-right: 10px;"><a href="#">登录帮助</a></td>
  </tr>
</table>
<div style="height:70px;"></div>
<table width="100%" height="303" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="47%" valign="top">
      <table width="100%" height="234" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="66" align="center">
        	<img src="images/title2.jpg" width="425" height="39" />
        </td>
      </tr>
      <tr>
        <td height="168">
        <table width="300" height="141" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="63">
            	<img src="images/01.gif" width="36" height="36" />
            </td>
            <td width="237">
            	<table width="200" height="40" border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td class="STYLE4">六大功能平台</td>
	              </tr>
	              <tr>
	                <td>
	                	协调指挥 统计分析  短信通知...<br>
	                </td>
	              </tr>
	            </table>
	         </td>
          </tr>
          <tr>
            <td>
            	<img src="images/02.gif" width="36" height="40" />
            </td>
            <td>
	            <table width="200" height="40" border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td class="STYLE4">食品安全网站举报</td>
	              </tr>
	              <tr>
	                <td>食品安全事件在线举报,网站举报...</td>
	              </tr>
	            </table>
	        </td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="2%">
    	<img src="images/logging_middle.gif" width="3" height="378" />
    </td>
    <td width="51%" valign="top" style="padding-top:20px;">
      <div class="bt" style="padding:5 0 20 0;">欢迎登录！</div>
	  <form id="loginForm" action='${ctx}/j_security_check' method="post" >
      <table width="431" height="124" border="0" align="center" cellpadding="0" cellspacing="0">
      	  <tr>
      	  	<td colspan="2">
      	  	 <c:if test="${param.login_error == 'user_psw_error' || param.login_error == '1'}">
			    <div style="border-bottom: 1px dashed #88ABDC; width: 270px; padding: 5 0 2 8;">
			      <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;用户名或密码错误,请重试！
			    </div>
			  </c:if> 
			  <c:if test="${param.login_error == 'too_many_user_error'}">
			    <div style="border-bottom: 1px dashed #88ABDC; width: 270px; padding: 5 0 2 8;">
			       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;该用户已经在其他地方登录了,请稍候。
			   </div>
			  </c:if>
      	  	</td>
      	  </tr>
	      <tr>
	        <td width="83" align="center" class="STYLE3">
	        	用户名：
	        </td>
	        <td width="348">
	        	<input type="text" name="j_username" id="j_username" class="username" size="20" />
	        </td>
	      </tr>
	      <tr>
	        <td align="center" class="STYLE3">
	        	密　码：
			</td>
	        <td>
	        	<input name="j_password" id="j_password" type="password" class="password" size="21" />
	        </td>
	      </tr>
	      <tr>
	        <td colspan="2" style="padding-left:83px;">
	        	<input type="submit" class="button" value="登&nbsp;录" />
	            <input type="reset" class="button" value="重&nbsp;置" />
	        </td>
	      </tr>
    	</table>
    	</form>
    </td>
  </tr>
</table>
<div style="height:50px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#8AB2ED">
  <tr>
    <td height="1"></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td style="padding-top: 10px;">
    	<div align="center">
    		石家庄市人民政府 &nbsp;&nbsp;河北新龙科技股份有限公司
    	</div>
    	<div align="center" style="padding-top: 5px;">
    		Copyright 2009-2010 SysTop Co,Ltd All Right is Reserved
    	</div>
    </td>
  </tr>
</table>

</body>
</html>
