<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
  var faCaseId = null;
  var showProcessWindow = new Ext.Window({
	  el:'showProcessWindow',
	  width:'710',
	  layout:'fit',
	  closeAction:'hide',
	  buttonAlign:'center',
	  modal:'true',
	  buttons:[{
		  text:'关闭',
		  handler:function(){
		  showProcessWindow.hide();
		  }
		  }]
});

  function showProcess(status,i,isSubmited,caseTime,closedTime,dispatchTime,closedTaskTime,isMultiple){
	  this.status = status;
	  this.i = i;
	  this.isSubmited = isSubmited;
	  document.getElementById("fsCase").innerHTML = '事件发生<br><font size="1px">' + caseTime.substring(0, 16) + '</font>';
	  if(isMultiple =='0'){ //单体事件
		if(status == '1'){
		  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";	
		  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
		}
		if(i == '1'){
		  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
		  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
		  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";	
	    }
		if(status == '2'){
		  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
		  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
		  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";
		  document.getElementById("status3").background = "${ctx}/pages/fsCase/flow/images/color/flow_15.gif";
		  document.getElementById("status3").innerHTML = '任务处理<br><font size="1px">' + closedTaskTime.substring(0, 16) + '</font>';	
		}
		if(status == '3'){
		  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
		  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
		  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";
		  document.getElementById("status4").background = "${ctx}/pages/fsCase/flow/images/color/flow_05.gif";	
		  document.getElementById("status4").innerHTML = '任务退回<br><font size="1px">' + closedTime.substring(0, 16) + '</font>'; 
		}
		if(status == '4'){
		  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
		  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
		  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";
		  document.getElementById("status3").background = "${ctx}/pages/fsCase/flow/images/color/flow_15.gif";
		  document.getElementById("status3").innerHTML = '任务处理<br><font size="1px">' + closedTaskTime.substring(0, 16) + '</font>';
		  document.getElementById("status5").background = "${ctx}/pages/fsCase/flow/images/color/flow_17.gif";
		  document.getElementById("status5").innerHTML = '核实通过<br><font size="1px">' + closedTime.substring(0, 10) + '</font>'; 
		  document.getElementById("status6").background = "${ctx}/pages/fsCase/flow/images/color/flow_19.gif";	
		}
		if(status == '6'){
		  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
		  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
		  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";
		  document.getElementById("status3").background = "${ctx}/pages/fsCase/flow/images/color/flow_15.gif";
		  document.getElementById("status3").innerHTML = '任务处理<br><font size="1px">' + closedTaskTime.substring(0, 16) + '</font>';
		  document.getElementById("status5").background = "${ctx}/pages/fsCase/flow/images/color/flow_17.gif";
		  document.getElementById("status7").background = "${ctx}/pages/fsCase/flow/images/blank/flow_23.gif";	
		  document.getElementById("status7").innerHTML = '核实未通过';
		}
		if(isSubmited == '1'){
		  document.getElementById("status8").background = "${ctx}/pages/fsCase/flow/images/color/flow_01.gif";	
		}
	  }else{//多体事件
		  if(status == '0'){
			  document.getElementById("status5").background = "${ctx}/pages/fsCase/flow/images/blank/flow_19.gif";
			  document.getElementById("status5").innerHTML = '案件<br>结束';
			  document.getElementById("arrow").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").innerHTML = "";
			}
		  if(status == '1'){
			  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";	
			  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
			  document.getElementById("status5").background = "${ctx}/pages/fsCase/flow/images/blank/flow_19.gif";
			  document.getElementById("status5").innerHTML = '案件<br>结束';
			  document.getElementById("arrow").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").innerHTML = "";
			}
			if(i == '1'){
			  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
			  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
			  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";	
			  document.getElementById("status5").background = "${ctx}/pages/fsCase/flow/images/blank/flow_19.gif";
			  document.getElementById("status5").innerHTML = '案件<br>结束';
			  document.getElementById("arrow").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").innerHTML = "";
			}
			if(status == '2'){
			  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
			  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
			  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";
			  document.getElementById("status3").background = "${ctx}/pages/fsCase/flow/images/color/flow_15.gif";
			  document.getElementById("status3").innerHTML = '任务处理<br><font size="1px">' + closedTaskTime.substring(0, 16) + '</font>';	
			  document.getElementById("status5").background = "${ctx}/pages/fsCase/flow/images/color/flow_19.gif";
			  document.getElementById("status5").innerHTML = '案件<br>结束';
			  document.getElementById("arrow").background = "${ctx}/pages/fsCase/flow/images/color/flow_22.gif";
			  document.getElementById("status6").background = "${ctx}/pages/fsCase/flow/images/color/flow_22.gif";
			  document.getElementById("status6").innerHTML = "";
			}
			if(status == '3'){
			  document.getElementById("status1").background = "${ctx}/pages/fsCase/flow/images/color/flow_11.gif";
			  document.getElementById("status1").innerHTML = '任务派遣<br><font size="1px">' + dispatchTime.substring(0, 16) + '</font>';
			  document.getElementById("status2").background = "${ctx}/pages/fsCase/flow/images/color/flow_13.gif";
			  document.getElementById("status4").background = "${ctx}/pages/fsCase/flow/images/color/flow_05.gif";	
			  document.getElementById("status4").innerHTML = '任务退回<br><font size="1px">' + closedTime.substring(0, 16) + '</font>'; 
			  document.getElementById("status5").background = "${ctx}/pages/fsCase/flow/images/blank/flow_19.gif";
			  document.getElementById("status5").innerHTML = '案件<br>结束';
			  document.getElementById("arrow").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").background = "${ctx}/pages/fsCase/flow/images/blank/flow_22.gif";
			  document.getElementById("status6").innerHTML = "";
			}
			if(isSubmited == '1'){
			  document.getElementById("status8").background = "${ctx}/pages/fsCase/flow/images/color/flow_01.gif";	
			}
	  }
	showProcessWindow.show();	  
} 

</script>
<div id="showProcessWindow" class="x-hidden">
<div class="x-window-header">流程回溯</div>
<div class="x-window-body">
<table width="670" height="201" border="0" align="center" cellpadding="0" cellspacing="0" id="__01" style="margin:10px,10px,10px,10px">
	<tr>
		<td align="center" id="status8" background="${ctx}/pages/fsCase/flow/images/blank/flow_01.gif" width="91" height="47">
			上报市级
		</td>
		<td align="center" rowspan="5" background="${ctx}/pages/fsCase/flow/images/blank/flow_02.gif" width="1" height="201">
		</td>
		<td align="center" rowspan="2" background="${ctx}/pages/fsCase/flow/images/blank/flow_03.gif" width="27" height="72">
		</td>
		<td colspan="2" rowspan="2" background="${ctx}/pages/fsCase/flow/images/blank/flow_04.gif" width="120" height="72"></td>
		<td align="center" id="status4" background="${ctx}/pages/fsCase/flow/images/blank/flow_05.gif" width="91" height="47">
			任务退回
		</td>
		<td colspan="6" rowspan="2" background="${ctx}/pages/fsCase/flow/images/blank/flow_06.gif" width="340" height="72"></td>
	</tr>
	<tr>
		<td background="${ctx}/pages/fsCase/flow/images/blank/flow_07.gif" width="91" height="25"></td>
		<td background="${ctx}/pages/fsCase/flow/images/blank/flow_08.gif" width="91" height="25"></td>
	</tr>
	<tr>
		<td align="center" id="fsCase" background="${ctx}/pages/fsCase/flow/images/color/flow_09.gif" width="91" height="47">
			事件发生
		</td>
		<td background="${ctx}/pages/fsCase/flow/images/blank/flow_10.gif" width="27" height="47"></td>
		<td align="center" id="status1" background="${ctx}/pages/fsCase/flow/images/blank/flow_11.gif" width="92" height="47">
			任务派遣
		</td>
		<td background="${ctx}/pages/fsCase/flow/images/blank/flow_12.gif" width="28" height="47">
		</td>
		<td align="center" id="status2" background="${ctx}/pages/fsCase/flow/images/blank/flow_13.gif" width="91" height="47">
			任务接收
		</td>
		<td background="${ctx}/pages/fsCase/flow/images/blank/flow_14.gif" width="30" height="47">
		</td>
		<td align="center" id="status3" background="${ctx}/pages/fsCase/flow/images/blank/flow_15.gif" width="92" height="47">
			任务处理
		</td>
		<td background="${ctx}/pages/fsCase/flow/images/blank/flow_16.gif" width="32" height="47"></td>
		<td align="center" id="status5" bgcolor="#FFFFFF" style="background-repeat:no-repeat" background="${ctx}/pages/fsCase/flow/images/blank/flow_17.gif" width="91" height="47">
			核实
		</td>
		<td id="arrow" background="${ctx}/pages/fsCase/flow/images/blank/flow_18.gif" width="42" height="47"></td>
		<td align="right" id="status6" background="${ctx}/pages/fsCase/flow/images/blank/flow_19.gif" width="53" height="47">
			案件<br>
			结束
		</td>
	</tr>
    <tr>
		<td rowspan="2">
			<img src="${ctx}/pages/fsCase/flow/images/blank/flow_20.gif" width="91" height="82" ></td>
		<td colspan="8">
			<img src="${ctx}/pages/fsCase/flow/images/blank/flow_21.gif" width="483" height="1" ></td>
		<td colspan="2" rowspan="2">
			<img src="${ctx}/pages/fsCase/flow/images/blank/flow_22.gif" width="95" height="82" ></td>
	</tr>
	<tr>
		<td colspan="8" id="status7" background="${ctx}/pages/fsCase/flow/images/blank/flow_24.gif" width="483" height="81" >
		</td>
	</tr>
</table>
</div>
</div>