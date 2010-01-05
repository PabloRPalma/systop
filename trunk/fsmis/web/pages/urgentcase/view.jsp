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
			activeTab : ${param['actId']},
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
			}
			<c:if test="${model.status eq '2'|| model.status eq '3'|| model.status eq '4'}">
			, {
				contentEl : 'sendgroup',
				title : '派遣结果'
			}
			</c:if> ]
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
				<td align="right">
					<c:if test="${model.isAgree eq '0' || model.isAgree == null}">
						<a href="#" onclick="CheckWindow.show()"><img
							src="${ctx}/images/icons/house.gif" /> 审核</a>
					</c:if>
					<c:if test="${model.isAgree eq '1'&& model.status eq '1'}">
						<a href="#" onclick="DispatchWindow.show()"><img
							src="${ctx}/images/icons/house.gif" /> 任务派遣</a>
					</c:if>
					<c:if test="${model.status eq '2'}">
						<a href="#" onclick="finished()"><img
							src="${ctx}/images/icons/house.gif" /> 处理完毕</a>
					</c:if>
					<a href="${ctx}/urgentcase/viewResultReports.do?model.id=${model.id}" target="_blank"><img
							src="${ctx}/images/icons/house.gif" /> 查看报告</a>
				</td>
				<c:if test="${model.isAgree eq '0' || model.isAgree == null || model.status eq '1' || model.status eq '2'}">	
					<td><span class="ytb-sep"></span></td>
				</c:if>
				<td align="right"><a href="${ctx}/urgentcase/index.do"><img
					src="${ctx}/images/icons/house.gif" /> 应急事件列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div id="tabs">
<div id="basic" class="x-hide-display"><s:hidden name="model.id" id="caseId" />
<table id="mytable" height="360" style="margin-top: 5px">
	<tr>
		<td align="right" width="215">事件名称：</td>
		<td align="left" colspan="3"><s:textfield id="title"
			name="model.title" cssStyle="width:500px" disabled="true" />
		</td>
	</tr>
	<tr>
		<td align="right">事发时间：</td>
		<td width="287" align="left"><input type="text" name="model.caseTime" style="width: 148px"
			value='<s:property value="model.caseTime"/>'
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
			class="Wdate" /></td>
		<td width="88" align="right">预案等级：</td>
        <td width="348" align="left">${model.plansLevel}</td>
	</tr>
	<tr>
		<td align="right">事发地点：</td>
		<td align="left" colspan="3"><s:textfield id="address"
			name="model.address" cssStyle="width:500px" disabled="true" />
		</td>
	</tr>
	<tr>
		<td align="right">波及范围：</td>
		<td align="left" colspan="3"><s:textfield id="caseRange"
			name="model.caseRange" cssStyle="width:500px" disabled="true"/></td>
	</tr>
	<tr>
		<td align="right">受害人数：</td>
		<td align="left"><s:textfield id="harmNum"
			name="model.harmNum" cssStyle="width:148px" disabled="true"/></td>
		<td align="right">死亡人数：</td>
		<td align="left"><s:textfield id="deathNum"
			name="model.deathNum" cssStyle="width:148px" disabled="true"/></td>
	</tr>
	<tr>
		<td align="right">事件报告人：</td>
		<td align="left"><s:textfield id="reporter"
			name="model.reporter" cssStyle="width:148px" disabled="true"/></td>
		<td align="right">报告人电话：</td>
		<td align="left"><s:textfield id="reporterPhone"
			name="model.reporterPhone" cssStyle="width:148px" disabled="true" /></td>
	</tr>
	<tr>
		<td align="right">报告人单位：</td>
		<td align="left" colspan="3"><s:textfield id="reporterUnits"
			name="model.reporterUnits" cssStyle="width:500px" disabled="true"/></td>
	</tr>
	<tr>
		<td align="right">事件描述：</td>
		<td align="left" colspan="3" style="vertical-align: top"><s:textarea id="descn"
			name="model.descn" cssStyle="width:500px; height:170px" disabled="true" />
		</td>
	</tr>
</table>
</div>
<div id="hospital" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px">
	<tr>
		<td width="15%" align="right">周边医院情况：</td>
		<td align="left" style="vertical-align: top;" width="85%"><s:textarea
			id="hospitalInf" name="model.hospitalInf"
			cssStyle="width:600px; height:330px" disabled="true"/></td>
	</tr>
</table>
</div>
<div id="traffic" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px">
	<tr>
		<td width="15%" align="right">周边交通情况：</td>
		<td align="left" style="vertical-align: top;" width="85%"><s:textarea
			id="trafficInf" name="model.trafficInf"
			cssStyle="width:600px; height:330px" disabled="true"/></td>
	</tr>
</table>
</div>
<div id="sendgroup" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px">
	<tr>
		<td>
			<iframe name="groups" src="${ctx}/urgentcase/queryGroupResult.do?model.id=${model.id}" width="100%" height="550" frameborder="1"></iframe>			
		</td>
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
        	 var caseId = document.getElementById('caseId').value;
      	     var reason = document.getElementById('reason').value;
      	     $.ajax({
    			url: '${ctx}/urgentcase/saveCheckResult.do',
    			type: 'post',
    			dataType: 'json',
    			data: {caseId : caseId, isAgree : agreeVal, reason: reason},
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
             minWidth:200,
             msg:'<div style=\'width:150\';><br/>请选择审核是否通过！</div>',
             buttons:Ext.MessageBox.OK,
             icon:Ext.MessageBox.INFO
        });
        return false;
    }
    if (reason == null || reason == '') {
      Ext.MessageBox.show({
           title:'提示',
           minWidth:200,
           msg:'<div style=\'width:150\';><br/>请填写审核具体意见！</div>',
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
</script>
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
	var caseId = document.getElementById('caseId').value;
	Ext.MessageBox.confirm('提示','确定要选择该派遣环节吗？派遣后不能撤销！', function(btn){
        if (btn == 'yes') {
            //alert("派遣环节ID:" + typeId);
            $.ajax({
    			url: '${ctx}/urgentcase/sendUrgentCase.do',
    			type: 'post',
    			dataType: 'json',
    			data: {caseId : caseId, typeId : typeId},
    			success: function(rst, textStatus){
    				window.location = '${ctx}/urgentcase/view.do?model.id='+ caseId +'&actId=3';
    				//window.location = '${ctx}/urgentcase/index.do';
    			}
    	  	 });
        	DispatchWindow.hide();
        }
    });
  }
</script>
<!-- 任务派遣 -->
<div id="dispatchWindow" class="x-hidden">
<div class="x-window-header">任务派遣</div>
<div class="x-window-body">
	<table align="center" width="567" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td align="left" style="padding: 20px 0px 5px 0px;">
		<h4>事件名称：${model.title }</h4>
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
<script type="text/javascript">
  function finished() {
	  var caseId = document.getElementById('caseId').value;
	  Ext.MessageBox.confirm('提示','确定该应急事件已经处理完毕吗？', function(btn){
	        if (btn == 'yes') {
	        	window.location = '${ctx}/urgentcase/endUrgentCase.do?model.id='+ caseId;
	        }
	    });
  }
</script>
</body>
</html>