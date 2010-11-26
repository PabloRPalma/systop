<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<title></title>
<%@include file="/common/quake.jsp" %>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-1.7.1.css"	rel="stylesheet" />
<script type="text/javascript"	src="${ctx}/scripts/jquery/bgiframe/jquery.bgiframe.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(function() {
	$("#queryFrm").validate();
});
</script>
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
</head>
<body>
<s:form id="queryFrm" action="view" theme="simple">
<fieldset>
   <legend>地震目录信息</legend>
   <table WIDTH="95%" BORDER="0" CELLSPACING="1" CELLPADDING="1" ALIGN="CENTER">
       <tr>
           <td>发震时刻:<s:date name="catalogBySeed.O_TIME" format="yyyy-MM-dd HH:mm:ss"/></td>
           <td>震级(${catalogBySeed.M_SOURCE})：<s:property value="catalogBySeed.M"></s:property></td>
           <td>经度(°):<s:property value="catalogBySeed.EPI_LON"></s:property></td>
           <td>纬度(°):<s:property value="catalogBySeed.EPI_LAT"></s:property></td>
           <td>深度(KM):<s:property value="catalogBySeed.EPI_DEPTH"></s:property></td>
           <td>震中地名:<s:property value="catalogBySeed.LOCATION_CNAME"></s:property></td>
       </tr>
   </table>
</fieldset>
<%@include file="channels_table.jsp" %>
<fieldset>
    <legend>台站</legend>
    <table WIDTH="95%" BORDER="0" CELLSPACING="1" CELLPADDING="1" ALIGN="CENTER">
        <tr>
	            <td style="color:#0099bb;" align="left" colspan='3'>
	               <ul>
	                   <li>点击台站名称可查看台站测震波形图。</li>
	                   <li>选择台站，可将该台站数据包含在导出的数据中。</li>
	               </ul>
	            </td>
	      </tr>
         <tr>
	            <td style="color:#990000;" align="left">
	               <input type="checkbox" name="allStations" value="*" onclick="selecteAll(this)"></input>
	               全部台站
	            </td>
	      </tr>
	      <s:iterator value="#request.items" var="item" status="status">
	         
	         <s:if test="(#status.index)>0 && (#status.index)%4==0">
	             </tr>
	         </s:if>
	         <s:if test="(#status.index)%4==0">
	             <tr>
	         </s:if>
	         <td>
	               <input type="checkbox" name="stations" value="${item.stationCode}"></input>
	               <a href="javascript:void(0);" onclick="showGraph('${item.id}');" title="查看波形图" class="sta">
	                 <s:property value="station"/>
	               </a>
	         </td>
		</s:iterator>
	</table>
</fieldset>
<fieldset>
		<legend>事件波形</legend>
		<table width="98%" align="center">
			<tr>				
		  	    <td>
		  	    
		  	    输出格式:
		  	    <s:select list="@quake.seismic.data.seed.webapp.EventExportAction@OUTPUT_FORMAT"
		  	        name="format"></s:select>
		  	    <input type="hidden" name="seed" value="${param.seedname}"></input>
		  	    
		  	      <input type="button" align="center" value="导出数据" onclick="return exp();"  class="button"/>
		  	      
		  	     &nbsp;&nbsp; <input type="button" align="center" value="返回上一页" onclick="history.go(-1);"  class="button"/>
		  	    </td>
			</tr>			
		</table>
</fieldset>
</s:form>
<script type="text/javascript">	
	function selecteAll(val) {
     var checkBox = document.getElementsByName("stations");
     if (val.checked) {
       for (var i=0;i<checkBox.length;i++) {
	      if (!checkBox[i].disabled) {
	         checkBox[i].checked = 'checked';
	      }
       }
     } else {
       for (var i=0;i<checkBox.length;i++) {
	      if (checkBox[i].checked = 'checked') {
	         checkBox[i].checked = '';
	      }
       }
     }
   }

	function exp() {
		$('#queryFrm').attr("action", "${ctx}/quake/seismic/data/seed/eventexp/export.do");
		$('#queryFrm').submit();
	}

	function showGraph(id) {
		$('#queryFrm').attr("action", "view.do?id=" + id);
		$('#queryFrm').attr("target", "_blank");
		$('#queryFrm').submit();
	}
</script>
</body>
</html>