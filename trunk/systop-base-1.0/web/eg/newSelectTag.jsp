<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>test new select tag</title>
</head>
<body>
<systop:systopSelector catalog="sys_admin" name="admin" defaultValue="2"/><br>
<systop:systopSelector catalog="test" name="test1" defaultLabel="all" onchange="javascript:alert(this.value)"></systop:systopSelector><br>
<systop:systopSelector catalog="test" name="test1" multiLine="true"  onclick="javascript:alert(this.value)"></systop:systopSelector><br>
<systop:systopSelector catalog="test" name="test2" templateName="catalogRadio" onclick="javascript:alert(this.value)"></systop:systopSelector><br>
<systop:systopSelector catalog="test" name="test3" templateName="catalogCheckbox" onclick="javascript:alert(this.value)"></systop:systopSelector><br>
</body>
</html>