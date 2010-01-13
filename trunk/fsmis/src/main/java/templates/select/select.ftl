<#-- 不使用该死的数字格式化功能 -->
<#setting number_format="0"/> 
<select name="${data.name?if_exists}" id="${data.name?if_exists}" class="${data.itemClass?if_exists}">
	<option value="0">请选择部门</option>
	<#list data.depts?if_exists as dept>
		<option value="${dept.id}">${dept.name}</option>
	</#list>  
</select>
<div style="color:red"><b>${data.errorMsg?if_exists}</b></div>