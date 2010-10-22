<?xml version="1.0" encoding="UTF-8" ?>

<GIS>
<TITLE>省(市)测震台站分布图</TITLE>
  <#list data.stations as sta>
	<STATION>
		<NAME>${sta.STA_CNAME?default("null")}</NAME>
		<LONGITUDE>${sta.STA_LON?default("null")}</LONGITUDE>
		<LATITUDE>${sta.STA_LAT?default("null")}</LATITUDE>
		<ELVETUDE>${sta.STA_ELEV?default("null")}</ELVETUDE>
		<ROCK_TYPE>${sta.ROCK_TYPE?default("null")}</ROCK_TYPE>
	</STATION>
  </#list>
</GIS>