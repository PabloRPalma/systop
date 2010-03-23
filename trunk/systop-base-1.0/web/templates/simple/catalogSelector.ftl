
<select id="${data.name}" name="${data.name}"<#if data.onclick?exists> 
onclick="${data.onclick}" </#if><#if data.size?exists> 
size="${data.size}" </#if><#if data.onchange?exists> 
onchange="${data.onchange}" </#if><#if data.onblur?exists> 
onblur="${data.onblur}" </#if><#if data.multiLine == true>
multiple="multiple"</#if><#if data.style?exists> 
style="${data.style}" </#if><#if data.styleClass?exists> 
class="${data.styleClass}" </#if>>
<#--指定的缺省值，不在catalog之内-->
<#if data.defaultValue?exists && data.defaultLabel?exists && data.defaultLabel != "">
       <option value="${data.defaultValue}">${data.defaultLabel}</option>
</#if>
<#--catalogs遍历-->
<#if data.catalogs?exists>
  <#list data.catalogs as c>   
       <#--如果当前值被选中（结果记录在Request中）-->
       <#if c.getSelected() == true>
          <option selected="selected" value="${c.getValue()}">${c.getLabel()}</option>
       <#else>
          <#--如果指定了缺省值，并且所有值都没有选中，并且缺省值等于当前值-->
          <#if data.defaultValue?exists && !data.hasSelected && data.defaultValue == c.getValue()>
                <option selected="selected" value="${c.getValue()}">${c.getLabel()}</option>
             <#else>
                <option value="${c.getValue()}">${c.getLabel()}</option>
          </#if>
       </#if> 
  </#list>
</#if>
</select>