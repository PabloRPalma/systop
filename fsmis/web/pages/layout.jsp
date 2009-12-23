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
				title : '系统菜单',
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
					title : '网站管理',
					html : document.getElementById('cms').innerHTML,
					border : false,
					iconCls : 'cms'
				}, {
					title : '许可管理',
					html : document.getElementById('menu_sys').innerHTML,
					border : false,
					iconCls : 'permit'
				}, {
					title : '系统配置',
					html : document.getElementById('smtpsetting').innerHTML,
					border : false,
					iconCls : 'system'
				}, {
					title : '网站统计',
					html : document.getElementById('counter').innerHTML,
					border : false,
					iconCls : 'system'
				} , {
					title : '信息员管理',
					html : document.getElementById('supervisor').innerHTML,
					border : false,
					iconCls : 'system'
				}]
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
</body>
</html>
