<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp" %>
<title></title>
</head>
<body>
<%@ include file="/common/messages.jsp" %>
<s:form namespace="/security/rbac" action="user/save" validate="true">
	<s:textfield id="model.password" name="model.password"/><br/>
	<s:submit/>
</s:form>
</body>
</html>