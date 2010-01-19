<?xml version="1.0" encoding="utf-8"?>
<%@ page contentType="text/vnd.wap.wml; charset=utf-8" language="java" %>
<%@ include file="common.jsp" %>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.3//EN" "http://www.wapforum.org/DTD/wml13.dtd" >
<wml>
<card title="食品安全系统WAP举报平台 > 企业列表">
<p>
输入企业关键字：
<input  name="queryCorpName" type="text" value="${queryCorpName}"/>
<anchor>
	查询
	<go href="${ctx}/wapcorp/index.do" method="post">
		<postfield  name="queryCorpName" value="$(queryCorpName)"/>
	</go>
</anchor>
<table columns="2">
	<c:forEach items="${companylist}" varStatus="status" var="cm">
		<tr>
			<td>${status.index+beginIndex}</td>
			<td>
				<a href="${ctx}/wapcorp/view.do?companyId=${cm.id}">${cm.name}</a>
			</td>			
		</tr>
	</c:forEach>
</table>
<table columns="3">
<tr>
	<td>
		<anchor>
			返回首页
			<go href="${ctx}/wap/index.do"></go>			
		</anchor>
	</td>
	<td>
		共${pageCount}页
		第${pageCompany+1}页
	</td>	
	<td>		
		<c:choose>
			<c:when test="${pageCount eq 0}">
				未查到符合条件的数据！
			</c:when>			
			<c:when test="${pageSize gt pageCount}">
				<c:if test="${pageCompany > 0}">
					<anchor>
						第一页
						<go href="${ctx}/wapcorp/index.do"  method="post">
							<postfield  name="queryCorpName" value="$(queryCorpName)"/>
							<postfield name="pageCompany" value="0"/>
						</go>
					</anchor>
					<anchor>
						上一页
						<go href="${ctx}/wapcorp/index.do"  method="post">
							<postfield  name="queryCorpName" value="$(queryCorpName)"/>
							<postfield name="pageCompany" value="${pageCompany-1}"/>
						</go>
					</anchor>
				</c:if>
				<c:if test="${pageCompany < pageCount-1}">
					<anchor>
						下一页
						<go href="${ctx}/wapcorp/index.do" method="post">
							<postfield  name="queryCorpName" value="$(queryCorpName)"/>
							<postfield name="pageCompany" value="${pageCompany+1}"/>
						</go>
					</anchor>
					<anchor>
						最后一页
						<go href="${ctx}/wapcorp/index.do" method="post">
							<postfield  name="queryCorpName" value="$(queryCorpName)"/>
							<postfield name="pageCompany" value="${pageCount-1}"/>
						</go>
					</anchor>
				</c:if>			
			</c:when>
		</c:choose>		
	</td>
</tr>
</table>
</p>
</card>
</wml>