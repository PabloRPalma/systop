<#-- 不使用该死的数字格式化功能 -->
<#setting number_format="0"/> 
<div id="view" style="width:${data.column * data.columnWidth};"></div>
<div style="width:${data.column * data.columnWidth};">
	<#list data.depts?if_exists as dept>  
		<div style="width: ${data.columnWidth};float: left;border-bottom:${data.splitLineStyle?if_exists};margin-top: 5px; margin-left: -1px;">
			<input type="checkbox" name="${data.name?if_exists}" class="${data.itemClass?if_exists}" value="${dept.id}" ${dept.checked}>${dept.name}
		</div>
	</#list>  
	<div style="color:red"><b>${data.errorMsg?if_exists}</b></div>
</div>
