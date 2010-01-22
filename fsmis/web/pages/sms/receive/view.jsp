<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看短信</title>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">短信信息</div>
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/smsreceive/index.do">接收短信列表</a>
        </td>
      </tr>
    </table>
</div>
<div class="x-panel-body">
<fieldset style="margin: 30px;"><legend>短信信息</legend>
<table width="100%">
	<tr>
		<td>接收手机号</td>
		<td>${model.mobileNum }</td>
	</tr>
	<tr>
		<td>发送时间</td>
		<td><s:date name="model.sendTime" format="yyyy-MM-dd HH:mm" /></td>
	</tr>
	<tr>
		<td>接收时间</td>
		<td><s:date name="model.receiveTime" format="yyyy-MM-dd HH:mm" />
		</td>
	</tr>
	<tr>
		<td>内容</td>
		<td><div style="overflow: auto;">${model.content }</div></td>
	</tr>
</table>
</fieldset>
</div>
</div>
</body>
</html>
