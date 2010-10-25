<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
 	<tr>
        <td width="20%" bgcolor="#EBEBEB" align="right">浏览权限：</td>
    	<td width="80%" bgcolor="#EBEBEB">
    		<input type="radio" name="view" value="0" checked="checked" onClick="showViewQxDiv(this.value)">
      		<span class="STYLE2">开放栏目</span>&nbsp;&nbsp;任何人(包括游客)可以浏览和查看此栏目下的信息。
  			<br>
  			<br>
	  		<input type="radio" name="view" value="1" onClick="showViewQxDiv(this.value)">
	  		<span class="STYLE2">认证栏目</span>&nbsp;&nbsp;游客不可查看，根据栏目权限设置决定是否可以查看。
	  		<div id="viewQxDiv" style="display:none; border-style:dotted; border-width:1px">
	  			<input type="checkbox" name="view" value="checkbox">普通会员
				<input type="checkbox" name="view" value="checkbox">文章发布员
				<input type="checkbox" name="view" value="checkbox">文章审核员
				<input type="checkbox" name="view" value="checkbox">栏目管理员
			  	<input type="checkbox" name="view" value="checkbox">系统管理员
		  	</div>	  	    
		</td>
 	</tr>
	<tr>
	  	<td bgcolor="#EBEBEB" align="right">发布权限：</td>
		<td bgcolor="#EBEBEB">
			<input type="checkbox" name="view" value="checkbox">普通会员
			<input type="checkbox" name="view" value="checkbox">文章发布员
			<input type="checkbox" name="view" value="checkbox">文章审核员
			<input type="checkbox" name="view" value="checkbox">栏目管理员
			<input type="checkbox" name="view" value="checkbox">系统管理员
		</td>
	</tr>
	<tr>
	  	<td bgcolor="#EBEBEB" align="right">审核权限：</td>
		<td bgcolor="#EBEBEB">
			<input type="checkbox" name="view" value="checkbox">普通会员
			<input type="checkbox" name="view" value="checkbox">文章发布员
			<input type="checkbox" name="view" value="checkbox">文章审核员
			<input type="checkbox" name="view" value="checkbox">栏目管理员
			<input type="checkbox" name="view" value="checkbox">系统管理员
		</td>
 		</tr>
</table>