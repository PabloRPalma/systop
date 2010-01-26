<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>单体核实短信接收</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/ec.jsp"%>
</head>
<body style="padding: 0px 0px 0px 0px; margin: 0px 0px 0px 0px;">

<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="checkedMsgIndex.do" 
	useAjax="false"
	doPreload="false" 
	editable="false"
	sortable="true" 
	rowsDisplayed="2" 
	generateScript="true"
	resizeColWidth="true" 
	classic="false" 
	width="100%" 
	height="135px"
	minHeight="135"
	toolbarContent="navigation|pagejump|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="100" property="mobileNum" title="电话号码" />
		<ec:column width="350" property="content" title="短信内容" />
		<ec:column width="120" property="receiveTime" title="系统接收时间" cell="date" format="yyyy-MM-dd HH:mm"/>
		<ec:column width="85" property="_stuts" title="状态" style="text-align:center">
			<s:if test="#attr.item.isNew eq 1"><font color="red">未读</font></s:if>
			<s:else><font color="green">已读</font></s:else>
		</ec:column>
		<ec:column width="80" property="_option" title="操作" style="text-align:center">
			<a href="#" onClick="viewSmsInfo(${item.id})">查看</a>
		</ec:column>
	</ec:row>
</ec:table>


<!-- 查看短信内容 -->
<div id="smsWindow" class="x-hidden">
<div class="x-window-header">短信内容</div>
<div class="x-window-body">
	<table width="600">
	  <tr>
	    <td align="right" width="100" height="20"><font color="green">发送号码：</font></td>
		<td align="left" id="smsNum"></td>
	  </tr>
	  <tr>
	    <td align="right" width="100" height="20"><font color="green">接收时间：</font></td>
		<td align="left" id="receiveTime"></td>
	  </tr>
	  <tr>
	    <td align="right" width="100" height="24"><font color="green">短信内容：</font></td>
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
      height: 200,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'false',
      buttons:[
        {text:'关闭',
        	handler:function(){
        	SmsWindow.hide();
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