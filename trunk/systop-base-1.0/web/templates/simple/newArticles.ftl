<table id="${data.id}" align="center"  cellspacing="5" width="100%" style="font-size:12px">

<#if data.list?exists>
  <#list data.list as content>
  <tr><td>
   	<#assign titleLength = content.getTitle()?length?number>
    <img src="/systop-base/images/icons/articleIco.gif"/>
 		<a href="eg/veiwContent.jsp?contentId=${content.getId()?string('##')}" target="${data.target}" title="${ content.getTitle()}">
	<#if titleLength < 13>
		${ content.getTitle()}
	<#else>
		${ content.getTitle()?substring(0,12)}...
	</#if></a>
  </#list>
  <tr><td>
  <#else>
  <tr><td>
 		<img src='/systop-base/images/icons/sound_2.gif'/>&nbsp;访问的数据不存在...
  <tr><td>
</#if>
</td></tr>
</table>