<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>查看应急事件</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
#mytable {
	border: 0px solid #A6C9E2;
	margin-left: 0px;
	margin-top: 0px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
}
</style>
</head>
<body>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			height : 380,
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '应急事件'
			}, {
				contentEl : 'hospital',
				title : '周边医院'
			}, {
				contentEl : 'traffic',
				title : '周边交通'
			} ]
		});
	});
</script>
<div class="x-panel">
<div class="x-panel-header">查看应急事件</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="#" onclick="CheckWindow.show()"><img
					src="${ctx}/images/icons/house.gif" /> 审核</a></td>
				<td><span class="ytb-sep"></span></td>
				<td align="right"><a href="${ctx}/urgentcase/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 应急事件列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div id="tabs">
<div id="basic" class="x-hide-display"><s:hidden name="model.id" />
<table id="mytable" height="360" style="margin-top: 5px">
	<tr>
		<td align="right" width="215">事件名称：</td>
		<td align="left" colspan="3"><s:textfield id="title"
			name="model.title" cssStyle="width:500px" cssClass="required" /><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">事发时间：</td>
		<td width="287" align="left"><input type="text" name="model.caseTime" style="width: 148px"
			value='<s:property value="model.caseTime"/>'
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
			class="Wdate" /><font color="red">&nbsp;*</font></td>
		<td width="88" align="right">预案等级：</td>
        <td width="348" align="left">        </td>
	</tr>
	<tr>
		<td align="right">事发地点：</td>
		<td align="left" colspan="3"><s:textfield id="address"
			name="model.address" cssStyle="width:500px" cssClass="required" /><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">波及范围：</td>
		<td align="left" colspan="3"><s:textfield id="caseRange"
			name="model.caseRange" cssStyle="width:500px" /></td>
	</tr>
	<tr>
		<td align="right">受害人数：</td>
		<td align="left"><s:textfield id="harmNum"
			name="model.harmNum" cssStyle="width:148px" /></td>
		<td align="right">死亡人数：</td>
		<td align="left"><s:textfield id="deathNum"
			name="model.deathNum" cssStyle="width:148px" /></td>
	</tr>
	<tr>
		<td align="right">事件报告人：</td>
		<td align="left"><s:textfield id="reporter"
			name="model.reporter" cssStyle="width:148px" cssClass="required" /><font
			color="red">&nbsp;*</font></td>
		<td align="right">报告人电话：</td>
		<td align="left"><s:textfield id="reporterPhone"
			name="model.reporterPhone" cssStyle="width:148px" cssClass="required" /><font
			color="red">&nbsp;*</font></td>
	</tr>
	<tr>
		<td align="right">报告人单位：</td>
		<td align="left" colspan="3"><s:textfield id="reporterUnits"
			name="model.reporterUnits" cssStyle="width:500px" /></td>
	</tr>
	<tr>
		<td align="right">事件描述：</td>
		<td align="left" colspan="3" style="vertical-align: top"><s:textarea id="descn"
			name="model.descn" cssStyle="width:500px; height:170px" cssClass="required" />
			<font color="red">&nbsp;*</font></td>
	</tr>
</table>
</div>
<div id="hospital" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px">
	<tr>
		<td width="15%" align="right">周边医院情况：</td>
		<td align="left" style="vertical-align: top;" width="85%"><s:textarea
			id="hospitalInf" name="model.hospitalInf"
			cssStyle="width:600px; height:330px" /></td>
	</tr>
</table>
</div>
<div id="traffic" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px">
	<tr>
		<td width="15%" align="right">周边交通情况：</td>
		<td align="left" style="vertical-align: top;" width="85%"><s:textarea
			id="trafficInf" name="model.trafficInf"
			cssStyle="width:600px; height:330px" /></td>
	</tr>
</table>
</div>
</div>
</div>
<!-- 审核意见 -->
<script type="text/javascript">
  var CheckWindow = new Ext.Window({
      el: 'checkWindow',
      width: 400,
      height: 250,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'true',
      buttons:[{
        text:'确定',
        handler:function(){
         if (CheckWindow.check()){
        	 var isAgree = document.getElementById('isAgree').value;
      	     var reason = document.getElementById('reason').value;

      	     //此处用ajax方式提交审核信息
      	     CheckWindow.hide();
             CheckWindow.clear();
          }
        }
     }, {text:'取消',
         handler:function(){
           CheckWindow.hide();
           CheckWindow.clear();
         }
     }]
  });
  
  // 清空
  CheckWindow.clear = function() {
    document.getElementById('reason').value = '';
  }
  
  // 检测
  CheckWindow.check = function() {
    var reason = document.getElementById('reason').value;
    if (reason == null || reason == '') {
      Ext.MessageBox.show({
           title:'提示',
           minWidth:200,
           msg:'<div style=\'width:150\';><br/>审核具体意见不能为空！</div>',
           buttons:Ext.MessageBox.OK,
           icon:Ext.MessageBox.INFO
      });
      return false;
    }
    return true;
  }
</script>
<div id="checkWindow" class="x-hidden">
<div class="x-window-header">审核意见</div>
<div class="x-window-body">
	<table align="center" cellspacing="6">
	  <tr></tr>
	  <tr>
	    <td align="right">是否同意：</td>
	    <td><input type="text" style="width:200px;" id="isAgree" />
            <font color="red">*</font>
        </td>
	  </tr>
	  <tr>
	    <td align="right">具体意见：</td>
		<td align="left">
		  <textarea rows="3" name="reason" style="width:200px;" id="reason"></textarea>
	    </td>
	  </tr>
	</table>
</div>
</div>
</body>
</html>