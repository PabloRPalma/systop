<table id="${data.id}" align="center" cellpadding="1" cellspacing="1" width="100%">
<#if data.list?exists>
<tr>
  <#list data.list as content>
    <td>
    	<img style="padding-top:2px;padding-left:2px" src="/systop-base/images/icons/folder_add.gif"/> <a href="/cms/viewFolder.action?id='${content.getId()}'" target="_blank">${content.getTitle()}</a></td>
  </#list>
  <#else>
  	<td><img src='/systop-base/images/icons/sound_2.gif'/>&nbsp;访问的数据不存在...</td>
  </tr>
</#if>

</table>