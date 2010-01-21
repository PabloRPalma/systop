<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<script type="text/javascript">
	/**任务明细实体id*/
	var taskDetailId = null;
	function showReturnWindow(taskDetailId,userName,taskTitle){
		this.taskDetailId = taskDetailId;
		ReturnWindow.show();
		document.getElementById('returnPeople').value = userName;
		document.getElementById('taskTitle').innerHTML = taskTitle;
	}
	
	var ReturnWindow = new Ext.Window({
		el:'returnWindow',
		width:500,
		height:270,
		layout:'fit',
		closeAction:'hide',
		buttonAlign:'center',
		modal:'true',
		buttons:[{
			text:'确定',
			handler:function(){
				if(ReturnWindow.check()){
					var returnPeople = document.getElementById('returnPeople').value;
					var returnReason = document.getElementById('returnReason').value;
					$.ajax({
						url:'${ctx}/taskdetail/doReturnTaskDetail.do',
						type:'post',
						dataType:'json',
						data:{'model.id' : taskDetailId,'model.returnPeople':returnPeople,'model.returnReason':returnReason},
						success:function(result,textStatus){
							if(result.result == 'success'){	
								ECSideUtil.reload('ec');
								ReturnWindow.hide();								
								Ext.my().msg('', '您已经成功退回了任务.');														
							}else{
								ReturnWindow.hide();
								alert("退回任务失败,请重试...");								
							}
						},
						failure: function() {
					          Ext.Msg.alert('错误', "退回失败");
					    }
											
					});
					ReturnWindow.hide();
					ReturnWindow.clear();
				}
			}
		},{text:'取消',
			handler:function(){
			ReturnWindow.hide();
			ReturnWindow.clear();
		 }			
		}]
	});
	ReturnWindow.clear = function(){
		document.getElementById('returnReason').value = '';
	}
	ReturnWindow.check = function(){
		var returnPeople = document.getElementById('returnPeople').value;
		var returnReason = document.getElementById('returnReason').value;
		if(returnPeople == null || returnPeople == ''){
			Ext.MessageBox.show({
				title :'提示',
				minWidth:220,
				msg : '请输入负责人姓名!',
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
			return false;
		}
		if(returnReason == null || returnReason == ''){
			Ext.MessageBox.show({
				title:'提示',
				minWidth:220,
				msg:'请填写退回原因!',
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<!-- 退回任务明细 -->
<div id="returnWindow" class="x-hidden">
<div class="x-window-header">退回任务</div>
<div class="x-window-body">
<table align="center" cellspacing="6">
	<tr><td></td><td></td></tr>
	<tr>
		<td  align="right">任务标题：</td>
		<td><div id="taskTitle"></div></td>
	</tr>
	<tr>
		<td align="right">负责人：</td>
		<td><s:textfield name="model.returnPeople" id="returnPeople"></s:textfield><font color="red">*</font></td>
	</tr>
	<tr>
		<td align="right">退回原因：</td>
		<td><textarea rows="6" cols="50" style="widows: 300px;" id="returnReason"
			name="model.returnReason"></textarea><font color="red">*</font></td>
	</tr>
</table>
</div>
</div>
</body>
</html>