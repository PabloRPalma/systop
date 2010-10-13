<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/meta.jsp" %>
<%
String xml = (String) request.getAttribute("xmlUrl");
if(xml != null) {
  System.out.println(xml);
}
%>
<title></title>
</head>
<body>
台站信息访问地址：${xmlUrl}
</body>
</html>