<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
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
        	 
      	     var reason = document.getElementById('reason').value;
      	     $.ajax({
    			url: '${ctx}/urgentcase/saveCheckResult.do',
    			type: 'post',
    			dataType: 'json',
    			data: {caseId : cId, isAgree : agreeVal, reason: reason},
    			success: function(rst, textStatus){
    	  		  if(rst.result == "success"){
        	  		window.location = '${ctx}/urgentcase/index.do';
    	  	  	  }else{
    	  	  		CheckWindow.hide();
    	  	  		alert("审核失败，请重试...");
    	  	  	  }
    			}
    	  	 });
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
    if (agreeVal == null || agreeVal == '') {
        Ext.MessageBox.show({
             title:'提示',
             minWidth:220,
             msg:'<div style=\'width:180\';><br/>请选择审核是否通过！</div>',
             buttons:Ext.MessageBox.OK,
             icon:Ext.MessageBox.INFO
        });
        return false;
    }
    if (reason == null || reason == '') {
      Ext.MessageBox.show({
           title:'提示',
           minWidth:220,
           msg:'<div style=\'width:180\';><br/>请填写审核具体意见！</div>',
           buttons:Ext.MessageBox.OK,
           icon:Ext.MessageBox.INFO
      });
      return false;
    }
    return true;
  }
  var agreeVal = null;
  function setAgreeValue(aValue) {
	agreeVal = aValue;
  }
  var cId = null;
  function showCheckWindow(csId) {
	this.cId = csId;
	CheckWindow.show();
  }
</script>
<!-- 任务派遣 -->
<script type="text/javascript">
  var DispatchWindow = new Ext.Window({
      el: 'dispatchWindow',
      width: 600,
      height: 350,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'true',
      buttons:[
       {text:'关闭',
           handler:function(){
    	   	DispatchWindow.hide();
           }
       }]
  });
  //选择派遣环节进行任务派遣
  DispatchWindow.selectType = function(typeId) {
	//var caseId = document.getElementById('caseId').value;
	Ext.MessageBox.confirm('提示','确定要选择该派遣环节吗？派遣后不能撤销！', function(btn){
        if (btn == 'yes') {
            //alert("派遣环节ID:" + typeId);
            $.ajax({
    			url: '${ctx}/urgentcase/sendUrgentCase.do',
    			type: 'post',
    			dataType: 'json',
    			data: {caseId : ccId, typeId : typeId},
    			success: function(rst, textStatus){
    				window.location = '${ctx}/urgentcase/view.do?model.id='+ ccId +'&actId=3';
    				//window.location = '${ctx}/urgentcase/index.do';
    			}
    	  	 });
        	DispatchWindow.hide();
        }
    });
  }
  var ccId = null;
  function showDispatchWindow(csId,csName) {
	this.ccId = csId;
	document.getElementById('caseName').innerHTML = csName;
	DispatchWindow.show();
  }
</script>
<!-- 流程回溯 -->
<script type="text/javascript">
  var FlowWindow = new Ext.Window({
      el: 'flowWindow',
      width: 660,
      height: 300,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'true',
      buttons:[
       {text:'关闭',
           handler:function(){
    	   	FlowWindow.hide();
           }
       }]
  });

  function viewCaseInfo(cca) {
	  window.location = '${ctx}/urgentcase/view.do?model.id='+ ccfId +'&actId='+cca;
  }
  var ccfId = null;
  function showFlowWindow(csId,csName,status) {
	this.ccfId = csId;
	document.getElementById('caseNameFlow').innerHTML = csName;
	if (status == '' || status == 0) {
		document.getElementById('csCheck').style.backgroundImage="url(${ctx}/pages/urgentcase/images/color/index_03.gif)";
		document.getElementById('csCheck').innerHTML = "<a href='#' onclick='viewCaseInfo(0)'>"+"审核"+"</a>";
	}
	if (status != '' && status != 0) {
		document.getElementById('csCheck').style.backgroundImage="url(${ctx}/pages/urgentcase/images/blank/index_03.gif)";
		document.getElementById('csCheck').innerHTML = "审核";
	}
	if (status == 1) {
		document.getElementById('csPai').style.backgroundImage="url(${ctx}/pages/urgentcase/images/color/index_11.gif)";
		document.getElementById('csPai').innerHTML = "<a href='#' onclick='viewCaseInfo(0)'>"+"派遣"+"</a>";
	}
	if (status != 1) {
		document.getElementById('csPai').style.backgroundImage="url(${ctx}/pages/urgentcase/images/blank/index_11.gif)";
		document.getElementById('csPai').innerHTML = "派遣";
	}
	if (status == 2) {
		document.getElementById('csDail').style.backgroundImage="url(${ctx}/pages/urgentcase/images/color/index_13.gif)";
		document.getElementById('csDail').innerHTML = "<a href='#' onclick='viewCaseInfo(3)'>"+"处理"+"</a>";
	}
	if (status != 2) {
		document.getElementById('csDail').style.backgroundImage="url(${ctx}/pages/urgentcase/images/blank/index_13.gif)";
		document.getElementById('csDail').innerHTML = "处理";
	}
	if (status == 3) {
		document.getElementById('csEnd').style.backgroundImage="url(${ctx}/pages/urgentcase/images/color/index_15.gif)";
	}
	if (status != 3) {
		document.getElementById('csEnd').style.backgroundImage="url(${ctx}/pages/urgentcase/images/blank/index_15.gif)";
	}
	FlowWindow.show();
  }
</script>
</head>
<body>
<!-- 审核 -->
<div id="checkWindow" class="x-hidden">
<div class="x-window-header">审核意见</div>
<div class="x-window-body">
	<table align="center" cellspacing="6">
	  <tr></tr>
	  <tr>
	    <td align="right">是否通过：</td>
	    <td>
	    	<s:radio list="isAgreeMap" id="isagree" name="isagree" onchange="setAgreeValue(this.value)" cssStyle="border:0px;"/> 
            <font color="red">*</font>
        </td>
	  </tr>
	  <tr>
	    <td align="right">具体意见：</td>
		<td align="left">
		  <textarea rows="7" name="reason" style="width:280px;" id="reason"></textarea>
		  <font color="red">*</font>
	    </td>
	  </tr>
	</table>
</div>
</div>
<!-- 任务派遣 -->
<div id="dispatchWindow" class="x-hidden">
<div class="x-window-header">任务派遣</div>
<div class="x-window-body">
	<table align="center" width="567" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td align="left" style="padding: 20px 0px 5px 0px;">
		<h4>事件名称：<s:label id="caseName"></s:label></h4>
	</td>
  </tr>
  <tr>
    <td style="margin-top: -1;margin-left: -2;"></td>
  </tr>
</table>
<table align="center" width="567" height="199" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table align="center" width="567" height="35" border="0" cellpadding="0" cellspacing="0">
        	<tr><td>&nbsp;</td></tr>
        	<c:forEach items="${ucTypeList}" var="type">
       		<tr>
	       		<td>
	       			<input onclick="DispatchWindow.selectType('${type.id }')" type="button" value="${type.name}" style="width:560px; height:40px;  border: 0px; background-image: url(${ctx}/images/choosesendtype/bg.gif);font-size:12px;color:#000;font-weight:bold"/>
	       		</td>
       		</tr>
       		</c:forEach>
       		<tr><td>&nbsp;</td></tr>
    	</table>
    </td>
  </tr>
</table>
</div>
</div>
<!-- 流程回溯 -->
<div id="flowWindow" class="x-hidden">
<div class="x-window-header">流程回溯</div>
<div class="x-window-body">
<table width="567" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td align="left" style="padding: 15px 0px 15px 0px;">
		<h4>&nbsp;&nbsp;事件名称：<s:label id="caseNameFlow"></s:label></h4>
	</td>
  </tr>
  <tr>
    <td style="margin-top: -1;margin-left: -2;"></td>
  </tr>
</table>
<table id="__01" width="653" height="95" border="0" cellpadding="0" cellspacing="0">
	<tr><td height="25" colspan="10" bgcolor="#FFFFFF"></td></tr>
	<tr>
		<td id="csStart" align="center" background="${ctx}/pages/urgentcase/images/blank/index_01.gif" width="93" height="48">
			事件开始</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_02.gif" width="33" height="48">
			</td>
		<td id="csCheck" align="center" background="${ctx}/pages/urgentcase/images/blank/index_03.gif" width="91" height="48">
			待审核</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_04.gif" width="88" height="48">
			</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_05.gif" width="348" height="48" colspan="6">
			</td>
	</tr>
	<tr>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_06.gif" width="93" height="47">
			</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_07.gif" width="33" height="47">
			</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_08.gif" width="91" height="47">
			<font color="red"><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未通过</font></td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_09.gif" width="88" height="47">
			审核</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_10.gif" width="46" height="47">
			<font color="green"><br><br>通过</font></td>
		<td id="csPai" align="center" background="${ctx}/pages/urgentcase/images/blank/index_11.gif" width="92" height="47">
			派遣</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_12.gif" width="35" height="47">
			</td>
		<td id="csDail" align="center" background="${ctx}/pages/urgentcase/images/blank/index_13.gif" width="92" height="47">
			处理</td>
		<td align="center" background="${ctx}/pages/urgentcase/images/blank/index_14.gif" width="26" height="47">
			</td>
		<td id="csEnd" align="center" background="${ctx}/pages/urgentcase/images/blank/index_15.gif" width="57" height="47">
			&nbsp;&nbsp;完成</td>
	</tr>
	<tr><td height="25" colspan="10" bgcolor="#FFFFFF"></td></tr>
</table>
</div>
</div>
</body>