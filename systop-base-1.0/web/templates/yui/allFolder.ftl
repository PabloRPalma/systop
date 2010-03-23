<table align="center" width="100%">
<#if data.list?exists>
<tr>
  <#list data.list as content>
    <td>${content.getTitle()}</td>
  </#list>
  <#else>
  	<td>没有数据.</td>
  </tr>
</#if>

</table>