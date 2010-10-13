<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<table align="center" width="100%" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
	<tr>
   		<td width="29%" bgcolor="#EBEBEB" align="right">栏目模板：</td>
   		<td width="17%" bgcolor="#EBEBEB">
			<select name="catalogTemplate" id="catalogTemplate"/>
			<s:hidden name="model.cataTemplate.id" id="model.cataTemplate.id"/>
  			</td>
		<td width="54%" bgcolor="#EBEBEB">
			<span class="STYLE1"><span class="prompting">* 栏目的显示模板，其中包含了栏目设计和版式等信息。</span>
		</td>
  	</tr>
	<tr>
   		<td bgcolor="#EBEBEB" align="right">文章模板：</td>
   		<td bgcolor="#EBEBEB">
   			<select name="articleTemplate" id="articleTemplate"/>
   			<s:hidden name="model.artTemplate.id" id="model.artTemplate.id"/>
           </td>
   		<td bgcolor="#EBEBEB">
			<span class="prompting">* 该栏目下文章的默认模板。包含了文章显示页面的设计和版式等信息。</span>
		</td>
	</tr>
	<tr>
           <td bgcolor="#EBEBEB" align="right">是否在首页显示：</td>
 		    <td bgcolor="#EBEBEB">
 		    	<s:radio list="states" name="model.showOnIndex" cssStyle="border:0"/>
			</td>
 		    <td bgcolor="#EBEBEB">
			<span class="prompting">* 如不需要在首页显示请选择‘否’ 。</span>
		</td>
	</tr>
 		<tr>
        	<td bgcolor="#EBEBEB" align="right">是否顶部导航栏显示：</td>
 		   	<td bgcolor="#EBEBEB">
				<s:radio list="states" name="model.showOnTop" cssStyle="border:0"/>
			</td>
 		    <td bgcolor="#EBEBEB">
 		    	<span class="prompting">* 如不需要在顶部导航显示请选择‘否’。</span>
 		    </td>
	</tr>
	<tr>
   		<td width="29%" bgcolor="#EBEBEB" align="right">是否在父栏目导航栏显示：</td>
   		<td bgcolor="#EBEBEB">
   			<s:radio list="states" name="model.showOnParlist" cssStyle="border:0"/>
		</td>
		<td width="54%" bgcolor="#EBEBEB">
			<span class="prompting">* 如果选择否将不在父栏目的栏目列表显示。</span>
		</td>
	</tr>
	<tr>
   		<td width="29%" bgcolor="#EBEBEB" align="right">本栏目是否可用：</td>
   		<td bgcolor="#EBEBEB">
   			<s:radio list="states" name="model.isEnable" cssStyle="border:0"/>
		</td>
		<td width="54%" bgcolor="#EBEBEB"><span class="prompting">* 如果选择否将禁用此栏目 。 </span></td>
	</tr>
</table>