<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<LINK href="${ctx}/styles/style.css" type='text/css' rel='stylesheet'>
<style type="text/css">
#mytable {border:1px solid #A6C9E2;width: 90%; border-collapse: collapse; }
#mytable th{border:1px solid #A6C9E2;width: 150;}
#mytable td{border:1px solid #A6C9E2;width: 150;height: 24;}
</style>
</head>
<body>
  <table id="mytable" width="90%" align="center" style="text-align:center">
	<tr bgcolor="#DDECF7">
		<td>数据集</td>
		<td>相应链接</td>
		<td>元数据</td>
	</tr>
	<c:forEach items="${items}" var="item">
	<tr>
		<td>${item.metaSet}</td>
		<td>
			<a href="${ctx}/quake/index.do?type=${item.type}" target="_blank"><font color="blue">链接</font></a>
		</td>
		<td width="100px">
			<a href="${ctx}/quake/metadata/queryMetadataInfo.do?model.type=${item.type}" target="_blank">查看</a> | 
			<a href="${ctx}/quake/metadata/downloadMetadataInfo.do?model.type=${item.type}" target="_blank">下载</a>
		</td>
	</tr>
	</c:forEach>
  </table>
</body>
</html>