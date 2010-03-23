<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<fmt:bundle basename="errors">

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="errorPage.title"/></title>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/style.css'/>" />
</head>

<body id="error">
    <div id="page">
        <div id="content" class="clearfix">
            <div id="main">
                <fmt:message key="errorPage.heading"/>
                <%@ include file="/common/messages.jsp" %>
                <pre>
                 <% if (exception != null) {
                    exception.printStackTrace(new java.io.PrintWriter(out));
                 } else if (request.getAttribute("javax.servlet.error.exception") != null) { 
                   ((Exception)request.getAttribute("javax.servlet.error.exception"))
                                           .printStackTrace(new java.io.PrintWriter(out));
                 }%>
                 </pre>
            </div>
        </div>
    </div>
</body>
</html>
</fmt:bundle>