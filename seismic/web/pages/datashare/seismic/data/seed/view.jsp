<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/quake.jsp" %>
<title></title>
</head>
<body>
<center><br /><a href="javascript:history.go(-1);"><font color="#00bbe8">返回上一页</font></a></center>
	<s:if test="#attr.items != null">	
	<s:iterator value="#attr.items">
	&nbsp;&nbsp;&nbsp;<s:property value="SeedFile"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="Station"/><p />
		<img  src="${ctx}/quake/seismic/data/seed/jpgUrl.do?url=<s:property value='MapFile'/>"/><p />
	</s:iterator>
	</s:if>
	<s:else>
		<center><span><br/><b>没有任何可以显示的图片</b></span></center>
	</s:else>
	<center><br /><a href="javascript:history.go(-1);"><font color="#00bbe8">返回上一页</font></a></center>
</body>
</html>