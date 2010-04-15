<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@ taglib prefix="stc" uri="/systop/common" %>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/welcome.css"/>
</head>
<body>
<div class="col">
  <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/exticons/preview-right.gif" width="16" height="16">&nbsp;信息提示
		 </td>
		 <td class="block-title"  align="right">
		   <a href="#" target="main">
			  <img src="${ctx}/images/icons/more2.gif" title="查看更多..." width="14" height="14">
		   </a>&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
     <div class="block-body">
       <iframe src="${ctx}/fscaseCount/getCaseCount.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/authority.gif" width="16" height="16">&nbsp;文章列表
		 </td>
		 <td class="block-title"  align="right">
		   <a href="${ctx}/office/doc/index.do" target="main">
			  <img src="${ctx}/images/icons/more2.gif" title="查看更多..." width="14" height="14">
		   </a>&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
	 <div class="block-body">
       <iframe src="${ctx}/office/doc/documentOfWelcome.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/authority.gif" width="16" height="16">&nbsp;近期短信
		 </td>
		 <td class="block-title"  align="right">
		   <a href="#" target="main">
			  <img src="${ctx}/images/icons/more2.gif" title="查看更多..." width="14" height="14">
		   </a>&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
	 <div class="block-body">
       <iframe src="${ctx}/smsreceive/indexByDept.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
 </div>
<div class="col"> 
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/sound_1.gif" width="16" height="16">&nbsp;通知公告
		 </td>
		 <td class="block-title"  align="right">
		   <a href="${ctx}/office/receiverecord/index.do" target="main">
			  <img src="${ctx}/images/icons/more2.gif" title="查看更多..." width="14" height="14">
		   </a>&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
     <div class="block-body">
       <iframe src="${ctx}/office/receiverecord/noticeOfWelcome.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
    
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/authority.gif" width="16" height="16">&nbsp;风险评估
		 </td>
		 <td class="block-title"  align="right">
		   <a href="${ctx}/assessment/index.do" target="main">
			  <img src="${ctx}/images/icons/more2.gif" title="查看更多..." width="14" height="14">
		   </a>&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
	 <div class="block-body">
       <iframe src="${ctx}/assessment/viewWelcome.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
 </div>
</body>
</html>