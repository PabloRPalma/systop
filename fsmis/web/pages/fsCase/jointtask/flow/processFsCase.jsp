<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var ProcessWindow = new Ext.Window({
    el: 'processWindow',
    width: 789,
    height: 230,
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
function showProcessWindow(status,csStatus,isReceive,createDate,dispatchDate,checkDate,viewDate,receiveDate,dealDate) {
	document.getElementById('viewDate').innerHTML = "";
	document.getElementById('dispatchDate').innerHTML = "";
	document.getElementById('checkDate').innerHTML = "";
	document.getElementById('receiveDate').innerHTML = "";
	document.getElementById('dealDate').innerHTML = "";
	document.getElementById("pass").style.display = "block";
	document.getElementById("pass2").style.display = "none";
	document.getElementById("unPass").style.display = "block";
	document.getElementById("unPass2").style.display = "none";
	document.getElementById("taskDispatch").background = "${ctx}/pages/fsCase/jointtask/flow/images/black/index_03.gif";
	document.getElementById("check").background = "${ctx}/pages/fsCase/jointtask/flow/images/black/index_09.gif";
	document.getElementById("receive").background = "${ctx}/pages/fsCase/jointtask/flow/images/black/index_11.gif";
	document.getElementById("deal").background = "${ctx}/pages/fsCase/jointtask/flow/images/black/index_13.gif";
	document.getElementById("end").background = "${ctx}/pages/fsCase/jointtask/flow/images/black/index_15.gif";
	document.getElementById("view").background = "${ctx}/pages/fsCase/jointtask/flow/images/black/index_16.gif";
	//alert(status+"!"+csStatus+"!"+isReceive+"!");
	this.status = status;
	this.csStatus = csStatus;
	this.isReceive = isReceive;
	if(csStatus=='0'){//未派遣
		document.getElementById("unPass").style.display = "none";
		document.getElementById("pass").style.display = "none";
		document.getElementById("pass2").style.display = "block";
		document.getElementById("unPass2").style.display = "block";
	}
	if(csStatus=='1'){//任务派遣
		document.getElementById("taskDispatch").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_03.gif";
		document.getElementById("unPass").style.display = "none";
		document.getElementById("unPass2").style.display = "block";
		document.getElementById("pass").style.display = "none";
		document.getElementById("pass2").style.display = "block";
		document.getElementById('dispatchDate').innerHTML = dispatchDate.substring(0,16);
	}
	
	if(status=='1'){//审核通过
		document.getElementById("check").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_09.gif";
		document.getElementById("unPass").style.display = "none";
		document.getElementById("unPass2").style.display = "block";
		document.getElementById("pass").style.display = "block";
		document.getElementById("pass2").style.display = "none";
		document.getElementById('dispatchDate').innerHTML = dispatchDate.substring(0,16);
		document.getElementById('checkDate').innerHTML = checkDate.substring(0,16);
	}
	if(status=='2'){//审核未通过
		document.getElementById("check").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_09.gif";
		document.getElementById("pass").style.display = "none";
		document.getElementById("pass2").style.display = "block";
		document.getElementById("unPass").style.display = "block";
		document.getElementById("unPass2").style.display = "none";
		document.getElementById('dispatchDate').innerHTML = dispatchDate.substring(0,16);
		document.getElementById('checkDate').innerHTML = checkDate.substring(0,16);
	}
	if(isReceive=='1'){//已查看
		document.getElementById("unPass").style.display = "none";
		document.getElementById("unPass2").style.display = "block";
		document.getElementById("taskDispatch").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_03.gif";
		document.getElementById("check").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_09.gif";
		document.getElementById("view").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_16.gif";
		document.getElementById('viewDate').innerHTML = viewDate.substring(0,16);
		document.getElementById('dispatchDate').innerHTML = dispatchDate.substring(0,16);
		document.getElementById('checkDate').innerHTML = checkDate.substring(0,16);
		}
	if(isReceive=='2'){//已接收
		document.getElementById("unPass").style.display = "none";
		document.getElementById("unPass2").style.display = "block";
		document.getElementById("taskDispatch").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_03.gif";
		document.getElementById("check").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_09.gif";
		document.getElementById("receive").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_11.gif";
		document.getElementById("view").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_16.gif";
		document.getElementById('viewDate').innerHTML = viewDate.substring(0,16);
		document.getElementById('dispatchDate').innerHTML = dispatchDate.substring(0,16);
		document.getElementById('checkDate').innerHTML = checkDate.substring(0,16);
		document.getElementById('receiveDate').innerHTML = receiveDate.substring(0,16);
		}
	if(csStatus=='2'){//已处理
		document.getElementById("unPass").style.display = "none";
		document.getElementById("unPass2").style.display = "block";
		document.getElementById("taskDispatch").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_03.gif";
		document.getElementById("check").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_09.gif";
		document.getElementById("receive").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_11.gif";
		document.getElementById("deal").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_13.gif";
		document.getElementById("end").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_15.gif";
		document.getElementById("view").background = "${ctx}/pages/fsCase/jointtask/flow/images/color/index_16.gif";
		document.getElementById('viewDate').innerHTML = viewDate.substring(0,16);
		document.getElementById('dispatchDate').innerHTML = dispatchDate.substring(0,16);
		document.getElementById('checkDate').innerHTML = checkDate.substring(0,16);
		document.getElementById('receiveDate').innerHTML = receiveDate.substring(0,16);
		document.getElementById('dealDate').innerHTML = dealDate.substring(0,16);
		}
	document.getElementById('createDate').innerHTML = createDate.substring(0,16);
	ProcessWindow.show();
  }
</script>
<div id="processWindow" class="x-hidden">
<div class="x-window-header">联合整治流程图</div>
<div class="x-window-body">
	<table width="780" height="230" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
	<tr height="30">
	<td colspan="12" width="780" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_05.gif"></td>
	</tr>
	<tr height="48">
		<td width="93"  background="${ctx}/pages/fsCase/jointtask/flow/images/color/index_01.gif" align="center">
			事件发生</br>
			<FONT color="blue"><s:label id="createDate"></s:label></FONT></td>
		<td width="33" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_02.gif"></td>
		<td id="taskDispatch" width="83"  background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_03.gif" align="center">任务派遣</br>
			<FONT color="blue"><s:label id="dispatchDate"></s:label></FONT></td>
		<td width="88" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_04.gif"></td>
		<td colspan="8" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_05.gif" width="440" ></td>
	</tr>
	<tr>
		<td width="93" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_06.gif"></td>
		<td width="33" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_07.gif"></td>
		<td id="unPass" width="91" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_08.gif" align="right" valign="top"><font color="red">未通过</font></td>
		<td id="unPass2" width="91" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_06.gif" style=" display: none"></td>
		<td id="check" width="88" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_09.gif" align="center">审核</br>
			<FONT color="blue"><s:label id="checkDate"></s:label></FONT></td>
		<td id="pass" width="46" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/color/index_10.gif" valign="top" align="center"><font color="red">通过</font></td>
		<td id="pass2" width="46" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_10.gif" valign="top" align="center" style=" display: none"></td>
		<td id="view" width="92" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_16.gif" align="center">任务查看</br>
			<FONT color="blue"><s:label id="viewDate"></s:label></FONT></td>
		<td width="35" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_12.gif"></td>
		<td id="receive" width="92" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_11.gif" align="center">任务接收</br>
			<FONT color="blue"><s:label id="receiveDate"></s:label></FONT></td>
		<td width="35" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_12.gif"></td>
		<td id="deal" width="92" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_13.gif" align="center">任务处理</br>
			<FONT color="blue"><s:label id="dealDate"></s:label></FONT></td>
		<td width="26" height="47" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_14.gif"></td>
		<td id="end" width="57" height="47"background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_15.gif"></td>
	</tr>
	<tr height="100">
	<td colspan="12" width="780" background="${ctx}/pages/fsCase/jointtask/flow/images/black/index_05.gif">fffff</td>
	</tr>
</table>
</div>
</div>