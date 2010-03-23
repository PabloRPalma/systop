<#if data.catalogs?exists>
  <#list data.catalogs as c>   
       <input type="checkbox" id="${data.name}" name="${data.name}"<#if data.onclick?exists> 
onclick="${data.onclick}" </#if><#if data.onblur?exists> 
onblur="${data.onblur}" </#if><#if data.style?exists> 
style="${data.style}" </#if><#if data.styleClass?exists> 
class="${data.styleClass}" </#if> value="${c.getValue()}"
<#--如果当前值被选中，或者当前值等于指定的缺省值-->
<#if c.getSelected()==true || (data.defaultValue?exists && data.defaultValue==c.getValue())>checked </#if>>${c.getLabel()}&nbsp;
<#if data.multiLine == true><br></#if>
  </#list>
</#if>