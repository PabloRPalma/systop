<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**任务明细实体id*/
	var taskDetailId = null;
	function showResultWindow(taskDetailId) {
		this.taskDetailId = taskDetailId;
		Ext.Ajax.request({
		    url: '${ctx}/taskdetail/viewTaskDetailById.do',
		    params: { taskDetailId: taskDetailId },
		    success: function(response) {
		    	var jsonResult = Ext.util.JSON.decode(response.responseText);
		        //Ext.Msg.alert('成功', jsonResult.taskTitle);
		        
		        document.getElementById('taskTitle').innerHTML =jsonResult.taskTitle;
		    	document.getElementById('deptName').innerHTML = jsonResult.deptName;		    	
		    	document.getElementById('inputer').innerHTML = jsonResult.inputer;
		    	document.getElementById('processor').innerHTML = jsonResult.processor;
		    	document.getElementById('basis').innerHTML = jsonResult.basis;
		    	document.getElementById('result').innerHTML = jsonResult.result;
		    	document.getElementById('process').innerHTML = jsonResult.process;
		    },
		    failure: function(response) {
		        Ext.Msg.alert('失败', response.responseText);
		    }
		});
		ResultWindow.show();
		//document.getElementById('returnPeople').value = userName;
	}

	var ResultWindow = new Ext.Window( {
		el : 'resultWindow',
		width : 570,
		height : 350,
		layout : 'fit',
		closeAction : 'hide',
		buttonAlign : 'center',
		modal : 'true',
		buttons : [ {
			text : '关闭',
			handler : function() {
				ResultWindow.hide();
				//ResultWindow.clear();
			}
		} ]
	});
</script>
<!-- 查看任务明细处理结果 -->
<div id="resultWindow" class="x-hidden">
<div class="x-window-header">任务处理结果</div>
<div class="x-window-body">
<div>
<table align="left" class="mytable" width="500px">
	<tr>
		<td>
		<table width="500px" align="center">
		<tr>
				<td align="right" width="100">任务标题：</td>
				<td width="450"><div id="taskTitle"></div></td>
			</tr>
			<tr>
				<td align="right" width="100">处理部门：</td>
				<td width="450"><div id="deptName"></div></td>
			</tr>
			<tr>
				<td align="right" >填写人：</td>
				<td align="left" ><div id="inputer"></div></td>
			</tr>
			<tr>
				<td align="right">处理人：</td>
				<td align="left"><div id="processor" ></div></td>
			</tr>
			<tr>
				<td align="right">处理过程：</td>
				<td align="left">
				<div style="overflow: auto;" id="process"></div>
				</td>
			</tr>
			<tr>
				<td align="right">处理依据：</td>
				<td align="left">
				<div style="overflow: auto;" id="basis"></div>
				</td>
			</tr>
			<tr>
				<td align="right">处理结果：</td>
				<td align="left">
				<div style="overflow: auto;" id="result"></div>
				</td>
			</tr>
		</table>

		</td>
	</tr>
</table>
</div>
</div>
</div>

