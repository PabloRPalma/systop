<div style="width: 448px;" id="${data.id}">
<#if data.content?exists>
	<h2 style="background: url(/systop-base/images/h2-bg.gif) no-repeat;	background-position: bottom;
						font-family: Arial, Helvetica, sans-serif; font-size: 16px;
						font-weight: normal; letter-spacing: -1px; padding: 0 0 17px 0;">
		${data.content.getTitle()}
	</h2>		
		<#assign bodyLength = data.content.getBody()?length?string('##')?number>
		<#if data.imgSrc?exists>		
			<div class="image">
				<img src="${data.imgSrc}" width="${data.imgWidth}" height="${data.imgHeight}"/>
			</div>
		</#if>
		<#if bodyLength < data.wordCount>
			<p>${data.content.getBody()}</p>
		<#else>
			<p>${data.content.getBody()?substring(0,data.wordCount)}... ...</p>
		</#if>
	
		<p class='date'>
			<img src='/systop-base/images/icons/timeicon.gif'/> 
			<a href="/systop-base/eg/veiwContent.jsp?contentId=${data.content.getId()?string('##')}" target="_blank" title="阅读全文">Read me</a>&nbsp;&nbsp;
			<img src='/systop-base/images/icons/comment.gif'/><a href='#'>${data.author}</a>&nbsp;&nbsp;
			<img src='/systop-base/images/icons/more.gif'/>${data.content.getUpdateTime()}
		</p>

<#else>
		<img src='/systop-base/images/icons/sound_2.gif'/>&nbsp;访问的数据不存在...
</#if>
</div>