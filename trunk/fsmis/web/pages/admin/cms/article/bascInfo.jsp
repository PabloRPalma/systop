
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function onDisplay(){
    document.getElementById("wordNumber").style.display = "block";
}

function onHide(){
	 document.getElementById("wordNumber").style.display = "none";
}
/** set parentId */
function setCatalog(){
	CatalogDwrAction.getCatalog("catalog","1",function(catalog){
		DWRUtil.removeAllOptions("catalogId");
		DWRUtil.addOptions($("catalogId"), catalog,
    		function getValue(catalog) {
    			return catalog.id;
    		},
    		function getText(catalog) {
    			return catalog.text;
    		});
    	//设置默认栏目，用于修改时
		DWRUtil.setValue("catalogId", $('model.catalog.id').value);
		} 
	);
}
</script>
<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2"
	cellspacing="1">
	<tr>
		<td width="90" bgcolor="#EBEBEB">
		<div align="right"><span class="font_b">所属栏目：</span></div>
		</td>
		<td width="" bgcolor="#EBEBEB" colspan="2">
		<table id="coloum">
			<tr id="clmTr">
				<td>
					<select id="catalogId" name="catalogId"/>
					<s:hidden name="model.catalog.id" id="model.catalog.id"/>
				</td>
				<td align="right" width="140">
				<span class="prompting">*请选择该文章所属的栏目</span>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="90" bgcolor="#EBEBEB">
		<div align="right">文章标题：</div>
		</td>
		<td width="" bgcolor="#EBEBEB">简短标题：<s:textfield cssStyle="background-image : url('../../images/icons/TxtRule.jpg');"
			name="model.shortTitle" theme="simple" size="70" id="model.shortTitle" maxlength="170" />&nbsp;<font
			color="red">*</font><br>
		完整标题：<s:textfield name="model.title" theme="simple" size="70"
			id="model.title" maxlength="250" />&nbsp;<font color="red">*</font><br>
		副&nbsp;标&nbsp;题：<s:textfield name="model.subtitle" theme="simple" size="70"
			id="subtitle" maxlength="170" />&nbsp;<font color="red">*</font></td>
		<td width="140" bgcolor="#EBEBEB"><span class="prompting">*简短标题为页面显示标题
		</span></td>
	</tr>
	<tr>
		<td width="" bgcolor="#EBEBEB" height="100%" align="right">文章内容：
		</td>
		<td colspan="2" bgcolor="#EBEBEB" style=" padding:0px 0px 0px 0px;">
		<s:textarea id="model.content" name="model.content" rows="" cols=""></s:textarea>
		</td>
	</tr>
</table>
<script type="text/javascript">
function preFckEditor(){
	var fckEditor = new FCKeditor( 'model.content' ) ;
    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
    fckEditor.ToolbarSet = 'Default';
    fckEditor.Height = 340;
    fckEditor.ReplaceTextarea();
    setCatalog();
}
</script>

