<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@page import="com.systop.common.modules.security.user.UserUtil"%>
<%@page import="com.systop.common.modules.security.user.model.User"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>地震数据共享中心</title>
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/layout/layout.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/treeview/treeview.css" rel="stylesheet" />
<style type="text/css">
a[href='#'] {
   color:red;
}
</style>
<style type="text/css">
#mytable {border:1px solid #A6C9E2;width: 100%; border-collapse: collapse; }
#mytable th{border:1px solid #A6C9E2;width: 150;}
#mytable td{border:1px solid #A6C9E2;width: 150;height: 28px;}
</style>
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.async.js" ></script>

<script type="text/javascript">
	$(function(){
  	   
	    $('body').layout({west__size: 250});
		$("#accordion").accordion({header: "h3", fillSpace:true});
		$("#stations").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol"
		});

		$("#dataService").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol"
		});
		
		$("#mailPreorder").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol"
		});
		
		$("#persionIfro").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol"
		});
		//var srcStr = '${ctx}/datashare/descr/queryDescribe.do?model.type=seismicNetDescn&model.hasMetadata=0';
		//document.getElementById('main').src = srcStr;
    	//document.getElementById('main').location.reload(); 
    	
    if('${param.type}'){
  	   $('#dataserv').click();
  	   var note = 'note_' + '${param.type}';
  	   if(note != 'note_seismicMetadata' && note != 'note_signMetadata'){
  	  		if($('#note_seismicMetadata').children().find('#'+note)){
	   			$('#note_seismicMetadata').click();
	   			$('#'+note).click();
	   		}else if($('#note_seismicMetadata').children().find('#'+note)){
	   			$('#note_signMetadata').click();
	  	 	}
	   	}else{
	   		$('#'+note).click();
	   	}
  	   $('#main').attr("src", $('#${param.type}').attr("href"));
  	  }
  	 //  $('#'+note).click(); 
  	//   $('.hitarea').each(function(){
  	 //    $(this).click();
  	//   });
	});
</script>
<script type="text/javascript">
	function clsWin() {
		window.opener=null;  
		window.open("","_self");    
		window.close();    
	}
</script>
</head>
<body>
<div class="ui-layout-west">
	<table id="mytable" width="100%" align="center" style="text-align:center">
		<tr bgcolor="#FCFDFD">
			<td style="mvertical-align: middle;">
				<a href="${ctx}/index.shtml" style="color:#336699" title="返回首页">
					<font color="#2E6E9E"><img src="${ctx}/images/icons/house.gif"><b>&nbsp;首页</b></font>
				</a>&nbsp;&nbsp;
				<a href="${ctx}/j_acegi_logout" style="color:#336699">
					<font color="#2E6E9E"><img src="${ctx}/images/icons/user_go.gif"><b>&nbsp;注销</b></font>
				</a>&nbsp;&nbsp;
				<a href="#" onclick="clsWin()" style="color:#336699" title="关闭本页">
					<font color="#2E6E9E"><img src="${ctx}/images/exticons/delete.gif"><b>&nbsp;关闭</b></font>
				</a>
			</td>
		</tr>
  	</table>

  	
  	<div style="height: 95%;">
    <div id="accordion">
		<div>
		    <h3><a href="#">台网信息</a></h3>
		    <div>
				<ul id="stations" style="margin:10px 10px 10px 10px">
					<li><span><b><img src="${ctx}/images/icons/folder.gif">&nbsp;测震台网</b></span>
						<ul>
							<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/descr/queryDescribe.do?model.type=seismicNetDescn&model.hasMetadata=0" target="main">台网概述</a></li>
							<li><img src="${ctx}/images/icons/go.gif" class="icon"><a href="${ctx}/datashare/seismic/station/list.do" target="main">测震台站查询</a></li>
							<li><img src="${ctx}/images/icons/go.gif" class="icon"><a href="${ctx}/datashare/seismic/instrument/channel/list.do" target="main">通道参数查询</a></li>
							<li><img src="${ctx}/images/icons/go.gif" class="icon"><a href="${ctx}/datashare/seismic/instrument/stasite/list.do" target="main">场地响应查询</a></li>
						</ul>
					</li>
					<li><span><strong><img src="${ctx}/images/icons/folder.gif">&nbsp;前兆台网</strong></span>
						<ul>
							<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/descr/queryDescribe.do?model.type=signNetDescn&model.hasMetadata=0" target="main">台网概述</a></li>
							<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/sign/station/indexForSta.do" target="main" title="按台站查询">台站查询(按台站)</a></li>
							<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/sign/station/index.do" target="main" title="按学科-测项-仪器查询">台站查询(按学科)</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>

		<div>
		    <h3><a id="dataserv" href="javascript:void(0);">数据服务</a></h3>
		    <div>
				<ul id="dataService" style="margin:10px 10px 10px 10px">
					<li><span><strong><img id="note_seismicMetadata" src="${ctx}/images/icons/folder.gif">&nbsp;<a id="seismicMetadata" href="${ctx}/datashare/descr/queryDescribe.do?model.type=seismicMetadata&model.hasMetadata=1" target="main"><b>地震观测数据</b></a></strong></span>
						<ul>
					<stc:role ifAnyGranted="ROLE_NORMAL,ROLE_SENIOR,ROLE_ADMIN,ROLE_CATALOG">
					  <li><span><b><img id="note_catalog" src="${ctx}/images/icons/folder.gif">&nbsp;<a id="catalog" href="${ctx}/datashare/descr/queryDescribe.do?model.type=catalog&model.hasMetadata=1" target="main">地震目录</a></b></span>
						<ul>
							<s:iterator value="CZCATALOGS">
								<li>
								  <img src="${ctx}/images/icons/go.gif">
									<a href="${ctx}/datashare/seismic/data/catalog/index.do?model.tableName=<s:property value="cltName"/>" target="main">
									  <s:property value="clcName"/>
									</a>
								</li>
							</s:iterator>
						</ul> 
					  </li>
					</stc:role>
					<stc:role ifAnyGranted="ROLE_SENIOR,ROLE_ADMIN,ROLE_PHASE">
					  <li><span><b><img id="note_phase" src="${ctx}/images/icons/folder.gif">&nbsp;<a id="phase" href="${ctx}/datashare/descr/queryDescribe.do?model.type=phase&model.hasMetadata=1" target="main">震相</a></b></span>
					    <ul>
							<s:iterator value="pHASECATS"> 
								<li>
								  <img src="${ctx}/images/icons/go.gif">
									<a href="${ctx}/datashare/seismic/data/catalog/indexPhase.do?model.tableName=<s:property value="cltName"/>" target="main">
									  <s:property value="clcName"/>
									</a>
								</li>
							</s:iterator>
						</ul>
					  </li>
					</stc:role>
					<stc:role ifAnyGranted="ROLE_SENIOR,ROLE_ADMIN,ROLE_EVENT">
					  <li><span><img id="note_eventWave" src="${ctx}/images/icons/folder.gif">&nbsp;<a id="eventWave" href="${ctx}/datashare/descr/queryDescribe.do?model.type=eventWave&model.hasMetadata=1" target="main"><b>事件波形</b></a></span>
					    <ul>
							<s:iterator value="SEEDCATS">
								<li>
								  <img src="${ctx}/images/icons/go.gif">
									<a href="${ctx}/datashare/seismic/data/catalog/indexSeed.do?model.tableName=<s:property value="cltName"/>" target="main">
									  <s:property value="clcName"/>
									</a>
								</li>
							</s:iterator>
						</ul> 
					  </li>
					 </stc:role>
					 <stc:role ifAnyGranted="ROLE_SENIOR,ROLE_ADMIN,ROLE_CONTINUE">
					  <li><img id="note_sequenceWave" src="${ctx}/images/icons/folder.gif">&nbsp;<a id="sequenceWave" href="${ctx}/datashare/descr/queryDescribe.do?model.type=sequenceWave&model.hasMetadata=1" target="main"><b>连续波形</b></a></li>
					 </stc:role>
					</ul>
					</li>
					<li><span><strong><img id="note_signMetadata" src="${ctx}/images/icons/folder.gif">&nbsp;<a id="signMetadata" href="${ctx}/datashare/descr/queryDescribe.do?model.type=signMetadata&model.hasMetadata=1" target="main"><b>地球物理化学数据</b></a></strong></span>
						<ul>
							<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/sign/overview/index.do" target="main" title="前兆数据总览">数据总览</a></li>
							<s:iterator value="SUBJECTS" var="subject">
							<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/prequery/index.do?subject.id=${subject.id}" target="main">${subject.name}</a></li>	
							</s:iterator>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		
		<div>
		    <h3><a href="#">邮件订阅</a></h3>
		    <div>
				<ul id="mailPreorder" style="margin:10px 10px 10px 10px">
					<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/email/sign/index.do" target="main">订阅前兆数据</a></li>
					<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/datashare/email/seismic/index.do" target="main">订阅测震数据</a></li>
				</ul>
			</div>
		</div>
		
		<div>
		    <h3><a href="#">个人信息</a></h3>
		    <div>
				<ul id="persionIfro" style="margin:10px 10px 10px 10px">
					<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/security/user/changePassword.do" target="main">修改个人密码</a></li>
					<%
					User user = UserUtil.getPrincipal(request);
					if(user != null) {
					%>
					<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/user/showSelf.do?selfEdit=1&model.id=<%=user.getId()%>" target="main">修改基本信息</a></li>
					<%}%>
					<!--  
					<li><img src="${ctx}/images/icons/go.gif"><a href="${ctx}/j_acegi_logout">注销</a></li>
					-->
				</ul>
			</div>
		</div>
	</div>
	</div>
</div>
<div class="ui-layout-center" style="padding:0px;margin:0px;">
	<iframe src="${ctx}/datashare/descr/queryDescribe.do?model.type=seismicNetDescn&model.hasMetadata=0" id="main" name="main" style="width:100%; height:100%; border:0px;" frameborder="0"></iframe>
</div>
</body>
</html>