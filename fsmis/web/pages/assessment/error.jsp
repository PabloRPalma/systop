<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<html>
<head>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/meta.jsp"%>

<title>服务器错误</title>
</head>
<body>
<br/>
<br/>
<br/>
<div id="container" style="width:580px;text-align:center">
<div style="margin:auto">
<table border="0" width="590" height="172" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right"><img src="${ctx}/images/500.jpg" width="270" height="145" /></td>
  	<td>
  		<b>错误类型：&nbsp;&nbsp;&nbsp;用户操作错误！</b><br>
		<b>错误原因：<font color="red">
			<c:choose>
				<c:when test="${empty errorMsg}">
					文件类型或大小不符合要求!
				</c:when>
				<c:otherwise>
					${errorMsg}
				</c:otherwise>	
			</c:choose>			
			</font></b><br>			
  	</td>  	
  	<td>
  		<input type="button" value="返回" class="button" onclick="history.go(-1);" />
  	</td>
  </tr>
  </table>
</div>
</div>
</body>
</html>