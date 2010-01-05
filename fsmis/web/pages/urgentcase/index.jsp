<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
  function remove(caseId) {
    Ext.MessageBox.confirm('提示','确认要删除所选择的应急事件吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
          window.location = "${ctx}/urgentcase/remove.do?model.id=" + caseId;
        }
    });
  }
  
  function refresh() {
    ECSideUtil.reload('ec');
  }

  function print(id){
		var url = "${ctx}/emergency/event/printEmgcApply.do?model.id=" + id;
		window.open(url);
	}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">应急事件管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		<form action="${ctx}/urgentcase/index.do" method="post">
			 事件名称：
		    <s:textfield name="model.title"></s:textfield>
			<s:submit value="查询" cssClass="button"></s:submit>
		</form>
		</td>
		<td align="right">
		  <table>
			<tr>
				<td><a href="edit.do" title="添加应急事件"><img
					src="${ctx}/images/icons/add.gif" /></a></td>
				<td valign="middle"><a href="edit.do" title="添加应急事件">添加</a></td>
			</tr>
		  </table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" 
	var="item" 
	retrieveRowsCallback="process"
	sortRowsCallback="process" 
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
	    <ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
		<ec:column width="180" property="title" title="事件名称" >
		  <c:if test="${item.status == null || item.status eq '0' || item.status eq '1'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=0" title="查看事件"><font color="blue">${item.title}</font></a>
		  </c:if>
		  <c:if test="${item.status eq '2' || item.status eq '3' || item.status eq '4'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=3" title="查看事件"><font color="blue">${item.title}</font></a>
		  </c:if>
		</ec:column>
		<ec:column width="180" property="address" title="事发地点"/>
		<ec:column width="110" property="caseTime" title="事发时间" style="text-align:center" format="yyyy-MM-dd HH:mm" cell="date"/>
		<ec:column width="60" property="status" title="状态" style="text-align:center">
		    <c:if test="${item.status == null}"><font color="#990099">未审核</font></c:if>
		    <c:if test="${item.status == '0'}"> <font color="red">未通过</font></c:if>
			<c:if test="${item.status == '1'}"> <font color="blue">未派遣</font></c:if>
			<c:if test="${item.status == '2'}"> <font color="green">已派遣</font></c:if>
			<c:if test="${item.status == '3'}"> <font color="green">已处理</font></c:if>
			<c:if test="${item.status == '4'}"> <font color="green">已核实</font></c:if>
		</ec:column>
		<ec:column width="60" property="_v" title="查看" style="text-align:center">
			<c:if test="${item.status != null}">
				<a href="${ctx}/urgentcase/listCheckResult.do?model.id=${item.id}">审核记录</a>
			</c:if>
			<c:if test="${item.status == null}">
				<font color="silver">无记录</font>
			</c:if>
		</ec:column>
		<ec:column width="210" property="_0" title="操作" style="text-align:center" sortable="false">
		  <c:if test="${item.status == null || item.status eq '0'}">
			<a href="${ctx}/urgentcase/edit.do?model.id=${item.id}" title="修改事件">改</a> | 
			<a href="#" onclick="showCheckWindow('${item.id}')" title="审核事件">审</a> | 
			<font color="silver">派</font> |
		  </c:if>
		  <c:if test="${item.status eq '1'}">
		  	<font color="silver">改</font> | 
		  	<font color="silver">审</font> | 
			<a href="#" onclick="showDispatchWindow('${item.id}','${item.title}')" title="任务派遣">派</a> | 
		  </c:if>
		  <c:if test="${item.status eq '2' || item.status eq '3' || item.status eq '4'}">
		  	<font color="silver">改</font> | 
		  	<font color="silver">审</font> | 
			<font color="silver">派</font> | 
		  </c:if>
		  <c:if test="${item.status == null || item.status eq '0' || item.status eq '1'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=0" title="查看事件">看</a> | 
		  </c:if>
		  <c:if test="${item.status == '2' || item.status eq '3' || item.status eq '4'}">
			<a href="${ctx}/urgentcase/view.do?model.id=${item.id}&actId=3" title="查看事件">看</a> | 
		  </c:if>
		  	<a href="${ctx}/urgentcase/printAppForm.do?model.id=${item.id}" title="打印应急预案申请单" target="_blank">打</a> | 
			<a href="#" onclick="remove(${item.id})" title="删除事件">删</a>
		</ec:column>
	</ec:row>
</ec:table>
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
  var cId = null;
  function showCheckWindow(csId) {
	this.cId = csId;
	CheckWindow.show();
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
	document.getElementById('caseName').innerText = csName;
	DispatchWindow.show();
  }
</script>
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
</html>