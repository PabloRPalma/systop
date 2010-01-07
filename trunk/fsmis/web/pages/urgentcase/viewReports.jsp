<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急指挥中心</title>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
	.tdBottom{
		border-bottom:2px solid black;
		padding:8px 5px 6px 5px;
	}
	.tdBottom1{
		border-top:1px solid #c5c5c5;
		padding:2px 2px 2px 2px;
	}
</style>
</head>
<body>
<div class="x-panel">
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center">
	<table width="750px" align="center">
        <tr>
          	<td colspan="4" align="center" style="padding-top: 20px;font-size: 20pt"><b>应急指挥报告</b></td>
  		</tr>
  		<c:forEach var="entry" items="${reportsMap}" varStatus="status">
        <tr align="left"><td colspan="4">&nbsp;</td></tr>
        <tr align="left">
        	<td colspan="4"  class="tdBottom" style="font-family: 黑体;font-size: 15pt">${entry.key }</td>
        </tr>
        <tr><td height="15" colspan="4"></td></tr>
          <c:forEach var="rst" items="${entry.value}" varStatus="status">
            <tr>
				<td align="left" colspan="3">
					&nbsp;&nbsp;&nbsp;&nbsp;${rst.key }：
					<c:if test="${rst.value == 'null'}">
					</c:if>
					<c:if test="${rst.value != 'null'}">
						${rst.value }
					</c:if>
				</td>
			</tr>
			<tr><td colspan="4" class="tdBottom1">&nbsp;</td></tr>
		  </c:forEach>
        </c:forEach>
      </table>
	</div>
</div>
</body>
</html>