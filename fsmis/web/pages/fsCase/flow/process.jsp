<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
  var faCaseId = null;
  var showProcessWindow = new Ext.Window({
	  el:'showProcessWindow',
	  width:'685',
	  height:'221',
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

  function showProcess(status){
		
		showProcessWindow.show();	  
} 

</script>
<div id="showProcessWindow" class="x-hidden">
<div class="x-window-header">流程回溯</div>
<div class="x-window-body">
<table width="670" height="201" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
	<tr>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_01.gif" width="91" height="47">
			上报市级
		</td>
		<td align="center" rowspan="5" background="${ctx}/fscase/flow/images/blank/flow_02.gif" width="1" height="201">
		</td>
		<td align="center" rowspan="2" background="${ctx}/fscase/flow/images/blank/flow_03.gif" width="27" height="72">
		</td>
		<td colspan="2" rowspan="2" background="${ctx}/fscase/flow/images/blank/flow_04.gif" width="120" height="72"></td>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_05.gif" width="91" height="47">
			任务退回
		</td>
		<td colspan="6" rowspan="2" background="${ctx}/fscase/flow/images/blank/flow_06.gif" width="340" height="72"></td>
	</tr>
	<tr>
		<td background="${ctx}/fscase/flow/images/blank/flow_07.gif" width="91" height="25"></td>
		<td background="${ctx}/fscase/flow/images/blank/flow_08.gif" width="91" height="25"></td>
	</tr>
	<tr>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_09.gif" width="91" height="47">
			事件发生
		</td>
		<td background="${ctx}/fscase/flow/images/blank/flow_10.gif" width="27" height="47"></td>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_11.gif" width="92" height="47">
			任务派遣
		</td>
		<td background="${ctx}/fscase/flow/images/blank/flow_12.gif" width="28" height="47">
		</td>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_13.gif" width="91" height="47">
			任务接收
		</td>
		<td background="${ctx}/fscase/flow/images/blank/flow_14.gif" width="30" height="47">
		</td>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_15.gif" width="92" height="47">
			任务处理
		</td>
		<td background="${ctx}/fscase/flow/images/blank/flow_16.gif" width="32" height="47"></td>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_17.gif" width="91" height="47">
			核实通过
		</td>
		<td background="${ctx}/fscase/flow/images/blank/flow_18.gif" width="31" height="47"></td>
		<td align="center" background="${ctx}/fscase/flow/images/blank/flow_19.gif" width="64" height="47">
			案件<br>
			结束
		</td>
	</tr>
	<tr>
		<td rowspan="2" background="${ctx}/fscase/flow/images/blank/flow_20.gif" width="91" height="82" alt="">
		
		</td>
		<td colspan="7" rowspan="2" background="${ctx}/fscase/flow/images/blank/flow_21.gif" width="392" height="82">
		
		</td>
		<td background="${ctx}/fscase/flow/images/blank/flow_22.gif" width="91" height="33">
		
		</td>
		<td colspan="2" rowspan="2" background="${ctx}/fscase/flow/images/blank/flow_23.gif" width="95" height="82">
		
		</td>
	</tr>
	<tr>
		<td align="center" background="${ctx}/fsCase/flow/images/blank/flow_24.gif" width="91" height="49">
			未核实通过
		</td>
	</tr>
</table>
</div>
</div>