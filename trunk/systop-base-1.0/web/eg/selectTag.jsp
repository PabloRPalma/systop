<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body style="font:12px">

<table style="border:1px #000000 dashed; ">
<tr><td>普通,指定宽度:</td><td>
	<systop:catalogSelector catalog="test" style="width:200px" name="operation" templateDir="web:templates"/>
	</td></tr>
<tr><td>普通,指定缺省选项</td><td>
	<systop:catalogSelector catalog="test" name="operation" templateDir="web:templates" defaultValue="2"/>
	</td></tr>
<tr><td>缺省值来自I18N:</td><td>
	<systop:catalogSelector catalog="test" name="operation" templateDir="web:templates" 
	  defaultValue="1" defaultLabel="global.all"/>
	</td></tr>
<tr><td>被选中一个:
	<c:set value="4" var="x" scope="request"/>
	</td><td>
	<systop:catalogSelector catalog="test" name="x" templateDir="web:templates" defaultValue="" defaultLabel="All"/>
	</td></tr>
<tr><td>带有缺省值: </td><td>
	<systop:catalogSelector catalog="test" name="operation" templateDir="web:templates" defaultValue="" defaultLabel="All"/>
	</td></tr>
<tr><td>多选：
	</td><td>
		<systop:catalogSelector catalog="test" name="operation" templateDir="web:templates" defaultValue="" defaultLabel="All"
 multiLine="true"/>
	</td></tr>
<tr><td>多选，指定显示行数:</td><td>
	<systop:catalogSelector catalog="test" name="d" templateDir="web:templates"
 size="3" multiLine="true"/>
	</td></tr>
<tr><td>
	多选，已经选中多个:<%
 request.setAttribute("y", new String[]{"1", "2"});
 %></td><td>
		<systop:catalogSelector catalog="test" name="y" templateDir="web:templates" size="6" multiLine="true"/>
	</td></tr>
<tr><td>响应事件:</td><td>
<systop:catalogSelector onchange="javascript:alert(this.value)" catalog="test" style="width:200px" name="operation" templateDir="web:templates"/>
</td></tr>
<tr><td>checkbox:</td><td>
<systop:catalogSelector onclick="javascript:alert(this.value)" 
   catalog="test" 
   name="y" 
   templateDir="web:templates" templateName="catalogCheckbox" multiLine="true"/>
</td></tr>
<tr><td>radio:</td><td>
<systop:catalogSelector onclick="javascript:alert(this.value)" 
   catalog="test" 
   name="x" 
   templateDir="web:templates" templateName="catalogRadio" multiLine="false"/></td></tr>
<tr><td>radio 带缺省选项:</td><td>
<systop:catalogSelector catalog="test" 
   name="t" 
   templateDir="web:templates" templateName="catalogRadio" multiLine="true"
    defaultValue="3"/>
</td></tr>
<tr><td></td><td></td></tr>
</table>
</body>
</html>