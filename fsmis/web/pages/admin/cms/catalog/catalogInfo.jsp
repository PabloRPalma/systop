<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
<!--
	jQuery(document).ready(function() {			
		var data = ["_self","_blank","_parent","_top"];
		jQuery("#target").autocomplete(data, {minChars: 0,
			autoFill: true,
			mustMatch: true,
			matchContains: true});  
	});    
//-->
</script>
<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
    <tr>
	  	<td align="right" bgcolor="#EBEBEB">所属栏目：</td>
		<td colspan="2" bgcolor="#EBEBEB">
			<select id="parentId" name="parentId"/>
			<s:hidden name="model.parentCatalog.id" id="model.parentCatalog.id"/>
	  	</td>
		<td width="24%" bgcolor="#EBEBEB"><span class="prompting"> * (注：外部栏目不在此列表显示)</span></td>
    </tr>
    <tr>
		<td bgcolor="#EBEBEB" align="right">栏目名称：</td>
		<td colspan="2" bgcolor="#EBEBEB"><s:textfield id="model.name" name="model.name" maxLength="20"/></td>
		<td bgcolor="#EBEBEB"><span class="prompting"> * 不能使用特殊符号(如：@、#、$、%等符号). </span></td>
	</tr>
	<tr>
	  	<td rowspan="2" bgcolor="#EBEBEB" align="right">
	  		栏目类型：
	  		<s:hidden name="model.type" id="model.type"/>
	  	</td>
		<td colspan="2" bgcolor="#EBEBEB">
			<input type="radio" name="radiobutton" id="inner" value="1" checked="checked" onClick="showInner()">
			<span class="STYLE2">
		  		内部栏目
		  	</span> 
		  	内部栏目可以添加文章和子栏目.
		  	<br>
		  	<div id="root">
			  	内部栏目的目录名：
			  	<s:textfield id="model.root" name="model.root" maxLength="25"/>
			</div>
		</td>
		<td bgcolor="#EBEBEB">
			<span class="prompting"> 
				* 目录名称只能是英文字符,不能带空格或“\”,“/”等符号.
			</span>
		</td>
    </tr>
    <tr>
		<td colspan="2" bgcolor="#EBEBEB">
			<input type="radio" name="radiobutton" id="external" value="2" onClick="showExternal()">
		  	<span class="STYLE2">
		  		外部栏目
		  	</span> 
		  	不能添加文章和子栏目。用于添加一个有效链接.<br> 
		  	<div id="externalUrl" style=" display:none">
			  栏目地址：
			  	<s:textfield id="model.linkUrl" name="model.linkUrl"  maxLength="200" cssStyle="width:400px"/>
		  	</div>
		</td>
		<td bgcolor="#EBEBEB"><span class="prompting">* 外部栏目地址请填写有效的网址.</span></td>
	 </tr>
	 <tr>
           <td bgcolor="#EBEBEB" align="right">栏目地址打开目标：</td>
 		    <td colspan="2" bgcolor="#EBEBEB">
 		    	<s:textfield id="target" name="model.target" maxLength="20"/>
			</td>
 		    <td bgcolor="#EBEBEB">
			<span class="prompting">* eg:[_self _blank _parent].也可以是自定义的目标</span>
		</td>
	</tr>
	 <tr>
	   	<td bgcolor="#EBEBEB" align="right">栏目说明：</td>
		<td colspan="2" bgcolor="#EBEBEB" align="left">
		  	<s:textarea name="model.descn" id="model.descn" rows="6" cols="50"/>
	   	</td>
		<td bgcolor="#EBEBEB"><span class="prompting">* 栏目的简单介绍说明.</span></td>
    </tr>
</table>
