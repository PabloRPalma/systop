<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>短信发送列表</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">短信发送</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td> 
			<form action="index.do" method="post" target="">
				&nbsp;&nbsp;姓名：<s:textfield name="model.name"/>&nbsp;&nbsp;
				手机号：<s:textfield name="model.mobileNum"/>&nbsp;&nbsp;
				最新：<input type="checkbox" style="border: 0px;" name="model.isNew" value="1" <s:if test="model.isNew != null">checked="checked"</s:if>>&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="查询" class="button">
			</form>
		</td>
		<td align="right">
		<table>
			<tr>
				<td><a href="edit.do"><img src="${ctx}/images/icons/add.gif" />添加</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="#" onclick="remove()"><img src="${ctx}/images/icons/delete.gif" />删除</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
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
	height="462px"
	minHeight="462"
	csvFileName="短信发送纪录.csv"
	toolbarContent="navigation|pagejump|pagesize|export|refresh|extend|status">
	<ec:row style="height: 50;">
		<ec:column width="40" property="_select" title="选择"
			style="text-align:center" viewsAllowed="html">
			<input type="checkbox" name="selectedItems" value="${item.id}" style="margin: -1px 0px -1px 0px; border: 0px;"/>
		</ec:column>
		<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="80" property="mobileNum" title="电话号码" style="text-align:center"/>
		<ec:column width="80" property="name" title="接收人" style="text-align:center"/>
		<ec:column width="260" property="content" title="短信内容" />
		<ec:column width="120" property="createTime" title="创建时间" cell="date" format="yyyy-MM-dd HH:mm" style="text-align:center"/>
		<ec:column width="80" property="_type" title="发送状态" style="text-align:center">
			<s:if test="#attr.item.isNew eq 1"><font color="red">未发</font></s:if>
			<s:if test="#attr.item.isNew eq 0"><font color="green">已发</font></s:if>
		</ec:column>
		<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
		<ec:column width="80" property="_isReceive" title="接收状态" style="text-align:center">
			<s:if test="#attr.item.isReceive eq 1"><font color="green">成功</font></s:if>
			<s:if test="#attr.item.isReceive eq 0"><font color="red">失败</font></s:if>
			<s:if test="#attr.item.isReceive eq 2"><font color="blue">未知</font></s:if>
		</ec:column>
		</stc:role>      
		<ec:column width="40" property="_option" title="操作" style="text-align:center">
			<a href="#" onClick="viewSmsInfo(${item.id})">查看</a>
		</ec:column>
	</ec:row>
</ec:table>
</div>
</div>
</div>
<!-- 查看短信内容 -->
<div id="smsWindow" class="x-hidden">
<div class="x-window-header">短信内容</div>
<div class="x-window-body">
	<table width="600">
	  <tr>
	    <td align="right" width="100" height="28"><font color="green">发送号码：</font></td>
		<td align="left" id="smsNum"></td>
	  </tr>
	  <tr>
	    <td align="right" width="100" height="28"><font color="green">接&nbsp;收&nbsp;人：</font></td>
		<td align="left" id="reciever"></td>
	  </tr>
	  <tr>
	    <td align="right" width="100" height="28"><font color="green">发送时间：</font></td>
		<td align="left" id="sendTime"></td>
	  </tr>
	  <tr>
	    <td align="right" width="100" height="28"><font color="green">短信内容：</font></td>
		<td align="left" id="smsContent"></td>
	  </tr>
	</table>
 </div>
</div>
<!-- 查看短信内容 -->
<script type="text/javascript">
  var SmsWindow = new Ext.Window({
      el: 'smsWindow',
      width: 600,
      height: 320,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'false',
      buttons:[
        {text:'关闭',
        	handler:function(){
        	SmsWindow.hide();
        }
      }]
  });
  function viewSmsInfo(smsId) {
	  $.ajax({
		     url: '${ctx}/smssend/viewSmsSendInfo.do',
		     type: 'post',
			 dataType: 'json',
			 data: {smsSendId : smsId},
			 success: function(rst, textStatus){
				 if(rst.mobileNum != null) {
					 document.getElementById("smsNum").innerHTML = rst.mobileNum;
				 }
				 if(rst.name != null) {
					 document.getElementById("reciever").innerHTML = rst.name;
				 }
				 if(rst.sendTime != null) {
					 document.getElementById("sendTime").innerHTML = rst.sendTime;
				 }
				 if(rst.content != null) {
					 document.getElementById("smsContent").innerHTML = rst.content;
				 }
				 SmsWindow.show();
			 }
  	  }); 
  }
</script>

<script type="text/javascript">
/**
 * 删除提交
 */
function remove(){
    var sels = document.getElementsByName("selectedItems");
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
    	Ext.MessageBox.show({
            title:'提示',
            minWidth:260,
            msg:'<div style=\'width:180\';><br/>请选择要删除的短信！</div>',
            buttons:Ext.MessageBox.OK,
            icon:Ext.MessageBox.INFO
       });
       return;
    }
	Ext.MessageBox.confirm('提示','确定要删除所选择的短信吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
        	var from = document.getElementById("ec");
    		from.target="_self";
    		from.action="remove.do";
    		from.submit();
        }
    });
}

</script>
</body>
</html>