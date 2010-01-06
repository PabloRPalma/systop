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
    <td><img src="${ctx}/images/choosesendtype/table_top.gif" width="567" height="13" /></td>
  </tr>
</table>
<table align="center" width="567" height="199" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td background="${ctx}/images/choosesendtype/table_bg.gif">
    	<table align="center" width="567" height="35" border="0" cellpadding="0" cellspacing="0">
        	<tr><td>&nbsp;</td></tr>
        	<c:forEach items="${ucTypeList}" var="type">
       		<tr>
	       		<td>
	       			<input onclick="DispatchWindow.selectType('${type.id }')" type="button" value="${type.name}" style="width:555px; height:35px; border: 0px; background-image: url(${ctx}/images/choosesendtype/01.gif);font-size:16px;color:#000"/>
	       		</td>
       		</tr>
       		</c:forEach>
       		<tr><td>&nbsp;</td></tr>
    	</table>
    </td>
  </tr>
</table>
<table align="center" width="567" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="${ctx}/images/choosesendtype/table_botom.gif" width="567" height="18" /></td>
  </tr>
</table>
</div>
</div>
</body>