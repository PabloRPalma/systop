<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/style.css'/>">
<%@include file="/common/yahooUi.jsp" %>

<title>Insert title here</title>
</head>
<body>
<table width="80%" align="center" border="1">
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>

<tr>
<td>&nbsp;</td>
<td>
<cms:allFolder templateName="allFolder" id="allFolder" templateDir="web:templates" theme="simple"></cms:allFolder>
</td>
<td>&nbsp;</td>
</tr>

<tr>
<td>&nbsp;</td>
<td><cms:moietyArticle id="001" articleId="34209792"></cms:moietyArticle></td>
<td></td>
</tr>
</table>

</body>
</html>