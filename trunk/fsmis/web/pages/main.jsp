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
  <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CITY,ROLE_COUNTY">
  <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/exticons/preview-right.gif" width="16" height="16">&nbsp;信息提示
		 </td>
		 <td class="block-title"  align="right">
			  <img src="${ctx}/images/icons/more2.gif" width="14" height="14">&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
     <div class="block-body">
       <iframe src="${ctx}/fscaseCount/getCaseCount.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
   </stc:role>
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/authority.gif" width="16" height="16">&nbsp;文章列表
		 </td>
		 <td class="block-title"  align="right">
			  <img src="${ctx}/images/icons/more2.gif" width="14" height="14">&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
	 <div class="block-body">
       <iframe src="${ctx}/office/doc/documentOfWelcome.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
   <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CITY,ROLE_COUNTY">
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/authority.gif" width="16" height="16">&nbsp;近期短信
		 </td>
		 <td class="block-title"  align="right">
			  <img src="${ctx}/images/icons/more2.gif" width="14" height="14">&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
	 <div class="block-body">
       <iframe src="${ctx}/smsreceive/indexByDept.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
   </stc:role>
 </div>
<div class="col"> 
  <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_COUNTY,ROLE_DEPT">
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/sound_1.gif" width="16" height="16">&nbsp;任务接收
		 </td>
		 <td class="block-title"  align="right">
			  <img src="${ctx}/images/icons/more2.gif" width="14" height="14">&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
     <div class="block-body">
       <iframe src="${ctx}/taskdetailCount/getTaskDetailCount.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
    </stc:role>
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/sound_1.gif" width="16" height="16">&nbsp;通知公告
		 </td>
		 <td class="block-title"  align="right">
			  <img src="${ctx}/images/icons/more2.gif" width="14" height="14">&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
     <div class="block-body">
       <iframe src="${ctx}/office/receiverecord/noticeOfWelcome.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
   <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CITY,ROLE_COUNTY">
   <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/icons/authority.gif" width="16" height="16">&nbsp;风险评估
		 </td>
		 <td class="block-title"  align="right">
			  <img src="${ctx}/images/icons/more2.gif" width="14" height="14">&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
	 <div class="block-body">
       <iframe src="${ctx}/assessment/viewWelcome.do" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>
   </stc:role>
 </div>
</body>
</html>