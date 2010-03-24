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
.title{font-family: verdana, tahoma, sans-serif;FONT-SIZE: 12px;}
.orange {font-family: verdana, tahoma, sans-serif;font-size:12px;color:#FF6600}
.copy {font-family: verdana, tahoma, sans-serif;font-size:12px;color:#CCCCCC}
.username{
    background-image:url(${ctx}/images/icons/user.gif);
	background-position: 1px 1px;
	background-repeat:no-repeat;
	padding-left:20px;
	height:20px;
	width:170px;
}
.password{
    background-image:url(${ctx}/images/icons/lock_go.gif);
	background-position: 1px 1px;
	background-repeat:no-repeat;
	padding-left:20px;
	height:20px;
	width:170px;
}
.menu {
	font-size: 14px;
	font-weight: bold;
	color:#286e94;
}

<!--
.STYLE3 {font-size: 12px}
-->
</style>
 <script type="text/javascript">
     function addBookmark() {
        if (window.sidebar) { 
            window.sidebar.addPanel(document.title, location.href,""); 
        } else if( document.all ) {
            window.external.AddFavorite( location.href, document.title);
        } else if( window.opera && window.print ) {
            return true;
        }
     }
  </script>
</head>
<body bgcolor="#FFFFFF">

<br><br><br>
<table align="center" width="350" cellpadding="2" cellspacing="2">
<tr><td align="left">
<c:if test="${param.login_error == 'code_error'}">
   <div style="border:1px solid #a9a9a9; width: 350; padding: 2px; margin: 2px">
       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;验证码输入错误！
   </div>
</c:if> 
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
    <td valign="top" background="images/002.gif"><table width="500" border="0" cellspacing="0" cellpadding="0">
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
              <td><span class="STYLE3">验证码：</span></td>
              <td>
	            <table>
	              <tr>
	              <td>
		                <input name="j_captcha_response" type="text" 
		                id="captcha" style="width: 50px;" autocomplete="off"/>
		          </td>
		          <td>
		             <iframe id="captchaFrame" name="cf" src="<c:url value='/captcha.jpg' />" frameborder="0" scrolling="no" style="width:95px;height:26px;" marginheight="0" marginwidth="0"></iframe>						
				  </td>
				  <td>
					<a href="#" style="color:#425E8D;" onclick="frames['cf'].location='<c:url value="/captcha.jpg" />';">
					[刷]</a>
				  </td>
				 </tr>
				</table>
			  </td>
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
        		<img src="images/11.gif" id="signOn" width="92" height="22" style="CURSOR:hand;cursor:pointer""/> &nbsp;&nbsp;&nbsp;&nbsp;
        		<img src="images/22.gif" id="reg" width="92" height="22" style="CURSOR:hand;cursor:pointer"/>
        	</div>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<div id="alert">请输入验证码!</div>
<br><br><br>
<jsp:include page="/common/foot.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
   var dlg = $('#alert').dialog({
       autoOpen:false,
       modal:true,
       buttons:{'确定' : function() {
           $('#captcha').focus();
           $(this).dialog('close');}
       }
   });
   $('#signOn').click(function() {
        if($('#captcha').val() == '') {
           $('#alert').dialog('open');
           return false;
       }
       $('#loginForm').submit();
   });
   $('#reg').click(function(){
       window.location.href="${ctx}/regist/registMemo.do";
   });
});
   
</script>
</body>
</html>
