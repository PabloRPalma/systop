<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setAttribute("ctx", request.getContextPath()); %>
<%@ taglib prefix="stc" uri="/systop/common" %>
<div id="menu" style="display:none">
	<div id="cms">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/folder_table.gif" class="icon">
				<a href="${ctx}/admin/catalog/listCatalog.do" target="main">栏目管理</a>
			</div>
			<br>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/articles.gif" class="icon">
				<a href="${ctx}/admin/article/listArticles.do" target="main">文章管理</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/article_check.gif" class="icon">
				<a href="${ctx}/admin/article/listAuditArticles.do" target="main">文章审核</a>
			</div>
			<br>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/compressed.gif" class="icon">
				<a href="${ctx}/admin/software/index.do" target="main">软件管理</a>
			</div>
			<br>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/xhtml_go.gif" class="icon">
				<a href="${ctx}/admin/template/listTemplate.do" target="main">模板管理</a>
			</div>
			<br>
			
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/link.gif" class="icon">
				<a href="${ctx}/admin/links/listLink.do" target="main">链接管理</a>
			</div>
			
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/admin/advisory/list.do" target="main">反馈管理</a>
			</div>		
			<br>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/a_list.gif" class="icon">
				<a href="${ctx}/admin/announce/listAnnounce.do" target="main">公告管理</a>
			</div>
		</div>
	</div>
	<div id="menu_corp">
		<div style="padding-left:5px;">
		    <div style="padding-top:2px">
				<img src="${ctx}/images/icons/add.gif" class="icon">
				<a href="${ctx}/enterprise/edit.do" target="main">添加企业</a>
			</div>          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/building_go.png" class="icon">
				<a href="${ctx}/enterprise/index.do" target="main">企业管理</a>
			</div>
		</div>
	</div>
	<div id="menu_sys">
		<div style="padding-left:5px;">
		    <div style="padding-top:2px">
				<img src="${ctx}/images/icons/group.gif" class="icon">
				<a href="${ctx}/admin/dept/index.do" target="main">单位/部门管理</a>
			</div>          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/user.gif" class="icon">
				<a href="${ctx}/security/user/index.do" target="main">用户管理</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/role.gif" class="icon">
				<a href="${ctx}/security/role/index.do" target="main">角色管理</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/authority.gif" class="icon">
				<a href="${ctx}/security/permission/index.do" target="main">权限管理</a>
			</div>
			<!--  
			<div style="padding-top:2px"> 
				<img src="${ctx}/images/icons/resource.gif" class="icon">
				<a href="${ctx}/security/resource/index.do" target="main">资源管理</a>
			</div>
			-->
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/user_go.gif" class="icon">
				<a href="${ctx}/regist/index.do" target="main">注册用户管理</a>
			</div>
		</div>
	</div>
	<div id="smtpsetting">
		<div style="padding-left:5px;">
		    <div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/datashare/admin/ds/edit.do" target="main">数据源配置</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/datashare/admin/seedpath/edit.do" target="main">Seed解析程序配置</a>
			</div>		          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/admin/mail/view.do" target="main">SMTP配置</a>
			</div>
				
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/datashare/admin/samplerate/index.do" target="main">采样率配置</a>
			</div>
			
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/datashare/admin/subject/index.do" target="main">测项配置</a>
			</div>	
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/datashare/admin/czcatalog/index.do" target="main">地震目录管理</a>
			</div>  
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/regist/regMemo/edit.do" target="main">注册说明设置</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/datashare/email/admin/edit.do" target="main">邮件订阅属性设置</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/datashare/email/admin/seisverify/list.do" target="main">测震邮件审核</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/datashare/email/admin/signverify/list.do" target="main">前兆邮件审核</a>
			</div>
			
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/datashare/admin/sitecfg/index.do" target="main">网站基本信息设置</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/datashare/admin/googlemap/edit.do" target="main">Google Map配置</a>
			</div>
		</div>		
	</div>
	<div id="supervisor">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/folder_table.gif" class="icon">
				<a href="${ctx}/supervisor/index.do" target="main">信息员管理</a>
			</div>
			<br>
		</div>
	</div>
	<div id="counter">
		<div  style="padding-left:5px;">
			<div style="padding-top:2px">
			<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/datashare/admin/counter/set/index.do" target="main">网站点击率统计</a>
			</div>
		</div>
	</div>
</div>
