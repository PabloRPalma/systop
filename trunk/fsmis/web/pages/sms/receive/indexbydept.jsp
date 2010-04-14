<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>部门接收短信</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">最近收到的短信</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="10,20,50,100" 
	editable="false"
	sortable="true" 
	rowsDisplayed="10" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="230px"
	minHeight="230"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_no" value="${GLOBALROWCOUNT}" title="No." style="text-align:center" sortable="false"/>
		<ec:column width="120" property="receiveTime" title="系统接收时间"
			cell="date" format="yyyy-MM-dd HH:mm" style="text-align:center" sortable="false"/>
		<ec:column width="100" property="content" title="短信内容" ellipsis="true" style="cursor:hand" sortable="false" onclick="viewSmsInfo(${item.id})"/>
		<ec:column width="40" property="_stuts" title="状态" style="text-align:center;cursor:hand;" sortable="false" viewsAllowed="html" onclick="viewSmsInfo(${item.id})">
			<s:if test="#attr.item.isNew eq 1"><font color="red">NEW</font></s:if>
			<s:else><font color="green">已读</font></s:else>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
<!-- 查看短信内容 -->
<div id="smsWindow" class="x-hidden">
<div class="x-window-header">短信内容</div>
<div class="x-window-body">
	<table width="600">
	  <tr>
	    <td align="right" width="100" height="28"><font color="green">发送号码：</font></td>
		<td align="left" id="smsNum"></td>
	  </tr>
	  <tr>
	    <td align="right" width="100" height="28"><font color="green">接收时间：</font></td>
		<td align="left" id="receiveTime"></td>
	  </tr>
	  <tr>
	    <td align="right" width="100" height="28"><font color="green">短信内容：</font></td>
		<td align="left" id="smsContent"></td>
	  </tr>
	</table>
 </div>
</div>
<!-- 查看短信内容 -->
<script type="text/javascript">
  var SmsWindow = new Ext.Window({
      el: 'smsWindow',
      width: 600,
      height: 320,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'false',
      buttons:[
        {text:'关闭',
        	handler:function(){
        	SmsWindow.hide();
        	window.location = '${ctx}/smsreceive/indexByDept.do';
        }
      }]
  });
  function viewSmsInfo(smsId) {
	  $.ajax({
		     url: '${ctx}/smsreceive/viewSmsReceiveInfo.do',
		     type: 'post',
			 dataType: 'json',
			 data: {smsReceiveId : smsId},
			 success: function(rst, textStatus){
				 if(rst.mobileNum != null) {
					 document.getElementById("smsNum").innerHTML = rst.mobileNum;
				 }
				 if(rst.receiveTime != null) {
					 document.getElementById("receiveTime").innerHTML = rst.receiveTime;
				 }
				 if(rst.content != null) {
					 document.getElementById("smsContent").innerHTML = rst.content;
				 }
				 SmsWindow.show();
			 }
  	  }); 
  }
</script>
</body>
</html>