<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="../styles/index.css" />

</head>

<body>

<div id="container" style=" height: auto;">
	<div id="header">

		<h1><a href="#" title="&#26032;&#40857;&#20869;&#23481;&#31649;&#29702;">Systop<span> CMS 1.0</span></a></h1>
		<div id="navigation">
			<ul>
			  <li><a href="#" title='Homepage'>&#39318;&#39029;</a></li>
			</ul><ul>
				<li><a href="#">&#20851;&#20110;</a></li>
				<li><a href="#">&#26381;&#21153;</a></li>	
				<li><a href="#">&#35770;&#22363;</a></li>
				<li><a href="#">&#20851;&#20110;</a></li>
			</ul>
		</div><!-- navigation -->
		<hr />
	</div><!-- header -->
	<div id="primary-content" style=" width:710px; height: 450px;">
		<cms:viewContent id="001" templateName="viewContent"/>
	</div><!-- primary-content -->
	
	<div id="footer" style=" width: 700px;">
		<p>&copy; Copyright 2007 Systop, by.&#26032;&#40857;&#20844;&#21496;</p>

	</div><!-- footer -->
</div>

</body>

</html>