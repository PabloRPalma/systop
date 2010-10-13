<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<link href="${ctx}/styles/quake.css" type='text/css' rel='stylesheet'>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(function(){
	var aziFlagSize = $('#aziFlagSize').val();
	var diffSize = $('#diffSize').val();
	var itemsSize = $('#itemsSize').val();
	var instrParamSize = $('#instrParamSize').val();
	var staCaveSize = $('#staCaveSize').val();
	var staWellSize = $('#staWellSize').val();
	if (aziFlagSize == 0) {
		$('#aziFlag').hide();
	}
	if (diffSize == 0) {
		$('#diff').hide();
	}
	if (itemsSize == 0) {
		$('#staItem').hide();
	}
	if (instrParamSize == 0) {
		$('#instrParam').hide();
	}
	if (staCaveSize == 0) {
		$('#cave').hide();
	}
	if (staWellSize == 0) {
		$('#well').hide();
	}
	// Tabs
	$('#tabs').tabs();
});
</script>
<title></title>
<style type="text/css">
#mytable {border:1px solid #A6C9E2;margin-left: -21px; margin-top:-1px; width: 96%; border-collapse: collapse; }
#mytable td{border:1px solid #A6C9E2;height: 24;}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-body">
<div id="tabs">
	<s:hidden id="aziFlagSize" name="model.aziFlagSize" />
	<s:hidden id="diffSize" name="model.diffSize" />
	<s:hidden id="itemsSize" name="model.itemsSize" />
	<s:hidden id="instrParamSize" name="model.instrParamSize" />
	<s:hidden id="staCaveSize" name="model.staCaveSize" />
	<s:hidden id="staWellSize" name="model.staWellSize" />
	<ul>
		<li><a href="#tabs-1">测点</a></li>
		<li id="aziFlag"><a href="#tabs-2">地磁方位标</a></li>
		<li id="diff"><a href="#tabs-3">地磁墩差</a></li>
		<li id="staItem"><a href="#tabs-4">分量</a></li>
		<li id="instrParam"><a href="#tabs-6">仪器参数</a></li>
		<li id="cave"><a href="#tabs-7">洞体</a></li>
		<li id="well"><a href="#tabs-8">井泉</a></li>
	</ul>
	<div id="tabs-1" style="margin-bottom: -16px;">
		<table id="mytable">
			<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站测点信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">台站名称：</td>
				<td width="150"class="simple">${point.STATIONNAME}</td>
				<td width="100" class="simple" align="right">子台编码：</td>
				<td width="80" class="simple">${point.SUBSTATIONID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测点编码：</td>
				<td class="simple" colspan="3">${point.POINTID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">建点改造日期：</td>
				<td class="simple" colspan="3">${point.POINTCONSDATE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测点名称：</td>
				<td class="simple">${point.POINTNAME}</td>
				<td width="100" class="simple" align="right">高程：</td>
				<td class="simple">${point.ALTITUDE}(米)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">地理坐标获取方式：</td>
				<td class="simple">${point.COORDETERMINATIONWAY}</td>
				<td width="100" class="simple" align="right">测点离主台距离：</td>
				<td class="simple">${point.POINTDISTANCE}(米)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">行政区划：</td>
				<td class="simple" colspan="3">${point.POINTADMINREGION}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">主要干扰源：</td>
				<td class="simple" colspan="3">${point.POINTMAINDISTURBSOURCE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">地形地质构造图：</td>
				<td class="simple" colspan="3">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATIONPOINTS','${point.STATIONID}','${point.POINTID}','STATIONSTRUCTUREMAP','PHOTO')">图片</a>
				</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测点图片：</td>
				<td class="simple" colspan="3">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATIONPOINTS','${point.STATIONID}','${point.POINTID}','POINTPHOTO','PHOTO')">图片</a>
				</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测点建设报告：</td>
				<td class="simple" colspan="3">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATIONPOINTS','${point.STATIONID}','${point.POINTID}','POINTREPORT','WORD')">报告</a>
				</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">备注：</td>
				<td class="simple" colspan="3">${point.POINTDESCRIPTION}</td>
			</tr>
		</table>
	</div>
	<div id="tabs-2" style="margin-bottom: -16px;">
		<c:forEach items="${aziFlagList}" var="aziFlag">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站地磁观测方位标信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">台站名称：</td>
				<td width="150"class="simple">${aziFlag.STATIONNAME}</td>
				<td width="100" class="simple" align="right">测点编码：</td>
				<td width="80" class="simple">${aziFlag.POINTID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">方位标编号：</td>
				<td class="simple" colspan="3">${aziFlag.AZIFLAGNO}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">观测墩号：</td>
				<td class="simple" colspan="3">${aziFlag.STACKNO}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测定日期：</td>
				<td width="150"class="simple">${aziFlag.MEADATE}</td>
				<td width="100" class="simple" align="right">是否为主标志：</td>
				<td width="80" class="simple">${aziFlag.ISMAINAZIFLAG_CN}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">方位标距绝对观测室距离：</td>
				<td width="150"class="simple">${aziFlag.AZIFLAGDISTANCE}(米)</td>
				<td width="100" class="simple" align="right">方位标建设方式类型：</td>
				<td width="80" class="simple">${aziFlag.AZIFLAGTYPE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">该观测墩至该方位标方位角：</td>
				<td class="simple" colspan="3">${aziFlag.AZIMUTHANGLE}</td>
			</tr>
		  </table>
		  </c:forEach>
	</div>
	<div id="tabs-3" style="margin-bottom: -16px;">
		<c:forEach items="${diffList}" var="diff">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站地磁观测墩差信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">台站名称：</td>
				<td width="150"class="simple">${diff.STATIONNAME}</td>
				<td width="100" class="simple" align="right">测点编码：</td>
				<td width="80" class="simple">${diff.POINTID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测定日期：</td>
				<td class="simple" colspan="3">${diff.MEADATE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">墩号：</td>
				<td width="150"class="simple">${diff.STACKNO}</td>
				<td width="100" class="simple" align="right">是否为标准墩：</td>
				<td width="80" class="simple">${diff.ISSTANDARDSTACK_CN}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">D墩差：</td>
				<td width="150"class="simple">${diff.DDIFFVALUE}</td>
				<td width="100" class="simple" align="right">I墩差：</td>
				<td width="80" class="simple">${diff.IDIFFVALUE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">F墩差：</td>
				<td width="150"class="simple">${diff.FDIFFVALUE}</td>
				<td width="100" class="simple" align="right">H墩差：</td>
				<td width="80" class="simple">${diff.HDIFFVALUE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">Z墩差：</td>
				<td class="simple" colspan="3">${diff.ZDIFFVALUE}</td>
			</tr>
		  </table>
		  </c:forEach>
	</div>
	<div id="tabs-4" style="margin-bottom: -16px;">
		<c:forEach items="${itemsList}" var="item">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站测项分量信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">台站名称：</td>
				<td width="150"class="simple">${item.STATIONNAME}</td>
				<td width="100" class="simple" align="right">测点编码：</td>
				<td width="80" class="simple">${item.POINTID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测项分量：</td>
				<td class="simple" colspan="3">${item.ITEMNAME}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">采样率：</td>
				<td width="150"class="simple">${item.SAMPLERATENAME}</td>
				<td width="100" class="simple" align="right">观测方法：</td>
				<td width="80" class="simple">${item.ANALOGORDIGITAL_CN}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">数据起始时间：</td>
				<td width="150"class="simple">${item.STARTDATE}</td>
				<td width="100" class="simple" align="right">数据结束时间：</td>
				<td width="80" class="simple">${item.ENDDATE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">备注：</td>
				<td class="simple" colspan="3">${item.NOTE}</td>
			</tr>
		  </table>
		  </c:forEach>
	</div>
	<div id="tabs-6" style="margin-bottom: -16px;">
		<c:forEach items="${instrParamList}" var="params">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站仪器运行参数信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">仪器号：</td>
				<td class="simple" colspan="3">${params.INSTRID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">台站名称：</td>
				<td width="150"class="simple">${params.STATIONNAME}</td>
				<td width="100" class="simple" align="right">测点编码：</td>
				<td width="80" class="simple">${params.POINTID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">启用时间：</td>
				<td width="150"class="simple">${params.MEASDATE}</td>
				<td width="100" class="simple" align="right">失效日期：</td>
				<td width="80" class="simple">${params.INVALIDDATE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">参数名：</td>
				<td width="150"class="simple">${params.PARANAME}</td>
				<td width="100" class="simple" align="right">参数值：</td>
				<td width="80" class="simple">${params.PARAVALUE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">参数描述：</td>
				<td class="simple" colspan="3">${params.PARADESC}</td>
			</tr>
		  </table>
		  </c:forEach>
	</div>
	<div id="tabs-7" style="margin-bottom: -16px;">
		<c:forEach items="${staCaveList}" var="cave">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站洞体测项信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">台站名称：</td>
				<td width="150"class="simple">${cave.STATIONNAME}</td>
				<td width="100" class="simple" align="right">测点编码：</td>
				<td width="80" class="simple">${cave.POINTID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测项分量代码：</td>
				<td width="150"class="simple">${cave.ITEMNAME}</td>
				<td width="100" class="simple" align="right">洞体名：</td>
				<td width="80" class="simple">${cave.CAVENAME}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">进深：</td>
				<td width="150"class="simple">${cave.ENTRYDISTANCE}</td>
				<td width="100" class="simple" align="right">方向：</td>
				<td width="80" class="simple">${cave.DIRECTION}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">仪器墩方位：</td>
				<td width="150"class="simple">${cave.INSTRDIRECTION}</td>
				<td width="100" class="simple" align="right">仪器长度：</td>
				<td width="80" class="simple">${cave.INSTRLENGTH}</td>
			</tr>
			<tr>
				<td class="simple" align="right">备注：</td>
				<td class="simple" colspan="3">${cave.NOTE}</td>
			</tr>
		  </table>
		  </c:forEach>
	</div>
	<div id="tabs-8" style="margin-bottom: -16px;">
		<c:forEach items="${staWellList}" var="well">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站井泉测项信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">台站名称：</td>
				<td width="150"class="simple">${well.STATIONNAME}</td>
				<td width="100" class="simple" align="right">测点编码：</td>
				<td width="80" class="simple">${well.POINTID}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">测项分量代码：</td>
				<td width="150"class="simple">${well.ITEMNAME}</td>
				<td width="100" class="simple" align="right">井名或泉名：</td>
				<td width="80" class="simple">${well.WELLNAME}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">元件方向：</td>
				<td width="150"class="simple">${well.DIRECTION}</td>
				<td width="100" class="simple" align="right">观测层深度：</td>
				<td width="80" class="simple">${well.MEALAYERDEPTH}(米)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">观测层揭露厚度：</td>
				<td width="150"class="simple">${well.MEALAYERTHICK}</td>
				<td width="100" class="simple" align="right">观测层年代：</td>
				<td width="80" class="simple">${well.ROCKAGE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">观测层岩性：</td>
				<td width="150"class="simple">${well.ROCKTYPE}</td>
				<td width="100" class="simple" align="right">观测层水温：</td>
				<td width="80" class="simple">${well.MEALAYERTEMPERATURE}(度)</td>
			</tr>
			<tr>
				<td class="simple" align="right">备注：</td>
				<td class="simple" colspan="3">${well.NOTE}</td>
			</tr>
		  </table>
		  </c:forEach>
	</div>
</div>
</div></div>
<script type="text/javascript">
  function showBlobVal(schmType,tableName,stationId,pointId,columnName,columnType) {  	
  	var url = '${ctx}/quake/sign/bval/queryBlobVal.do?schemaType='+schmType+'&tableName='
  		+tableName+'&stationId='+stationId+'&pointId='+pointId+'&columnName='+columnName+'&columnType='+columnType;
  	window.open(url,"_black");
  }
</script>
</body>
</html>