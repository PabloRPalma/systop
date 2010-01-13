<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<script type="text/javascript">
  var faCaseId = null;
  var ChooseSendTypeWindow = new Ext.Window({
	  el:'chooseSendTypeWindow',
	  width:'630',
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
	  window.location = "${ctx}/task/edit.do?sendTypeId=" + sendtypeId + "&caseId=" + faCaseId + "&isMultipleCase=${isMultipleCase}&modelId=${param['modelId']}";
	  ChooseSendTypeWindow.hide();
  }
  function showChooseSendTypeWindow(faCaseId){
		this.faCaseId = faCaseId;
		ChooseSendTypeWindow.show();	  
} 
</script>
</head>
<body>
<div id="chooseSendTypeWindow" class="x-hidden">
<div class="x-window-header">选择派遣类别</div>
<div class="x-window-body">
	<table align="center" width="566" cellspacing="6">
	<tr><td>&nbsp;</td></tr>
	<tr><td>&nbsp;</td></tr>
		<c:forEach items="${sendTypes}" var="sendType">
			<tr>
				<td><input onclick="ChooseSendTypeWindow.selectType(${sendType.id})" type="button"
						value="${sendType.name}"
						style="width:555px; height:35px;  border: 0px; background-image: url(${ctx}/images/choosesendtype/01.gif);font-size:16px;color:#000" />
				</td>
			</tr>
		</c:forEach>	
		<tr>
				<td><input type="button" value="其他派遣" onclick="send()"
					style="width:555px; height:35px; border: 0px; background-image: url(${ctx}/images/choosesendtype/01.gif);font-size:16px;color:#000" />
				</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
	</table>
</div>
</div>
</body>
</html>