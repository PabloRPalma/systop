<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/yahooUi.jsp" %>
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/style.css'/>">
<title>Insert title here</title>
</head>
<body>
<cms:folder id="folder1" folderId="33554432" openPage="eg/viewContnet.jsp" templateName="folder" templateDir="web:templates" theme="simple"/>
<cms:folder id="myfolder" folderId="33554432" openPage="eg/viewContnet.jsp" templateName="folder" templateDir="web:templates" theme="yui"></cms:folder>
</body>
</html>