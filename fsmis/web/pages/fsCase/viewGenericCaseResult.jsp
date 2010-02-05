<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**单体实体id*/
	var genericCaseId = null;
	function showGenericWindow(genericCaseId) {
		this.genericCaseId = genericCaseId;
		Ext.Ajax.request({
		    url: '${ctx}/fscase/viewGenericCaseById.do',
		    params: { genericCaseId: genericCaseId },
		    success: function(response) {
		    	var jsonResult = Ext.util.JSON.decode(response.responseText);
		        //Ext.Msg.alert('成功', jsonResult.taskTitle);
		        
		        document.getElementById('title').innerHTML =jsonResult.title;
		    	document.getElementById('caseTypeName').innerHTML = jsonResult.caseTypeName;		    	
		    	document.getElementById('address').innerHTML = jsonResult.address;
		    	document.getElementById('caseTime').innerHTML = jsonResult.caseTime;
		    	document.getElementById('informer').innerHTML = jsonResult.informer;
		    	document.getElementById('informerPhone').innerHTML = jsonResult.informerPhone;
		    	document.getElementById('descn').innerHTML = jsonResult.descn;
		    	
		    },
		    failure: function(response) {
		        Ext.Msg.alert('失败', response.responseText);
		    }
		});
		GenericWindow.show();
		//document.getElementById('returnPeople').value = userName;
	}

	var GenericWindow = new Ext.Window( {
		el : 'genericWindow',
		width : 570,
		height : 350,
		layout : 'fit',
		closeAction : 'hide',
		buttonAlign : 'center',
		modal : 'true',
		buttons : [ {
			text : '关闭',
			handler : function() {
			    GenericWindow.hide();
			}
		} ]
	});
</script>
<!-- 查看任务明细处理结果 -->
<div id="genericWindow" class="x-hidden">
<div class="x-window-header">单体事件信息</div>
<div class="x-window-body">
<div>
<table align="left" class="mytable" width="500px">
	<tr>
		<td>
		<table width="500px" align="center">
		<tr>
				<td align="right" width="100">事件标题：</td>
				<td width="450"><div id="title"></div></td>
			</tr>
			<tr>
				<td align="right" width="100">事件类别：</td>
				<td width="450"><div id="caseTypeName"></div></td>
			</tr>
			<tr>
				<td align="right" >事件地点：</td>
				<td align="left" ><div id="address"></div></td>
			</tr>
			<tr>
				<td align="right">事件时间：</td>
				<td align="left"><div id="caseTime" ></div></td>
			</tr>
			<tr>
				<td align="right">上报人：</td>
				<td align="left">
				<div id="informer"></div>
				</td>
			</tr>
			<tr>
				<td align="right">上报电话：</td>
				<td align="left">
				<div  id="informerPhone"></div>
				</td>
			</tr>
			<tr>
				<td align="right" valign="top">事件描述：</td>
				<td align="left">
				<div  style="overflow: auto; height: 100px;" id="descn"></div>
				</td>
			</tr>
		</table>

		</td>
	</tr>
</table>
</div>
</div>
</div>

