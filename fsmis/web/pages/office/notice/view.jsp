<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>${model.title} 的详细内容 </title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
  //关闭当前页面
  function goBack(){
	  window.location.href="index.do";
  }
</script>
<script type="text/javascript">
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        height : 355,
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'basic', title: '通知内容'},
            {contentEl:'received', title: '部门回执'}
        ]
    });
});
</script>
</head>
<body>
<div id="tabs">
<div id="basic" class="x-hide-display">
<div align="center">
	<table width="100%" align="center" cellpadding="3" cellspacing="4">
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
			<td align="left" style="padding: 10 30 10 30;" valign="top">
				<div style="line-height: 20px; word-spacing: 50px; overflow:auto; width:800px; height:210px">
					${model.content}
				</div>
			</td>
		</tr>

		<tr>
			<td align="left" style="border-bottom: solid 1px black; ">
				<div style="padding: 15 30 15 30;font-size:15px; font-weight: bold;">
				相关附件文档：
					<c:if test="${!empty model.att}">
						<a href="${ctx}${model.att}" target="_blank">点击下载</a>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
<div id="received" class="x-hide-display">
<iframe height="360" id="iFrame1" name="iFrame1" width="100%" frameborder="0" src="${ctx }/office/receiverecord/listbyNoticeId.do?noticeId=${model.id}"></iframe>
</div>
</div>
<div align="center" style="padding-top: 10px;">
	<input type="button" value="返回" cssClass="button" onclick="goBack();"/>
</div>
</body>
</html>