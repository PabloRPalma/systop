<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<b>查询条件：</b><br>
<b>发震时刻:</b><%=request.getParameter("model.startDate")%>至<%=request.getParameter("model.endDate")%><br>
<b>震级:</b><%=request.getParameter("model.startM")%>至<%=request.getParameter("model.endM")%><br>
<b>纬度:</b><%=request.getParameter("model.startLat")%>至<%=request.getParameter("model.endLat")%><br>
<b>经度:</b><%=request.getParameter("model.startLon")%>至<%=request.getParameter("model.endLon")%><br>
<b>也可以用JSP表达式方法得到:</b><br>
<b>发震时刻:</b>${param['model.startDate']}至${param['model.endDate']}<br>
<b>震级:</b>${param['model.startM']}至${param['model.endM']}<br>
<b>纬度:</b>${param['model.startLat']}至${param['model.endLat']}<br>
<b>经度:</b>${param['model.startLon']}至${param['model.endLon']}<br>

</body>
</html>