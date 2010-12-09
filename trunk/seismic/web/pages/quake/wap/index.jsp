<?xml version="1.0" encoding="utf-8"?>
<%@ page contentType="application/vnd.wap.xhtml+xml;charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"     "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@ include file="common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>测震台网数据服务系统WAP平台</title>
</head>
<body>
<form action="index.do">
<select name="tableName">
<option>请选择地震目录</option>
<c:forEach items="${catalogs}" var="c">
<option value="${c.cltName}" 
<c:if test="${c.cltName eq tableName}">
selected="selected"
</c:if>
>${c.clcName }</option>
</c:forEach>
</select>
 <input  type="submit" value="查询"/>
 </form>
<p>地震目录列表</p>
<table>
	<tr>
		<td>序号</td>
		<td>震时</td>
		<td>纬度</td>
		<td>经度</td>
		<td>震级</td>
		<td>震中</td>
	</tr>
	
	<c:forEach items="${cats}" var="c" varStatus="s">
		<tr>
			<td>${s.index+beginIndex}</td>
			<td>
				${c.EQ_TIME}
			</td>
			<td>
				${c.EPI_LON}
			</td>
			<td>
				${c.EPI_LAT}
			</td>
			<td>
				${c.M}
			</td>
			<td>
			 	<a href="${ctx}/quake/wap/showGis.do?tableName=${tableName}&amp;catalogId=${c.ID}&amp;pageCurrent=${pageCurrent}">${c.LOCATION_CNAME}</a>
			</td>
		</tr>
</c:forEach>
</table>
<table>
<tr>
	<td>
		共${pageCount}页
		<c:choose>
   			<c:when test="${pageCount eq 0 &&pageCurrent eq 1}">   
   				第${pageCurrent-1}页
 			</c:when>
   			<c:otherwise> 
   				第${pageCurrent}页
   			</c:otherwise>
		</c:choose>		
	</td>	
	<td>		
			<c:if test="${pageCount eq 0 &&pageCurrent eq 1}">
				未查到符合条件的数据！
			</c:if>		
				<c:if test="${pageCurrent > 1}">
				<a href="${ctx}/quake/wap/index.do?tableName=${tableName}&amp;pageCurrent=1">首页</a>
				<a href="${ctx}/quake/wap/index.do?tableName=${tableName}&amp;pageCurrent=${pageCurrent-1}">上一页</a>
				</c:if>
				<c:if test="${pageCurrent < 2 && pageCount ne 0}">
				首页&nbsp;
				上一页
				</c:if>
				<c:if test="${pageCurrent < pageCount}">
					<a href="${ctx}/quake/wap/index.do?tableName=${tableName}&amp;pageCurrent=${pageCurrent+1}">下一页</a>
					<a href="${ctx}/quake/wap/index.do?tableName=${tableName}&amp;pageCurrent=${pageCount}">尾页</a>
				</c:if>	
				<c:if test="${pageCurrent == pageCount}">
			下一页&nbsp;
			尾页
			</c:if>					
	</td>
</tr>
</table>
</body>
</html>