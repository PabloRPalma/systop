<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setAttribute("ctx", request.getContextPath()); %>
<%@ taglib prefix="stc" uri="/systop/common" %>
<div id="menu" style="display:none">
	<div id="catalog">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/folder_add.gif" class="icon">
				<a href="${ctx}/admin/catalog/newCatalog.do" target="main">栏目增加</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/folder_table.gif" class="icon">
				<a href="${ctx}/admin/catalog/listCatalog.do" target="main">栏目列表</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/folder_go.gif" class="icon">
				<a href="${ctx}/admin/catalog/listOrderCatalog.do" target="main">栏目排序</a>
			</div>
		</div>
	</div>
	
	<div id="article">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/article_add.gif" class="icon">
				<a href="${ctx}/admin/article/newArticle.do" target="main">文章添加</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/articles.gif" class="icon">
				<a href="${ctx}/admin/article/listArticles.do" target="main">文章列表</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/article_check.gif" class="icon">
				<a href="${ctx}/admin/article/listAuditArticles.do" target="main">文章审核</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/article_go.gif" class="icon">
				<a href="${ctx}/admin/article/orderArticle.do" target="main">文章排序</a>
			</div>
		</div>
	</div>
	
	<div id="software">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/soft.gif" class="icon">
				<a href="${ctx}/admin/software/newSoft.do" target="main">软件添加</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/resource.gif" class="icon">
				<a href="${ctx}/admin/software/index.do" target="main">软件列表</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/softcatalog.gif" class="icon">
				<a href="${ctx}/admin/software/listcatas.do" target="main">软件类别</a>
			</div>
		</div>
	</div>
	
	<div id="template">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/xhtml_add.gif" class="icon">
				<a href="${ctx}/admin/template/newTemplate.do" target="main">模板添加</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/xhtml_go.gif" class="icon">
				<a href="${ctx}/admin/template/listTemplate.do" target="main">模板列表</a>
			</div>
		</div>
	</div>
	<div id="specials">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/xhtml_add.gif" class="icon">
				<a href="${ctx}/admin/special/edit.do" target="main">专题添加</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/xhtml_go.gif" class="icon">
				<a href="${ctx}/admin/special/index.do" target="main">专题列表</a>
			</div>
		</div>
	</div>
	<div id="seisEmail">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/quake/email/admin/seisverify/list.do" target="main">测震邮件订阅审核</a>
			</div>
		</div>
	</div>
	<div id="announce">
		<div style="padding-left:5px;">    
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/link_catalog.gif" class="icon">
				<a href="${ctx}/admin/links/listCatas.do" target="main">类别管理</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/link.gif" class="icon">
				<a href="${ctx}/admin/links/listLink.do" target="main">链接管理</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/link_go.gif" class="icon">
				<a href="${ctx }/admin/links/orderLink.do" target="main">链接排序</a>
			</div>
		</div>
	</div>	
	<div id="menu_sys">
		<div style="padding-left:5px;">          
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
				<a href="${ctx}/quake/admin/ds/edit.do" target="main">数据源配置</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/quake/admin/seedpath/edit.do" target="main">Seed解析程序配置</a>
			</div>		          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/admin/mail/view.do" target="main">SMTP配置</a>
			</div>
				
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/quake/admin/catalog/index.do" target="main">地震目录管理</a>
			</div>  
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/regist/regMemo/edit.do" target="main">注册说明设置</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/quake/email/admin/edit.do" target="main">邮件订阅属性设置</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/quake/admin/sitecfg/index.do" target="main">网站基本信息设置</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/quake/admin/googlemap/edit.do" target="main">Google Map配置</a>
			</div>
			<div style="padding-top:2px">	
				<img src="${ctx}/images/icons/template.gif" class="icon">
                  <a href="${ctx}/quake/wap/wap.do" target="_black">测试wap</a>
			</div>
		</div>		
	</div>
</div>
