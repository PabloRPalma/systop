<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
  var faCaseId = null;
  var showProcessWindow = new Ext.Window({
	  el:'showProcessWindow',
	  width:'700',
	  height:'300',
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
<table align="center" width="467" height="260" border="0" cellspacing="0"cellpadding="0">
	<tr>
	<td colspan="9"></td>
	</tr>
	<tr><!-- 0未派遣1已派遣2已处理3回退4已核实完成5忽略6核实不过-->
		<td class="left">新事件</td>
		<td class="left">>>>>>></td>
		<td class="left">已派遣</td>
		<td class="left">>>>>>></td>
		<td class="left">处理中</td>
		<td class="left">>>>>>></td>
		<td class="left">已处理</td>
		<td class="left">>>>>>></td>
		<td class="left">已核实结案</td>
	</tr>					
	<tr>
		<td style="margin-top: -1;margin-left: -2;"></td>
	</tr>
</table>
</div>
</div>