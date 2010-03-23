<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/meta.jsp" %>
<html>
<head>

<title>SystopCMS</title>

<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="English" />
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="styles/index.css" />

</head>

<body>

<div id="container">
	<div id="header">

		<h1><a href="#" title=新龙内容管理">Systop<span> CMS 1.0</span></a></h1>
		<div id="navigation">
			<ul>
			  <li><a href="#">首页</a></li>
				<li><a href="#">服务</a></li>
				<li><a href="#">博客</a></li>	
				<li><a href="#">论坛</a></li>
				<li><a href="#">关于</a></li>
			</ul>
		</div><!-- navigation -->
		<hr />
	</div><!-- header -->
	<div id="path-search">
		<p class="search">Saturday 2006/09/16</p>
	</div>

	<div id="primary-content">
		<cms:moietyArticle id="moiety001" articleId="12310" imgSrc="images/zhuanqian.jpg" wordCount="500"/>
		<cms:moietyArticle id="moiety002" articleId="34177024"/>
		<cms:moietyArticle id="moiety003" articleId="34209792"/>
	</div><!-- primary-content -->
	<hr />
	<div id="secondary-content">
		<h2>*文章搜索</h2>
			<form id="search_engine" method="post" action="" > 
				<input  id="search_query" name="search_query" type="text" class="text"/>
				<br>
				<input type="submit" name="search" tabindex="100" value="Search" />
				<br><br>
		</form>
		<h2>*最新文章:</h2>
		<cms:newArticles id="newArticles01" displayRows="10"/>
		<h2>*最新目录:</h2>
		<cms:newFolders id="newFolders01" displayRows="6"/>
		<h2>*已读文章:</h2>
		<cms:newArticles id="newArticles01" displayRows="8"/>
		<h2>*本地链接:</h2>
					 &nbsp;&nbsp;<img src="images/icons/articleIco.gif"/>&nbsp;<a href="#">首页</a>
			<br/>&nbsp;&nbsp;<img src="images/icons/articleIco.gif"/>&nbsp;<a href="#">服务</a>
			<br/>&nbsp;&nbsp;<img src="images/icons/articleIco.gif"/>&nbsp;<a href="#">博客</a>
			<br/>&nbsp;&nbsp;<img src="images/icons/articleIco.gif"/>&nbsp;<a href="#">论坛</a>
			<br/>&nbsp;&nbsp;<img src="images/icons/articleIco.gif"/>&nbsp;<a href="#">关于</a><br/>
		</div><!-- secondary-content -->
	<hr />
	<div id="footer">
		<p>&copy; Copyright 2007 Systop, by.&#26032;&#40857;&#20844;&#21496;</p>

	</div><!-- footer -->
</div>

</body>

</html>