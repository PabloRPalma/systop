<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<script type="text/javascript">
	function addHttp(obj){
		if (obj.value == "" || obj.value == null) {
			obj.value = "http://";
		} 
	}
	
	/**
	 * 显示首页flash显示图片上传组件
	 */
	function showFlashImg(flg){
		if (flg) {
			document.getElementById("flashImgDiv").style.display="";
			document.getElementById("flashImg").disabled="";
		}else{
			document.getElementById("flashImgDiv").style.display="none";
			document.getElementById("flashImg").disabled="true";
		}
	}
</script>
<table align="center" width="100%" bgcolor="#FFFFFF" cellpadding="4"
	cellspacing="1">
	<tr>
		<td width="20%" bgcolor="#EBEBEB">
			<div align="right"><span class="font_b">转向链接：</span></div>
		</td>
		<td bgcolor="#EBEBEB">
			<s:textfield name="model.linkUrl" onfocus="addHttp(this)" theme="simple" size="30" id="linkUrl" maxlength="255"/></td>
		<td width="25%" bgcolor="#EBEBEB"><span class="prompting"> *
		请填写有效的网址(如:http://www.systop.com.cn)</span></td>
	</tr>
	<tr>
		<td width="20%" bgcolor="#EBEBEB">
			<div align="right"><span class="font_b">文章作者：</span></div>
		</td>
		<td bgcolor="#EBEBEB">
			<s:textfield name="model.author" theme="simple" size="30" id="author" maxlength="100"/></td>
		<td width="18%" bgcolor="#EBEBEB"></td>
	</tr>
	<tr>
		<td width="20%" bgcolor="#EBEBEB">
		<div align="right"><span class="font_b">文章简介：</span></div>
		</td>
		<td bgcolor="#EBEBEB">
		<s:textarea name="model.summary" id="summary" cols="60" rows="2" ></s:textarea></td>
		<td width="18%" bgcolor="#EBEBEB"></td>
	</tr>
	<tr>
		<td bgcolor="#EBEBEB">
		<div align="right"><span class="font_b">关键字：</span></div>
		</td>
		<td bgcolor="#EBEBEB"><s:textfield name="model.keyWord" theme="simple" size="68" id="keyWord"/></td>
		<td bgcolor="#EBEBEB"><span class="prompting"> *
		多个关键字，用“|”隔开。</span></td>
	</tr>
	<tr>
		<td width="20%" bgcolor="#EBEBEB">
			<div align="right"><span class="font_b">文章属性：</span></div>
		</td>
		<td bgcolor="#EBEBEB">
			<input type="checkbox" name="model.onTop" id="onTop" value="1" ${model.onTop == 1?"checked":""}/>固顶文章
			<input type="checkbox" name="model.isElite" id="isElite" value="1" ${model.isElite == 1?"checked":""} onclick="showFlashImg(this.checked)"/>推荐文章
			<div id="flashImgDiv" style="display: none;">
				<span class="font_b">&nbsp;Flash图片：</span>
				<input type="file" id="flashImg" name="flashImg" cssClass="FileText" style="width:300px"/><br><br>
				<span class="font_b">已使用图片：</span>
				<a href="${ctx}/${model.flashImg}" target="_blank">${model.flashImg}</a>
			</div>
		</td>
		<td bgcolor="#EBEBEB"><span class="STYLE1"></span></td>
	</tr>
	<tr>
		<td width="20%" bgcolor="#EBEBEB">
		<div align="right"><span class="font_b">点击数初始值：</span></div>
		</td>
		<td bgcolor="#EBEBEB">
			<s:textfield name="model.hits" theme="simple" maxlength="5" size="5" id="hits" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="this.value=this.value.replace(/\D/g,'')"/>
		<td bgcolor="#EBEBEB"><span class="STYLE1"></span></td>
	</tr>
</table>
