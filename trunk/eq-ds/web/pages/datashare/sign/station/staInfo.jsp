<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<link href="${ctx}/styles/datashare.css" type='text/css' rel='stylesheet'>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(function(){
	var statCaveSize = $('#statCaveSize').val();
	var statWellSize = $('#statWellSize').val();
	var statSpringSize = $('#statSpringSize').val();
	var faultsSize = $('#faultsSize').val();
	if (statCaveSize == 0) {
		$('#statCave').hide();
	}
	if (statWellSize == 0) {
		$('#statWell').hide();
	}
	if (statSpringSize == 0) {
		$('#statSpring').hide();
	}
	if (faultsSize == 0) {
		$('#faults').hide();
	}
	// Tabs
	$('#tabs').tabs();
});
</script>
<title>台站基础数据</title>
<style type="text/css">
#mytable {border:1px solid #A6C9E2;margin-left: -21px; margin-top:-1px; width: 97%; border-collapse: collapse; }
#mytable td{border:1px solid #A6C9E2;height: 24;}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-body">
<div id="tabs">
	<s:hidden id="statCaveSize" name="model.statCaveSize" />
	<s:hidden id="statWellSize" name="model.statWellSize" />
	<s:hidden id="statSpringSize" name="model.statSpringSize" />
	<s:hidden id="faultsSize" name="model.faultsSize" />
	<ul>
		<li><a href="#tabs-1">台站</a></li>
		<li id="statCave"><a href="#tabs-2">洞体</a></li>
		<li id="statWell"><a href="#tabs-3">井信息</a></li>
		<li id="statSpring"><a href="#tabs-4">泉信息</a></li>
		<li id="faults"><a href="#tabs-5">断层</a></li>
	</ul>
	<div id="tabs-1" style="margin-bottom: -16px;">
		<table id="mytable">
			<tr>
				<td bgcolor="#DDECF7" height="30" align="center" colspan="4">台站基础数据</td>
			</tr>
			<tr>
				<td width="122" class="simple" align="right">台站代码：</td>
				<td class="simple">${station.STATIONID}</td>
				<td class="simple" align="right">台站名称：</td>
				<td class="simple">${station.STATIONNAME}</td>
			</tr>
			<tr>
				<td class="simple" align="right">建台改造日期：</td>
				<td class="simple">${station.STATIONCONSDATE}</td>
				<td class="simple" align="right">高程：</td>
				<td class="simple">${station.ALTITUDE}</td>
			</tr>
			<stc:role ifAllGranted="ROLE_LONGLAT">
			  <tr>
				<td class="simple" align="right">经度：</td>
				<td class="simple">${station.LONGITUDE}</td>
				<td class="simple" align="right">纬度：</td>
				<td class="simple">${station.LATITUDE}</td>
			  </tr>
			</stc:role>
			<tr>
				<td class="simple" align="right">主管单位：</td>
				<td class="simple">${station.UNITCODE}</td>
				<td class="simple" align="right">台基岩性：</td>
				<td class="simple">${station.STATIONBASEROCK}</td>
			</tr>
			<tr>
				<td class="simple" align="right">台站编码：</td>
				<td class="simple">${station.STATIONOLDID}</td>
				<td class="simple" align="right">两位代码：</td>
				<td class="simple">${station.STATID_2}</td>
			</tr>
			<tr>
				<td class="simple" align="right">台址勘选情况：</td>
				<td class="simple" colspan="3">${station.STATIONSITEDETAIL}</td>
			</tr>
			<tr>
				<td class="simple" align="right">地震地质条件：</td>
				<td class="simple" colspan="3">${station.STATIONGEOLOGYCONDITION}</td>
			</tr>
			<tr>
				<td class="simple" align="right">周围地震活动性背景：</td>
				<td class="simple" colspan="3">${station.STASEISMICITY}</td>
			</tr>
			<tr>
				<td class="simple" align="right">占地面积：</td>
				<td class="simple" colspan="3">${station.STATIONGROUNDAREA}</td>
			</tr>
			<tr>
				<td class="simple" align="right">值守方式：</td>
				<td class="simple">${station.STATIONMGMTMODE}</td>
				<td class="simple" align="right">值班电话：</td>
				<td class="simple">${station.STATIONDUTYPHONE}</td>
			</tr>
			<tr>
				<td class="simple" align="right">通讯地址：</td>
				<td class="simple" colspan="3">${station.STATIONMAILADDRESS}</td>
			</tr>
			<tr>
				<td class="simple" align="right">自然地理：</td>
				<td class="simple" colspan="3">${station.STATIONNATURALGEOGRAPHY}</td>
			</tr>
			<tr>
				<td class="simple" align="right">气候特征：</td>
				<td class="simple" colspan="3">${station.STATIONWEATHER}</td>
			</tr>
			<tr>
				<td class="simple" align="right">历史沿革：</td>
				<td class="simple" colspan="3">${station.STATIONHISTORY}</td>
			</tr>
			<tr>
				<td class="simple" align="right">工作生活条件：</td>
				<td class="simple" colspan="3">${station.STATIONWORKINGLIVINGFACILITY}</td>
			</tr>
			<tr>
				<td class="simple" align="right">台站基本情况描述：</td>
				<td class="simple" colspan="3">${station.DESCRIPTION}</td>
			</tr>
			<tr>
				<td class="simple" align="right">台站建设报告：</td>
				<td class="simple" colspan="3">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATIONS','${station.STATIONID}','','STATIONREPORT','WORD')">报告</a>
				</td>
			</tr>
			<tr>
				<td class="simple" align="right">台站平面分布图：</td>
				<td class="simple" colspan="3">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATIONS','${station.STATIONID}','','STATIONPHOTO','PHOTO')">图片</a>
				</td>
			</tr>
			<tr>
				<td class="simple" align="right">测点分布图：</td>
				<td class="simple" colspan="3">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATIONS','${station.STATIONID}','','STATIONPNTMAP','PHOTO')">图片</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="tabs-2" style="margin-bottom: -16px;">
		<c:forEach items="${statCaveList}" var="cave">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站洞体信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">洞体名称：</td>
				<td width="150"class="simple">${cave.CAVENAME}</td>
				<td width="100" class="simple" align="right">建设改造日期：</td>
				<td width="80" class="simple">${cave.CONSTRUCTDATE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">山洞岩性：</td>
				<td width="150"class="simple">${cave.ROCKTYPE}</td>
				<td width="100" class="simple" align="right">洞顶覆盖层厚度：</td>
				<td width="80" class="simple">${cave.UPDEPTH}(米)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">洞内温度：</td>
				<td width="150"class="simple">${cave.TEMPERATURE}(度)</td>
				<td width="100" class="simple" align="right">洞内湿度：</td>
				<td width="80" class="simple">${cave.HUMIDITY}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">年温差：</td>
				<td width="150"class="simple">${cave.TEMPVARIATION}(度)</td>
				<td class="simple" align="right">洞体图片：</td>
				<td class="simple">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATCAVE','${station.STATIONID}','','CAVEMAP','PHOTO')">图片</a>
				</td>
			</tr>
		  </table>
		</c:forEach>
	</div>
	<div id="tabs-3" style="margin-bottom: -16px;">
		<c:forEach items="${statWellList}" var="well">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站井信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">井名：</td>
				<td width="150"class="simple">${well.WELLNAME}</td>
				<td width="100" class="simple" align="right">成井单位：</td>
				<td width="80" class="simple">${well.CONSTRUNIT}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">建设改造日期：</td>
				<td width="150"class="simple">${well.CONSTRUCTDATE}</td>
				<td width="100" class="simple" align="right">成井日期：</td>
				<td width="80" class="simple">${well.FINISHDATE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">干扰源：</td>
				<td width="150"class="simple">${well.DISTURBFACTOR}</td>
				<td width="100" class="simple" align="right">孔口标高：</td>
				<td width="80" class="simple">${well.WELLMOUTHALTITUDE}(米)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">构造部位：</td>
				<td width="150"class="simple">${well.WELLLOCATION}</td>
				<td width="100" class="simple" align="right">水文地质条件：</td>
				<td width="80" class="simple">${well.GEOCONDITION}</td>
			</tr>
			
			<tr>
				<td width="120" class="simple" align="right">完钻井深：</td>
				<td width="150"class="simple">${well.WELLINIDEPTH}(米)</td>
				<td width="100" class="simple" align="right">现有井深：</td>
				<td width="80" class="simple">${well.WELLCURDEPTH}(米)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">孔径：</td>
				<td width="150"class="simple">${well.HOLEDIAMETER}(毫米)</td>
				<td width="100" class="simple" align="right">钻孔倾向：</td>
				<td width="80" class="simple">${well.HOLETENDENCY}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">钻孔倾角：</td>
				<td width="150"class="simple">${well.HOLEDIPANGLE}(度)</td>
				<td width="100" class="simple" align="right">变径情况：</td>
				<td width="80" class="simple">${well.DIACHANGECONDITION}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">止水情况：</td>
				<td width="150"class="simple">${well.WATERSTOPCONDITION}</td>
				<td width="100" class="simple" align="right">含水层与井孔接触类型：</td>
				<td width="80" class="simple">${well.CONTACTTYPE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">套管主要参数：</td>
				<td width="150"class="simple">${well.MAINPARAMETER}</td>
				<td width="100" class="simple" align="right">射孔资料：</td>
				<td width="80" class="simple">${well.PERFORATINGDATA}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">井斜情况：</td>
				<td width="150"class="simple">${well.SLIPCONDITION}</td>
				<td width="100" class="simple" align="right">水温：</td>
				<td width="80" class="simple">${well.WTEMPERATURE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">井类型：</td>
				<td width="150"class="simple">${well.WELLTYPE}</td>
				<td width="100" class="simple" align="right">渗透系数：</td>
				<td width="80" class="simple">${well.PERMEABILITYCOEFFICIENT}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">观测含水层的观测段：</td>
				<td width="150"class="simple">${well.OBSERVEDZONE}</td>
				<td width="100" class="simple" align="right">观测含水层的岩性：</td>
				<td width="80" class="simple">${well.AQUIFERLITH}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">观测含水层的揭露厚度：</td>
				<td width="150"class="simple">${well.OPENTHICKNESS}</td>
				<td width="100" class="simple" align="right">地下水埋藏类型：</td>
				<td width="80" class="simple">${well.WATERTYPE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">抽水试验资料：</td>
				<td width="150"class="simple">${well.PUMPTESTDATA}</td>
				<td width="100" class="simple" align="right">地下水宏观描述：</td>
				<td width="80" class="simple">${well.MACRODESC}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">矿化度：</td>
				<td width="150"class="simple">${well.MINERALDEGREE}(g/l)</td>
				<td width="100" class="simple" align="right">pH值：</td>
				<td width="80" class="simple">${well.PHVALUE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">水化学类型：</td>
				<td width="150"class="simple">${well.HYDROCHEMTYPE}</td>
				<td width="100" class="simple" align="right">常规元素：</td>
				<td width="80" class="simple">${well.ROUTELEMENT}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">微量元素：</td>
				<td width="150"class="simple">${well.MICROELEMENT}</td>
				<td width="100" class="simple" align="right">其它化学成分：</td>
				<td width="80" class="simple">${well.OTHERCOMPONENT}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">井房面积：</td>
				<td width="150"class="simple">${well.HOUSEAREA}(平方米)</td>
				<td width="100" class="simple" align="right">井房条件：</td>
				<td width="80" class="simple">${well.HOUSECONDITION}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">井口装置描述：</td>
				<td width="150" class="simple" colspan="3">${well.WELLMOUTHSETDESC}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">井口装置示意图：</td>
				<td width="150"class="simple">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATWELL','${station.STATIONID}','','WELLMOUTHSETSKMAP','PHOTO')">图片</a></td>
				<td width="100" class="simple" align="right">集气装置示意图：</td>
				<td width="80" class="simple">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATWELL','${station.STATIONID}','','COLLGASSETSKMAP','PHOTO')">图片</a></td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">钻孔地层柱状图：</td>
				<td width="150"class="simple">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATWELL','${station.STATIONID}','','MTDLAYOUTMAP','PHOTO')">图片</a></td>
				<td width="100" class="simple" align="right">井区地质简图：</td>
				<td width="80" class="simple">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATWELL','${station.STATIONID}','','GEOMAPWELLAREA','PHOTO')">图片</a></td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">备注：</td>
				<td class="simple" colspan="3">${well.NOTE}</td>
			</tr>
		  </table>
		</c:forEach>
	</div>
	<div id="tabs-4" style="margin-bottom: -16px;">
		<c:forEach items="${statSpringList}" var="spring">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站泉信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">泉名：</td>
				<td width="150"class="simple">${spring.SPRINGNAME}</td>
				<td width="100" class="simple" align="right">建设改造日期：</td>
				<td width="80" class="simple">${spring.CONSTRUCTDATE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">泉类型：</td>
				<td width="150"class="simple">${spring.SPRINGTYPE}</td>
				<td width="100" class="simple" align="right">出露条件：</td>
				<td width="80" class="simple">${spring.OUTCONDITION}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">构造部位：</td>
				<td width="150"class="simple">${spring.WELLLOCATION}</td>
				<td width="100" class="simple" align="right">水文地质条件：</td>
				<td width="80" class="simple">${spring.HYGEOCONDITION}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">水温：</td>
				<td width="150"class="simple">${spring.WTEMPERATURE}</td>
				<td width="100" class="simple" align="right">矿化度：</td>
				<td width="80" class="simple">${spring.MINERALDEGREE}(g/l)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">pH值：</td>
				<td width="150"class="simple">${spring.PHVALUE}</td>
				<td width="100" class="simple" align="right">水化学类型：</td>
				<td width="80" class="simple">${spring.HYDROCHEMTYPE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">常规元素：</td>
				<td width="150"class="simple">${spring.ROUTELEMENT}</td>
				<td width="100" class="simple" align="right">微量元素：</td>
				<td width="80" class="simple">${spring.MICROELEMENT}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">深源气体：</td>
				<td width="150"class="simple">${spring.PLUTGAS}</td>
				<td width="100" class="simple" align="right">其它化学成分：</td>
				<td width="80" class="simple">${spring.OTHERCOMPONENT}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">观测泉眼涌水量：</td>
				<td width="150"class="simple">${spring.OUTFLOW}(m3/s)</td>
				<td width="100" class="simple" align="right">总泉眼涌水量：</td>
				<td width="80" class="simple">${spring.TOTALOUTFLOW}(米)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">泉区水文地质剖面图：</td>
				<td width="150"class="simple">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATSPRING','${station.STATIONID}','','HYDROPROFILE','PHOTO')">图片</a></td>
				<td width="100" class="simple" align="right">泉图片：</td>
				<td width="80" class="simple">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_STATSPRING','${station.STATIONID}','','WELLPHOTO','PHOTO')">图片</a></td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">备注：</td>
				<td class="simple" colspan="3">${spring.NOTE}</td>
			</tr>
		  </table>
		</c:forEach>
	</div>
	<div id="tabs-5" style="margin-bottom: -16px;">
		<c:forEach items="${statFaultsList}" var="fault">
		  <table id="mytable">
		  	<tr>
				<td bgcolor="#DDECF7" align="center" colspan="4">台站泉信息</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">断层名：</td>
				<td width="150"class="simple">${fault.FAULTNAME}</td>
				<td width="100" class="simple" align="right">断层所属断裂带：</td>
				<td width="80" class="simple">${fault.FAULTTECTONICS}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">断层性质：</td>
				<td width="150"class="simple">${fault.FAULTTYPE}</td>
				<td width="100" class="simple" align="right">断层走向：</td>
				<td width="80" class="simple">${fault.FAULTSTRIKE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">断层错动方向：</td>
				<td width="150"class="simple">${fault.FAULTMOVDIRECTION}</td>
				<td width="100" class="simple" align="right">断层倾角：</td>
				<td width="80" class="simple">${fault.FAULTDIPANGLE}(度)</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">断层上盘岩性：</td>
				<td width="150"class="simple">${fault.UPPERROCKTYPE}</td>
				<td width="100" class="simple" align="right">断层下盘岩性：</td>
				<td width="80" class="simple">${fault.LOWERROCKTYPE}</td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">断层图片：</td>
				<td width="150"class="simple" colspan="3">
					<a href="#" onclick="showBlobVal('QZ','QZ_DICT_FAULTS','${station.STATIONID}','','FAULTPHOTO','PHOTO')">图片</a></td>
			</tr>
			<tr>
				<td width="120" class="simple" align="right">备注：</td>
				<td class="simple" colspan="3">${fault.NOTE}</td>
			</tr>
		  </table>
		</c:forEach>
	</div>
  </div>
 </div>
</div>
<script type="text/javascript">
  function showBlobVal(schmType,tableName,stationId,pointId,columnName,columnType) {  	
  	var url = '${ctx}/datashare/sign/bval/queryBlobVal.do?schemaType='+schmType+'&tableName='
  		+tableName+'&stationId='+stationId+'&pointId='+pointId+'&columnName='+columnName+'&columnType='+columnType;
  	window.open(url,"_black");
  }
</script>
</body>
</html>