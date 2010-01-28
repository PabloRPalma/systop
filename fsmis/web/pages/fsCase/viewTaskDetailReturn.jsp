<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**任务明细实体id*/
	function showReturnWindow(taskDetailId) {
		Ext.Ajax.request({
		    url: '${ctx}/taskdetail/viewTaskDetailById.do',
		    params: { taskDetailId: taskDetailId },
		    success: function(response) {
		    	var jsonResult = Ext.util.JSON.decode(response.responseText);
		        //Ext.Msg.alert('成功', jsonResult.taskTitle);
		        
		      document.getElementById('taskTitle_return').innerHTML =jsonResult.taskTitle;
		    	document.getElementById('deptName_return').innerHTML = jsonResult.deptName;		    	
		    	document.getElementById('returnPeople').innerHTML = jsonResult.returnPeople;
		    	document.getElementById('returnReason').innerHTML = jsonResult.returnReason;		    	
		    },
		    failure: function(response) {
		        Ext.Msg.alert('失败', response.responseText);
		    }
		});
		ReturnWindow.show();
		//document.getElementById('returnPeople').value = userName;
	}

	var ReturnWindow = new Ext.Window( {
		el : 'returnWindow',
		width : 460,
		height : 250,
		layout : 'fit',
		closeAction : 'hide',
		buttonAlign : 'center',
		modal : 'true',
		buttons : [ {
			text : '关闭',
			handler : function() {
			ReturnWindow.hide();
				//ResultWindow.clear();
			}
		} ]
	});
</script>
<!-- 查看任务明细处理结果 -->
<div id="returnWindow" class="x-hidden">
<div class="x-window-header">任务退回结果</div>
<div class="x-window-body">
<div>
<table align="left" class="mytable" width="500px">
	<tr>
		<td>
		<table width="500px" align="center">
		<tr></tr>
		<tr>
				<td align="right" width="100">任务标题：</td>
				<td width="450"><div id="taskTitle_return"></div></td>
			</tr>
			<tr>
				<td align="right" width="100">处理部门：</td>
				<td width="450"><div id="deptName_return"></div></td>
			</tr>
			<tr>
				<td align="right" >负责人：</td>
				<td align="left" ><div id="returnPeople"></div></td>
			</tr>				
			<tr>
				<td align="right" valign="top">退回原因：</td>
				<td align="left">
				<div style="overflow: auto; height: 60px;" id="returnReason"></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
</div>
</div>

