<table align="center" width="100%">
<#if data.list?exists>
  <#list data.list as content>
  	<tr>
    	<td>${content.getTitle()}</td>
    </tr>
  </#list>
  <#else>
  <tr>
  	<td>没有数据.</td>
  </tr>
</#if>

</table>