<?xml version="1.0" encoding="UTF-8" ?>

<GIS>
<TITLE>省(市)前兆台站分布图</TITLE>
  <#list data.stations as sta>
	<STATION>
		<NAME>${sta.STATIONNAME?default("null")}</NAME>
		<LONGITUDE>${sta.LONGITUDE?default("null")}</LONGITUDE>
		<LATITUDE>${sta.LATITUDE?default("null")}</LATITUDE>
		<ELVETUDE>${sta.ALTITUDE?default("null")}</ELVETUDE>
		<ROCK_TYPE>${sta.STATIONBASEROCK?default("null")}</ROCK_TYPE>
	</STATION>
  </#list>
</GIS>