<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="${ctx}/charts/amline/swfobject.js"></script>
</head>
<body>
<div id="line-1">

<strong>你需要升级你的Flash播放器。</strong>

</div>               
                  

<script type="text/javascript">
/**
* 当曲线图zoom区域改变的时候，将自动触发这个function
*/
function amGetZoom(chart_id, from, to){
  
}

var flashMovie; 
function amChartInited(chart_id){
  flashMovie = document.getElementById(chart_id);
}
// <![CDATA[		

var so = new SWFObject("${ctx}/charts/amline/amline.swf", "amline", "840", "420", "8", "#FFFFFF");
so.addVariable("chart_id", "amline"); // if you have more then one chart in one page, set different chart_id for each chart	
so.addVariable("path", "${ctx}/charts/amline/");   
so.addVariable("settings_file", escape("${ctx}/datashare/sign/data/lineSettings.do${model}"));
so.addVariable("data_file", escape("${ctx}/datashare/sign/data/csv.do${model}"));
so.addVariable("preloader_color", "#999999");
so.addVariable("wmode", "opaque");
so.write("line-1");

// ]]>

</script>
<button value="Reload" onclick="flashMovie.reloadData(escape('${ctx}/datashare/sign/data/csv.do${model}'), false)">Reload</button>

</body>
</html>