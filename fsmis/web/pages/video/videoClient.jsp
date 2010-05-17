<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.systop.common.modules.security.user.UserUtil"%>
<%@page import="com.systop.common.modules.security.user.model.User"%><html>
<%@page import="com.systop.fsmis.video.VideoConstants" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/flex/history/history.css" />
<!--  END Browser History required section -->
<title></title>
<script src="${ctx}/scripts/flex/AC_OETags.js" language="javascript"></script>
<!--  BEGIN Browser History required section -->
<script src="${ctx}/scripts/flex/history/history.js" language="javascript"></script>
<!--  END Browser History required section -->
<title></title>
</head>
<body>
<stc:ifNotLogin>
   请登录。
</stc:ifNotLogin>
<stc:ifLogin>
<%
//得到当前用户,以构建flex程序必须的参数
User user = UserUtil.getPrincipal(request);

String loginId = user.getLoginId();
Integer id = user.getId();
String room = request.getParameter("room"); //如果想进入房间，在参数中指定房间name
if(StringUtils.isBlank(room)) { //否则，新建房间，名称使用当前登录用户id
  if(user != null) {
    room = user.getId().toString();
  }
}
String host = request.getRequestURL().toString();
host = host.substring("http://".length());
int index = 0;
if(host.indexOf(":") > 0) {
  host = host.substring(0, host.indexOf(":"));
} else if(host.indexOf("/") > 0 ) {
  host = host.substring(0, host.indexOf("/"));
}
System.out.println("userId:"+id);
System.out.println("room:"+room);
System.out.println("host:"+host);
%>

  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="cnhrClient" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="${ctx}/flash/video/videoClient.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="FlashVars" value="room=<%=room%>&loginId=<%=loginId%>&host=<%=host%>&userId=<%=user.getId()%>&ctxPath=<%=VideoConstants.WEBAPP_CONTEXT_PATH%>"/>
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="${ctx}/flash/video/videoClient.swf" quality="high" bgcolor="#ffffff" 
				width="100%" height="100%" name="videoClient" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowFullScreen="true"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				FlashVars="room=<%=room%>&loginId=<%=loginId%>&host=<%=host%>&userId=<%=user.getId()%>&ctxPath=<%=VideoConstants.WEBAPP_CONTEXT_PATH%>"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>	
</stc:ifLogin>
</body>
</html>