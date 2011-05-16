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
      <th>台站名称</th>
      <th>台站ID</th>
      <th>测项分量</th>
      <th>测项ID</th>
      <th>测点代码</th>
      <th>采样率</th>
      <th>数据类型</th>
   </tr>
</thead>
<tbody>
  <tr>
      <td>${data.mail.stationName!''}</td>
      <td>${data.mail.stationId!''}</td>
      <td>${data.mail.itemName!''}</td>
      <td>${data.mail.itemId!''}</td>
      <td>${data.mail.pointId!''}</td>
      <td>${data.mail.sampleRate!''}</td>
      <td>${data.mail.dataType!''}</td>
   </tr>
</tbody>
</table>
<br>
<table border="1">
<thead>
   <tr>
        <th width="130">观测时间</th>	
		<th width="80">观测值</th>	
   </tr>
<tbody>
  <#list data.events as event>
  <tr>
       <td>${event.strTime!'&nbsp;'}</td>
       <td>${event.value!'&nbsp;'}</td>     
  <tr>
  </#list>
</tbody>
</table> 
</body>
</html>