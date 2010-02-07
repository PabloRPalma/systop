<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.systop.common.modules.security.user.UserUtil"%>
<%@page import="com.systop.common.modules.security.user.model.User"%>
<html>
<head>
<title>石家庄市食品安全信息管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/common/extjs.jsp"%>
<LINK href="${ctx}/styles/style.css" type='text/css' rel='stylesheet'>
<LINK href="${ctx}/pages/layout.css" type='text/css' rel='stylesheet'>
<style type="text/css">
.title{
	font-weight: bold;
}
</style>
</head>
<body>
<script type="text/javascript">
	Ext.onReady(function() {

		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ new Ext.BoxComponent( { // raw
						region : 'north',
						el : 'north',
						height : 32
					}), {
				region : 'west',
				id : 'west-panel',
				title : '&nbsp;',
				split : true,
				width : 150,
				minSize : 150,
				maxSize : 400,
				collapsible : true,
				margins : '0 0 0 5',
				layout : 'accordion',
				layoutConfig : {
					animate : true
				},
				items : [ {
					title : '<span class="title">协调指挥</span>',
					html : document.getElementById('command').innerHTML,
					border : false,
					iconCls : 'cms'
				}, {
					title : '<span class="title">专家评估</span>',
					html : document.getElementById('assess').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">应急指挥</span>',
					html : document.getElementById('urgent').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">统计分析</span>',
					html : document.getElementById('analyse').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">企业诚信</span>',
					html : document.getElementById('corp').innerHTML,
					border : false,
					iconCls : 'corp'
				},{
					title : '<span class="title">信息员管理</span>',
					html : document.getElementById('supervisor').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">短信管理</span>',
					html : document.getElementById('sms').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">内部办公</span>',
					html : document.getElementById('office').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">配置管理</span>',
					html : document.getElementById('configer').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">网站管理</span>',
					html : document.getElementById('cms').innerHTML,
					border : false,
					iconCls : 'cms'
				},{
					title : '<span class="title">成员管理</span>',
					html : document.getElementById('member').innerHTML,
					border : false,
					iconCls : 'cms'
				}, {
					title : '<span class="title">许可管理</span>',
					html : document.getElementById('xkgl').innerHTML,
					border : false,
					iconCls : 'permit'
				} ]
			},

			{
				region : 'center',
				contentEl : 'center',
				split : true,
				border : true,
				margins : '0 5 0 0'
			} ]
		});
	});
</script>
<%@include file="/pages/menu.jsp"%>
<div id="west"></div>
<div id="north" align="center" style="margin-top: 4px;">
<table width="100%" border="0">
	<tr>
		<td align="left" width="60%"></td>
		<stc:ifLogin>
			<td align="right" style="color: #336699; padding-right: 20px">
				欢迎您：<stc:username></stc:username> &nbsp;&nbsp;|&nbsp;&nbsp; 
				<a href="${ctx}/pages/main.jsp" target="main" style="color: #336699">
					<img src="${ctx}/images/icons/house.gif">&nbsp;后台管理首页
				</a>
				&nbsp;&nbsp;|&nbsp;&nbsp; 
				<a href="${ctx}/j_acegi_logout" target="_self" style="color: #336699">
					<img src="${ctx}/images/icons/lock_go.gif">&nbsp;注销 
				</a>
			</td>
		</stc:ifLogin>
		<stc:ifNotLogin>
			<td align="right" style="color: #336699; padding-right: 20px">
				<a href="${ctx}/login.jsp" target="_self" style="color: #336699">
					<img src="${ctx}/images/icons/user_go.gif">&nbsp;登录 
				</a>
				<a href="${ctx}/restorePassword/edit.do" target="_self" style="color: #336699">
					<img src="${ctx}/images/icons/zoom.gif">&nbsp;忘记密码
				</a>
			</td>
		</stc:ifNotLogin>
	</tr>
</table>
</div>
<div id="center">
	<iframe id="main" name="main"
		src="${ctx}/pages/main.jsp"
		style="width: 100%; height: 100%; border: 0px;" frameborder="0">
	</iframe>
</div>
	<script type="text/javascript">
		function checkNewMsg(){
			Ext.Ajax.request({
				url : '/office/message/hasNewMes.do',
				method : 'POST',
				success : function(xhr, textStatus) {
					if (xhr.responseText != '0'){
						var message = "您有"
							message += " <font color='red'>";
							message += xhr.responseText;
							message += "</font> 条新的短消息，请注意<a href='${ctx}/office/message/received.do' target='main'><font color='red'>查收</font></a>";
						Ext.my().msg('', message);
					}
				}
			});
			setTimeout("checkNewMsg()", 15000);
		}
		
		checkNewMsg();
		//------------------------
		function checkNewNotice(){
			Ext.Ajax.request({
				url : '/office/receiverecord/hasNewNotices.do',
				method : 'POST',
				success : function(xhr, textStatus) {
					if (xhr.responseText != '0'){
						var message = "您有";
							message += " <font color='red'>";
							message += xhr.responseText;
							message += "</font> 新的通知，请注意<a href='${ctx}/office/receiverecord/index.do' target='main'><font color='red'>查收</font></a>";
						Ext.my().msg('', message);
					}
				}
			});
			setTimeout("checkNewNotice()", 200000);
		}
		
		checkNewNotice();
		//提示新任务信息
		function checkNewTaskDetail(){
			Ext.Ajax.request({
				url: '${ctx}/taskdetail/getDeptTaskDetailMes.do',
				methos :'POST',
				success : function(response,textStatus){
					 var respText = Ext.util.JSON.decode(response.responseText);
					 var message = '';
					 if(respText.single != 0 && respText.single != undefined || respText.multiple != undefined && respText.multiple != 0){
						 message+= "您部门有";
						 if(respText.single != 0 && respText.single != 'undefined'){
							 message+=respText.single;
							 message+="条<a href='${ctx}/taskdetail/index.do?isMultipleCase=0&modelId=1' target='main'><font color='red'>单体任务</font></a>,";
						 }
						 if(respText.multiple != 0 && respText.multiple != 'undefined'){
							 message+=respText.multiple;
							 message+="条<a href='${ctx}/taskdetail/index.do?isMultipleCase=1&modelId=1' target='main'><font color='red'>多体任务</font></a>,";
						 }
						 message+="请注意接收";

						 Ext.my().msg('', message);
					 }					
				}
			});
			setTimeout("checkNewTaskDetail()", 20000);
		}
		checkNewTaskDetail();

		//提示新任务信息
		function checkNewMutilCase(){
			Ext.Ajax.request({
				url: '${ctx}/fscase/getMutilCaseMsg.do',
				methos :'POST',
				success : function(response,textStatus){
					 var respText = Ext.util.JSON.decode(response.responseText);
					 var message = '';
					 if(respText.single != 0 && respText.single != undefined || respText.multiple != undefined && respText.multiple != 0){
						 message+= "有";
						 if(respText.multiple != 0 && respText.multiple != 'undefined'){
							 message+=respText.multiple;
							 message+="条<a href='${ctx}/fscase/index.do?isMultipleCase=1&modelId=1' target='main'><font color='red'>新多体事件</font></a>,";
						 }
						 message+="请注意查看";

						 Ext.my().msg('', message);
					 }					
				}
			});
			setTimeout("checkNewMutilCase()", 20000);
		}
		checkNewMutilCase();
		
		//提示新联合整治任务信息
		function checkNewJointTaskDetail(){
			Ext.Ajax.request({
				url: '${ctx}/jointTask/deptTaskDetail/getDeptJointTaskDetailMes.do',
				method : 'POST',
				success : function(xhr, textStatus) {
					if (xhr.responseText != '0'){
						var message = "您有";
						message += "<font color='red'>";
						message += xhr.responseText;
						message += "</font>条联合整治任务，请注意<a href='${ctx}/jointTask/deptTaskDetail/deptTaskDetailIndex.do' target='main'><font color='red'>查收</font></a>";
						Ext.my().msg('', message);			
					}
				}

			});
			setTimeout("checkNewJointTaskDetail()", 20000);
		}
		checkNewJointTaskDetail();		
		
		//提示新短信信息
		function checkNewSms(){
			Ext.Ajax.request({
				url: '${ctx}/fscase/getFsCaseSmsMes.do',
				methos :'POST',
				success : function(response,textStatus){
					 var respText = Ext.util.JSON.decode(response.responseText);
					 var message = '';
					 if(respText.reportSms != 0 && respText.reportSms != undefined || respText.verifySms != undefined && respText.verifySms != 0){
						 message+= "有";
						 if(respText.reportSms != 0 && respText.reportSms != 'undefined'){
							 message+=respText.reportSms;
							 message+="条举报短信,";
						 }
						 if(respText.verifySms != 0 && respText.verifySms != 'undefined'){
							 message+=respText.verifySms;
							 message+="条核实短信,";
						 }
						 message+="请注意<a href='${ctx}/smsreceive/index.do?' target='main'><font color='red'>查收</font></a>";

						 Ext.my().msg('', message);
					 }					
				}
			});
			setTimeout("checkNewSms()", 20000);
		}
		checkNewSms();
	</script>
</body>
</html>
