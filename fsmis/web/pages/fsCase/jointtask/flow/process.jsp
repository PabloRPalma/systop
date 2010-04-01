<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var ProcessWindow = new Ext.Window({
    el: 'processWindow',
    width: 650,
    height: 350,
    layout : 'fit',
    closeAction:'hide',
    buttonAlign:'center',
    modal:'true',
    buttons:[
     {text:'关闭',
         handler:function(){
  	   	ProcessWindow.hide();
         }
     }]
});
function showProcessWindow(status,csStatus) {
	if(status='0'){
		
	}
	if(csStatus='1'){
		 document.getElementById("taskDispatch").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_03.gif";
	}
	if(status='2'){
		
	}
	if(status='4'){
		
	}
	
	ProcessWindow.show();
  }
</script>
<div id="processWindow" class="x-hidden">
<div class="x-window-header">联合整治流程图</div>
<div class="x-window-body">
	<table width="653" height="200" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
	<tr height="105">
	</tr>
	<tr height="48">
		<td width="93"  background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_01.gif" align="center">
			事件发生</td>
		<td><img src="${ctx}/pages/fsCase/jointtask/flow/images/black/index_02.gif" width="33"></td>
		<td id="taskDispatch" width="83"  background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_03.gif" align="center">任务派遣</td>
		<td>
			<img src="${ctx}/pages/fsCase/jointtask/flow/images/black/index_04.gif" width="88" height="48" alt=""></td>
		<td colspan="6" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_05.gif" width="348" ></td>
	</tr>
	<tr>
		<td width="93" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_06.gif"></td>
		<td width="33" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_07.gif"></td>
		<td width="91" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_08.gif" align="right" valign="top">未通过</td>
		<td id="cheeck" width="88" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_09.gif" align="center">审核</td>
		<td width="46" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_10.gif" valign="top" align="center">通过</td>
		<td width="92" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_11.gif" align="center">任务接收</td>
		<td width="35" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_12.gif"></td>
		<td width="92" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_13.gif" align="center">任务处理</td>
		<td width="26" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_14.gif"></td>
		<td width="57" height="47"background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_15.gif"></td>
	</tr>
</table>
</div>
</div>