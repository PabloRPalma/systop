<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>部门接收短信</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<table width="250" align="center">
	<c:forEach var="receive" items="${items}" begin="0" end="4">
		<tr>
			<td width="130" id="mobileNum"><img src="${ctx}/images/exticons/bit.png" align="middle">&nbsp;${receive.mobileNum}</td>
			<td width="150" id="content"><SPAN style="width:100px;overflow:hidden;text-overflow:ellipsis;"><NOBR>${receive.content}</NOBR></SPAN></td>
	      	<td width="20"><a href="${ctx}/smsreceive/index.do" target="main" style="color:red;">new</a></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>