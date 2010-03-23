<table align="center"  cellspacing="5" width="100%">

<#if data.list?exists>
	<tr><td background="images/index_03.jpg" style="padding-top:2px; padding-left:3px">
		${data.title}
	</td></tr>
	
  <#list data.list as content>
  <tr><td>
   	<#assign titleLength = content.getTitle()?length?number>
    <img src="/systop-base/images/icons/articleIco.gif"/>
    <a href="${data.openPage}?contentId=${content.getId()?string('##')}" target="${data.target}" title="${ content.getTitle()}">
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