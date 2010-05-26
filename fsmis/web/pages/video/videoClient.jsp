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
<title></title>
<script src="AC_OETags.js" language="javascript"></script>
<script src="history/history.js" language="javascript"></script>
</head>
<body>
<stc:ifNotLogin>
   请登录。
</stc:ifNotLogin>
<stc:ifLogin>
<%
String role = "NONE";
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
String appUrl = request.getRequestURL().toString();

//System.out.print("url-------->"+appUrl+"\n");
String host = appUrl.substring("http://".length());
int index = 0;
if(host.indexOf(":") > 0) {
  host = host.substring(0, host.indexOf(":"));
} else if(host.indexOf("/") > 0 ) {
  host = host.substring(0, host.indexOf("/"));
}
%>
<stc:role ifAnyGranted="ROLE_CITY,ROLE_COUNTY">
	<%role = "ROLE_CREATE"; %>	
	<%System.out.println(role); %>
</stc:role>

<script src="AC_OETags.js" language="javascript"></script>

<!--  BEGIN Browser History required section -->
<script src="history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
body { margin: 0px; overflow:hidden }
</style>
<script language="JavaScript" type="text/javascript">
<!--
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 9;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 124;
// -----------------------------------------------------------------------------
// -->
</script>
</head>

<body scroll="no">
<script language="JavaScript" type="text/javascript">
<!--
// Version check for the Flash Player that has the ability to start Player Product Install (6.0r65)
var hasProductInstall = DetectFlashVer(6, 0, 65);

// Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if ( hasProductInstall && !hasRequestedVersion ) {
	// DO NOT MODIFY THE FOLLOWING FOUR LINES
	// Location visited after installation is complete if installation is required
	var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
	var MMredirectURL = encodeURI(window.location);
    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
    var MMdoctitle = document.title;

    
   	AC_FL_RunContent(
		"src", "playerProductInstall",
		"FlashVars", "appUrl=<%=appUrl%>&userId=<%=user.getId()%>&ctxPath=<%=VideoConstants.WEBAPP_CONTEXT_PATH%>&role=<%=role%>",
		"width", "100%",
		"height", "100%",
		"align", "middle",
		"id", "videoClient",
		"quality", "high",
		"bgcolor", "#869ca7",
		"name", "videoClient",
		"allowFullScreen","true",
		"allowScriptAccess","sameDomain",
		"type", "application/x-shockwave-flash",
		"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
} else if (hasRequestedVersion) {
	// if we've detected an acceptable version
	// embed the Flash Content SWF when all tests are passed
	AC_FL_RunContent(
			"src", "videoClient",
			"FlashVars", "appUrl=<%=appUrl%>&userId=<%=user.getId()%>&ctxPath=<%=VideoConstants.WEBAPP_CONTEXT_PATH%>&role=<%=role%>",
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "videoClient",
			"quality", "high",
			"bgcolor", "#869ca7",
			"name", "videoClient",
			"allowFullScreen","true",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
  } else {  // flash is too old or we can't detect the plugin
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    //document.write(alternateContent);  // insert non-flash content
  }
// -->
</script>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="cnhrClient" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="${ctx}/flash/video/videoClient.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="FlashVars" value="room=<%=room%>&loginId=<%=loginId%>&host=<%=host%>&userId=<%=user.getId()%>&ctxPath=<%=VideoConstants.WEBAPP_CONTEXT_PATH%>&role=<%=role%>"/>
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
</noscript>
</stc:ifLogin>
</body>
</html>