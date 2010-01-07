<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
  function goBack(){
    window.location.href="index.do";
  }
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">通知内容</div>
	<table width="700px" align="center" cellpadding="3" cellspacing="4">
		<tr>
			<td align="center" style="border-bottom: solid 1px black; font-size:25px; font-weight: bold;">
				${model.notice.title}
			</td>
		</tr>
		<tr>
			<td align="center" style=" font-size:15px; font-weight: bold;">
				发布人：${model.notice.publisher.name }&nbsp;&nbsp;
				发布时间：<s:date name="model.notice.createTime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
		</tr>
		<tr>
			<td align="left" style="padding: 10 30 10 30;" height="150px" valign="top">
				<div style="line-height: 20px; word-spacing: 50px;">
					${model.notice.content}
				</div>
			</td>
		</tr>

		<tr>
			<td align="left" style="border-bottom: solid 1px black; ">
				<div style="padding: 15 30 15 30;font-size:15px; font-weight: bold;">
				相关附件文档：
				<c:if test="${!empty model.notice.att}">
					<a href="${ctx}${model.notice.att}" target="_blank">点击下载</a>
				</c:if>
				</div>
			</td>
		</tr>
	</table>
</div>
<div align="center" style="padding-top: 10px;">
	<input type="button" value="返回" cssClass="button" onclick="goBack()"/>
</div>
</body>
</html>