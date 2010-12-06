<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/quake.jsp" %>
<style type="text/css">
fieldset {
	border: 1px solid #97b7e7;
	padding: 2px;
	margin: 10px;
}
a.sta:link{
   color: #0099bb;
}
a.sta:hover{
	color: #bb3300;
	text-decoration:none;
}
</style>
<title></title>
</head>
<body>

<fieldset>
   <legend>台站信息</legend>
   <table WIDTH="95%" BORDER="0" CELLSPACING="1" CELLPADDING="1" ALIGN="CENTER">
       <tr>
           <td>台网代码:<b>${sta.NET_CODE}</b></td>
           <td>台站代码：<b>${sta.STA_CODE}</b></td>
           <td>台站名称:<b>${sta.STA_CNAME}</b></td>
           <td>台基:<b>${sta.ROCK_TYPE}</b></td>
           <td>台站类型:<b>${sta.STA_TYPE}</b></td>
           <td>地址:<b>${sta.SITE_NAME}</b></td>
       </tr>
       <tr>
          <td colspan="10">
          时间范围:<b><fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></b>
          <span style="margin:5px;">-</span>
          <b><fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></b>
          </td>
       </tr>
   </table>
</fieldset>
<div style="margin-left:15px;">下表中，加粗的通道${cha}为本台站可用的通道。</div>
<form id="exp" action="${ctx}/quake/seismic/data/seed/stationseed/export.do" method="post">
<input type="hidden" name="model.sta" value="${sta.STA_CODE}">
<input type="hidden" name="model.net" value="${sta.NET_CODE}">
<%@include file="channels_table.jsp" %>

<fieldset>
   <legend>导出</legend>
   <table WIDTH="95%" BORDER="0" CELLSPACING="1" CELLPADDING="1" ALIGN="CENTER">
       <tr>
           <td>
           输出格式：<s:select list="@quake.seismic.data.seed.webapp.EventExportAction@OUTPUT_FORMAT"
		  	        name="format"></s:select>
           </td>
           <td>开始时间：
           <input name="model.startTime" class="Wdate" id="startTime" style="width:150px;"
           onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>',maxDate:'#F{$dp.$D(\'endTime\');}'});"></input>
           </td>
           <td>终止时间：
           <input name="model.endTime" class="Wdate" id="endTime" style="width:150px;"
           onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>',minDate:'#F{$dp.$D(\'startTime\');}'});"></input>
           </td>
           
           <td><input type="submit" align="center" value="导出数据"  class="button"/>
		  	      
		  	     &nbsp;&nbsp; <input type="button" align="center" value="返回上一页" onclick="history.go(-1);"  class="button"/>
          </td>
       </tr>
   </table>
</fieldset>
</form>
<script type="text/javascript">

$(function() {
//将存在的通道标示出来
  var cha = ${cha};
  if(cha) {
	  $('.cha').each(function(idx, item){
		  for(var i = 0; i < cha.length; i++) {
			  var sp = $(item);
			  if(cha[i] == sp.html()) {
				  sp.css("font-weight", "bold");
			  }
		  }
	  });
  }		
});
</script>
</body>
</html>