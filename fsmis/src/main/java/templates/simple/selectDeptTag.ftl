<div style="width:${data.column * data.columnWidth};">
	<#assign i = 0>
	<#list data.depts as dept>  
		<#assign i = i + 1>
		<div style="width: ${data.columnWidth};float: left;border-bottom:${data.splitLineStyle?if_exists};margin-top: 5px; margin-left: -1px;">
			<input type="checkbox" name="${data.name?if_exists}" class="${data.itemClass?if_exists}" value="${dept.id}" ${dept.checked}>${dept.name}
		</div>
	</#list>  
</div>