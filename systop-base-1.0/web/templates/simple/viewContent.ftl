<table id="${data.id}" width="100%" align="center" style=" font-size:12px">
<#if data.content?exists>
  <tr>
    <td height="26" align="left">
    <b>${data.content.getTitle()}</b></td>
  </tr>
  <#if data.content.getSubtitle()?exists>
	  <tr>
	    <td height="18" align="left">
	    　　　　${data.content.getSubtitle()}</td>
	  </tr>
	</#if>
  <tr>
    <td align="right" style="padding-top:8px; padding-right:100px;">
    		<img src="/systop-base/images/icons/users.gif"/>作者:${data.author}
    </td>
  </tr>
  <tr>
    <td bgcolor="#000000"></td>
  </tr>
  <tr>
    <td style="padding-top:10px; padding-right:10px;padding-left:10px;padding-bottom:10px;">
    	&nbsp;&nbsp;&nbsp;&nbsp; ${data.content.getBody()}
    </td>
  </tr>
<#else>
	<tr><td>
		<img src='/systop-base/images/icons/sound_2.gif'/>&nbsp;访问的数据不存在...
	</td></tr>
</#if>
</table>