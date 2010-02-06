<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
  var faCaseId = null;
  var ChooseSendTypeWindow = new Ext.Window({
	  el:'chooseSendTypeWindow',
	  width:'600',
	  layout:'fit',
	  closeAction:'hide',
	  buttonAlign:'center',
	  modal:'true',
	  buttons:[{
		  text:'取消',
		  handler:function(){
		  ChooseSendTypeWindow.hide();
		  }
		  }]
});
  ChooseSendTypeWindow.selectType = function(sendtypeId){
	  Ext.MessageBox.confirm('提示','确定要选择该派遣环节吗？派遣后不能撤销！', function(btn){
	         if (btn == 'yes') {
	    		window.location = "${ctx}/task/edit.do?sendTypeId=" + sendtypeId + "&caseId=" + faCaseId + "&isMultipleCase=${isMultipleCase}&modelId=${modelId}";
	    		ChooseSendTypeWindow.hide();
	    	  };
	    });	  	 
	 
  }
  function showChooseSendTypeWindow(faCaseId,caseTitle){
		this.faCaseId = faCaseId;
		document.getElementById("caseTitle").innerHTML = caseTitle;
		ChooseSendTypeWindow.show();	  
} 
ChooseSendTypeWindow.selectOtherType = function(){	  	 
	  window.location = "${ctx}/task/edit.do?caseId=" + faCaseId + "&isMultipleCase=${isMultipleCase}&modelId=${modelId}";
	  ChooseSendTypeWindow.hide();
}
</script>
<div id="chooseSendTypeWindow" class="x-hidden">
<div class="x-window-header">选择派遣类别</div>
<div class="x-window-body">
<table align="center" width="567" border="0" cellspacing="0"cellpadding="0">
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>事件标题：<s:label id="caseTitle" /></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td style="margin-top: -1;margin-left: -2;"></td>
	</tr>
</table>
<table align="center" width="567" height="199" border="0"	cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table align="center" width="567" height="35" border="0"		cellpadding="0" cellspacing="0">
			<tr><td>&nbsp;</td></tr>
			<c:forEach items="${sendTypes}" var="sendType">
				<tr>
					<td>
					<input onclick="ChooseSendTypeWindow.selectType(${sendType.id})" type="button" value="${sendType.name}"style="width:560px; height:40px;  border: 0px; background-image: url(${ctx}/images/choosesendtype/bg.gif);font-size:12px;color:#000;font-weight:bold" />
					</td>
				</tr>
			</c:forEach>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>			
		</table>
		</td>
</table>
<table align="center" width="567" border="0" cellspacing="0"cellpadding="0">
	<tr>
		<td>
		</td>
	</tr>
</table>
</div>
</div>