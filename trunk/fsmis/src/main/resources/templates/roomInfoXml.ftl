<#setting number_format="0"/> 
<room>
    <name>${data.room.name}</name>   
    <remark>${data.room.remark?default("")}</remark> 
    <members>
        <#list data.members as member>
           <member>
	       <id>${member.id?default("")}</id>
               <name>${member.getName()?default("")}</name>
               <deptName>${member.dept.name?default("")}</deptName>     
           </member>
        </#list>
    </members>
</room>