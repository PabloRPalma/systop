<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
  var faCaseId = null;
  var showProcessWindow = new Ext.Window({
	  el:'showProcessWindow',
	  width:'670',
	  height:'221',
	  layout:'fit',
	  closeAction:'hide',
	  buttonAlign:'center',
	  resizable:false,	  
	  modal:'true',
	  buttons:[{
		  text:'关闭',
		  handler:function(){
		  showProcessWindow.hide();
		  }
		  }]
});

  function showProcess(status, caseTime, askDate, resultDate){
	  document.getElementById('fsCase').background="${ctx}/pages/assessment/flow/images/blank/index_01.gif";
  	  document.getElementById('fsCase').innerHTML = '事件发生';	  
	  document.getElementById('application').background="${ctx}/pages/assessment/flow/images/blank/index_03.gif";
  	  document.getElementById('application').innerHTML = '申请评估';	 	  
	  document.getElementById('audit').background="${ctx}/pages/assessment/flow/images/blank/index_09.gif";
	  document.getElementById('start').background="${ctx}/pages/assessment/flow/images/blank/index_11.gif";
  	  document.getElementById('start').innerHTML = '启动评估';		  
	  document.getElementById('result').background="${ctx}/pages/assessment/flow/images/blank/index_13.gif";
  	  document.getElementById('result').innerHTML = '上报结果';		  
	  document.getElementById('end').background="${ctx}/pages/assessment/flow/images/blank/index_15.gif";
  	  document.getElementById('failed').background="${ctx}/pages/assessment/flow/images/color/index_08.gif";	
	  document.getElementById('failed').innerHTML="<div style='margin-left: 50px;margin-right: 2px;margin-top:15px;'><font color='red'>未通过</font></div>";
  	  document.getElementById('passing').innerHTML="<div style='margin-bottom:15px;'><font color='green' >通过</font></div>"; 		  	  	  

	    if  (status == "0") {
	    	document.getElementById('fsCase').background="${ctx}/pages/assessment/flow/images/color/index_01.gif";
	    	document.getElementById('fsCase').innerHTML = '事件发生<br><font size="1px">' + caseTime.substring(0, 16) + '</font>';
	    	document.getElementById('application').background="${ctx}/pages/assessment/flow/images/color/index_03.gif";	    	
	    	document.getElementById('application').innerHTML = '申请评估<br><font size="1px">' + askDate.substring(0, 16) + '</font>';		    
	    }
	    if  (status == "1") {
	    	document.getElementById('fsCase').background="${ctx}/pages/assessment/flow/images/color/index_01.gif";
	    	document.getElementById('fsCase').innerHTML = '事件发生<br><font size="1px">' + caseTime.substring(0, 16) + '</font>';
	    	document.getElementById('application').background="${ctx}/pages/assessment/flow/images/color/index_03.gif";	    	
	    	document.getElementById('application').innerHTML = '申请评估<br><font size="1px">' + askDate.substring(0, 16) + '</font>';
	    	document.getElementById('audit').background="${ctx}/pages/assessment/flow/images/color/index_09.gif";
	    	document.getElementById('failed').background="${ctx}/pages/assessment/flow/images/color/index_06.gif";	
	    	document.getElementById('failed').innerHTML="";  		    	    				    
	    }	
	    if  (status == "2") {
	    	document.getElementById('fsCase').background="${ctx}/pages/assessment/flow/images/color/index_01.gif";
	    	document.getElementById('fsCase').innerHTML = '事件发生<br><font size="1px">' + caseTime.substring(0, 16) + '</font>';
	    	document.getElementById('application').background="${ctx}/pages/assessment/flow/images/color/index_03.gif";	    	
	    	document.getElementById('application').innerHTML = '申请评估<br><font size="1px">' + askDate.substring(0, 16) + '</font>';
	    	document.getElementById('audit').background="${ctx}/pages/assessment/flow/images/color/index_09.gif";
	    	document.getElementById('passing').innerHTML=""; 	    		    				    
	    }	
	    if  (status == "3") {
	    	document.getElementById('fsCase').background="${ctx}/pages/assessment/flow/images/color/index_01.gif";
	    	document.getElementById('fsCase').innerHTML = '事件发生<br><font size="1px">' + caseTime.substring(0, 16) + '</font>';
	    	document.getElementById('application').background="${ctx}/pages/assessment/flow/images/color/index_03.gif";	    	
	    	document.getElementById('application').innerHTML = '申请评估<br><font size="1px">' + askDate.substring(0, 16) + '</font>';
	    	document.getElementById('audit').background="${ctx}/pages/assessment/flow/images/color/index_09.gif";
	    	document.getElementById('failed').background="${ctx}/pages/assessment/flow/images/color/index_06.gif";	
	    	document.getElementById('failed').innerHTML="";  		
	    	document.getElementById('start').background="${ctx}/pages/assessment/flow/images/color/index_11.gif";	    				    
	    }	            
	    if (status == "4") {
	    	document.getElementById('fsCase').background="${ctx}/pages/assessment/flow/images/color/index_01.gif";
	    	document.getElementById('fsCase').innerHTML = '事件发生<br><font size="1px">' + caseTime.substring(0, 16) + '</font>';
	    	document.getElementById('application').background="${ctx}/pages/assessment/flow/images/color/index_03.gif";	    	
	    	document.getElementById('application').innerHTML = '申请评估<br><font size="1px">' + askDate.substring(0, 16) + '</font>';
	    	document.getElementById('failed').background="${ctx}/pages/assessment/flow/images/color/index_06.gif";	
	    	document.getElementById('failed').innerHTML="";  			
	    	document.getElementById('audit').background="${ctx}/pages/assessment/flow/images/color/index_09.gif";
	    	document.getElementById('start').background="${ctx}/pages/assessment/flow/images/color/index_11.gif";
	    	document.getElementById('result').background="${ctx}/pages/assessment/flow/images/color/index_13.gif";
	    	document.getElementById('result').innerHTML = '上报结果<br><font size="1px">' + resultDate.substring(0, 16) + '</font>';	    	
	    	document.getElementById('end').background="${ctx}/pages/assessment/flow/images/color/index_15.gif";	 	    		 	    	    	
	    } 
	    
		showProcessWindow.show();	  
} 

</script>
<div id="showProcessWindow" class="x-hidden">
<div class="x-window-header">流程回溯</div>
<div class="x-window-body">
<table width="653" height="95" border="0" align="center" cellpadding="0" cellspacing="0" id="__01" >
    <tr bgcolor="#FFFFFF"><td colspan="10">&nbsp;</td></tr>
    <tr bgcolor="#FFFFFF"><td colspan="10">&nbsp;</td></tr>    
	<tr>
		<td id="fsCase" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_01.gif" width="93" height="48" >
                                事件发生</td>
		<td align="center" background="${ctx}/pages/assessment/flow/images/blank/index_02.gif" width="33" height="48"></td>
		<td id="application" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_03.gif" width="91" height="48">
		           申请评估</td>
		<td align="center" background="${ctx}/pages/assessment/flow/images/blank/index_04.gif" width="88" height="48"></td>
		<td colspan="6" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_05.gif" width="348" height="48"></td>
	</tr>
	<tr>
		<td align="center" background="${ctx}/pages/assessment/flow/images/blank/index_06.gif" width="93" height="47"></td>
		<td align="center" background="${ctx}/pages/assessment/flow/images/blank/index_07.gif" width="33" height="47"></td>
		<td id="failed" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_08.gif" width="91" height="47">
		    <div style="margin-left: 50px;margin-right: 2px;margin-top:15px;"><font color="red">未通过</font></div></td>
		<td id="audit" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_09.gif" width="88" height="47">
		           审核</td>
		<td id="passing" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_10.gif" width="46" height="47">
		    <div style="margin-bottom:15px;"><font color="green" >通过</font></div></td>
		<td id="start" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_11.gif" width="92" height="47">
		          启动评估</td>
		<td align="center" background="${ctx}/pages/assessment/flow/images/blank/index_12.gif" width="35" height="47"></td>
		<td id="result" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_13.gif" width="92" height="47">
		         上报结果</td>
		<td align="center" background="${ctx}/pages/assessment/flow/images/blank/index_14.gif" width="26" height="47"></td>
		<td id="end" align="center" background="${ctx}/pages/assessment/flow/images/blank/index_15.gif" width="57" height="47">
           &nbsp;&nbsp;评估<br>&nbsp;&nbsp;结束</td>
	</tr>
    <tr bgcolor="#FFFFFF"><td colspan="10">&nbsp;</td></tr>
    <tr bgcolor="#FFFFFF"><td colspan="10">&nbsp;</td></tr>    
</table>
</div>
</div>