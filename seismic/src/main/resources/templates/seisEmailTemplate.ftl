<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
th{background-color:#efefef;}
body,th,td{font-size:12px;}
</style>
</head>
<body>
您的订阅详情是：<br>
<table border="1">
<thead>
   <tr>
      <th>地震目录</th>
      <th>电子邮件</th>
      <th>最小震级</th>
      <th>最大震级</th>
      <th>起始经度</th>
      <th>结束经度</th>
      <th>起始纬度</th>
      <th>结束纬度</th>
   </tr>
</thead>
<tbody>
  <tr>
      <td>${data.mail.catalogName!'&nbsp;'}</td>
      <td>${data.mail.emailAddr!'&nbsp;'}</td>
      <td>${data.mail.minM!'&nbsp;'}</td>
      <td>${data.mail.maxM!'&nbsp;'}</td>
      <td>${data.mail.startEpiLon!'&nbsp;'}</td>
      <td>${data.mail.endEpiLon!'&nbsp;'}</td>
      <td>${data.mail.startEpiLat!'&nbsp;'}</td>
      <td>${data.mail.endEpiLat!'&nbsp;'}</td>
   </tr>
</tbody>
</table>
<br>
地震目录：
<table border="1">
<thead>
   <tr>
        <th width="130" property="O_TIME">发震时刻</th>	
		<th width="80" property="O_TIME_FRAC">1/10000秒</th>	
		<th width="80" property="EPI_LAT">震中纬度</th>	
		<th width="80" property="EPI_LON">震中经度</th>
		<th width="60" property="M">M</th>		
		<th width="60" property="M_SOURCE">震级来源</th>
		<th width="60" property="MAG_VAL">震级来源值</th>
		<th width="80" property="EPI_DEPTH">深度(Km)</th>	
		<th width="60" property="QLOC">定位质量</th>	
		<th width="60" property="QCOM">综合质量</th>	
		<th width="120" property="LOCATION_CNAME">震中地名</th>
   </tr>
<tbody>
  <#list data.events as event>
  <tr>
       <td>
       <#if event.O_TIME?exists>
         ${event.O_TIME?string('yyyy-MM-dd HH:mm:ss')}
       </#if>
       </td>
       <td>${event.O_TIME_FRAC!'&nbsp;'}</td>
       <td>${event.EPI_LAT!'&nbsp;'}</td>
       <td>${event.EPI_LON!'&nbsp;'}</td>
       <td>
       	<#if event.M??>
       		${event.M?string('##.0')}
       	</#if>
       </td>
       <td>${event.M_SOURCE!'&nbsp;'}</td>
       <td>
       	 <#if event.MAG_VAL??>
         	${event.MAG_VAL?string('##.0')}
       	 </#if>
       </td>
       <td>${event.EPI_DEPTH?int}</td>
       <td>${event.QLOC!'&nbsp;'}</td>
       <td>${event.QCOM!'&nbsp;'}</td>
       <td>${event.LOCATION_CNAME!'&nbsp;'}</td>       
  <tr>
  </#list>
</tbody>
</table> 
</body>
</html>