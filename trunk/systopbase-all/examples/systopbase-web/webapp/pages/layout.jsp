<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("ctx", request.getContextPath());
%>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
<title>systopbase</title>
<%@ include file="/common/extjs.jsp" %>
<%@ include file="/common/meta.jsp" %>
<link href="${ctx}/pages/layout.css" type='text/css' rel='stylesheet'/>
<style type="text/css">
.x-panel {
	border-top: 0 none;
}
</style>
<script type="text/javascript">
    Ext.onReady(function(){
       var northBtmMargin = "5 5 3 5";
       if(Ext.isIE) {
    	   northBtmMargin = "5 5 5 5";
       }
       Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                new Ext.BoxComponent({ // raw
                    region:'north',
                    el: 'north',
                    height:32,
                    margins: northBtmMargin
                }),{
                    region:'east',
                    title: '用户列表',
                    collapsible: true,
                    split:true,
                    width: 200,
                    minSize: 175,
                    maxSize: 400,
                    margins:'0 5 0 0',
                    layout:'fit'
                 },{
                    region:'west',
                    id:'west-panel',
                    title:'系统菜单',
                    split:true,
                    width: 200,
                    minSize: 175,
                    maxSize: 400,
                    collapsible: true,
                    margins:'0 0 0 5',
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items: [{                    
                        title:'许可管理',
                        html:document.getElementById('menu_sys').innerHTML,
                        border:false,
                        iconCls:'settings'
                    },{
                        title:'日程管理',
                        border:false,
                        autoScroll:true,
                        iconCls:'settings'
                    },{
                        title:'个人设置',
                        border:false,
                        autoScroll:true,
                        iconCls:'settings'
                    }]
                },
               {
                    region:'center',
                    contentEl: 'center',
                    border:true
                }
             ]
        });
       
    });
</script>
<%@include file="/pages/menu.jsp"%>
</head>
<body>
<div id="west"></div>
<div id="north">
    <div>
    <img src="${ctx}/images/logo.gif" width="30" height="25">
    </div>
</div>
<div id="center"><iframe src="${ctx}/pages/main.jsp" id="main"
	name="main" style="width:100%; height:100%; border:0px;" frameborder="0"></iframe></div>
</body>
</html>
