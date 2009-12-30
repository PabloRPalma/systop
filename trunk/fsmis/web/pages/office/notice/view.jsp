<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>${model.title} 的详细内容 </title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
  //关闭当前页面
  function exit(){
    if(confirm("确定关闭本页面吗？")){
      window.close();
    }
  }
</script>
</head>
<body>
<br>
<div align="center">
	<table width="700px" align="center" cellpadding="3" cellspacing="4">
		<tr>
			<td align="center" style="border-bottom: solid 1px black; font-size:25px; font-weight: bold;">
				${model.title}
			</td>
		</tr>
		<tr>
			<td align="center" style=" font-size:15px; font-weight: bold;">
				发布人：${model.publisher.name }&nbsp;&nbsp;
				发布时间：<s:date name="model.createTime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
		</tr>
		<tr>
			<td align="left" style="padding: 10 30 10 30;" height="150px" valign="top">
				<div style="line-height: 20px; word-spacing: 50px;">
					${model.content}
				</div>
			</td>
		</tr>

		<tr>
			<td align="left" style="border-bottom: solid 1px black; ">
				<div style="padding: 15 30 15 30;font-size:15px; font-weight: bold;">
				相关附件文档：
				<a href="${ctx}${model.att}" target="_blank">点击下载</a>
				</div>
			</td>
		</tr>
	</table>
</div>
<br>
<iframe height="300" align="center" id="iFrame1" name="iFrame1" width="700" onload="this.height=iFrame1.document.body.scrollHeight" frameborder="0" src="${ctx }/office/receiverecord/listbyNoticeId.do?noticeId=${model.id}"></iframe>

<div align="center" style="padding-top: 10px;">
	<input type="button" value="关闭当前页" cssClass="button" onclick="exit();"/>
</div>
</body>
</html>